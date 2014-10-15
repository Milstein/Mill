package nineMensMorris;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class SlotButton extends JButton {

	private static final long serialVersionUID = 1L;
	//0 for none, 1 for player 1, 2 for player 2
	private int filled = 0;
	private boolean selected = false;
	
	public SlotButton(ImageIcon normal, ImageIcon focused, ImageIcon pressed) {
		super(normal);
		setRolloverIcon(focused);
		setPressedIcon(pressed);
		setBorderPainted(false);
		setContentAreaFilled(false);
		setFocusPainted(false);
		setBackground(Color.WHITE);
		setOpaque(true);
	}

	public void setBounds(double d, double e, int i, int j) {
		super.setBounds((int)d, (int)e, i, j);
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (selected) {
			g.setColor(new Color(0, 255, 0));
			g.fillOval(0,0,45,45);
		}
		switch (filled) {
		case 0:
			break;
		case 1:
			g.setColor(new Color(0, 0, 0));
			g.fillOval(10, 10, 20, 20);
			break;
		case 2:
			g.setColor(new Color(255, 0, 0));
			g.fillOval(10, 10, 20, 20);
			break;
		}
	}

	public int getFilled() {
		return filled;
	}

	public boolean setFilled(int filled) {
		if (!(0<=filled && filled<=2))
			return false;
		this.filled = filled;
		return true;
	}

}
