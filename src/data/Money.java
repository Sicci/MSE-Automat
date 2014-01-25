package data;

public class Money {
	private int value;
	private int quantity;
	private MoneyType type;

	public Money(int value, int quantity, MoneyType type) {
		super();
		this.value = value;
		this.quantity = quantity;
		this.type = type;
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
	
	public MoneyType getType() {
		return type;
	}

}
