// Name: Emilee Zhang, Nevetha Arullinkam
// Date: 5/28/2025
// Description: Fortune's Fast Food is a game where the player works at a burger shop, taking and fulfilling orders as quickly and accurately as possible

import java.awt.*;
import java.awt.event.MouseEvent;
import javax.swing.*;

public class DrinkStation extends Rectangle {

	Drink drinkActive;
	public static Image backgroundImage;
	public boolean drinkPoured;
	public boolean cupSelected;
	public boolean capAdded;
	public boolean strawAdded;
	public String flavour;
	public static Image cupStack;
	public static Image holder;
	public static Image pouringLiquid;
	public static Image pouringCoke;
	public boolean pouringState;

	// Constructor for drinkstation
	public DrinkStation() {
		drinkActive = null;
		backgroundImage = new ImageIcon("drinkstation.png").getImage();
		holder = new ImageIcon("holder.png").getImage();
		drinkPoured = false;
		cupSelected = false;
		strawAdded = false;
		flavour = null;
		cupStack = new ImageIcon("cupStack.png").getImage();
		pouringLiquid = new ImageIcon("pouringLiquid.png").getImage();
		pouringCoke = new ImageIcon("pouringCoke.png").getImage();
		pouringState = false;

	}

	// Draws drinkstation
	public void draw(Graphics g) {
		g.drawImage(backgroundImage, 0, 0, 1040, 900, null);
		g.drawImage(cupStack, 430, 550, 390, 370, null);
		g.drawImage(holder, 780, 525, 250, 270, null);

		g.setColor(Color.black);
		g.setFont(new Font("Consolas", Font.BOLD, 30));
		g.drawString("CUP", 630, 600);
		g.drawString(" STRAW", 905, 530);
		g.drawString(" CAP", 800, 530);

		if (drinkActive != null && cupSelected == true) {
			drinkActive.drawCup(g, flavour, capAdded, strawAdded);
		}

		// Draws drink pour depending on which drink was selected
		if (pouringState) {
			if (flavour == "Coca Cola") {
				g.drawImage(pouringCoke, 109, 422, 50, 250, null);
			} else if (flavour == "Pepsi") {
				g.drawImage(pouringCoke, 207, 422, 50, 250, null);
			} else if (flavour == "Mountain Dew") {
				g.drawImage(pouringLiquid, 305, 422, 50, 250, null);
			} else if (flavour == "Sprite") {
				g.drawImage(pouringLiquid, 405, 422, 50, 250, null);
			}
		}

	}

	// method recording the x and y coordinate of a mouse click
	public void mousePressed(MouseEvent e) {
		x = e.getX();
		y = e.getY();
	}

	// Adds new drink if none exist yet
	public void addDrink() {
		if (x > 509 && y > 599 && x < 771 && y < 871) {
			if (drinkActive == null) {
				drinkActive = new Drink();
			}
			cupSelected = true;
		}
	}

	// Adds straw to drink if cap already added
	public void addStraw() {
		if (capAdded == true) {
			if (x > 904 && y > 524 && x < 1031 && y < 796) {
				strawAdded = true;
			}
		}
	}

	// Adds cap to drink if drink already poured
	public void addCap() {
		if (drinkPoured == true && pouringState == false) {
			if (x > 779 && y > 524 && x < 916 && y < 796) {
				capAdded = true;
			}
		}
	}

	// Selects flavour of drink depending on user input
	public void chooseFlavour() {
		if (drinkPoured == false && cupSelected == true) {
			pouringState = true;
			new javax.swing.Timer(5000, e -> {
				pouringState = false;
				((javax.swing.Timer) e.getSource()).stop();
			}).start();
			if (x > 79 && y > 209 && x < 171 && y < 350) {
				drinkPoured = true;
				flavour = "Coca Cola";
			} else if (x > 181 && y > 209 && x < 273 && y < 350) {
				drinkPoured = true;
				flavour = "Pepsi";
			} else if (x > 283 && y > 209 && x < 375 && y < 350) {
				drinkPoured = true;
				flavour = "Mountain Dew";
			} else if (x > 385 && y > 209 && x < 477 && y < 350) {
				drinkPoured = true;
				flavour = "Sprite";
			}
		}
	}

	// Checks if drink is ready to serve
	public boolean checkDrinkReady() {
		return (drinkActive != null && strawAdded);
	}

	// Resets drink to empty
	public void reset() {
		drinkActive = null;
		strawAdded = false;
		capAdded = false;
		drinkPoured = false;
		cupSelected = false;
		pouringState = false;
		flavour = null;
	}

	// Checks if trash clicked and removes drink if so
	public void checkTrash() {
		if (x > 899 && y > 149 && x < 1121 && y < 271) {
			reset();
		}
	}
}