package main;

import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;

import javax.swing.ImageIcon;

import SwingComponents.GUI;

public class Setup {
	private ArrayList<String> charNames = new ArrayList<String>();
	private ArrayList<String> rooms = new ArrayList<String>();
	private HashMap<String, Point> weapons = new HashMap<String, Point>();
	private ArrayList<Point> points = new ArrayList<Point>();
	private HashMap<String, RoomObject> roomObjects = new HashMap<String, RoomObject>();
	private String answer ;

	private String secretRoom;
	private String secretCharacter;
	private String secretWeapon;

	private ArrayList<Player> players = new ArrayList<Player>();
	private ArrayList<Player> activePlayers;
	private ArrayList<Player> lostPlayers;
	private Player winner;
	private int playerNum = -1;
	private int otherPlayer = 0;

	public Setup() {
		addElements();
	}

	/**
	 * Sets up the entire game
	 * @param game
	 * @param gui
	 */

	public void setup(Game game, GUI gui) {
		setPlayers(gui, game.getBoard());
		setSecretCards();
		dealCards();
		begin();
	}

	/**
	 * Adds all of the elements of the game into their designated Lists etc...
	 */

	public void addElements() {
		/* Adds the Names of Characters to a list of Characters */
		charNames.add("Miss Scarlet");
		charNames.add("Colonel Mustard");
		charNames.add("Mrs White");
		charNames.add("Rev Green");
		charNames.add("Mrs Peacock");
		charNames.add("Professor Plum");

		/* Adds the Points X & Y coordinates of each character into a list */
		// Miss Scarlett #1
		points.add(new Point(7, 24));
		// Colonel Mustard #2
		points.add(new Point(0, 17));
		// Mrs. White #3
		points.add(new Point(9, 0));
		// The Reverend Green #4
		points.add(new Point(14, 0));
		// Mrs. Peacock #5
		points.add(new Point(23, 6));
		// Professor Plum #6
		points.add(new Point(23, 19));

		/* Adds the room to a list of Rooms */
		rooms.add("Kitchen");
		rooms.add("Conservatory");
		rooms.add("Lounge");
		rooms.add("Study");
		rooms.add("Dining Room");
		rooms.add("Hall");
		rooms.add("Library");
		rooms.add("Billiard Room");
		rooms.add("Ball Room");

		// Creates and adds room objects
		RoomObject kitchen = new RoomObject("Kitchen", new Point[] { new Point(6, 4) }, new Point(1, 5));
		RoomObject conservatory = new RoomObject("Conservatory", new Point[] { new Point(4, 18) }, new Point(5, 22));
		RoomObject lounge = new RoomObject("Lounge", new Point[] { new Point(19, 6) }, new Point(19, 0));
		RoomObject study = new RoomObject("Study", new Point[] { new Point(21, 17) }, new Point(21, 23));

		// Sets opposite room
		kitchen.setOppositeRoomOb(lounge);
		conservatory.setOppositeRoomOb(study);
		lounge.setOppositeRoomOb(kitchen);
		study.setOppositeRoomOb(conservatory);

		roomObjects.put("K", kitchen);
		roomObjects.put("C", conservatory);
		roomObjects.put("G", lounge);
		roomObjects.put("T", study);
		roomObjects.put("Y", new RoomObject("Dining Room", new Point[] { new Point(15, 6), new Point(12, 7) }, null));
		roomObjects.put("H",
				new RoomObject("hall", new Point[] { new Point(20, 14), new Point(18, 12), new Point(18, 11) }, null));
		roomObjects.put("L", new RoomObject("Library", new Point[] { new Point(14, 20), new Point(16, 17) }, null));
		roomObjects.put("B", new RoomObject("Ball room",
				new Point[] { new Point(5, 8), new Point(5, 15), new Point(7, 14), new Point(7, 9) }, null));
		roomObjects.put("R",
				new RoomObject("Billiard Room", new Point[] { new Point(9, 18), new Point(12, 22) }, null));

		/* Adds the Names of the Weapons to a list of Weapons */
		ArrayList<Point> weaponPointList = new ArrayList<Point>();
		// kitchen
		weaponPointList.add(new Point(2, 5));
		// Ballroom
		weaponPointList.add(new Point(11, 6));
		// Conservatoty
		weaponPointList.add(new Point(20, 4));
		// Dining Room
		weaponPointList.add(new Point(3, 14));
		// Billiard Room
		weaponPointList.add(new Point(20, 11));
		// Library
		weaponPointList.add(new Point(19, 17));
		// Lounge
		weaponPointList.add(new Point(2, 23));
		// Hall
		weaponPointList.add(new Point(11, 22));
		// Study
		weaponPointList.add(new Point(19, 23));

		Collections.shuffle(weaponPointList);
		weapons.put("Candlestick", weaponPointList.get(0));
		weapons.put("Dagger", weaponPointList.get(1));
		weapons.put("Lead Pipe", weaponPointList.get(2));
		weapons.put("Revolver", weaponPointList.get(3));
		weapons.put("Rope", weaponPointList.get(4));
		weapons.put("Spanner", weaponPointList.get(5));

		// Adds weapons into rooms
		ArrayList<String> weaponStrings = new ArrayList<String>(weapons.keySet());
		ArrayList<RoomObject> temp = new ArrayList<RoomObject>(roomObjects.values());
		for (int i = 0; i < weaponStrings.size(); i++) {
			temp.get(i).setRoomObWeapon(weaponStrings.get(i));
		}
	}

	/**
	 * Shuffles deck, and distributes it to all players
	 */

	public void dealCards() {
		ArrayList<String> deck = new ArrayList<String>(charNames);
		deck.addAll(rooms);
		deck.addAll(weapons.keySet());
		deck.remove(secretWeapon);
		Collections.shuffle(deck);
		while (!deck.isEmpty()) {
			for (Player p : players) {
				if (!deck.isEmpty()) {
					p.addCard(deck.remove(0));
				}
			}
		}
	}

	/**
	 * Sets all of the secret cards for the game
	 */

	public void setSecretCards() {
		Collections.shuffle(charNames);
		Collections.shuffle(rooms);
		ArrayList<String> weaponStrings = new ArrayList<String>(weapons.keySet());
		Collections.shuffle(weaponStrings);

		secretRoom = rooms.remove(0);
		secretCharacter = charNames.remove(0);
		secretWeapon = weaponStrings.remove(0);
		
		answer= "ANSWERS ARE: \n";
		answer+= secretRoom + "\n";
		answer+= secretCharacter + "\n";	
		answer+= secretWeapon + "\n";
		
	}
	
	public String getAnswer(){
		return answer;
	}

	/**
	 * Creates a new player, and adds them into the game itself
	 *
	 * @param name
	 * @param playerNumber
	 * @param board
	 */

	public void addPlayer(String name, int playerNumber, Board board) {
		players.add(new Player(name, charNames.get(playerNumber), points.get(playerNumber), playerNumber + 1));
	}

	/**
	 * Begins a new game, resetting all of the feilds to their default values
	 */

	public void begin() {
		winner = null;
		activePlayers = new ArrayList<Player>(players);
		lostPlayers = new ArrayList<Player>();
	}

	/**
	 * Sets the players within the game
	 *
	 * @param gui
	 * @param board
	 */

	public void setPlayers(GUI gui, Board board) {
		String name = null;
		int numOfPlayers = gui.getNumofPlayers();
		while (numOfPlayers == 0) {
			numOfPlayers = gui.getNumofPlayers();
		}
		for (int i = 0; i < numOfPlayers; i++) {
			name = gui.askName("Player #" + (i + 1) + " Please enter in your name: ");
			while (name.length() == 0) {
				name = gui.askName("Player #" + (i + 1) + " Cannot enter empty name ");
			}
			addPlayer(name, i, board);
		}
	}

	/**
	 * Returns the winning Player
	 *
	 * @return
	 */

	public Player getWinner() {
		return winner;
	}

	/**
	 * Sets the winning player
	 *
	 * @param p
	 */

	public void setWinenr(Player p) {
		winner = p;
	}

	/**
	 * Returns the active players of the current game
	 *
	 * @return
	 */

	public ArrayList<Player> getActivePlayers() {
		return activePlayers;
	}

	/**
	 * Returns the losing players of the current game
	 *
	 * @return
	 */

	public ArrayList<Player> getLostPlayers() {
		return lostPlayers;
	}

	/**
	 * Returns the HashMap of the RoomObjects
	 *
	 * @return
	 */

	public HashMap<String, RoomObject> getRoomObject() {
		return roomObjects;
	}

	/**
	 * Returns the name of the secret room
	 *
	 * @return
	 */

	public String getSecretRoom() {
		return secretRoom;
	}

	/**
	 * Returns the name of the secret weapon
	 *
	 * @return
	 */

	public String getSecretWeapon() {
		return secretWeapon;
	}

	/**
	 * Returns the name of the secret character
	 *
	 * @return
	 */

	public String getSecretCharacter() {
		return secretCharacter;
	}

	/**
	 * Returns the arrayList of Players
	 *
	 * @return
	 */

	public ArrayList<Player> getPlayers() {
		return players;
	}

	/**
	 * Draws the weapons onto the board, within their specified rooms
	 *
	 * @param g
	 */

	public void drawWeapons(Graphics g) {
		// Candlestick
		ImageIcon image = new ImageIcon("src/resources/Candle Stick.png");
		Point point = weapons.get("Candlestick");
		g.drawImage(image.getImage(), point.x * 20, point.y * 20, 40, 40, null);
		// Dagger
		image = new ImageIcon("src/resources/Dagger.png");
		point = weapons.get("Dagger");
		g.drawImage(image.getImage(), point.x * 20, point.y * 20, 40, 40, null);
		// LeadPipe
		image = new ImageIcon("src/resources/LeadPipe.png");
		point = weapons.get("Lead Pipe");
		g.drawImage(image.getImage(), point.x * 20, point.y * 20, 40, 40, null);
		// Revolver
		image = new ImageIcon("src/resources/Revolver.png");
		point = weapons.get("Revolver");
		g.drawImage(image.getImage(), point.x * 20, point.y * 20, 40, 40, null);
		// Rope
		image = new ImageIcon("src/resources/Rope.png");
		point = weapons.get("Rope");
		g.drawImage(image.getImage(), point.x * 20, point.y * 20, 40, 40, null);
		// Spanner
		image = new ImageIcon("src/resources/Spanner.png");
		point = weapons.get("Spanner");
		g.drawImage(image.getImage(), point.x * 20, point.y * 20, 40, 40, null);
	}

	/**
	 * Returns the current player
	 *
	 * @return the current player
	 */

	public Player getCurrentPlayer() {
		playerNum++;
		if (playerNum > getActivePlayers().size() - 1) {
			playerNum = 0;
		}
		return getActivePlayers().get(playerNum);
	}

	/**
	 * Decrements the players lists
	 *
	 * @param p
	 */

	public void decrementPlayerlist(Player p) {
		playerNum--;
		otherPlayer--;
		getActivePlayers().remove(p);
	}

	/**
	 * Returns the next player scheduled to play
	 *
	 * @return player after the current player
	 */

	public Player getFollowingPlayer() {
		otherPlayer++;
		if (otherPlayer > getActivePlayers().size() - 1) {
			otherPlayer = 0;
		}
		return getActivePlayers().get(otherPlayer);
	}

}
