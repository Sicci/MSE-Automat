package AutomatSimulator.Automat;

import java.awt.Color;
import java.util.HashMap;

import javax.swing.border.EmptyBorder;

import AutomatSimulator.AutomatView;
import AutomatSimulator.Automat.Component.Numpad.NumpadView;
import AutomatSimulator.Automat.Component.Payment.PaymentView;
import AutomatSimulator.Lib.ImageAreaPanel;
import AutomatSimulator.Lib.StylePanel;

public class MiddleAreaView extends StylePanel {
	private static final long serialVersionUID = 1L;
	private NumpadView nvNumpadView; 
	private PaymentView pvPaymentView; 
	
	public MiddleAreaView(  ImageAreaPanel iapAutomatLogo, 
							String[] aAccepedButtons, 
							String[] aAcceptedPaymentTypes, 
							HashMap<String, String[]> hmAcceptedMoney) {
		
		super(AutomatView.COLOR, Color.BLACK, new EmptyBorder(5, 5, 5, 5));

		
		gridStyle.setGrid(0.5,0.5,1,0);
		this.nvNumpadView = new NumpadView(aAccepedButtons);
		this.nvNumpadView.setBackground(AutomatView.COLOR);
	    add(this.nvNumpadView, gridStyle);

		gridStyle.setGrid(0.5,0.5,1,1);
		this.pvPaymentView = new PaymentView(aAcceptedPaymentTypes, hmAcceptedMoney);
		this.pvPaymentView.setBackground(AutomatView.COLOR);
	    add(this.pvPaymentView, gridStyle);

	    gridStyle.setGrid(0.5,0.5,0,0);
	    this.gridStyle.gridheight = 2;
	    add(iapAutomatLogo, gridStyle);
	    
	}
	
	public NumpadView getNumpadPanel() {
		return this.nvNumpadView;
	}
	
	public PaymentView getPaymentPanel() {
		return this.pvPaymentView;
	}
}