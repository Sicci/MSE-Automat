package AutomatSimulator;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import AutomatSimulator.Lib.StylePanel;



public class LogView extends StylePanel {
	private static final long serialVersionUID = 1L;
	private JTextArea taLogPanelTextArea;

	public LogView() {
		super(new Color(238, 238, 238), Color.BLACK, new EmptyBorder(10, 10, 10, 10));
		setPreferredSize(new Dimension(100,100));
		
		JLabel namePanel;
		JScrollPane spPanel;
		
		gridStyle.setGrid(1.0,0.0,0,0);
		namePanel = new JLabel("LogPanel:");
	    namePanel.setBackground(Color.BLUE);
	    add(namePanel, gridStyle);
	    
	    gridStyle.setPadding(0,0,10,10);
	    gridStyle.setMargin(10,10,0,0);
		gridStyle.setGrid(1.0,1.0,0,1);
		
		taLogPanelTextArea = new JTextArea();
		spPanel = new JScrollPane(taLogPanelTextArea);
		spPanel.setBackground(Color.WHITE);
		spPanel.setBorder(compound);
	    add(spPanel, gridStyle);
	}
	
	public JTextArea getLogPanelTextArea() {
		return taLogPanelTextArea;
	}
}