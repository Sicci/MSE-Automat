package AutomatSimulator.Automat;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import AutomatSimulator.Lib.StylePanel;

public class TopAreaView extends StylePanel {
	private static final long serialVersionUID = 1L;
	private String sAutomatName;
	
	public TopAreaView(String sAutomatName) {
		this.sAutomatName = sAutomatName;
	}
	
	public void paint(Graphics g) {
		Font font = new Font("Arial",Font.BOLD,20);
		g.setFont(font);
		g.setColor(new Color(100,150,200));
		g.drawString(this.sAutomatName, 0, 25);
	}
	
	public String getAutomatName() {
		return this.sAutomatName;
	}
}