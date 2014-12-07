package game;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

public class Splashscreen extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	public Settings setting;
	private int appWidth = 270;
	private int appHeigth = 223;

	/**
	 * Create the dialog.
	 */
	public Splashscreen() {
		setTitle("9MM - Nine Men's Morris");
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);

		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setBounds((dim.width / 2 - (appWidth / 2)),
				(dim.height / 2 - (appHeigth / 2)), appWidth, appHeigth);

		contentPanel.setLayout(null);
		{
			JLabel lblPlayer = new JLabel("Player 1:");
			lblPlayer.setBounds(23, 48, 61, 23);
			contentPanel.add(lblPlayer);
		}
		{
			JLabel lblPlayer_1 = new JLabel("Player 2:");
			lblPlayer_1.setBounds(23, 90, 61, 23);
			contentPanel.add(lblPlayer_1);
		}
		{
			JLabel lblHuman = new JLabel("Human");
			lblHuman.setBounds(87, 20, 50, 16);
			contentPanel.add(lblHuman);
		}
		{
			JLabel lblComputer = new JLabel("Computer");
			lblComputer.setBounds(175, 20, 72, 16);
			contentPanel.add(lblComputer);
		}

		final JRadioButton rbtn_player1_human = new JRadioButton("", true);
		rbtn_player1_human.setName("Player_1_Human");
		rbtn_player1_human.setBounds(96, 48, 23, 23);
		contentPanel.add(rbtn_player1_human);

		final JRadioButton rbtn_player2_human = new JRadioButton("", false);
		rbtn_player2_human.setName("Player_2_Human");
		rbtn_player2_human.setBounds(96, 90, 23, 23);
		contentPanel.add(rbtn_player2_human);

		// final JRadioButton rbtn_player1_computer = new JRadioButton("",
		// false);
		// rbtn_player1_computer.setName("Player_1_Computer");
		// rbtn_player1_computer.setBounds(192, 48, 23, 23);
		// contentPanel.add(rbtn_player1_computer);

		final JRadioButton rbtn_player2_computer = new JRadioButton("", true);
		rbtn_player2_computer.setName("Player_2_Computer");
		rbtn_player2_computer.setBounds(192, 90, 23, 23);
		contentPanel.add(rbtn_player2_computer);

		ButtonGroup player1 = new ButtonGroup();
		player1.add(rbtn_player1_human);
		// player1.add(rbtn_player1_computer);

		ButtonGroup player2 = new ButtonGroup();
		player2.add(rbtn_player2_computer);
		player2.add(rbtn_player2_human);

		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				setting = new Settings();
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						setting.setStart(true);

						if (rbtn_player1_human.isSelected()) {
							setting.setPlayer(rbtn_player1_human.getName(), 1);
						}
						// else {
						// setting.setPlayer1(rbtn_player1_computer.getName());
						// }
						if (rbtn_player2_human.isSelected()) {
							setting.setPlayer(rbtn_player2_human.getName(), 2);
						} else {
							setting.setPlayer(rbtn_player2_computer.getName(), 2);
						}

						SwingUtilities.invokeLater(new Runnable() {
							public void run() {
								try {
									// NMMPanel window = new NMMPanel(setting);
									// window.frame.setVisible(true);
									NMMPanel newContentPane = new NMMPanel(
											setting);
									newContentPane.setOpaque(true);
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						});

					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent arg0) {
						System.exit(0);
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
}
