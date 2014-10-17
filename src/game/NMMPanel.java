package game;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
import javax.swing.SwingConstants;
import javax.swing.event.MouseInputAdapter;

/**
 * @author Jimmy Wang
 * @version September 17, 2014
 */
public class NMMPanel {

	private JFrame frame;
	private int appWidth = 728;
	private int appHeigth = 828;

	private JPanel topButtonPanel, topLeftPanel, topRightPanel; // main
	private JLayeredPane centerPanel;
	private JTextArea txtLogArea;
	private JButton btnNewButton, btnQuitButton;

	private JPanel user1Panel, user2Panel; // sub panels for user1 and user2
	private JLabel chessBoardLabel, stateLabel;

	private JTextField name1TextField, name2TextField;
	private JDialog newGameDialog;
	private boolean turnOfStarter = true;

	private JLabel[] blacks = null;
	private JLabel[] whites = null;

	private Node[] nodes = new Node[24];

	/**
	 * Constructor: components and variables setup.
	 */
	public NMMPanel() {
		initializeWindow();
		initializeGameField();
	}

	private void initializeWindow() {
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds((dim.width / 2 - (appWidth / 2)),
				(dim.height / 2 - (appHeigth / 2)), appWidth, appHeigth);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		frame.setTitle("9MM - Nine Men's Morris");

		topButtonPanel = new JPanel();
		topButtonPanel.setBounds(6, 0, 710, 36);
		btnNewButton = new JButton("New");
		btnQuitButton = new JButton("Quit");
		topButtonPanel.add(btnNewButton);
		topButtonPanel.add(btnQuitButton);
		frame.getContentPane().add(topButtonPanel);

		// Add listeners to buttons.
		ButtonListener listenToButton = new ButtonListener();
		btnNewButton.addActionListener(listenToButton);
		btnQuitButton.addActionListener(listenToButton);		
		
		topLeftPanel = new JPanel();
		topLeftPanel.setForeground(new Color(0, 0, 0));
		topLeftPanel.setBackground(new Color(255, 255, 204));
		topLeftPanel.setBounds(6, 35, 352, 100);
		frame.getContentPane().add(topLeftPanel);
		topLeftPanel.setLayout(null);
		
		JLabel lblPlayer1 = new JLabel("PLAYER 1");
		lblPlayer1.setBounds(150, 80, 61, 16);
		topLeftPanel.add(lblPlayer1);
		
		topRightPanel = new JPanel();
		topRightPanel.setBackground(Color.DARK_GRAY);
		topRightPanel.setBounds(364, 35, 350, 100);
		frame.getContentPane().add(topRightPanel);
		topRightPanel.setLayout(null);
		
		JLabel lblPlayer2 = new JLabel("PLAYER 2");
		lblPlayer2.setForeground(new Color(255, 255, 255));
		lblPlayer2.setBounds(150, 80, 61, 16);
		topRightPanel.add(lblPlayer2);	
				
		centerPanel = new JLayeredPane();
		centerPanel.setBackground(Color.ORANGE);
		centerPanel.setBounds(118, 140, 500, 500);
		frame.getContentPane().add(centerPanel);
		centerPanel.setLayout(null);

		ImageIcon field = createImageIcon("/resources/Spielfeld_roundedCorners.png");
		JLabel feld = new JLabel(field);
		feld.setBounds(0, 0, 500, 500);
		centerPanel.add(feld, 2);

		ImageIcon texture = createImageIcon("/resources/Wood.jpg");
		JLabel textureCenter = new JLabel(texture);
		textureCenter.setBounds(0, 0, 500, 500);
		centerPanel.add(textureCenter, 3);

		setupEventFields(24);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(6, 645, 710, 149);
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

	private void setupEventFields(int counter) {
		initNodes();
		for (int i = 0; i < counter; i++) {
			final JLabel interactionFields = new JLabel("FFFF");
			interactionFields.setHorizontalAlignment(SwingConstants.CENTER);
			interactionFields.setBounds(nodes[i].location.x,
					nodes[i].location.y, 50, 50);
			interactionFields.setCursor(Cursor
					.getPredefinedCursor(Cursor.HAND_CURSOR));

			// TODO : Remove later
			interactionFields.setForeground(new Color(255, 255, 255));

			interactionFields.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseEntered(MouseEvent e) {
					System.out.println("entered here");
					System.out.println(interactionFields.getLocation());
				}

				@Override
				public void mouseClicked(MouseEvent e) {
					System.out.println("Clicked here");

				}

			});
			centerPanel.add(interactionFields, 0);
		}
	}

	private void initNodes() {
		int x = 10;
		int y = 10;
		nodes[0] = new Node("O_0", x, y);

		x = 225;
		y = 10;
		nodes[1] = new Node("O_1", x, y);

		x = 440;
		y = 10;
		nodes[2] = new Node("O_2", x, y);

		x = 74;
		y = 74;
		nodes[3] = new Node("M_0", x, y);

		x = 225;
		y = 72;
		nodes[4] = new Node("M_1", x, y);

		x = 376;
		y = 74;
		nodes[5] = new Node("M_2", x, y);

		x = 137;
		y = 135;
		nodes[6] = new Node("I_0", x, y);

		x = 225;
		y = 135;
		nodes[7] = new Node("I_1", x, y);

		x = 314;
		y = 136;
		nodes[8] = new Node("I_2", x, y);

		x = 10;
		y = 225;
		nodes[9] = new Node("O_3", x, y);

		x = 72;
		y = 225;
		nodes[10] = new Node("M_3", x, y);

		x = 135;
		y = 225;
		nodes[11] = new Node("I_3", x, y);

		x = 316;
		y = 225;
		nodes[12] = new Node("I_4", x, y);

		x = 378;
		y = 225;
		nodes[13] = new Node("M_4", x, y);

		x = 440;
		y = 225;
		nodes[14] = new Node("O_4", x, y);

		x = 137;
		y = 313;
		nodes[15] = new Node("I_5", x, y);

		x = 225;
		y = 316;
		nodes[16] = new Node("I_6", x, y);

		x = 314;
		y = 314;
		nodes[17] = new Node("I_7", x, y);

		x = 74;
		y = 375;
		nodes[18] = new Node("M_5", x, y);

		x = 225;
		y = 377;
		nodes[19] = new Node("M_6", x, y);

		x = 376;
		y = 376;
		nodes[20] = new Node("M_7", x, y);

		x = 10;
		y = 440;
		nodes[21] = new Node("O_5", x, y);

		x = 225;
		y = 440;
		nodes[22] = new Node("O_6", x, y);

		x = 440;
		y = 440;
		nodes[23] = new Node("O_7", x, y);
	}

	protected Node getNode(Point location) {
		for (int i = 0; i < nodes.length; i++) {
			if ((nodes[i].location.x == location.x)
					&& (nodes[i].location.y == location.y)) {
				return nodes[i];
			}
		}
		return null;
	}

	/** Returns an ImageIcon, or null if the path was invalid. */
	protected ImageIcon createImageIcon(String path) {
		URL imgURL = getClass().getResource(path);
		if (imgURL != null) {
			return new ImageIcon(imgURL);
		} else {
			System.err.println("Couldn't find file: " + path);
			return null;
		}
	}

	private void initializeGameField() {
		ImageIcon iconWhite = createImageIcon("/resources/White_Stone.png");
		int space = 10;
		blacks = new JLabel[9];
		whites = new JLabel[9];	

		for (int i = 0; i < 9; i++) {
			JLabel lblWhite = new JLabel();
			lblWhite.setIcon(iconWhite);
			lblWhite.setBounds(space, 25, 50, 50);
			topLeftPanel.add(lblWhite);
			space += 35;
			whites[i] = lblWhite;
		}

		ImageIcon iconBlack = createImageIcon("/resources/Black_Stone.png");

		space = 10;
		for (int j = 0; j < 9; j++) {
			JLabel lblBlack = new JLabel();			
			lblBlack.setIcon(iconBlack);
			lblBlack.setBounds(space, 25, 50, 50);
			topRightPanel.add(lblBlack);					
			space += 35;
			blacks[j] = lblBlack;
		}	
		
	}

	public void doSomething(Point point) {

		System.out.println(point.x + ", " + point.y);
		// if (whitesTurn) {
		// JLabel lblWhite = getLabel(point);
		//
		// if (deleteFlag && whitesTurn) {
		// if (getNode(point).getIsBusy() == 1) {
		// // System.out.println("delete Stein");
		// deleteFlag = false;
		// getNode(lblWhite.getBounds().getLocation()).setIsBusy(0);
		//
		// lblWhite.setVisible(false);
		// lblWhite.setLocation(0, 0);
		// for (Point p : placedPieces) {
		// if (p.x == lblWhite.getBounds().x
		// && p.y == lblWhite.getBounds().y) {
		// placedPieces.remove(p);
		// break;
		// }
		// }
		// if (setting.getPlayer2().contains("Computer")) {
		// if (countPieces(false) <= 3 && placedCounter > 17) {
		// System.out.println(countPieces(false));
		// brain.jumpStone(nodes, 2);
		// } else {
		// if (placedCounter > 17) {
		// System.out.println(countPieces(false));
		// brain.moveStone(nodes, 2);
		// }
		// }
		// }
		// }
		// } else {
		// if (placedCounter == 18) {
		// if (whitesTurn) {
		// selectedPiece = lblWhite;
		// }
		// }
		// }
		// } else {
		// JLabel lblBlack = getLabel(point);
		// ;
		// if (deleteFlag && blacksTurn) {
		// if (getNode(point).getIsBusy() == 2) {
		// System.out.println("delete Stein");
		// deleteFlag = false;
		// getNode(lblBlack.getBounds().getLocation()).setIsBusy(0);
		//
		// lblBlack.setVisible(false);
		// for (Point p : placedPieces) {
		// if (p.x == lblBlack.getBounds().x
		// && p.y == lblBlack.getBounds().y) {
		// placedPieces.remove(p);
		// break;
		// }
		// }
		// if (setting.getPlayer1().contains("Computer")) {
		// if (countPieces(false) <= 3 && placedCounter > 17) {
		// System.out.println(countPieces(false));
		// brain.jumpStone(nodes, 1);
		// } else {
		// System.out.println(countPieces(false));
		//
		// brain.moveStone(nodes, 1);
		// }
		// }
		// }
		//
		// } else {
		// if (placedCounter <= 18) {
		// if (blacksTurn) {
		// selectedPiece = lblBlack;
		// }
		// }
		// }
		// }
	}

	/**
	 * The Button class that react against the JButton events.
	 */
	private class ButtonListener implements ActionListener {

		public void actionPerformed(ActionEvent event) {
			if (event.getSource() == btnNewButton)
				startNewGame();
			if (event.getSource() == btnQuitButton)
				System.exit(0);
		}

	}

	private void startNewGame() {
		JPanel newGameDialogPanel = new JPanel(); // for starting a new game
		JRadioButton radiobutton1 = new JRadioButton("1P");
		JRadioButton radiobutton2 = new JRadioButton("2P");
		newGameDialogPanel.add(radiobutton1);
		newGameDialogPanel.add(radiobutton2);
		JOptionPane.showInputDialog(newGameDialogPanel);
	}

	private class MyListener extends MouseInputAdapter {
		public void mousePressed(MouseEvent e) {
			System.out.println("Mouse Pressed!");
			// int x = e.getX();
			// int y = e.getY();
			// currentRect = new Rectangle(x, y, 0, 0);
			// updateDrawableRect(getWidth(), getHeight());
			// repaint();
		}

		public void mouseDragged(MouseEvent e) {
			System.out.println("Mouse Draged!");
			updateSize(e);
		}

		public void mouseReleased(MouseEvent e) {
			System.out.println("Mouse Released!");
			updateSize(e);
		}

		/*
		 * Update the size of the current rectangle and call repaint. Because
		 * currentRect always has the same origin, translate it if the width or
		 * height is negative.
		 * 
		 * For efficiency (though that isn't an issue for this program), specify
		 * the painting region using arguments to the repaint() call.
		 */
		void updateSize(MouseEvent e) {
			// int x = e.getX();
			// int y = e.getY();
			// currentRect.setSize(x - currentRect.x, y - currentRect.y);
			// updateDrawableRect(getWidth(), getHeight());
			// Rectangle totalRepaint = rectToDraw.union(previousRectDrawn);
			// repaint(totalRepaint.x, totalRepaint.y, totalRepaint.width,
			// totalRepaint.height);
		}
	}
}