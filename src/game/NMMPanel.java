package game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * @author Jimmy Wang
 * @version September 17, 2014
 */
public class NMMPanel {

	private JFrame frame;
	private int appWidth = 736;
	private int appHeigth = 855;

	private JPanel topButtonPanel, topLeftPanel, topRightPanel, pilePanel,
			chessBoardPanel; // main
	private JPanel leftPanel; // panels
	private JPanel user1Panel, user2Panel; // sub panels for user1 and user2
	private JLabel chessBoardLabel, stateLabel;
	private JLabel user1nameLabel, user2nameLabel; // name label
	private JLabel user1pileLabel, user2pileLabel; // pile label
	private JButton btnRestartButton, btnQuitButton;
	private JTextField name1TextField, name2TextField;
	private JDialog newGameDialog;
	private boolean turnOfStarter = true;
	private JTextArea txtLogArea;
	private JLayeredPane centerPanel;

	/**
	 * Constructor: components and variables setup.
	 */
	public NMMPanel() {
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame = new JFrame();
		frame.setBounds((dim.width / 2 - (appWidth / 2)),
				(dim.height / 2 - (appHeigth / 2)), appWidth, appHeigth);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		frame.setTitle("9MM - Nine Men's Morris");

		// A panel for the buttons.
		topButtonPanel = new JPanel();
		topButtonPanel.setForeground(new Color(0, 0, 0));
		topButtonPanel.setBackground(new Color(255, 255, 204));
		topButtonPanel.setBounds(6, 6, 710, 149);
		btnRestartButton = new JButton("Restart");
		btnQuitButton = new JButton("Quit");
		topButtonPanel.add(btnRestartButton);
		topButtonPanel.add(btnQuitButton);
		//topButtonPanel.setLayout(null);
		frame.getContentPane().add(topButtonPanel);

		// Add listeners to buttons.
		ButtonListener listenToButton = new ButtonListener();
		btnRestartButton.addActionListener(listenToButton);
		btnQuitButton.addActionListener(listenToButton);

		topLeftPanel = new JPanel();
		topLeftPanel.setForeground(new Color(0, 0, 0));
		topLeftPanel.setBackground(new Color(255, 255, 204));
		topLeftPanel.setBounds(6, 35, 350, 100);
		frame.getContentPane().add(topLeftPanel);
		topLeftPanel.setLayout(null);

		JLabel lblPlayer = new JLabel("PLAYER 1");
		lblPlayer.setBounds(20, 454, 61, 16);
		topLeftPanel.add(lblPlayer);

		topRightPanel = new JPanel();
		topRightPanel.setForeground(new Color(0, 0, 0));
		topRightPanel.setBackground(new Color(255, 255, 204));
		topRightPanel.setBounds(360, 35, 350, 100);
		frame.getContentPane().add(topRightPanel);
		topRightPanel.setLayout(null);

		JLabel lblPlayer_1 = new JLabel("PLAYER 2");
		lblPlayer_1.setForeground(new Color(255, 255, 255));
		lblPlayer_1.setBounds(20, 454, 61, 16);
		topRightPanel.add(lblPlayer_1);

		ImageIcon texture = createImageIcon("/resources/Wood.jpg");

		centerPanel = new JLayeredPane();
		centerPanel.setBackground(Color.ORANGE);
		centerPanel.setBounds(118, 160, 500, 500);
		frame.getContentPane().add(centerPanel);
		centerPanel.setLayout(null);
		ImageIcon field = createImageIcon("/resources/Spielfeld_roundedCorners.png");

		JLabel feld = new JLabel(field);
		feld.setBounds(0, 0, 500, 500);
		centerPanel.add(feld, 2);

		JLabel textureCenter = new JLabel(texture);
		textureCenter.setBounds(0, 0, 500, 500);
		centerPanel.add(textureCenter, 3);

		// setupEventFields(24);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(6, 662, 710, 149);
		frame.getContentPane().add(scrollPane);

		txtLogArea = new JTextArea();
		txtLogArea.setEditable(false);
		scrollPane.setViewportView(txtLogArea);

		if (turnOfStarter) {
			txtLogArea.append("Whites turn!\n");
		} else {
			txtLogArea.append("Blacks turn!\n");
		}
		frame.setVisible(true);

	}

	private ImageIcon createImageIcon(String path) {
		URL imgURL = getClass().getResource(path);
		if (imgURL != null) {
			return new ImageIcon(imgURL);
		} else {
			System.err.println("Couldn't find file: " + path);
			return null;
		}
	}

	/**
	 * The TextListener class that react against the textField event.
	 */
	private class TextListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub

		}

	}

	/**
	 * The Button class that react against the JButton events.
	 */
	private class ButtonListener implements ActionListener {

		public void actionPerformed(ActionEvent event) {
			if (event.getSource() == btnRestartButton)
				startNewGame();
			if (event.getSource() == btnQuitButton)
				System.exit(0);
		}

	}

	private void makeAMove() {
		// TODO Auto-generated method stub
	}

	private void undo() {
		// TODO Auto-generated method stub
	}

	private void startNewGame() {
		JPanel newGameDialogPanel = new JPanel(); // for starting a new game
		JRadioButton radiobutton1 = new JRadioButton("1P");
		JRadioButton radiobutton2 = new JRadioButton("2P");
		newGameDialogPanel.add(radiobutton1);
		newGameDialogPanel.add(radiobutton2);
		JOptionPane.showInputDialog(newGameDialogPanel);
	}
}
