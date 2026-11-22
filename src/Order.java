// Name: Emilee Zhang, Nevetha Arullinkam
// Date: 5/28/2025
// Description: Fortune's Fast Food is a game where the player works at a burger shop, taking and fulfilling orders as quickly and accurately as possible

import java.awt.*;

public class Order {

    public static int ordersTaken = 0;
    public static int lastId = 0;
    public static int numOrdersCompletedToday = 0;
    public static int maxOrdersToday = 2;
    public String customerName;
    public String status;
    public int posInLine;
    public int id;
    public int score;
    public Food[] itemsSubmitted;
    public Image customerImage;
    public int orderTimeNeeded;
    public int orderTimeTaken;

    // Contains foods in order: burger, drink
    public String[] itemsNeeded;

    public static final int RACK_TICKET_HEIGHT = 13 * 10;
    public static final int RACK_TICKET_WIDTH = 70;

    // Constructor for Order object with name of customer, foods ordered
    public Order(String name, String[] ordered, Image customerAppearance, int time) {
        id = lastId + 1;
        lastId++;
        customerName = name;
        customerImage = customerAppearance;
        itemsNeeded = ordered;
        itemsSubmitted = new Food[2];
        orderTimeNeeded = time;
    }


    // Calculates cook score
    public double calculateCookScore() {
        double score;

        Burger burg = (Burger) itemsSubmitted[0];
        if (!burg.meatPlaced || burg.cook >= 21) {
            score = 0;
        } else {
            score = (10.0 - (Math.abs(10 - burg.cook))) / 10;
        }
        return score * 100;
    }

    // Calculates score based on placement of each topping
    public double calculatePlacementScore() {
        double score = 0;
        Burger burg = (Burger) itemsSubmitted[0];

        // Returns score of zero if burger is empty (bun only)
        if (burg.itemsAssembled.length() == 2) {
            return 0;
        }

        // Calculates total offset of toppings and reverses it
        for (int i = 0; i < burg.itemsAssembled.length(); i++) {
            score += 100 - Math.abs(burg.toppingAccuracy[i]);
        }

        // Calculates accuracy as a percentage
        score = score / burg.itemsAssembled.length();

        return score;

    }

    // Calculates accuracy score
    public double calculateAccuracyScore() {
        double accuracy = 0;

        Burger burg = (Burger) itemsSubmitted[0];
        String needed = itemsNeeded[0].substring(1); // Starts at 1 to skip identifying char at start (i.e. "B")
        String gotOriginal = burg.itemsAssembled;
        String got = "";

        for (int i = gotOriginal.length() - 1; i >= 0; i--) {
            if (gotOriginal.charAt(i) > '1') {
                got += (char) (gotOriginal.charAt(i) - 1);
            } else {
                got += (char) (gotOriginal.charAt(i));
            }
        }

        int minLen = Math.min(needed.length(), got.length());

        // Deals with if topping is in correct spot
        for (int i = 0; i < minLen; i++) {
            if (needed.charAt(i) == got.charAt(i)) {
                accuracy += 1; // +1 point if topping in correct place
            }
        }

        // Deals with if topping is not in range or not in correct spot
        for (int i = 0; i < got.length(); i++) {
            if (i >= needed.length() || needed.charAt(i) != got.charAt(i)) {
                if (needed.indexOf(got.charAt(i)) != -1) {
                    accuracy += 0.1; // +0.1 points if topping exists
                } else {
                    accuracy -= 0.1; // -0.1 points if topping doesn't exist
                }
            }
        }

        // Penalizes for wrong number of toppings
        accuracy -= 0.1 * Math.abs(needed.length() - got.length());

        // Max accuracy is 1.0
        accuracy /= needed.length();

        return accuracy * 100;

    }

    // Calculates score based on accuracy of drink (if drink needed)
    public double calculateDrinkScore() {
        Drink drink = (Drink) itemsSubmitted[1];
        if (drink != null) {
            if (drink.type == (int) (itemsNeeded[1].charAt(1)) - '0') {
                return 100;
            } else {
                return 0;
            }
        } else {
            return -1;
        }

    }

    // Calculates score based on time (only awarded if other scores are good)
    public double calculateTimeBonus() {
        return Math.max(orderTimeNeeded - orderTimeTaken, 0) * 100 / orderTimeNeeded;

    }

    // Calculates full order tips based on topping accuracy and time taken to
    // complete order
    // ~50% score = 0 tips
    public double calculateTips(double multi) {
        double totalScore = 0;

        // No tips if meat is completely burnt or missing
        if (calculateCookScore() == 0) {
            return 0;
        }

        // Adds up components of score
        totalScore += calculateAccuracyScore() + calculateCookScore();

        // Checking if drink needed and incorporating into score if so
        if (itemsSubmitted[1] != null) {
            totalScore += calculateDrinkScore();
            totalScore = (totalScore - 1.5) / 3;
        } else {
            totalScore = (totalScore - 0.9) / 2;
        }

        // Provides time bonus given good score in other areas (prevents tips from submitting a bad burger very quickly)
        if (totalScore * 2 / 100 > 1) {
            totalScore += calculateTimeBonus();
        }

        return totalScore * 2 / 100;

    }

    // Calculates earnings for order based on order score
    public double calculateRevenue() {
        double revenue = 0;
        revenue += itemsSubmitted[0].getPrice();
        if (itemsSubmitted[1] != null) {
            revenue += itemsSubmitted[1].getPrice();
        }
        return revenue;
    }

    // Checks if order is done (has correct number of each item)
    public boolean checkOrderDone() {
        for (int i = 0; i < itemsNeeded.length; i++) {
            if (itemsNeeded[i] != null && itemsSubmitted[i] == null) {
                return false;
            }
        }
        return true;
    }

    // Draw customer in line
    public void drawCustomerInLine(Graphics g, int n) {
        g.drawImage(customerImage, 200 + n * 140, 460, 500, 440, null);
        g.setColor(Color.white);
        g.setFont(new Font("Consolas", Font.BOLD, 15));
        g.drawString(customerName, 400 + n * 140, 510);
    }

    // Draws order tickets
    public void draw(Graphics g, int ticketNum) {
        int startY = 55;
        // Draw outline
        g.setColor(Color.black);
        g.fillRect(50 + ticketNum * (RACK_TICKET_WIDTH + 15) - 3, startY - 3, RACK_TICKET_WIDTH + 6,
                RACK_TICKET_HEIGHT + 6);
        // Draw inside
        g.setColor(Color.white);
        g.fillRect(50 + ticketNum * (RACK_TICKET_WIDTH + 15), startY, RACK_TICKET_WIDTH, RACK_TICKET_HEIGHT);

        // Draw rows
        // Checks whether burger was submitted
        if (itemsSubmitted[0] != null) {
            // Makes top portion of ticket green to indicate burger is done (white part)
            g.setColor(new Color(204, 255, 179));
            g.fillRect(50 + ticketNum * (RACK_TICKET_WIDTH + 15), startY, RACK_TICKET_WIDTH,
                    RACK_TICKET_HEIGHT * 10 / 13);

            // Makes top portion of ticket green to indicate burger is done (grey part)
            g.setColor(new Color(153, 255, 102));
        } else {
            g.setColor(Color.LIGHT_GRAY);
        }

        for (int i = 0; i < 5; i++) {
            g.fillRect(50 + ticketNum * (RACK_TICKET_WIDTH + 15), startY + (1 + i * 2) * (RACK_TICKET_HEIGHT / 13),
                    (RACK_TICKET_WIDTH), (RACK_TICKET_HEIGHT / 13));
        }

        // Checks whether drink was submitted
        if (itemsSubmitted[1] != null) {
            // Makes bottom portion of ticket green to indicate drink is done
            g.setColor(new Color(204, 255, 179));
            g.fillRect(50 + ticketNum * (RACK_TICKET_WIDTH + 15), startY + RACK_TICKET_HEIGHT * 10 / 13,
                    RACK_TICKET_WIDTH, RACK_TICKET_HEIGHT * 3 / 13);
        }

        // Draws toppings
        drawToppings(g, ticketNum, startY);

        // Draw drink
        if (itemsNeeded[1] != null) {
            int n = (int) (itemsNeeded[1].charAt(1) - '0');
            String[] flavours = Drink.flavours;
            g.setColor(Color.black);
            g.setFont(new Font("Consolas", Font.BOLD, 9));
            g.drawString(flavours[n], 52 + ticketNum * (RACK_TICKET_WIDTH + 15) + 2,
                    startY + 11 * (RACK_TICKET_HEIGHT / 13) - 2);
        }

        // Draw number on ticket on rack
        g.setColor(Color.black);
        g.setFont(new Font("Consolas", Font.BOLD, 8));
        g.drawString(id + "", 50 + ticketNum * (RACK_TICKET_WIDTH + 15) + 2, startY + 9);
    }

    // Draws on each row of ticket on rack
    public void drawToppings(Graphics g, int ticketNum, int sy) {
        int topping;

        // Draw top bun
        g.drawImage(AssemblyStation.topBun,
                50 + ticketNum * (RACK_TICKET_WIDTH + 15) + (RACK_TICKET_WIDTH / 3),
                sy + (1 + 0) * (RACK_TICKET_HEIGHT / 13),
                (RACK_TICKET_WIDTH / 3), (RACK_TICKET_HEIGHT / 13), null);

        // Draw rest of burger
        // Starts at 2 due to food char ('B' or 'D') in position 0 and top bun already
        // drawn in position 1
        for (int i = 2; i < itemsNeeded[0].length(); i++) {
            topping = itemsNeeded[0].charAt(i) - '0';
            if (topping == 0) { // Draw bun
                g.drawImage(AssemblyStation.bottomBun,
                        50 + ticketNum * (RACK_TICKET_WIDTH + 15) + (RACK_TICKET_WIDTH / 3),
                        sy + i * (RACK_TICKET_HEIGHT / 13),
                        (RACK_TICKET_WIDTH / 3), (RACK_TICKET_HEIGHT / 13), null);
            } else if (topping == 1) { // Draw meat
                g.drawImage(Burger.cookStages[3], 50 + ticketNum * (RACK_TICKET_WIDTH + 15) + (RACK_TICKET_WIDTH / 3),
                        sy + i * (RACK_TICKET_HEIGHT / 13),
                        (RACK_TICKET_WIDTH / 3), (RACK_TICKET_HEIGHT / 13), null);
            } else { // Draw topping
                g.drawImage(AssemblyStation.toppingImages[topping - 2],
                        50 + ticketNum * (RACK_TICKET_WIDTH + 15) + (RACK_TICKET_WIDTH / 3),
                        sy + i * (RACK_TICKET_HEIGHT / 13),
                        (RACK_TICKET_WIDTH / 3), (RACK_TICKET_HEIGHT / 13), null);
            }
        }

    }

    // Draws on rows of ticket for active order
    public void drawActiveTicket(Graphics g) {
        int topping;

        // Draw top bun
        g.drawImage(AssemblyStation.topBun, 1065 + TicketMenu.TICKET_ROW_WIDTH / 3,
                160 + (1 + 0) * (TicketMenu.TICKET_ROW_HEIGHT), (TicketMenu.TICKET_ROW_WIDTH / 3) - 20,
                (TicketMenu.TICKET_ROW_HEIGHT), null);

        // Draw rest of burger
        for (int i = 2; i < itemsNeeded[0].length(); i++) {
            topping = itemsNeeded[0].charAt(i) - '0';
            if (topping == 0) {
                g.drawImage(AssemblyStation.bottomBun, 1065 + TicketMenu.TICKET_ROW_WIDTH / 3,
                        160 + i * (TicketMenu.TICKET_ROW_HEIGHT) - 3, (TicketMenu.TICKET_ROW_WIDTH / 3) - 20,
                        (TicketMenu.TICKET_ROW_HEIGHT) + 8, null);
            } else if (topping == 1) {
                g.drawImage(Burger.cookStages[3], 1065 + TicketMenu.TICKET_ROW_WIDTH / 3,
                        160 + i * (TicketMenu.TICKET_ROW_HEIGHT) + 1, (TicketMenu.TICKET_ROW_WIDTH / 3) - 20,
                        (TicketMenu.TICKET_ROW_HEIGHT) - 3, null);
            } else {
                g.drawImage(AssemblyStation.toppingImages[topping - 2], 1065 + TicketMenu.TICKET_ROW_WIDTH / 3,
                        160 + i * (TicketMenu.TICKET_ROW_HEIGHT) - 7, (TicketMenu.TICKET_ROW_WIDTH / 3) - 20,
                        (TicketMenu.TICKET_ROW_HEIGHT) + 15, null);
            }
        }

        // Draw drink on active ticket
        if (itemsNeeded[1] != null) {
            int n = (int) (itemsNeeded[1].charAt(1) - '0');
            String[] flavours = Drink.flavours;

            g.setColor(Color.black);
            g.setFont(new Font("Consolas", Font.BOLD, 20));
            g.drawString(flavours[n], 1065 + TicketMenu.TICKET_ROW_WIDTH / 3,
                    160 + 11 * (TicketMenu.TICKET_ROW_HEIGHT) - 10);
        }

        // Draws order number
        g.setColor(Color.darkGray);
        g.setFont(new Font("Consolas", Font.BOLD, 30));
        g.drawString(id + "", 1065 + 10, 160 + 30);
    }

    // Returns number rounded to two decimal places
    // from
    // https://stackoverflow.com/questions/2538787/how-to-print-a-float-with-2-decimal-places-in-java
    public static String roundNum(double num) {
        return String.format("%.2f", num);
    }

}
