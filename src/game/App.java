package game;

import java.awt.Container;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class App extends JPanel implements MouseMotionListener {
	private static final int SquareWidth = 10;

	private static final int Max = 100;

	private Rectangle[] squares = new Rectangle[Max];

	private int squareCount = 0;

	private int currentSquareIndex = -1;

	public App() {
		addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent evt) {
				int x = evt.getX();
				int y = evt.getY();
				currentSquareIndex = getSquare(x, y);
				if (currentSquareIndex < 0) // not inside a square
					add(x, y);
			}

			public void mouseClicked(MouseEvent evt) {
				int x = evt.getX();
				int y = evt.getY();

				if (evt.getClickCount() >= 2) {
					remove(currentSquareIndex);
				}
			}
		});
		addMouseMotionListener(this);
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		for (int i = 0; i < squareCount; i++)
			((Graphics2D) g).draw(squares[i]);
	}

	public int getSquare(int x, int y) {
		for (int i = 0; i < squareCount; i++)
			if (squares[i].contains(x, y))
				return i;
		return -1;
	}

	public void add(int x, int y) {
		if (squareCount < Max) {
			squares[squareCount] = new Rectangle(x, y, SquareWidth, SquareWidth);
			currentSquareIndex = squareCount;
			squareCount++;
			repaint();
		}
	}

	public void remove(int n) {
		if (n < 0 || n >= squareCount)
			return;
		squareCount--;
		squares[n] = squares[squareCount];
		if (currentSquareIndex == n)
			currentSquareIndex = -1;
		repaint();
	}

	public void mouseMoved(MouseEvent evt) {
		int x = evt.getX();
		int y = evt.getY();

		if (getSquare(x, y) >= 0)
			setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
		else
			setCursor(Cursor.getDefaultCursor());
	}

	public void mouseDragged(MouseEvent evt) {
		int x = evt.getX();
		int y = evt.getY();

		if (currentSquareIndex >= 0) {
			Graphics g = getGraphics();
			g.setXORMode(getBackground());
			((Graphics2D) g).draw(squares[currentSquareIndex]);
			squares[currentSquareIndex].x = x;
			squares[currentSquareIndex].y = y;
			((Graphics2D) g).draw(squares[currentSquareIndex]);
			g.dispose();
		}
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setTitle("MouseTest");
		frame.setSize(300, 200);
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});

		Container contentPane = frame.getContentPane();
		contentPane.add(new App());

		frame.show();
	}
}
