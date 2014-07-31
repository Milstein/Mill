package nineMensMorris;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.*;

public class NineMensMorris {

	private static final long serialVersionUID = 1L;
	
	protected static JFrame frame;
	private static Game game;
	private static JLabel bgLabel;
	
	private static void initGUI() {
        //Create and set up the window.
        frame = new JFrame("Nine Men's Morris");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        //Menu Bar
        JMenuBar menuBar = new JMenuBar();
        menuBar.setOpaque(true);
        menuBar.setBackground(new Color(100, 100, 100));
        menuBar.setPreferredSize(new Dimension(600, 20));
        
        game = new NineGame();
        frame.setContentPane(game.getDisplay());
        
        bgLabel = new JLabel();
		bgLabel.setOpaque(true);
		bgLabel.setBackground(new Color(0, 0, 0));
		bgLabel.setPreferredSize(new Dimension(600, 600));
		frame.add(bgLabel);
		frame.setPreferredSize(new Dimension(630, 670));
		
        //Add components
        frame.setJMenuBar(menuBar);
        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }
	
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                initGUI();
            }
        });
    }

}
