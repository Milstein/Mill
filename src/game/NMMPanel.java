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
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.TransferHandler;

/**
 * @author Milson Munakami
 * @version September 17, 2014
 */
public class NMMPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JFrame frame;
	private int appWidth = 735;
	private int appHeigth = 830;

	private JPanel topButtonPanel, topLeftPanel, topRightPanel; // main
	private JLayeredPane centerPanel;
	private JTextArea txtLogArea;
	private JButton btnNewButton, btnQuitButton;

	private JLabel[] blacks = null;
	private JLabel[] whites = null;

	private Node[] nodes = new Node[24];

	private NMMPanel parentBoard;

	private boolean turnOfStarter = true;
	private ArrayList<Point> placedPieces = new ArrayList<Point>();
	private int placedCounter = 0;
	// private static Splashscreen splash;
	private JLabel selectedPiece;
	private boolean deleteFlag = false;

	private static Settings setting;

	private int whitePointer = 0;
	private int blackPointer = 0;

	private boolean validMove = false;

	private boolean blacksTurn = false;
	private boolean whitesTurn = true;
	private static Splashscreen splash;

	private ArrayList<Node> whiteMills = new ArrayList<Node>();
	private ArrayList<Node> blackMills = new ArrayList<Node>();

	private Board game = null;
	private Player p1 = null;
	private Player p2 = null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

		// EventQueue.invokeLater(new Runnable() {
		// public void run() {
		// try {
		// NMMPanel window = new NMMPanel(setting);
		// window.frame.setVisible(true);
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		// }
		// });

		splash = new Splashscreen();
		splash.setVisible(true);

		// NMMPanel newContentPane = new NMMPanel(setting);
		// newContentPane.setOpaque(true); // content panes must be
	}

	/**
	 * Constructor: components and variables setup.
	 */
	public NMMPanel(Settings setting) {
		game = new Board("Player1", "Player2");
		p1 = game.getPlayer(0);
		p2 = game.getPlayer(1);

		this.setting = setting;
		splash.dispose();
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

			// interactionFields.setText(nodes[i].location.x + ", " +
			// nodes[i].location.y);

			interactionFields.setHorizontalAlignment(SwingConstants.CENTER);
			interactionFields.setBounds(nodes[i].location.x,
					nodes[i].location.y, 50, 50);

			interactionFields.setCursor(Cursor
					.getPredefinedCursor(Cursor.HAND_CURSOR));

			interactionFields.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent arg0) {
					boolean automateAI = true;
					int limitMoves = 0;

					while (automateAI && placedCounter < 18) {
						if (!deleteFlag) {
							if (setting.getPlayer1().contains("Computer")) {
								// TODO: No Need to use this Not Possible to
								// implement
								// makeAIplace(0);
							} else {
								setPieces(interactionFields.getLocation());

								automateAI = false;
							}
						}

						if (!deleteFlag
								&& setting.getPlayer2().contains("Computer")) {
							// brain.setPiece(nodes, 1);
							// A.I.place
							makeAIplace(1);
						}
					}

					while (automateAI && placedCounter >= 18 && limitMoves < 1) {
						limitMoves++;
						if (!deleteFlag) {
							if (setting.getPlayer1().contains("Computer")) {
								if (countPieces(true) <= 3) {
									System.out.println(countPieces(true));

									// brain.jumpPiece(nodes, 0);
									// A.I.fly
									makeAIfly(0);
								} else {
									System.out.println(countPieces(true));

									// brain.movePiece(nodes, 0);
									// A.I.move
									makeAImove(0);
								}
							} else {
								setPieces(interactionFields.getLocation());

								automateAI = false;
							}
						}

						if (!deleteFlag
								&& setting.getPlayer2().contains("Computer")) {
							if (countPieces(false) <= 3) {
								System.out.println(countPieces(false));
								// brain.jumpPiece(nodes, 1);
								// A.I.fly
								makeAIfly(1);
							} else {
								System.out.println(countPieces(false));

								// brain.movePiece(nodes, 1);
								// A.I.move
								makeAImove(1);
							}
						}
					}
				}
			});

			centerPanel.add(interactionFields, 0);
		}
	}

	private int countPieces(boolean whitesTurn) {
		int state = 0;
		int count = 0;
		if (whitesTurn) {
			state = 1;
		} else {
			state = 2;
		}
		for (Node n : nodes) {
			if (n.getIsBusy() == state) {
				count++;
			}
		}
		return count;
	}

	public void setPieces(Point point) {
		if (!game.endOfGame()) {
			Player player = null;
			Player opponent = null;
			int remove;
			if (turnOfStarter) {
				player = p1;
				opponent = p2;
				remove = 1;
				getNode(point).setIsBusy(1);
			} else {
				player = p2;
				opponent = p1;
				remove = 0;
				getNode(point).setIsBusy(2);
			}
			// Test line......................................
			for (PointGame pt : p1.getMenOnTheBoard()) {
				System.out.print(pt);
			}
			System.out.println("");
			for (PointGame pt : p2.getMenOnTheBoard()) {
				System.out.print(pt);
			}
			System.out.println("");
			// --------------------------------------------------
			System.out.println("Player" + getNode(point).getIsBusy()
					+ "'s turn.");

			// Players;
			// if (!validMove) {
			if (player.getMenHoldInHand() > 0) {
//				System.out.println("Player " + getNode(point).getIsBusy()
//						+ " to place a man at point: ");
				int x_coor = getNode(point).getPosition().x;
				int y_coor = getNode(point).getPosition().y;

				PointGame newpt = new PointGame(x_coor, y_coor);
				validMove = game.makeAnAction(null, newpt, getNode(point)
						.getIsBusy() - 1);
				txtLogArea.append(game.getAction() + "\n");

				if (validMove) {
					if (turnOfStarter) {
						whites[whitePointer].setLocation(point);
						centerPanel.add(whites[whitePointer], 0);
						whitePointer++;
						Point p = new Point();
						p.x = point.x;
						p.y = point.y;
						placedPieces.add(p);
						placedCounter++;
						// Check if the new point makes a mill.
						if (game.hasMills(getNode(point).getIsBusy() - 1, newpt)) {
							System.out.println("Player "
									+ getNode(point).getIsBusy()
									+ " has a MILL!");
							System.out.println("Ask Player "
									+ getNode(point).getIsBusy()
									+ ": to remove a man of Player "
									+ (remove + 1));
							deleteFlag = true;
							System.out.println("\nYou can remove one from: ");
							for (PointGame pt : opponent.getMenOnTheBoard()) {
								System.out.print(pt);
							}
							System.out
									.println("\nSelect the Black Man you want to remove while some on hand: ");

							// deletePiece(referee.checkRules(nodes,
							// whitesTurn));
						}
						whitesTurn = false;
						blacksTurn = true;
					} else {
						blacks[blackPointer].setLocation(point);
						centerPanel.add(blacks[blackPointer], 0);
						blackPointer++;
						Point p = new Point();
						p.x = point.x;
						p.y = point.y;
						placedPieces.add(p);
						placedCounter++;
						// Check if the new point makes a mill.
						if (game.hasMills(getNode(point).getIsBusy() - 1, newpt)) {
							System.out.println("Player "
									+ getNode(point).getIsBusy()
									+ " has a MILL!");
							System.out.println("Ask Player "
									+ getNode(point).getIsBusy()
									+ ": to remove a man of Player "
									+ (remove + 1));
							deleteFlag = true;
							System.out.println("\nYou can remove one from: ");
							for (PointGame pt : opponent.getMenOnTheBoard()) {
								System.out.print(pt);
							}
							System.out
									.println("\nSelect the White Man you want to remove while some on hand: ");

							// deletePiece(referee.checkRules(nodes,
							// whitesTurn));
						}
						whitesTurn = true;
						blacksTurn = false;
					}

					if (whitesTurn) {
						txtLogArea.append("Whites turn!\n");
					} else {
						txtLogArea.append("Blacks turn!\n");
					}
					turnOfStarter = !turnOfStarter;
				}
			} else {
				if (player.getMenHoldInHand() == 0) {
					System.out.println("Player" + getNode(point).getIsBusy()
							+ " to move a man: ");
					System.out.println("Available points:");
					for (PointGame pt : player.getMenOnTheBoard()) {
						System.out.print(pt);
					}
					System.out.println("From:");
					int x_1 = 0;
					int y_1 = 0;
					if (selectedPiece != null) {
						Node resetNode = getNode(new Point(
								selectedPiece.getBounds().x,
								selectedPiece.getBounds().y));
						int x = point.x;
						int y = point.y;
						Node n = getNode(new Point(x, y));
						System.out
								.println("------Selected FROM Point was: ------");
						System.out.println(resetNode.getId());

						x_1 = resetNode.getPosition().x;
						y_1 = resetNode.getPosition().y;

						ImageIcon icon = null;
						if (whitesTurn) {
							icon = createImageIcon("/resources/White_Stone.png");
							selectedPiece.setIcon(icon);
						} else {
							icon = createImageIcon("/resources/Black_Stone.png");
							selectedPiece.setIcon(icon);
						}

						System.out.println("To:");
						int x_2 = getNode(point).getPosition().x;
						int y_2 = getNode(point).getPosition().y;
						PointGame newpt = new PointGame(x_2, y_2);
						validMove = game.makeAnAction(new PointGame(x_1, y_1),
								newpt, getNode(point).getIsBusy() - 1);
						if (validMove) {
							txtLogArea.append(game.getAction() + "\n");
							switch (game.getAction()) {
							case "FLY":
								if (n.getIsBusy() == 0) {
									selectedPiece.setBounds(x, y, 50, 50);
									n.setIsBusy(resetNode.getIsBusy());
									resetNode.setIsBusy(0);
									selectedPiece = null;

									// check mills condition here
									// deletePiece(referee.checkRules(nodes,whitesTurn));
								}
								break;
							case "MOVE":
								// if (n.getIsBusy() == 0) {
								selectedPiece.setBounds(x, y, 50, 50);
								n.setIsBusy(resetNode.getIsBusy());
								resetNode.setIsBusy(0);
								selectedPiece = null;

								// check mills condition here
								// deletePiece(referee.checkRules(nodes,whitesTurn));
								// }
								break;
							case "REMOVE":
								System.out.println("Remove me");

							default:
								break;
							}

							if (game.hasMills(getNode(point).getIsBusy() - 1,
									newpt)) {
								System.out.println("Player "
										+ getNode(point).getIsBusy()
										+ " has a MILL!");
								System.out.println("Ask Player "
										+ getNode(point).getIsBusy()
										+ ": to remove a man of Player "
										+ (remove + 1));
								deleteFlag = true;
								System.out.println("You can remove one from: ");
								for (PointGame pt : opponent.getMenOnTheBoard()) {
									System.out.print(pt);
								}
								System.out
										.println("\nSelect the "
												+ getNode(point).getIsBusy()
												+ "Man you want to remove while some NOthing on hand ");
							}

							whitesTurn = !whitesTurn;
							blacksTurn = !blacksTurn;
							if (whitesTurn) {
								txtLogArea.append("Whites turn!\n");
							} else {
								txtLogArea.append("Blacks turn!\n");
							}
							turnOfStarter = !turnOfStarter;

						}
					}
				}
			}
			// }
			// reset.
			validMove = false;
			// if (game.endOfGame())
			// break;
		} else {
			// End of Game
			if (p1.lose()) {
				System.out.println("Black Wins!");
				txtLogArea.append("Black Wins!");
			} else {
				System.out.println("White Wins!");
				txtLogArea.append("White Wins!");
			}
		}

	}

	protected void deletePiece(boolean checkRules) {
		if (checkRules) {
			txtLogArea.append("############## Mill! ############### \n");
			txtLogArea
					.append("##       Select piece to delete then...         ## \n");
			txtLogArea.append("################################# \n");

			deleteFlag = true;
		}
	}

	/**
	 * // a.i. Return an array of points. // Return an array of 3 point: [null,
	 * pt2, null]: Player2 placed a man on pt2, no mills formed. // Return an
	 * array of 3 points: [null, pt2, pt3]: Player2 placed a man on pt2, form a
	 * mill and remove pt3 from Player1.
	 * 
	 * @param player
	 * @return
	 */
	public PointGame[] makeAIplace(int player) {
		return game.makeAIplace(player);
	}

	/**
	 * // a.i. Return an array of points. // Return an array of 3 points: [pt1,
	 * pt2, null]: Player2 moved a man from pt1 to pt2, no mills formed. //
	 * Return an array of 3 points: [null, pt2, pt3]: Player2 moved a man from
	 * pt1 to pt2, form a mill and remove pt3 from Player1.
	 * 
	 * @param player
	 * @return
	 */
	public PointGame[] makeAImove(int player) {
		return game.makeAImove(player);
	}

	/**
	 * // a.i. Return an array of points. // Return an array of 3 points: [pt1,
	 * pt2, null]: Player2 moved a man from pt1 to pt2, no mills formed. //
	 * Return an array of 3 points: [null, pt2, pt3]: Player2 moved a man from
	 * pt1 to pt2, form a mill and remove pt3 from Player1.
	 * 
	 * @param player
	 * @return
	 */
	public PointGame[] makeAIfly(int player) {
		return game.makeAIfly(player);
	}

	/**
	 * Initialize the 24 main nodes with fixed co-ordinates
	 */
	private void initNodes() {

		// 0,0
		int x = 10;
		int y = 10;
		nodes[0] = new Node("0,0", x, y);

		// 3,0
		x = 225;
		y = 10;
		nodes[1] = new Node("3,0", x, y);

		// 6,0
		x = 440;
		y = 10;
		nodes[2] = new Node("6,0", x, y);

		// 1,1
		x = 74;
		y = 74;
		nodes[3] = new Node("1,1", x, y);

		// 3,1
		x = 225;
		y = 72;
		nodes[4] = new Node("3,1", x, y);

		x = 376;
		y = 74;
		nodes[5] = new Node("5,1", x, y);

		x = 137;
		y = 135;
		nodes[6] = new Node("2,2", x, y);

		x = 225;
		y = 135;
		nodes[7] = new Node("3,2", x, y);

		x = 314;
		y = 136;
		nodes[8] = new Node("4,2", x, y);

		x = 10;
		y = 225;
		nodes[9] = new Node("0,3", x, y);

		x = 72;
		y = 225;
		nodes[10] = new Node("1,3", x, y);

		x = 135;
		y = 225;
		nodes[11] = new Node("2,3", x, y);

		x = 316;
		y = 225;
		nodes[12] = new Node("4,3", x, y);

		x = 378;
		y = 225;
		nodes[13] = new Node("5,3", x, y);

		x = 440;
		y = 225;
		nodes[14] = new Node("6,3", x, y);

		x = 137;
		y = 313;
		nodes[15] = new Node("2,4", x, y);

		x = 225;
		y = 316;
		nodes[16] = new Node("3,4", x, y);

		x = 314;
		y = 314;
		nodes[17] = new Node("4,4", x, y);

		x = 74;
		y = 375;
		nodes[18] = new Node("1,5", x, y);

		x = 225;
		y = 377;
		nodes[19] = new Node("3,5", x, y);

		x = 376;
		y = 376;
		nodes[20] = new Node("5,5", x, y);

		x = 10;
		y = 440;
		nodes[21] = new Node("0,6", x, y);

		x = 225;
		y = 440;
		nodes[22] = new Node("3,6", x, y);

		x = 440;
		y = 440;
		nodes[23] = new Node("6,6", x, y);
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
	 * Initialize the Game board GUI for Label and Player Men
	 */
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
			// lblWhite.setTransferHandler(new TransferHandler("icon"));
			lblWhite.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					doSomething(lblWhite.getLocation());
					if (!deleteFlag) {
						if (setting.getPlayer1().contains("Computer")) {
							if (placedCounter <= 18) {
								// brain.setPiece(nodes, 1);
							} else {
								// brain.movePiece(nodes, 1);
								System.out.println("seems to work");
							}
						}
					}
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
			lblBlack.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					doSomething(lblBlack.getLocation());
					if (!deleteFlag) {
						if (setting.getPlayer2().contains("Computer")) {
							if (placedCounter <= 18) {
								// brain.setPiece(nodes, 2);
							} else {
								// brain.movePiece(nodes, 2);
								System.out.println("seems to work");
							}

						}
					}

				}
			});
			lblBlack.setBounds(space, 25, 50, 50);
			topRightPanel.add(lblBlack);
			space += 35;
			blacks[j] = lblBlack;
		}

		setting.printProperties();
		// String path = "";
		// if (setting.getPlayer2().contains("Computer")) {
		// path = "/resources/computer.png";
		// } else {
		// path = "/resources/human.png";
		// }
		//
		// turnOfStarter = setting.isStart();
		//
		// space += 64;
		//
		// ImageIcon iconRight = createImageIcon(path);
		// JLabel lblRight = new JLabel(iconRight);
		// lblRight.setBounds(20, space, 64, 64);
		// topRightPanel.add(lblRight);
		//
		// if (setting.getPlayer1().contains("Computer")) {
		// path = "/resources/computer.png";
		// } else {
		// path = "/resources/human.png";
		// }
		//
		// ImageIcon iconLeft = createImageIcon(path);
		// JLabel lblLeft = new JLabel(iconLeft);
		// lblLeft.setBounds(20, space, 64, 64);
		// topLeftPanel.add(lblLeft);
	}

	public void doSomething(Point point) {
		if (whitesTurn) {
			JLabel lblWhite = getLabel(point);
			if (deleteFlag && whitesTurn) {
				if (getNode(point).getIsBusy() == 1) {
					System.out.println("delete Men");
					deleteFlag = false;
					getNode(lblWhite.getBounds().getLocation()).setIsBusy(0);

					lblWhite.setVisible(false);
					for (Point p : placedPieces) {
						if (p.x == lblWhite.getBounds().x
								&& p.y == lblWhite.getBounds().y) {
							placedPieces.remove(p);

							int x_remove = getNode(lblWhite.getLocation())
									.getPosition().x;
							int y_remove = getNode(lblWhite.getLocation())
									.getPosition().y;
							PointGame pt = new PointGame(x_remove, y_remove);
							if (p1.getMenOnTheBoard().contains(pt)) {
								game.removeAMan(0, pt);
							} else {
								System.err.println("Invalid point to remove!");
							}

							break;
						}
					}
					if (setting.getPlayer2().contains("Computer")) {
						if (countPieces(false) <= 3 && placedCounter > 17) {
							System.out.println(countPieces(false));
							// brain.jumpPiece(nodes, 2);
						} else {
							if (placedCounter > 17) {
								System.out.println(countPieces(false));
								// brain.movePiece(nodes, 2);
							}
						}
					}
				}
			} else {
				if (p1.getMenHoldInHand() == 0) {
					if (whitesTurn) {
						if (selectedPiece != null) {
							ImageIcon iconWhiteSelected = createImageIcon("/resources/White_Stone.png");
							selectedPiece.setIcon(iconWhiteSelected);
						}
						selectedPiece = lblWhite;
						ImageIcon iconWhiteSelected = createImageIcon("/resources/White_Stone_Selected.png");
						selectedPiece.setIcon(iconWhiteSelected);
						selectedPiece.setCursor(Cursor
								.getPredefinedCursor(Cursor.HAND_CURSOR));
					}
				}
			}
		} else {
			JLabel lblBlack = getLabel(point);
			if (deleteFlag && blacksTurn) {
				if (getNode(point).getIsBusy() == 2) {
					System.out.println("delete Men");
					deleteFlag = false;
					getNode(lblBlack.getBounds().getLocation()).setIsBusy(0);

					lblBlack.setVisible(false);
					for (Point p : placedPieces) {
						if (p.x == lblBlack.getBounds().x
								&& p.y == lblBlack.getBounds().y) {
							placedPieces.remove(p);

							int x_remove = getNode(lblBlack.getLocation())
									.getPosition().x;
							int y_remove = getNode(lblBlack.getLocation())
									.getPosition().y;
							PointGame pt = new PointGame(x_remove, y_remove);
							if (p2.getMenOnTheBoard().contains(pt)) {
								game.removeAMan(1, pt);
							} else {
								System.err.println("Invalid point to remove!");
							}

							break;
						}
					}
					if (setting.getPlayer1().contains("Computer")) {
						if (countPieces(false) <= 3 && placedCounter > 17) {
							System.out.println(countPieces(false));
							// brain.jumpPiece(nodes, 1);
						} else {
							System.out.println(countPieces(false));
							// brain.movePiece(nodes, 1);
						}
					}
				}

			} else {
				if (p2.getMenHoldInHand() == 0) {
					if (blacksTurn) {
						if (selectedPiece != null) {
							ImageIcon iconBlackSelected = createImageIcon("/resources/Black_Stone.png");
							selectedPiece.setIcon(iconBlackSelected);
						}
						selectedPiece = lblBlack;
						ImageIcon iconBlackSelected = createImageIcon("/resources/Black_Stone_Selected.png");
						selectedPiece.setIcon(iconBlackSelected);
						selectedPiece.setCursor(Cursor
								.getPredefinedCursor(Cursor.HAND_CURSOR));
					}
				}
			}
		}
	}

	private JLabel getLabel(Point point) {
		JLabel label = null;
		JLabel[] searchLabel;
		if (whitesTurn) {
			searchLabel = whites;
		} else {
			searchLabel = blacks;
		}

		for (JLabel l : searchLabel) {
			if (l.getBounds().x == point.x && l.getBounds().y == point.y) {
				label = l;
			}
		}
		return label;
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
		splash = new Splashscreen();
		splash.setVisible(true);

		// JPanel newGameDialogPanel = new JPanel(); // for starting a new game
		// JRadioButton radiobutton1 = new JRadioButton("2 Players");
		// JRadioButton radiobutton2 = new JRadioButton("With Computer");
		// newGameDialogPanel.add(radiobutton1);
		// newGameDialogPanel.add(radiobutton2);
		// JOptionPane.showInputDialog(newGameDialogPanel);
	}

}
