package game;

import java.util.Set;

public class Player {

	String name;
	int color; // (0="white"&&1="black"),
	Set<Point> menOnTheBoard;
	int menHoldInHand;

	public Player(String name, int color) {
		this.name = name;
		this.color = color;
	}

	String placeAMan(Point pt) {
		if (menHoldInHand > 0) {
			menOnTheBoard.add(pt);
			menHoldInHand--;
		} else {
			return "Not Allowed to move";
		}
		return pt.getX() + ", " + pt.getY();
	}

	// TODO
	Point moveAMan(Point pt1, Point pt2) {
		return null;
	}

	// TODO
	private boolean allowToFly() {
		return false;
	}

	// TODO
	private boolean lose() {
		return false;
	}

	// TODO
	private boolean hasLessThanThree() {
		return false;
	}

	// TODO
	private boolean hasLegalMoves() {
		return false;
	}

	// getters
	public String getName() {
		return name;
	}

	public int getColor() {
		return color;
	}

	public Set<Point> getMenOnTheBoard() {
		return menOnTheBoard;
	}

	public int getMenHoldInHand() {
		return menHoldInHand;
	}

}
