package gui;

import gui.automat.component.ImageAreaComp;
import gui.automat.component.numpad.NumpadButton;
import gui.automat.component.numpad.NumpadDisplay;
import gui.automat.component.payment.MoneyButton;
import gui.automat.component.payment.MoneyButtonPanel;
import gui.automat.component.payment.PaymentButton;
import gui.automat.component.payment.PaymentDisplay;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;

import localisation.Localiser;

import controller.Automat;
import controller.commands.ChangeItemIdCommand;
import controller.commands.ClearItemIdCommand;
import controller.commands.InsertCardCommand;
import controller.commands.InsertMoneyCommand;
import controller.commands.SelectItemCommand;
import controller.exceptions.AutomatException;
import data.Item;
import data.Money;

public class FrameView extends JFrame implements Observer {
	private static final long serialVersionUID = 1L;
	private StatusView svStatusView;
	private AutomatView avAutomatView;
	private LogView lvLogView;

	private Automat automat;

	public FrameView(Automat automat) {
		this.automat = automat;

		automat.addObserver(this);

		avAutomatView = new AutomatView(automat);
		svStatusView = new StatusView();

		lvLogView = new LogView();

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				System.exit(0);
			}
		});

		setBounds(0, 0, 1024, 768);
		setMinimumSize(new Dimension(1024, 768));
		// setResizable(false);

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

		// Additional PaymentButtonEvents
		ArrayList<PaymentButton> alpb = getPaymentButtons();
		for (final PaymentButton pb : alpb) {
			pb.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent me) {
					// lvLogView.addToLog("\nClicked PaymentButton: " + "(" +
					// pb.getValue() + ")");
				}
			});
		}

		// Additional MoneyButtonEvents
		HashMap<String, MoneyButtonPanel> hmmbp = getMoneyButtonPanels();
		Iterator<String> i4hmmbp = hmmbp.keySet().iterator();

		while (i4hmmbp.hasNext()) {
			String key = i4hmmbp.next();
			for (final MoneyButton mb : hmmbp.get(key).getMoneyButtons()) {
				mb.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if (mb.getValue() > 0) {
							try {
								automat.handleCommand(new InsertMoneyCommand(mb.getValue()));
							} catch (AutomatException exc) {
								getPaymentDisplay().setText(exc.getMessage());
								lvLogView.addToLog("\n" + exc.getMessage());
							}
						} else {
							try {
								automat.handleCommand(new InsertCardCommand(mb.getText()));
							} catch (AutomatException exc) {
								getPaymentDisplay().setText(exc.getMessage());
								lvLogView.addToLog("\n" + exc.getMessage());
							}
						}
					}
				});
			}
		}

		// Additional NumpadButtonEvents
		ArrayList<NumpadButton> alnb = getNumpadButtons();

		for (final NumpadButton nb : alnb) {
			nb.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (nb.getValue() == avAutomatView.getBtnLabelEnter()) {
						try {
							automat.handleCommand(new SelectItemCommand());
						} catch (AutomatException exc) {
							getNumpadDisplay().setText(exc.getMessage());
							lvLogView.addToLog("\n" + exc.getMessage());
						}
					} else if (nb.getValue() == avAutomatView.getBtnLabelCancel()) {
						try {
							automat.handleCommand(new ClearItemIdCommand());
						} catch (AutomatException exc) {
							getNumpadDisplay().setText(exc.getMessage());
							lvLogView.addToLog("\n" + exc.getMessage());
						}
					} else {
						try {
							automat.handleCommand(new ChangeItemIdCommand(nb.getValue()));
						} catch (AutomatException exc) {
							getNumpadDisplay().setText(exc.getMessage());
							lvLogView.addToLog("\n" + exc.getMessage());
						}
					}
				}
			});
		}

		// neat stuff
		final ImageAreaComp iacFull = avAutomatView.getBottomArea().getAutomatOutputFull();
		iacFull.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent me) {
				avAutomatView.getBottomArea().changeToEmpty();

				lvLogView.addToLog(Localiser.getString("EMPTIED_COMPARTMENT"));
			}
		});

		final ImageAreaComp iacEmpty = avAutomatView.getBottomArea().getAutomatOutputEmpty();
		iacEmpty.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent me) {
				// avAutomatView.getBottomArea().changeToFull();
				lvLogView.addToLog(Localiser.getString("EMPTY_COMPARTMENT"));
			}
		});

		lvLogView.clearLog();
		lvLogView.addToLog(Localiser.getString("AUTOMAT_START").replace("%s", avAutomatView.getAutomatName()));
		
		getNumpadDisplay().setText(Localiser.getString("ITEM_PROMPT_START"));
		getPaymentDisplay().setText(Localiser.getString("PAYMENT_PROMPT_START"));
		
		updateStats();
	}

	// shortcuts
	private HashMap<String, MoneyButtonPanel> getMoneyButtonPanels() {
		return avAutomatView.getMiddleArea().getPaymentPanel().getMoneyButtonPanels();
	}

	private ArrayList<PaymentButton> getPaymentButtons() {
		return avAutomatView.getMiddleArea().getPaymentPanel().getPaymentButtonPanel().getPaymentButtons();
	}

	private ArrayList<NumpadButton> getNumpadButtons() {
		return avAutomatView.getMiddleArea().getNumpadPanel().getNumpad().getNumpadButtons();
	}

	private PaymentDisplay getPaymentDisplay() {
		return avAutomatView.getMiddleArea().getPaymentPanel().getPaymentDisplay();
	}

	private NumpadDisplay getNumpadDisplay() {
		return avAutomatView.getMiddleArea().getNumpadPanel().getNumpadDisplay();
	}

	public static void main(String args[]) throws IOException {
		Automat automat = new Automat();
		new FrameView(automat);
	}

	@Override
	public void update(Observable o, Object arg) {
		if (!(arg instanceof String)) {
			System.err.println("no string in AutomatView.update(...)");
			return;
		}

		String t = (String) arg;

		if (t.equals("itemSelected")) {
			lvLogView.addToLog(Localiser.getString("ITEM_SELECTED").replace("%s", automat.getCurrentItem().getName()));
			getPaymentDisplay().setText(Localiser.getString("PAYMENT_PROMPT").replace("%s", formatCurrency(automat.getUpdatedCurrentItemCost())));
		} else if (t.equals("statsChanged")) {
			updateStats();
		} else if (t.equals("handedOutChange")) {
			logChangeMoney(automat.retrieveChange());
		} else if (t.equals("itemIdChanged")) {
			if (automat.getCurrentItemId().length() > 0) {
				lvLogView.addToLog(Localiser.getString("ITEM_SELECTION_CHANGE").replace("%s", automat.getCurrentItemId()));
			}
			getNumpadDisplay().setText(Localiser.getString("ITEM_PROMPT").replace("%s", automat.getCurrentItemId()));
		} else if (t.equals("insertedMoney")) {
			lvLogView.addToLog(Localiser.getString("MONEY_INSERTED").replace("%s", formatCurrency(automat.getSumInputMoney())));
			getPaymentDisplay().setText(Localiser.getString("PAYMENT_PROMPT").replace("%s", formatCurrency(automat.getUpdatedCurrentItemCost())));
		} else if (t.equals("handedOutItem")) {
			if (automat.getOutputItem() != null) {
				lvLogView.addToLog(Localiser.getString("OUTPUT_ITEM").replace("%s", automat.getOutputItem().getName()));

				avAutomatView.getBottomArea().changeToFull();
			}

		} else if (t.equals("paidWithCard")) {
			if (automat.getOutputItem() != null) {
				lvLogView.addToLog(Localiser.getString("PAID_WITH_CARD").replace("%s", formatCurrency(automat.getOutputItem().getPrice())));
			}
			logChangeMoney(automat.retrieveChange());
		} else if (t.startsWith("log:")) {
			lvLogView.addToLog(Localiser.getString("LOG") + Localiser.getString(t.substring(4)));
		}
	}

	private String formatCurrency(int v) {
		return String.format("%.2f", v / 100.) + automat.getCurrency();
	}

	private void logChangeMoney(List<Integer> change) {
		StringBuilder sb = new StringBuilder();

		int sum = 0;
		int i = 0;
		if (change != null) {
			for (int v : change) {
				if (i++ > 0)
					sb.append(", ");
				sb.append("" + formatCurrency(v));
				sum += v;
			}
		}

		if (sb.length() > 0) {
			lvLogView.addToLog(Localiser.getString("CHANGE_OUTPUT").replace("%s", sb.toString()));
		} else {
			lvLogView.addToLog(Localiser.getString("NO_CHANGE"));
		}

		if (sum > 0) {
			getPaymentDisplay().setText(Localiser.getString("RETRIEVE_CHANGE_PROMPT").replace("%s", formatCurrency(sum)));
		} else {
			getPaymentDisplay().setText("");
		}
	}

	private void updateStats() {
		StringBuilder sb = new StringBuilder();

		for (Item i : automat.getItems().getItemList()) {
			sb.append("" + i.getNumCode() + ": " + formatCurrency(i.getPrice()) + " - " + i.getName() + " (" + i.getQuantity() + "x)\n");
		}

		svStatusView.getItemStoragePanelTextArea().setText(sb.toString());

		sb.setLength(0);

		for (Money m : automat.getMoneyStorage().getMoneyList()) {
			sb.append("" + formatCurrency(m.getValue()) + ":\t" + m.getQuantity() + "x");
			sb.append('\n');
		}

		svStatusView.getMoneyStoragePanelTextArea().setText(sb.toString());
	}

}