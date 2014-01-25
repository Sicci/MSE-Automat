package controller.exceptions;

public class NoItemSelectedException extends AutomatException {
	private static final long serialVersionUID = 1L;

	
	public NoItemSelectedException() {
		super();
	}
	
	public NoItemSelectedException(String msg) {
		super(msg);
	}
}
