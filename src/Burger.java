// Name: Emilee Zhang, Nevetha Arullinkam
// Date: 5/28/2025
// Description: Fortune's Fast Food is a game where the player works at a burger shop, taking and fulfilling orders as quickly and accurately as possible

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.awt.*;
import javax.swing.*;

//burger class is a part of food since it is one of the menu options (food for customers)
public class Burger extends Food {

    // variable initialization
    public static double price = 7.99;
    public static Image[] cookStages;
    public boolean meatPlaced;
    public int cook;
    public String status;
    public int row;
    public int col;
    public String itemsAssembled;
    public int[] toppingAccuracy;
    public boolean[] toppingsAdded;
    public int miniX;
    public static Color transparentColor;
    public static Color underTransparentColor;
    public static Color redTransparentColor;
    public static Color redUnderTransparentColor;
    // constructor for burger created in cook
    public Burger(int r, int c) {
        cook = 0;
        status = "COOK";
        row = r;
        col = c;
        miniX = 0;

        // For assembly station
        meatPlaced = false;
        toppingAccuracy = new int[12];

        toppingsAdded = new boolean[12];

        // Starts cook timer
        startCounter();
    }

    // for temp burger created in assembly station
    public Burger() {
        status = "BUILD";
        miniX = 0;
        meatPlaced = false;

        // For assembly station
        meatPlaced = false;
        toppingAccuracy = new int[12];
        toppingsAdded = new boolean[12];
    }

    static {
        cookStages = new Image[8];
        // creating images for burger
        for (int i = 0; i < 8; i++) {
            cookStages[i] = new ImageIcon("cook" + (i + 1) + ".png").getImage();
        }
        // creating colours for burger
        transparentColor = new Color(200, 200, 200, 100);
        underTransparentColor = new Color(200, 200, 200, 30);
        redTransparentColor = new Color(255, 0, 0, 100);
        redUnderTransparentColor = new Color(255, 0, 0, 30);
    }

    // Updates temp burger with cook of cooked burger
    public void updateMeat(Burger b2) {
        meatPlaced = true;
        cook = b2.cook;
    }

    // from:
    // https://www.geeksforgeeks.org/`heduledexecutorservice-interface-in-java/ and
    // https://www.geeksforgeeks.org/runnable-interface-in-java/
    private void startCounter() {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(9);

        // Define the task for counter
        Runnable counterTask = new Runnable() {
            @Override
            public void run() {
                if (!status.equals("COOK")) {
                    scheduler.shutdown();
                }
                cook++;
            }
        };

        // Schedule the counter task
        scheduler.scheduleAtFixedRate(counterTask, 1, 1, TimeUnit.SECONDS);

    }

    // Returns price of object
    public double getPrice() {
        return price;
    }

    // Draws meat on stove
    public void drawOnStove(Graphics g) {

        // drawing the burger at given coordinates
        int x = 312 + 237 * row;
        int y = 230 + 220 * col;
        int stage = Math.min(cook / 3, 7);
        final int max = 21;

        // drawing meat
        g.drawImage(cookStages[stage], 312 + 237 * row, 260 + 220 * col, 180, 150, null);

        // drawing meat timer with moving hand
        g.setColor(Color.black);
        g.fillOval(x + 50, y - 20 + 140, 80, 80);
        g.setColor(Color.white);
        g.fillOval(x + 55, y - 15 + 140, 70, 70);
        g.setColor(Color.green);
        g.drawLine(x + 55 + 35, y - 15 + 140 + 35,
                    x + 55 + 35 + (int) ((35) * Math.sin(2.0 * 9 * Math.PI / max)),
                    y - 15 + 140 + 35 - (int) ((35) * Math.cos(2.0 * 9 * Math.PI / max)));
        
        g.setColor(Color.black);
        if (cook >= max) {
            g.drawLine(x + 55 + 35, y - 15 + 140 + 35,
                    x + 55 + 35 + (int) ((35) * Math.sin(2.0 * max * Math.PI / max)),
                    y - 15 + 140 + 35 - (int) ((35) * Math.cos(2.0 * max * Math.PI / max)));
        } else {
            g.drawLine(x + 55 + 35, y - 15 + 140 + 35,
                    x + 55 + 35 + (int) ((35) * Math.sin(2.0 * cook * Math.PI / max)),
                    y - 15 + 140 + 35 - (int) ((35) * Math.cos(2.0 * cook * Math.PI / max)));
        }
    }

    // Draws meat in pile in cook station
    public static void drawInPile(Graphics g, int x, int y) {
        g.drawImage(cookStages[0], x, y, 180, 150, null);

    }

    // draws meat in stack in assembly
    public void drawInAssembly(Graphics g, int x, int y) {
        g.drawImage(cookStages[Math.min(cook / 3, 7)], x, y, 180, 150, null);

    }

    // Draws active burger in assemblystation
    public void drawActive(Graphics g, boolean gameOn) {
        int x;
        int y;

        // Draws each topping/item
        for (int i = 0; i < itemsAssembled.length(); i++) {
            x = 600 + toppingAccuracy[i];
            // Moves up toppings located above meat
            y = 650 - i * 25 - ((itemsAssembled.indexOf("1") <= i && itemsAssembled.indexOf("1") != -1) ? 10 : 0);

            if (itemsAssembled.charAt(i) == '0' && i == 0) {
                // draw bottom bun
                g.drawImage(AssemblyStation.bottomBun, 600, y, 180, 150, null);
            } else if (itemsAssembled.charAt(i) == '0') {
                // draw top bun
                g.drawImage(AssemblyStation.topBun, 600, y-10, 180, 150, null);
            } else if (itemsAssembled.charAt(i) == '1') {
                // draw meat
                g.drawImage(cookStages[Math.min(cook / 3, 7)], 600, y, 180, 150, null);
            } else {
                // draw all other toppings with position depending on minigame status
                if (i == itemsAssembled.indexOf('1') - 1) {
                    g.drawImage(AssemblyStation.toppingImages[((int) itemsAssembled.charAt(i) - '0' - 3)], 600 + toppingAccuracy[i+1], y, 180,
                            150, null);
                } else if (gameOn && i == itemsAssembled.length() - 1 || i == Minigame.toppingInProgress) {
                    g.drawImage(AssemblyStation.toppingImages[((int) itemsAssembled.charAt(i) - '0' - 3)], miniX, y, 180,
                            150, null);
                } else {
                    g.drawImage(AssemblyStation.toppingImages[((int) itemsAssembled.charAt(i) - '0' - 3)], x, y, 180, 150,
                            null);
                }

            }
        }
    }

    // creating the indicators for where users can click when they chose to add a
    // burger onto the grill
    public static void drawSpots(Graphics g) {
        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 3; column++) {
                // indicator ellipse
                g.setColor(Color.DARK_GRAY);
                g.fillOval(312 + 237 * row, 230 + 220 * column + 30, 180, 140);
                // '+' button for clarity, user knows they're adding a burger there
                g.setColor(Color.black);
                g.fillRect(312 + 237 * row + 80, 230 + 220 * column + 50, 20, 100);
                g.fillRect(312 + 237 * row + 40, 230 + 220 * column + 90, 100, 20);
            }
        }
    }

    // creating the indicators for where users can click when they chose to move
    // meat from grill to assembly
    public static void drawRemoval(Graphics g, Burger[][] burgers) {
        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 3; column++) {
                if (burgers[row][column] != null) {
                    // indicator ellipse
                    g.setColor(transparentColor);
                    g.fillOval(312 + 237 * row, 230 + 220 * column + 30, 180, 130);
                    g.setColor(underTransparentColor);
                    g.fillOval(312 + 237 * row, 230 + 220 * column + 30, 180, 150);
                    // '-' button for clarity, user knows they're removing a burger there
                    g.setColor(Color.white);
                    g.fillRect(312 + 237 * row + 40, 230 + 220 * column + 90, 100, 20);
                }
            }
        }
    }

    // Draws trash can
    public static void drawTrash(Graphics g, Burger[][] burgers) {
        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 3; column++) {
                if (burgers[row][column] != null) {
                    // indicator ellipse
                    g.setColor(redTransparentColor);
                    g.fillOval(312 + 237 * row, 230 + 220 * column + 30, 180, 130);
                    g.setColor(redUnderTransparentColor);
                    g.fillOval(312 + 237 * row, 230 + 220 * column + 30, 180, 150);
                    // '-' button for clarity, user knows they're trashing a burger there
                    g.setColor(Color.red);
                    g.fillRect(312 + 237 * row + 40, 230 + 220 * column + 90, 100, 20);
                }
            }
        }
    }
}
