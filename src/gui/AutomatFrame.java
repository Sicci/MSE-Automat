package gui;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import controller.Automat;
import controller.exceptions.AutomatException;

public class AutomatFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	public AutomatFrame() {
		super("Automat");
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}

		try {
			Automat model = new Automat();

			AutomatView view = new AutomatView(model);
			add(view);
			setSize(500, 200);
			pack();
			setResizable(false);
		} catch (AutomatException e) {
			JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	public static void main(String[] args) {
		AutomatFrame af = new AutomatFrame();
		af.setVisible(true);
	}
}
