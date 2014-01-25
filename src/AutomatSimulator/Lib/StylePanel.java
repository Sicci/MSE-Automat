package AutomatSimulator.Lib;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import AutomatSimulator.AutomatView;



abstract public class StylePanel extends JPanel {
	
	protected ExtendedGridBagConstraints gridStyle;
	protected CompoundBorder compound;
	protected Color lineColor;
	
	private static final long serialVersionUID = 1L;
	
	public StylePanel(Color bgColor, Color lineColor, EmptyBorder margin){
		super();
		initStyle(bgColor, lineColor, margin);
	}
	
	public StylePanel() {
		super();
		initStyle(AutomatView.COLOR, AutomatView.COLOR, new EmptyBorder(0, 0, 0, 0));
	}
	
	protected void initStyle(Color bgColor, Color lineColor, EmptyBorder margin) {
		gridStyle = new ExtendedGridBagConstraints();
		gridStyle.fill = GridBagConstraints.BOTH;
		setLayout(new GridBagLayout());
		setBackground(bgColor);
		
		Border line = new LineBorder(lineColor);
		this.lineColor = lineColor;
		
		compound = new CompoundBorder(line, margin);
		setBorder(compound);
	}
	
	public class ExtendedGridBagConstraints extends GridBagConstraints {
		private static final long serialVersionUID = 1L;

		public void setGrid(double weightx, double weighty, int gridx, int gridy) {
			this.weightx = weightx;
			this.weighty = weighty;
			this.gridx = gridx;
			this.gridy = gridy;
		}
		
		public void setPadding(int top, int left, int bottom, int right) {
			insets = new Insets(top, left, bottom, right);
		}
		
		public void setMargin(int top, int left, int bottom, int right) {
			compound = new CompoundBorder(new LineBorder(lineColor), new EmptyBorder(top, left, bottom, right));
			setBorder(compound);
		}
	}
}
