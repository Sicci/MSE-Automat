package controller.exceptions;

public class InvalidInputException extends AutomatException {
	private static final long serialVersionUID = 1L;

	
	public InvalidInputException() {
		super();
	}
	
	public InvalidInputException(String msg) {
		super(msg);
	}
}
