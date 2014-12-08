package game;

import java.util.ArrayList;
import java.util.Set;

public class Player {

	String name;
	int color; // (0="white"&&1="black"),
	ArrayList<PointGame> menOnTheBoard;
	int menHoldInHand;

	/**
	 * Constructor.
	 * 
	 * @param name
	 * @param color
	 */
	public Player(String name, int color) {
		this.name = name;
		this.color = color;
		menHoldInHand = 9;
		menOnTheBoard = new ArrayList<PointGame>();
	}

	/**
	 * Place a man on the board. Assuming always place to a valid (empty) point.
	 * 
	 * @param pt
	 */
	public void placeAMan(PointGame pt) {
		if (menHoldInHand > 0) {
			menOnTheBoard.add(pt);
			menHoldInHand--;
			System.out
					.println("Player" + (color + 1) + " place a man on " + pt);
		}
	}

	/**
	 * Move a Man. assuming menHoldInHand=0 and it's always a valid move.
	 * 
	 * @param pt1
	 * @param pt2
	 */
	public void moveAMan(PointGame pt1, PointGame pt2) {
		if (menOnTheBoard.contains(pt1) && !menOnTheBoard.contains(pt2)) {
			menOnTheBoard.remove(pt1);
			menOnTheBoard.add(pt2);
			System.out.println("Player" + (color + 1) + " move a man from "
					+ pt1 + " to " + pt2);
		}
	}

	/**
	 * Remove a man. message should be sent by the opponents' move.
	 * 
	 * @param pt
	 */
	public void removeAMan(PointGame pt) {
		menOnTheBoard.remove(pt);
		System.out.println("Player" + (color + 1) + " lose a man on " + pt);
	}

	/**
	 * Determine if a player is allowed to fly.
	 * 
	 * @return
	 */
	public boolean allowToFly() {
		if (menHoldInHand == 0 && menOnTheBoard.size() == 3)
			return true;
		return false;
	}

	/**
	 * Judge if the player loses.
	 * 
	 * @return
	 */
	public boolean lose() {
		if (menOnTheBoard.size() == 2 && menHoldInHand == 0)
			return true;
		if (menHoldInHand == 0 && !hasLegalMoves())
			return true;
		return false;
	}

	// when menHoldInHand>0 we won't call this method.
	/**
	 * determine if the player has any legal moves.
	 * 
	 * @return
	 */
	private boolean hasLegalMoves() {
		for (PointGame pt : menOnTheBoard) {
			Set<PointGame> adj = pt.getAdjacentPoints();
			for (PointGame p : adj) {
				if (!Board.isOccupied(p))
					return true;
			}
		}
		return false;
	}

	// getters
	/**
	 * Get player's name.
	 * 
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * Get color.
	 * 
	 * @return
	 */
	public int getColor() {
		return color;
	}

	/**
	 * Get a list of men on the board.
	 * 
	 * @return
	 */
	public ArrayList<PointGame> getMenOnTheBoard() {
		return menOnTheBoard;
	}

	/**
	 * See how many marble hold in hand.
	 * 
	 * @return
	 */
	public int getMenHoldInHand() {
		return menHoldInHand;
	}

	/**
	 * Check whether a point is hold by this player or not.
	 * 
	 * @param pt
	 * @return
	 */
	public boolean hasPoint(PointGame pt) {
		return menOnTheBoard.contains(pt);
	}

	/**
	 * Check if there is a NEW mill - related to the newpt. check 6
	 * possibilities: newpt, leftright(x+-), updown(y+-) to the left(x--),
	 * right(x++) or up(y--), down(y++).
	 * 
	 * @param newpt
	 * @return
	 */
	public boolean hasMills(PointGame newpt) {
		// int x0 = newpt.getX();
		// int y0 = newpt.getY();
		if (!menOnTheBoard.contains(newpt))
			return false;
		PointGame left = newpt.getLeftNeighbor();
		PointGame right = newpt.getRightNeighbor();
		PointGame up = newpt.getUpNeighbor();
		PointGame down = newpt.getDownNeighbor();
		// check left_right(x+-):
		if (left != null && right != null)
			if (menOnTheBoard.contains(left) && menOnTheBoard.contains(right))
				return true;
		// check up_down(x+-):
		if (up != null && down != null)
			if (menOnTheBoard.contains(up) && menOnTheBoard.contains(down))
				return true;
		// check left(x--):
		if (left != null) {
			PointGame leftleft = left.getLeftNeighbor();
			if (leftleft != null) {
				if (menOnTheBoard.contains(left)
						&& menOnTheBoard.contains(leftleft)) {
					return true;
				}
			}
		}
		// check right(x++):
		if (right != null) {
			PointGame rightright = right.getRightNeighbor();
			if (rightright != null)
				if (menOnTheBoard.contains(right)
						&& menOnTheBoard.contains(rightright))
					return true;
		}
		// check up(y--):
		if (up != null) {
			PointGame upup = up.getUpNeighbor();
			if (upup != null)
				if (menOnTheBoard.contains(up) && menOnTheBoard.contains(upup))
					return true;
		}
		// check down(y++):
		if (down != null) {
			PointGame downdown = down.getDownNeighbor();
			if (downdown != null)
				if (menOnTheBoard.contains(down)
						&& menOnTheBoard.contains(downdown))
					return true;
		}
		return false;
	}

	/**
	 * Make a move as an a.i.
	 * @return
	 */
	public PointGame findAStupidPlace() {
		PointGame pointToPlace = null;
		for (PointGame pt : Board.validPoints) {
			if (!Board.isOccupied(pt)) {
				pointToPlace = pt;
				return pointToPlace;
			}
		}
		return pointToPlace;
	}
	/**
	 * Make a move as an A.I.
	 * @return - an array of length 2 for moving a man: [pointFrom, pointTo]
	 */
	public PointGame[] findAStupidMove() {
		PointGame[] moveSeq = new PointGame[2];
		for (PointGame pt1 : getMenOnTheBoard()) {
			Set<PointGame> adj = pt1.getAdjacentPoints();
			for (PointGame pt2 : adj) {
				if (!Board.isOccupied(pt2)) {
					moveSeq[0] = pt1;
					moveSeq[1] = pt2;
					return moveSeq;
				}
			}
		}
		return null;
	}

	/**
	 * Make a stupid fly as an A.I.
	 * @return
	 */
	public PointGame[] findAStupidFly() {
		PointGame[] moveSeq = new PointGame[2];
		moveSeq[0] = menOnTheBoard.get(0);
		for(PointGame pt : Board.validPoints){
			if (!Board.isOccupied(pt)) {
				moveSeq[1] = pt;
				return moveSeq;
			}
		}
		return null;
	}
	
	/**
	 * Stupid remove method. IQ = 20.
	 * @return
	 */
	public PointGame makeStupidRemove() {
		return menOnTheBoard.get(0);
	}
	
	/**
	 * return a string of the player.
	 */
	public String toString() {
		String str = "Name: " + name + " Color: "
				+ (color == 0 ? "white\n" : "black ");
		str += ("MenHoldInHand=" + menHoldInHand + " ");
		str += ("MenOnTheBoard: " + menOnTheBoard);
		return str;
	}


}
