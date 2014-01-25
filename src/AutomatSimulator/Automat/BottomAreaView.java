package AutomatSimulator.Automat;

import java.awt.Color;
import java.awt.Cursor;

import javax.swing.border.EmptyBorder;

import AutomatSimulator.AutomatView;
import AutomatSimulator.Automat.Component.ImageAreaComp;
import AutomatSimulator.Lib.StylePanel;

public class BottomAreaView extends StylePanel {
	private static final long serialVersionUID = 1L;
	private ImageAreaComp iacAutomatOutput;
	
	public BottomAreaView() {
		super(AutomatView.COLOR, AutomatView.COLOR, new EmptyBorder(0, 0, 0, 0));
		gridStyle.setPadding(50, 0, 5, 0);
		
		gridStyle.setGrid(1.0,1.0,0,0);
		iacAutomatOutput = new ImageAreaComp("img/output_soda_empty.png", Color.BLACK);
		iacAutomatOutput.setCursor(new Cursor(Cursor.HAND_CURSOR));
		add(iacAutomatOutput, gridStyle);
	}
	
	public ImageAreaComp getAutomatOutput() {
		return iacAutomatOutput;
	}
}