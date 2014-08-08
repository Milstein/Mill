package nineMensMorris;

public class NineGame extends Game {
	//0 for none, 1 for player 1, 2 for player 2
	private Slot[][] slots;
	private Slot selectedSlot;
	//0 for placing, 1 for moving, 2 for flying, 3 for removal
	private int phase = 0;
	//Turn tracker
	private int player = 1;
	
	public NineGame() {
		setDisplay(new NineGameDisplay(this));
		slots = new Slot[3][8];
		for (int i = 0; i != 3; i++) {
			for (int j = 0; j != 8; j++)
				slots[i][j] = new Slot();
		}
	}
	
	protected int getSlot(int square, int location) throws Exception {
		if (!(0<=square && square<=2 && location <=0 && location <=7))
			throw new Exception("Accessed outside of slot bounds");
		return slots[square][location].getVal();
	}
	
	public void clickPosition(int square, int location) {
		switch (phase) {
		case 0:
			placePiece(square, location);
			endTurn();
			break;
		}
	}
	
	private void endTurn() {
		player = (player==1)?2:1;
	}
	
	private void placePiece(int square, int location) {
		slots[square][location].setVal(player);
		display.fillSlot(square, location, player);
	}
}
