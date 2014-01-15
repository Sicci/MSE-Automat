package controller.exceptions;

public class ItemNotAvailableException extends AutomatException {
	private static final long serialVersionUID = 1L;

	
	public ItemNotAvailableException() {
		super();
	}
	
	public ItemNotAvailableException(String msg) {
		super(msg);
	}
}
