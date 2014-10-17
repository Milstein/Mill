package game;

import java.awt.Point;
import java.util.ArrayList;

public class Node {

	private ArrayList <Node> neighbours = new ArrayList<Node>();
	private String id = "";
	public String getId() {
		return id;
	}

	private int isBusy = 0;			// 0 = Empty	1 = White	2 = Black
	public Point location;
	
	public Node (String id, int x, int y) {
		this.id = id;
		location = new Point(x, y);
	}	
	
	public void setIsBusy(int state){
		isBusy = state;
	}
	
	public int getIsBusy() {
		return isBusy;
	}
}
