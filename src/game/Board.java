package game;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Board {

	// data field:
	private static Player[] players;
	static Set<Point> validPoints = new HashSet<Point>();

	// Constructor
	public Board(String name1, String name2) {
		this.init(name1, name2);
		System.out.println("Game started: White (" + name1 + ") first: ");
	}

	// methods:
	/**
	 * Initialize the game board.
	 * @param name1
	 * @param name2
	 */
	public void init(String name1, String name2) {
		// Initialize the board.
		players = new Player[2];
		players[0] = new Player(name1, 0);
		players[1] = new Player(name2, 1);
		validPoints = new HashSet<Point>();
		validPoints.add(new Point(0, 0));
		validPoints.add(new Point(3, 0));
		validPoints.add(new Point(6, 0));
		validPoints.add(new Point(1, 1));
		validPoints.add(new Point(3, 1));
		validPoints.add(new Point(5, 1));
		validPoints.add(new Point(2, 2));
		validPoints.add(new Point(3, 2));
		validPoints.add(new Point(4, 2));
		validPoints.add(new Point(0, 3));
		validPoints.add(new Point(1, 3));
		validPoints.add(new Point(2, 3));
		validPoints.add(new Point(4, 3));
		validPoints.add(new Point(5, 3));
		validPoints.add(new Point(6, 3));
		validPoints.add(new Point(2, 4));
		validPoints.add(new Point(3, 4));
		validPoints.add(new Point(4, 4));
		validPoints.add(new Point(1, 5));
		validPoints.add(new Point(3, 5));
		validPoints.add(new Point(5, 5));
		validPoints.add(new Point(0, 6));
		validPoints.add(new Point(3, 6));
		validPoints.add(new Point(6, 6));
	}

	// reserved for a.i.
	public boolean makeAnAction(int player) {
		return false;
	}

	/**
	 * Generic action: place a man or move a man.
	 * 
	 * @param point1 - null if place a new man.
	 * @param point2
	 * @param player
	 * @return true if success. false if invalid action.
	 */
	public boolean makeAnAction(Point point1, Point point2, int player) {
		// validation check: point2 has to be a valid point in the board.
		if (!validPoints.contains(point2)) {
			System.err.println("Point " + point2 + " is not a valid point!");
			System.out.println("Player " + (player+1) + "'s turn.");
			return false;
		}
		// add a new man: makeAnAction(null,point2,player)
		// move a man: makeAnAction(point1,point2,player)
		if (point1 == null) {
			if (players[player].getMenHoldInHand() == 0) {
				System.err.println("Player has no more men in hand!");
				System.out.println("Player " + (player+1) + "'s turn.");
				return false;
			}
			if (isOccupied(point2)) {
				System.err.println("Point " + point2 + " is occupied!");
				System.out.println("Player " + (player+1) + "'s turn.");
				return false;
			}
			return placeAMan(point2, player);
		} else {
			// validation check: point1 has to be a valid point in the board.
			if (!validPoints.contains(point1)) {
				System.err.println("Point " + point1 + " is not a valid point!");
				System.out.println("Player" + (player+1) + "'s turn.");
				return false;
			}
			// check if pt1 is the player's man
			if (!players[player].getMenOnTheBoard().contains(point1)) {
				System.err.println("Point " + point1 + " does not has Player" + (player+1) + "'s man!");
				System.out.println("Player" + (player+1) + "'s turn.");
				return false;
			}
			// point2 cannot be occupied.
			if (isOccupied(point2)) {
				System.err.println("Point " + point2 + " is occupied!");
				System.out.println("Player " + (player+1) + "'s turn.");
				return false;
			}
			return moveAMan(point1, point2, player);
		}

	}

	// check player1 and player2 if they have this point under menOnTheBoard.
	/**
	 * Check if the point is occupied.
	 * @param pt
	 * @return
	 */
	public static boolean isOccupied(Point pt) {
		if (players[0].hasPoint(pt))
			return true;
		if (players[1].hasPoint(pt))
			return true;
		return false;
	}

	/**
	 * Place a new man on the board.
	 * @param pt
	 * @param player
	 * @return
	 */
	private boolean placeAMan(Point pt, int player) {
		players[player].placeAMan(pt);
		// update the board, update player.
		// check if player has mills, if has, remove one opponent's man.
 
		return true;
	}

	// precondition: pt1 and pt2 are valid.
	/**
	 * Move a man.
	 * precondition: pt1 is not null, pt2 is not occupied.
	 * @param pt1
	 * @param pt2
	 * @param player
	 * @return
	 */
	private boolean moveAMan(Point pt1, Point pt2, int player) {
		// check if player enables flying.
		if (enableFlying(player)) {
			System.out.println("Player" + (player+1) + " flying enabled!");
			players[player].moveAMan(pt1, pt2);
//			if (hasMills(player)) {
//				System.out.println("Player " + (player+1) + " has a MILL!");
//				System.out.println("Ask Player" + (player+1) + ": to remove a man of Player " + ((player == 0 ? 1 : 0)+1));
//				//removeAMan(player == 0 ? 1 : 0);
//			}
			return true;
		} else {
			// check adjacent points of pt1 and see if any of them matches pt2.
			if (pt1.getAdjacentPoints().contains(pt2)) {
				players[player].moveAMan(pt1, pt2);
//				if (hasMills(player)) {
//					System.out.println("Player " + (player+1) + " has a MILL!");
//					System.out.println("Ask Player" + (player+1) + ": to remove a man of Player " + ((player == 0 ? 1 : 0)+1));					
//					//removeAMan(player == 0 ? 1 : 0);
//				}
				return true;
			} else {
				System.err.println("Player" + (player+1) + ": Invalid move from point " + pt1 + " to " + pt2);
				System.out.println("Player " + (player+1) + "'s turn.");
				return false;
			}
		}
	}

	// check if the player has mills.
	/**
	 * 
	 * @param player
	 * @return
	 */
	public static boolean hasMills(int player, Point newpt) {
		return players[player].hasMills(newpt);
	}

	private void removeAMan(int player, Point pt) {
		players[player].removeAMan(pt);
	}

	private boolean enableFlying(int player) {
		return players[player].allowToFly();
	}

	private static boolean endOfGame() {
		if (players[0].lose() || players[1].lose())
			return true;
		return false;
	}

	// Testing
	public static void main(String[] args) {
		Board game = new Board("Player1", "Player2");
//		System.out.println(game.makeAnAction(null, new Point(0, 0), 0));
//		System.out.println(game.makeAnAction(null, new Point(3, 0), 0));
//		System.out.println(game.makeAnAction(null, new Point(6, 0), 0));

		// Scanner for scanning 
		Scanner in = new Scanner(System.in);
		// delimeter?
		boolean validMove = false;
		boolean validRemove = false;
		while(!endOfGame()) {
			// Test line......................................
			for(Point pt : players[0].getMenOnTheBoard()) {
				System.out.print(pt);
			}
			System.out.println("");
			for(Point pt : players[1].getMenOnTheBoard()) {
				System.out.print(pt);
			}
			System.out.println("");
			//--------------------------------------------------
			System.out.println("Player1's turn.");
			// Player1;
			while (!validMove) {
				if(players[0].getMenHoldInHand()>0) {
					System.out.println("Player1 to place a man at point: ");
					int x_coor = in.nextInt();
					int y_coor = in.nextInt();
					Point newpt = new Point(x_coor, y_coor);
					validMove = game.makeAnAction(null, newpt, 0);
					// if the new point makes a mill.
					if (validMove && hasMills(0, newpt)) {
						System.out.println("Player1" + " has a MILL!");
						System.out.println("Ask Player1" + ": to remove a man of Player2");
						while (!validRemove) {
							System.out.println("\nYou can remove one from: ");
							for(Point pt : players[1].getMenOnTheBoard()) {
								System.out.print(pt);
							}
							System.out.println("\nSelect the man you want to remove: ");
							int x_remove = in.nextInt();
							int y_remove = in.nextInt();
							Point pt = new Point(x_remove, y_remove);
							if (players[1].getMenOnTheBoard().contains(pt)) {
								game.removeAMan(1, pt);
								validRemove = true;
							} else {
								System.err.println("Invalid point to remove!");
							}
						}
					}
				} else {
					if(players[0].getMenHoldInHand()==0) {
						System.out.println("Player1 to move a man: ");
						System.out.println("Available points:");
						for(Point pt : players[0].getMenOnTheBoard()) {
							System.out.print(pt);
						}
						System.out.println("From:");
						int x_1 = in.nextInt();
						int y_1 = in.nextInt();
						System.out.println("To:");
						int x_2 = in.nextInt();
						int y_2 = in.nextInt();
						Point newpt = new Point(x_2, y_2);
						validMove = game.makeAnAction(new Point(x_1,y_1), newpt, 0);
						if (validMove && hasMills(0, newpt)) {
							System.out.println("Player1" + " has a MILL!");
							System.out.println("Ask Player1" + ": to remove a man of Player2");
							while (!validRemove) {
								System.out.println("You can remove one from: ");
								for(Point pt : players[1].getMenOnTheBoard()) {
									System.out.print(pt);
								}
								System.out.println("\nSelect the man you want to remove: ");
								int x_remove = in.nextInt();
								int y_remove = in.nextInt();
								Point pt = new Point(x_remove, y_remove);
								if (players[1].getMenOnTheBoard().contains(pt)) {
									game.removeAMan(1, pt);
									validRemove = true;
								} else {
									System.err.println("Invalid point to remove!");
								}
							}
						}
					}
				}
			}
			// reset.
			validMove=false;
			validRemove=false;
			
			System.out.println("Player2's turn.");
			// Player2;
			if (endOfGame())
				break;
			while (!validMove) {
				if(players[1].getMenHoldInHand()>0) {
					System.out.println("Player2 to place a man at point: ");
					int x_coor = in.nextInt();
					int y_coor = in.nextInt();
					Point newpt = new Point(x_coor, y_coor);
					validMove = game.makeAnAction(null, newpt, 1);
					if (validMove && hasMills(1, newpt)) {
						System.out.println("Player2" + " has a MILL!");
						System.out.println("Ask Player2" + ": to remove a man of Player1");
						while (!validRemove) {
							System.out.println("You can remove one from: ");
							for(Point pt : players[0].getMenOnTheBoard()) {
								System.out.print(pt);
							}
							System.out.println("\nSelect the man you want to remove: ");
							int x_remove = in.nextInt();
							int y_remove = in.nextInt();
							Point pt = new Point(x_remove, y_remove);
							if (players[0].getMenOnTheBoard().contains(pt)) {
								game.removeAMan(0, pt);
								validRemove = true;
							} else {
								System.err.println("Invalid point to remove!");
							}
						}
					}
				} else {
					if(players[1].getMenHoldInHand()==0) {
						System.out.println("Player2 to move a man: ");
						// list available points.
						System.out.println("Available points:");
						for(Point pt : players[1].getMenOnTheBoard()) {
							System.out.print(pt);
						}
						System.out.println("From:");
						int x_1 = in.nextInt();
						int y_1 = in.nextInt();
						System.out.println("To:");
						int x_2 = in.nextInt();
						int y_2 = in.nextInt();
						Point newpt = new Point(x_2, y_2);
						validMove = game.makeAnAction(new Point(x_1,y_1), newpt, 1);
						if (validMove && hasMills(1, newpt)) {
							System.out.println("Player2" + " has a MILL!");
							System.out.println("Ask Player2" + ": to remove a man of Player1");
							while (!validRemove) {
								System.out.println("You can remove one from: ");
								for(Point pt : players[0].getMenOnTheBoard()) {
									System.out.print(pt);
								}
								System.out.println("\nSelect the man you want to remove: ");
								int x_remove = in.nextInt();
								int y_remove = in.nextInt();
								Point pt = new Point(x_remove, y_remove);
								if (players[0].getMenOnTheBoard().contains(pt)) {
									game.removeAMan(0, pt);
									validRemove = true;
								} else {
									System.err.println("Invalid point to remove!");
								}
							}
						}
					}
				}
			}
			// reset.
			validMove=false;
			validRemove = false;
		}
		in.close();
	}

}
