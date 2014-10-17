package game;

import java.util.HashSet;
import java.util.Set;

public class Point {

	private int x;
	private int y;
	static final int MINX = 0;
	static final int MINY = 0;
	static final int MAXX = 6;
	static final int MAXY = 6;

	Point(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
	public String toString() {
		return "(" + x + "," + y + ")";
	}

	// Get neighbors in specific direction...
	public Point getRightNeighbor(int x0, int y0) {
		while (x0 < MAXX) {
			// check if x=2 and y=3, we cannot go to the right.
			if(x0==2 && y0==3)
				break;
			x0++;
			if (Board.validPoints.contains(new Point(x0,y0))) {
				return new Point(x0,y0);
			}
		}
		return null;
	}
	public Point getLeftNeighbor(int x0, int y0) {
		while (x0 > MINX) {
			// check if x=4 and y=3, we cannot go to the left.
			if(x0==4 && y0==3)
				break;
			x0--;
			if (Board.validPoints.contains(new Point(x0,y0))) {
				return new Point(x0,y0);
			}
		}
		return null;
	}
	public Point getDownNeighbor(int x0, int y0) {
		while (y0 < MAXY) {
			// check if x=3 and y=2, we cannot go down.
			if (x0==3 && y0==2) {
				break;
			}
			y0++;
			if (Board.validPoints.contains(new Point(x0,y0))) {
				return new Point(x0,y0);
			}
		}
		return null;
	}
	public Point getUpNeighbor(int x0, int y0) {
		while (y0 > MINY) {
			// check if x=3 and y=4, we cannot go down.
			if (x0==3 && y0==4)
				break;
			y0--;
			if (Board.validPoints.contains(new Point(x0,y0))) {
				return new Point(x0,y0);
			}
		}
		return null;
	}
	
	/**
	 * Search for adjacent points
	 * 
	 * @return a set contains adjacent points.
	 */
	public Set<Point> getAdjacentPoints() {
		Set<Point> adjacent = new HashSet<Point>();
		// Search in 4 directions: We are done each branch if
		// we run out of boundary or have found a valid point.
		// ----------------------------------------
		// x+ direction:
		Point rightNeighbor = getRightNeighbor(x,y);
		if(rightNeighbor!=null)
			adjacent.add(rightNeighbor);
		// x- direction:
		Point leftNeighbor = getLeftNeighbor(x,y);
		if(leftNeighbor!=null)
			adjacent.add(leftNeighbor);
		// y+ direction: down
		Point downNeighbor = getDownNeighbor(x,y);
		if(downNeighbor!=null)
			adjacent.add(downNeighbor);
		// y- direction: up
		Point upNeighbor = getUpNeighbor(x,y);
		if(upNeighbor!=null)
			adjacent.add(upNeighbor);
		
		return adjacent;
	}

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
		Point point = (Point) object;
		if (this.hashCode() == point.hashCode())
			return true;
		return false;
	}
	
	// Test main
	public static void main(String[] args) {
		Board game = new Board("Player1", "Player2");
		game.toString();
		for(Point p : Board.validPoints) {
			System.out.print("The adjacent points of " + p + " is: ");
			for(Point pt : p.getAdjacentPoints())
				System.out.print(pt);
			System.out.println();
		}
	}
}
