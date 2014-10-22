package tests;

import static org.junit.Assert.*;
import game.Player;
import game.Point;

import org.junit.Test;

public class PlayerJUnitTests {

	@Test
	public void testShowPlayer() {
		System.out.println("---------TestShowPlayer------------------");
		Player p1 = new Player("Jimmy", 0);
		System.out.println(p1);
		p1.placeAMan(new Point(0,0));
		System.out.println(p1);
		p1.placeAMan(new Point(3,0));
		System.out.println(p1);
		p1.placeAMan(new Point(6,0));
		System.out.println(p1);
		// Player level does not check validity of move. so we can move points
		// even if menHoldInHand is not 0.
		p1.moveAMan(new Point(6,0), new Point(0,3));
		System.out.println(p1);
		p1.moveAMan(new Point(3,0), new Point(0,6));
		System.out.println(p1);
		System.out.println("---------TestShowPlayer------------------");
	}
	
	@Test
	public void testPlaceAMan() {
		System.out.println("---------TestPlaceAMan------------------");
		Player p1 = new Player("Milson", 0);
		System.out.println(p1);
		assertTrue(p1.getMenHoldInHand()==9);
		p1.placeAMan(new Point(0,0));
		assertTrue(p1.getMenHoldInHand()==8);
		p1.placeAMan(new Point(3,0));
		assertTrue(p1.getMenHoldInHand()==7);
		p1.placeAMan(new Point(6,0));
		assertTrue(p1.getMenHoldInHand()==6);
		p1.placeAMan(new Point(1,1));
		assertTrue(p1.getMenHoldInHand()==5);
		System.out.println(p1);
		// An arrayList can have duplicate; in our game, this issue is checked in the Board class.
		p1.placeAMan(new Point(1,1));
		System.out.println(p1);
		assertTrue(p1.getMenHoldInHand()==4);
		assertTrue(p1.getMenOnTheBoard().contains(new Point(0,0)));
		assertTrue(p1.getMenOnTheBoard().contains(new Point(3,0)));
		assertTrue(p1.getMenOnTheBoard().contains(new Point(6,0)));
		assertTrue(p1.getMenOnTheBoard().contains(new Point(1,1)));
		
		System.out.println("---------TestPlaceAMan------------------");
	}
	
	@Test
	public void testMoveAMan() {
		System.out.println("---------TestMoveAMan------------------");
		Player p1 = new Player("Arthur", 1);
		System.out.println(p1);
		assertTrue(p1.getMenHoldInHand()==9);
		p1.placeAMan(new Point(0,0));
		System.out.println(p1);
		
		assertTrue(p1.getMenHoldInHand()==8);
		assertTrue(p1.getMenOnTheBoard().contains(new Point(0,0)));
		assertFalse(p1.getMenOnTheBoard().contains(new Point(1,1)));
		p1.moveAMan(new Point(0,0), new Point(1,1));
		System.out.println(p1);
		
		assertTrue(p1.getMenHoldInHand()==8);
		assertTrue(p1.getMenOnTheBoard().contains(new Point(1,1)));
		assertFalse(p1.getMenOnTheBoard().contains(new Point(0,0)));
		
		p1.placeAMan(new Point(3,1));
		assertTrue(p1.getMenHoldInHand()==7);
		p1.placeAMan(new Point(6,0));
		assertTrue(p1.getMenHoldInHand()==6);
		p1.placeAMan(new Point(0,0));
		assertTrue(p1.getMenHoldInHand()==5);
		System.out.println(p1);
		
		p1.moveAMan(new Point(0,0), new Point(5,1));
		System.out.println(p1);
		assertTrue(p1.getMenHoldInHand()==5);
		assertFalse(p1.getMenOnTheBoard().contains(new Point(0,0)));
		assertTrue(p1.getMenOnTheBoard().contains(new Point(3,1)));
		assertTrue(p1.getMenOnTheBoard().contains(new Point(6,0)));
		assertTrue(p1.getMenOnTheBoard().contains(new Point(1,1)));
		assertTrue(p1.getMenOnTheBoard().contains(new Point(5,1)));
		
		System.out.println("---------TestMoveAMan------------------");
	}

}
