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

public class MoneyStorage {
	List<Money> moneyList;

	public MoneyStorage() {
		super();

		loadDefaultItems();
	}

	private void loadDefaultItems() {
		moneyList = new ArrayList<Money>();
		moneyList.add(new Money(10, 110));
		moneyList.add(new Money(20, 10));
		moneyList.add(new Money(50, 10));
		moneyList.add(new Money(100, 0));
		moneyList.add(new Money(200, 0));
		moneyList.add(new Money(500, 0));
		moneyList.add(new Money(1000, 0));
	}

	public List<Money> getMoneyList() {
		return moneyList;
	}

	public void writeToFile(String filename) {
		BufferedWriter writer = null;

		try {
			File logFile = new File(filename);

			System.out.println("writing to " + logFile.getCanonicalPath());

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
					System.out.println(line);

					moneyList.get(i).setQuantity(Integer.valueOf(line));
					i++;
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
}
