package controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import controller.commands.ICommand;
import controller.exceptions.AutomatException;
import controller.exceptions.InvalidInputException;
import controller.exceptions.ItemNotAvailableException;
import controller.exceptions.NotEnoughChangeException;
import data.AutomatType;
import data.Item;
import data.Money;

public class Automat extends Observable {
	private AutomatType type;
	private String language;
	private String currency;
	private String fileItems;
	private String fileMoney;

	private ItemStorage itemStorage;
	private MoneyStorage moneyStorage;

	private Item currentItem;
	private List<Integer> currentChange;

	public Automat() throws NotEnoughChangeException {
		type = AutomatType.beverages;

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

	public AutomatType getType() {
		return type;
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

	public void returnExchangeMoney(int value) throws NotEnoughChangeException {
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

		this.currentChange = l;

		this.setChanged();
		this.notifyObservers("readyForRetrievingChange");
		this.notifyObservers("statsChanged");
	}

	public List<Integer> retrieveExchangeMoney() {
		List<Integer> l = this.currentChange;
		this.currentChange = null;
		return l;
	}

	public void addMoneyToStorage(int value) {
		boolean hasChanged = false;

		if (value > 0) {
			for (Money m : moneyStorage.getMoneyList()) {
				if (m.getValue() == value) {
					m.setQuantity(m.getQuantity() + 1);
					hasChanged = true;
				}
			}
		}

		if (hasChanged) {
			this.setChanged();
			this.notifyObservers("statsChanged");
		}
	}

	public Item returnItem() {
		Item i = getCurrentItem();
		i.reduceQuantity();

		this.setChanged();
		this.notifyObservers("statsChanged");

		return i;
	}

	public void reset() throws NotEnoughChangeException {
		currentItem = null;
		// currentChange = new ArrayList<Integer>();

		if (!hasEnoughChangeMoney()) {
			throw new NotEnoughChangeException("Not enough change!");
		}

		itemStorage.writeToFile(fileItems);
		moneyStorage.writeToFile(fileMoney);
	}

	public void handleCommand(ICommand cmd) throws AutomatException {
		// System.out.println(cmd.getClass());
		cmd.execute(this);
	}

	public void selectItem(String numCode) throws ItemNotAvailableException, InvalidInputException {
		if (this.checkIfInputIsValid(numCode)) {
			if (this.checkIfItemIsAvailable(numCode)) {
				Item item = this.getItemByNumCode(numCode);
				if (item != null) {
					this.setCurrentItem(item);
				} else {
					// highly unlikely
					throw new ItemNotAvailableException("Item's really not available");
				}
				this.setChanged();
				this.notifyObservers("itemSelected");
			} else {
				throw new ItemNotAvailableException("Item not available");
			}
		} else {
			throw new InvalidInputException("Invalid Input");
		}
	}

}
