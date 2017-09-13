package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import javax.swing.ImageIcon;

public class Board {

	public static String fileName = "src/resources/board.jpeg";
	public static ImageIcon img = new ImageIcon(fileName);
	private String[][] board = new String[25][24];
	public static final int tileWidth = 480 / 24;
	public static final int tileHeight = 500 / 25;

	public Board() {
		populateRooms();
		populateBorder();
		populateStairs();
	}

	/**
	 * Draws the hovering tile onto the board
	 *
	 * @param g
	 * @param xPoint
	 * @param yPoint
	 */

	public void fillTile(Graphics g, int xPoint, int yPoint) {
		drawBoard(g);
		if (board[yPoint][xPoint] == null) {
			g.setColor(new Color(250, 250, 210));
			g.fillRect(xPoint * 20, yPoint * 20, 20, 20);
		}
	}

	/**
	 * Draws the entire board
	 *
	 * @param g
	 * @param string
	 */

	public void drawBoard(Graphics g) {
		g.clearRect(0, 0, 480, 500);
		g.drawImage(img.getImage(), 0, 0, 480, 500, null);
		for (int row = 0; row < board.length; row++) {
			for (int col = 0; col < board[0].length; col++) {
				if (board[row][col] != null) {
					switch (board[row][col]) {
					case "1":
						String MissScarlettToken = "src/resources/Miss ScarlettToken.png";
						ImageIcon imgScar = new ImageIcon(MissScarlettToken);
						g.drawImage(imgScar.getImage(), col * 20, row * 20, 20, 20, null);
						break;
					case "2":
						String ColonelMustardToken = "src/resources/Colonel MustardToken.png";
						ImageIcon imgMus = new ImageIcon(ColonelMustardToken);
						g.drawImage(imgMus.getImage(), col * 20, row * 20, 20, 20, null);
						break;
					case "3":
						String MrsWhiteToken = "src/resources/Mrs WhiteToken.png";
						ImageIcon imgWhi = new ImageIcon(MrsWhiteToken);
						g.drawImage(imgWhi.getImage(), col * 20, row * 20, 20, 20, null);
						break;
					case "4":
						String ReverendGreenToken = "src/resources/Reverend GreenToken.png";
						ImageIcon imgGre = new ImageIcon(ReverendGreenToken);
						g.drawImage(imgGre.getImage(), col * 20, row * 20, 20, 20, null);
						break;
					case "5":
						String MrsPeacockToken = "src/resources/Mrs PeacockToken.png";
						ImageIcon imgPea = new ImageIcon(MrsPeacockToken);
						g.drawImage(imgPea.getImage(), col * 20, row * 20, 20, 20, null);
						break;
					case "6":
						String ProfessorPlumToken = "src/resources/Professor PlumToken.png";
						ImageIcon imgPlu = new ImageIcon(ProfessorPlumToken);
						g.drawImage(imgPlu.getImage(), col * 20, row * 20, 20, 20, null);
						break;
					}
				}
			}
		}

	}

	/**
	 * Sets and changes the characters coordinates within the board array
	 *
	 * @param Player
	 * @param Point
	 * @param RoomObject
	 */

	public void movePlayer(Player p, Point newPos, RoomObject room) {
		if (!p.isInRoom()) {
			board[p.getPlayerPoint().y][p.getPlayerPoint().x] = null;
		}
		p.setInRoom(room);

		setPlayerPosition(p, room, newPos);
		if (!p.isInRoom())
			board[p.getPlayerPoint().y][p.getPlayerPoint().x] = "" + p.getPlayerNum();
	}

	/**
	 * Sets the players position on the board
	 *
	 * @param p
	 * @param room
	 * @param newPos
	 */

	private void setPlayerPosition(Player p, RoomObject room, Point newPos) {
		if (room != null) {
			Point shortestDoor = null;
			int shortestPath = -1;
			for (Point door : room.getRoomObDoors()) {
				int xMoved = Math.abs(p.getPlayerPoint().x - door.y);
				int yMoved = Math.abs(p.getPlayerPoint().y - door.x);
				if (shortestPath == -1 || xMoved + yMoved < shortestPath) {
					shortestPath = xMoved + yMoved;
					shortestDoor = door;
				}
			}
			p.setPlayerPoint(new Point(shortestDoor.y, shortestDoor.x));
		} else {
			p.setPlayerPoint(newPos);
		}
	}

	/**
	 * Generates the boarders within the board array
	 */

	public void populateBorder() {
		for (int i = 0; i < board[0].length; i++) {
			if (i != 9 && i != 14)
				board[0][i] = "*";
		}
		board[8][0] = "*";
		board[16][0] = "*";
		board[18][0] = "*";
		board[board.length - 1][8] = "*";
		board[board.length - 1][15] = "*";
		board[board.length - 1][6] = "*";
		board[20][board[0].length - 1] = "*";
		board[18][board[0].length - 1] = "*";
		board[14][board[0].length - 1] = "*";
		board[13][board[0].length - 1] = "*";
		board[7][board[0].length - 1] = "*";
		board[5][board[0].length - 1] = "*";
		board[1][6] = "*";
		board[1][17] = "*";
		board[board.length - 1][17] = "*";
	}

	/**
	 * Generates the rooms within the board array
	 */

	public void populateRooms() {
		// Draws Kitchen on the board, and fills with "K"
		for (int verticle = 1; verticle < 7; verticle++) {
			for (int horizontal = 0; horizontal < 6; horizontal++) {
				board[verticle][horizontal] = "K"; // for Kitchen
			}
		}
		// Draws Ballroom on the board, and fills with "B"
		for (int verticle = 1; verticle < 8; verticle++) {
			for (int horizontal = 8; horizontal < 16; horizontal++) {
				board[verticle][horizontal] = "B";
			}
		}
		board[1][8] = null;
		board[1][9] = null;
		board[1][14] = null;
		board[1][15] = null;
		// Draws Conservatory on the board, and fills with "C"
		for (int verticle = 1; verticle < 6; verticle++) {
			for (int horizontal = 18; horizontal < board[0].length; horizontal++) {
				board[verticle][horizontal] = "C";
			}
		}
		board[5][18] = null;

		// Draws Billiard Room on the board, and fills with "R"
		for (int verticle = 8; verticle < 13; verticle++) {
			for (int horizontal = 18; horizontal < board[0].length; horizontal++) {
				board[verticle][horizontal] = "R";
			}
		}
		// Draws Library on the board, and fills with "L"
		for (int verticle = 14; verticle < 19; verticle++) {
			for (int horizontal = 17; horizontal < board[0].length; horizontal++) {
				board[verticle][horizontal] = "L";
			}
		}
		board[14][17] = null;
		board[18][17] = null;
		// Draws Study on the board, and fills with "T"
		for (int verticle = 21; verticle < board.length; verticle++) {
			for (int horizontal = 17; horizontal < board[0].length; horizontal++) {
				board[verticle][horizontal] = "T";
			}
		}
		// Draws Hall on the board, and fills with "H"
		for (int verticle = 18; verticle < board.length; verticle++) {
			for (int horizontal = 9; horizontal < 15; horizontal++) {
				board[verticle][horizontal] = "H";
			}
		}
		// Draws Lounge on the board, and fills with "G"
		for (int verticle = 19; verticle < board.length; verticle++) {
			for (int horizontal = 0; horizontal < 7; horizontal++) {
				board[verticle][horizontal] = "G";
			}
		}
		// Draws Dining Room on the board, and fills with "Y"
		for (int verticle = 9; verticle < 16; verticle++) {
			for (int horizontal = 0; horizontal < 8; horizontal++) {
				board[verticle][horizontal] = "Y";
			}
		}
		board[9][5] = null;
		board[9][6] = null;
		board[9][7] = null;
		// Draws Center on the board, and fills with "X"
		for (int verticle = 10; verticle < 17; verticle++) {
			for (int horizontal = 10; horizontal < 15; horizontal++) {
				board[verticle][horizontal] = "X";
			}
		}
	}

	/**
	 * Checks if is Integer
	 *
	 * @param String
	 * @return Boolean
	 */

	public boolean isInteger(String s) {
		try {
			Integer.parseInt(s);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}

	/**
	 * Checks to see if the players move is a valid move
	 *
	 * @param setup
	 * @param movesLeft
	 *
	 * @param Player
	 * @param Point
	 * @return Boolean
	 */

	public boolean validMove(Player p, Point newPos, Setup setup, int movesLeft) {
		if (newPos != null && newPos.x > 0 && newPos.x < board[0].length && newPos.y > 0 && newPos.y < board.length
				&& (board[newPos.y][newPos.x] == null || !isInteger(board[newPos.y][newPos.x]))) {
			int xMoved = Math.abs(p.getPlayerPoint().x - newPos.x);
			int yMoved = Math.abs(p.getPlayerPoint().y - newPos.y);
			return xMoved + yMoved <= movesLeft;
		} else {
			int x = newPos.x;
			int y = newPos.y;
			RoomObject room = setup.getRoomObject().get(board[y][x]);
			if (room == null) {
				return false;
			}
			int shortestPath = -1;
			for (Point door : room.getRoomObDoors()) {
				int xMoved = Math.abs(p.getPlayerPoint().x - door.y);
				int yMoved = Math.abs(p.getPlayerPoint().y - door.x);
				if (shortestPath == -1 || xMoved + yMoved < shortestPath) {
					shortestPath = xMoved + yMoved;
				}
			}
			return shortestPath <= movesLeft;
		}
	}

	/**
	 * Generates the staircases within the board array
	 */

	public void populateStairs() {
		// Kitchen
		board[1][5] = "S";
		// Conservatory
		board[5][22] = "S";
		// Lounge
		board[19][0] = "S";
		// Study
		board[21][23] = "S";
	}

	/**
	 * Removes player from board if their accusation is incorrect
	 *
	 * @param Player
	 */

	public void removePlayer(Player p) {
		if (!p.isInRoom()) {
			board[p.getPlayerPoint().y][p.getPlayerPoint().x] = null;
		}
	}

	/**
	 * Gets the Board array
	 *
	 * @return
	 */

	public String[][] getBoardArray() {
		return board;
	}
}
