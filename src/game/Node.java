package game;

import java.awt.Point;
import java.util.ArrayList;

public class Node {

	private ArrayList<Node> neighbours = new ArrayList<Node>();
	private String id = "";

	public String getId() {
		return id;
	}

	private int isBusy = 0; // 0 = Empty 1 = White 2 = Black
	public Point location;
	public Point position;

	public Node(String id, int x, int y) {
		this.id = id;
		String[] result = id.split(",");
		if (result.length != 2) {
			throw new IllegalArgumentException("String not in correct format");
		} else {
			position = new Point(Integer.parseInt(result[0]),
					Integer.parseInt(result[1]));
		}
		location = new Point(x, y);
	}

	public Point getPosition() {
		return position;
	}

	public void setIsBusy(int state) {
		isBusy = state;
	}

	public int getIsBusy() {
		return isBusy;
	}
}
