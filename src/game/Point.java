package game;

import java.util.HashSet;
import java.util.Set;

public class Point {
		private int x;
		private int y;
		private boolean occupied;
	
		Point(int x, int y, boolean occupied) {
			this.x = x;
			this.y = y;
			this.occupied = occupied;
		}
		
		public boolean isOccupied() {
			return occupied;
		}
		
		public int getX() {
			return x;
		}
		
		public int getY() {
			return y;
		}
		
		public Set<Point> getAdjacentPoints() {
			//Set<Point> adjacent = new HashSet<Point>();
			return null;
		}
}
