package controller.exceptions;

public class NoPartialCardPaymentException extends AutomatException {
	private static final long serialVersionUID = 1L;

	
	public NoPartialCardPaymentException() {
		super();
	}
	
	public NoPartialCardPaymentException(String msg) {
		super(msg);
	}
}
