package gui;

import gui.pads.MoneyPad;
import gui.pads.NumPad;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.Box;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import controller.Automat;
import controller.commands.SelectItemCommand;
import controller.exceptions.AutomatException;
import controller.exceptions.NotEnoughChangeException;
import data.Item;
import data.Money;

public class AutomatView extends JPanel implements Observer {

	private static final long serialVersionUID = 1L;

	private Automat model;

	/*
	 * GUI elements
	 */
	private JTextArea taItemList;
	private JTextArea taStatusMessages;
	private JTextArea taAutomatInformation;

	private NumPad numPad;
	private MoneyPad moneyPad;

	public AutomatView(final Automat model) {
		this.model = model;
		model.addObserver(this);

		Box flowPanel = Box.createHorizontalBox();

		flowPanel.add(buildBoxItemList());
		flowPanel.add(Box.createRigidArea(new Dimension(5, 0)));

		flowPanel.add(buildBoxNumpad());
		flowPanel.add(Box.createRigidArea(new Dimension(5, 0)));

		flowPanel.add(buildBoxMoney());

		add(flowPanel);

		handleStatsChanged();
	}

	private Box buildBoxItemList() {
		JScrollPane scrollpane = null;
		StringBuilder sb = new StringBuilder();
		Box box = Box.createVerticalBox();

		box.setAlignmentY(0);

		taItemList = new JTextArea(50, 10);
		taItemList.setPreferredSize(new Dimension(150, 220));
		taItemList.setWrapStyleWord(true);
		taItemList.setLineWrap(true);
		taItemList.setEditable(false);

		for (Item i : model.getItems().getItemList()) {
			sb.append("" + i.getNumCode() + ": " + (i.getPrice() / 100.) + model.getCurrency() + " - " + i.getName() + "\n");
		}
		taItemList.setText(sb.toString());

		// for (int i = 0; i < 40; i++)
		// taItemList.setText(taItemList.getText() + "item " + i + "\n");

		scrollpane = new JScrollPane(taItemList, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		// scrollpane.setBorder(BorderFactory.createTitledBorder("ItemList"));
		scrollpane.setPreferredSize(new Dimension(250, 250));

		box.add(scrollpane);

		return box;
	}

	private Box buildBoxNumpad() {
		JScrollPane scrollpane = null;
		Box box = Box.createVerticalBox();

		numPad = new NumPad();

		box.setAlignmentY(0);

		taStatusMessages = new JTextArea(30, 5);
		taStatusMessages.setPreferredSize(new Dimension(250, 300));
		taStatusMessages.setWrapStyleWord(false);
		taStatusMessages.setLineWrap(false);
		taStatusMessages.setEditable(false);

		numPad.getBtEnter().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				try {
					model.handleCommand(new SelectItemCommand(numPad.getCurrentNumCode()));
				} catch (AutomatException ex) {
					numPad.setErrorText("Fehler: " + ex.getMessage());
				}
			}
		});

		scrollpane = new JScrollPane(taStatusMessages, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollpane.setPreferredSize(new Dimension(250, 500));

		box.add(numPad.getTfDisplayStatus());
		box.add(Box.createRigidArea(new Dimension(0, 5)));

		box.add(numPad.getPanel());
		box.add(Box.createRigidArea(new Dimension(0, 5)));

		box.add(scrollpane);
		// box.add(taAutomatInformation);

		return box;
	}

	private Box buildBoxMoney() {
		JScrollPane scrollpane = null;
		Box box = Box.createVerticalBox();

		ActionListener onFullyPaid = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				List<Integer> l = moneyPad.retrieveInsertedMoney();

				int len = l.size();

				for (int i = 0; i < len; i++) {
					model.addMoneyToStorage(l.remove(0));
				}

				moneyPad.setEnabled(false);

				try {
					model.returnExchangeMoney(moneyPad.getCurrentMoney());

					Item i = model.returnItem();

					makeReadyForNewOrder();

					taStatusMessages.setText("Ausgeworfenes Item:\n " + i.getName() + "\n" + taStatusMessages.getText());

					taStatusMessages.setText("------------------------\n" + taStatusMessages.getText());
				} catch (NotEnoughChangeException ex) {
					numPad.setErrorText(ex.getMessage());
				}
			}
		};

		moneyPad = new MoneyPad(onFullyPaid, model.getMoneyStorage().getMoneyList(), model.getCurrency());

		moneyPad.getBtReturnChange().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				handleRetrieveChange(moneyPad.retrieveInsertedMoney());
				makeReadyForNewOrder();
			}
		});

		moneyPad.getBtMoneyCard().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Item i = model.returnItem();
				makeReadyForNewOrder();

				taStatusMessages.setText("Ausgeworfenes Item:\n " + i.getName() + " (Zahlung mit Karte)\n" + taStatusMessages.getText());
				taStatusMessages.setText("------------------------\n" + taStatusMessages.getText());
			}
		});

		taAutomatInformation = new JTextArea(30, 5);
		taAutomatInformation.setPreferredSize(new Dimension(250, 100));
		taAutomatInformation.setWrapStyleWord(false);
		taAutomatInformation.setLineWrap(false);
		taAutomatInformation.setEditable(false);

		scrollpane = new JScrollPane(taAutomatInformation, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollpane.setPreferredSize(new Dimension(250, 300));

		box.setAlignmentY(0);

		box.add(moneyPad.getTfDisplayMoney());
		box.add(Box.createRigidArea(new Dimension(0, 5)));
		box.add(moneyPad.getPanel());
		box.add(Box.createRigidArea(new Dimension(0, 5)));
		box.add(taAutomatInformation);

		// box.add(Box.createRigidArea(new Dimension(0, 350)));

		moneyPad.setEnabled(false);

		return box;
	}

	@Override
	public void update(Observable o, Object arg) {
		if (!(arg instanceof String)) {
			System.err.println("no string in AutomatView.update(...)");
			return;
		}

		String t = (String) arg;

		if (t.equals("itemSelected")) {
			handleItemSelected();
		} else if (t.equals("statsChanged")) {
			handleStatsChanged();
		} else if (t.equals("readyForRetrievingChange")) {
			handleRetrieveChange(model.retrieveExchangeMoney());
		}
	}

	public void handleItemSelected() {
		moneyPad.setText("" + model.getCurrentItem().getPrice());
		numPad.setText("Bitte Geld einwerfen für " + model.getCurrentItem().getName());
		numPad.setEnabled(false);
		moneyPad.setEnabled(true);
		moneyPad.setCurrentMoney(model.getCurrentItem().getPrice());
	}

	public void handleStatsChanged() {
		StringBuilder sb = new StringBuilder();

		sb.append("Automat-Information:\n\n");

		sb.append("Items:\n");
		for (Item i : model.getItems().getItemList()) {
			sb.append("" + i.getNumCode() + " - " + i.getName() + " - " + (i.getPrice() / 100.) + model.getCurrency() + " - " + i.getQuantity());
			sb.append('\n');
		}

		sb.append("\nMoney:\n");
		for (Money m : model.getMoneyStorage().getMoneyList()) {
			sb.append("" + m.getValue() + " - " + m.getQuantity() + "x");
			sb.append('\n');
		}

		taAutomatInformation.setText(sb.toString());
	}

	public void handleRetrieveChange(List<Integer> l) {
		StringBuilder sb = new StringBuilder();

		sb.append("\nRückgeld:\n ");

		for (int i = 0; i < l.size(); i++) {
			sb.append(l.get(i) / 100. + model.getCurrency());

			if (i != l.size() - 1) {
				sb.append(", ");
			}

			if ((i + 1) % 3 == 0 && i != 0) {
				sb.append("\n ");
			}
		}

		if (l.size() == 0) {
			sb.append(" keins");
		}

		sb.append('\n');
		sb.append('\n');

		taStatusMessages.setText(sb.toString() + taStatusMessages.getText());
	}

	public final void makeReadyForNewOrder() {
		moneyPad.reset();
		numPad.reset();
		try {
			model.reset();
		} catch (NotEnoughChangeException e) {
			JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			System.exit(-1);
		}

		moneyPad.setEnabled(false);
		numPad.setEnabled(true);

	}
}
