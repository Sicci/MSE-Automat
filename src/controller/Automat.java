package controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import localisation.Localiser;
import controller.commands.ICommand;
import controller.exceptions.AutomatException;
import controller.exceptions.InvalidInputException;
import controller.exceptions.ItemNotAvailableException;
import controller.exceptions.NoItemSelectedException;
import controller.exceptions.NoPartialCardPaymentException;
import controller.exceptions.NotEnoughChangeException;
import controller.exceptions.ValueNotAcceptedException;
import data.Item;
import data.Money;

public class Automat extends Observable {
	private String name;
	private String language;
	private String currency;
	private String fileItems;
	private String fileMoney;

	private ItemStorage itemStorage;
	private MoneyStorage moneyStorage;

	private String currentItemId;
	private Item currentItem;

	private List<Integer> outputChange;
	private List<Integer> inputMoney;

	private Item outputItem;

	public Automat() throws NotEnoughChangeException {
		this.name = "SODA MASTER 3000";

		setCurrency("€");
		setFileItems("items.txt");
		setFileMoney("money.txt");

		itemStorage = new ItemStorage();
		itemStorage.readFromFile(fileItems);

		moneyStorage = new MoneyStorage();
		moneyStorage.readFromFile(fileMoney);

		reset();
	}

	private boolean checkIfItemIsAvailable(String numCode) {
		return itemStorage.checkIfItemIsAvailable(numCode);
	}

	private boolean checkIfInputIsValid(String numCode) {
		return itemStorage.hasItemWithNumCode(numCode);
	}

	private Item getItemByNumCode(String numCode) {
		return itemStorage.getItemByNumCode(numCode);
	}

	public String getName() {
		return name;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public ItemStorage getItems() {
		return itemStorage;
	}

	public Item getCurrentItem() {
		return currentItem;
	}

	public void setCurrentItem(Item currentItem) {
		this.currentItem = currentItem;
	}

	public MoneyStorage getMoneyStorage() {
		return moneyStorage;
	}

	public String getFileItems() {
		return fileItems;
	}

	public void setFileItems(String fileItems) {
		this.fileItems = fileItems;
	}

	public String getFileMoney() {
		return fileMoney;
	}

	public void setFileMoney(String fileMoney) {
		this.fileMoney = fileMoney;
	}

	public String getCurrentItemId() {
		return currentItemId;
	}

	private void setCurrentItemId(String currentItemId) {
		this.currentItemId = currentItemId;

		this.setChanged();
		this.notifyObservers("itemIdChanged");
	}

	public int getSumInputMoney() {
		int currentMoney = 0;

		if (inputMoney != null) {
			for (Integer i : inputMoney) {
				currentMoney += i;
			}
		}

		return currentMoney;
	}

	public void insertMoney(int value) throws ValueNotAcceptedException, NotEnoughChangeException, NoItemSelectedException {
		if (currentItem == null) {
			throw new NoItemSelectedException("Select an item!");
		}

		if (inputMoney == null) {
			inputMoney = new ArrayList<Integer>();
		}

		if (!moneyStorage.isAccpetedValue(value)) {
			throw new ValueNotAcceptedException("This value is not being accepted by the automat!");
		}

		// ToDO: check if correct form

		inputMoney.add(value);

		this.setChanged();
		this.notifyObservers("insertedMoney");

		if (isItemPaid()) {
			boolean isPayingWithCard = false;

			outputItem(isPayingWithCard);
		}
	}

	public void payWithCard(String card) throws NotEnoughChangeException, NoPartialCardPaymentException, NoItemSelectedException {
		// if paying with cards, return all inserted money first
		boolean isPayingWithCard = true;

		if (inputMoney != null && inputMoney.size() > 0) {
			throw new NoPartialCardPaymentException("No card after coins!");
		}
		
		if (currentItem == null) {
			throw new NoItemSelectedException("Select an item!");
		}

		outputChange = inputMoney;
		inputMoney = null;

		outputItem(isPayingWithCard);

		this.setChanged();
		this.notifyObservers("paidWithCard");
	}

	private void outputItem(boolean isPayingWithCard) throws NotEnoughChangeException {

		if (!isPayingWithCard) {
			int len = inputMoney.size();

			calcAndHandOutChange(getUpdatedCurrentItemCost());

			for (int i = 0; i < len; i++) {
				moneyStorage.addToStorage(inputMoney.remove(0));
			}
		}

		setOutputItem(handOutItem());

		this.setChanged();
		this.notifyObservers("handedOutItem");

		reset();
	}

	public int getUpdatedCurrentItemCost() {
		int price = 0;

		if (currentItem != null) {
			price = currentItem.getPrice();
		}

		return price - getSumInputMoney();
	}

	private boolean isItemPaid() {
		return currentItem != null && getSumInputMoney() >= currentItem.getPrice();
	}

	public void addToCurrentId(String s) {
		// ToDO: check if correct form

		String t = getCurrentItemId() + s;

		if (getCurrentItemId().length() > 3) {
			setCurrentItemId(t.substring(1));
		} else {
			setCurrentItemId(t);
		}
	}

	public void clearCurrentId() {
		if (currentItem == null) {
			setCurrentItemId("");
		} else {
			currentItem = null;

			outputChange = inputMoney;
			inputMoney = null;

			this.setChanged();
			this.notifyObservers("handedOutChange");
		}
	}

	public boolean hasEnoughChangeMoney() {
		Money minValue = null;
		Money maxValue = null;

		boolean hasEnoughChangeMoney = true;

		// smallest and biggest coin
		for (Money i : moneyStorage.getMoneyList()) {
			if (minValue == null || i.getValue() < minValue.getValue()) {
				minValue = i;
			}

			if (maxValue == null || i.getValue() > maxValue.getValue()) {
				maxValue = i;
			}
		}

		// max price
		for (Item i : itemStorage.getItemList()) {
			if (i.getPrice() % minValue.getValue() != 0) {
				hasEnoughChangeMoney = false;
			}
		}

		int neededQuantity = maxValue.getValue() / minValue.getValue();

		if (minValue.getQuantity() < neededQuantity) {
			hasEnoughChangeMoney = false;
		}

		return hasEnoughChangeMoney;
	}

	public void calcAndHandOutChange(int value) throws NotEnoughChangeException {
		int exchange = Math.abs(value);

		List<Integer> l = new ArrayList<Integer>();
		for (int i = moneyStorage.size() - 1; i >= 0; i--) {
			Money m = moneyStorage.get(i);

			int amount = exchange / m.getValue();

			if (amount > 0) {
				for (int j = 0; (j < amount) && m.getQuantity() > 0; j++) {
					l.add(m.getValue());
					exchange -= m.getValue();
					m.setQuantity(m.getQuantity() - 1);
				}
			}
		}

		this.outputChange = l;

		this.setChanged();
		this.notifyObservers("handedOutChange");
		this.notifyObservers("statsChanged");
	}

	public List<Integer> retrieveChange() {
		List<Integer> l = this.outputChange;
		this.outputChange = null;
		return l;
	}

	private Item handOutItem() {
		Item i = getCurrentItem();

		if (i != null) {
			i.reduceQuantity();

			this.setChanged();
			this.notifyObservers("statsChanged");
		}

		return i;
	}

	public void reset() throws NotEnoughChangeException {
		currentItem = null;
		setCurrentItemId("");
		// setCurrentMoney(0);

		// currentChange = new ArrayList<Integer>();

		if (!hasEnoughChangeMoney()) {
			throw new NotEnoughChangeException(Localiser.getString("Automat.Error_NotEnoughChange"));
		}

		itemStorage.writeToFile(fileItems);
		moneyStorage.writeToFile(fileMoney);
	}

	public void handleCommand(ICommand cmd) throws AutomatException {
		// System.out.println(cmd.getClass());
		cmd.execute(this);
	}

	public void selectItem() throws ItemNotAvailableException, InvalidInputException {
		String numCode = getCurrentItemId();

		currentItemId = "";

		if (this.checkIfInputIsValid(numCode)) {
			if (this.checkIfItemIsAvailable(numCode)) {
				Item item = this.getItemByNumCode(numCode);

				if (item != null) {
					this.setCurrentItem(item);
				} else {
					// highly unlikely
					throw new ItemNotAvailableException(Localiser.getString("Automat.Error_ItemNotAvailable"));
				}

				this.setChanged();
				this.notifyObservers("itemSelected");
			} else {
				throw new ItemNotAvailableException(Localiser.getString("Automat.Error_ItemNotAvailable"));
			}
		} else {
			throw new InvalidInputException(Localiser.getString("Automat.Error_InvalidInput"));
		}
	}

	public Item getOutputItem() {
		return outputItem;
	}

	private void setOutputItem(Item outputItem) {
		this.outputItem = outputItem;
	}

	public String[] getAcceptedCoins() {
		return moneyStorage.getAcceptedCoins();
	}

	public String[] getAcceptedNotes() {
		return moneyStorage.getAcceptedNotes();
	}

	public String[] getAcceptedCards() {
		return moneyStorage.getAcceptedCards();
	}
}
