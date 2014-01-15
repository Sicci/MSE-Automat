package gui.pads;

import gui.buttons.NumpadButton;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class NumPad extends JPanel {
	private static final long serialVersionUID = 1L;
	private JTextField tfDisplayStatus;
	private List<NumpadButton> btListNumpad = new ArrayList<NumpadButton>();

	private JPanel pnNumpad;

	private JButton btDel;

	private JButton btEnter;

	private String currentNumCode;

	private boolean isEnabled;

	public NumPad() {
		super();

		initNumPad();
		reset();
	}

	protected void initNumPad() {
		pnNumpad = new JPanel(new GridLayout(4, 3));

		tfDisplayStatus = new JTextField("");
		tfDisplayStatus.setEditable(false);

		for (int i = 0; i <= 9; i++) {
			btListNumpad.add(new NumpadButton(i));
		}

		btDel = new JButton("c");
		btEnter = new JButton("enter");

		for (final NumpadButton btn : btListNumpad) {
			btn.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					setCurrentNumCode(getCurrentNumCode() + btn.getValue());

					if (currentNumCode.length() > 3) {
						setCurrentNumCode(getCurrentNumCode().substring(1));
					}

				}
			});
		}

		btDel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setCurrentNumCode("");

				// System.out.println("Eingegebenes Geld wird ausgeworfen.");
				// model.resetAutomat();
				// resetGUI();
			}
		});

		// Add buttons in desired order:
		for (int i = 1; i <= 9; i++) {
			pnNumpad.add(btListNumpad.get(i));
		}

		pnNumpad.add(btDel);
		pnNumpad.add(btListNumpad.get(0));
		pnNumpad.add(btEnter);

		this.setEnabled(true);
	}

	public JTextField getTfDisplayStatus() {
		return tfDisplayStatus;
	}

	public List<NumpadButton> getBtListNumpad() {
		return btListNumpad;
	}

	public JButton getBtDel() {
		return btDel;
	}

	public JButton getBtEnter() {
		return btEnter;
	}

	public JPanel getPanel() {
		return pnNumpad;
	}

	public String getCurrentNumCode() {
		return currentNumCode;
	}

	protected void setCurrentNumCode(String currentNumCode) {
		this.currentNumCode = currentNumCode;

		this.setText("Itemnummer: " + currentNumCode);
	}

	public void setText(String t) {
		tfDisplayStatus.setText(t);
	}

	public void setErrorText(String t) {
		tfDisplayStatus.setText("Itemnummer: " + currentNumCode + " (" + t + ")");
	}

	public boolean getEnabled() {
		return isEnabled;
	}

	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;

		for (NumpadButton m : btListNumpad) {
			m.setEnabled(isEnabled);
		}

		btEnter.setEnabled(isEnabled);
		btDel.setEnabled(isEnabled);
	}

	public void reset() {
		setCurrentNumCode("");
	}
}
