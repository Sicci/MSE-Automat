package data;

public class Item {
	private String name;
	private double price;
	private int quantity;
	private String numCode;
	
	public Item() {
		
	}
	
	public Item(String numCode, String name, double price, int quantity) {
		super();
		this.name = name;
		this.price = price;
		this.quantity = quantity; //TODO hier speichern oder in ein objekt auslagern
		this.setNumCode(numCode);
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
	
	public double getPrice() {
		return price;
	}
	
	public void setPrice(double price) {
		this.price = price;
	}
	
	@Override
	public String toString() {
		return name +"("+quantity+") "+ price;
		
	}

	public String getNumCode() {
		return numCode;
	}

	public void setNumCode(String numCode) {
		this.numCode = numCode;
	}
	
	public void reduceQuantity() {
		this.quantity--;
	}
}
