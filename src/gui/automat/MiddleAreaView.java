package gui.automat;

import gui.AutomatView;
import gui.automat.component.ImageAreaComp;
import gui.automat.component.numpad.NumpadComp;
import gui.automat.component.payment.PaymentComp;
import gui.lib.StylePanel;

import java.awt.Color;
import java.util.HashMap;
import java.util.List;

import javax.swing.border.EmptyBorder;


public class MiddleAreaView extends StylePanel {
	private static final long serialVersionUID = 1L;
	private NumpadComp ncNumpadComp; 
	private PaymentComp pcPaymentComp; 
	
	public MiddleAreaView(  ImageAreaComp iacAutomatLogo, 
							String[] aAccepedButtons, 
							List<String> aAcceptedPaymentTypes, 
							HashMap<String, String[]> hmAcceptedMoney) {
		
		super(AutomatView.COLOR, Color.BLACK, new EmptyBorder(5, 5, 5, 5));

		
		gridStyle.setGrid(0.5,0.5,1,0);
		this.ncNumpadComp = new NumpadComp(aAccepedButtons);
		this.ncNumpadComp.setBackground(AutomatView.COLOR);
	    add(this.ncNumpadComp, gridStyle);

		gridStyle.setGrid(0.5,0.5,1,1);
		this.pcPaymentComp = new PaymentComp(aAcceptedPaymentTypes, hmAcceptedMoney);
		this.pcPaymentComp.setBackground(AutomatView.COLOR);
	    add(this.pcPaymentComp, gridStyle);

	    gridStyle.setGrid(0.5,0.5,0,0);
	    this.gridStyle.gridheight = 2;
	    add(iacAutomatLogo, gridStyle);
	}
	
	public NumpadComp getNumpadPanel() {
		return this.ncNumpadComp;
	}
	
	public PaymentComp getPaymentPanel() {
		return this.pcPaymentComp;
	}
}