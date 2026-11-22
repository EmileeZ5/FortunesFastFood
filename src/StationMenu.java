// Name: Emilee Zhang, Nevetha Arullinkam
// Date: 5/28/2025
// Description: Fortune's Fast Food is a game where the player works at a burger shop, taking and fulfilling orders as quickly and accurately as possible

import java.awt.*;
import java.awt.event.*;

public class StationMenu extends Rectangle {

    public int activeStation;
    public String background;
    public boolean switchedStation;
    public int pastStation;

    public StationMenu() {
        // Sets default active station to OrderStation
        activeStation = 1;
        pastStation = 1;
        switchedStation = false;
    }

    // Updates x and y if mouse click detected
    public void mousePressed(MouseEvent e) {
        x = e.getX();
        y = e.getY();
    }

    // Detects when station button pressed and switches station
    public void switchStation() {
        if (Math.pow(Math.pow((x - 1090), 2) + Math.pow((y - 70), 2), 0.5) < 45) {
            activeStation = 1;
        }
        if (Math.pow(Math.pow((x - 1190), 2) + Math.pow((y - 70), 2), 0.5) < 45) {
            activeStation = 2;

        }
        if (Math.pow(Math.pow((x - 1290), 2) + Math.pow((y - 70), 2), 0.5) < 45) {
            activeStation = 3;

        }
        if (Math.pow(Math.pow((x - 1390), 2) + Math.pow((y - 70), 2), 0.5) < 45) {
            activeStation = 4;
        }
        switchedStation = pastStation != activeStation;
        pastStation = activeStation;

    }

}
