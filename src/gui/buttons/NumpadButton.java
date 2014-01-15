package gui.buttons;
import javax.swing.JButton;


public class NumpadButton extends JButton {
	private static final long serialVersionUID = 1L;
	private int value;
	
	public NumpadButton(int value) {
		super(""+value);
		this.value = value;
	}

	public int getValue() {
		return value;
	}

}
