package SwingComponents;

import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class DicePanel extends JPanel {

	// just a plain white picture
	private ImageIcon image = new ImageIcon("src/resources/dice0.png");

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
	}

	@Override
	public void paint(Graphics g) {
		drawImage(g);
	}

	/**
	 * Draws the given image onto the graphics
	 * @param g
	 */

	public void drawImage(Graphics g) {
		g.drawImage(image.getImage(), 0, 0, 75, 75, this);
	}

	/**
	 * Displays the number of die rolled by the player.
	 *
	 * @param diceRolled
	 *            the number of the dice the player rolled
	 */

	public void drawDice(int diceRolled) {
		String rolled = Integer.toString(diceRolled);
		String imageString = "src/resources/dice" + rolled + ".png";
		image = new ImageIcon(imageString);
		drawImage(this.getGraphics());
	}

}
