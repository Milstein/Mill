package nineMM;

public abstract class IModel {

	static protected final int xMax = 6; 
	static protected final int xMin = 0; 
	static protected final int yMax = 6; 
	static protected final int yMin = 0; 
	
	abstract protected void init(); 
	abstract protected boolean setMarble(Coor coor, Marble marble); 
	abstract protected Marble getMarble(Coor coor); 
}
