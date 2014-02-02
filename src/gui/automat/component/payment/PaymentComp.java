package gui.automat.component.payment;

import gui.AutomatView;
import gui.lib.StylePanel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.swing.border.EmptyBorder;


public class PaymentComp extends StylePanel {
	private static final long serialVersionUID = 1L;
	private HashMap <String, MoneyButtonPanel> hmMoneyButtonPanels;
	private PaymentDisplay pdPaymentDisplay;
	private PaymentButtonPanel pbpPaymentButtonPanel;
	
	private MoneyButtonPanel mbpCurrent;
	private MoneyButtonPanel mbpDefault;

	public PaymentComp(List<String> aAcceptedPaymentTypes, HashMap<String, String[]> hmAcceptedMoney) {
		super(AutomatView.COLOR, AutomatView.COLOR, new EmptyBorder(2, 1, 2, 1));
		
		Iterator<String> i4hmAcceptedMoney;
		MoneyButtonPanel p;
		
		hmMoneyButtonPanels = new HashMap<String, MoneyButtonPanel>();
		mbpCurrent = null;
		pdPaymentDisplay = new PaymentDisplay();
		pbpPaymentButtonPanel = new PaymentButtonPanel(aAcceptedPaymentTypes);
			
		gridStyle.setGrid(1.0,0.1,0,0);
	    add(pdPaymentDisplay, gridStyle);
	    gridStyle.setGrid(1.0,0.2,0,1);
	    add(pbpPaymentButtonPanel, gridStyle);
	    gridStyle.setGrid(1.0,1.0,0,2);

	    
	    i4hmAcceptedMoney = hmAcceptedMoney.keySet().iterator();
	    while(i4hmAcceptedMoney.hasNext()){
	    	String key = i4hmAcceptedMoney.next();
			p = new MoneyButtonPanel((String[])hmAcceptedMoney.get(key));
			hmMoneyButtonPanels.put(key, p);
	    }
		
		p = new MoneyButtonPanel();
		p.setBackground(AutomatView.COLOR);
		add(p, gridStyle);
		
		mbpCurrent = p;
		mbpDefault = p;
		
		
		// bind dem mechanicz
		for (final PaymentButton pb : pbpPaymentButtonPanel.getPaymentButtons()) {
			pb.addMouseListener(new MouseAdapter() {
		        public void mouseClicked(MouseEvent me) {
					changeMoneyButtonPanel(pb.getValue());
		        }
		    });
		}
		
		// after each click hide buttons again
		// i4hmAcceptedMoney = hmAcceptedMoney.keySet().iterator();
		// while(i4hmAcceptedMoney.hasNext()){
		// String key = i4hmAcceptedMoney.next();
		// for (final MoneyButton mb :
		// hmMoneyButtonPanels.get(key).getMoneyButtons()) {
		// mb.addActionListener(new ActionListener() {
		// public void actionPerformed(ActionEvent e) {
		// changeMoneyButtonPanel("default");
		// }
		// });
		// }
		// }
	}
	
	
	public HashMap<String, MoneyButtonPanel> getMoneyButtonPanels() {
		return hmMoneyButtonPanels;
	}
    
    public PaymentButtonPanel getPaymentButtonPanel() {
    	return pbpPaymentButtonPanel;
    }
    
    public PaymentDisplay getPaymentDisplay() {
    	return pdPaymentDisplay;
    }
	
	protected void initStyle(Color bgColor, Color lineColor, EmptyBorder margin) {
		super.initStyle(bgColor, lineColor, margin);
		setPreferredSize(new Dimension(15, 15));
	}

	public void changeMoneyButtonPanel(String key) {
		remove(mbpCurrent);
	
		gridStyle.setGrid(1.0,1.0,0,2);
		mbpCurrent = (hmMoneyButtonPanels.containsKey(key))? hmMoneyButtonPanels.get(key) : mbpDefault;
		add(mbpCurrent, gridStyle);
		
		revalidate();
		repaint();
	}
}
