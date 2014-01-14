import javax.swing.JButton;


public class MoneyButton extends JButton {
	private static final long serialVersionUID = 1L;
	private double value;
	
	public MoneyButton(double value) {
		super();
		this.value = value;
	}

	public double getValue() {
		return value;
	}

}
