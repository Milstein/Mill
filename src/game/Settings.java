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

	public void setPlayer(String player, int i) {
		if(i==1)
			this.player1 = player;
		if(i==2)
			this.player2 = player;
	}

	public String getPlayer(int i) {
		if(i==1)
			return player1;
		if(i==2)
			return player2;
		return "";
	}


	public void printProperties() {
		System.out.println("Start: " + start);
		System.out.println("Player 1: " + player1);
		System.out.println("Player 2: " + player2);
	}

}
