// Name: Emilee Zhang, Nevetha Arullinkam
// Date: 5/28/2025
// Description: Fortune's Fast Food is a game where the player works at a burger shop, taking and fulfilling orders as quickly and accurately as possible

import java.awt.*;
import java.awt.event.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.swing.*;

public class GamePanel extends JPanel implements Runnable {

    public static final int GAME_WIDTH = 1440;
    public static final int GAME_HEIGHT = 900;
    public boolean endOfDay;
    public Thread gameThread;
    public Image image;
    public Graphics graphics;
    public StationMenu station;
    public Player user;
    public OrderStation orderStation;
    public DrinkStation drinkStation;
    public CookStation cookStation;
    public AssemblyStation assemblyStation;
    public TicketMenu ticketMenu;
    public Sound sounds;
    public static Image trash;
    public Upgrades upgrades;
    public static boolean waitingToEndDay;
    public static boolean viewUpgrades;
    public Instructions instructions;
    public static boolean viewInstructions;

    // Constructor for GamePanel
    public GamePanel() {
        station = new StationMenu();
        user = new Player();
        ticketMenu = new TicketMenu();
        station = new StationMenu();
        assemblyStation = new AssemblyStation();
        orderStation = new OrderStation();
        drinkStation = new DrinkStation();
        cookStation = new CookStation();
        sounds = new Sound();
        upgrades = new Upgrades();
        instructions = new Instructions();
        endOfDay = false;
        viewInstructions = true;
        this.setFocusable(true);
        trash = new ImageIcon("trash.png").getImage();
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                station.mousePressed(e);
                ticketMenu.mousePressed(e);
                cookStation.mousePressed(e);
                assemblyStation.mousePressed(e);
                drinkStation.mousePressed(e);
                user.mousePressed(e);
                instructions.mousePressed(e);
                if (viewUpgrades)
                    upgrades.mousePressed(e);
            }
        });
        this.setPreferredSize(new Dimension(GAME_WIDTH, GAME_HEIGHT));

        schedule();
        ticketMenu.updateOrderTime();

        gameThread = new Thread(this);
        gameThread.start();
        Sound.playMusic();

    }

    // Called regularly to update what is happening in the window
    public void paint(Graphics g) {
        image = createImage(GAME_WIDTH, GAME_HEIGHT);
        graphics = image.getGraphics();
        draw(graphics);
        g.drawImage(image, 0, 0, this);
    }

    // Called to draw station and ticketmenu on gamepanel, draws station and other
    // objects on screen depending
    // on which is active
    public void draw(Graphics g) {
		if (!upgrades.gotPromoted) {
        if (orderStation.dayEnded == false) {
            // draws stations (normal gameplay)
            if (station.activeStation == 1) {
                orderStation.draw(g);
            }
            if (station.activeStation == 2) {
                drinkStation.draw(g);
                g.drawImage(trash, 900, 150, 120, 120, null);
            }
            if (station.activeStation == 3) {
                cookStation.draw(g);
            }
            if (station.activeStation == 4) {
                assemblyStation.draw(g);
                g.drawImage(trash, 900, 150, 120, 120, null);
            }
            ticketMenu.setStation(station.activeStation);
            ticketMenu.draw(g, upgrades.getUpgradesMultiplier());
            user.draw(g);
            if (viewInstructions) {
					instructions.draw(g, station.activeStation, (cookStation.numberOnStove),
							AssemblyStation.numberOnTray);
            }

        } else {
            // draws end of day screen or upgrades if it has been selected
            if (viewUpgrades) {
                upgrades.draw(g, user.bank);
                user.x = 0;
                user.y = 0;
            } else {
                user.drawEndScreen(g);
                upgrades.x = 0;
                upgrades.y = 0;
            }
        }
		}
		else 
			upgrades.drawPromotion(g);

    }

    // Called regularly to update positions
    public void play() {
        // Officially ends day after user closes final reciept
        endTheDay();

        // Switches stations
        if (orderStation.dayEnded == false) {
            station.switchStation();
            if (station.switchedStation == true) {
                sounds.playSoundEffect("bounce.wav");
            }
            updateButton();
            if (station.activeStation == 2) {
                drinkStation.addDrink();
                drinkStation.addStraw();
                drinkStation.addCap();
                drinkStation.chooseFlavour();
                drinkStation.checkTrash();

            }
            if (station.activeStation == 3) {
                cookStation.addMeatToStove();
                cookStation.takeOffMeat();
                cookStation.checkTrash();
            }
            if (station.activeStation == 4) {
                assemblyStation.checkTopping();
                assemblyStation.checkTrash(Instructions.stepsCompleted[16]);

            }
            if (viewInstructions) {
                viewInstructions = instructions.instructionsView();
                if (Instructions.stepsCompleted[19] == true)
                    viewInstructions = false;
            }
        } else {
            // Displays end
            user.checkViewUpgrades();
            if (viewUpgrades) {
                upgrades.changeScreen();
                user.bank = upgrades.purchaseUpgrade(user.bank);
            }
            if (!viewUpgrades) {
                orderStation.dayEnded = !user.checkDayBegin();
            }
        }
    }

    // Update status of button and calls appropriate method to current station if
    // active and clicked
    public void updateButton() {
        String button = ticketMenu.buttonPressed();
        if (button.equals("left") && ticketMenu.activeTicket > 0) {
                                                                    // condition
            ticketMenu.activeTicket--;
        }
        if (button.equals("right") && ticketMenu.activeTicket < Order.ordersTaken - 1) {
            ticketMenu.activeTicket++;
        }
        if (station.activeStation == 1 && button.equals("action")) {
            orderStation.takeOrder();
        }
        if (station.activeStation == 2 && button.equals("action")) {
            submitToOrder('D');
        }
        if (station.activeStation == 4 && button.equals("action")) {
            submitToOrder('B');
        }
        if (button.equals("trash")) {
            ticketMenu.trashSelected = !ticketMenu.trashSelected;
        }
        if (button.equals("closeResult")) {
            ticketMenu.closeActive();
        }
    }

    // Submits order if possible
    public void submitToOrder(char food) {
        // Checks if item can be submitted, and submits it if so
        if (TicketMenu.currentOrders[0] == null) {
            user.showErrorMessage("No order selected");
            // provide visual feedback
            // item not needed for order
        } else if (!ticketMenu.checkItemNeeded(food)) {
            user.showErrorMessage("Item not needed in this order");
            // no completed item ready to submit
        } else if ((food == 'D' && (drinkStation.drinkActive == null || !drinkStation.checkDrinkReady()))
                || (food == 'B' && (assemblyStation.activeBurger == null
                        || !assemblyStation.activeBurger.status.equals("READY")))) {
            user.showErrorMessage("No item to submit");
        } else {
            // item ready to submit
            if (food == 'D') {
                if (TicketMenu.currentOrders[ticketMenu.activeTicket].itemsSubmitted[1] == null) {
                    TicketMenu.currentOrders[ticketMenu.activeTicket].itemsSubmitted[1] = drinkStation.drinkActive;
                    drinkStation.reset();
                } else {
                    user.showErrorMessage("Item already added");
                }
            } else {
                if (TicketMenu.currentOrders[ticketMenu.activeTicket].itemsSubmitted[0] == null) {
                    TicketMenu.currentOrders[ticketMenu.activeTicket].itemsSubmitted[0] = assemblyStation.activeBurger;
                    assemblyStation.activeBurger = null;
                } else {
                    user.showErrorMessage("Item already added");
                }
            }
        }

        // Auto-submits when all components are assigned
        if (Order.ordersTaken != 0 && TicketMenu.currentOrders[ticketMenu.activeTicket] != null
                && TicketMenu.currentOrders[ticketMenu.activeTicket].checkOrderDone()) {

            // Updates day's earnings
            user.earningsToday += TicketMenu.currentOrders[ticketMenu.activeTicket].calculateRevenue()
                    + TicketMenu.currentOrders[ticketMenu.activeTicket].calculateTips(upgrades.getUpgradesMultiplier());
            user.tipsToday += TicketMenu.currentOrders[ticketMenu.activeTicket]
                    .calculateTips(upgrades.getUpgradesMultiplier());
            user.bank += TicketMenu.currentOrders[ticketMenu.activeTicket]
                    .calculateTips(upgrades.getUpgradesMultiplier());
            user.earningsTotal += TicketMenu.currentOrders[ticketMenu.activeTicket].calculateRevenue()
                    + TicketMenu.currentOrders[ticketMenu.activeTicket].calculateTips(upgrades.getUpgradesMultiplier());

            // Shows completion screen
            ticketMenu.showingResult = true;

            // Checks whether or not ready to end day
            if (!waitingToEndDay && !orderStation.dayEnded && Order.numOrdersCompletedToday >= Order.maxOrdersToday - 1) {
                Order.lastId = 0;
                orderStation.numOrdersInLine = 0;
                waitingToEndDay = true;

            }

        }

    }

    // Ends day if ready and reciept for last order has been closed
    public void endTheDay() {
        if (waitingToEndDay && !ticketMenu.showingResult) {
            orderStation.dayEnded = true;
            station.activeStation = 1;
            orderStation.generateStartingCustomers(false);
            waitingToEndDay = false;
        }
    }

    // from:
    // https://www.geeksforgeeks.org/scheduledexecutorservice-interface-in-java/ and
    // https://www.geeksforgeeks.org/runnable-interface-in-java/
    public void schedule() {
        // creating a ScheduledExecutorService object
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(10);

        // Define the task to add a customer
        Runnable addCustomerTask = new Runnable() {
            @Override
            public void run() {
                orderStation.addCustomer();
            }
        };

        // Schedule the addCustomer task to run every 30 seconds (starting immediately)
        scheduler.scheduleAtFixedRate(addCustomerTask, 0, 12, TimeUnit.SECONDS);
    }

    // Runs everything in a loop
    public void run() {
        long lastTime = System.nanoTime();
        double amountOfTicks = 60;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long now;

        // Loops indefinitely
        while (true) {
            now = System.nanoTime();
            delta = delta + (now - lastTime) / ns;
            lastTime = now;

            // Moves objects around and updates screen if enough time has passed
            if (delta >= 1) {
                play();
                repaint();
                delta--;
            }
        }
    }
}