package game;

import java.util.ArrayList;

public class Board {

	// data field:
	private static Player[] players;
	public static ArrayList<Point> validPoints = new ArrayList<Point>();

	/**
	 * Constructor.
	 * @param name1
	 * @param name2
	 */
	public Board(String name1, String name2) {
		this.init(name1, name2);
	}

	// methods:
	/**
	 * Initialize the game board.
	 * @param name1
	 * @param name2
	 */
	public void init(String name1, String name2) {
		// Initialize the board.
		System.out.print("Game Initializing...");
		players = new Player[2];
		players[0] = new Player(name1, 0);
		players[1] = new Player(name2, 1);
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
		System.out.println("...Done\n");
		System.out.println("Game started: White (" + name1 + ") first: ");
	}
	
	// do something.
	public void idle() {	}
	
	/**
	 * For testing. assuming i = 0/1.
	 * @param i
	 * @return
	 */
	public Player getPlayer(int i) {
		return players[i];
	}

	// reserved for a.i. player = 1?
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
	public boolean hasMills(int player, Point newpt) {
		return players[player].hasMills(newpt);
	}

	/**
	 * Remove a man from a certain player, with the point specified.
	 * Point is assuming to be a valid point.
	 * @param player
	 * @param pt
	 */
	public void removeAMan(int player, Point pt) {
		players[player].removeAMan(pt);
	}

	/**
	 * if flying is enable for certain player.
	 * @param player
	 * @return
	 */
	private boolean enableFlying(int player) {
		return players[player].allowToFly();
	}

	/**
	 * If the game is end.
	 * @return
	 */
	public boolean endOfGame() {
		if (players[0].lose() || players[1].lose())
			return true;
		return false;
	}


}
