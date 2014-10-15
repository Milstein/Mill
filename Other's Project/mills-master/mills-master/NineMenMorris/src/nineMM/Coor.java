package nineMM;

public class Coor {
	private int x,y;
	
	private Coor(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
	
	protected static Coor newCoor(int x, int y)
	{
		if( (x >= Model.xMin) && (x <= Model.xMax) )
			if( (y >= Model.yMin) && (y <= Model.yMax) )
				return new Coor(x,y);
		return null;
	}
	
	protected static Coor newCoor(String coor)
	{
		int x,y;
		
		if(coor == null) return null;
		if(coor.length() < 2) return null;
		
		switch(coor.charAt(0))
		{
			case 'a':
			case 'A': x = 0; break;
			
			case 'b':
			case 'B': x = 1; break;
			
			case 'c':
			case 'C': x = 2; break;
			
			case 'd':
			case 'D': x = 3; break;
			
			case 'e':
			case 'E': x = 4; break;
			
			case 'f':
			case 'F': x = 5; break;
			
			case 'g':
			case 'G': x = 6; break;
			
			default: return null;
		}
		
		switch(coor.charAt(1))
		{
			case '1': y = 0; break;
			case '2': y = 1; break;
			case '3': y = 2; break;
			case '4': y = 3; break;
			case '5': y = 4; break;
			case '6': y = 5; break;
			case '7': y = 6; break;
			default: return null;
		}
		
		return new Coor(x,y);
	}
	
	protected int getX() { return x; }
	
	protected int getY() { return y; }
}
