package gui;

import gui.automat.BottomAreaView;
import gui.automat.MiddleAreaView;
import gui.automat.TopAreaView;
import gui.automat.component.ImageAreaComp;
import gui.lib.StylePanel;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.border.EmptyBorder;

import controller.Automat;

public class AutomatView extends StylePanel {
	public static final Color COLOR = new Color(1, 90, 158);
	public static final String RESPATH = "img/";
	private static final long serialVersionUID = 1L;

	private String sAutomatName;
	private TopAreaView tavTopAreaView;
	private MiddleAreaView mavMiddleAreaView;
	private BottomAreaView bavBottomAreaView;
	private Automat automat;
	
	private String btnLabelEnter = "E";
	private String btnLabelCancel = "C";

	public AutomatView(Automat automat) {
		super(AutomatView.COLOR, Color.BLACK, new EmptyBorder(5, 15, 5, 15));
		ImageAreaComp iacAutomatLogo;

		this.automat = automat;

		sAutomatName = automat.getName();
		iacAutomatLogo = new ImageAreaComp(AutomatView.RESPATH + "soda/logo.png", AutomatView.COLOR);

		String[] aAccepedButtons = { "7", "8", "9", "4", "5", "6", "1", "2", "3", btnLabelCancel, "0", btnLabelEnter };

		List<String> aAcceptedPaymentTypes = new ArrayList<String>();

		
		HashMap<String, String[]> hmAcceptedMoney = new HashMap<String, String[]>();

		String[] aAcceptedCoins = automat.getAcceptedCoins(); // { "5", "10", "20", "50", "100", "200" };
		if (aAcceptedCoins.length > 0) {
			hmAcceptedMoney.put("coin", aAcceptedCoins);
			aAcceptedPaymentTypes.add("coin");
		}

		String[] aAcceptedBankNotes = automat.getAcceptedNotes(); // { "5", "10", "20" };
		if (aAcceptedBankNotes.length > 0) {
			hmAcceptedMoney.put("banknote", aAcceptedBankNotes);
			aAcceptedPaymentTypes.add("banknote");
		}

		String[] aAcceptedCreditCards = automat.getAcceptedCards(); // { "Visa", "MasterCard" };
		if (aAcceptedCreditCards.length > 0) {
			hmAcceptedMoney.put("creditcard", aAcceptedCreditCards);
			aAcceptedPaymentTypes.add("creditcard");
		}

		gridStyle.setGrid(1.0, 0.1, 0, 0);
		tavTopAreaView = new TopAreaView(sAutomatName);
		add(tavTopAreaView, gridStyle);

		gridStyle.setGrid(0.5, 0.5, 0, 2);
		mavMiddleAreaView = new MiddleAreaView(iacAutomatLogo, aAccepedButtons, aAcceptedPaymentTypes, hmAcceptedMoney);
		add(mavMiddleAreaView, gridStyle);

		gridStyle.setGrid(1.0, 0.3, 0, 3);
		bavBottomAreaView = new BottomAreaView();
		add(bavBottomAreaView, gridStyle);
	}

	public String getAutomatName() {
		return sAutomatName;
	}

	public TopAreaView getTopArea() {
		return this.tavTopAreaView;
	}

	public MiddleAreaView getMiddleArea() {
		return this.mavMiddleAreaView;
	}

	public BottomAreaView getBottomArea() {
		return this.bavBottomAreaView;
	}

	public Automat getAutomat() {
		return automat;
	}
	
	public String getBtnLabelEnter() {
		return btnLabelEnter;
	}

	public String getBtnLabelCancel() {
		return btnLabelCancel;
	}

}
