package SwingComponents;

import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.BoxLayout;
import java.awt.CardLayout;
import javax.swing.JTextArea;
import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JButton;
import java.awt.FlowLayout;
import javax.swing.border.MatteBorder;
import javax.swing.border.LineBorder;
import javax.swing.JScrollPane;
import javax.swing.JCheckBox;
import java.awt.GridLayout;

public class PlayerInformationFrame {

	private JFrame frame;

	/**
	 * Launch the application.
	 */

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PlayerInformationFrame window = new PlayerInformationFrame();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */

	public PlayerInformationFrame() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */

	private void initialize() {
		frame = new JFrame();

		// frame.setAlwaysOnTop(true);
		frame.setBounds(100, 100, 840, 700);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.X_AXIS));

		JPanel checkListPanel = new JPanel();
		checkListPanel.setBackground(Color.CYAN);
		checkListPanel.setPreferredSize(new Dimension(180, 700));
		frame.getContentPane().add(checkListPanel);
		checkListPanel.setLayout(new GridLayout(18, 1));

		JCheckBox chckbxNewCheckBox = new JCheckBox("Reverend Green");
		checkListPanel.add(chckbxNewCheckBox);

		JCheckBox chckbxNewCheckBox_1 = new JCheckBox("Mrs. Peacock");
		checkListPanel.add(chckbxNewCheckBox_1);

		JPanel boardPanel = new JPanel();
		boardPanel.setBackground(Color.ORANGE);
		boardPanel.setPreferredSize(new Dimension(480, 700));
		frame.getContentPane().add(boardPanel);
		boardPanel.setLayout(new BoxLayout(boardPanel, BoxLayout.Y_AXIS));

		JPanel cluedoBoardPanel = new JPanel();
		cluedoBoardPanel.setBackground(new Color(255, 250, 205));
		cluedoBoardPanel.setPreferredSize(new Dimension(480, 500));
		boardPanel.add(cluedoBoardPanel);

		JPanel textPanel = new JPanel();
		textPanel.setBackground(new Color(147, 112, 219));
		textPanel.setPreferredSize(new Dimension(4800, 100));
		boardPanel.add(textPanel);
		textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.X_AXIS));

		JPanel output = new JPanel();
		output.setBackground(new Color(50, 205, 50));
		output.setPreferredSize(new Dimension(405, 100));
		textPanel.add(output);
		output.setLayout(new BorderLayout(0, 0));

		JPanel dicePanel = new JPanel();
		dicePanel.setBackground(new Color(30, 144, 255));
		dicePanel.setPreferredSize(new Dimension(75, 100));
		textPanel.add(dicePanel);
		dicePanel.setLayout(new BorderLayout(0, 0));

		JTextArea textArea_1 = new JTextArea();
		dicePanel.add(textArea_1, BorderLayout.SOUTH);

		JPanel buttonPanel = new JPanel();
		buttonPanel.setBackground(new Color(152, 251, 152));
		buttonPanel.setPreferredSize(new Dimension(480, 100));
		boardPanel.add(buttonPanel);
		buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 50));

		JButton btnNewButton = new JButton("New button");
		buttonPanel.add(btnNewButton);

		JPanel playerPanel = new JPanel();
		playerPanel.setBackground(Color.PINK);
		playerPanel.setPreferredSize(new Dimension(180, 700));
		frame.getContentPane().add(playerPanel);
		playerPanel.setLayout(new BoxLayout(playerPanel, BoxLayout.Y_AXIS));

		JPanel characterPanel = new JPanel();
		characterPanel.setBackground(new Color(221, 160, 221));
		characterPanel.setPreferredSize(new Dimension(180, 150));
		playerPanel.add(characterPanel);

		JPanel characterInfo = new JPanel();
		characterInfo.setBorder(new LineBorder(new Color(0, 0, 0), 4));
		characterInfo.setPreferredSize(new Dimension(180, 150));
		playerPanel.add(characterInfo);
		characterInfo.setLayout(new BorderLayout(0, 0));

		JTextArea textArea = new JTextArea();
		textArea.setPreferredSize(new Dimension(180, 150));
		characterInfo.add(textArea);

		JPanel dividerPanel = new JPanel();
		dividerPanel.setBackground(new Color(175, 238, 238));
		dividerPanel.setPreferredSize(new Dimension(180, 40));
		playerPanel.add(dividerPanel);

		JPanel cardsPanel = new JPanel();
		cardsPanel.setBackground(new Color(233, 150, 122));
		cardsPanel.setPreferredSize(new Dimension(180, 360));
		playerPanel.add(cardsPanel);
		frame.pack();
		frame.setVisible(true);
	}
}
