// Name: Emilee Zhang, Nevetha Arullinkam
// Date: 5/28/2025
// Description: Fortune's Fast Food is a game where the player works at a burger shop, taking and fulfilling orders as quickly and accurately as possible

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.*;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;

public class Player {

    public int dayNum;
    public double earningsToday;
    public double earningsTotal;
    public double tipsToday;
    public double bank;
    public int x;
    public int y;
    public String error;
    public static Image endOfDay;

    // Constructor for player
    public Player() {
        earningsToday = 0;
        tipsToday = 0;
        earningsTotal = 0;
        bank = 1000;
        dayNum = 1;
        x = 0;
        y = 0;
        error = "";
		endOfDay = new ImageIcon("blackboard.png").getImage();

    }

    // Draws end of day screen displaying information regarding money earned
    public void drawEndScreen(Graphics g) {

        g.setColor(Color.black);
        g.fillRect(0, 0, 1440, 900);
        g.drawImage(endOfDay, 0, 0, 1440, 850, null);
        // Prints info
        g.setColor(Color.white);
        g.setFont(new Font("Consolas", Font.BOLD, 30));
        g.drawString("Day " + dayNum + " summary:", 400, 370);
        g.drawString("Day's earnings: $" + Order.roundNum(earningsToday), 400, 400);
        g.drawString("Total earnings: $" + Order.roundNum(earningsTotal), 400, 430);
        g.drawString("Day's tips: $" + Order.roundNum(tipsToday), 400, 460);
        g.drawString("Total spending money: $" + Order.roundNum(bank), 400, 490);

        // Displays button to continue
        g.setColor(Color.black);
        g.fillRect(550 - 5, 550 - 5, 300 + 10, 60 + 10);

        g.setColor(Color.green);
        g.fillRect(550, 550, 300, 60);

        g.setColor(Color.black);
        g.drawString("CONTINUE", 620, 590);
		g.setColor(Color.white);
		g.fillRect(30, 820, 200, 70);
        g.setColor(Color.white);
        g.setFont(new Font("Consolas", Font.BOLD, 30));
        g.drawString("VIEW", 50, 845);
        g.drawString("UPGRADES", 50, 880);
    }

    // method recording the x and y coordinate of a mouse click
    public void mousePressed(MouseEvent e) {
        x = e.getX();
        y = e.getY();
    }

    // Draws amount of spending money user has
    public void draw(Graphics g) {
        // Prints money in bank
        g.setColor(Color.black);
        g.setFont(new Font("Consolas", Font.BOLD, 30));
        g.drawString("Spending money: $" + Order.roundNum(tipsToday), 665, 78);
        g.drawString("Day #" + dayNum, 665, 112);

        // Prints error message
        if (!error.equals("")) {
            g.setFont(new Font("Consolas", Font.BOLD, 16));
            g.setColor(Color.white);
            g.fillRect(550 - 6, 750 - g.getFontMetrics().getHeight() - 4, g.getFontMetrics().stringWidth(error) + 12,
                    g.getFontMetrics().getHeight() + 12);
            g.setColor(Color.black);

            g.drawString(error, 550, 750);

        }
    }

    // Checks status of whether or not user wants to view upgrades and updates accordingly
    public void checkViewUpgrades() {
        if (x >= 30 && x <= 230 && y >= 820 && y <= 890) {
            if (GamePanel.viewUpgrades)
                GamePanel.viewUpgrades = false;
            else {
                GamePanel.viewUpgrades = true;

            }
            x = 0;
            y = 0;
        }
    }

    // Checks if user is ready to begin day
    public boolean checkDayBegin() {
        // Checks if clicked continue button, update max orders and reset number of
        // orders completed today
        if (x > 550 && x < 850 && y > 550 && y < 610) {
            x = 0;
            y = 0;
            dayNum++;
            Order.maxOrdersToday = (int) (dayNum * 1 / 3 + 3);
            Order.numOrdersCompletedToday = 0;
            Order.lastId = 0;
            earningsToday = 0;
            return true;
        }
        return false;
    }

    // Displays error message on screen
    public void showErrorMessage(String m) {
        error = m;
        new javax.swing.Timer(1000, e -> {
            error = "";
            ((javax.swing.Timer) e.getSource()).stop();
        }).start();

    }
}
