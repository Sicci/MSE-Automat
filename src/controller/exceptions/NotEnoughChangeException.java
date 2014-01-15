package controller.exceptions;

public class NotEnoughChangeException extends AutomatException {
	private static final long serialVersionUID = 1L;

	
	public NotEnoughChangeException() {
		super();
	}
	
	public NotEnoughChangeException(String msg) {
		super(msg);
	}
}
