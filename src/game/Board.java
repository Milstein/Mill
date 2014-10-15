package game;

import java.util.HashSet;
import java.util.Set;

public class Board {
	
	//data field: 
	private Player[] players;
	static Set<Point> validPoints = new HashSet<Point>(); // = {	pt1,pt2,...,pt24 }
	
	// Constructor
	public Board(String name1, String name2) {
		players = new Player[2];
		players[0] = new Player(name1, 0);
		players[1] = new Player(name2, 1);
		validPoints = new HashSet<Point>();
		validPoints.add(new Point(0,0,false));
		validPoints.add(new Point(3,0,false));
		validPoints.add(new Point(6,0,false));
		validPoints.add(new Point(1,1,false));
		validPoints.add(new Point(3,1,false));
		validPoints.add(new Point(5,1,false));
		validPoints.add(new Point(2,2,false));
		validPoints.add(new Point(3,2,false));
		validPoints.add(new Point(4,2,false));
		validPoints.add(new Point(0,3,false));
		validPoints.add(new Point(1,3,false));
		validPoints.add(new Point(2,3,false));
		validPoints.add(new Point(4,3,false));
		validPoints.add(new Point(5,3,false));
		validPoints.add(new Point(6,3,false));
		validPoints.add(new Point(2,4,false));
		validPoints.add(new Point(3,4,false));
		validPoints.add(new Point(4,4,false));
		validPoints.add(new Point(1,5,false));
		validPoints.add(new Point(3,5,false));
		validPoints.add(new Point(5,5,false));
		validPoints.add(new Point(0,6,false));
		validPoints.add(new Point(3,6,false));
		validPoints.add(new Point(6,6,false));
	}
	//methods:
	public void init() {
		// Initialize the board.
		//TODO
	}
	//TODO
	public boolean makeAMove(Point point1, Point point2, int player) {
		// validation check: point2 has to be a valid point in the board.
		if(!validpoints.contains(point2))	
			return false;
		// add a new man: makeAnAction(null,point2,player)
		// move a man: makeAnAction(point1,point2,player)
		if(point1==null) {
			if(players[player].getMenHoldInHand()==0)
				return false;
			if(point2.isOccupied())
				return false;
			return placeAMan(point2,player);
		} else {
			// validation check: point1 has to be a valid point in the board.
			if(!validpoints.contains(point1))		
				return false;
			if(point2.isOccupied())
				return false;
			return moveAMan(point1,point2,player);
		}
				
	}
		
	//TODO
	private boolean placeAMan(Point pt, int player) {
		// check if pt is occupied.
		// if not, put a new man at pt on the board.
		// update the board, update player
		// check if player has mills, if has, remove one opponent's mill.
		return false;
	}
	
	// TODO
	private boolean moveAMan(Point pt1, Point pt2, int player) {
		if(pt2.isOccupied())
			return false;
		if(enableFlying(player)) {
			// if pt2 is not occupied, update player&board, 
			return true;
		} else {
			// check adjacent points of pt1 and see if any of them matches pt2.
			if (pt1.getAdjacentPoints().contains(pt2)) {
				players[player].moveAMan(pt1,pt2);
				return true;
			} else {
				return false;
			}				
		}
	}

	boolean hasMills(int player) {
		//TODO
		return false;
	}
	void removeAMill(int player) {
		// TODO
	}
	boolean enableFlying(int player) {
		if (players[player].getMenHoldInHand()==0 && players[player].getMenOnTheBoard().size()==3) 
			return true;
		else
			return false;
	}
}
