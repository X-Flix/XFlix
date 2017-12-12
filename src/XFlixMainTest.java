/*
 * This is the main test file to launch the X-Flix GUI app, which starts by
 * welcoming the user and prompting them for a username and password to login.
 */

// Imports the AWT and Swing packages needed to implement GUI elements
import javax.swing.*;

public class XFlixMainTest {

  public static void main(String[] args) {

    // Four statements needed for almost every GUI program
    PrimaryWindow primary = new PrimaryWindow();
    primary.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    primary.setSize(1000, 600);
    primary.setLocationRelativeTo(null); // Centers frame on screen
    primary.setVisible(true); // Makes frame (window) visible
    primary.revalidate();
  }

}
