package com.ai;

import java.util.ArrayList;

import com.gui.Node;

public class StateOfPlay {
	public Node[] nodes = new Node[24];
	private ArrayList<Node> mynodes = new ArrayList<Node>();
	private ArrayList<Node> enemynodes = new ArrayList<Node>();
	private ArrayList<Node> freenodes = new ArrayList<Node>();

	private int level;
	private int value;
	private int type;
	private int depth;
	private int player;

	private StateOfPlay parent = null;
	private ArrayList<StateOfPlay> children = new ArrayList<StateOfPlay>();
	private Node[] originMove = new Node[2];
	private Node rememberDeletion = null;

	public StateOfPlay(StateOfPlay parent, Node[] nodes2copy,
			Node[] originMove, int type, int level, int depth) {
		this.parent = parent;
		this.type = type;
		this.level = level;
		this.depth = depth;

		if (level % 2 == 0) {
			player = type;
		} else {
			if (type == 1) {
				player = 2;
			} else {
				player = 1;
			}
		}

		if (originMove != null) {
			this.originMove[0] = originMove[0];
			this.originMove[1] = originMove[1];
		}

		for (int i = 0; i < nodes2copy.length; i++) {			
			Node newnode = new Node(nodes2copy[i].getId(),nodes2copy[i].location.x, nodes2copy[i].location.y);
			this.nodes[i] = newnode;
			this.nodes[i].setIsBusy(nodes2copy[i].getIsBusy());
			this.nodes[i].setUnchangedFlag(nodes2copy[i].getUnchangedFlag());
		}
		
		for (int i = 0; i < nodes2copy.length; i++){			
			for(Node neighbour : nodes2copy[i].getNeighbours()){
				for(int j = 0; j< nodes.length ; j++){
					if(nodes[j].getId().contains(neighbour.getId())){
						nodes[i].addNeighbour(nodes[j]);
					}
				}
				
			}
		}
	}


	public void generatePossibilities() {
		// System.out.println("---generating--level -" + (level+1) +
		// "----------------------");
		if (depth >= 0) {
			for (int i = 0; i < this.nodes.length; i++) {
				if (this.nodes[i].getIsBusy() == player) {
					for (Node potential : this.nodes[i].getNeighbours()) {
						if (potential.getIsBusy() == 0) {
							for (int j = 0; j < this.nodes.length; j++) {
								if (nodes[j].getId().contains(potential.getId())) {
									
									Node[] nextmove = new Node[2];
									nodes[i].setIsBusy(0);
									nodes[j].setIsBusy(player);
									nextmove[0] = nodes[i];
									nextmove[1] = nodes[j];

									Node remember = null;
				
									if (checkNodeInMill(nodes[j], i)) {
										System.out.println("I recognized the mill");
										remember = findForDeletion();							
										analyzeNodes();
									}

									int turn;
									if (type == 1) {
										turn = 2;
									} else {
										turn = 1;
									}
									
									// System.out.println("---generating level "+(this.level+1)+"----");
									StateOfPlay strategy = new StateOfPlay(
											this, this.nodes, nextmove, type,
											this.level + 1, depth - 1);

									if (remember != null) {
										strategy.setRememberDeletion(remember);
									}
									//evaluateStrategy(strategy);																			
									this.children.add(strategy);
									strategy.generatePossibilities();

									if (strategy.getRememberDeletion() != null) {
										System.out.println("I recognized a mill for"+ strategy.getOriginMove()[0].getId()
														+ " to "
														+ strategy
																.getOriginMove()[1]
																.getId());
										if(this.type==this.player){
											strategy.setValue(strategy.getValue() + (1000 / (strategy.level)));
										}
										else{
											strategy.setValue(strategy.getValue() - (1000 / (strategy.level)));
										}
										remember.setIsBusy(turn);
									}
									
									nodes[i].setIsBusy(player);
									nodes[j].setIsBusy(0);
								}
							}
						}
					}
				}
			}
		} else {
			StateOfPlay x = this;
			while (x.level != 0) {
				if (x.getPlayer() == x.getType()) {
					for (StateOfPlay y : x.getChildren()) {
						x.setValue(x.getValue() + y.getValue());
					}
				} else {
					for (StateOfPlay y : x.getChildren()) {
						x.setValue(x.getValue() + y.getValue());
					}
				}
				x = x.getParent();
			}
		}
	}

//	private void evaluateStrategy(StateOfPlay strategy) {		
//		for(Node n : nodes){
//			for(Node m : n.getNeighbours()){
//				if(m.getIsBusy()==type){
//					strategy.setValue(strategy.getValue()+2);
//				}else{
//					if(m.getIsBusy()==0){
//						if(player  == type){
//							strategy.setValue(strategy.getValue()-1);							
//						}else{
//							strategy.setValue(strategy.getValue()+1);
//						}						
//				}else{
//					strategy.setValue(strategy.getValue()-2);
//				}					
//				}
//			}
//		}
//	}


	private Node findForDeletion() {
		analyzeNodes();
		Node selectedNode = null;
		for (Node n : enemynodes) {
			selectedNode = n;
			for (Node m : n.getNeighbours()) {
				if (enemynodes.contains(m)) {
					selectedNode = m;
					break;
				}
				if (freenodes.contains(m)){
					for(Node last : m.getNeighbours()){
						if(enemynodes.contains(last) && ((last.location.x == n.location.x)||(last.location.y == n.location.y))){
							selectedNode = last;
							break;
						}
					}
				}
			}
		}
		
		return selectedNode;
	}

	private void analyzeNodes() {
		freenodes.clear();
		mynodes.clear();
		enemynodes.clear();
		for (int i = 0; i < this.nodes.length; i++) {
			if (nodes[i].getIsBusy() == 0) {
				this.freenodes.add(nodes[i]);
				continue;
			}
			if (nodes[i].getIsBusy() == player) {
				this.mynodes.add(nodes[i]);
				continue;
			}
			this.enemynodes.add(nodes[i]);
		}
		
	}

	private boolean checkNodeInMill(Node node, int p) {
		
		System.out.println("---------------------------------");

		System.out.println("we wanted to move " + nodes[p].getId() +","+nodes[p].getIsBusy()+ " to: "+node.getId() + ","+node.getIsBusy());

		analyzeNodes();
		
		for (Node n : mynodes) {
			for (Node m : n.getNeighbours()) {				
				if (m.getIsBusy() == n.getIsBusy()
						&& (m.location.x == n.location.x || m.location.y == n.location.y)) {
					for (Node f : m.getNeighbours()) {
						if (f.getIsBusy() == m.getIsBusy()
								&& (f.location.x == m.location.x
										&& (f.location.x == n.location.x) || f.location.y == m.location.y
										&& (f.location.y == n.location.y))
								&& !f.equals(n)){							
							if(n.getId().contains(node.getId()) || m.getId().contains(node.getId()) || f.getId().contains(node.getId())){								
								return true;
							}
						}							
					}
				}

			}
		}
		return false;
	}

	public boolean deleteSomething() {
		if (rememberDeletion == null) {
			return false;
		} else {
			return true;
		}
	}

	public StateOfPlay calculateBestEffort() {
		StateOfPlay result = null;
		int maxval = this.children.get(0).getValue();
		for (StateOfPlay child : this.children) {
			if (child.value >= maxval) {
				maxval = child.value;
				result = child;
			}
		}
		return result;
	}

	
	public Node getRememberDeletion() {
		return rememberDeletion;
	}

	public void setRememberDeletion(Node rememberDeletion) {
		this.rememberDeletion = rememberDeletion;
	}

	public int getType() {
		return type;
	}

	public int getPlayer() {
		return player;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public StateOfPlay getParent() {
		return parent;
	}

	public void setParent(StateOfPlay parent) {
		this.parent = parent;
	}

	public ArrayList<StateOfPlay> getChildren() {
		return children;
	}

	public void setChildren(ArrayList<StateOfPlay> children) {
		this.children = children;
	}

	public Node[] getOriginMove() {
		return originMove;
	}

	public void setOriginMove(Node[] originMove) {
		this.originMove = originMove;
	}

}
