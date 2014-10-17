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

	// TODO: consider x=3 or y=3.
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
		int originalX = x;
		int originalY = y;

		// x+ direction:
		while (x < MAXX) {
			// check if x=2 and y=3, we cannot go to the right.
			if(x==2 && y==3)
				break;
			x++;
			if (Board.validPoints.contains(new Point(x,y))) {
				adjacent.add(new Point(x,y));
				break;
			}
		}
		x = originalX;
		// x- direction:
		while (x > MINX) {
			// check if x=4 and y=3, we cannot go to the left.
			if(x==4 && y==3)
				break;
			x--;
			if (Board.validPoints.contains(new Point(x,y))) {
				adjacent.add(new Point(x,y));
				break;
			}
		}
		x = originalX;
		// y+ direction: down
		while (y < MAXY) {
			// check if x=3 and y=2, we cannot go down.
			if (x==3 && y==2) {
				break;
			}
			y++;
			if (Board.validPoints.contains(new Point(x,y))) {
				adjacent.add(new Point(x,y));
				break;
			}
		}
		y = originalY;
		// y- direction: up
		while (y > MINY) {
			// check if x=3 and y=4, we cannot go down.
			if (x==3 && y==4)
				break;
			y--;
			if (Board.validPoints.contains(new Point(x,y))) {
				adjacent.add(new Point(x,y));
				break;
			}
		}
		y = originalY;
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
		for(Point p : Board.validPoints) {
			System.out.print("The adjacent points of " + p + " is: ");
			for(Point pt : p.getAdjacentPoints())
				System.out.print(pt);
			System.out.println();
		}
	}
}
