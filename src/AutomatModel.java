import java.util.ArrayList;
import java.util.Observable;

import data.AutomatType;
import data.Item;
import data.Money;

public class AutomatModel  extends Observable {
	private AutomatType type;
	private String language;
	private String currency;
	private ArrayList<Item> itemList;
	private ArrayList<Money> moneyStorage;
	
	private ArrayList<Double> currentMoney;
	private String currentNumCode;
	private Item currentItem;
	private double currentCosts;


	public AutomatModel() {
		
		currentMoney = new ArrayList<Double>();
		
		itemList = new ArrayList<Item>();
		itemList.add(new Item("1", "Cola", 1.2, 10));
		itemList.add(new Item("2", "Fanta", 1.3, 15));
		itemList.add(new Item("3", "Sprite", 1.4, 5));
		
		moneyStorage = new ArrayList<Money>();
		moneyStorage.add(new Money(0.1, 100));
		moneyStorage.add(new Money(0.2, 100));
		moneyStorage.add(new Money(0.5, 100));
		moneyStorage.add(new Money(1., 100));
		moneyStorage.add(new Money(2., 100));
		moneyStorage.add(new Money(5., 50)); // schein
		moneyStorage.add(new Money(10., 0)); // schein
		currentNumCode = "";

	}

	protected boolean checkIfItemIsAvailable() {
		boolean isAvailable = false;
		Item item = getItemByNumCode(currentNumCode);
		if (item != null && item.getQuantity() > 0) {
			isAvailable = true;
		}
		return isAvailable;
	}

	protected boolean checkIfInputIsValid() {
		boolean isValid = false;
		for (Item item : itemList) {
			if (currentNumCode.equals(item.getNumCode())) {
				isValid = true;
			}
		}
		return isValid;
	}

	protected Item getItemByNumCode(String numCode) {
		Item foundItem = null;
		for (Item item : itemList) {
			if (item.getNumCode().equals(numCode)) {
				foundItem = item;
			}
		}
		return foundItem;
	}
	
	protected boolean checkIfCurrentMoneyIsEnough() {
		boolean isEnough = false;
		if (currentCosts < 0.001) {
			isEnough = true;
		}
		return isEnough;
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

	public ArrayList<Item> getItemList() {
		return itemList;
	}

	public void setItemList(ArrayList<Item> itemList) {
		this.itemList = itemList;
	}

	public String getCurrentNumCode() {
		return currentNumCode;
	}

	public void setCurrentNumCode(String currentNumCode) {
		this.currentNumCode = currentNumCode;
	}
	public Item getCurrentItem() {
		return currentItem;
	}

	public void setCurrentItem(Item currentItem) {
		this.currentItem = currentItem;
		this.currentCosts = currentItem.getPrice();
	}

	public ArrayList<Money> getMoneyStorage() {
		return moneyStorage;
	}

	public void setMoneyStorage(ArrayList<Money> moneyStorage) {
		this.moneyStorage = moneyStorage;
	}

	public ArrayList<Double> getCurrentMoney() {
		return currentMoney;
	}

	public void setCurrentMoney(ArrayList<Double> currentMoney) {
		this.currentMoney = currentMoney;
	}

	public double getCurrentCosts() {
		return Math.floor((currentCosts * 100.0)+0.5) / 100.;
	}

	public void reduceCurrentCosts(double reduceValue) {
		
		this.currentCosts -= reduceValue;
	}

	public void returnExchangeMoney() {
		double exchange = Math.floor((Math.abs(currentCosts)*100)+0.5)/100;
		System.out.println("wechselgeld: "+exchange);
		//TODO macht ben
			
		
	}

	public Item returnItem() {
		Item i = getCurrentItem(); 
		i.reduceQuantity();
		return i;
		
	}

	public void resetAutomat() {
		currentCosts = 0.;
		currentItem = null;
		currentMoney = new ArrayList<Double>();
		currentNumCode = "";
		
	}


}
