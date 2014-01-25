package gui.automat.component.numpad;

import gui.AutomatView;
import gui.lib.StylePanel;

import java.util.ArrayList;
import javax.swing.border.EmptyBorder;


public class NumpadButtonPanel extends StylePanel {
	private static final long serialVersionUID = 1L;
	private ArrayList<NumpadButton> aNumpadButtons;

	public NumpadButtonPanel(String[] aAccepedButtons) {
		super(AutomatView.COLOR, AutomatView.COLOR, new EmptyBorder(2, 0, 2, 0));
		
		aNumpadButtons = new ArrayList<NumpadButton>();			

		int x =  0;
		int y = -1;

		for (int i = 0; i < aAccepedButtons.length; i++) {
			if(i%3 == 0) {
				x = 0;
				y++;
			}

			gridStyle.setGrid(0.3,1.0,x,y);
		    NumpadButton btnNum = new NumpadButton(aAccepedButtons[i]);    
		    aNumpadButtons.add(btnNum);
		    add(btnNum, gridStyle);
			x++;
		}
	}
	
	public ArrayList<NumpadButton> getNumpadButtons() {
		return aNumpadButtons;
	}
}