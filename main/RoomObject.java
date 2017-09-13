package main;

import java.awt.Point;
import java.util.List;

public class RoomObject {
	private String name;
	private Point[] doors;
	private Point stairwell = null;
	private String weapon = "";
	private RoomObject oppositeRoom;

	public RoomObject(String name, Point[] doors, Point stairwell) {
		this.name = name;
		this.doors = doors;
		this.stairwell = stairwell;
	}

	/**
	 * Returns an array of doors within the specified room
	 *
	 * @return Point []
	 */

	public Point[] getRoomObDoors() {
		return doors;
	}

	/**
	 * Checks to see if the room contains a staircase
	 *
	 * @return Boolean
	 */

	public boolean containsStairs() {
		return stairwell != null;
	}

	/**
	 * Sets a weapon to be in the room
	 *
	 * @param String
	 */

	public void setRoomObWeapon(String s) {
		weapon = s;
	}

	/**
	 * Returns what object is within the room
	 *
	 * @return String
	 */

	public String getRoomObWeapon() {
		return weapon;
	}

	/**
	 * Sets the opposite room, of the current room
	 *
	 * @param RoomObject
	 */

	public void setOppositeRoomOb(RoomObject r) {
		oppositeRoom = r;
	}

	/**
	 * Returns the opposite room of the room
	 *
	 * @return RoomObject
	 */

	public RoomObject getOppositeRoom() {
		return oppositeRoom;
	}

	/**
	 * toString method
	 */

	public String toString() {
		return name;
	}

	/**
	 * Returns the rooms name
	 * @return
	 */

	public String getName() {
		return name;
	}
}
