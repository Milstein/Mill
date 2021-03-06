package game;

import java.util.HashSet;
import java.util.Set;

public class PointGame {

	private int x;
	private int y;
	static final int MINX = 0;
	static final int MINY = 0;
	static final int MAXX = 6;
	static final int MAXY = 6;

	/**
	 * Constructor.
	 * @param x
	 * @param y
	 */
	public PointGame(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * get X.
	 * @return
	 */
	public int getX() {
		return x;
	}

	/**
	 * get Y.
	 * @return
	 */
	public int getY() {
		return y;
	}
	
	/**
	 * return a string of the point.
	 */
	public String toString() {
		return "(" + x + "," + y + ")";
	}

	// Get neighbors in specific direction...
	/**
	 * Get right neighbor, if there is.
	 * @return
	 */
	public PointGame getRightNeighbor() {
		int x0 = x;
		int y0 = y;
		while (x0 < MAXX) {
			// check if x=2 and y=3, we cannot go to the right.
			if(x0==2 && y0==3)
				break;
			x0++;
			if (Board.validPoints.contains(new PointGame(x0,y0))) {
				return new PointGame(x0,y0);
			}
		}
		return null;
	}
	/**
	 * Get left neighbor.
	 * @return
	 */
	public PointGame getLeftNeighbor() {
		int x0 = x;
		int y0 = y;
		while (x0 > MINX) {
			// check if x=4 and y=3, we cannot go to the left.
			if(x0==4 && y0==3)
				break;
			x0--;
			if (Board.validPoints.contains(new PointGame(x0,y0))) {
				return new PointGame(x0,y0);
			}
		}
		return null;
	}
	/**
	 * Get down neighbor.
	 * @return
	 */
	public PointGame getDownNeighbor() {
		int x0 = x;
		int y0 = y;
		while (y0 < MAXY) {
			// check if x=3 and y=2, we cannot go down.
			if (x0==3 && y0==2) {
				break;
			}
			y0++;
			if (Board.validPoints.contains(new PointGame(x0,y0))) {
				return new PointGame(x0,y0);
			}
		}
		return null;
	}
	/**
	 * Get up neighbor.
	 * @return
	 */
	public PointGame getUpNeighbor() {
		int x0 = x;
		int y0 = y;
		while (y0 > MINY) {
			// check if x=3 and y=4, we cannot go down.
			if (x0==3 && y0==4)
				break;
			y0--;
			if (Board.validPoints.contains(new PointGame(x0,y0))) {
				return new PointGame(x0,y0);
			}
		}
		return null;
	}
	
	/**
	 * Search for adjacent points
	 * 
	 * @return a set contains adjacent points.
	 */
	public Set<PointGame> getAdjacentPoints() {
		Set<PointGame> adjacent = new HashSet<PointGame>();
		// Search in 4 directions: We are done each branch if
		// we run out of boundary or have found a valid point.
		// ----------------------------------------
		// x+ direction:
		PointGame rightNeighbor = getRightNeighbor();
		if(rightNeighbor!=null)
			adjacent.add(rightNeighbor);
		// x- direction:
		PointGame leftNeighbor = getLeftNeighbor();
		if(leftNeighbor!=null)
			adjacent.add(leftNeighbor);
		// y+ direction: down
		PointGame downNeighbor = getDownNeighbor();
		if(downNeighbor!=null)
			adjacent.add(downNeighbor);
		// y- direction: up
		PointGame upNeighbor = getUpNeighbor();
		if(upNeighbor!=null)
			adjacent.add(upNeighbor);
		
		return adjacent;
	}

	// Used for comparison of two points. just x1=x2 and y1=y2.
	@Override
	public int hashCode() {
		StringBuffer buffer = new StringBuffer();
		buffer.append(this.x);
		buffer.append(this.y);
		return buffer.toString().hashCode();
	}

	@Override
	public boolean equals(Object object) {
		if (object == null)
			return false;
		if (object == this)
			return true;
		if (this.getClass() != object.getClass())
			return false;
		PointGame point = (PointGame) object;
		if (this.hashCode() == point.hashCode())
			return true;
		return false;
	}
	
	// Test main
	public static void main(String[] args) {
		Board game = new Board("Player1", "Player2");
		game.init("White", "Black");
		for(PointGame p : Board.validPoints) {
			System.out.print("The adjacent points of " + p + " is: ");
			for(PointGame pt : p.getAdjacentPoints())
				System.out.print(pt);
			System.out.println();
		}
	}
}
