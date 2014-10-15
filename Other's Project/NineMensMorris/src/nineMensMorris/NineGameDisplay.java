package nineMensMorris;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Line2D;
import javax.swing.ImageIcon;

public class NineGameDisplay extends GameDisplay implements ActionListener {

	private static final long serialVersionUID = 1L;
	//Order: btl, btc, btr, bsl, bsr, bbl, bbc, bbr, mtl, mtc, mtr, msl, msr, mbl, mbc, mbr, stl, stc, str, ssl, ssr, sbl, sbc, sbr
	protected SlotButton[][] positionButtons;
	private Rectangle bigSquare, medSquare, lilSquare;
	private NineGame game;
	
	public NineGameDisplay(NineGame theGame) {
		game = theGame;
		setLayout(null);
		this.setBounds(0,0,600,600);
		bigSquare = new Rectangle(65, 65, 480, 480);
		medSquare = new Rectangle(bigSquare);
		medSquare.grow(-80, -80);
		lilSquare = new Rectangle(medSquare);
		lilSquare.grow(-80, -80);
		
		ImageIcon buttonUnselected = new ImageIcon("images/ButtonUnselected.png");
		ImageIcon buttonSelected = new ImageIcon("images/ButtonSelected.png");
		ImageIcon buttonFocused = new ImageIcon("images/ButtonFocused.png");
		
		positionButtons = new SlotButton[3][8];
		for (int i=0; i!=3; i++) {
			for (int j=0; j!=8; j++) {
				positionButtons[i][j] = new SlotButton(buttonUnselected, buttonFocused, buttonSelected);
				add(positionButtons[i][j]);
				positionButtons[i][j].setActionCommand(i+";"+j);
				positionButtons[i][j].addActionListener(this);
			}
		}
		//Set all buttons manually aaah
		positionButtons[0][0].setBounds(bigSquare.getMinX()-20, bigSquare.getMinY()-20, 40, 40);
		positionButtons[0][1].setBounds(bigSquare.getCenterX()-20, bigSquare.getMinY()-20, 40, 40);
		positionButtons[0][2].setBounds(bigSquare.getMaxX()-20, bigSquare.getMinY()-20, 40, 40);
		positionButtons[0][3].setBounds(bigSquare.getMaxX()-20, bigSquare.getCenterY()-20, 40, 40);
		positionButtons[0][4].setBounds(bigSquare.getMaxX()-20, bigSquare.getMaxY()-20, 40, 40);
		positionButtons[0][5].setBounds(bigSquare.getCenterX()-20, bigSquare.getMaxY()-20, 40, 40);
		positionButtons[0][6].setBounds(bigSquare.getMinX()-20, bigSquare.getMaxY()-20, 40, 40);
		positionButtons[0][7].setBounds(bigSquare.getMinX()-20, bigSquare.getCenterY()-20, 40, 40);
		positionButtons[1][0].setBounds(medSquare.getMinX()-20, medSquare.getMinY()-20, 40, 40);
		positionButtons[1][1].setBounds(medSquare.getCenterX()-20, medSquare.getMinY()-20, 40, 40);
		positionButtons[1][2].setBounds(medSquare.getMaxX()-20, medSquare.getMinY()-20, 40, 40);
		positionButtons[1][3].setBounds(medSquare.getMaxX()-20, medSquare.getCenterY()-20, 40, 40);
		positionButtons[1][4].setBounds(medSquare.getMaxX()-20, medSquare.getMaxY()-20, 40, 40);
		positionButtons[1][5].setBounds(medSquare.getCenterX()-20, medSquare.getMaxY()-20, 40, 40);
		positionButtons[1][6].setBounds(medSquare.getMinX()-20, medSquare.getMaxY()-20, 40, 40);
		positionButtons[1][7].setBounds(medSquare.getMinX()-20, medSquare.getCenterY()-20, 40, 40);
		positionButtons[2][0].setBounds(lilSquare.getMinX()-20, lilSquare.getMinY()-20, 40, 40);
		positionButtons[2][1].setBounds(lilSquare.getCenterX()-20, lilSquare.getMinY()-20, 40, 40);
		positionButtons[2][2].setBounds(lilSquare.getMaxX()-20, lilSquare.getMinY()-20, 40, 40);
		positionButtons[2][3].setBounds(lilSquare.getMaxX()-20, lilSquare.getCenterY()-20, 40, 40);
		positionButtons[2][4].setBounds(lilSquare.getMaxX()-20, lilSquare.getMaxY()-20, 40, 40);
		positionButtons[2][5].setBounds(lilSquare.getCenterX()-20, lilSquare.getMaxY()-20, 40, 40);
		positionButtons[2][6].setBounds(lilSquare.getMinX()-20, lilSquare.getMaxY()-20, 40, 40);
		positionButtons[2][7].setBounds(lilSquare.getMinX()-20, lilSquare.getCenterY()-20, 40, 40);
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.WHITE);
		g2.fillRect(0, 0, 610, 610);
		g2.setColor(Color.BLUE);
		g2.draw(bigSquare);
		g2.draw(medSquare);
		g2.draw(lilSquare);
		Line2D line = new Line2D.Double(lilSquare.getCenterX(), lilSquare.getMaxY(), bigSquare.getCenterX(), bigSquare.getMaxY());
		g2.draw(line);
		line.setLine(lilSquare.getCenterX(), lilSquare.getMinY(), bigSquare.getCenterX(), bigSquare.getMinY());
		g2.draw(line);
		line.setLine(lilSquare.getMinX(), lilSquare.getCenterY(), bigSquare.getMinX(), bigSquare.getCenterY());
		g2.draw(line);
		line.setLine(lilSquare.getMaxX(), lilSquare.getCenterY(), bigSquare.getMaxX(), bigSquare.getCenterY());
		g2.draw(line);
	}

	public void actionPerformed(ActionEvent e) {
		int square = Integer.parseInt(e.getActionCommand().substring(0, e.getActionCommand().indexOf(";")));
		int location = Integer.parseInt(e.getActionCommand().substring(e.getActionCommand().indexOf(";")+1, e.getActionCommand().length()));
		game.clickPosition(square, location);
	}

	public void fillSlot(int square, int location, int player) {
		positionButtons[square][location].setFilled(player);
	}
}
