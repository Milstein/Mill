package game;

import javax.swing.JFrame;

/**
 * The main class that set up the frame for the gui.
 * 
 * @author Jimmy Wang
 * @version Sep 17 2014
 *
 */

public class NMM {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// Set up the frame and add the panel. Other settings included.
		JFrame frame = new JFrame("9 Men's Morris");
		frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);		
		NMMPanel panel = new NMMPanel();
		
		frame.getContentPane().add(panel);
		frame.pack();
		frame.setVisible(true);
	}

}
