package main;

import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Game {

	private Board board = new Board();

	/**
	 * Checks to see of the choosen option is valid
	 *
	 * @param option
	 * @return
	 */

	public boolean validOption(String option) {
		return option.equals("roll") || option.equals("hand") || option.equals("accuse") || option.equals("end turn")
				|| option.equals("suggest") || option.equals("staircase");
	}

	/**
	 * Moves players by changing their coordinates
	 *
	 * @param setup
	 *
	 * @param Player
	 */

	private RoomObject checkIfInRoom(Player player, Point newPos, Setup setup) {
		String[][] temp = board.getBoardArray();
		return setup.getRoomObject().get(temp[newPos.y][newPos.x]);
	}

	/**
	 * Generates the Dice roll
	 *
	 * @return
	 */

	public int rollDice() {
		Random r = new Random();
		int low = 1;
		int high = 6;
		int result = r.nextInt(high - low) + low;
		return result;
	}

	/**
	 * Initiates the accusation, returns true if correct, else returns a false
	 *
	 * @param room
	 * @param character
	 * @param weapon
	 * @param setup
	 * @param p
	 * @return
	 */

	public boolean accuse(String room, String character, String weapon, Setup setup, Player p) {
		if (room.equals(setup.getSecretRoom()) && weapon.equals(setup.getSecretWeapon())
				&& character.equals(setup.getSecretCharacter())) {
			setup.setWinenr(p);
			return true;
		} else {
			board.removePlayer(p);
			setup.getLostPlayers().add(p);
			setup.getActivePlayers().remove(p);

			return false;
		}
	}

	/**
	 * Initiates the Suggestion, returns true if the suggestion is correct, else
	 * returns a false
	 *
	 * @param p
	 * @param room
	 * @param weapon
	 * @param character
	 * @param setup
	 * @return
	 */
	public String suggest(Player p, String room, String weapon, String character, Setup setup) {
		for (Player player : setup.getPlayers()) {
			if (!player.equals(p)) {
				ArrayList<String> hands = player.getHand();
				for (String card : hands) {
					if (card.equals(room) || card.equals(weapon) || card.equals(character)) {
						return player.getPlayerName();
					}
				}
			}
		}
		return null;
	}

	/**
	 * calls players staircase method, and returns true
	 *
	 * @param p
	 * @param hasRolled
	 * @return
	 */

	public boolean staircase(Player p, boolean hasRolled) {
		p.staircase();
		hasRolled = true;
		return hasRolled;
	}

	/**
	 * returns the Board object
	 *
	 * @return
	 */

	public Board getBoard() {
		return board;
	}

	/**
	 * Returns true if player has made a valid move, else returns a false
	 *
	 * @param player
	 * @param newPos
	 * @param setup
	 * @param movesLeft
	 * @return
	 */

	public boolean validMove(Player player, Point newPos, Setup setup, int movesLeft) {
		return board.validMove(player, newPos, setup, movesLeft);
	}

	/**
	 * Calculates the players new position on the board
	 *
	 * @param player
	 * @param newPos
	 * @param setup
	 * @param dice
	 * @return
	 */

	public int move(Player player, Point newPos, Setup setup, int dice) {
		if (validMove(player, newPos, setup, dice)) {
			RoomObject room = checkIfInRoom(player, newPos, setup);
			int xMoved = Math.abs(player.getPlayerPoint().x - newPos.x);
			int yMoved = Math.abs(player.getPlayerPoint().y - newPos.y);
			board.movePlayer(player, newPos, room);
			newPos = null;
			return xMoved + yMoved;
		}
		return 0;
	}

	/**
	 * Returns true of player has stepped into a corner room, else returns a
	 * false
	 *
	 * @param currentPlayer
	 * @return
	 */

	public boolean isInCornerRoom(Player currentPlayer) {
		return currentPlayer.getRoom().containsStairs();
	}

	/**
	 * Adds ticked items on players checklist to
	 *
	 * @param player
	 * @param name
	 */

	public void removeFromChecklist(Player player, String name) {
		player.removeFromChecklist(name);
	}

	/**
	 * adds players to suspect list
	 *
	 * @param player
	 * @param name
	 */

	public void addToSuspectList(Player player, String name) {
		player.addToSuspectList(name);
	}

	/**
	 * Calls and draws the board
	 *
	 * @param g
	 */

	public void drawBoard(Graphics g) {
		board.drawBoard(g);
	}

	/**
	 * Draws the hovering tile onto the graphics
	 *
	 * @param g
	 * @param xPoint
	 * @param yPoint
	 */

	public void fillTile(Graphics g, int xPoint, int yPoint) {
		board.fillTile(g, xPoint, yPoint);
	}

	/**
	 * Returns the 2D array String representation of the game board
	 *
	 * @return
	 */

	public String[][] getStringBoard() {
		return board.getBoardArray();
	}

}
