package game;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.TransferHandler;

/**
 * The main class that set up the frame for the gui.
 * 
 * @author Jimmy Wang
 * @version September 17, 2014
 *
 */

public class NMM {

	public static void main(String[] args) {
		// SwingUtilities.invokeLater(new Runnable() {
		// public void run() {
		// try {
		// NMMPanel panel = new NMMPanel();
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		// }
		// });

		JButton button = new JButton("This is the text");
		TransferHandler transfer = new TransferHandler("text");

		// the JButton can now be dragged and the text will be dropped
		button.setTransferHandler(transfer);

		// add a MouseListener to initiate the Drag on the appropriate
		// MouseEvent
		button.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				JButton button = (JButton) e.getSource();
				TransferHandler handle = button.getTransferHandler();
				handle.exportAsDrag(button, e, TransferHandler.COPY);
			}
		});
	}
}