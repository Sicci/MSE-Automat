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
	private List<Item> itemList;
	private List<Money> moneyStorage;

	private Item currentItem;
	private List<Integer> currentChange;

	public Automat() throws NotEnoughChangeException {
		setCurrency("€");

		itemList = new ArrayList<Item>();
		itemList.add(new Item("1", "Cola", 120, 10));
		itemList.add(new Item("2", "Fanta", 130, 15));
		itemList.add(new Item("3", "Sprite", 140, 5));

		moneyStorage = new ArrayList<Money>();
		moneyStorage.add(new Money(10, 100));
		moneyStorage.add(new Money(20, 0));
		moneyStorage.add(new Money(50, 0));
		moneyStorage.add(new Money(100, 100));
		moneyStorage.add(new Money(200, 100));
		moneyStorage.add(new Money(500, 50)); // schein
		moneyStorage.add(new Money(1000, 0)); // schein

		if (!hasEnoughChangeMoney()) {
			throw new NotEnoughChangeException("Not enough change!");
		}

		reset();
	}

	public boolean checkIfItemIsAvailable(String numCode) {
		boolean isAvailable = false;
		Item item = getItemByNumCode(numCode);
		if (item != null && item.getQuantity() > 0) {
			isAvailable = true;
		}
		return isAvailable;
	}

	public boolean checkIfInputIsValid(String numCode) {
		boolean isValid = false;
		for (Item item : itemList) {
			if (numCode.equals(item.getNumCode())) {
				isValid = true;
			}
		}
		return isValid;
	}

	public Item getItemByNumCode(String numCode) {
		Item foundItem = null;
		for (Item item : itemList) {
			if (item.getNumCode().equals(numCode)) {
				foundItem = item;
			}
		}
		return foundItem;
	}

	public AutomatType getType() {
		return type;
	}

	public void setType(AutomatType type) {
		this.type = type;
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

	public List<Item> getItemList() {
		return itemList;
	}

	public void setItemList(List<Item> itemList) {
		this.itemList = itemList;
	}

	public Item getCurrentItem() {
		return currentItem;
	}

	public void setCurrentItem(Item currentItem) {
		this.currentItem = currentItem;
	}

	public List<Money> getMoneyStorage() {
		return moneyStorage;
	}

	public void setMoneyStorage(List<Money> moneyStorage) {
		this.moneyStorage = moneyStorage;
	}

	public boolean hasEnoughChangeMoney() {
		Money minValue = null;
		Money maxValue = null;

		boolean hasEnoughChangeMoney = true;

		// smallest and biggest coin
		for (Money i : moneyStorage) {
			if (minValue == null || i.getValue() < minValue.getValue()) {
				minValue = i;
			}

			if (maxValue == null || i.getValue() > maxValue.getValue()) {
				maxValue = i;
			}
		}

		// max price
		for (Item i : itemList) {
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
		System.out.println(exchange);
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
			for (Money m : moneyStorage) {
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

	public void reset() {
		currentItem = null;
		// currentChange = new ArrayList<Integer>();
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
