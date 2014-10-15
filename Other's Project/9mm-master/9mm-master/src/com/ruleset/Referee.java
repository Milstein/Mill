package com.ruleset;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import com.gui.NineMM;
import com.gui.Node;

public class Referee {

	private boolean isWhitesTurn;
	private ArrayList<Node> checkOccupants = new ArrayList<Node>();
	private ArrayList<Node> whiteMills = new ArrayList<Node>();
	private ArrayList<Node> blackMills = new ArrayList<Node>();
	private NineMM game;

	public Referee(NineMM game) {
		this.game = game;
	}

	public boolean checkRules(Node[] nodes, boolean isWhitesTurn) {

		this.isWhitesTurn = isWhitesTurn;
		boolean check = checkMill(nodes);
		checkEndOfGame(nodes);
		return check;
	}

	public void checkEndOfGame(Node[] nodes) {
		
		if(game.getPlacedCounter() < 18){
			//System.out.println("Pieces placed: " + game.getPlacedCounter());
		}
		else{
			// check for 
			int countblackoptions = 0;
			int countwhiteoptions = 0;
			for(int i = 0; i < nodes.length; i++ ){
				for(Node n : nodes[i].getNeighbours()){
					if(n.getIsBusy()==0){
						if(nodes[i].getIsBusy()==1){
							countwhiteoptions++;
						}
						if(nodes[i].getIsBusy()==1){
							countblackoptions++;
						}
					}
				}
			}
			
			int countBlacks = 0;
			int countWhites = 0;
			
			for(Node n : nodes){
				if(n.getIsBusy() == 1){
					countWhites++;
				}
				else{
					if(n.getIsBusy() == 2){
						countBlacks++;
					}
				}
			}
						
			if((countblackoptions == 0) || (countwhiteoptions == 0) || (countBlacks < 3) || (countWhites < 3)){
				String winner = "";
				if((countblackoptions == 0) || (countWhites < 3)){
					winner = "Black";
				}
				else{
					winner = "White";
				}
				JOptionPane.showMessageDialog(null, "End of game: " + winner + " wins the game!");
				System.out.println("SPIELENDE");	
			}
		}
	}
	
	private boolean checkMill(Node[] nodes) {
		ArrayList<Node> whiteOccupants = new ArrayList<Node>();
		ArrayList<Node> blackOccupants = new ArrayList<Node>();
		int old_black_mills = 0;
		int old_white_mills = 0;

		for (int i = 0; i < nodes.length; i++) {
			if (nodes[i].getIsBusy() == 1)
				whiteOccupants.add(nodes[i]);
			else if (nodes[i].getIsBusy() == 2)
				blackOccupants.add(nodes[i]);
		}

		old_black_mills = this.blackMills.size();
		old_white_mills = this.whiteMills.size();
		
		if (isWhitesTurn) {
			checkOccupants = whiteOccupants;
			this.whiteMills.clear();
			//System.out.println("Erased the list to:" + whiteMills.size());
		} else {
			checkOccupants = blackOccupants;
			this.blackMills.clear();
			//System.out.println("Erased the list to:" + blackMills.size());
		}


		
//		System.out.println("We used to have " + old_black_mills
//				+ "nodes in black mills");
//		System.out.println("We used to have " + old_white_mills
//				+ "nodes in white mills");
		
		

		for (Node n : checkOccupants) {
			for (Node m : n.getNeighbours()) {
				if (checkOccupants.contains(m)
						&& (m.location.x == n.location.x || m.location.y == n.location.y)) {
					for (Node f : m.getNeighbours()) {
						if (checkOccupants.contains(f)
								&& (f.location.x == m.location.x
										&& (f.location.x == n.location.x) || f.location.y == m.location.y
										&& (f.location.y == n.location.y))
								&& !f.equals(n))							
							if (isWhitesTurn) {
								if (!this.whiteMills.contains(n))
									this.whiteMills.add(n);
								if (!this.whiteMills.contains(m))
									this.whiteMills.add(m);
								if (!this.whiteMills.contains(f))
									this.whiteMills.add(f);
							} else {
								if (!this.blackMills.contains(n))
									this.blackMills.add(n);
								if (!this.blackMills.contains(m))
									this.blackMills.add(m);
								if (!this.blackMills.contains(f))
									this.blackMills.add(f);
							}
					}
				}

			}
		}

		
//		System.out.println("now we have " + this.blackMills.size()
//				+ "nodes in black mills");
//		
//		System.out.println("now we have " + this.whiteMills.size()
//				+ "nodes in white mills");
		
		if (!isWhitesTurn && this.blackMills.size() > old_black_mills) {
			System.out.println("RF: There is a black mill!!!");
			return true;
		}

		if (isWhitesTurn && this.whiteMills.size() > old_white_mills) {
			System.out.println("RF: There is a white mill!!!");
			return true;
		}

		return false;

		// if (!isWhitesTurn) {
		// int whiteCheck = 0;
		// for (Node n : blackMills) {
		// System.out.println("in black mills: " + n.getId());
		// if (n.getUnchangedFlag()) {
		// whiteCheck++;
		// n.setUnchangedFlag(false);
		// }
		// }
		// if (whiteCheck > 1) {
		// mill = true;
		// }
		// } else {
		// int blackCheck = 0;
		// for (Node n : whiteMills) {
		// System.out.println("in white mills: " + n.getId());
		// if (n.getUnchangedFlag()) {
		// blackCheck++;
		// n.setUnchangedFlag(false);
		// }
		// }
		// if (blackCheck > 1) {
		// mill = true;
		// }
		// }

		// return mill;
	}

}
