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

import data.Item;

public class ItemStorage {
	private List<Item> itemList;

	public ItemStorage() {
		super();

		loadDefaultItems();
	}

	private void loadDefaultItems() {
		itemList = new ArrayList<Item>();
		itemList.add(new Item("1", "Cola", 120, 10));
		itemList.add(new Item("2", "Fanta", 130, 15));
		itemList.add(new Item("3", "Sprite", 140, 5));
	}

	public List<Item> getItemList() {
		return itemList;
	}

	public boolean hasItemWithNumCode(String numCode) {
		boolean isValid = false;

		for (Item item : this.getItemList()) {
			if (numCode.equals(item.getNumCode())) {
				isValid = true;
			}
		}

		return isValid;
	}

	public Item getItemByNumCode(String numCode) {
		Item foundItem = null;

		for (Item item : this.getItemList()) {
			if (item.getNumCode().equals(numCode)) {
				foundItem = item;
			}
		}
		return foundItem;
	}

	public boolean checkIfItemIsAvailable(String numCode) {
		boolean isAvailable = false;
		Item item = getItemByNumCode(numCode);

		if (item != null && item.getQuantity() > 0) {
			isAvailable = true;
		}

		return isAvailable;
	}

	public void writeToFile(String filename) {
		BufferedWriter writer = null;

		try {
			File logFile = new File(filename);

			System.out.println("writing to " + logFile.getCanonicalPath());

			writer = new BufferedWriter(new FileWriter(logFile));

			for (int i = 0; i < getItemList().size(); i++) {
				Item item = getItemList().get(i);

				writer.write(item.getQuantity() + "\n");
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

					itemList.get(i).setQuantity(Integer.valueOf(line));
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
