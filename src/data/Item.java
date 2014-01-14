package data;

public class Item {
	private String name;
	private int price;
	private int quantity;
	
	public Item() {
		
	}
	
	public Item(String name, int price, int quantity) {
		super();
		this.name = name;
		this.price = price;
		this.quantity = quantity; //TODO hier speichern oder in ein objekt auslagern
	}
	
	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public int getPrice() {
		return price;
	}
	
	public void setPrice(int price) {
		this.price = price;
	}
	
	@Override
	public String toString() {
		return name +"("+quantity+") "+ price;
		
	}
}
