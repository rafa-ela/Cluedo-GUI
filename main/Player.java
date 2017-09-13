package main;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

public class Player {

	private String name;
	private String character;
	private Point point;
	private ArrayList<String> hand = new ArrayList<String>();
	private int playerNum;
	private RoomObject room = null;
	private HashSet<String> innocentList = new HashSet<String>();

	public Player(String name, String character, Point points, int playerNum) {
		this.name = name;
		this.character = character;
		this.point = points;
		this.playerNum = playerNum;
	}

	/**
	 * Returns a room object if they are in a room
	 *
	 * @return RoomObject
	 */

	public RoomObject getRoom() {
		return room;
	}

	/**
	 *
	 * Adds a card to the player's innocent list
	 *
	 * @param the
	 *            name of the card you want to add on your notepad
	 */

	public void removeFromChecklist(String card) {
		innocentList.add(card);
	}

	/**
	 * @param card
	 *            to be removed from the innocent list
	 */

	public void addToSuspectList(String card) {
		innocentList.remove(card);
	}

	/**
	 * This is used in the CheckList panel so it will only display the the
	 * crossed out rooms, character and weapons
	 */

	public HashSet<String> getRemovedSuspects() {
		return innocentList;
	}

	/**
	 * Checks if the player is in a room
	 *
	 * @return Boolean
	 */

	public boolean isInRoom() {
		return room != null;
	}

	/**
	 * Sets the player to be in a room
	 *
	 * @param RoomObject
	 */

	public void setInRoom(RoomObject newRoom) {
		this.room = newRoom;
	}

	/**
	 * Returns the players name
	 *
	 * @return String
	 */

	public String getPlayerName() {
		return name;
	}

	/**
	 * Returns the players character name
	 *
	 * @return String
	 */

	public String getPlayerChar() {
		return character;
	}

	/**
	 * Changes and sets the players point position
	 *
	 * @param p
	 */

	public void setPlayerPoint(Point p) {
		point = p;
	}

	/**
	 * Returns the players point position
	 *
	 * @return Point
	 */

	public Point getPlayerPoint() {
		return point;
	}

	/**
	 * Returns the Arraylist of the players hand
	 *
	 * @return ArrayList<String>
	 */

	public ArrayList<String> getHand() {
		return hand;
	}

	/**
	 * Adds a card to the players hand
	 *
	 * @param String
	 */

	public void addCard(String s) {
		hand.add(s);
	}

	/**
	 * Returns the players number
	 *
	 * @return int
	 */

	public int getPlayerNum() {
		return playerNum;
	}

	/**
	 *
	 * Returns the room name the player is in
	 */

	public String roomName() {
		if (isInRoom())
			return room.toString();
		else
			return "Not in a room";
	}

	/**
	 * Moves the players point to the opposite room, and informs players what
	 * room they are in
	 */

	public void staircase() {
		point.x = room.getOppositeRoom().getRoomObDoors()[0].y;
		point.y = room.getOppositeRoom().getRoomObDoors()[0].x;
		room = room.getOppositeRoom();
	}

}
