package data;

import java.util.ArrayList;

public class Automat {
	private AutomatType type;
	private String language;
	private String currency;
	private ArrayList<Item> itemList;
	//accept money --> new object?
	//current money in automat --> new object?
	
	//TODO generate Constructor
	
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

}
