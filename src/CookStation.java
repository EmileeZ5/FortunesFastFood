// Name: Emilee Zhang, Nevetha Arullinkam
// Date: 5/28/2025
// Description: Fortune's Fast Food is a game where the player works at a burger shop, taking and fulfilling orders as quickly and accurately as possible

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class CookStation extends Rectangle {

	// creating an array of burger meats on the stove
	public Burger[][] burgersOnStove;
	// creating boolean variable that stores "true" if user selected "ADD BURGER"
	// and "false" otherwise
	public boolean burgerSelected;
	public boolean removeSelected;
	public boolean trashSelected;
	// creating variable that records number of burger meats on the stove
	public int numberOnStove;
	// background image
	public Image backgroundImage;
	// background transparent image for pop-up message when user has too many meats
	// in AssemblyStation
	public static Color transparentColor;

	// Constructor method
	public CookStation() {
		super();
		burgersOnStove = new Burger[3][3];
		burgerSelected = false;
		numberOnStove = 0;
		removeSelected = false;
		backgroundImage = new ImageIcon("cookstation.png").getImage();
	}

	static {
		transparentColor = new Color(0, 0, 0, 100);
	}

	// method recording the x and y coordinate of a mouse click
	public void mousePressed(MouseEvent e) {
		x = e.getX();
		y = e.getY();
	}

	// method to draw the cookStation visuals
	public void draw(Graphics g) {
		// Background
		g.drawImage(backgroundImage, 0, 0, 1040, 900, null);
		g.drawImage(GamePanel.trash, 45, 210, 150, 150, null);

		g.setColor(Color.white);
		g.setFont(new Font("Consolas", Font.BOLD, 30));
		g.drawString("MOVE OFF", 55, 480);
		// display burger meats on the tray
		Burger.drawInPile(g, 30, 695);
		Burger.drawInPile(g, 30, 665);
		Burger.drawInPile(g, 30, 635);
		Burger.drawInPile(g, 30, 605);
		// text to indicate clicking the tray allows users to add burger meat
		g.setColor(Color.white);
		g.setFont(new Font("Consolas", Font.BOLD, 30));
		g.drawString("ADD MEAT", 55, 600);

		// if the user does want to add a burger meat onto the stove, display the slots
		if (burgerSelected == true && AssemblyStation.numberOnTray < 9) {
			Burger.drawSpots(g);
		}

		// draw all burger meats that have been placed by checking each spot in the
		// array to see if the burger exists at that spot
		for (int row = 0; row < 3; row++) {
			for (int column = 0; column < 3; column++) {
				// if the burger does exist, draw it
				if (burgersOnStove[row][column] != null) {
					burgersOnStove[row][column].drawOnStove(g);
				}
			}
		}
		// Draws circles where burger can be taken off
		if (removeSelected && AssemblyStation.numberOnTray < 9) {
			Burger.drawRemoval(g, burgersOnStove);
		}

		// Draws circles where burger can be trashed
		if (trashSelected == true && numberOnStove > 0) {
			Burger.drawTrash(g, burgersOnStove);
		}

		// Tells user when assembly station is full
		if (AssemblyStation.numberOnTray > 8) {
			g.setColor(transparentColor);
			g.fillRect(0, 200, 1440, 110);
			g.setColor(Color.red);
			g.setFont(new Font("Consolas", Font.BOLD, 30));
			g.drawString("Assembly station is full with 9 meats.", 170, 250);
			g.setFont(new Font("Consolas", Font.ITALIC, 30));
			g.drawString("Please either delete or use meats in the Assembly station", 50, 300);

		}
	}

	// Updates cook of the burger with time
	public void updateCook() {
		for (int row = 0; row < burgersOnStove.length; row++) {
			for (int col = 0; col < burgersOnStove[0].length; col++) {
				if (burgersOnStove[row][col] != null) {
					burgersOnStove[row][col].cook++;
				}
			}
		}
	}

	// method to add meat to the stove
	public void addMeatToStove() {
		// checks if user selected the "ADD BURGER" tray based on the coordinates of
		// their mouse click

		// sets burgerSelected to true if so, informing program that the user does wish
		if (x > 14 && x < 236 && y > 554 && y < 876 && numberOnStove < 9 && removeSelected == false && !trashSelected)
		// to place another burger
		{
			burgerSelected = true;
		}
		// if the user does wish to place another burger, finds out where they want it
		// placed
		if (burgerSelected) {
			for (int row = 0; row < 3; row++) {
				for (int column = 0; column < 3; column++) {
					// Checking every ellipse being checked if any is clicked using mathematical
					// ellipse formula to see if the x/y coordinates are within the ellipse
					if (Math.pow((x - (312 + 237 * row + 90)) / 90.0, 2)
							+ Math.pow((y - (230 + 220 * column + 30 + 70)) / 70.0, 2) <= 1.0) {
						// if there is currently no burger there, create a burger there
						if (burgersOnStove[row][column] == null) {
							burgersOnStove[row][column] = new Burger(row, column);
							numberOnStove++;
							burgerSelected = false;
						}
						// stop checking after placing one burger
						return;
					}
				}
			}
		}
	}

	public void takeOffMeat() {
		// checks if user selected the "remove burger" spatula based on the coordinates
		// off their mouse click
		if ((x > 14 && x < 236 && y > 399 && y < 541) && numberOnStove > 0 && AssemblyStation.numberOnTray < 9
				&& !trashSelected && !burgerSelected) // sets burgerSelected to true if so,
		// informing program that the user does wish
		// to place another burger
		{
			burgerSelected = false;
			removeSelected = true;
		}
		// if the user does wish to take off a burger, finds out where they want it
		// taken off
		if (removeSelected && AssemblyStation.numberOnTray < 9) {
			for (int row = 0; row < 3; row++) {
				for (int column = 0; column < 3; column++) {
					// Checking every ellipse being checked if any is clicked using mathematical
					// ellipse formula to see if the x/y coordinates are within the ellipse
					if (((Math.pow((x - (312 + 237 * row + 90)) / 90.0, 2)
							+ Math.pow((y - (230 + 220 * column + 30 + 65)) / 65.0, 2) <= 1.0)
							|| (Math.pow((x - (312 + 237 * row + 90)) / 90.0, 2)
									+ Math.pow((y - (230 + 220 * column + 30 + 75)) / 75.0, 2) <= 1.0))
							&& AssemblyStation.numberOnTray < 9) {
						// if there is currently no burger there, create a burger there
						if (burgersOnStove[row][column] != null) {
							burgersOnStove[row][column].status = "BUILD";
							AssemblyStation.addBurger(burgersOnStove[row][column]);
							burgersOnStove[row][column] = null;
							numberOnStove--;
							removeSelected = false;
						}
						// stop checking after placing one burger
						return;
					}
				}
			}
		}
	}

	// Checks if trash clicked and allows user to trash meat if so
	public void checkTrash() {
		if (x > 44 && y > 209 && x < 196 && y < 361 && numberOnStove > 0 && !removeSelected && !burgerSelected) {
			trashSelected = true;
		}

		if (trashSelected) {
			for (int row = 0; row < 3; row++) {
				for (int column = 0; column < 3; column++) {
					// Checking every ellipse being checked if any is clicked using mathematical
					// ellipse formula to see if the x/y coordinates are within the ellipse
					if (((Math.pow((x - (312 + 237 * row + 90)) / 90.0, 2)
							+ Math.pow((y - (230 + 220 * column + 30 + 65)) / 65.0, 2) <= 1.0)
							|| (Math.pow((x - (312 + 237 * row + 90)) / 90.0, 2)
									+ Math.pow((y - (230 + 220 * column + 30 + 75)) / 75.0, 2) <= 1.0))
							) {
						// if there is currently no burger there, create a burger there
						if (burgersOnStove[row][column] != null) {
							burgersOnStove[row][column] = null;
							numberOnStove--;
							trashSelected = false;
						}
						return;
					}
				}
			}
		}
	}

}
