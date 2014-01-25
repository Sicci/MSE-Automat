package gui.automat.component.numpad;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class NumpadButton extends JButton {
	private static final long serialVersionUID = 1L;
	private String value;

	public NumpadButton(String text) {
		super(text);
		initStyle();
		setText(text);
		value = text;
	}
	
	public String getValue() {
		return value;
	}
	
	private void initStyle() {
		setForeground(Color.BLACK);
		setBackground(new Color(170,190,205));
		
		Font font = new Font("Arial",Font.BOLD, 12);
		setFont(font);
		
		Border line = new LineBorder(Color.DARK_GRAY);
		Border margin = new EmptyBorder(0, 0, 0, 0);
		Border compound = new CompoundBorder(line, margin);
		setBorder(compound);
		
		setCursor(new Cursor(Cursor.HAND_CURSOR));
	}
}