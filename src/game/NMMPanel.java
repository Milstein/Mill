package game;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Jimmy Wang
 * @version September 17, 2014
 */
public class NMMPanel extends JPanel{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -27918399736831981L;
	
	private JPanel buttonPanel, statePanel, pilePanel, chessBoardPanel; // main panels
	private JPanel user1Panel, user2Panel; // sub panels for user1 and user2
	private JLabel chessBoardLabel, stateLabel; 
	private JLabel user1nameLabel, user2nameLabel; // name label
	private JLabel user1pileLabel, user2pileLabel; // pile label
	private JButton newGameButton, undoButton, makeAMoveButton;
	private JTextField name1TextField, name2TextField;
	
	/**
	 * Constructor: components and variables setup.
	 */
	public NMMPanel ()
	{
		setLayout (new BoxLayout (this, BoxLayout.Y_AXIS));
		
		// A panel for the buttons.
		buttonPanel = new JPanel();
		newGameButton = new JButton("New");
		undoButton = new JButton("Undo");
		makeAMoveButton = new JButton("Move");
		buttonPanel.add(newGameButton);
		buttonPanel.add(undoButton);
		buttonPanel.add(makeAMoveButton);
		
			// Add listeners to buttons.
			ButtonListener listenToButton = new ButtonListener();
			newGameButton.addActionListener(listenToButton);
			undoButton.addActionListener(listenToButton);
			makeAMoveButton.addActionListener(listenToButton);
		
		// A panel for the state
		statePanel = new JPanel();
		stateLabel = new JLabel("State"); //TODO
		statePanel.add(stateLabel);
		
		// A panel for the piles
		pilePanel = new JPanel();
		pilePanel.setLayout(new BoxLayout(pilePanel, BoxLayout.X_AXIS));
		// subpanel for user1 and user2
		user1Panel = new JPanel();
		user2Panel = new JPanel();
		user1nameLabel = new JLabel("==============Player1==============");
		user2nameLabel = new JLabel("==============Player2==============");
		user1pileLabel = new JLabel("==============9 to go==============");
		user2pileLabel = new JLabel("==============9 to go==============");
		
		user1Panel.setLayout(new BoxLayout(user1Panel, BoxLayout.Y_AXIS));
		user2Panel.setLayout(new BoxLayout(user2Panel, BoxLayout.Y_AXIS));
		user1Panel.add(user1nameLabel);
		user1Panel.add(user1pileLabel);
		user2Panel.add(user2nameLabel);
		user2Panel.add(user2pileLabel);
		pilePanel.add(user1Panel);
		pilePanel.add(user2Panel);
		
		chessBoardPanel = new JPanel();
		chessBoardLabel = new JLabel (new ImageIcon("chessboard.png")); //TODO make image resizable
		chessBoardPanel.add(chessBoardLabel);
		

		
		add(buttonPanel);
		add(statePanel);
		add(pilePanel);
		add(chessBoardPanel);
		//add(buttonPanel);
		
	}
	
	/**
	 * The TextListener class that react against the textField event.
	 * 
	 * @author Jimmy Wang
	 * @version September 17, 2014
	 */
	private class TextListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	/**
	 * The Button class that react against the JButton events.
	 * 
	 * @author Zhenyu Wang
	 * @version April 13, 2012
	 *
	 */
	private class ButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
}
