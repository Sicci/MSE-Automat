package AutomatSimulator.Automat;

import java.awt.Color;
import java.util.HashMap;

import javax.swing.border.EmptyBorder;

import AutomatSimulator.AutomatView;
import AutomatSimulator.Automat.Component.ImageAreaComp;
import AutomatSimulator.Automat.Component.Numpad.NumpadComp;
import AutomatSimulator.Automat.Component.Payment.PaymentComp;
import AutomatSimulator.Lib.StylePanel;

public class MiddleAreaView extends StylePanel {
	private static final long serialVersionUID = 1L;
	private NumpadComp nvNumpadView; 
	private PaymentComp pvPaymentView; 
	
	public MiddleAreaView(  ImageAreaComp iacAutomatLogo, 
							String[] aAccepedButtons, 
							String[] aAcceptedPaymentTypes, 
							HashMap<String, String[]> hmAcceptedMoney) {
		
		super(AutomatView.COLOR, Color.BLACK, new EmptyBorder(5, 5, 5, 5));

		
		gridStyle.setGrid(0.5,0.5,1,0);
		this.nvNumpadView = new NumpadComp(aAccepedButtons);
		this.nvNumpadView.setBackground(AutomatView.COLOR);
	    add(this.nvNumpadView, gridStyle);

		gridStyle.setGrid(0.5,0.5,1,1);
		this.pvPaymentView = new PaymentComp(aAcceptedPaymentTypes, hmAcceptedMoney);
		this.pvPaymentView.setBackground(AutomatView.COLOR);
	    add(this.pvPaymentView, gridStyle);

	    gridStyle.setGrid(0.5,0.5,0,0);
	    this.gridStyle.gridheight = 2;
	    add(iacAutomatLogo, gridStyle);
	}
	
	public NumpadComp getNumpadPanel() {
		return this.nvNumpadView;
	}
	
	public PaymentComp getPaymentPanel() {
		return this.pvPaymentView;
	}
}