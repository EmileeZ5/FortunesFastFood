// Name: Emilee Zhang, Nevetha Arullinkam
// Date: 5/28/2025
// Description: Fortune's Fast Food is a game where the player works at a burger shop, taking and fulfilling orders as quickly and accurately as possible

import java.awt.*;
import java.awt.event.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.swing.ImageIcon;

public class TicketMenu extends Rectangle {

	public static final int SIDEBAR_WIDTH = 400;
	public static final int SIDEBAR_HEIGHT = 900;
	public static final int TICKET_ROW_WIDTH = 350;
	public static final int TICKET_ROW_HEIGHT = 50;
	public int activeTicket;
	public static Order currentOrders[];
	public boolean trashSelected;
	public int activeStationNumber;
	public boolean showingResult;
	public static Image assemblyIcon;
	public static Image cookIcon;
	public static Image drinkIcon;
	public static Image orderIcon;

	// Constructor for ticket menu with empty order list
	public TicketMenu() {
		super(SIDEBAR_WIDTH, SIDEBAR_HEIGHT);
		activeTicket = 0;
		currentOrders = new Order[10];
		trashSelected = false;
		activeStationNumber = 1;
		orderIcon = new ImageIcon("ordersicon.png").getImage();
		drinkIcon = new ImageIcon("drinksicon.png").getImage();
		assemblyIcon = new ImageIcon("burgersicon.png").getImage();
		cookIcon = new ImageIcon("fireicon.png").getImage();
	}

	public void setStation(int stationNumber) {
		activeStationNumber = stationNumber;

	}

	// Checks whether item is needed in active order
	public boolean checkItemNeeded(char item) {

		for (int i = 0; currentOrders[activeTicket] != null
				&& i < currentOrders[activeTicket].itemsNeeded.length; i++) {
			if (currentOrders[activeTicket].itemsNeeded[i] != null
					&& currentOrders[activeTicket].itemsNeeded[i].charAt(0) == item) {
				return true;
			}
		}
		return false;
	}

	// Close active ticket
	public void closeActive() {
		// Shift orders after activeTicket to the left
		for (int i = activeTicket; i < Order.ordersTaken - 1; i++) {
			currentOrders[i] = currentOrders[i + 1];
		}

		Order.ordersTaken--;
		currentOrders[Order.ordersTaken] = null;
		if (activeTicket >= Order.ordersTaken) {
			activeTicket = Math.max(0, Order.ordersTaken - 1);
		}

		showingResult = false;
		Order.numOrdersCompletedToday++;
	}

	// Checks if a button on the menu was pressed
	public String buttonPressed() {
		if (x > 1440 - 400 + 100 - 5 && x < 1440 - 400 + 100 - 5 + 400 - 100 - 100 + 10 && y > 770 - 5
				&& y < 770 - 5 + 50 + 10) {
			x = 0;
			y = 0;
			if (showingResult) {
				return "closeResult";
			} else {
				return "action";
			}
		} else if (x > 1060 && x < 1115 && y > 775 && y < 835) {
			x = 0;
			y = 0;
			return "left";
		} else if (x > 1365 && x < 1420 && y > 775 && y < 835) {
			x = 0;
			y = 0;
			return "right";
		} else {
			x = 0;
			y = 0;
			return "none";
		}
	}

	// Updates position of mouse when click detected
	public void mousePressed(MouseEvent e) {
		x = e.getX();
		y = e.getY();
	}

	
    // Updates order timer if active
	// from:
    // https://www.geeksforgeeks.org/scheduledexecutorservice-interface-in-java/ and
    // https://www.geeksforgeeks.org/runnable-interface-in-java/
    public void updateOrderTime() {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        // Define the task for counter
        Runnable orderTimerTask = new Runnable() {
            @Override
            public void run() {
                if (currentOrders[activeTicket] != null && !showingResult) {
					currentOrders[activeTicket].orderTimeTaken++;
				}
            }
        };

        // Schedule the counter task
        scheduler.scheduleAtFixedRate(orderTimerTask, 1, 1, TimeUnit.SECONDS);

    }

	// Draws on ticket menu
	public void draw(Graphics g, double multi) {
		int[] xPoints;
		int[] yPoints;
		int startResultY;

		// creating the bar where tickets are put on
		g.setColor(Color.BLACK);
		g.fillRect(0, 27, 1440, 25);
		g.setColor(Color.DARK_GRAY);
		g.fillRect(0, 29, 1440, 17);
		g.setColor(Color.GRAY);
		g.fillRect(0, 32, 1440, 12);
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(0, 34, 1440, 5);

		// creating the sidebar
		g.setColor(Color.DARK_GRAY);
		g.fillRect(1040, 0, SIDEBAR_WIDTH, SIDEBAR_HEIGHT);

		// Create the shadow effect on the buttons (makes it look 3D)
		g.setColor(Color.black);
		g.fillOval(1045, 25, 90, 90);
		g.setColor(Color.black);
		g.fillOval(1145, 25, 90, 90);
		g.setColor(Color.black);
		g.fillOval(1245, 25, 90, 90);
		g.setColor(Color.black);
		g.fillOval(1345, 25, 90, 90);

		// Station buttons
		g.drawImage(orderIcon, 1045, 25, 90, 90, null);
		g.drawImage(drinkIcon, 1145, 25, 90, 90, null);
		g.drawImage(cookIcon, 1245, 25, 90, 90, null);
		g.drawImage(assemblyIcon, 1345, 25, 90, 90, null);

		// Labels for station buttons
		g.setColor(Color.white);
		g.setFont(new Font("Consolas", Font.BOLD, 30));
		g.drawString("ORDER", 1045, 140);
		g.setColor(Color.white);
		g.setFont(new Font("Consolas", Font.BOLD, 30));
		g.drawString("DRINK", 1145, 140);
		g.setColor(Color.white);
		g.setFont(new Font("Consolas", Font.BOLD, 30));
		g.drawString("COOK", 1255, 140);
		g.setColor(Color.white);
		g.setFont(new Font("Consolas", Font.BOLD, 30));
		g.drawString("BUILD", 1345, 140);

		// Draws take order/other action button if station is not cookstation
		if (activeStationNumber != 3) {
			g.setColor(Color.black);
			g.fillRect(1440 - 400 + 100 - 5, 770 - 5, 400 - 100 - 100 + 10, 50 + 10);
			g.setColor(Color.green);
			g.fillRect(1440 - 400 + 100, 770, 400 - 100 - 100, 50);
			g.setColor(Color.black);
		}

		// Sets action button text depending on station
		g.setFont(new Font("Consolas", Font.BOLD, 25));
		if (activeStationNumber == 1) {
			g.drawString("TAKE ORDER", 1168, 770 + 50 - 15);
		}
		if (activeStationNumber == 2) {
			g.drawString("ASSIGN DRINK", 1155, 770 + 50 - 15);
		}
		if (activeStationNumber == 3) {

		}
		if (activeStationNumber == 4) {
			g.drawString("ASSIGN BURGER", 1155, 770 + 50 - 15);
		}

		// Arrows to switch between orders
		// Left arrow
		g.setColor(Color.black);
		g.fillRect(1440 - 400 + 20, 770 - 5, 45 + 10, 50 + 10);
		g.setColor(Color.green);
		// Triangle
		xPoints = new int[] { 1440 - 400 + 5 + 20, 1440 - 400 + 5 + 20 + 45, 1440 - 400 + 5 + 20 + 45 };
		yPoints = new int[] { 770 + 45 / 2, 770 + 45, 770 };
		g.fillPolygon(xPoints, yPoints, 3);

		// Right arrow
		g.setColor(Color.black);
		g.fillRect(1440 - 20 - 45 - 10, 770 - 5, 45 + 10, 50 + 10);
		g.setColor(Color.green);
		// Triangle
		xPoints = new int[] { 1440 - 20 - 45 + 5 - 10, 1440 - 20 - 45 + 5 - 10, 1440 - 20 - 45 + 5 - 10 + 45 };
		yPoints = new int[] { 770 + 45, 770, 770 + 45 / 2 };
		g.fillPolygon(xPoints, yPoints, 3);

		// Draws ticket if not showing result, and draws ticket result if showing result
		if (!showingResult) {
			// Draw active ticket
			// Draw outline
			g.setColor(Color.black);
			g.fillRect(1060, 155, TICKET_ROW_WIDTH + 10, 595);
			// Draw inside
			g.setColor(Color.white);
			g.fillRect(1065, 160, TICKET_ROW_WIDTH, 585);

			// Checks whether burger is done
			if (Order.ordersTaken != 0 && currentOrders[activeTicket] != null
					&& currentOrders[activeTicket].itemsSubmitted[0] != null) {
				// Makes top portion of ticket green to indicate burger is done (white part)
				g.setColor(new Color(204, 255, 179));
				g.fillRect(1065, 160, TICKET_ROW_WIDTH, 585 * 10 / 13);

				// Makes top portion of ticket green to indicate burger is done (grey part)
				g.setColor(new Color(153, 255, 102));
			} else {
				g.setColor(Color.LIGHT_GRAY);
			}

			// Draw rows (darker)
			for (int i = 0; i < 5; i++) {
				g.fillRect(1065, 210 + i * 100, TICKET_ROW_WIDTH, TICKET_ROW_HEIGHT);
			}

			// Checks whether drink was submitted
			if (Order.ordersTaken != 0 && currentOrders[activeTicket] != null
					&& currentOrders[activeTicket].itemsSubmitted[1] != null) {
				// Makes bottom portion of ticket green to indicate drink is done
				g.setColor(new Color(204, 255, 179));
				g.fillRect(1065, 160 + 585 * 11 / 13, TICKET_ROW_WIDTH, 585 * 2 / 13);
			}

			// Draw toppings for active ticket
			if (TicketMenu.currentOrders[activeTicket] != null && Order.ordersTaken > 0) {
				currentOrders[activeTicket].drawActiveTicket(g);
			}
		} else if (currentOrders[activeTicket] != null) {
			// Draw background of result screen where ticket used to be
			g.setColor(Color.white);
			g.fillRect(1065, 160, TICKET_ROW_WIDTH, 585);

			// Draw profit info
			startResultY = 310;
			g.setColor(Color.black);
			g.drawString("RECIEPT", 1085,
					startResultY - 40);
			g.drawString("COOK: " + Math.round(currentOrders[activeTicket].calculateCookScore()) + "%", 1085,
					startResultY);
			g.drawString("TOPPING ACCURACY: " + Math.round(currentOrders[activeTicket].calculateAccuracyScore()) + "%",
					1085, startResultY + 40 * 1);
			g.drawString(
					"TOPPING PLACEMENT: " + Math.round(currentOrders[activeTicket].calculatePlacementScore()) + "%",
					1085, startResultY + 40 * 2);
			if (currentOrders[activeTicket].calculateDrinkScore() == -1) {
				g.drawString("DRINK ACCURACY: N/A", 1085, startResultY + 40 * 3);
			} else {
				g.drawString("DRINK ACCURACY: " + Math.round(currentOrders[activeTicket].calculateDrinkScore()) + "%",
						1085, startResultY + 40 * 3);
			}
			g.drawString("TIME: " + Math.round(currentOrders[activeTicket].calculateTimeBonus()) + "%", 1085, startResultY + 40*4);
			g.drawString("Tips: $" + Order.roundNum(currentOrders[activeTicket].calculateTips(multi)), 1085,
					startResultY + 40 * 6);
			g.drawString("Total: $" + Order.roundNum(currentOrders[activeTicket].calculateRevenue()+
						currentOrders[activeTicket].calculateTips(multi)), 1085,
					startResultY + 40 * 7);

			// Draw button
			g.setColor(Color.yellow);
			g.fillRect(1440 - 400 + 100, 770, 400 - 100 - 100, 50);
			g.setColor(Color.black);
			g.drawString("CONTINUE", 1168, 770 + 50 - 15);

		}

		// Draw individual tickets on rack
		for (int i = 0; i < Order.ordersTaken; i++) {
			currentOrders[i].draw(g, i);
		}

	}
}
