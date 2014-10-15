package nineMensMorris;

public class Game {
	private GameDisplay display;
	//0 for none, 1 for player 1, 2 for player 2
	private Slot[][] slots;
	private Slot selectedSlot;
	protected enum Phase { PLACING, MOVING, REMOVING }
	private Phase phase = Phase.PLACING; 
	//Turn tracker
	private int player = 1;
	
	protected GameDisplay getDisplay() {
		return display;
	}
	
	protected Game setDisplay(GameDisplay newDisplay) {
		display = newDisplay;
		return this;
	}
	
	protected Phase getPhase() {
		return phase;
	}
	
	protected int getPlayer() {
		return player;
	}
	
	protected void endTurn() {
		player = (player==1)?2:1;
	}
	
	protected void clickPosition(int square, int location) {
		System.out.println("Square "+square+", location "+location);
	}

	protected Slot[][] getSlots() {
		return slots;
	}

	protected void setSlots(Slot[][] slots) {
		this.slots = slots;
	}
}
