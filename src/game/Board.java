package game;

import java.util.HashSet;
import java.util.Set;

public class Board {
	
	//data field: 
	private static Player[] players;
	static Set<String> validPoints = new HashSet<String>();
	
	// Constructor
	public Board(String name1, String name2) {
		this.init(name1, name2);
	}
	//methods:
	public void init(String name1, String name2) {
		// Initialize the board.
		players = new Player[2];
		players[0] = new Player(name1, 0);
		players[1] = new Player(name2, 1);
		validPoints = new HashSet<String>();
		validPoints.add("00");
		validPoints.add("30");
		validPoints.add("60");
		validPoints.add("11");
		validPoints.add("31");
		validPoints.add("51");
		validPoints.add("22");
		validPoints.add("32");
		validPoints.add("42");
		validPoints.add("03");
		validPoints.add("13");
		validPoints.add("23");
		validPoints.add("43");
		validPoints.add("53");
		validPoints.add("63");
		validPoints.add("24");
		validPoints.add("34");
		validPoints.add("44");
		validPoints.add("15");
		validPoints.add("35");
		validPoints.add("55");
		validPoints.add("06");
		validPoints.add("36");
		validPoints.add("66");
	}

	public boolean makeAnAction(int player) {
		return false;
	}
	/**
	 * Generic action: place a man or move a man.
	 * @param point1
	 * @param point2
	 * @param player
	 * @return true if success. false if invalid action.
	 */
	public boolean makeAnAction(Point point1, Point point2, int player) {
		// validation check: point2 has to be a valid point in the board.
		if(!validPoints.contains(point2.toString()))	
			return false;
		// add a new man: makeAnAction(null,point2,player)
		// move a man: makeAnAction(point1,point2,player)
		if(point1==null) {
			if(players[player].getMenHoldInHand()==0)
				return false;
			if(isOccupied(point2))
				return false;
			return placeAMan(point2,player);
		} else {
			// validation check: point1 has to be a valid point in the board.
			if(!validPoints.contains(point1.toString()))		
				return false;
			// check if pt1 is the player's man
			if (!players[player].getMenOnTheBoard().contains(point1.toString()))
				return false;
			// point2 cannot be occupied.
			if(isOccupied(point2))
				return false;
			return moveAMan(point1,point2,player);
		}
				
	}

	// Check if the point is occupied.
	// check player1 and player2 if they have this point under menOnTheBoard.
	private boolean isOccupied(Point pt) {
		if (players[0].hasPoint(pt.toString()))
			return true;
		if (players[1].hasPoint(pt.toString()))
			return true;
		return false;
	}
	private boolean placeAMan(Point pt, int player) {
		// check if the player has a man on hand:
		// if not, put a new man at pt on the board.
		if (players[player].getMenHoldInHand()<=0)
			return false;
		players[player].placeAMan(pt);
		// update the board, update player.
		// check if player has mills, if has, remove one opponent's man.
		if (hasMills(player)) {
			removeAMan(player==0 ? 1 : 0);
		}
		return true;
	}
	
	// precondition: pt1 and pt2 are valid.
	private boolean moveAMan(Point pt1, Point pt2, int player) {
		// check if player enables flying.
		if(enableFlying(player)) {
			players[player].moveAMan(pt1, pt2);
			if (hasMills(player)) {
				removeAMan(player==0 ? 1 : 0);
			}
			return true;
		} else {
			// check adjacent points of pt1 and see if any of them matches pt2.
			if (pt1.getAdjacentPoints().contains(pt2.toString())) {
				players[player].moveAMan(pt1,pt2);
				if (hasMills(player)) {
					removeAMan(player==0 ? 1 : 0);
				}
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
	void removeAMan(int player) {
		removeAMan(player, "00");
	}
	private void removeAMan(int player, String str) {
		players[player].removeAMan(str);
	}
	
	boolean enableFlying(int player) {
		if (players[player].getMenHoldInHand()==0 && players[player].getMenOnTheBoard().size()==3) 
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
		//System.out.println(validPoints.contains(new Point(0,0,false).toString()));
		System.out.println(game.makeAnAction(null, new Point(0,0), 0));
		System.out.println(game.makeAnAction(null, new Point(0,3), 1));
		System.out.println(game.makeAnAction(null, new Point(3,0), 0));
		System.out.println(game.makeAnAction(null, new Point(0,3), 1));
	}
	
}
