package game;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.SwingUtilities;
import javax.swing.TransferHandler;

/**
 * The main class that set up the frame for the gui.
 * 
 * @author Milson Munakami
 * @version September 17, 2014
 *
 */

public class NMM {
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				try {
					NMMPanel newContentPane = new NMMPanel();
					newContentPane.setOpaque(true); // content panes must be
													// opaque

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}