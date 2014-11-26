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

	/***
	 * To initialize the main GUI game window
	 */
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

	/**
	 * Fix the grid with valid twenty-four intersections or points to move the
	 * Men
	 * 
	 * @param counter
	 */
	private void setupEventFields(int counter) {
		initNodes();
		for (int i = 0; i < counter; i++) {
			final JLabel interactionFields = new JLabel();

			interactionFields.setText(nodes[i].location.x + ", "
					+ nodes[i].location.y);

			interactionFields.setHorizontalAlignment(SwingConstants.CENTER);
			interactionFields.setBounds(nodes[i].location.x,
					nodes[i].location.y, 50, 50);
			interactionFields.setCursor(Cursor
					.getPredefinedCursor(Cursor.HAND_CURSOR));

			// TODO : Remove later
			interactionFields.setForeground(new Color(255, 255, 255));

			interactionFields.setTransferHandler(new TransferHandler("icon"));
			interactionFields.addMouseListener(new MouseListener() {

				@Override
				public void mouseClicked(MouseEvent e) {
					// TODO Auto-generated method stub

				}

				@Override
				public void mouseEntered(MouseEvent e) {
					// TODO Auto-generated method stub

				}

				@Override
				public void mouseExited(MouseEvent e) {
					// TODO Auto-generated method stub

				}

				@Override
				public void mousePressed(MouseEvent e) {
					System.out.println("Mouse Pressed" + e.getX() + ", "
							+ e.getY());

				}

				@Override
				public void mouseReleased(MouseEvent e) {
					System.out.println("Mouse Released" + e.getX() + ", "
							+ e.getY());
					JComponent c = (JComponent) e.getSource();
					TransferHandler handler = c.getTransferHandler();
					handler.exportAsDrag(c, e, TransferHandler.COPY);

				}

			});

			centerPanel.add(interactionFields, 0);
		}
	}

	/**
	 * Initialize the 24 main nodes with fixed co-ordinates
	 */
	private void initNodes() {

		// 0,0
		int x = 10;
		int y = 10;
		nodes[0] = new Node("0_0", x, y);

		// 3,0
		x = 225;
		y = 10;
		nodes[1] = new Node("3_0", x, y);

		// 6,0
		x = 440;
		y = 10;
		nodes[2] = new Node("6_0", x, y);

		// 1,1
		x = 74;
		y = 74;
		nodes[3] = new Node("1_1", x, y);

		// 3,1
		x = 225;
		y = 72;
		nodes[4] = new Node("3_1", x, y);

		x = 376;
		y = 74;
		nodes[5] = new Node("5_1", x, y);

		x = 137;
		y = 135;
		nodes[6] = new Node("2_2", x, y);

		x = 225;
		y = 135;
		nodes[7] = new Node("3_2", x, y);

		x = 314;
		y = 136;
		nodes[8] = new Node("4_2", x, y);

		x = 10;
		y = 225;
		nodes[9] = new Node("0_3", x, y);

		x = 72;
		y = 225;
		nodes[10] = new Node("1_3", x, y);

		x = 135;
		y = 225;
		nodes[11] = new Node("2_3", x, y);

		x = 316;
		y = 225;
		nodes[12] = new Node("4_3", x, y);

		x = 378;
		y = 225;
		nodes[13] = new Node("5_3", x, y);

		x = 440;
		y = 225;
		nodes[14] = new Node("6_3", x, y);

		x = 137;
		y = 313;
		nodes[15] = new Node("2_4", x, y);

		x = 225;
		y = 316;
		nodes[16] = new Node("3_4", x, y);

		x = 314;
		y = 314;
		nodes[17] = new Node("4_4", x, y);

		x = 74;
		y = 375;
		nodes[18] = new Node("1_5", x, y);

		x = 225;
		y = 377;
		nodes[19] = new Node("3_5", x, y);

		x = 376;
		y = 376;
		nodes[20] = new Node("5_5", x, y);

		x = 10;
		y = 440;
		nodes[21] = new Node("0_6", x, y);

		x = 225;
		y = 440;
		nodes[22] = new Node("3_6", x, y);

		x = 440;
		y = 440;
		nodes[23] = new Node("6_6", x, y);
	}

	/*
	 * get node's current location (x,y)
	 */
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

	/**
	 * Initialize the Game board GUI
	 */
	private void initializeGameField() {

		int space = 10;
		blacks = new JLabel[9];
		whites = new JLabel[9];

		JLabel lblPlayer1 = new JLabel("PLAYER 1");
		lblPlayer1.setBounds(150, 80, 61, 16);
		topLeftPanel.add(lblPlayer1);

		ImageIcon iconWhite = createImageIcon("/resources/White_Stone.png");

		// for (int x = 0; x < 9; x++) {
		// final JLabel lblwhites = new JLabel(iconWhite);
		// lblwhites.addMouseListener(new MouseAdapter() {
		// @Override
		// public void mouseClicked(MouseEvent e) {
		// }
		// });

		for (int i = 0; i < 9; i++) {
			final JLabel lblWhite = new JLabel(iconWhite, JLabel.CENTER);
			lblWhite.setTransferHandler(new TransferHandler("icon"));
			lblWhite.addMouseListener(new MouseListener() {
				// @Override
				// public void mouseDragged(MouseEvent e) {
				// if(selected){
				// this.pieceShape = new Ellipse2D.Float(e.getX()-SIZE/2,
				// e.getY()-SIZE/2, SIZE, SIZE);
				// parentBoard.repaint();
				// }
				//
				// }
				@Override
				public void mouseReleased(MouseEvent e) {
					System.out.println("Mouse Released" + e.getX() + ", "
							+ e.getY());
					JComponent c = (JComponent) e.getSource();
					TransferHandler handler = c.getTransferHandler();
					handler.exportAsDrag(c, e, TransferHandler.COPY);

				}

				@Override
				public void mousePressed(MouseEvent e) {
					System.out.println("Mouse Pressed" + e.getX() + ", "
							+ e.getY());
				}

				@Override
				public void mouseExited(MouseEvent e) {
					// System.out.println("Mouse Exited" + e.getX() + ", " +
					// e.getY());
				}

				@Override
				public void mouseEntered(MouseEvent e) {
					// System.out.println("Mouse Entered" + e.getX() + ", " +
					// e.getY());
				}

				@Override
				public void mouseClicked(MouseEvent e) {
					// System.out.println("Mouse Clicked" + e.getX() + ", " +
					// e.getY());
				}
			});
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
			lblBlack.addMouseListener(new MouseListener() {

				@Override
				public void mouseReleased(MouseEvent e) {
					// TODO Auto-generated method stub

				}

				@Override
				public void mousePressed(MouseEvent e) {

				}

				@Override
				public void mouseExited(MouseEvent e) {
					// TODO Auto-generated method stub

				}

				@Override
				public void mouseEntered(MouseEvent e) {
					// TODO Auto-generated method stub

				}

				@Override
				public void mouseClicked(MouseEvent arg0) {
					// TODO Auto-generated method stub

				}
			});
			lblBlack.setBounds(space, 25, 50, 50);
			topRightPanel.add(lblBlack);
			space += 35;
			blacks[j] = lblBlack;
		}
	}

	/**
	 * Do add the drag and drop mouse event listener
	 * 
	 * @author milsonmunakami
	 *
	 */
	class DragMouseAdapter extends MouseAdapter {
		public void mousePressed(MouseEvent e) {
			JComponent c = (JComponent) e.getSource();
			TransferHandler handler = c.getTransferHandler();
			handler.exportAsDrag(c, e, TransferHandler.COPY);
			// c.setLayout(new FlowLayout());
		}
	}

	// private boolean checkPlace(Point location) {
	// boolean check = false;
	// for (Point p : placedPieces) {
	// if ((p.x == location.x)
	// && (p.y == location.y)) {
	// check = true;
	// break;
	// } else {
	// check = false;
	// }
	// }
	// return check;
	// }

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

	/**
	 * To show on popup for new game options
	 */
	private void startNewGame() {
		JPanel newGameDialogPanel = new JPanel(); // for starting a new game
		JRadioButton radiobutton1 = new JRadioButton("2 Players");
		JRadioButton radiobutton2 = new JRadioButton("With Computer");
		newGameDialogPanel.add(radiobutton1);
		newGameDialogPanel.add(radiobutton2);
		JOptionPane.showInputDialog(newGameDialogPanel);
	}

}
