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
		
		/**
		 * Search for adjacent points
		 * @return a set contains adjacent points.
		 */
		public Set<Point> getAdjacentPoints() {
			Set<Point> adjacent = new HashSet<Point>();
			// Search in 4 directions: We are done each branch if 
			// we run out of boundary or have found a valid point.
			//----------------------------------------
			int originalX = x;
			int originalY = y;
			// x+ direction:
			while(x<MAXX) {
				x++;
				if(Board.validPoints.contains(new Point(x,y,false))) {
					adjacent.add(new Point(x,y,false));
					break;
				}
			}
			x = originalX;
			// x- direction:
			while(x>MINX) {
				x--;
				if(Board.validPoints.contains(new Point(x,y,false))) {
					adjacent.add(new Point(x,y,false));
					break;
				}
			}
			x = originalX;
			// y+ direction:
			while(y<MAXY) {
				y++;
				if(Board.validPoints.contains(new Point(x,y,false))) {
					adjacent.add(new Point(x,y,false));
					break;
				}
			}
			y = originalY;
			// y- direction:
			while(y>MINY) {
				y--;
				if(Board.validPoints.contains(new Point(x,y,false))) {
					adjacent.add(new Point(x,y,false));
					break;
				}
			}
			y = originalY;
			return adjacent;
		}
}
