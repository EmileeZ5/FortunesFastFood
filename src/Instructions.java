// Name: Emilee Zhang, Nevetha Arullinkam
// Date: 5/28/2025
// Description: Fortune's Fast Food is a game where the player works at a burger shop, taking and fulfilling orders as quickly and accurately as possible

import java.awt.*;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;

public class Instructions extends Rectangle {

	public static boolean[] stepsCompleted;
	public static boolean instructionsWanted;
	public static Image arrow;

	// Constructor for Instructions setting all steps as not completed
	public Instructions() {
		stepsCompleted = new boolean[20];
		instructionsWanted = true;
		arrow = new ImageIcon("arrow.png").getImage();

	}

	// method recording the x and y coordinate of a mouse click
	public void mousePressed(MouseEvent e) {
		x = e.getX();
		y = e.getY();
	}

	// Draws instructions
	public void draw(Graphics g, int activeScreen, int burgerCook, int burgerAssembly) {
		// draw box with instructions
		g.setColor(Color.black);
		g.fillRect(218, 60, 434, 104);
		g.setColor(Color.white);
		g.fillRect(220, 62, 430, 100);
		// draw box allowing user to skip instructions
		g.setColor(Color.black);
		g.fillRect(1148, 838, 194, 54);
		g.setColor(Color.green);
		g.fillRect(1150, 840, 190, 50);
		g.setColor(Color.black);
		g.setFont(new Font("Consolas", Font.BOLD, 25));
		g.drawString("SKIP TUTORIAL", 1155, 875);
		// draw arrow to direct user to the take order button
		if (!stepsCompleted[0]) {
			// they can only take the order if they are on the orderstation screen, so make
			// sure they are on it when displaying arrow and checking for if the user
			// clicked 'take order'
			g.drawImage(arrow, 1000, 750, 150, 90, null);
			g.setColor(Color.black);
			g.setFont(new Font("Consolas", Font.BOLD, 25));
			g.drawString("TUTORIAL: ", 222, 85);
			g.setFont(new Font("Consolas", Font.PLAIN, 25));
			g.drawString("Take your first order", 350, 85);
			g.drawString("by pressing TAKE ORDER!", 222, 120);
			g.setFont(new Font("Consolas", Font.ITALIC, 20));
			g.drawString("FOLLOW THE RED ARROW FOR INSTRUCTIONS.", 222, 150);
			if (activeScreen == 1) {

				if (x > 1140 && x < 1340 && y > 770 && y < 820) {
					stepsCompleted[0] = true;
				}
			}
			// direct user back to the order station if theyre not on it
			// the logic repeats for all following code (if user is not on the correct
			// station, the arrows will point to the button guiding user to the correct
			// station)
			else {
				g.drawImage(arrow, 910, 32, 150, 90, null);
			}
		}
		// draw arrow to direct user to the drink station
		else if (!stepsCompleted[1] && stepsCompleted[0]) {
			g.setColor(Color.black);
			g.setFont(new Font("Consolas", Font.PLAIN, 25));
			g.drawString("You just took your first order! ", 220, 85);
			g.drawString("Start filling it out by going  ", 222, 120);
			g.drawString("to the DRINK STATION.", 222, 155);

			if (Math.pow(Math.pow((x - 1190), 2) + Math.pow((y - 70), 2), 0.5) < 45) {
				stepsCompleted[1] = true;
			} else {
				g.drawImage(arrow, 1000, 32, 150, 90, null);
			}
		}
		// draw arrow to direct user to the add cup
		else if (!stepsCompleted[2] && stepsCompleted[0] && stepsCompleted[1]) {
			g.setColor(Color.black);
			g.setFont(new Font("Consolas", Font.PLAIN, 25));
			g.drawString("This is the drink station. ", 222, 85);
			g.drawString("Start making a drink by ", 222, 120);
			g.drawString("getting a cup.", 222, 155);

			if (activeScreen == 2) {
				if (x > 509 && y > 599 && x < 771 && y < 871) {
					stepsCompleted[2] = true;
				} else {
					g.drawImage(arrow, 400, 600, 150, 90, null);
				}
			} else {
				g.drawImage(arrow, 1000, 32, 150, 90, null);
			}
		}
		// draw arrow to direct user to the choosing flavour
		else if (!stepsCompleted[3] && stepsCompleted[0] && stepsCompleted[1] && stepsCompleted[2]) {
			g.setColor(Color.black);
			g.setFont(new Font("Consolas", Font.PLAIN, 25));
			g.drawString("Now, choose a flavour. For ", 222, 85);
			g.drawString("this order, press Mountain", 222, 120);
			g.drawString("Dew.", 222, 155);
			if (activeScreen == 2) {
				if ((x > 283 && y > 209 && x < 375 && y < 350) || (x > 385 && y > 209 && x < 477 && y < 350)
						|| (x > 181 && y > 209 && x < 273 && y < 350) || (x > 79 && y > 209 && x < 171 && y < 350)) {
					stepsCompleted[3] = true;
				} else {
					g.drawImage(arrow, 140, 250, 150, 90, null);
				}
			} else {
				g.drawImage(arrow, 1000, 32, 150, 90, null);
			}
		}
		// draw arrow to direct user to the drink cap, if they click it before the drink
		// is done the arrow will disappear but the text indicates that they should
		// click it when the drink is finished pouring, so user is still aware of when
		// to click later
		else if (!stepsCompleted[4] && stepsCompleted[3] && stepsCompleted[0] && stepsCompleted[1]
				&& stepsCompleted[2]) {
			g.setColor(Color.black);
			g.setFont(new Font("Consolas", Font.PLAIN, 25));
			g.drawString("Put on a cap when the drink", 222, 85);
			g.drawString("is done, but only when the", 222, 120);
			g.drawString("drink is done filling up.", 222, 155);
			if (activeScreen == 2) {
				if (x > 779 && y > 524 && x < 916 && y < 796) {
					stepsCompleted[4] = true;
				} else {
					g.drawImage(arrow, 640, 600, 150, 90, null);
				}
			} else {
				g.drawImage(arrow, 1000, 32, 150, 90, null);
			}
		}
		// draw arrow to indicate where the user can add the straw
		else if (!stepsCompleted[5] && stepsCompleted[4] && stepsCompleted[3] && stepsCompleted[0] && stepsCompleted[1]
				&& stepsCompleted[2]) {
			g.setColor(Color.black);
			g.setFont(new Font("Consolas", Font.PLAIN, 25));
			g.drawString("Put in a straw if the cap ", 222, 85);
			g.drawString("is on!", 222, 120);
			if (activeScreen == 2) {
				if (x > 904 && y > 524 && x < 1031 && y < 796) {
					stepsCompleted[5] = true;
				} else {
					g.drawImage(arrow, 765, 600, 150, 90, null);
				}
			} else {
				g.drawImage(arrow, 1000, 32, 150, 90, null);
			}
		}
		// draw arrow to indicate where user can submit the drink order
		else if (!stepsCompleted[6] && stepsCompleted[5] && stepsCompleted[4] && stepsCompleted[3] && stepsCompleted[0]
				&& stepsCompleted[1] && stepsCompleted[2]) {
			g.setColor(Color.black);
			g.setFont(new Font("Consolas", Font.PLAIN, 25));
			g.drawString("Now, submit the drink to the", 222, 85);
			g.drawString("order. You can go through ", 222, 120);
			g.drawString("orders with the arrows.", 222, 155);
			if (activeScreen == 2) {
				g.drawImage(arrow, 1000, 750, 150, 90, null);
				if (x > 1140 && x < 1340 && y > 770 && y < 820) {
					stepsCompleted[6] = true;
				}
			} else {
				g.drawImage(arrow, 1000, 32, 150, 90, null);
			}
		}

		// draw image telling user to go to the cookstation
		else if (!stepsCompleted[7] && stepsCompleted[6] && stepsCompleted[5] && stepsCompleted[4] && stepsCompleted[3]
				&& stepsCompleted[0] && stepsCompleted[1] && stepsCompleted[2]) {
			g.setColor(Color.black);
			g.setFont(new Font("Consolas", Font.PLAIN, 25));
			g.drawString("Now, go to the cook station", 222, 85);
			g.drawString("to cook the burger meat.", 222, 120);
			if (Math.pow(Math.pow((x - 1290), 2) + Math.pow((y - 70), 2), 0.5) < 45) {
				stepsCompleted[7] = true;
			} else {
				g.drawImage(arrow, 1100, 32, 150, 90, null);
			}
		}
		// draw an image telling the user to click on the 'add burger' option
		else if (!stepsCompleted[8] && stepsCompleted[7] && stepsCompleted[6] && stepsCompleted[5] && stepsCompleted[4]
				&& stepsCompleted[3] && stepsCompleted[0] && stepsCompleted[1] && stepsCompleted[2]) {
			g.setColor(Color.black);
			g.setFont(new Font("Consolas", Font.PLAIN, 25));
			g.drawString("Add as meats as you want to", 222, 85);
			g.drawString("the grill. They cook over", 222, 120);
			g.drawString("time, so watch the timer.", 222, 155);
			if (activeScreen == 3) {
				// if the user skipped ahead and already figured out how to add burgers, skip
				// this step and let them automatically pass it
				if (!(burgerAssembly > 8)) {
					g.drawImage(arrow, 300, 700, -150, 90, null);
					if (x > 14 && x < 236 && y > 554 && y < 876)
						stepsCompleted[8] = true;
				} else {
					stepsCompleted[8] = true;
				}
			} else {
				g.drawImage(arrow, 1100, 32, 150, 90, null);
			}
		}
		// teaching user how to use the trash
		else if (!stepsCompleted[9] && stepsCompleted[8] && stepsCompleted[7] && stepsCompleted[6] && stepsCompleted[5]
				&& stepsCompleted[4] && stepsCompleted[3] && stepsCompleted[0] && stepsCompleted[1]
				&& stepsCompleted[2]) {
			g.setColor(Color.black);
			g.setFont(new Font("Consolas", Font.PLAIN, 25));
			g.drawString("After adding it, you can trash ", 222, 85);
			g.drawString("the burgers if something goes", 222, 120);
			g.drawString("wrong. Try now!", 222, 155);
			if (activeScreen == 3) {
				g.drawImage(arrow, 330, 250, -150, 90, null);
				if (x > 44 && y > 209 && x < 196 && y < 361 && burgerCook != 0) {
					stepsCompleted[9] = true;
				}
				if (burgerAssembly > 8) {
					stepsCompleted[9] = true;
				}
			} else {
				g.drawImage(arrow, 1100, 32, 150, 90, null);
			}
		}

		// telling user to take off a burger, adding it to the assembly station
		else if (!stepsCompleted[10] && stepsCompleted[9] && stepsCompleted[8] && stepsCompleted[7] && stepsCompleted[6]
				&& stepsCompleted[5] && stepsCompleted[4] && stepsCompleted[3] && stepsCompleted[0] && stepsCompleted[1]
				&& stepsCompleted[2]) {
			g.setColor(Color.black);
			g.setFont(new Font("Consolas", Font.PLAIN, 25));
			g.drawString("After trashing, add meat and", 222, 85);
			g.drawString("move it to the assembly ", 222, 120);
			g.drawString("station in the same way. ", 222, 155);
			if (activeScreen == 3) {
				g.drawImage(arrow, 350, 430, -150, 90, null);
				g.drawImage(arrow, 300, 700, -150, 90, null);

				if (burgerAssembly > 0) {
					stepsCompleted[10] = true;
				}
			} else {
				g.drawImage(arrow, 1100, 32, 150, 90, null);
			}
		}

		// draw image telling user to go to the assembly station
		else if (!stepsCompleted[11] && stepsCompleted[10] && stepsCompleted[9] && stepsCompleted[8]
				&& stepsCompleted[7] && stepsCompleted[6] && stepsCompleted[5] && stepsCompleted[4] && stepsCompleted[3]
				&& stepsCompleted[0] && stepsCompleted[1] && stepsCompleted[2]) {
			g.setColor(Color.black);
			g.setFont(new Font("Consolas", Font.PLAIN, 25));
			g.drawString("After moving it, go to the  ", 222, 85);
			g.drawString("assembly station to finish", 222, 120);
			g.drawString("the burger.", 222, 155);

			if (Math.pow(Math.pow((x - 1390), 2) + Math.pow((y - 70), 2), 0.5) < 45) {
				stepsCompleted[11] = true;
			} else {
				g.drawImage(arrow, 1200, 32, 150, 90, null);
			}
		}

		// instruct user to add the top bun to the burger
		else if (!stepsCompleted[12] && stepsCompleted[11] && stepsCompleted[10] && stepsCompleted[9]
				&& stepsCompleted[8] && stepsCompleted[7] && stepsCompleted[6] && stepsCompleted[5] && stepsCompleted[4]
				&& stepsCompleted[3] && stepsCompleted[0] && stepsCompleted[1] && stepsCompleted[2]) {
			g.setColor(Color.black);
			g.setFont(new Font("Consolas", Font.PLAIN, 25));
			g.drawString("First, put the bottom bun.", 222, 85);
			if (activeScreen == 4) {

				if (x >= 0 && x <= 250 && y >= 70 && y < 225) {
					stepsCompleted[12] = true;
				} else {
					g.drawImage(arrow, 370, 180, -150, 90, null);
				}
			} else
				g.drawImage(arrow, 1200, 32, 150, 90, null);

		}

		// instruct user to add the rest of the toppings to the burger
		else if (!stepsCompleted[13] && stepsCompleted[12] && stepsCompleted[11] && stepsCompleted[10]
				&& stepsCompleted[9] && stepsCompleted[8] && stepsCompleted[7] && stepsCompleted[6] && stepsCompleted[5]
				&& stepsCompleted[4] && stepsCompleted[3] && stepsCompleted[0] && stepsCompleted[1]
				&& stepsCompleted[2]) {

			g.setColor(Color.black);
			g.setFont(new Font("Consolas", Font.PLAIN, 25));
			g.drawString("Now, place the rest of the", 222, 85);
			g.drawString("toppings on the left matching", 222, 120);
			g.drawString("the order on the right.", 222, 155);
			if (activeScreen == 4) {

				if (x >= 0 && x <= 250 && y >= 266 && y <= 846) {
					stepsCompleted[13] = true;
					x = -1;
					y = -1;
				} else {
					g.drawImage(arrow, 370, 500, -150, 90, null);
				}
			} else
				g.drawImage(arrow, 1200, 32, 150, 90, null);
		}
		// inform user of minigame rules
		else if (!stepsCompleted[14] && stepsCompleted[13] && stepsCompleted[12] && stepsCompleted[11]
				&& stepsCompleted[10] && stepsCompleted[9] && stepsCompleted[8] && stepsCompleted[7]
				&& stepsCompleted[6] && stepsCompleted[5] && stepsCompleted[4] && stepsCompleted[3] && stepsCompleted[0]
				&& stepsCompleted[1] && stepsCompleted[2]) {
			g.setColor(Color.black);
			g.setFont(new Font("Consolas", Font.PLAIN, 25));
			g.drawString("Try to land the bar in the", 222, 85);
			g.drawString("green center to have accurate", 222, 120);
			g.drawString("topping placement.", 222, 155);
			if (activeScreen == 4) {

				if (x >= 0 && x <= 1440 && y >= 0 && y <= 900) {
					stepsCompleted[14] = true;
					x = -1;
					y = -1;
				}
			} else
				g.drawImage(arrow, 1200, 32, 150, 90, null);
		}

		// inform user of how to add meat
		else if (!stepsCompleted[15] && stepsCompleted[14] && stepsCompleted[13] && stepsCompleted[12]
				&& stepsCompleted[11] && stepsCompleted[10] && stepsCompleted[9] && stepsCompleted[8]
				&& stepsCompleted[7] && stepsCompleted[6] && stepsCompleted[5] && stepsCompleted[4] && stepsCompleted[3]
				&& stepsCompleted[0] && stepsCompleted[1] && stepsCompleted[2]) {
			g.setColor(Color.black);
			g.setFont(new Font("Consolas", Font.PLAIN, 25));
			g.drawString("Next, place the meat by ", 222, 85);
			g.drawString("clicking on the pile. The ", 222, 120);
			g.drawString("meat at the top is added.", 222, 155);
			if (activeScreen == 4) {

				if (x >= 0 && x <= 1440 && y >= 0 && y <= 900) {
					stepsCompleted[15] = true;
				}
			} else
				g.drawImage(arrow, 1200, 32, 150, 90, null);
		}

		// inform user of minigame rules
		else if (!stepsCompleted[16] && stepsCompleted[15] && stepsCompleted[14] && stepsCompleted[13]
				&& stepsCompleted[12]
				&& stepsCompleted[11] && stepsCompleted[10] && stepsCompleted[9] && stepsCompleted[8]
				&& stepsCompleted[7] && stepsCompleted[6] && stepsCompleted[5] && stepsCompleted[4] && stepsCompleted[3]
				&& stepsCompleted[0] && stepsCompleted[1] && stepsCompleted[2]) {
			g.setColor(Color.black);
			g.setFont(new Font("Consolas", Font.PLAIN, 25));
			g.drawString("When all the ingredients", 222, 85);
			g.drawString("are placed, put the top bun.", 222, 120);
			if (activeScreen == 4) {

				if (x >= 0 && x <= 250 && y >= 70 && y < 225) {
					stepsCompleted[16] = true;
					x = -1;
					y = -1;
				} else {
					g.drawImage(arrow, 370, 180, -150, 90, null);
				}
			} else
				g.drawImage(arrow, 1200, 32, 150, 90, null);
		}

		// inform user of the trash button that is activated in gamepanel now
		else if (!stepsCompleted[17] && stepsCompleted[16] && stepsCompleted[15] && stepsCompleted[14]
				&& stepsCompleted[13] && stepsCompleted[12]
				&& stepsCompleted[11] && stepsCompleted[10] && stepsCompleted[9] && stepsCompleted[8]
				&& stepsCompleted[7] && stepsCompleted[6] && stepsCompleted[5] && stepsCompleted[4] && stepsCompleted[3]
				&& stepsCompleted[0] && stepsCompleted[1] && stepsCompleted[2]) {
			g.setColor(Color.black);
			g.setFont(new Font("Consolas", Font.PLAIN, 25));
			g.drawString("If you make a mistake, you", 222, 85);
			g.drawString("can delete the whole burger.", 222, 120);
			g.drawString("Click anywhere else if not.", 222, 155);
			if (activeScreen == 4) {
				g.drawImage(arrow, 770, 130, 150, 90, null);
				if (x >= 0 && x <= 1440 && y >= 0 && y <= 900) {
					stepsCompleted[17] = true;
					x = -1;
					y = -1;
				}
			} else {
				g.drawImage(arrow, 1200, 32, 150, 90, null);
			}
		}
		// draw arrow to indicate where user can submit the burger order
		else if (!stepsCompleted[18] && stepsCompleted[17] && stepsCompleted[16] && stepsCompleted[15]
				&& stepsCompleted[14] && stepsCompleted[13] && stepsCompleted[12]
				&& stepsCompleted[11] && stepsCompleted[10] && stepsCompleted[9] && stepsCompleted[8]
				&& stepsCompleted[7] && stepsCompleted[6] && stepsCompleted[5] && stepsCompleted[4] && stepsCompleted[3]
				&& stepsCompleted[0] && stepsCompleted[1] && stepsCompleted[2]) {
			g.setColor(Color.black);
			g.setFont(new Font("Consolas", Font.PLAIN, 25));
			g.drawString("Finally, you can submit the", 222, 85);
			g.drawString("burger to its order. ", 222, 120);
			if (activeScreen == 4) {
				g.drawImage(arrow, 1000, 750, 150, 90, null);
				if (x > 1140 && x < 1340 && y > 770 && y < 820) {
					stepsCompleted[18] = true;
					x = -1;
					y = -1;
				}
			} else {
				g.drawImage(arrow, 1200, 32, 150, 90, null);
			}
		}
		// inform user of end condition and that they have completed the tutorial
		else if (!stepsCompleted[19] && stepsCompleted[18] && stepsCompleted[17] && stepsCompleted[16]
				&& stepsCompleted[15] && stepsCompleted[14] && stepsCompleted[13] && stepsCompleted[12]
				&& stepsCompleted[11] && stepsCompleted[10] && stepsCompleted[9] && stepsCompleted[8]
				&& stepsCompleted[7] && stepsCompleted[6] && stepsCompleted[5] && stepsCompleted[4] && stepsCompleted[3]
				&& stepsCompleted[0] && stepsCompleted[1] && stepsCompleted[2]) {
			g.setColor(Color.black);
			g.setFont(new Font("Consolas", Font.PLAIN, 25));
			g.drawString("Tutorial Complete! To be ", 222, 85);
			g.drawString("promoted to a new job, buy", 222, 120);
			g.setFont(new Font("Consolas", Font.PLAIN, 24));
			g.drawString("the scooter from UPGRADES!", 221, 155);

			if (x >= 0 && x <= 1440 && y >= 0 && y <= 900) {
				stepsCompleted[19] = true;
			}
		}
	}

	// Sets all instructions steps as completed
	public void skipInstructions() {

		for (int i = 0; i < 20; i++) {
			stepsCompleted[i] = true;
		}
	}

	// skip all the levels if you choose to
	public boolean instructionsView() {
		if (x >= 1150 && x <= 1340 && y >= 840 && y <= 890) {
			skipInstructions();
			return false;
		}
		return true;
	}

}