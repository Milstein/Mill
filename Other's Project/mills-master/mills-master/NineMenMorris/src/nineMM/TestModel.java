package nineMM;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestModel {


	@Test
	public void testPlacement()
	{
		Marble marble = new Marble(Marble.MarbleType.NaM);
		Model model = new Model();
		Coor coor = Coor.newCoor(2,5);
		
		assertTrue("SOMEHOW return true; returned false, or a null coordinate or marble was passed.", model.setMarble(coor, marble));
		
		assertEquals("Placed marble type NaM, but another type was returned from the moedel.",model.getMarble(coor).getOwner(), Marble.MarbleType.NaM);
	
		assertFalse("Setting a fake marble at a real coordinate in the model was actually successful. Problem.",model.setMarble(coor, null));
	
		assertNull("Asking model for a marble at a fake (null) coor actually returned something. Problem.",model.getMarble(null));
	}

}
