package game;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Player {

	String name;
	int color; // (0="white"&&1="black"),
	ArrayList<Point> menOnTheBoard;
	int menHoldInHand;

	/**
	 * Constructor.
	 * @param name
	 * @param color
	 */
	public Player(String name, int color) {
		this.name = name;
		this.color = color;
		menHoldInHand = 9;
		menOnTheBoard = new ArrayList<Point>();
	}

	/**
	 * Place a man on the board. Assuming always place to a valid (empty) point.
	 * @param pt
	 */
	public void placeAMan(Point pt) {
		if (menHoldInHand>0) {
			menOnTheBoard.add(pt);
			menHoldInHand--;
			System.out.println("Player" + (color + 1) + " place a man on " + pt);
		}
	}

	/**
	 * Move a Man. assuming menHoldInHand=0 and it's always a valid move.
	 * @param pt1
	 * @param pt2
	 */
	public void moveAMan(Point pt1, Point pt2) {
		if (menOnTheBoard.contains(pt1) && !menOnTheBoard.contains(pt2)) {
			menOnTheBoard.remove(pt1);
			menOnTheBoard.add(pt2);
			System.out.println("Player" + (color + 1) + " move a man from "
					+ pt1 + " to " + pt2);
		}
	}

	/**
	 * Remove a man. message should be sent by the opponents' move.
	 * @param pt
	 */
	public void removeAMan(Point pt) {
		menOnTheBoard.remove(pt);
		System.out.println("Player" + (color + 1) + " lose a man on " + pt);
	}
	
	/**
	 * Determine if a player is allowed to fly.
	 * @return
	 */
	public boolean allowToFly() {
		if (menHoldInHand==0 && menOnTheBoard.size()==3)
			return true;
		return false;
	}

	/**
	 * Judge if the player loses.
	 * @return
	 */
	public boolean lose() {
		if (menOnTheBoard.size()==2 && menHoldInHand==0)
			return true;
		if (menHoldInHand==0 && !hasLegalMoves()) 
			return true;
		return false;
	}

	// TODO
	private boolean hasLegalMoves() {
		for (Point pt : menOnTheBoard) {
			Set<Point> adj = pt.getAdjacentPoints();
			for(Point p : adj) {
				if(!Board.isOccupied(p))
					return true;
			}
		}
		return false;
	}

	// getters
	public String getName() {
		return name;
	}

	public int getColor() {
		return color;
	}

	public ArrayList<Point> getMenOnTheBoard() {
		return menOnTheBoard;
	}

	public int getMenHoldInHand() {
		return menHoldInHand;
	}

	public boolean hasPoint(Point pt) {
//		System.out.println("pt=" + pt);
//		System.out.print("Player" + (color+1) + " has: ");
//		for (Point p : menOnTheBoard)
//			System.out.print(p+" ");
//		System.out.println();
		return menOnTheBoard.contains(pt);
	}

	/**
	 * Check if there is a new mill - related to the newpt.
	 * 		check 6 possibilities: newpt, leftright(x+-), updown(y+-) to the left(x--), right(x++) or up(y--), down(y++).
	 * @param newpt
	 * @return
	 */
	public boolean hasMills(Point newpt) {
		// TODO Auto-generated method stub
//		int x0 = newpt.getX();
//		int y0 = newpt.getY();
		Point left = newpt.getLeftNeighbor();
		Point right = newpt.getRightNeighbor();
		Point up = newpt.getUpNeighbor();
		Point down = newpt.getDownNeighbor();
		// check left_right(x+-):
		if(left!=null && right!=null)
			if(menOnTheBoard.contains(left) && menOnTheBoard.contains(right))
				return true;
		// check up_down(x+-):
		if(up!=null && down!=null)
			if(menOnTheBoard.contains(up) && menOnTheBoard.contains(down))
				return true;
		// check left(x--):
		if(left!=null) {
			Point leftleft = left.getLeftNeighbor();
			if (leftleft!=null)
				if (menOnTheBoard.contains(left) && menOnTheBoard.contains(leftleft))
					return true;
		}
		// check right(x++):
		if(right!=null) {
			Point rightright = right.getRightNeighbor();
			if (rightright!=null)
				if (menOnTheBoard.contains(right) && menOnTheBoard.contains(rightright))
					return true;
		}
		// check up(y--):
		if(up!=null) {
			Point upup = up.getUpNeighbor();
			if (upup!=null)
				if (menOnTheBoard.contains(up) && menOnTheBoard.contains(upup))
					return true;
		}
		// check down(y++):
		if(down!=null) {
			Point downdown = down.getDownNeighbor();
			if (downdown!=null)
				if (menOnTheBoard.contains(down) && menOnTheBoard.contains(downdown))
					return true;
		}
		return false;
	}
	
	public String toString() {
		String str = "Name: " + name + " Color: " + (color==0 ? "white\n" : "black ");
		str += ("MenHoldInHand=" + menHoldInHand + " ");
		str += ("MenOnTheBoard: " + menOnTheBoard);
		return str;
	}

}
