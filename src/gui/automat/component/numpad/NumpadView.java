package gui.automat.component.numpad;

import gui.AutomatView;
import gui.lib.StylePanel;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.border.EmptyBorder;


public class NumpadView extends StylePanel {
	private static final long serialVersionUID = 1L;
	private NumpadDisplay ndNumpadDisplay;
	private NumpadButtonPanel nNumpad;

	public NumpadView(String[] aAcceptedButtons) {
		super(AutomatView.COLOR, AutomatView.COLOR, new EmptyBorder(2, 0, 2, 0));

		gridStyle.setGrid(1.0,0.1,0,0);
		ndNumpadDisplay = new NumpadDisplay();
	    add(ndNumpadDisplay, gridStyle);

		gridStyle.setGrid(1.0,0.8,0,1);
		nNumpad = new NumpadButtonPanel(aAcceptedButtons);
	    add(nNumpad, gridStyle);
	}
	
	public NumpadDisplay getNumpadDisplay() {
		return ndNumpadDisplay;
	}
	
	public NumpadButtonPanel getNumpad() {
		return nNumpad;
	}
	
	protected void initStyle(Color bgColor, Color lineColor, EmptyBorder margin) {
		super.initStyle(bgColor, lineColor, margin);
		setPreferredSize(new Dimension(15, 15));
	}
	
}
