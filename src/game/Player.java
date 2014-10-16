package game;

import java.util.HashSet;
import java.util.Set;

public class Player {

	String name;
	int color; // (0="white"&&1="black"),
	Set<Point> menOnTheBoard;
	int menHoldInHand;

	public Player(String name, int color) {
		this.name = name;
		this.color = color;
		menHoldInHand = 9;
		menOnTheBoard = new HashSet<Point>();
	}

	void placeAMan(Point pt) {
		menOnTheBoard.add(pt);
		menHoldInHand--;
		System.out.println("Player" + color + " place a man on " + pt);
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
	public boolean lose() {
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

	public boolean hasPoint(Point pt) {
		System.out.println("pt=" + pt);
		System.out.print("Player" + color + " has: ");
		for (Point p : menOnTheBoard)
			System.out.print(p+" ");
		System.out.println();
		return menOnTheBoard.contains(pt);
	}

	public void removeAMan(Point pt) {
		menOnTheBoard.remove(pt);
	}

}
