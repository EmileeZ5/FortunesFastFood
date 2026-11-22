// Name: Emilee Zhang, Nevetha Arullinkam
// Date: 5/28/2025
// Description: Fortune's Fast Food is a game where the player works at a burger shop, taking and fulfilling orders as quickly and accurately as possible

import java.awt.Color;
import java.awt.Graphics;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Minigame {
    public double score;
    public boolean gameInProgress;
    public int currentBarX;
    public int xPos = 2000;
    public int yPos = 2000;
    public static int toppingInProgress;

    // Constructor for minigame, initially is not in use
    public Minigame() {
        score = 0;
        gameInProgress = false;
        currentBarX = 0;
        toppingInProgress = -1;
    }

    public void draw(Graphics g) {
        if (gameInProgress) {
            // Draw outline
            g.setColor(Color.black);
            g.fillRect(xPos - 5, yPos - 5, 200 + 10, 50 + 10);

            // Draw coloured sections
            g.setColor(Color.red);
            g.fillRect(xPos, yPos, 200, 50);

            g.setColor(Color.orange);
            g.fillRect(xPos + 40, yPos, 200 - 80, 50);

            g.setColor(Color.green);
            g.fillRect(xPos + 80, yPos, 200 - 160, 50);

            // Draw moving line
            g.setColor(Color.black);
            g.fillRect(xPos + 100 + currentBarX, yPos, 2, 50);
        }
    }

    // Begin minigame
    // from:
    // https://www.geeksforgeeks.org/scheduledexecutorservice-interface-in-java/ and
    // https://www.geeksforgeeks.org/runnable-interface-in-java/

    public int play(int currentPos, boolean meat) {
        xPos = 590;
        yPos = 510 - currentPos * 25;
        gameInProgress = true;
        toppingInProgress = -1;

        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        // Define the task for minigame
        Runnable mini = new Runnable() {
            int directionFactor = 1;

            @Override
            public void run() {
                if (!gameInProgress) {
                    scheduler.shutdown();
                    
                }
                currentBarX += directionFactor;
                if (currentBarX > 100 || currentBarX < -100) {
                    directionFactor *= -1;
                }
            }
        };

        // Schedule the minigame task
        scheduler.scheduleAtFixedRate(mini, 10, 2, TimeUnit.MILLISECONDS);
        return currentBarX;

    }

}
