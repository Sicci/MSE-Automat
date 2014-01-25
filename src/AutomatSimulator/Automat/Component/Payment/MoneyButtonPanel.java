package AutomatSimulator.Automat.Component.Payment;

import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.border.EmptyBorder;

import AutomatSimulator.AutomatView;
import AutomatSimulator.Lib.StylePanel;

public class MoneyButtonPanel extends StylePanel { // könnten auch mit Bildchen sein
	private static final long serialVersionUID = 1L;
	private ArrayList<MoneyButton> aMoneyButtons;
	
	public MoneyButtonPanel(String[] aBtnText) {
		super(AutomatView.COLOR,AutomatView.COLOR,new EmptyBorder(1,1,1,1));
		setPreferredSize(new Dimension(5,3));
		MoneyButton mb;
		
		aMoneyButtons = new ArrayList<MoneyButton>();
		
		int x =  0;
		int y = -1;

		for (int i = 0; i<aBtnText.length; i++) {
			if(i%3 == 0) {
				x = 0;
				y++;
			}

			gridStyle.setGrid(1.0,1.0,x,y);
			mb = new MoneyButton(aBtnText[i]);

			aMoneyButtons.add(mb);
			add(mb, gridStyle); 
			x++;
		}
	}
	
	public MoneyButtonPanel() {
		super(AutomatView.COLOR,AutomatView.COLOR,new EmptyBorder(1,1,1,1));
		aMoneyButtons = new ArrayList<MoneyButton>();
	}
	
	public ArrayList<MoneyButton> getMoneyButtons() {
		return aMoneyButtons;
	}
}