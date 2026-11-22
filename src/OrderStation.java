// Name: Emilee Zhang, Nevetha Arullinkam
// Date: 5/28/2025
// Description: Fortune's Fast Food is a game where the player works at a burger shop, taking and fulfilling orders as quickly and accurately as possible

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.util.Scanner;

import javax.swing.ImageIcon;

public class OrderStation {

    public static Order[] ordersInLine;
    public static Image[] customerImages;
    public static Image backgroundImage;
    public String[] customerNames;
    public int numOrdersInLine;
    public boolean dayEnded;

    // Constructor for orderstation, reads list of customer names
    public OrderStation() {
        ordersInLine = new Order[10];
        customerNames = readListFromFile("customerNames.txt");
        numOrdersInLine = 0;
        backgroundImage = new ImageIcon("orderstation.png").getImage();
        dayEnded = false;

        generateStartingCustomers(true);

    }

    static {
        customerImages = new Image[58];
        for (int i = 0; i < 46; i++) {
            customerImages[i] = new ImageIcon("girl" + (i + 1) + ".png").getImage();
        }
        for (int i = 46; i < 57; i++) {
            customerImages[i] = new ImageIcon("boy" + (i - 45) + ".png").getImage();

        }
    }

    // Draws orderstation
    public void draw(Graphics g) {
        // Draws image for background
        g.drawImage(backgroundImage, 0, 0, 1040, 900, null);

        // Draws customers
        for (int i = 0; ordersInLine[i] != null && i < numOrdersInLine; i++) {
            ordersInLine[i].drawCustomerInLine(g, i);
        }
    }

    // Adds order to taken orders and removes customer from line
    public void takeOrder() {
        if (Order.ordersTaken < 7 && numOrdersInLine > 0) { // Can hold onto max 7 tickets at a time
            TicketMenu.currentOrders[Order.ordersTaken] = ordersInLine[0];
            Order.ordersTaken++;
            for (int i = 0; i < numOrdersInLine - 1; i++) {
                ordersInLine[i] = ordersInLine[i + 1];
            }
            ordersInLine[numOrdersInLine - 1] = null;
            numOrdersInLine--;
        }
    }

    // Reads list from a txt file (used for customer names)
    private String[] readListFromFile(String filepath) {
        String[] names = new String[50];
        for (int i = 0; i < names.length; i++) {
            names[i] = "Customer" + (i + 1);
        }
        try {
            File list = new File(filepath);
            Scanner myReader = new Scanner(list);
            for (int i = 0; myReader.hasNextLine() && i < names.length; i++) {
                names[i] = myReader.nextLine();
            }
            myReader.close();

        } catch (Exception e) {
            
        }
        return names;
    }

    // Generates order with randomness, includes name + order
    private Order generateOrder() {
        
        String name;
        Image customerImage = customerImages[(int) (Math.random() * 56)];
        String[] order = new String[4];
        Order newOrder;
        int time = 27;
        
        customerNames = readListFromFile("customerNames.txt");
        name = customerNames[(int) (Math.random() * 50)];

        // Decides on number of burgers and number of drinks
        int numBurgers = 1; // Always 1 burger
        int numDrinks = (int) (Math.random() * 2); // Minimum 0 drinks, maximum 1
        int numToppings = (int) (Math.random() * 4 + 2); // Maximum 5 toppings, minimum 2

        // Generates burger info in a string, where 0 = bun and 1 = meat
        for (int i = 0; i < numBurgers; i++) {
            order[i] = "B0";
            for (int k = 0; k < numToppings; k++) {
                order[i] += (int) (Math.random() * 5 + 2);
                if (k == (numToppings - 1) / 2) {
                    order[i] += "1";
                }
            }
            order[i] += "0";
        }

        // Generates drink (if one exists)
        if (numDrinks == 1) {
            order[1] = "D" + (int) (Math.random() * 4); // 4 types of drinks (0-3)
            // Adds extra time allowance for orders with drinks
            time += 6;
        }

        newOrder = new Order(name, order, customerImage, time);
        return newOrder;
    }

    // Adds customer to line in orderstation if restaurant is not full/closing
    public void addCustomer() {
        // Adds order to line if not exceeding max number of orders for that day or max
        // number of orders in line
        if (!dayEnded && Order.lastId < Order.maxOrdersToday) {
            ordersInLine[numOrdersInLine] = generateOrder();
            numOrdersInLine++;
        }
    }

    // Generates starting customers
    public void generateStartingCustomers(boolean isFirstDay) {
        // First customer, tutorial or regular
        if (isFirstDay) {
            ordersInLine[0] = new Order(customerNames[0], new String[]{"B02140", "D2"}, customerImages[0], 27);
            numOrdersInLine += 1;
        } else {
            ordersInLine[0] = generateOrder();
            numOrdersInLine += 1;
        }
        
        
    }

}
