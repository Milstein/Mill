package nineMM;

import static org.junit.Assert.*;


import org.junit.Test;

public class TestCoor {
	
	@Test
	public void testBoundaries()
	{
		Coor inboundaries = Coor.newCoor(3, 4); 
		assertEquals("Faild on boundaries, expected x-coordinated = 3", inboundaries.getX(), 3); 
		assertEquals("Faild on boundaries, expected y-coordinated = 4", inboundaries.getY(), 4);
		
		Coor outboundaries = Coor.newCoor(7, -7); 
		assertNull("Failed on boundaries: should return null if out of boudaries", outboundaries);
		
		outboundaries = Coor.newCoor(2, 8);  
		assertNull("Failed on boundaries: y-coordinate out side of boundaries", outboundaries);

		outboundaries = Coor.newCoor(9, 1);  
		assertNull("Failed on boundaries: x-coordinate out side of boundaries", outboundaries);
		
		inboundaries = Coor.newCoor("a2"); 
		assertEquals("Faild on boundaries, expected x-coordinated = 0", inboundaries.getX(), 0); 
		assertEquals("Faild on boundaries, expected y-coordinated = 1", inboundaries.getY(), 1);

		outboundaries = Coor.newCoor("g9"); 
		assertNull("Failed on boundaries: should return null if out of boudaries", outboundaries);
	
		outboundaries = Coor.newCoor("g"); 
		assertNull("Failed on boundaries: should return null if string isn't correct.", outboundaries);
	
	}
	
	
}
