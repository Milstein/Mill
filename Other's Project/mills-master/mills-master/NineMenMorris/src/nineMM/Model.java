package nineMM;

import nineMM.Marble.MarbleType;

public class Model extends IModel {

	private  Marble[][] marbles = new Marble[xMax + 1][yMax + 1]; 
	
	protected Model()
	{
		this.init();
	}
	
	@Override
	protected void init() {
		for(int i = 0; i < xMax; i++)
			for (int j = 0; j < yMax; j++)
				marbles[i][j] = new Marble(MarbleType.EMPTY); 
	}

	@Override
	protected boolean setMarble(Coor coor, Marble marble) {
		if( (coor == null) || (marble == null)) return false;
		
		marbles[coor.getX()][coor.getY()] = marble;  
		return true;
	}

	@Override
	protected Marble getMarble(Coor coor) {
		if(coor==null) return null;
		
		return marbles[coor.getX()][coor.getY()];
	}

}
