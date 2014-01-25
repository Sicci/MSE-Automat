package AutomatSimulator;

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
import javax.swing.JOptionPane;

import AutomatSimulator.Automat.Component.ImageAreaComp;
import AutomatSimulator.Automat.Component.Numpad.NumpadButton;
import AutomatSimulator.Automat.Component.Numpad.NumpadDisplay;
import AutomatSimulator.Automat.Component.Payment.MoneyButton;
import AutomatSimulator.Automat.Component.Payment.MoneyButtonPanel;
import AutomatSimulator.Automat.Component.Payment.PaymentButton;
import AutomatSimulator.Automat.Component.Payment.PaymentDisplay;
import controller.Automat;
import controller.commands.ChangeItemIdCommand;
import controller.commands.ClearItemIdCommand;
import controller.commands.SelectItemCommand;
import controller.commands.InsertMoneyCommand;
import controller.commands.InsertCardCommand;
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

		setBounds(0, 0, 800, 600);
		setMinimumSize(new Dimension(800, 600));
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
							}
						} else {
							try {
								automat.handleCommand(new InsertCardCommand(mb.getText()));
							} catch (AutomatException exc) {
								getPaymentDisplay().setText(exc.getMessage());
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
						}
					} else if (nb.getValue() == avAutomatView.getBtnLabelCancel()) {
						try {
							automat.handleCommand(new ClearItemIdCommand());
						} catch (AutomatException exc) {
							getNumpadDisplay().setText(exc.getMessage());
						}
					} else {
						try {
							automat.handleCommand(new ChangeItemIdCommand(nb.getValue()));
						} catch (AutomatException exc) {
							getNumpadDisplay().setText(exc.getMessage());
						}
					}
				}
			});
		}

		// neat stuff
		final ImageAreaComp iac = avAutomatView.getBottomArea().getAutomatOutput();
		iac.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent me) {
				lvLogView.addToLog("\n" + "Tried to take stuff out of the machine");
			}
		});

		lvLogView.clearLog();
		lvLogView.addToLog(avAutomatView.getAutomatName() + " wurde gestartet");

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
		try {
			Automat automat = new Automat();
			new FrameView(automat);
		} catch (AutomatException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			System.exit(-1);
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		if (!(arg instanceof String)) {
			System.err.println("no string in AutomatView.update(...)");
			return;
		}

		String t = (String) arg;

		if (t.equals("itemSelected")) {
			lvLogView.addToLog("\nItem selected: " + automat.getCurrentItem().getName() + "\n");
			getPaymentDisplay().setText("to pay: " + automat.getUpdatedCurrentItemCost());
		} else if (t.equals("statsChanged")) {
			lvLogView.addToLog("\nStats updated\n");
			updateStats();
		} else if (t.equals("handedOutChange")) {
			logChangeMoney(automat.retrieveChange());
		} else if (t.equals("itemIdChanged")) {
			if (automat.getCurrentItemId().length() > 0) {
				lvLogView.addToLog("\nCurrent Item Id = " + automat.getCurrentItemId() + "\n");
			}
			getNumpadDisplay().setText("Item: " + automat.getCurrentItemId());
		} else if (t.equals("insertedMoney")) {
			lvLogView.addToLog("\nCurrently in automat: " + automat.getSumInputMoney());
			getPaymentDisplay().setText("to pay: " + automat.getUpdatedCurrentItemCost());
		} else if (t.equals("handedOutItem")) {
			if (automat.getOutputItem() != null) {
				lvLogView.addToLog("\nHanded out item: " + automat.getOutputItem().getName());
			}

		} else if (t.equals("paidWithCard")) {
			if (automat.getOutputItem() != null) {
				lvLogView.addToLog("\nPaid with Card: " + automat.getOutputItem().getName());
			}
			logChangeMoney(automat.retrieveChange());
		}
	}

	private void logChangeMoney(List<Integer> change) {
		StringBuilder sb = new StringBuilder();

		int sum = 0;
		int i = 0;
		if (change != null) {
			for (int v : change) {
				if (i++ > 0)
					sb.append(", ");
				sb.append("" + v);
				sum += v;
			}
		}

		if (sb.length() > 0) {
			lvLogView.addToLog("\nChange: " + sb.toString());
		}

		if (sum > 0) {
			getPaymentDisplay().setText("Retrieve change! " + sum);
		} else {
			getPaymentDisplay().setText("");
		}
	}

	private void updateStats() {
		StringBuilder sb = new StringBuilder();

		for (Item i : automat.getItems().getItemList()) {
			sb.append("" + i.getNumCode() + ": " + (i.getPrice() / 100.) + automat.getCurrency() + " - " + i.getName() + " (" + i.getQuantity() + "x)\n");
		}

		svStatusView.getItemStoragePanelTextArea().setText(sb.toString());

		sb.setLength(0);

		for (Money m : automat.getMoneyStorage().getMoneyList()) {
			sb.append("" + m.getValue() + ":\t" + m.getQuantity() + "x");
			sb.append('\n');
		}

		svStatusView.getMoneyStoragePanelTextArea().setText(sb.toString());
	}

}