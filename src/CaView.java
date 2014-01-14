import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class CaView extends JPanel implements ActionListener, Observer {

	private static final long serialVersionUID = 1L;

	private AutomatModel model;
	
	private JTextArea itemList, automatInformation;
	
	
	private JTextField statusDisplay, priceDisplay;
	private JButton zero, one, two, three, four, five, six, seven, eight, nine;
	private JButton money10, money20, money50, money100, money200, moneyCard;
	
	//dynamisch buttons für geldeinwurf

		
	
	public CaView(AutomatModel model) {
		this.model = model;
		JScrollPane scrollpane = null;
			
		
		// Layout
		// �������������������������������������������������������������������������������������� 
		//JPanel flowPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
		Box flowPanel = Box.createHorizontalBox();
		JPanel numpad = new JPanel(new GridLayout(4,3));	
		JPanel moneypad = new JPanel(new GridLayout(0,3));
		moneypad.setSize(10,10);
	
		Box box1 = Box.createVerticalBox();
		Box box2 = Box.createVerticalBox();
		Box box3 = Box.createVerticalBox();
		
		box1.setAlignmentY(0);
		box2.setAlignmentY(0);
		box3.setAlignmentY(0);
		
		//boxall.setPreferredSize(new Dimension(430,250));
		
			
		// JTextAreas, JTextFields und Labels
		// ��������������������������������������������������������������������������������������
		
		statusDisplay = new JTextField("");
		statusDisplay.setEditable(true);
		
		priceDisplay = new JTextField("");
		priceDisplay.setEditable(true);
		
		itemList = new JTextArea();
		itemList.setPreferredSize(new Dimension(150,220));
		itemList.setWrapStyleWord(true);
		itemList.setLineWrap(true);
		itemList.setEditable(false);
		
		automatInformation = new JTextArea("Informationen über den Automaten\nTyp:Getränke");
		automatInformation.setPreferredSize(new Dimension(250,100));
		automatInformation.setWrapStyleWord(false);
		automatInformation.setLineWrap(false);
		automatInformation.setEditable(false);
		
		JLabel label = new JLabel("Key\\Keywort"); //TODO connect label with string from properties or xml file
					
		scrollpane = new JScrollPane(itemList);
		scrollpane.setBorder(BorderFactory.createTitledBorder("ItemList")); //TODO @string from xml file
		scrollpane.setPreferredSize(new Dimension(100,250)); //???	
					
			
		// Buttons
		// ��������������������������������������������������������������������������������������
		zero = new JButton("0");
		one = new JButton("1");
		two = new JButton("2");
		three = new JButton("3");
		four = new JButton("4");
		five = new JButton("5");
		six = new JButton("6");
		seven = new JButton("7");
		eight = new JButton("8");
		nine = new JButton("9");
		
		money10 = new JButton("0,50"); //TODO +Currency
		money20 = new JButton("0,20");
		money50 = new JButton("0,50");
		money100 = new JButton("1");
		money200 = new JButton("2");
		moneyCard = new JButton("Karte");
		
		// Adding Components
		// ��������������������������������������������������������������������������������������
			
		numpad.add(one);
		numpad.add(two);
		numpad.add(three);
		numpad.add(four);
		numpad.add(five);
		numpad.add(six);
		numpad.add(seven);
		numpad.add(eight);
		numpad.add(nine);
		numpad.add(zero);
		
		moneypad.add(money10);
		moneypad.add(money20);
		moneypad.add(money50);
		moneypad.add(money100);
		moneypad.add(money200);
		moneypad.add(moneyCard);
		
		box1.add(itemList);
		
		box2.add(statusDisplay);
		box2.add(Box.createRigidArea(new Dimension(0,5)));
		box2.add(numpad);
		box2.add(Box.createRigidArea(new Dimension(0,5)));
		box2.add(automatInformation);
		
		box3.add(priceDisplay);
		box3.add(Box.createRigidArea(new Dimension(0,5)));
		box3.add(moneypad);
		box3.add(Box.createRigidArea(new Dimension(0,150)));
		
		flowPanel.add(box1);
		flowPanel.add(Box.createRigidArea(new Dimension(5,0)));
		flowPanel.add(box2);
		flowPanel.add(Box.createRigidArea(new Dimension(5,0)));
		flowPanel.add(box3);
		add(flowPanel);
		
		
		// ActionListeners
		// ���������������������������������������������������������������������������������������
		
		zero.addActionListener(this);

		
		
	}
/*	
	private int bar() {
		mytest = model2.getMyDataList();
		b.setText(mytest.get(k).toString());
		return mytest.get(k).getKey();
	}
	
	private void chooseMethod(boolean caesar, boolean method)
			throws WrongInputException {
		if (caesar && a.getText().matches("\\d+")) {
			model.setKey(Integer.valueOf(a.getText()));
			if (method)
//				model.encipher();
				model.tryit(true);
			else
//				model.decipher();
				model.tryit(false);
		} else if (!caesar && a.getText().matches("[a-zA-Z]+")) {
			model.setKeyword(a.getText());
			if (method)
//				model.encipher_viginere();
				model.tryit_vig(true);
			else
//				model.decipher_viginere();
				model.tryit_vig(false);

		} else
			throw new WrongInputException();
	}
	
	private void readAttack() {
		try {
			if (kryptT.getText().matches("[a-zA-Z]+")) {

				this.k = 0;
				model2.analyse(kryptT.getText());
				decipher(bar());
			} else
				throw new WrongInputException();
		} catch (WrongInputException e) {
			JOptionPane.showMessageDialog(this,
					"Bitte nur Buchstaben eingeben", "Eingabefehler",
					JOptionPane.ERROR_MESSAGE);

		}
	}
	
	
	private void decipher(int key) {
		model.setCiphertext(kryptT.getText());
		model.setKey(key);
//		model.decipher();
		model.tryit(false);
		enkryptT.setText(model.getPlaintext());
	}

	private void readInput(boolean bool) {
		try {
			boolean caesar = false;

			if (cae.isSelected())
				caesar = true;

			if (bool) {
				if (!(plainT.getText().equals(""))) {
					model.setPlaintext(plainT.getText());
					chooseMethod(caesar, bool);
					cipherT.setText(model.getCiphertext());
				} else
					throw new NoCalculationException();
			} else {
				if (!(cipherT.getText().equals(""))) {
					model.setCiphertext(cipherT.getText());
					chooseMethod(caesar, bool);
					plainT.setText(model.getPlaintext());
				} else
					throw new NoCalculationException();
			}

		} catch (WrongInputException e) {
			JOptionPane
					.showMessageDialog(
							this,
							"Nicht erlaubtes Zeichen f�r Ihre gew�hlte Verschl�sselung",
							"Eingabefehler", JOptionPane.ERROR_MESSAGE);
		} catch (NoCalculationException e) {
			JOptionPane.showMessageDialog(this,
					"Es existiert kein Text zum verschl�sseln/entschl�sseln",
					"Eingabefehler", JOptionPane.ERROR_MESSAGE);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		//Verschl�sseln
		if (e.getSource() == cipherB) readInput(true);
		
		//Entschl�sseln
		if (e.getSource() == decipherB) readInput(false);
		
		//Angriff auf Caesar
		if (e.getSource() == attackB) {
			readAttack();
			next.setEnabled(true);
			prev.setEnabled(false);
		}
		
		//N�chste Angriffsl�sung
		if (e.getSource() == next)
			if (k < 25) {
				prev.setEnabled(true);
				k++;
				if (k == 25)
					next.setEnabled(false);
				decipher(bar());
			}
		
		//Vorherige Angriffsl�sung
		if (e.getSource() == prev)
			if (k > 0) {
				next.setEnabled(true);
				k--;
				if (k == 0)
					prev.setEnabled(false);
				decipher(bar());
			}
		
		//Alles zur�cksetzen	
		if (e.getSource() == resetAll) {
			b.setText("");
			kryptT.setText("");
			enkryptT.setText("");
			next.setEnabled(false);
			prev.setEnabled(false);
		}
		
		//Datei-Dialog �ffnen
		if (e.getSource() == openButton) {
			int returnVal = fc.showOpenDialog(CaView.this);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();

				try {
					InputStreamReader inputStream = new InputStreamReader(
							new FileInputStream(file));
					char[] charInputArray = new char[(int) file.length()];

					inputStream.read(charInputArray);
					StringBuffer sb = new StringBuffer();
					for (int i = 0; i < file.length(); i++)
						sb.append((char) charInputArray[i]);

					if (left.isSelected())
						plainT.setText(sb.toString());
					else
						cipherT.setText(sb.toString());

					inputStream.close();

				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
	}
*/
	@Override
	public void update(Observable o, Object arg) {

	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}
