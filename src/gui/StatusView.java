package gui;

import gui.lib.StylePanel;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;




public class StatusView extends StylePanel {
	private static final long serialVersionUID = 1L;
	private JTextArea taItemStoragePanelTextArea;
	private JTextArea taMoneyStoragePanelTextArea;
	
	
	public StatusView() {
		super(new Color(238, 238, 238), Color.BLACK, new EmptyBorder(10, 10, 10, 10));
		setPreferredSize(new Dimension(100,100));
		
		JLabel namePanel;
		JScrollPane spPanel;
		JTextArea taContent;
		
		gridStyle.setGrid(1.0,0.0,0,0);
		namePanel = new JLabel("ItemStoragePanel:");
	    add(namePanel, gridStyle);
	    
	    gridStyle.setPadding(0,0,10,10);
	    gridStyle.setMargin(10,10,0,0);
		gridStyle.setGrid(1.0,1.0,0,1);
		
		taItemStoragePanelTextArea = new JTextArea();
		spPanel = new JScrollPane(taItemStoragePanelTextArea);
		spPanel.setBackground(Color.WHITE);
		spPanel.setBorder(compound);
	    add(spPanel, gridStyle);
		
		taContent = new JTextArea();
		taContent.setPreferredSize(new Dimension(1, 1));
		taContent.setBorder(compound);
	    add(taContent, gridStyle);
	    
	    gridStyle.setPadding(0,0,0,0);
		gridStyle.setGrid(1.0,0.0,0,2);
		namePanel = new JLabel("MoneyStoragePanel:");
	    add(namePanel, gridStyle);
	    
	    gridStyle.setPadding(0,0,10,10);
	    gridStyle.setMargin(10,10,0,0);
		gridStyle.setGrid(1.0,1.0,0,3);
		taMoneyStoragePanelTextArea = new JTextArea();
		spPanel = new JScrollPane(taMoneyStoragePanelTextArea);
		spPanel.setBackground(Color.WHITE);
		spPanel.setBorder(compound);
	    add(spPanel, gridStyle);
	}
	
	public JTextArea getMoneyStoragePanelTextArea() {
		return taMoneyStoragePanelTextArea;
	}
	
	public JTextArea getItemStoragePanelTextArea() {
		return taItemStoragePanelTextArea;
	}
}
