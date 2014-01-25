package AutomatSimulator.Automat;

import java.awt.Color;
import java.awt.Cursor;

import javax.swing.border.EmptyBorder;

import AutomatSimulator.AutomatView;
import AutomatSimulator.Lib.ImageAreaPanel;
import AutomatSimulator.Lib.StylePanel;

public class BottomAreaView extends StylePanel {
	private static final long serialVersionUID = 1L;
	private ImageAreaPanel iapAutomatOutput;
	
	public BottomAreaView() {
		super(AutomatView.COLOR, AutomatView.COLOR, new EmptyBorder(0, 0, 0, 0));
		gridStyle.setPadding(50, 0, 5, 0);
		
		gridStyle.setGrid(1.0,1.0,0,0);
		iapAutomatOutput = new ImageAreaPanel("img/output_soda_empty.png", Color.BLACK);
		iapAutomatOutput.setCursor(new Cursor(Cursor.HAND_CURSOR));
		add(iapAutomatOutput, gridStyle);
	}
	
	public ImageAreaPanel getAutomatOutput() {
		return iapAutomatOutput;
	}
}