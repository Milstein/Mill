package game;


public class Settings {

	private boolean start;
	private String player1 = "";
	private String player2 = "";

	public boolean isStart() {
		return start;
	}

	public void setStart(boolean start) {
		this.start = start;
	}

	public String getPlayer1() {
		return player1;
	}

	public void setPlayer1(String player1) {
		this.player1 = player1;
	}

	public String getPlayer2() {
		return player2;
	}

	public void setPlayer2(String player2) {
		this.player2 = player2;
	}

	public void printProperties() {
		System.out.println("Start: " + start);
		System.out.println("Player 1: " + player1);
		System.out.println("Player 2: " + player2);
	}

}
