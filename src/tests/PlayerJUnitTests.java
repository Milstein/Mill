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
	
	@Test
	public void testRemoveAMan() {
		System.out.println("---------TestRemoveAMan------------------");
		Player p1 = new Player("Milson", 0);
		System.out.println(p1);
		assertTrue(p1.getMenHoldInHand()==9);
		p1.removeAMan(new Point(6,6)); // non-existence.
		assertTrue(p1.getMenHoldInHand()==9);
		assertFalse(p1.getMenOnTheBoard().contains(new Point(6,6)));
		p1.placeAMan(new Point(0,0));
		assertTrue(p1.getMenHoldInHand()==8);
		assertTrue(p1.getMenOnTheBoard().contains(new Point(0,0)));
		p1.removeAMan(new Point(0,0)); // non-existence.
		assertTrue(p1.getMenHoldInHand()==8); // we don't let this go back.
		assertFalse(p1.getMenOnTheBoard().contains(new Point(0,0)));
		
		p1.placeAMan(new Point(3,0));
		assertTrue(p1.getMenHoldInHand()==7);
		p1.placeAMan(new Point(6,0));
		assertTrue(p1.getMenHoldInHand()==6);
		p1.placeAMan(new Point(1,1));
		assertTrue(p1.getMenHoldInHand()==5);
		System.out.println(p1);
		p1.removeAMan(new Point(1,1));
		assertTrue(p1.getMenHoldInHand()==5);
		assertTrue(p1.getMenOnTheBoard().contains(new Point(3,0)));
		assertTrue(p1.getMenOnTheBoard().contains(new Point(6,0)));
		assertFalse(p1.getMenOnTheBoard().contains(new Point(1,1)));
		
		System.out.println("---------TestRemoveAMan------------------");
	}

	@Test
	public void testAllowToFly() {
		System.out.println("---------TestAllowToFly------------------");
		Player p1 = new Player("Arthur", 1);
		System.out.println(p1);
		assertTrue(p1.getMenHoldInHand()==9);
		assertFalse(p1.allowToFly());
		// place-------------
		p1.placeAMan(new Point(0,0));		
		assertTrue(p1.getMenHoldInHand()==8);
		assertFalse(p1.allowToFly());		
		p1.placeAMan(new Point(3,0));
		assertTrue(p1.getMenHoldInHand()==7);
		assertFalse(p1.allowToFly());
		p1.placeAMan(new Point(6,0));
		assertTrue(p1.getMenHoldInHand()==6);
		assertFalse(p1.allowToFly());
		p1.placeAMan(new Point(1,1));
		assertTrue(p1.getMenHoldInHand()==5);
		assertFalse(p1.allowToFly());
		p1.placeAMan(new Point(3,1));
		assertTrue(p1.getMenHoldInHand()==4);
		assertFalse(p1.allowToFly());
		p1.placeAMan(new Point(5,1));
		assertTrue(p1.getMenHoldInHand()==3);
		assertFalse(p1.allowToFly());
		p1.placeAMan(new Point(2,2));
		assertTrue(p1.getMenHoldInHand()==2);
		assertFalse(p1.allowToFly());
		p1.placeAMan(new Point(2,3));
		assertTrue(p1.getMenHoldInHand()==1);
		assertFalse(p1.allowToFly());
		p1.placeAMan(new Point(2,4));
		assertTrue(p1.getMenHoldInHand()==0);
		assertFalse(p1.allowToFly());
		// remove-------------
		p1.removeAMan(new Point(6,0));
		assertTrue(p1.getMenHoldInHand()==0);
		assertFalse(p1.allowToFly());
		p1.removeAMan(new Point(1,1));
		assertTrue(p1.getMenHoldInHand()==0);
		assertFalse(p1.allowToFly());
		p1.removeAMan(new Point(3,1));
		assertTrue(p1.getMenHoldInHand()==0);
		assertFalse(p1.allowToFly());
		p1.removeAMan(new Point(5,1));
		assertTrue(p1.getMenHoldInHand()==0);
		assertFalse(p1.allowToFly());
		p1.removeAMan(new Point(2,2));
		assertTrue(p1.getMenHoldInHand()==0);
		assertFalse(p1.allowToFly());
		p1.removeAMan(new Point(2,3));
		assertTrue(p1.getMenHoldInHand()==0);
		assertTrue(p1.allowToFly());
		System.out.println(p1);
		
		p1.removeAMan(new Point(2,4));
		assertTrue(p1.getMenHoldInHand()==0);
		assertFalse(p1.allowToFly()); // actually, died.
		System.out.println(p1);
		
		System.out.println("---------TestAllowToFly------------------");
	}
	
	@Test
	public void testHasLegalMove() {
		//TODO
	}
	
}
