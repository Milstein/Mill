package com.gui;

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
	private boolean unchangedFlag = true;
	
	public Node (String id, int x, int y) {
		this.id = id;
		location = new Point(x, y);
	}
	
	public void addNeighbour(Node k){
		neighbours.add(k);
	}
	
	public ArrayList<Node> getNeighbours(){
		return neighbours;
	}
	
	public void setIsBusy(int state){
		isBusy = state;
	}
	
	public int getIsBusy() {
		return isBusy;
	}
	
	public boolean getUnchangedFlag() {
		return unchangedFlag;
	}
	public void setUnchangedFlag(boolean check) {
		unchangedFlag = check;
	}
	
}
