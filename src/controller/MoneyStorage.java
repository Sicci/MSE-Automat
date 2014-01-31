package controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import data.Money;
import data.MoneyType;

public class MoneyStorage {
	List<Money> moneyList;
	List<String> cards;

	public MoneyStorage() {
		super();

		loadDefaultMoney();
	}

	private void loadDefaultMoney() {
		moneyList = new ArrayList<Money>();
		moneyList.add(new Money(10, 110, MoneyType.COIN));
		moneyList.add(new Money(20, 10, MoneyType.COIN));
		moneyList.add(new Money(50, 10, MoneyType.COIN));
		moneyList.add(new Money(100, 0, MoneyType.COIN));
		moneyList.add(new Money(200, 0, MoneyType.COIN));

		moneyList.add(new Money(500, 0, MoneyType.NOTE));
		moneyList.add(new Money(1000, 0, MoneyType.NOTE));

		cards = new ArrayList<String>();
		cards.add("Visa");
		cards.add("MasterCard");
	}

	public List<Money> getMoneyList() {
		return moneyList;
	}

	public void writeToFile(String filename) {
		BufferedWriter writer = null;

		try {
			File logFile = new File(filename);

			// System.out.println("writing to " + logFile.getCanonicalPath());

			writer = new BufferedWriter(new FileWriter(logFile));

			for (int i = 0; i < getMoneyList().size(); i++) {
				Money m = getMoneyList().get(i);

				writer.write(m.getQuantity() + "\n");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public int size() {
		return moneyList.size();
	}

	public Money get(int i) {
		return moneyList.get(i);
	}

	public boolean addToStorage(int value) {
		boolean hasChanged = false;

		if (value > 0) {
			for (Money m : getMoneyList()) {
				if (m.getValue() == value) {
					m.setQuantity(m.getQuantity() + 1);
					hasChanged = true;
				}
			}
		}

		return hasChanged;
	}

	public void readFromFile(String filename) {
		BufferedReader reader = null;

		if (!new File(filename).exists()) {
			return;
		}

		try {
			reader = new BufferedReader(new FileReader(filename));

			try {
				String line = null;
				int i = 0;

				while ((line = reader.readLine()) != null) {
					// System.out.println(line);

					moneyList.get(i).setQuantity(Integer.valueOf(line));
					i++;
				}
			} catch (IOException e) {
				loadDefaultMoney();
			} catch (IndexOutOfBoundsException e) {
				loadDefaultMoney();
			}
		} catch (FileNotFoundException e) {
			loadDefaultMoney();
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	public boolean isAccpetedValue(int value) {
		boolean isAccepted = false;

		for (Money i : moneyList) {
			if (i.getValue() == value) {
				isAccepted = true;
			}
		}

		return isAccepted;
	}

	private String[] getAcceptedByType(MoneyType t) {
		List<String> l = new ArrayList<String>();

		for (Money i : moneyList) {
			if (i.getType() == t) {
				l.add("" + i.getValue());
			}
		}

		return l.toArray(new String[l.size()]);
	}

	public String[] getAcceptedCoins() {
		return getAcceptedByType(MoneyType.COIN);
	}

	public String[] getAcceptedNotes() {
		return getAcceptedByType(MoneyType.NOTE);
	}

	public String[] getAcceptedCards() {
		return cards.toArray(new String[cards.size()]);
	}
}
