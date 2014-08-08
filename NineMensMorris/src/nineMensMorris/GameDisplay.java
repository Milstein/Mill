package nineMensMorris;

import javax.swing.JPanel;

public abstract class GameDisplay extends JPanel {

	private static final long serialVersionUID = 1L;
	
	abstract public void fillSlot(int square, int location, int player);
}
