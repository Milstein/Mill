package tests;

import static org.junit.Assert.*;
import game.Board;
import game.Point;

import org.junit.Test;

public class PointJUnitTests {

	// Test point system: 0<=x<=6, 0<=y<=6;
	// 24 valid points enumerated in Board.java. 
	
	Board board = new Board("Player1", "Player2");
	
	@Test
	public void showAllAdjacentPoints() {
		// for every valid points show their adjacent valid points.
		for(Point p : Board.validPoints) {
			System.out.print("The adjacent points of " + p + " is: ");
			for(Point pt : p.getAdjacentPoints())
				System.out.print(pt);
			System.out.println();
		}
	}
	
	// My neighbor test is arithmetic manipulation: so just need to test half
	// the board because of symmetric.
	@Test
	public void testRightNeighbor() {
		assertTrue(new Point(0,0).getRightNeighbor().equals(new Point(3,0)));
		assertTrue(new Point(3,0).getRightNeighbor().equals(new Point(6,0)));
		assertTrue(new Point(6,0).getRightNeighbor()==null);
		assertTrue(new Point(1,1).getRightNeighbor().equals(new Point(3,1)));
		assertTrue(new Point(3,1).getRightNeighbor().equals(new Point(5,1)));
		assertTrue(new Point(5,1).getRightNeighbor()==null);
		assertTrue(new Point(2,2).getRightNeighbor().equals(new Point(3,2)));
		assertTrue(new Point(3,2).getRightNeighbor().equals(new Point(4,2)));
		assertTrue(new Point(4,2).getRightNeighbor()==null);
		assertTrue(new Point(0,3).getRightNeighbor().equals(new Point(1,3)));
		assertTrue(new Point(1,3).getRightNeighbor().equals(new Point(2,3)));
		assertTrue(new Point(2,3).getRightNeighbor()==null);
		assertTrue(new Point(4,3).getRightNeighbor().equals(new Point(5,3)));
		assertTrue(new Point(5,3).getRightNeighbor().equals(new Point(6,3)));
		assertTrue(new Point(6,3).getRightNeighbor()==null);
	}
	
	@Test
	public void testLeftNeighbor() {
		assertTrue(new Point(6,0).getLeftNeighbor().equals(new Point(3,0)));
		assertTrue(new Point(3,0).getLeftNeighbor().equals(new Point(0,0)));
		assertTrue(new Point(0,0).getLeftNeighbor()==null);
		assertTrue(new Point(5,1).getLeftNeighbor().equals(new Point(3,1)));
		assertTrue(new Point(3,1).getLeftNeighbor().equals(new Point(1,1)));
		assertTrue(new Point(1,1).getLeftNeighbor()==null);
		assertTrue(new Point(4,2).getLeftNeighbor().equals(new Point(3,2)));
		assertTrue(new Point(3,2).getLeftNeighbor().equals(new Point(2,2)));
		assertTrue(new Point(2,2).getLeftNeighbor()==null);
		assertTrue(new Point(6,3).getLeftNeighbor().equals(new Point(5,3)));
		assertTrue(new Point(5,3).getLeftNeighbor().equals(new Point(4,3)));
		assertTrue(new Point(4,3).getLeftNeighbor()==null);
		assertTrue(new Point(2,3).getLeftNeighbor().equals(new Point(1,3)));
		assertTrue(new Point(1,3).getLeftNeighbor().equals(new Point(0,3)));
		assertTrue(new Point(0,3).getLeftNeighbor()==null);
		
	}

	@Test
	public void testDownNeighbor() {
		assertTrue(new Point(0,0).getDownNeighbor().equals(new Point(0,3)));
		assertTrue(new Point(0,3).getDownNeighbor().equals(new Point(0,6)));
		assertTrue(new Point(0,6).getDownNeighbor()==null);
		assertTrue(new Point(1,1).getDownNeighbor().equals(new Point(1,3)));
		assertTrue(new Point(1,3).getDownNeighbor().equals(new Point(1,5)));
		assertTrue(new Point(1,5).getDownNeighbor()==null);
		assertTrue(new Point(2,2).getDownNeighbor().equals(new Point(2,3)));
		assertTrue(new Point(2,3).getDownNeighbor().equals(new Point(2,4)));
		assertTrue(new Point(2,4).getDownNeighbor()==null);
		assertTrue(new Point(3,0).getDownNeighbor().equals(new Point(3,1)));
		assertTrue(new Point(3,1).getDownNeighbor().equals(new Point(3,2)));
		assertTrue(new Point(3,2).getDownNeighbor()==null);
		assertTrue(new Point(3,4).getDownNeighbor().equals(new Point(3,5)));
		assertTrue(new Point(3,5).getDownNeighbor().equals(new Point(3,6)));
		assertTrue(new Point(3,6).getDownNeighbor()==null);
	}
	
	@Test
	public void testUpNeighbor() {
		assertTrue(new Point(0,6).getUpNeighbor().equals(new Point(0,3)));
		assertTrue(new Point(0,3).getUpNeighbor().equals(new Point(0,0)));
		assertTrue(new Point(0,0).getUpNeighbor()==null);
		assertTrue(new Point(1,5).getUpNeighbor().equals(new Point(1,3)));
		assertTrue(new Point(1,3).getUpNeighbor().equals(new Point(1,1)));
		assertTrue(new Point(1,1).getUpNeighbor()==null);
		assertTrue(new Point(2,4).getUpNeighbor().equals(new Point(2,3)));
		assertTrue(new Point(2,3).getUpNeighbor().equals(new Point(2,2)));
		assertTrue(new Point(2,2).getUpNeighbor()==null);
		assertTrue(new Point(3,6).getUpNeighbor().equals(new Point(3,5)));
		assertTrue(new Point(3,5).getUpNeighbor().equals(new Point(3,4)));
		assertTrue(new Point(3,4).getUpNeighbor()==null);
		assertTrue(new Point(3,2).getUpNeighbor().equals(new Point(3,1)));
		assertTrue(new Point(3,1).getUpNeighbor().equals(new Point(3,0)));
		assertTrue(new Point(3,0).getUpNeighbor()==null);
	}
	
}
