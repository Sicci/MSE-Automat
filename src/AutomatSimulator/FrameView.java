package AutomatSimulator;
import javax.swing.*;

import AutomatSimulator.Automat.Component.ImageAreaComp;
import AutomatSimulator.Automat.Component.Numpad.*;
import AutomatSimulator.Automat.Component.Payment.*;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class FrameView extends JFrame {
	private static final long serialVersionUID = 1L;
	private StatusView svStatusView;
	private AutomatView avAutomatView;
	private LogView lvLogView;
	
	public FrameView() {
		svStatusView 	= new StatusView();
		avAutomatView 	= new AutomatView();
		lvLogView 		= new LogView();
		
	    addWindowListener(new WindowAdapter(){
	        public void windowClosing(WindowEvent we){
	            System.exit(0);
	            }
	        });
	    
	    setBounds(0, 0, 800, 600);
	    setMinimumSize(new Dimension(800, 600));
	    //setResizable(false);
	    
		GridBagConstraints gbc = new GridBagConstraints();
		setLayout(new GridBagLayout());
		
		gbc.weightx = 1.0;
		gbc.fill = GridBagConstraints.BOTH;
		
		gbc.weightx = 0.20;
		gbc.gridx = 0;
		gbc.gridy = 0;
	    add(svStatusView, gbc);
		
		gbc.weightx = 0.5;
		gbc.weighty = 0.3;
		gbc.gridx = 1;
		gbc.gridy = 0;
		add(avAutomatView, gbc);
	    
		
		gbc.weightx = 0.30;
		gbc.gridx = 2;
		gbc.gridy = 0;
		add(lvLogView, gbc);
	    
	    setVisible(true);

	    initView();
	}
	
	
	
	// if you wanna set listeners & observers from the view instead of mps
	
	private void initView() {
	    final JTextArea taLog = lvLogView.getLogPanelTextArea();
	    
	    // Additional PaymentButtonEvents
	    ArrayList<PaymentButton> alpb = getPaymentButtons();
		for (final PaymentButton pb : alpb) {
			pb.addMouseListener(new MouseAdapter() {
		        public void mouseClicked(MouseEvent me) {
		        	taLog.setText(taLog.getText() + "\n" + "Clicked PaymentButton: " + "(" + pb.getValue() + ")");
		        }
		    });
		}
		
		// Additional MoneyButtonEvents
	    HashMap<String, MoneyButtonPanel> hmmbp = getMoneyButtonPanels();
	    Iterator<String> i4hmmbp = hmmbp.keySet().iterator();
	    final PaymentDisplay pd = getPaymentDisplay();
	    
	    while(i4hmmbp.hasNext()){
	    	String key = i4hmmbp.next();
			for (final MoneyButton mb : hmmbp.get(key).getMoneyButtons()) {
				mb.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						taLog.setText(taLog.getText() + "\n" + "Clicked MoneyButton: " + "(" + mb.getValue() + ")");
						pd.setText(mb.getValue());
					}
				});
			}
	    }
		
		// Additional NumpadButtonEvents
		ArrayList<NumpadButton> alnb = getNumpadButtons();
		final NumpadDisplay nd = getNumpadDisplay();
		
		for (final NumpadButton nb : alnb) {
			nb.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					taLog.setText(taLog.getText() + "\n" + "Clicked NumpadButton: " + "(" + nb.getValue() + ")");
					
					if(nb.getValue()!="C" && nb.getValue()!="E") {
						nd.setText(nd.getText() + nb.getValue());
					} else {
						nd.setText("");
					}
				}
			});
		}
		
		
		// neat stuff
		final ImageAreaComp iac = avAutomatView.getBottomArea().getAutomatOutput();
		iac.addMouseListener(new MouseAdapter() {
	        public void mouseClicked(MouseEvent me) {
	        	taLog.setText(taLog.getText() + "\n" + "Tried to take stuff out of the machine");
	        }
	    });
		
		
		taLog.setText(avAutomatView.getAutomatName() + " wurde gestartet");
	}
	
	// shortcuts
	private HashMap<String, MoneyButtonPanel> getMoneyButtonPanels() { return avAutomatView.getMiddleArea().getPaymentPanel().getMoneyButtonPanels(); }
	private ArrayList<PaymentButton> getPaymentButtons() { return avAutomatView.getMiddleArea().getPaymentPanel().getPaymentButtonPanel().getPaymentButtons(); }
	private ArrayList<NumpadButton> getNumpadButtons() { return avAutomatView.getMiddleArea().getNumpadPanel().getNumpad().getNumpadButtons(); }

	private PaymentDisplay getPaymentDisplay() { return avAutomatView.getMiddleArea().getPaymentPanel().getPaymentDisplay(); }
	private NumpadDisplay getNumpadDisplay() { return avAutomatView.getMiddleArea().getNumpadPanel().getNumpadDisplay(); }

	
	public static void main(String args[]) throws IOException
	{
	    new FrameView();
	}

}