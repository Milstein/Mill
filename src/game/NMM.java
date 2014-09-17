package game;

import java.awt.Dimension;

import javax.swing.JFrame;

/**
 * The main class that set up the frame for the gui.
 * 
 * @author Jimmy Wang
 * @version September 17, 2014
 *
 */

public class NMM {

	public static void main(String[] args) {
		// Set up the frame and add the panel.
		JFrame frame = new JFrame("9 Men's Morris");
		frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);		
		NMMPanel panel = new NMMPanel();
		
		frame.getContentPane().add(panel);
		frame.setSize(new Dimension(650,650));
		frame.setMinimumSize(new Dimension(650,650));
		frame.setVisible(true);
	}

}
