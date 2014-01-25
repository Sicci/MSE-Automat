package AutomatSimulator.Automat.Component.Payment;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class MoneyButton extends JButton {
	private static final long serialVersionUID = 1L;
	private int value;
	
	public MoneyButton(String value) {
		super(value);
		initStyle();
		setText(value);
		
		try {
			this.value = Integer.valueOf(value);
		} catch (NumberFormatException e) {
			this.value = -1;
		}
	}
	
	public int getValue() {
		return value;
	}
	
	private void initStyle() {
		setForeground(Color.BLACK);
		setBackground(new Color(170,190,205));

		Font font = new Font("Arial",Font.BOLD, 10);
		setFont(font);
		
		Border line = new LineBorder(Color.DARK_GRAY);
		Border margin = new EmptyBorder(0, 0, 0, 0);
		Border compound = new CompoundBorder(line, margin);
		setBorder(compound);
		
		setCursor(new Cursor(Cursor.HAND_CURSOR));
	}
}