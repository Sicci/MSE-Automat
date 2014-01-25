package AutomatSimulator.Automat.Component.Payment;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class PaymentDisplay extends JTextField{
	private static final long serialVersionUID = 1L;

	public PaymentDisplay() {
		super();
		initStyle();
	}

	public void initStyle() {
		setEditable(false);
		setBackground(new Color(180,190,205));
		setHorizontalAlignment(JTextField.RIGHT);
		
		Font font = new Font("Arial",Font.BOLD, 11);
		setFont(font);
		
		Border line = new LineBorder(Color.DARK_GRAY);
		Border margin = new EmptyBorder(0, 0, 0, 0);
		Border compound = new CompoundBorder(line, margin);
		setBorder(compound);
	}
}