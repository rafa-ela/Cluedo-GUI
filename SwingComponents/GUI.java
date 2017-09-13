package SwingComponents;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.ImageIcon;

import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JMenu;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.text.DefaultCaret;
import javax.xml.transform.OutputKeys;

import main.Board;
import main.Player;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;

/**
 * This class has contains all the logic for displaying the GUI, getting the
 * user input and pop up messages.
 *
 * @author Raff and Angelo
 *
 */
public class GUI {
	public JFrame mainFrame;
	private DicePanel dicePanel;
	private BoardPanel cluedoPanel;
	private JPanel characterPanel;
	private JPanel weaponPanel;
	private JPanel roomPanel;
	private ButtonGroup charButtonGroup;
	private ButtonGroup weaponButtonGroup;
	private ButtonGroup roomButtonGroup;
	private JButton accusationBtn;
	private JButton rollDieBtn;
	private JButton staircaseBtn;
	private JButton nextPlayerBtn;
	private JButton showCardsBtn;
	private String character;
	private String weapon;
	private String room;
	private int numPlayers = 0;
	private JRadioButton three;
	private JRadioButton four;
	private JRadioButton five;
	private JRadioButton six;
	private JPanel numPlayerPanel;
	private ChecklistPanel checkListPanel;
	private VisualPanel cardsPanel;
	private JTextArea diceText;
	private VisualPanel characterPhoto;
	private JTextArea playerTextArea;
	private JTextArea infoTextArea;
	private ArrayList<Component> componentList = new ArrayList<Component>();
	private MyActionListener myActionListener = new MyActionListener();
	private JButton checkListBtn;
	private String selectedCharacter;
	private JRadioButton white;
	private JRadioButton mustard;
	private JRadioButton plum;
	private JRadioButton green;
	private JRadioButton scarlett;
	private JRadioButton peacock;

	public GUI() {
		initialize();
		numPlayersPanel();
		radioWeapons();
		radioCharacters();
		radioRooms();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	public void initialize() {
		mainFrame = new JFrame();
		mainFrame.setBounds(0, 0, 840, 700);
		mainFrame.setLocationRelativeTo(null);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				int option = JOptionPane.showConfirmDialog(mainFrame, "Are you sure to exit the game?", "EXIT GAME?",
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				if (option == JOptionPane.YES_OPTION) {
					System.exit(0);
				} else if (option == JOptionPane.NO_OPTION) {
					makeMessageDialog("Resuming game", "RESUME");
				}

			}
		});
		mainFrame.getContentPane().setLayout(new BoxLayout(mainFrame.getContentPane(), BoxLayout.X_AXIS));
		makeLeftPanel();
		drawMiddlePanel();
		makeRightPanel();
		makeMenuBar();
		mainFrame.setResizable(false);
		mainFrame.pack();
		mainFrame.setVisible(true);
	}

	/**
	 * Makes the menu bar and the menu items it contains
	 */

	public void makeMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		JMenu menuOption = new JMenu("Option");
		menuOption.setMnemonic(KeyEvent.VK_O);
		JMenu menuHelp = new JMenu("Help");
		menuHelp.setMnemonic(KeyEvent.VK_H);
		menuBar.add(menuOption);
		JMenuItem menItemQuit = new JMenuItem("Quit");
		menItemQuit.setActionCommand("quit");
		// adds a shortcut key command
		menItemQuit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, InputEvent.CTRL_MASK));
		menuOption.add(menItemQuit);
		JMenuItem menItemRestart = new JMenuItem("Restart");
		menItemRestart.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, InputEvent.CTRL_MASK));
		menItemRestart.setActionCommand("restart");
		menuOption.add(menItemRestart);
		JMenuItem menItemRules = new JMenuItem("How to play");
		menItemRules.setActionCommand("howToPlay");
		menItemRules.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T, InputEvent.CTRL_MASK));
		menuBar.add(menuHelp);
		// adds it to the 'parent' menu
		componentList.add(menItemRestart);
		// adds it to the component list
		componentList.add(menItemRules);
		componentList.add(menItemQuit);
		menuHelp.add(menItemRules);
		mainFrame.setJMenuBar(menuBar);
	}

	/**
	 * Makes the detective note pad on the left side of the frame which is
	 * essentially a checklist that a player can refer, to help solve the
	 * mystery.
	 */

	public void makeLeftPanel() {
		JPanel leftpanel = new JPanel();
		leftpanel.setLayout(new BoxLayout(leftpanel, BoxLayout.Y_AXIS));
		JPanel upperPanel = new JPanel();
		upperPanel.setBackground(new Color(180, 236, 180));
		upperPanel.setPreferredSize(new Dimension(180, 50));
		checkListBtn = new JButton("Show Checklist");
		checkListPanel = new ChecklistPanel();
		checkListPanel.setBackground(new Color(175, 238, 238));
		checkListPanel.setPreferredSize(new Dimension(180, 650));
		checkListPanel.setLayout(new FlowLayout(10, 30, 8));
		upperPanel.add(checkListBtn);
		leftpanel.add(upperPanel);
		leftpanel.add(checkListPanel);
		mainFrame.getContentPane().add(leftpanel);
	}

	/**
	 * Makes and divides the middle panel into 3 sections
	 */

	public void drawMiddlePanel() {

		JPanel middlePanel = new JPanel();
		middlePanel.setBackground(new Color(255, 250, 205));
		middlePanel.setPreferredSize(new Dimension(480, 700));
		middlePanel.setLayout(new BoxLayout(middlePanel, BoxLayout.Y_AXIS));
		cluedoPanel = new BoardPanel(); // The panel that will display the
										// Cluedo board
		cluedoPanel.setPreferredSize(new Dimension(480, 500));
		JPanel textPanel = new JPanel(); // displays messages to the players
		textPanel.setBackground(new Color(147, 112, 219));
		textPanel.setPreferredSize(new Dimension(4800, 100));
		textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.X_AXIS));
		// they need to be added in order(top ->bottom) otherwise it will screw
		// up the layout
		middlePanel.add(cluedoPanel);
		makeTextArea(textPanel);
		middlePanel.add(textPanel);
		makeDicePanel(textPanel);
		makeButtonPanel(middlePanel);
		mainFrame.getContentPane().add(middlePanel);
	}

	/**
	 * Makes the text area in the middle panel which displays messages to the
	 * player
	 *
	 * @param middlePanel
	 *            is the parent panel a.k.a. where the new components will be
	 *            attached to
	 */

	public void makeTextArea(JPanel middlePanel) {
		infoTextArea = new JTextArea();
		infoTextArea.setEditable(false);
		infoTextArea.setBackground(new Color(255, 255, 102));
		JScrollPane infoTextPanel = new JScrollPane(infoTextArea);
		infoTextPanel.setPreferredSize(new Dimension(405, 100));
		DefaultCaret caret = (DefaultCaret) infoTextArea.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		infoTextArea.setLineWrap(true);
		middlePanel.add(infoTextPanel);
	}

	/**
	 * makes the dice panel in the middle panel
	 *
	 * @param textPanel
	 */

	public void makeDicePanel(JPanel textPanel) {
		JPanel diceInfo = new JPanel();
		diceInfo.setLayout(new BoxLayout(diceInfo, BoxLayout.Y_AXIS));
		JPanel textAreaPanel = new JPanel();
		textAreaPanel.setPreferredSize(new Dimension(75, 25));
		dicePanel = new DicePanel();
		dicePanel.setPreferredSize(new Dimension(75, 75));
		dicePanel.setBackground(new Color(30, 144, 255));
		diceText = new JTextArea(); // types up the number of moves left
		textAreaPanel.add(diceText);
		diceInfo.add(dicePanel);
		diceInfo.add(textAreaPanel);
		textPanel.add(diceInfo);
	}

	/**
	 * Makes the JButtons in the lower middle panel
	 *
	 * @param boardPanel
	 *            the parent panel or the panel where the buttons will be
	 *            attached to
	 */

	public void makeButtonPanel(JPanel boardPanel) {
		JPanel buttonPanel = new JPanel();
		buttonPanel.setBackground(new Color(152, 251, 152));
		buttonPanel.setPreferredSize(new Dimension(480, 100));
		buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 45));
		accusationBtn = new JButton("Accusation");
		accusationBtn.setToolTipText("Click me to accuse!");
		buttonPanel.add(accusationBtn);
		componentList.add(accusationBtn);
		staircaseBtn = new JButton("Stair Case");
		buttonPanel.add(staircaseBtn);
		staircaseBtn.setToolTipText("Transports player to the opposite corner room");
		componentList.add(staircaseBtn);
		buttonPanel.validate();
		rollDieBtn = new JButton("Roll Die");
		rollDieBtn.setToolTipText("Click me! so you can move around");
		buttonPanel.add(rollDieBtn);
		componentList.add(rollDieBtn);
		nextPlayerBtn = new JButton("Next Player");
		nextPlayerBtn.setToolTipText("Click me! if you want to end your turn");
		componentList.add(nextPlayerBtn);
		buttonPanel.add(nextPlayerBtn);
		boardPanel.add(buttonPanel);
	}

	/**
	 * This panel is responsible for displaying all the player's information
	 * such as their name, cards, room they are currently residing and name of
	 * the next player
	 */

	public void makeRightPanel() {
		JPanel rightPanel = new JPanel(); // parent panel
		rightPanel.setBackground(new Color(141, 233, 113));
		rightPanel.setPreferredSize(new Dimension(180, 700));

		/* split the sub-panels in the y axis */
		rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));

		/* the panel that displays the photo of the player's character */
		characterPhoto = new VisualPanel();
		characterPhoto.setBackground(new Color(141, 233, 113));
		characterPhoto.setPreferredSize(new Dimension(180, 150));
		characterPhoto.setLayout(new BorderLayout());
		rightPanel.add(characterPhoto);

		/* Shows the player's information by appending it to a JTextArea */
		JPanel playerInfoPanel = new JPanel();
		playerInfoPanel.setPreferredSize(new Dimension(180, 150));
		rightPanel.add(playerInfoPanel);
		playerInfoPanel.setLayout(new BorderLayout(0, 0));
		playerTextArea = new JTextArea(); // where the info is being displayed
		playerTextArea.setBackground(new Color(141, 233, 113));
		playerTextArea.setEditable(false);
		playerTextArea.setPreferredSize(new Dimension(180, 150));
		playerInfoPanel.add(playerTextArea);
		makeDivider(rightPanel);

		/* the sub-panel that will hold the player's card */
		cardsPanel = new VisualPanel();
		cardsPanel.setBackground(new Color(233, 150, 122));
		cardsPanel.setLayout(new GridLayout(3, 2));
		cardsPanel.setPreferredSize(new Dimension(180, 360));
		rightPanel.add(cardsPanel);
		mainFrame.getContentPane().add(rightPanel);
	}

	/**
	 * Makes a panel between the card panel and player information panel. It has
	 * a "Show Cards" button that allows the player to expose/hide the cards in
	 * their hand
	 *
	 * @param rightPanel
	 *            the parent panel or where the components will be attached to
	 */

	public void makeDivider(JPanel rightPanel) {
		JPanel dividerPanel = new JPanel();
		dividerPanel.setBackground(new Color(175, 238, 238));
		dividerPanel.setPreferredSize(new Dimension(180, 40));
		showCardsBtn = new JButton("Show Cards");
		dividerPanel.add(showCardsBtn);
		componentList.add(showCardsBtn);
		rightPanel.add(dividerPanel);
	}

	/**
	 * Creates the panel and button group that's responsible for asking the
	 * number players
	 */

	public void numPlayersPanel() {
		ButtonGroup numberGroup = new ButtonGroup();
		numPlayerPanel = new JPanel(new GridLayout(5, 1));
		numPlayerPanel.setPreferredSize(new Dimension(70, 150));
		three = new JRadioButton("3");
		four = new JRadioButton("4");
		five = new JRadioButton("5");
		six = new JRadioButton("6");
		numberGroup.add(three);
		numberGroup.add(five);
		numberGroup.add(four);
		numberGroup.add(six);
		three.addActionListener(myActionListener);
		four.addActionListener(myActionListener);
		five.addActionListener(myActionListener);
		six.addActionListener(myActionListener);
		numPlayerPanel.add(three);
		numPlayerPanel.add(four);
		numPlayerPanel.add(five);
		numPlayerPanel.add(six);
		numPlayerPanel
				.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "How many players?"));
	}

	/* "************************************************************************************************" */
	/* GETS INPUT FROM THE USER */
	/* "************************************************************************************************" */

	/**
	 * Asks the user how many players are going to participate
	 *
	 * @return
	 */
	public int getNumofPlayers() {
		int option = JOptionPane.showConfirmDialog(null, numPlayerPanel, "Welcome!", JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.PLAIN_MESSAGE);
		if (option == JOptionPane.OK_OPTION) {
			return numPlayers;
		} else if (showConfirmDialog("Are you sure you want to exit the game?", "  "))
			// if they want to exit the game terminate the whole program
			System.exit(0);
		else
			return 0;
		return 0;
	}

	/**
	 * Confirms the players answer
	 *
	 * @param question
	 *            the message you want to ask the player
	 * @param title
	 *            of the pane
	 * @return true if they pick they 'Yes' option
	 */

	public boolean showConfirmDialog(String question, String title) {
		if (JOptionPane.showConfirmDialog(mainFrame, question, title, JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
			return true;
		}
		return false;
	}

	/**
	 * Asks the player for the accused or suggested item.
	 *
	 * @param panel
	 *            to be used e.g. Character, Weapon or Room
	 * @param accused
	 *            which item is going to be accused
	 * @param message
	 * @return accused character/weapon/room
	 */

	public String getAccused(JPanel panel, String accused, String message) {
		String[] options = { "OK" };
		int option = JOptionPane.showOptionDialog(null, panel, message, JOptionPane.NO_OPTION,
				JOptionPane.QUESTION_MESSAGE, new ImageIcon("src/resources/quit.png"), options, options[0]);
		if (option == JOptionPane.OK_OPTION) {
			switch (accused) {
			case "character":
				return character;
			case "room":
				return room;
			case "weapon":
				return weapon;
			}
		} else if (option == JOptionPane.CLOSED_OPTION) {
			JOptionPane.getRootFrame().dispose();
		}
		return null;
	}

	/**
	 * Asks the player's name
	 *
	 * @param message
	 * @return name of the player
	 */

	public String askName(String message) {
		String[] options = { "OK" };
		String text = null;
		JPanel panel = new JPanel();
		JLabel label = new JLabel("Enter Your name: ");
		JTextField nameField = new JTextField(10);
		panel.add(label);
		panel.add(nameField);
		int selectedOption = JOptionPane.showOptionDialog(null, panel, message, JOptionPane.NO_OPTION,
				JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
		if (selectedOption == JOptionPane.OK_OPTION && nameField.getText() != null) {
			return nameField.getText();
		} else if (selectedOption == JOptionPane.CLOSED_OPTION) {
			// ask player if they really want to exit the game
			if (!showConfirmDialog("Are you sure you want to exit the game?", " "))
				return nameField.getText();
			else
				System.exit(0); // if they do, stop the whole program
		}
		return text;
	}

	/**
	 * Makes a pop up message to the player
	 *
	 * @param message
	 *            you want to display
	 * @param title
	 *            of the dialog
	 */

	public void makeMessageDialog(String message, String title) {
		JOptionPane.showMessageDialog(mainFrame, message, title, JOptionPane.NO_OPTION);
	}

	/*
	 * "************************************************************************************************"
	 * GETTERS
	 * "************************************************************************************************"
	 */

	public BoardPanel getCluedoPanel() {
		return cluedoPanel;
	}

	public JPanel getCharacterPanel() {
		return characterPanel;
	}

	public JPanel getRoomPanel() {
		return roomPanel;
	}

	public ChecklistPanel getCheckListPanel() {
		return checkListPanel;
	}

	public JPanel getWeaponPanel() {
		return weaponPanel;
	}

	public JButton getAccusationBtn() {
		return accusationBtn;
	}

	public JButton getNextPlayerBtn() {
		return nextPlayerBtn;
	}

	public JButton getRollDieBtn() {
		return rollDieBtn;
	}

	public JButton getNxtPlayeBtn() {
		return nextPlayerBtn;
	}

	public JButton getStaircaseBtn() {
		return staircaseBtn;
	}

	public JButton getShowCardsBtn() {
		return showCardsBtn;
	}

	public JButton getShowListbtn() {
		return checkListBtn;
	}

	public ArrayList<Component> getButtonList() {
		return componentList;
	}

	/*
	 * "************************************************************************************************"
	 * METHODS THAT DRAWS IMAGES OR DISPLAYS TEXT IN THE FRAME
	 * "************************************************************************************************"
	 */

	/**
	 * Displays the player's hand in the card panel
	 *
	 * @param playerCards
	 *            the list of cards the player has
	 */

	public void drawCards(ArrayList<String> playerCards) {
		cardsPanel.drawHand(playerCards);
		Graphics visualGraphics = cardsPanel.getGraphics();
		cardsPanel.update(visualGraphics);
	}

	/**
	 * Displays information about the current player
	 *
	 * @param player
	 *            the current player
	 * @param nextPlayer
	 *            name of the next player
	 */

	public void displayPlayerInfo(Player player, Player nextPlayer) {
		characterPhoto.drawCharacter(player.getPlayerChar());
		playerTextArea.setText(null);
		playerTextArea.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 16));
		String message = "Player:  " + player.getPlayerName() + "\n \n";
		message += "Player number:  " + player.getPlayerNum() + "\n \n";
		message += "Room:  " + player.roomName() + "\n \n";
		message += "Next player: " + nextPlayer.getPlayerName();
		playerTextArea.append(message);
	}

	/**
	 * Appends or adds a message to the JTextArea in the middle panel
	 *
	 * @param message
	 */
	public void appendToTextArea(String message) {
		infoTextArea.append(message);
	}

	/**
	 * clears the the JTextArea in the middle panel
	 */

	public void clearTextArea() {
		infoTextArea.setText(" ");
	}

	/**
	 * Appends the remaining moves of the player in the dice text area
	 *
	 * @param moves
	 *            remaining moves the player has
	 */

	public void drawMovesLeft(int moves) {
		diceText.setText(null);
		diceText.append("Moves" + moves);
		dicePanel.drawDice(moves);
	}

	/**
	 * Ticks the check boxes
	 *
	 * @param current
	 *            player
	 */

	public void tickChecklist(Player p) {
		checkListPanel.unCheckAllBoxes();
		checkListPanel.updateCheckbox(p);
	}

	/**
	 * Repaints the card panel so that it can display the next player's
	 * hand/cards
	 */

	public void clearCardpanel() {
		cardsPanel.clearGraphics();
	}

	/**
	 * Makes a JDialog that presents the rules of the game
	 */

	public void showHowToPlay() {
		JDialog rules = new JDialog(mainFrame, "How to play");
		JDialog.setDefaultLookAndFeelDecorated(true);
		rules.setSize(540, 405);
		rules.setPreferredSize(new Dimension(540, 405));
		/* sets the window to be in the center of the screen */
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension screenSize = toolkit.getScreenSize();
		int x = (screenSize.width - rules.getWidth()) / 2;
		int y = (screenSize.height - rules.getHeight()) / 2;
		rules.setLocation(x, y);
		JLabel howToPlay = new JLabel(new ImageIcon("src/resources/HowToPlay.jpg"));
		rules.add(howToPlay);
		rules.setVisible(true);
		rules.setResizable(false);
	}

	/*
	 * "************************************************************************************************"
	 * MAKES RADIO BUTTONS /*
	 * "************************************************************************************************"
	 */

	/**
	 * Makes radio buttons for characters and adds it to the character panel and
	 * button group
	 */

	public void radioCharacters() {
		CharacterListener cl = new CharacterListener();
		characterPanel = new JPanel(new GridLayout(6, 1));
		charButtonGroup = new ButtonGroup();/*
											 * Adds the buttons to a button
											 * group so that you can't select >1
											 * option
											 */
		scarlett = new JRadioButton("Miss Scarlet");
		green = new JRadioButton("Rev Green");
		peacock = new JRadioButton("Mrs Peacock");
		plum = new JRadioButton("Professor Plum");
		white = new JRadioButton("Mrs White");
		mustard = new JRadioButton("Colonel Mustard");

		charButtonGroup.add(scarlett);
		charButtonGroup.add(green);
		charButtonGroup.add(peacock);
		charButtonGroup.add(plum);
		charButtonGroup.add(white);
		charButtonGroup.add(mustard);

		characterPanel.add(mustard);
		characterPanel.add(scarlett);
		characterPanel.add(green);
		characterPanel.add(peacock);
		characterPanel.add(plum);
		characterPanel.add(white);

		// adds action listener to each JRadiobutton
		for (Enumeration<AbstractButton> buttons = charButtonGroup.getElements(); buttons.hasMoreElements();) {
			AbstractButton button = buttons.nextElement();
			button.addActionListener(cl);
		}
	}

	/**
	 * Makes radio buttons for weapons and adds it to the weapon panel and
	 * button group
	 */

	public void radioWeapons() {
		// same logic applied here
		WeaponListener wp = new WeaponListener();
		weaponButtonGroup = new ButtonGroup();
		weaponPanel = new JPanel(new GridLayout(6, 1));
		JRadioButton dagger = new JRadioButton("Dagger");
		JRadioButton candle = new JRadioButton("Candlestick");
		JRadioButton leadPipe = new JRadioButton("Lead Pipe");
		JRadioButton rope = new JRadioButton("Rope");
		JRadioButton spanner = new JRadioButton("Spanner");
		JRadioButton revolver = new JRadioButton("Revolver");

		weaponButtonGroup.add(dagger);
		weaponButtonGroup.add(candle);
		weaponButtonGroup.add(leadPipe);
		weaponButtonGroup.add(rope);
		weaponButtonGroup.add(spanner);
		weaponButtonGroup.add(revolver);

		weaponPanel.add(dagger);
		weaponPanel.add(candle);
		weaponPanel.add(leadPipe);
		weaponPanel.add(rope);
		weaponPanel.add(spanner);
		weaponPanel.add(revolver);
		for (Enumeration<AbstractButton> buttons = weaponButtonGroup.getElements(); buttons.hasMoreElements();) {
			AbstractButton button = buttons.nextElement();
			button.addActionListener(wp);
		}
	}

	/**
	 * Makes radio buttons for rooms and adds it to the room panel and button
	 * group
	 */

	public void radioRooms() {
		// same logic applied here
		RoomListener rl = new RoomListener();
		roomButtonGroup = new ButtonGroup();
		roomPanel = new JPanel(new GridLayout(9, 1));

		JRadioButton hall = new JRadioButton("Hall");
		JRadioButton kitchen = new JRadioButton("Kitchen");
		JRadioButton library = new JRadioButton("Library");
		JRadioButton study = new JRadioButton("Study");
		JRadioButton conservatory = new JRadioButton("Conservatory");
		JRadioButton diningRoom = new JRadioButton("Dining Room");
		JRadioButton billiard = new JRadioButton("Billiard Room");
		JRadioButton ballRoom = new JRadioButton("Ball Room");
		JRadioButton lounge = new JRadioButton("Lounge");

		roomButtonGroup.add(hall);
		roomButtonGroup.add(kitchen);
		roomButtonGroup.add(library);
		roomButtonGroup.add(study);
		roomButtonGroup.add(conservatory);
		roomButtonGroup.add(diningRoom);
		roomButtonGroup.add(billiard);
		roomButtonGroup.add(ballRoom);
		roomButtonGroup.add(lounge);

		roomPanel.add(hall);
		roomPanel.add(kitchen);
		roomPanel.add(library);
		roomPanel.add(study);
		roomPanel.add(conservatory);
		roomPanel.add(diningRoom);
		roomPanel.add(ballRoom);
		roomPanel.add(lounge);
		roomPanel.add(billiard);

		for (Enumeration<AbstractButton> buttons = roomButtonGroup.getElements(); buttons.hasMoreElements();) {
			AbstractButton button = buttons.nextElement();
			button.addActionListener(rl);
		}
	}

	/**
	 * This method goes through the character button group and checks which one
	 * is selected and assigns it to the character field which gets returned to
	 * the Game Controller
	 */

	public void getSelectedCharacter() {
		for (Enumeration<AbstractButton> buttons = charButtonGroup.getElements(); buttons.hasMoreElements();) {
			AbstractButton chButton = buttons.nextElement();
			if (chButton.isSelected()) {
				character = chButton.getText();
				return;
			}
		}

	}

	/**
	 * This method goes through the weapon button group and checks which one is
	 * selected and assigns it to the weapon field which gets returned to the
	 * Game Controller
	 */

	public void getSelectedWeapon() {
		for (Enumeration<AbstractButton> buttons = weaponButtonGroup.getElements(); buttons.hasMoreElements();) {
			AbstractButton button = buttons.nextElement();
			if (button.isSelected()) {
				weapon = button.getText();
				return;
			}
		}

	}

	/**
	 * This method goes through the weapon room group and checks which one is
	 * selected and assigns it to the room field which gets returned to the Game
	 * Controller
	 */

	public void getSelectedRoom() {
		for (Enumeration<AbstractButton> buttons = roomButtonGroup.getElements(); buttons.hasMoreElements();) {
			AbstractButton button = buttons.nextElement();
			if (button.isSelected()) {
				room = button.getText();
				return;
			}
		}
	}

	/* "************************************************************************************************" */

	/* PRIVATE CLASSES */

	/* "************************************************************************************************" */

	public class MyActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == three) {
				numPlayers = 3;
			} else if (e.getSource() == four) {
				numPlayers = 4;
			} else if (e.getSource() == five) {
				numPlayers = 5;
			} else if (e.getSource() == six) {
				numPlayers = 6;
			}
		}
	}

	/**
	 * A class that only listens to the buttons in the character panel. I
	 * separated the listener into three small classes to reduce the number of
	 * if statements we will use.
	 *
	 * @author Rafaela and Angelo
	 *
	 */

	private class CharacterListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() instanceof JRadioButton) {
				getSelectedCharacter();
			}

		}
	}

	/**
	 * A class that only listens to the buttons in the weapon panel. I separated
	 * the listener into three small classes to reduce the number of if
	 * statements we will use.
	 *
	 * @author Rafaela and Angelo
	 *
	 */

	private class WeaponListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() instanceof JRadioButton) {
				getSelectedWeapon();
			}
		}
	}

	/**
	 * A class that only listens to the buttons in the room panel. I separated
	 * the listener into three small classes to reduce the number of if
	 * statements we will use.
	 *
	 * @author Rafaela and Angelo
	 *
	 */

	private class RoomListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() instanceof JRadioButton) {
				getSelectedRoom();
			}
		}
	}
}
