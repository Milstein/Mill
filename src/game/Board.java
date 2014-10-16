package game;

import java.util.HashSet;
import java.util.Set;

public class Board {

	// data field:
	private static Player[] players;
	static Set<Point> validPoints = new HashSet<Point>();

	// Constructor
	public Board(String name1, String name2) {
		this.init(name1, name2);
	}

	// methods:
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

	public boolean makeAnAction(int player) {
		return false;
	}

	/**
	 * Generic action: place a man or move a man.
	 * 
	 * @param point1
	 * @param point2
	 * @param player
	 * @return true if success. false if invalid action.
	 */
	public boolean makeAnAction(Point point1, Point point2, int player) {
		// validation check: point2 has to be a valid point in the board.
		if (!validPoints.contains(point2))
			return false;
		// add a new man: makeAnAction(null,point2,player)
		// move a man: makeAnAction(point1,point2,player)
		if (point1 == null) {
			if (players[player].getMenHoldInHand() == 0)
				return false;
			if (isOccupied(point2))
				return false;
			return placeAMan(point2, player);
		} else {
			// validation check: point1 has to be a valid point in the board.
			if (!validPoints.contains(point1))
				return false;
			// check if pt1 is the player's man
			if (!players[player].getMenOnTheBoard().contains(point1))
				return false;
			// point2 cannot be occupied.
			if (isOccupied(point2))
				return false;
			return moveAMan(point1, point2, player);
		}

	}

	// Check if the point is occupied.
	// check player1 and player2 if they have this point under menOnTheBoard.
	private boolean isOccupied(Point pt) {
		if (players[0].hasPoint(pt))
			return true;
		if (players[1].hasPoint(pt))
			return true;
		return false;
	}

	private boolean placeAMan(Point pt, int player) {
		// check if the player has a man on hand:
		// if not, put a new man at pt on the board.
		if (players[player].getMenHoldInHand() <= 0)
			return false;
		players[player].placeAMan(pt);
		// update the board, update player.
		// check if player has mills, if has, remove one opponent's man.
//		if (hasMills(player)) {
//			removeAMan(player == 0 ? 1 : 0);
//		}
		return true;
	}

	// precondition: pt1 and pt2 are valid.
	private boolean moveAMan(Point pt1, Point pt2, int player) {
		// check if player enables flying.
		if (enableFlying(player)) {
			players[player].moveAMan(pt1, pt2);
//			if (hasMills(player)) {
//				removeAMan(player == 0 ? 1 : 0);
//			}
			return true;
		} else {
			// check adjacent points of pt1 and see if any of them matches pt2.
			if (pt1.getAdjacentPoints().contains(pt2)) {
				players[player].moveAMan(pt1, pt2);
//				if (hasMills(player)) {
//					removeAMan(player == 0 ? 1 : 0);
//				}
				return true;
			} else {
				return false;
			}
		}
	}

	boolean hasMills(int player) {
		Set<Point> points = players[player].getMenOnTheBoard();
		// TODO loop and check top down
		for (Point pt : points) {
			int x = pt.getX();
			int y = pt.getY();
			// horizontally
			boolean xplus = false;
			boolean xminus = false;
			while (x < Point.MAXX) {
				x++;
				if (points.contains(new Point(x, y))) {
					xplus = true;
					break;
				}
			}
			x = pt.getX();
			while (x > Point.MINX) {
				x--;
				if (points.contains(new Point(x, y))) {
					xminus = true;
					break;
				}
			}
			x = pt.getX();
			if (xplus && xminus)
				return true;
			// vertically
			boolean yplus = false;
			boolean yminus = false;
			while (y < Point.MAXY) {
				y++;
				if (points.contains(new Point(x, y))) {
					yplus = true;
					break;
				}
			}
			y = pt.getY();
			while (y > Point.MINY) {
				y--;
				if (points.contains(new Point(x, y))) {
					yminus = true;
					break;
				}
			}
			y = pt.getY();
			if (yplus && yminus)
				return true;
		}
		return false;
	}

	void removeAMan(int player) {
		//
	}

	private void removeAMan(int player, Point pt) {
		players[player].removeAMan(pt);
	}

	boolean enableFlying(int player) {
		if (players[player].getMenHoldInHand() == 0
				&& players[player].getMenOnTheBoard().size() == 3)
			return true;
		else
			return false;
	}

	static boolean endOfGame() {
		if (players[0].lose() || players[1].lose())
			return true;
		return false;
	}

	// Testing
	public static void main(String[] args) {
		Board game = new Board("Player1", "Player2");
		// System.out.println(validPoints.contains(new
		// Point(0,0,false).toString()));
		System.out.println(game.makeAnAction(null, new Point(0, 0), 0));
		System.out.println(game.makeAnAction(null, new Point(3, 0), 0));
		System.out.println(game.makeAnAction(null, new Point(6, 0), 0));
		System.out.println(game.hasMills(0));
	}

}
