package com.ai;

import java.util.ArrayList;

import com.gui.NineMM;
import com.gui.Node;

public class Brain {

	private NineMM game;

	public Brain(NineMM game) {
		this.game = game;
	}

	public void setStone(Node[] nodes, int type) {
		ArrayList<Node> mynodes = new ArrayList<Node>();
		ArrayList<Node> enemynodes = new ArrayList<Node>();
		ArrayList<Node> freenodes = new ArrayList<Node>();
		boolean mill_created = false;

		Node selectedNode = null;
		
		for (Node n : nodes) {
			if (n.getIsBusy() == 0) {
				freenodes.add(n);
				continue;
			}
			if (n.getIsBusy() == type) {
				mynodes.add(n);
				continue;
			}
			enemynodes.add(n);
		}

		selectedNode = freenodes.get((int) (Math.random() * freenodes.size()));

		
		// check for max freenodes
		Node goodChoice = findMaxFreeNodes(freenodes);
		if (goodChoice != null)
			selectedNode = goodChoice;
		
		// if there is a free place beneath my token, take it
		for (Node n : mynodes) {
			for (Node m : n.getNeighbours()) {
				if (freenodes.contains(m)) {
					for(Node l : m.getNeighbours()){
						if(freenodes.contains(l) && (l.location.x == n.location.x || l.location.y == m.location.y)){
							selectedNode = m;
						}
					}					
				}
			}
		}
		
		for (Node n : enemynodes) {
			for (Node m : n.getNeighbours()) {
				if (freenodes.contains(m)) {
					for(Node l : m.getNeighbours()){
						if(freenodes.contains(l) && ((l.location.x == m.location.x && l.location.x == n.location.x)||(l.location.y == m.location.y && l.location.y == n.location.y))){
							for(Node k : l.getNeighbours()){
								if(freenodes.contains(k) && !k.getId().contains(m.getId())){
									for(Node j : k.getNeighbours()){
										if(enemynodes.contains(j) && ((j.location.x == k.location.x && j.location.x == l.location.x)||(j.location.y == k.location.y && j.location.y == l.location.y))){
											selectedNode = l;
										}
									}									
								}
							}
						}
					}
				}
			}
		}
		
		// check if the enemy has a mill draw
		goodChoice = findMillDraw(enemynodes, freenodes);
		if (goodChoice != null)
			selectedNode = goodChoice;

		// check if we have a mill draw
		goodChoice = findMillDraw(mynodes, freenodes);
		if (goodChoice != null){
			selectedNode = goodChoice;
			mill_created = true;
		}
			
		
		//System.out.println("AI: OK, I want to set " + selectedNode.getId());
		game.setStones(selectedNode.location);
		
		if(mill_created){
			deleteStone(nodes,type);
		}
		
	}

	public void moveStone(Node[] nodes, int type) {				
		Node[] move = new Node[2];
		
		StateOfPlay currentStateOfPlay = new StateOfPlay(null, nodes,null, type,0,1);		
		currentStateOfPlay.generatePossibilities();	
		
		for(StateOfPlay play : currentStateOfPlay.getChildren()){
			System.out.println(play.getOriginMove()[0].getId() + " to "+ play.getOriginMove()[1].getId()+",val "+play.getValue());
			if(play.getRememberDeletion()!=null){
				System.out.println(play.getRememberDeletion().getId());	
			}			
		}
		
		StateOfPlay potentialStateOfPlay = currentStateOfPlay.calculateBestEffort();		
		move = potentialStateOfPlay.getOriginMove();
		
		//System.out.println("I want to move from " + move[0].getId() + " to " + move[1].getId());		
		
		game.doSomething(move[0].location);
		game.setStones(move[1].location);		
		
		if(potentialStateOfPlay.deleteSomething()){
			deleteStone(nodes,type);
		}
		
	}

	public void jumpStone(Node[] nodes, int type) {
		System.out.println("AI: Jump, jump");
		ArrayList<Node> mynodes = new ArrayList<Node>();
		ArrayList<Node> enemynodes = new ArrayList<Node>();
		ArrayList<Node> freenodes = new ArrayList<Node>();
		boolean mill_created = false;

		Node sourceNode = null;
		Node destinationNode = null;
		
		for (Node n : nodes) {
			if (n.getIsBusy() == 0) {
				freenodes.add(n);
				continue;
			}
			if (n.getIsBusy() == type) {
				mynodes.add(n);
				continue;
			}
			enemynodes.add(n);
		}

		int num_neigh_glob = 4;
		for(Node n : mynodes){
			int num_neig_loc = 0;
			for(Node m : n.getNeighbours()){
				if(m.getIsBusy()==n.getIsBusy()){
					num_neig_loc++;
				}
			}
			if(num_neig_loc<num_neigh_glob){
				num_neigh_glob = num_neig_loc;
				sourceNode = n;
			}
		}
		
		destinationNode = freenodes.get((int) (Math.random() * freenodes.size()));
		
		// if there is a free place beneath my token, take it
		for (Node n : mynodes) {
			for (Node m : n.getNeighbours()) {
				if (freenodes.contains(m)) {
					destinationNode = m;
				}
			}
		}
		
		// check for max freenodes
		Node goodChoice = findMaxFreeNodes(freenodes);
		if (goodChoice != null)
			destinationNode = goodChoice;
		
		for (Node n : enemynodes) {
			for (Node m : n.getNeighbours()) {
				if (freenodes.contains(m)) {
					for(Node l : m.getNeighbours()){
						if(freenodes.contains(l) && ((l.location.x == m.location.x && l.location.x == n.location.x)||(l.location.y == m.location.y && l.location.y == n.location.y))){
							for(Node k : l.getNeighbours()){
								if(freenodes.contains(k) && !k.getId().contains(m.getId())){
									for(Node j : k.getNeighbours()){
										if(enemynodes.contains(j) && ((j.location.x == k.location.x && j.location.x == l.location.x)||(j.location.y == k.location.y && j.location.y == l.location.y))){
											destinationNode = l;
										}
									}									
								}
							}
						}
					}
				}
			}
		}
		
		for (Node n : enemynodes) {
			for (Node m : n.getNeighbours()) {
				if (freenodes.contains(m)) {
					for(Node k : m.getNeighbours()){
						if(enemynodes.contains(k)){
							destinationNode = m;
						}
					}
				}
			}
		}
		
		// check if the enemy has a mill draw
		goodChoice = findMillDraw(enemynodes, freenodes);
		if (goodChoice != null)
			destinationNode = goodChoice;

		// check if we have a mill draw
		goodChoice = findMillDraw(mynodes, freenodes);
		if (goodChoice != null){
			destinationNode = goodChoice;
			mill_created = true;
		}
			
		
		//System.out.println("AI: OK, I want to set " + selectedNode.getId());
		game.doSomething(sourceNode.location);
		game.setStones(destinationNode.location);
		
		if(mill_created){
			deleteStone(nodes,type);
		}
	}

	public void deleteStone(Node[] nodes, int type) {

		ArrayList<Node> mynodes = new ArrayList<Node>();
		ArrayList<Node> enemynodes = new ArrayList<Node>();
		ArrayList<Node> freenodes = new ArrayList<Node>();
		Node selectedNode = null;

		for (Node n : nodes) {
			if (n.getIsBusy() == 0) {
				freenodes.add(n);
				continue;
			}
			if (n.getIsBusy() == type) {
				mynodes.add(n);
				continue;
			}
			enemynodes.add(n);
		}

		for (Node n : enemynodes) {
			selectedNode = n;
			for (Node m : n.getNeighbours()) {
				if (m.getIsBusy() == 1) {
					selectedNode = m;
					break;
				}
			}
		}
		
		for (Node n : enemynodes) {
			for (Node m : n.getNeighbours()) {
				if (freenodes.contains(m)) {
					for(Node l : m.getNeighbours()){
						if(enemynodes.contains(l) && ((l.location.x == m.location.x && l.location.x == n.location.x)||(l.location.y == m.location.y && l.location.y == n.location.y))){
							for(Node k : l.getNeighbours()){
								if(freenodes.contains(k) && !k.getId().contains(m.getId())){
									for(Node j : k.getNeighbours()){
										if(enemynodes.contains(j) && ((j.location.x == k.location.x && j.location.x == l.location.x)||(j.location.y == k.location.y && j.location.y == l.location.y))){
											selectedNode = l;
										}
									}									
								}
							}
						}
					}
				}
			}
		}

		System.out.println("I want to delete " + selectedNode.getId());
		
		game.doSomething(selectedNode.location);
		
	}
	
	private Node findMaxFreeNodes(ArrayList<Node> nodes){
		int maxval = 0;
		Node result = null;
		
		for(Node n : nodes){
			int num = 0;
			for(Node f : n.getNeighbours()){
				if(f.getIsBusy() == 0){
					num++;	
				}				
			}
			if(num>=maxval){
				maxval = num;
				result = n;
			}
		}
		return result;			
	}

	private Node findMillDraw(ArrayList<Node> busynodes,
			ArrayList<Node> freenodes) {
		Node selectedNode = null;
		for (Node n : busynodes) {
			for (Node m : n.getNeighbours()) {
				if (busynodes.contains(m)) {
					for (Node f : m.getNeighbours()) {
						if (freenodes.contains(f)
								&& ((f.location.x == m.location.x && m.location.x == n.location.x) || (f.location.y == m.location.y && m.location.y == n.location.y))) {
							selectedNode = f;
						}
					}
				} else {
					if (freenodes.contains(m)) {
						for (Node f : m.getNeighbours()) {
							if (busynodes.contains(f)
									&& !f.equals(n)
									&& ((f.location.x == m.location.x && m.location.x == n.location.x) || (f.location.y == m.location.y && m.location.y == n.location.y))) {
								selectedNode = m;
							}
						}
					}
				}
			}
		}

		return selectedNode;
	}
	
}
