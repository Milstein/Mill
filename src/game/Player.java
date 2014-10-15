package game;

import java.util.Set;

public class Player {
	
	String name;
	int color; //(0="white"&&1="black"), 
	Set<Point> menOnTheBoard;
	int menHoldInHand;
	
//	boolean makeAMove(Point pt1, Point pt2) {
//		if (menHoldInHand>0)
//		    placeAMan(pt2);
//		else
//		    moveAMan(pt1,pt2);
//		return false;
//	}
	

	//TODO
	public Player(String name, int color) {
		this.name = name;
		this.color = color;
	}

	Point placeAMan(Point pt) {
		return null;
	}
	
	//TODO
	Point moveAMan(Point pt1, Point pt2) {
		return null;
	}
	
	//TODO
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
