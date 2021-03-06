package gui.automat.component.payment;

import gui.AutomatView;
import gui.automat.component.payment.PaymentButton;
import gui.lib.StylePanel;

import java.awt.Cursor;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;


public class PaymentButtonPanel extends StylePanel {
	private static final long serialVersionUID = 1L;
	private ArrayList<PaymentButton> aPaymentButtons;

	public PaymentButtonPanel(List<String> aAcceptedPaymentTypes) {	
		super();
		BufferedImage img;
		PaymentButton pb;
		ArrayList<String> alImgPaths;

		aPaymentButtons = new ArrayList<PaymentButton>();
		alImgPaths = new ArrayList<String>();
		for(String pt : aAcceptedPaymentTypes) {
			alImgPaths.add(AutomatView.RESPATH + "slot_" + pt + ".png");
		}

		for (int i = 0; i < alImgPaths.size(); i++) {
			final int y = i;

			gridStyle.setGrid(0.33,1.0,y,0);
			try {
				img = ImageIO.read(new File( alImgPaths.get(y)));
				pb = new PaymentButton(img, aAcceptedPaymentTypes.get(y));
				pb.setCursor(new Cursor(Cursor.HAND_CURSOR));
			      
			} catch (Exception e) {
				pb = new PaymentButton();
		    	System.out.println("Error: Missing Image");
		    	System.exit(0);
			}
			aPaymentButtons.add(pb);
		    add(pb, gridStyle);
		}

		// hackerton
		for(int j = 0; j < 4 - aPaymentButtons.size(); j++) {
			pb = new PaymentButton();
			gridStyle.setGrid(0.33,1.0,j+1,0);
		    add(pb, gridStyle);
		}
		
	}
	
    public ArrayList<PaymentButton> getPaymentButtons() {
    	return aPaymentButtons;
    }
}