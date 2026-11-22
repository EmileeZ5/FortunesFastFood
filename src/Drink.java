// Name: Emilee Zhang, Nevetha Arullinkam
// Date: 5/28/2025
// Description: Fortune's Fast Food is a game where the player works at a burger shop, taking and fulfilling orders as quickly and accurately as possible

import java.awt.*;
import javax.swing.*;

public class Drink extends Food {
	public static Image cup;
	public static Image cupAndCap;
	public static Image cupAndCapAndStraw;
	public static double price = 1.99;
	public int type;
	public static String[] flavours = { "Coca Cola", "Pepsi", "Mountain Dew", "Sprite" };

	// Constructor for Drink with type of drink selected
	public Drink() {
		type = -1;
	}

	static {
		// Getting drink-related images
		cupAndCap = new ImageIcon("cupandcap.png").getImage();
		cupAndCapAndStraw = new ImageIcon("cupandcapandstraw.png").getImage();
		cup = new ImageIcon("cup.png").getImage();

	}

	// Gets price of drink
	public double getPrice() {
		return price;
	}

	// Draws drink cup depending on flavour and status
	public void drawCup(Graphics g, String flavour, boolean capAdded, boolean strawAdded) {
		int x = 9000;
		g.setColor(Color.black);
		if (flavour != null) {
			if (flavour.equals("Coca Cola")) {
				type = 0;
				x = 80;
			} else if (flavour.equals("Pepsi")) {
				type = 1;
				x = 185;
			} else if (flavour.equals("Mountain Dew")) {
				type = 2;
				x = 290;
			} else if (flavour.equals("Sprite")) {
				type = 3;
				x = 390;
			}
			if (!capAdded && !strawAdded)
				g.drawImage(cup, x, 650, 100, 150, null);
			if (capAdded && !strawAdded) {
				g.drawImage(cupAndCap, x, 650, 100, 152, null);
			}
			if (strawAdded) {
				g.drawImage(cupAndCapAndStraw, x, 565, 100, 240, null);
			}

		} else {
			g.drawImage(cup, 730, 720, 100, 155, null);

		}
	}

}
