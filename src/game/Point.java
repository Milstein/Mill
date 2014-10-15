package game;

import java.util.HashSet;
import java.util.Set;

public class Point {

	private int x;
	private int y;
	static final int MINX = 0;
	static final int MINY = 0;
	static final int MAXX = 0;
	static final int MAXY = 0;

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
		return x + "" + y;
	}

	/**
	 * Search for adjacent points
	 * 
	 * @return a set contains adjacent points.
	 */
	public Set<String> getAdjacentPoints() {
		Set<String> adjacent = new HashSet<String>();
		// Search in 4 directions: We are done each branch if
		// we run out of boundary or have found a valid point.
		// ----------------------------------------
		int originalX = x;
		int originalY = y;
		// x+ direction:
		while (x < MAXX) {
			x++;
			if (Board.validPoints.contains(toString())) {
				adjacent.add(toString());
				break;
			}
		}
		x = originalX;
		// x- direction:
		while (x > MINX) {
			x--;
			if (Board.validPoints.contains(toString())) {
				adjacent.add(toString());
				break;
			}
		}
		x = originalX;
		// y+ direction:
		while (y < MAXY) {
			y++;
			if (Board.validPoints.contains(toString())) {
				adjacent.add(toString());
				break;
			}
		}
		y = originalY;
		// y- direction:
		while (y > MINY) {
			y--;
			if (Board.validPoints.contains(toString())) {
				adjacent.add(toString());
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
}
