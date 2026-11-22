import java.awt.*;
import java.awt.event.*;

import javax.swing.ImageIcon;

public class Upgrades extends Rectangle {

	public boolean isOpen;
	public boolean options[][];
	public boolean upgradesActivated[][];
	public int screenNumber;
	public boolean upgradeSelected[][];
	public Color buttonGreen;
	public Color lightGreen;
	public double upgradesMultiplier;
	public static Image scooterImage;
	public static Image promotion;
	public boolean gotPromoted;
	public static Image endOfGame;
	public static Image arrow;

// Constructor for ticket menu with empty order list
	public Upgrades() {
		super(1440, 900);
		scooterImage = new ImageIcon("scooter.png").getImage();
		promotion = new ImageIcon("promotion.png").getImage();
		endOfGame = new ImageIcon("congratulations.png").getImage();
		arrow = new ImageIcon("arrow.png").getImage();
		gotPromoted = false;
		options = new boolean[2][3];
		upgradesActivated = new boolean[2][3];
		upgradeSelected = new boolean[2][3];
		screenNumber = 1;
		upgradesMultiplier = 1;
		buttonGreen = new Color(50, 200, 50);
		lightGreen = new Color(150, 200, 150);
		for (int column = 1; column < 3; column++) {
			for (int row = 1; row < 4; row++) {
				upgradeSelected[column - 1][row - 1] = false;
				upgradesActivated[column - 1][row - 1] = false;
			}
		}
	}

// Updates position of mouse when click detected
	public void mousePressed(MouseEvent e) {
		x = e.getX();
		y = e.getY();
	}

// Draws on ticket menu
	public void draw(Graphics g, double bank) {
		if (GamePanel.viewUpgrades) {
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, 1440, 900);

			g.setColor(Color.white);
			g.fillRect(30, 820, 200, 70);
			g.fillRect(640, 820, 70, 70);
			g.fillRect(730, 820, 70, 70);
			g.drawImage(arrow, 640+70, 820, -70, 70, null);
			g.drawImage(arrow, 730, 820, 70, 70, null);
			g.setColor(Color.BLACK);
			g.setFont(new Font("Consolas", Font.BOLD, 30));
			g.drawString("RETURN TO", 30, 845);
			g.drawString("END OF DAY", 30, 880);
			g.setColor(Color.WHITE);
			g.drawString("Spending money: $" + Order.roundNum(bank), 940, 870);
			g.drawString("Tip Multiplier: x" + Order.roundNum(getUpgradesMultiplier()), 250, 870);

			if (screenNumber == 1) {
				g.fillRect(20, 20, 690, 250);
				g.fillRect(730, 20, 690, 250);
				g.fillRect(20, 290, 690, 250);
				g.fillRect(730, 290, 690, 250);
				g.fillRect(20, 560, 690, 250);
				g.fillRect(730, 560, 690, 250);
				for (int column = 1; column < 3; column++) {
					for (int row = 1; row < 4; row++) {
						g.setFont(new Font("Consolas", Font.BOLD, 40));
						g.setColor(buttonGreen);
						g.drawString("COST: $" + (column * 5 + 10 + row * 10), (column - 1) * 710 + 30,
								((row - 1) * 270) + 240);
						;

						if (upgradesActivated[column - 1][row - 1] == true) {
							g.setColor(lightGreen);
							g.fillRect((column - 1) * 710 + 20, ((row - 1) * 270) + 20, 690, 250);
							g.setColor(buttonGreen);
							g.fillRect((column - 1) * 710 + 500, ((row - 1) * 270) + 200, 200, 50);
							g.setColor(Color.white);
							g.setFont(new Font("Consolas", Font.BOLD, 30));
							g.drawString("OWNED", (column - 1) * 710 + 555, ((row - 1) * 270) + 235);
						}
						if (upgradesActivated[column - 1][row - 1] != true
								&& upgradeSelected[column - 1][row - 1] == false) {
							if (bank >= (column * 5 + 10 + row * 10)) {
								g.setColor(buttonGreen);
								g.fillRect((column - 1) * 710 + 500, ((row - 1) * 270) + 200, 200, 50);
								g.setColor(Color.white);
								g.setFont(new Font("Consolas", Font.BOLD, 30));
								g.drawString("PURCHASE", (column - 1) * 710 + 530, ((row - 1) * 270) + 235);
							} else {
								g.setColor(Color.red);
								g.fillRect((column - 1) * 710 + 500, ((row - 1) * 270) + 200, 200, 50);
								g.setColor(Color.white);
								g.setFont(new Font("Consolas", Font.BOLD, 30));
								g.drawString("NOT ENOUGH", (column - 1) * 710 + 520, ((row - 1) * 270) + 235);
							}

						}
						if (upgradeSelected[column - 1][row - 1] == true) {
							g.setColor(Color.black);
							g.setFont(new Font("Consolas", Font.BOLD, 40));
							g.drawString("CONFIRM?", (column - 1) * 710 + 250, ((row - 1) * 270) + 240);
							g.setColor(buttonGreen);
							g.fillRect((column - 1) * 710 + 500, ((row - 1) * 270) + 200, 200, 50);
							g.setColor(Color.red);
							g.fillRect((column - 1) * 710 + 500, ((row - 1) * 270) + 200, 100, 50);
							g.setColor(Color.WHITE);
							g.setFont(new Font("Consolas", Font.BOLD, 30));
							g.drawString("NO", (column - 1) * 710 + 530, ((row - 1) * 270) + 235);
							g.drawString("YES", (column - 1) * 710 + 620, ((row - 1) * 270) + 235);
						}
					}
				}
				g.setColor(Color.black);
				g.setFont(new Font("Consolas", Font.BOLD, 40));
				g.drawString("DAILY BUS PASS", 30, 60);
				g.drawString("CUSTOMER SERVICE SKILLS CLASS", 740, 60);
				g.drawString("SOCIAL SCIENCE CLASS", 30, 330);
				g.drawString("COMMUNITY BINGO MEMBERSHIP", 740, 330);
				g.drawString("COOKING CLASS", 30, 600);
				g.drawString("ETIQUETTE COURSE", 740, 600);
				g.setFont(new Font("Consolas", Font.PLAIN, 30));
				g.drawString("Be early to work more often!", 30, 110);
				g.drawString("Learn how to better talk to your ", 740, 110);
				g.drawString("customers!", 740, 150);
				g.drawString("Learn the science behind them!", 30, 380);
				g.drawString("Get to know your customers on fun", 740, 380);
				g.drawString("nights out!", 740, 420);
				g.drawString("Improve your cooking skills!", 30, 650);
				g.drawString("Learn more about classiness!", 740, 650);
				g.setFont(new Font("Consolas", Font.ITALIC, 30));
				g.drawString("Increase tips multiplier by 5%", 30, 150);
				g.drawString("Increase tips multiplier by 10%", 740, 190);
				g.drawString("Increase tips multiplier by 15%", 30, 420);
				g.drawString("Increase tips multiplier by 20%", 740, 460);
				g.drawString("Increase tips multiplier by 25%", 30, 690);
				g.drawString("Increase tips multiplier by 30%", 740, 690);
			}
			if (screenNumber == 2) {
				g.drawImage(scooterImage, 20, 20, 1400, 790, null);
				if (bank >= 1000) {
					g.setColor(buttonGreen);
					g.fillRect(1180, 750, 220, 50);
					g.setColor(Color.white);
					g.setFont(new Font("Consolas", Font.BOLD, 40));
					g.drawString("PURCHASE", 1202, 785);

				} else {
					g.setColor(Color.red);
					g.fillRect(1180, 750, 220, 50);
					g.setColor(Color.white);
					g.setFont(new Font("Consolas", Font.BOLD, 40));
					g.drawString("NOT ENOUGH", 1180, 785);
				}
			}
		}
	}

	public double purchaseUpgrade(double bank) {
		if (screenNumber == 1) {
			for (int column = 1; column < 3; column++) {
				for (int row = 1; row < 4; row++) {
					if (x > ((column - 1) * 710 + 500) && x < (column - 1) * 710 + 700 && y > ((row - 1) * 270 + 200)
							&& y < ((row - 1) * 270 + 250) && bank >= (column * 5 + 10 + row * 10)) {
						if (upgradesActivated[column - 1][row - 1] == false
								&& upgradeSelected[column - 1][row - 1] == false) {
							upgradeSelected[column - 1][row - 1] = true;
							x = 0;
							y = 0;
						}

						else if (upgradeSelected[column - 1][row - 1] == true) {
							if (!(x > ((column - 1) * 710 + 500) && x < ((column - 1) * 710 + 600))
									&& bank >= (column * 5 + 10 + row * 10)) {
								upgradesActivated[column - 1][row - 1] = true;
								bank = bank - (column * 5 + 10 + row * 10);
							}
							upgradeSelected[column - 1][row - 1] = false;
							x = 0;
							y = 0;
							for (int i = 1; i < 3; i++) {
								for (int j = 1; j < 4; j++) {
									if (!(bank >= (i * 5 + 10 + j * 10))) {
										upgradeSelected[i - 1][j - 1] = false;

									}
								}
							}
							break;
						}

					}
				}
			}
		} else {
			if (x >= 1180 && x <= 1400 && y >= 750 && y <= 800 && bank >= 1000) {
				gotPromoted = true;
			}
		}
		return bank;
	}

	public void changeScreen() {
		if (x > 640 && x < 710 && y > 820 && y < 890) {
			screenNumber = Math.max(1, screenNumber - 1);
		}
		if (x > 730 && x < 800 && y > 820 && y < 890) {
			screenNumber = Math.min(2, screenNumber + 1);
		}
	}

	public double getUpgradesMultiplier() {
		upgradesMultiplier = 1;
		for (int column = 1; column < 3; column++) {
			for (int row = 1; row < 4; row++) {
				if (upgradesActivated[column - 1][row - 1]) {
					upgradesMultiplier = upgradesMultiplier * (1 + (column + 2 * row - 2) * 0.05);
				}
			}
		}
		return upgradesMultiplier;
	}

	public void drawPromotion(Graphics g) {
		g.drawImage(endOfGame, 0, 0, 1440, 900, null);
	}
}