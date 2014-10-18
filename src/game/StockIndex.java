package game;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class StockIndex extends JPanel {
	// !! static String imageFile = "stockindextest.png";// replace with new
	// image
	public static final String IMAGE_PATH = "http://duke.kenai.com/iconSized/duke4.gif";

	// !! private static final int w = 87;// getting back here for width
	// !! private static final int h = 15;// getting back here for height

	private int w;
	private int h;

	private static final int SIDE = 500;
	private BufferedImage buffImage;
	private boolean _canDrag = false;
	private int _labelX = 370;// getting back here for x coord
	private int _labelY = 50;// getting back here for y coord
	private int _dragFromX = 0;
	private int _dragFromY = 0;

	public StockIndex() {
		super();
		initialize();
		MyMouseAdapter mouseAdapter = new MyMouseAdapter();
		this.addMouseListener(mouseAdapter);
		this.addMouseMotionListener(mouseAdapter);
	}

	private void initialize() {
		// !! String filePath = getClass().getResource("images").toString();
		// filePath = filePath.substring(6);
		// File f = new File(filePath + "/stockindextest" + ".png");
		try {
			URL imageUrl = new URL(IMAGE_PATH);
			// !! buffImage = ImageIO.read(f);
			buffImage = ImageIO.read(imageUrl); // !!
			w = buffImage.getWidth(); // !!
			h = buffImage.getHeight(); // !!
		} catch (IOException e2) {
			e2.printStackTrace();
		}
		this.setSize(720, 480);
		this.setLayout(null);
		this.setOpaque(false);
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(buffImage, _labelX, _labelY, w, h, null);
	}

	private class MyMouseAdapter extends MouseAdapter {
		@Override
		public void mouseDragged(MouseEvent e) {
			if (_canDrag) { // True only if button was pressed inside label.
				// --- label pos from mouse and original click displacement
				_labelX = e.getX() - _dragFromX;
				_labelY = e.getY() - _dragFromY;
				// --- Don't move the label off the screen sides
				_labelX = Math.max(_labelX, 0);
				_labelX = Math.min(_labelX, getWidth() - w);
				// --- Don't move the label off top or bottom
				_labelY = Math.max(_labelY, 0);
				_labelY = Math.min(_labelY, getHeight() - h);
				StockIndex.this.repaint();
			}
		}

		@Override
		public void mouseExited(MouseEvent e) {
			_canDrag = false;
		}

		@Override
		public void mousePressed(MouseEvent e) {
			int x = e.getX(); // Save the x coord of the click
			int y = e.getY(); // Save the y coord of the click
			if (x >= _labelX && x <= (_labelX + w) && y >= _labelY
					&& y <= (_labelY + h)) {
				_canDrag = true;
				_dragFromX = x - _labelX; // how far from left
				_dragFromY = y - _labelY; // how far from top
			} else {
				_canDrag = false;
			}
		}

		@Override
		public void mouseReleased(MouseEvent e) {
		}
	}

	private static void createAndShowUI() {
		StockIndex stockIndex = new StockIndex();
		stockIndex.setPreferredSize(new Dimension(SIDE, SIDE));
		JFrame frame = new JFrame("StockIndex");
		frame.getContentPane().add(stockIndex);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				createAndShowUI();
			}
		});
	}
}