// Name: Emilee Zhang, Nevetha Arullinkam
// Date: 5/28/2025
// Description: Fortune's Fast Food is a game where the player works at a burger shop, taking and fulfilling orders as quickly and accurately as possible

import java.awt.*;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;

// Constructor for AssemblyStation
public class AssemblyStation extends Rectangle {

	public static Burger[] burgersOnTray;
	public Burger activeBurger;
	public static int numberOnTray;
	public static Image topBun;
	public static Image bottomBun;
	public static Image[] toppingImages;
	public static Image backgroundImage;

	public Minigame minigame;

	public AssemblyStation() {
		burgersOnTray = new Burger[9];
		numberOnTray = 0;
		activeBurger = null;
		minigame = new Minigame();

	}

	// Creates images of toppings and background
	static {
		toppingImages = new Image[7];
		for (int i = 2; i < 7; i++) {
			toppingImages[i - 2] = new ImageIcon("toppings" + i + ".png").getImage();
		}
		topBun = new ImageIcon("top_bun.png").getImage();
		bottomBun = new ImageIcon("bottom_bun.png").getImage();
		backgroundImage = new ImageIcon("assemblystation.png").getImage();
	}

	// method recording the x and y coordinate of a mouse click
	public void mousePressed(MouseEvent e) {
		x = e.getX();
		y = e.getY();
	}

	// Draws assemblystation
	public void draw(Graphics g) {
		g.drawImage(backgroundImage, 0, 0, 1040, 900, null);

		// draw all burger meats that have been placed by checking each spot in the
		// array to see if the burger exists at that spot
		for (int row = 0; row < 8; row++) {
			// if the burger does exist, draw it
			if (burgersOnTray[row] != null) {
				burgersOnTray[row].drawInAssembly(g, 240, 700 - 30 * row);
			}
		}

		// draw active burger (if it exists)
		if (activeBurger != null) {
			activeBurger.drawActive(g, minigame.gameInProgress);
		}

		// Draw minigame
		minigame.draw(g);

	}

	// Adds meat to assembly from cook
	public static void addBurger(Burger burger) {

		for (int row = 0; row < 9; row++) {
			if (burgersOnTray[row] == null) {
				burgersOnTray[row] = burger;
				numberOnTray++;
				break;
			}

		}

	}

	// Adds topping to burger
	public void addTopping(int toppingNum) {
		// Adds bottom bun or top bun or normal topping

		// if non-bun clicked as first item, ignore
		if (activeBurger == null && toppingNum != 0) {
			return;
		}

		if (activeBurger == null && toppingNum == 0) {
			// add bottom bun
			// creates temp burger object until meat placed
			activeBurger = new Burger();
			activeBurger.itemsAssembled = "0";
			activeBurger.toppingAccuracy[0] = 0;
		} else if (toppingNum == 1 && numberOnTray != 0) {
			// manually set position for previous to avoid overriding
			if (activeBurger.itemsAssembled.length() > 0) {
				int lastIndex = activeBurger.itemsAssembled.length() - 1;
				activeBurger.toppingAccuracy[lastIndex+1] = minigame.currentBarX;
				minigame.gameInProgress = false;
				Minigame.toppingInProgress = lastIndex;
			}

			// place meat, update temp burger object with cooked meat
			activeBurger.updateMeat(burgersOnTray[numberOnTray - 1]);
			burgersOnTray[numberOnTray - 1] = null;
			numberOnTray--;
			activeBurger.itemsAssembled += "1";
		} else if (toppingNum == 0) {
			// add top bun
			activeBurger.itemsAssembled += "0";
			// activeBurger.toppingAccuracy[activeBurger.itemsAssembled.length() - 2] = 0;
			activeBurger.status = "READY";
		} else {
			// add normal toppings
			activeBurger.itemsAssembled += toppingNum;
			// determine position of topping with minigame
			activeBurger.toppingAccuracy[activeBurger.itemsAssembled.length() - 2] = minigame
						.play(activeBurger.itemsAssembled.length() - 2, false);


		}

	}

	// Checks what was clicked in assembly station, updates minigame status if
	// needed
	// Refers to items as follows: 0=bun, 1=meat, rest=toppings
	public void checkTopping() {
		// Checking if burger is complete
		if (!minigame.gameInProgress) {
			checkTrashWithinClass();

			if (activeBurger != null && activeBurger.status.equals("READY")) {
				return;
			}

			// check if meat clicked and adds it if so
			if (activeBurger != null &&
					x > 240 && x < 240 + 180 &&
					y > (730 - 30 * (numberOnTray - 1)) && y < (730 - 30 * (numberOnTray - 1) + 150) &&
					!activeBurger.meatPlaced) {
				addTopping(1);
				activeBurger.meatPlaced = true;
			}

			// Checks if topping clicked and adds it if so
			for (int i = 0; i < 6; i++) {
				// Adds bun
				if (i == 0) {
					if (x >= 0 && x <= 250 && y >= 70 && y < 225) {
						addTopping(0);
					}
				} else if (activeBurger != null && activeBurger.itemsAssembled.length() < 9) {
					// Adds topping
					if (x >= 0 && x <= 250 && y >= 150 + 116 * i && y < 150 + 116 * (i + 1)) {
						addTopping(i + 2);
						break;
					}
				}
			}
		} else {
			checkMiniGame();
		}

		x = 0;
		y = 0;
	}

	// Checks status of minigame and updates status if needed
	public void checkMiniGame() {
		if (activeBurger != null
				&& activeBurger.itemsAssembled.charAt(activeBurger.itemsAssembled.length() - 1) != '1') {
			activeBurger.miniX = minigame.currentBarX + 600;
		} else {
			// activeBurger.miniX = 600;
		}
		if ((x != 0 || y != 0) && minigame.gameInProgress) {
			minigame.gameInProgress = false;
			Minigame.toppingInProgress = activeBurger.itemsAssembled.length() - 1;
		}
	}

	// Checks if trash clicked and removes object if needed
	public void checkTrash(boolean instructions) {
		if (instructions) {
			if (x > 899 && y > 149 && x < 1121 && y < 271) {
				activeBurger = null;
			}
		}
	}
	
	public void checkTrashWithinClass() {
		if (x > 899 && y > 149 && x < 1121 && y < 271) {
			activeBurger = null;
		}
	}
}
