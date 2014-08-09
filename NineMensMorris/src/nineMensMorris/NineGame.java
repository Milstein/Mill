package nineMensMorris;

public class NineGame extends Game {	
	public NineGame() {
		setDisplay(new NineGameDisplay(this));
		setSlots(new Slot[3][8]);
		for (int i = 0; i != 3; i++) {
			for (int j = 0; j != 8; j++)
				getSlots()[i][j] = new Slot();
		}
	}
	
	protected int getSlot(int square, int location) throws Exception {
		if (!(0<=square && square<=2 && location <=0 && location <=7))
			throw new Exception("Accessed outside of slot bounds");
		return getSlots()[square][location].getVal();
	}
	
	public void clickPosition(int square, int location) {
		switch (getPhase()) {
		case PLACING:
			placePiece(square, location);
			endTurn();
			break;
		case MOVING:
			break;
		case REMOVING:
			removePiece(square, location);
			endTurn();
			break;
		}
	}
	
	private boolean placePiece(int square, int location) {
		getSlots()[square][location].setVal(getPlayer());
		getDisplay().fillSlot(square, location, getPlayer());
		return true;
	}
	
	private void removePiece(int square, int location) {
		getSlots()[square][location].setVal(0);
		getDisplay().fillSlot(square, location, 0);
	}
}
