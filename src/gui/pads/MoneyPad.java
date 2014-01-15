package gui.pads;

import gui.buttons.MoneyButton;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

import data.Money;

public class MoneyPad extends JPanel {
	private static final long serialVersionUID = 1L;

	private List<MoneyButton> btListMoney = new ArrayList<MoneyButton>();

	private JButton btMoneyCard;
	private JButton btReturnChange;
	private JTextField tfDisplayMoney;

	private JPanel pnNumpad;

	private int currentMoney;
	private List<Integer> insertedMoney;

	private boolean isEnabled;

	public MoneyPad(final ActionListener onFullyPayed, final List<Money> moneyStorage, final String currency) {
		super();

		initNumPad(onFullyPayed, moneyStorage, currency);
		reset();
	}

	protected void initNumPad(final ActionListener onFullyPayed, final List<Money> moneyStorage, final String currency) {
		pnNumpad = new JPanel(new GridLayout(0, 3));
		pnNumpad.setSize(10, 10);

		tfDisplayMoney = new JTextField("");
		tfDisplayMoney.setEditable(true);

		for (Money m : moneyStorage) {
			final MoneyButton tmpBtn = new MoneyButton(m.getValue());
			tmpBtn.setText("" + (double) (tmpBtn.getValue() / 100.) + currency);

			tmpBtn.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					int value = tmpBtn.getValue();

					insertedMoney.add(value);

					setCurrentMoney(currentMoney - value);

					btMoneyCard.setEnabled(false);

					if (currentMoney <= 0) {
						onFullyPayed.actionPerformed(e);
					}
				}

			});

			btListMoney.add(tmpBtn); // TODO +Currency
		}

		btMoneyCard = new JButton("Karte");
		btReturnChange = new JButton("Rückgeld");

		for (final MoneyButton btn : btListMoney) {
			pnNumpad.add(btn);
		}

		pnNumpad.add(btMoneyCard);
		pnNumpad.add(btReturnChange);
	}

	public List<MoneyButton> getBtListMoney() {
		return btListMoney;
	}

	public JButton getBtMoneyCard() {
		return btMoneyCard;
	}

	public JButton getBtReturnChange() {
		return btReturnChange;
	}

	public JTextField getTfDisplayMoney() {
		return tfDisplayMoney;
	}

	public JPanel getPanel() {
		return pnNumpad;
	}

	public int getCurrentMoney() {
		return currentMoney;
	}

	public boolean getEnabled() {
		return isEnabled;
	}

	public List<Integer> retrieveInsertedMoney() {
		List<Integer> l = insertedMoney;

		insertedMoney = new ArrayList<Integer>();

		return l;
	}

	public void setInsertedMoney(List<Integer> insertedMoney) {
		this.insertedMoney = insertedMoney;
	}

	public void setCurrentMoney(int price) {
		currentMoney = price;

		this.setText("Zu zahlen: " + (double) currentMoney / 100.);
	}

	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;

		for (MoneyButton m : btListMoney) {
			m.setEnabled(isEnabled);
		}

		btMoneyCard.setEnabled(isEnabled);
		btReturnChange.setEnabled(isEnabled);
	}

	public void setText(String t) {
		tfDisplayMoney.setText(t);
	}

	public void reset() {
		currentMoney = 0;
		insertedMoney = new ArrayList<Integer>();
		setText("-");
	}
}
