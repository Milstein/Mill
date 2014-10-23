package tests;

import java.util.Scanner;

import game.Board;
import game.Player;
import game.Point;

import org.junit.Test;

public class GameLogicTests {
	
	// placeMan is basically placeman() in the Player.java. //
	// hasMills is basically hasMills() in the Player.java. //
	// removeAMan is basically removeAMan() in the Player.java. //
	// enableFlying is basically enableFlying() in the Player.java. //
	
	// Accumulation tests.
	// Gaming rules and logic.
	
	//TODO: This is the game flow (console version), reading points from console input.
	// which is going to be implemented in a gui version.
	public static void main(String[] args) {
		
		Board game = new Board("Player1", "Player2");
//		System.out.println(game.makeAnAction(null, new Point(0, 0), 0));
//		System.out.println(game.makeAnAction(null, new Point(3, 0), 0));
//		System.out.println(game.makeAnAction(null, new Point(6, 0), 0));
		Player p1 = game.getPlayer(0);
		Player p2 = game.getPlayer(1);
		// Scanner for scanning 
		Scanner in = new Scanner(System.in);
		// delimeter?
		boolean validMove = false;
		boolean validRemove = false;
		
		// TODO: to be implemented: p1 cannot remove a point from p2 if that point forms a mill and 
		// p2 has some other points that does not form a mill. vise versa.
	
		while(!game.endOfGame()) {
			// Test line......................................
			for(Point pt : p1.getMenOnTheBoard()) {
				System.out.print(pt);
			}
			System.out.println("");
			for(Point pt : p2.getMenOnTheBoard()) {
				System.out.print(pt);
			}
			System.out.println("");
			//--------------------------------------------------
			System.out.println("Player1's turn.");
			// Player1;
			while (!validMove) {
				if(game.getPlayer(0).getMenHoldInHand()>0) {
					System.out.println("Player1 to place a man at point: ");
					int x_coor = in.nextInt();
					int y_coor = in.nextInt();
					Point newpt = new Point(x_coor, y_coor);
					validMove = game.makeAnAction(null, newpt, 0);
					// if the new point makes a mill.
					if (validMove && game.hasMills(0, newpt)) {
						System.out.println("Player1" + " has a MILL!");
						System.out.println("Ask Player1" + ": to remove a man of Player2");
						while (!validRemove) {
							System.out.println("\nYou can remove one from: ");
							for(Point pt : p2.getMenOnTheBoard()) {
								System.out.print(pt);
							}
							System.out.println("\nSelect the man you want to remove: ");
							int x_remove = in.nextInt();
							int y_remove = in.nextInt();
							Point pt = new Point(x_remove, y_remove);
							if (p2.getMenOnTheBoard().contains(pt)) {
								game.removeAMan(1, pt);
								validRemove = true;
							} else {
								System.err.println("Invalid point to remove!");
							}
						}
					}
				} else {
					if(p1.getMenHoldInHand()==0) {
						System.out.println("Player1 to move a man: ");
						System.out.println("Available points:");
						for(Point pt : p1.getMenOnTheBoard()) {
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
						if (validMove && game.hasMills(0, newpt)) {
							System.out.println("Player1" + " has a MILL!");
							System.out.println("Ask Player1" + ": to remove a man of Player2");
							while (!validRemove) {
								System.out.println("You can remove one from: ");
								for(Point pt : p2.getMenOnTheBoard()) {
									System.out.print(pt);
								}
								System.out.println("\nSelect the man you want to remove: ");
								int x_remove = in.nextInt();
								int y_remove = in.nextInt();
								Point pt = new Point(x_remove, y_remove);
								if (p2.getMenOnTheBoard().contains(pt)) {
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
			// helpful info.
			for(Point pt : p1.getMenOnTheBoard()) {
				System.out.print(pt);
			}
			System.out.println("");
			for(Point pt : p2.getMenOnTheBoard()) {
				System.out.print(pt);
			}
			System.out.println("");
			System.out.println("Player2's turn.");
			// Player2;
			if (game.endOfGame())
				break;
			while (!validMove) {
				if(p2.getMenHoldInHand()>0) {
					System.out.println("Player2 to place a man at point: ");
					int x_coor = in.nextInt();
					int y_coor = in.nextInt();
					Point newpt = new Point(x_coor, y_coor);
					validMove = game.makeAnAction(null, newpt, 1);
					if (validMove && game.hasMills(1, newpt)) {
						System.out.println("Player2" + " has a MILL!");
						System.out.println("Ask Player2" + ": to remove a man of Player1");
						while (!validRemove) {
							System.out.println("You can remove one from: ");
							for(Point pt : p1.getMenOnTheBoard()) {
								System.out.print(pt);
							}
							System.out.println("\nSelect the man you want to remove: ");
							int x_remove = in.nextInt();
							int y_remove = in.nextInt();
							Point pt = new Point(x_remove, y_remove);
							if (p1.getMenOnTheBoard().contains(pt)) {
								game.removeAMan(0, pt);
								validRemove = true;
							} else {
								System.err.println("Invalid point to remove!");
							}
						}
					}
				} else {
					if(p2.getMenHoldInHand()==0) {
						System.out.println("Player2 to move a man: ");
						// list available points.
						System.out.println("Available points:");
						for(Point pt : p2.getMenOnTheBoard()) {
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
						if (validMove && game.hasMills(1, newpt)) {
							System.out.println("Player2" + " has a MILL!");
							System.out.println("Ask Player2" + ": to remove a man of Player1");
							while (!validRemove) {
								System.out.println("You can remove one from: ");
								for(Point pt : p1.getMenOnTheBoard()) {
									System.out.print(pt);
								}
								System.out.println("\nSelect the man you want to remove: ");
								int x_remove = in.nextInt();
								int y_remove = in.nextInt();
								Point pt = new Point(x_remove, y_remove);
								if (p1.getMenOnTheBoard().contains(pt)) {
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
		if (p1.lose())
			System.out.println("Black Wins");
		else
			System.out.println("White Wins");
		in.close();
	}
}
