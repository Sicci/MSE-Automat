package data;

public class Money {
	private double value;
	private int quantity;
	
	public Money(double value, int quantity) {
		super();
		this.value = value;
		this.quantity = quantity;
	}
	
	public double getValue() {
		return value;
	}
	public void setValue(double value) {
		this.value = value;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
}
