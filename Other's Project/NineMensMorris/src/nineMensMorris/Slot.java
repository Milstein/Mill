package nineMensMorris;

public class Slot {
	
	//Value of contained piece: 0 for empty, 1 for player one, 2 for player two
	private int val;
	
	public Slot(int val) {
		setVal(val);
	}
	
	public Slot() {
		val = 0;
	}
	
	public int getVal() {
		return val;
	}
	
	public void setVal(int val) {
		if (!(0<=val && val<=2))
			try {
				throw new Exception("Slot value out of range");
			} catch (Exception e) {
				e.printStackTrace();
			}
		this.val = val;
	}
}
