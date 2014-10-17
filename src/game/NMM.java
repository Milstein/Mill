package game;

import java.awt.EventQueue;

import javax.swing.SwingUtilities;

/**
 * The main class that set up the frame for the gui.
 * 
 * @author Jimmy Wang
 * @version September 17, 2014
 *
 */

public class NMM {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				try {
					NMMPanel panel = new NMMPanel();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}