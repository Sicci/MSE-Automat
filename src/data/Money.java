package data;

public class Money {
	private int value;
	private int quantity;

	public Money(int value, int quantity) {
		super();
		this.value = value;
		this.quantity = quantity;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

}
