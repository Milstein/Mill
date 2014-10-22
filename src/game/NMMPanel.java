package game;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.TransferHandler;

/**
 * @author Milson Munakami
 * @version September 17, 2014
 */
public class NMMPanel extends JPanel {

	private JFrame frame;
	private int appWidth = 735;
	private int appHeigth = 830;

	private JPanel topButtonPanel, topLeftPanel, topRightPanel; // main
	private JLayeredPane centerPanel;
	private JTextArea txtLogArea;
	private JButton btnNewButton, btnQuitButton;
	private boolean turnOfStarter = true;

	private JLabel[] blacks = null;
	private JLabel[] whites = null;

	private Node[] nodes = new Node[24];

	MouseListener listener = new DragMouseAdapter();

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
		// frame.setResizable(false);
		frame.setBounds((dim.width / 2 - (appWidth / 2)),
				(dim.height / 2 - (appHeigth / 2)), appWidth, appHeigth);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		frame.setTitle("9MM - Nine Men's Morris");

		// Display the window.
		// frame.pack();
		frame.setVisible(true);

		topButtonPanel = new JPanel();
		topButtonPanel.setBounds(6, 0, 710, 36);
		btnNewButton = new JButton("New");
		btnQuitButton = new JButton("Quit");
		topButtonPanel.add(btnNewButton);
		topButtonPanel.add(btnQuitButton);
		frame.getContentPane().add(topButtonPanel);

		ButtonListener listenToButton = new ButtonListener();
		btnNewButton.addActionListener(listenToButton);
		btnQuitButton.addActionListener(listenToButton);

		topLeftPanel = new JPanel();
		topLeftPanel.setForeground(new Color(0, 0, 0));
		topLeftPanel.setBackground(new Color(233, 224, 219));
		topLeftPanel.setBounds(6, 35, 352, 100);
		frame.getContentPane().add(topLeftPanel);
		topLeftPanel.setLayout(null);

		topRightPanel = new JPanel();
		topRightPanel.setBackground(Color.DARK_GRAY);
		topRightPanel.setBounds(364, 35, 350, 100);
		frame.getContentPane().add(topRightPanel);
		topRightPanel.setLayout(null);

		centerPanel = new JLayeredPane();
		centerPanel.setBackground(Color.ORANGE);
		centerPanel.setBounds(118, 140, 500, 500);
		frame.getContentPane().add(centerPanel);
		centerPanel.setLayout(null);

		ImageIcon field = createImageIcon("/resources/board.png");
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
			final JLabel interactionFields = new JLabel();
			interactionFields.setHorizontalAlignment(SwingConstants.CENTER);
			interactionFields.setBounds(nodes[i].location.x,
					nodes[i].location.y, 50, 50);
			interactionFields.setCursor(Cursor
					.getPredefinedCursor(Cursor.HAND_CURSOR));

			// TODO : Remove later
			interactionFields.setForeground(new Color(255, 255, 255));

			interactionFields.setTransferHandler(new TransferHandler("icon"));
			interactionFields.addMouseListener(listener);

			centerPanel.add(interactionFields, 0);
		}
	}

	private void initNodes() {
		// Point p = new Point(0, 0);
		// for (int i = 0; i < 18; i++) {
		// placedPieces.add(p);
		// }

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

		// for (int i = 0; i < nodes.length; i++) {
		// nodes[i].setIsBusy(0);
		// }

		// setNeighbours();

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
		java.net.URL imgURL = getClass().getResource(path);
		if (imgURL != null) {
			return new ImageIcon(imgURL);
		} else {
			System.err.println("Couldn't find file: " + path);
			return null;
		}
	}

	private void initializeGameField() {

		int space = 10;
		blacks = new JLabel[9];
		whites = new JLabel[9];

		JLabel lblPlayer1 = new JLabel("PLAYER 1");
		lblPlayer1.setBounds(150, 80, 61, 16);
		topLeftPanel.add(lblPlayer1);

		ImageIcon iconWhite = createImageIcon("/resources/White_Stone.png");
		for (int i = 0; i < 9; i++) {
			final JLabel lblWhite = new JLabel(iconWhite, JLabel.CENTER);
			lblWhite.setTransferHandler(new TransferHandler("icon"));
			lblWhite.addMouseListener(listener);
			lblWhite.setBounds(space, 25, 50, 50);
			topLeftPanel.add(lblWhite);
			space += 35;
			whites[i] = lblWhite;
		}

		JLabel lblPlayer2 = new JLabel("PLAYER 2");
		lblPlayer2.setForeground(new Color(255, 255, 255));
		lblPlayer2.setBounds(150, 80, 61, 16);
		topRightPanel.add(lblPlayer2);

		ImageIcon iconBlack = createImageIcon("/resources/Black_Stone.png");
		space = 10;
		for (int j = 0; j < 9; j++) {
			final JLabel lblBlack = new JLabel(iconBlack);
			lblBlack.setTransferHandler(new TransferHandler("icon"));
			lblBlack.addMouseListener(listener);
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

	class DragMouseAdapter extends MouseAdapter {
		public void mousePressed(MouseEvent e) {
			JComponent c = (JComponent) e.getSource();
			TransferHandler handler = c.getTransferHandler();
			handler.exportAsDrag(c, e, TransferHandler.COPY);
			// c.setLayout(new FlowLayout());
		}
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

}
