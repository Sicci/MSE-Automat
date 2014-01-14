import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
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

import data.Item;
import data.Money;

public class AutomatView extends JPanel implements ActionListener, Observer {

	private static final long serialVersionUID = 1L;

	private AutomatModel model;
	
	private JTextArea itemList, automatInformation;
	
	
	private JTextField displayStatus, displayMoney;
	private ArrayList<NumpadButton> numpadBtns = new ArrayList<NumpadButton>();
	private ArrayList<MoneyButton> moneyBtns = new ArrayList<MoneyButton>();
	private JButton del, enter, moneyCard;	
	
	public AutomatView(final AutomatModel model) {
		this.model = model;
		JScrollPane scrollpane = null;
		String currency = "€";
			
		
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
		
		displayStatus = new JTextField("");
		displayStatus.setEditable(true);
		
		displayMoney = new JTextField("");
		displayMoney.setEditable(true);
		
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
					
		for (int i = 0; i <= 9; i++) {
			numpadBtns.add(new NumpadButton(i));
		}

		del = new JButton("c");
		enter = new JButton("enter");
				
		
		for (Money m : model.getMoneyStorage()) {
			final MoneyButton _btn = new MoneyButton(m.getValue());
			_btn.setText(""+_btn.getValue()+currency);
			_btn.setEnabled(false);
			moneyBtns.add(_btn);//TODO +Currency
			
			_btn.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					double value = _btn.getValue();
					model.reduceCurrentCosts(value);
					displayMoney.setText(""+model.getCurrentCosts());
					model.getCurrentMoney().add(value);
					if (model.checkIfCurrentMoneyIsEnough()) { //TODO auslagern
						model.returnExchangeMoney();
						Item returnedItem = model.returnItem();
						model.resetAutomat();
						resetGUI();
						displayStatus.setText(returnedItem.getName() + " wird ausgeworfen.");
					

					}
				}

			});
		}
		
		
		
		moneyCard = new JButton("Karte");
		moneyCard.setEnabled(false);
		
		
		for(final NumpadButton btn: numpadBtns) {
			numpad.add(btn);
			btn.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					String current = model.getCurrentNumCode();
					current += btn.getValue();
					model.setCurrentNumCode(current);
					displayStatus.setText(current);
				
				}
			});
		}
		
		del.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				//model changes?
				//werf eingeworfenes geld aus
				System.out.println("Eingegebenes Geld wird ausgeworfen.");
				model.resetAutomat();
				resetGUI();
			}
		});
		
		enter.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				System.out.println(model.getCurrentNumCode());
				if(model.checkIfInputIsValid()) {
					model.setCurrentItem(model.getItemByNumCode(model.getCurrentNumCode()));
					if(model.checkIfItemIsAvailable()) {
						
						
						for (NumpadButton m : numpadBtns) { 
							m.setEnabled(false);
						}
						
						displayMoney.setText(""+model.getCurrentItem().getPrice());
						displayStatus.setText("Bitte Geld einwerfen für "+model.getCurrentItem().getName());
						for (MoneyButton m : moneyBtns) {
							m.setEnabled(true);
						}
					}
				}
				
				
				
				//do magic
				//statusDisplay.getText() auf fehler/länge überprüfen
				//success: an automaten übergeben, refresh money display
				//check money: quantity of item --  (refresh money)
				//ausgabe des items im statusdisplay
				//enable money buttons after press enter
				//reset des moneydisplays nach erfolgreicher transaktion
			}
		});
		
		numpad.add(del);
		numpad.add(enter);
		

		for(final MoneyButton btn: moneyBtns) {
			moneypad.add(btn);
			btn.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					
				}
			});
		}
		moneypad.add(moneyCard);
		
		
		
		box1.add(itemList);
		
		box2.add(displayStatus);
		box2.add(Box.createRigidArea(new Dimension(0,5)));
		box2.add(numpad);
		box2.add(Box.createRigidArea(new Dimension(0,5)));
		box2.add(automatInformation);
		
		box3.add(displayMoney);
		box3.add(Box.createRigidArea(new Dimension(0,5)));
		box3.add(moneypad);
		box3.add(Box.createRigidArea(new Dimension(0,150)));
		
		flowPanel.add(box1);
		flowPanel.add(Box.createRigidArea(new Dimension(5,0)));
		flowPanel.add(box2);
		flowPanel.add(Box.createRigidArea(new Dimension(5,0)));
		flowPanel.add(box3);
		add(flowPanel);
		
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
	
	public final void resetGUI() {
		displayStatus.setText("");
		displayMoney.setText("");
		
		for (MoneyButton m : moneyBtns) { 
			m.setEnabled(false);
		}
		
		for (NumpadButton m : numpadBtns) { 
			m.setEnabled(true);
		}
	}
}
