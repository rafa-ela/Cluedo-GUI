package SwingComponents;

import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 * This class is responsible for drawing all the pictures displayed on the right
 * panel or the right side of the frame. I overrided the paint method of the
 * JPanel and added my own methods as well so I can use it for displaying
 * multiple cards(drawHand) and rendering the player's character photo
 * (drawCharacter)
 *
 * @author Raff and Angelo
 *
 */
public class VisualPanel extends JPanel {

	private ImageIcon image = null;
	private int startingX = 0;
	private String cardName;
	private int startingY = 0;

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
	}

	@Override
	public void paint(Graphics g) {
		if (image != null) {
			drawImage(g);
		}
	}

	/**
	 * Draws the card in the player's hand in a 2 x 3 grid
	 *
	 * @param hand
	 *            the name of the cards
	 */

	public void drawHand(ArrayList<String> hand) {
		startingX = 0;
		int index = 0;
		/*
		 * We are using a nested for loop because I set the layout of this panel
		 * to be GridLayout with 2 columns and 3 rows. This allows to draw the
		 * cards neatly and in order
		 */
		for (int row = 0; row < 3; row++) {
			for (int col = 0; col < 2; col++) {
				if (index >= hand.size()) {
					return;
				}
				cardName = "src/resources/" + hand.get(index) + "Card.png";
				startingX = col * 90; // each card is 90 pixels wide
				startingY = row * 120; // each card is 120 pixels tall
				image = new ImageIcon(cardName);
				drawImage(this.getGraphics());
				index++;
			}
		}
	}

	/**
	 * Draws the player's character on the top right panel
	 *
	 * @param imageName
	 *            character name
	 */

	public void drawCharacter(String imageName) {
		startingX = 45;
		startingY = 15;
		cardName = "src/resources/" + imageName + "Card.png";
		image = new ImageIcon(cardName);
		drawImage(this.getGraphics());

	}

	/**
	 * This method clears the cardPanel by redrawing it with a plain blue
	 * background. This allows the player to hide/show their cards whenever they
	 * toggle the 'show cards' button
	 */

	public void clearGraphics() {
		startingX = 0;
		for (int row = 0; row < 3; row++) {
			for (int col = 0; col < 2; col++) {
				cardName = "src/resources/defaultBackground.jpg";
				image = new ImageIcon(cardName);
				startingX = col * 90;
				startingY = row * 120;
				drawImage(this.getGraphics());
			}
		}
	}

	/**
	 * draws the image on the panel
	 *
	 * @param Graphics
	 *            of the this panel
	 */

	public void drawImage(Graphics g) {
		g.drawImage(image.getImage(), startingX, startingY, 90, 120, this);
	}

}
