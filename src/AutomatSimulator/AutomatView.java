package AutomatSimulator;

import java.awt.Color;
import java.util.HashMap;

import javax.swing.border.EmptyBorder;

import AutomatSimulator.Automat.BottomAreaView;
import AutomatSimulator.Automat.MiddleAreaView;
import AutomatSimulator.Automat.TopAreaView;
import AutomatSimulator.Automat.Component.ImageAreaComp;
import AutomatSimulator.Lib.StylePanel;
import controller.Automat;

public class AutomatView extends StylePanel {
	public static final Color COLOR = new Color(1, 90, 158);
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
		iacAutomatLogo = new ImageAreaComp("img/logo_soda.png", AutomatView.COLOR);

		String[] aAccepedButtons = { "7", "8", "9", "4", "5", "6", "1", "2", "3", btnLabelCancel, "0", btnLabelEnter };

		String[] aAcceptedPaymentTypes = { "coin", "banknote", "creditcard" };

		HashMap<String, String[]> hmAcceptedMoney = new HashMap<String, String[]>();

		String[] aAcceptedCoins = automat.getAcceptedCoins(); // { "5", "10", "20", "50", "100", "200" };
		hmAcceptedMoney.put("coin", aAcceptedCoins);

		String[] aAcceptedBankNotes = automat.getAcceptedNotes(); // { "5", "10", "20" };
		hmAcceptedMoney.put("banknote", aAcceptedBankNotes);

		String[] aAcceptedCreditCards = automat.getAcceptedCards(); // { "Visa", "MasterCard" };
		hmAcceptedMoney.put("creditcard", aAcceptedCreditCards);

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