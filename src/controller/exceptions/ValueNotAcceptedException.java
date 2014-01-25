package controller.exceptions;

public class ValueNotAcceptedException extends AutomatException {
	private static final long serialVersionUID = 1L;

	
	public ValueNotAcceptedException() {
		super();
	}
	
	public ValueNotAcceptedException(String msg) {
		super(msg);
	}
}
