package main;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JMenuItem;
import SwingComponents.GUI;
import SwingComponents.SoundEffects;

public class GameController implements ActionListener, MouseMotionListener, ItemListener, MouseListener {

	private Game game;
	private Setup setup;
	private GUI gui;
	boolean endTurn = false;
	private boolean hasAccused = true;
	boolean hasRolled = false;
	private boolean hasSeenCards = false;
	private boolean hasSeenList = false;
	private int movesLeft;
	private Player currentPlayer;
	public Point mousePoint;
	private String message = "";
	private SoundEffects soundeffect = new SoundEffects();

	/**
	 * Resets the fields so that when a player restarts the game it doesn't hold
	 * the previous values
	 */

	public void resetFields() {
		movesLeft = 0;
		endTurn = false;
		hasRolled = false;
		mousePoint = null;
		message = "";
	}

	/**
	 * Initializes everything and begins the game
	 */

	private void beginGame() {
		gui = new GUI();
		soundeffect.playBackgroundMusic();
		game = new Game();
		setup = new Setup();
		setup.setup(game, gui);
		addActionListenerToButtons();
		movesLeft = 0;
		resetFields();
		nextTurn();
	}

	/**
	 * Initialieses the next turn of the player
	 */

	private void nextTurn() {
		redraw();
		gui.clearTextArea();
		movesLeft = 0;
		gui.drawMovesLeft(movesLeft);
		if (setup.getWinner() == null) {
			if (setup.getActivePlayers().isEmpty()) {
				gui.makeMessageDialog(setup.getAnswer(), "GAME OVER!");
				askToPlayAgain();
			} else {
				setPlayerProperties();
				if (currentPlayer.isInRoom()) {
					gui.makeMessageDialog("You are inside " + currentPlayer.getRoom().toString(),
							"It's player " + currentPlayer.getPlayerName() + " turn!");
					gui.appendToTextArea("You must roll the die to get out of the room\n\n");
				} else {
					message = "***********Welcome to Bongelo's cuedo game! ***********\n\n";
					message += "Current player is now: " + currentPlayer.getPlayerName() + "\n\n";
					message += "You can now make an accusation or roll the die \n\n";
					gui.appendToTextArea(message);
				}
				endTurn = false;
				hasRolled = false;
				hasAccused = false;
			}
		} else {
			gui.makeMessageDialog("Player " + currentPlayer.getPlayerName() + " has won the game", "GAME OVER");
			askToPlayAgain();
		}

	}

	/**
	 * Asks the player if they want to play again once the game has ended
	 */

	public void askToPlayAgain() {
		if (gui.showConfirmDialog("Would you like to play again?", "GAME OVER")) {
			soundeffect.stopBackgroundMusic();
			gui.mainFrame.dispose();
			beginGame();
		} else {
			gui.makeMessageDialog("THANK YOU FOR PLAYING", "GOOD BYE");
			System.exit(0);
		}
	}

	/**
	 * Resets the fields for the next player and displays everything that the
	 * player will see
	 */

	public void setPlayerProperties() {
		currentPlayer = setup.getCurrentPlayer();
		hasSeenCards = false;
		hasSeenList = false;
		gui.clearCardpanel();
		gui.getCheckListPanel().setVisibility(false);
		gui.displayPlayerInfo(currentPlayer, setup.getFollowingPlayer());
		gui.tickChecklist(currentPlayer);
	}

	/**
	 * Calls the GUI to ask the player for the suggested character and weapon
	 */

	public void makeSuggest() {
		String character = gui.getAccused(gui.getCharacterPanel(), "character", " Suggest WHO");
		while (character == null) {
			character = gui.getAccused(gui.getCharacterPanel(), "character", " Suggest WHO");
		}
		String weapon = gui.getAccused(gui.getWeaponPanel(), "weapon", " Suggest WHAT");
		while (weapon == null) {
			weapon = gui.getAccused(gui.getWeaponPanel(), "weapon", " Suggest WHAT");
		}
		suggest(character, weapon);

	}

	/**
	 * Checks whether or not the player made the right suggestion
	 *
	 * @param character
	 *            murderer
	 * @param weapon
	 *            the equipment used
	 */

	private void suggest(String character, String weapon) {
		String room = currentPlayer.getRoom().toString();

		String objectingPlayer = game.suggest(currentPlayer, room, weapon, character, setup);
		if (objectingPlayer != null) {
			message = currentPlayer.getPlayerName() + "    Player:  " + objectingPlayer + " has one of your cards";
			gui.makeMessageDialog(message, "WRONG SUGGESTION");
		} else {
			gui.makeMessageDialog(currentPlayer.getPlayerName() + "'s suggestion is correct!", "CONRATULATIONS!");
		}
		endTurn = true;
	}

	/**
	 * Calls the GUI to ask the player for the accused character, room and
	 * weapon
	 */

	public void makeAccuse() {
		/*
		 * The boolean is necessary because it lets the GC know that the current
		 * player has not yet accused.
		 */
		if (!hasAccused) {
			String room = gui.getAccused(gui.getRoomPanel(), "room", "Where?");
			while (room == null) {
				room = gui.getAccused(gui.getRoomPanel(), "room", "Where?");
			}
			String character = gui.getAccused(gui.getCharacterPanel(), "character", "Who?");
			while (character == null) {
				character = gui.getAccused(gui.getCharacterPanel(), "character", "Who?");
			}
			String weapon = gui.getAccused(gui.getWeaponPanel(), "weapon", "What?");
			while (weapon == null) {
				weapon = gui.getAccused(gui.getWeaponPanel(), "weapon", "What?");
			}
			accuse(room, character, weapon);
		}
	}

	/**
	 * Checks whether or not the player made the right accusation
	 *
	 * @param room
	 *            location where the murder took place
	 * @param character
	 *            murderer
	 * @param weapon
	 *            the equipment used
	 */

	public void accuse(String room, String character, String weapon) {

		if (game.accuse(room, character, weapon, setup, currentPlayer)) {
			gui.makeMessageDialog(currentPlayer.getPlayerName() + " has won the game", "GAME OVER!");
			askToPlayAgain();
		} else {
			gui.makeMessageDialog("You are now removed from the game", "Wrong Accusation!");
			setup.decrementPlayerlist(currentPlayer);
			endTurn = true;
			nextTurn();
			hasAccused = true;
		}
	}

	/**
	 * Gets a random number from 1-6 and displays the number of moves in the
	 * middle panel.
	 */

	public void roll() {
		if (!hasRolled) {
			hasRolled = true;
			hasAccused = false;
			movesLeft = game.rollDice();
			gui.makeMessageDialog("You can now move around the board", "You've rolled " + movesLeft);
			gui.drawMovesLeft(movesLeft);
		} else
			gui.appendToTextArea("Invalid Move!  You have already rolled the dice\n\n");
	}

	/**
	 * Adds listeners to all the components attached to the frame
	 */

	public void addActionListenerToButtons() {
		for (Component component : gui.getButtonList()) {
			if (component instanceof JButton) {
				JButton button = (JButton) component;
				button.addActionListener(this);
			} else if (component instanceof JMenuItem) {
				JMenuItem item = (JMenuItem) component;
				item.addActionListener(this);
			}
		}
		for (JCheckBox che : gui.getCheckListPanel().getCheckboxList()) {
			che.addItemListener(this);
		}
		gui.getCluedoPanel().addMouseListener(this);
		gui.getShowListbtn().addActionListener(this);
		gui.getCluedoPanel().addMouseMotionListener(this);
	}

	/**
	 * redraws the CluedoBoard
	 */

	public void redraw() {
		Graphics g = gui.getCluedoPanel().getGraphics();
		game.drawBoard(g);
		setup.drawWeapons(g);
		gui.getCluedoPanel().update(g);
	}

	/**
	 * fills the tile the mouse is currently hovering
	 *
	 * @param x
	 *            position of the tile
	 * @param y
	 *            position of the tile
	 */

	public void fillTile(int x, int y) {
		Graphics g = gui.getCluedoPanel().getGraphics();
		game.fillTile(g, x, y);
		setup.drawWeapons(g);
	}

	/**
	 * Listens for input and checks which component is being accessed by the
	 * user/player.
	 */

	@Override
	public void actionPerformed(ActionEvent e) {
		soundeffect.playButtonClick();
		/*
		 * In our implementation a player can only use the staircase if they
		 * have already rolled the die
		 */
		if (e.getSource() == gui.getStaircaseBtn()) {
			if (currentPlayer.isInRoom() && hasRolled && !endTurn) {
				if (game.isInCornerRoom(currentPlayer)) {
					gui.clearTextArea();
					currentPlayer.staircase();
					gui.appendToTextArea("You are now inside    " + currentPlayer.getRoom().getName().toUpperCase());
					gui.displayPlayerInfo(currentPlayer, setup.getFollowingPlayer());
				}
			} else if (endTurn)
				gui.appendToTextArea("INVALID MOVE! You can use the staircase on your next turn.\n\n");
			else
				gui.makeMessageDialog("You cannot use the staircase! You must be inside a corner room", "INVALID MOVE");

		} else if (e.getSource() == gui.getAccusationBtn())
			makeAccuse();

		else if (e.getSource() == gui.getRollDieBtn())
			roll();
		else if (e.getSource() == gui.getNextPlayerBtn()) {
			if (!hasRolled)
				gui.appendToTextArea("INVALID MOVE!  You must make a move!\n\n");
			else
				nextTurn();
		} else if (e.getSource() == gui.getShowCardsBtn()) {	 
			 /* This boolean allows the user to toggle the button */
			if (!hasSeenCards) {
				gui.drawCards(currentPlayer.getHand());
				hasSeenCards = true;
				return;
			}
			gui.clearCardpanel(); // redraws the cards
			hasSeenCards = false;
		} else if (e.getSource() == gui.getShowListbtn()) {
			if (!hasSeenList) { 
				gui.getCheckListPanel().setVisibility(true);
				hasSeenList = true;
				return;
			}
			gui.getCheckListPanel().setVisibility(false);
			hasSeenList = false;

		} else if (e.getActionCommand().equals("restart")) {
			soundeffect.stopBackgroundMusic();
			gui.mainFrame.dispose();
			beginGame();
		} else if (e.getActionCommand().equals("quit")) {
			gui.makeMessageDialog("Thank you for playing", "GOODBYE");
			gui.mainFrame.dispose();
			System.exit(0);
		} else if (e.getActionCommand().equals("howToPlay")) {
			gui.showHowToPlay();
		}
	}

	/**
	 * Updates the check box of each player
	 */

	@Override
	public void itemStateChanged(ItemEvent e) {

		JCheckBox cb;
		if (e.getStateChange() == ItemEvent.SELECTED) {
			cb = (JCheckBox) e.getItem();
			game.removeFromChecklist(currentPlayer, cb.getText());
		} else if (e.getStateChange() == ItemEvent.DESELECTED) {
			cb = (JCheckBox) e.getItem();
			game.addToSuspectList(currentPlayer, cb.getText());
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {

	}

	@Override
	public void mousePressed(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	/**
	 * This is used for moving the playing around the board. It gets the x and y
	 * point of the mouse and converts it to a position in the board array. With
	 * that information it redraws the player's token if it's a valid move.
	 */

	@Override
	public void mouseClicked(MouseEvent e) {
		if (hasRolled) {
			if (movesLeft > 0) {
				Point mousePoint = new Point(e.getPoint());
				/*
				 * Converts the point to the position in the original 2d array
				 * board
				 */
				Point newPos = new Point((mousePoint.x / Board.tileWidth), (mousePoint.y / Board.tileHeight));
				/* Calculates the total number of steps they want to move */
				int totalMoved = game.move(currentPlayer, newPos, setup, movesLeft);
				redraw();
				if (totalMoved > 0) {
					movesLeft -= totalMoved;
					gui.drawMovesLeft(movesLeft); // redraw the dice
					if (currentPlayer.isInRoom()) {
						gui.makeMessageDialog(
								"YOU ARE NOW INSIDE THE " + currentPlayer.getRoom().toString().toUpperCase(), "   ");
						movesLeft = 0;
						gui.drawMovesLeft(movesLeft);
						makeSuggest();
						return;
					}
				} else {
					soundeffect.playNo();
					gui.appendToTextArea("INVALID MOVE! You don't have enough steps to go there\n\n");
				}
			} else {
				gui.appendToTextArea("You have no more remaing moves\n\n");
			}
			redraw();
		} else {
			soundeffect.playNo();
			gui.makeMessageDialog("You must roll the die first!", "INVALID MOVE");
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {

	}

	/**
	 * This is used for hovering. It retrieves the x and y point of the mouse
	 * and fills the tile by drawing a rectangle on that point if it is within
	 * the bounds of the board.
	 */

	@Override
	public void mouseMoved(MouseEvent e) {
		int xPoint = e.getX() / 20;
		int yPoint = e.getY() / 20;
		if (xPoint <= 24 && xPoint >= 0 && yPoint <= 25 && yPoint >= 0) {
		fillTile(xPoint, yPoint);
		}

	}

	public static void main(String[] args) {
		GameController gameController = new GameController();
		gameController.beginGame();
	}

}
