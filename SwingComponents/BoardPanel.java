package SwingComponents;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class BoardPanel extends JPanel {

	int pixel_x = 30;
	int pixel_y = 12;

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
	}

	@Override
	public void paint(Graphics g) {
	}

	public void drawImage(Graphics g) {
	}

}
