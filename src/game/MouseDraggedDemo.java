package game;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;

public class MouseDraggedDemo {
	public static void main(String args[]) {
		Frame frame = new Frame("MouseMotionListenerExample");
		frame.pack();
		frame.setSize(new Dimension(350, 250));
		frame.setVisible(true);
		//ImageIcon iconWhite = createImageIcon("/resources/White_Stone.png");
		frame.addMouseMotionListener(new MouseMotionAdapter() {
			// invoked when mouse is moved over the panel
			public void mouseMoved(MouseEvent me) {
				Point point = me.getPoint();
				System.out.println(point);
			}

			// invoked when mouse is dragged
			public void mouseDragged(MouseEvent me) {
				Point point = me.getPoint();
				System.out.println(point);
			}
		});
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				System.exit(0);
			}
		});
	}

	private ImageIcon createImageIcon(String path) {
		java.net.URL imgURL = getClass().getResource(path);
		if (imgURL != null) {
			return new ImageIcon(imgURL);
		} else {
			System.err.println("Couldn't find file: " + path);
			return null;
		}
	}
}
