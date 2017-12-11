/*
 * This class is for the welcome screen that a user is first presented with when
 * the application is run. It prompts them to enter their username/password to
 * gain access to X-Flix and use the service or view/update their account info.
 */

// Imports the AWT and Swing packages needed to implement GUI elements
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;
import java.util.StringTokenizer;

import javax.swing.*;

class PrimaryWindow extends JFrame {

  TopPanel topPan;
  private LoginPanel login;
  private JPanel loginHolder = new JPanel(new FlowLayout());
  MainBody main;

  String currentUserID;
  String currentUserName;
  boolean adminUser;
  boolean regUser;

  // Text files containing information on usernames/passwords, user accounts,
  // and movies currently in the rental library
  private Scanner readFile;

  // HashMaps serving as temporary databases that house info needed by users
  // to search for movies, as well as add or edit information under their
  // profiles.
  HashMap<String, String> userAndPass = new HashMap();
  HashMap<String, UserAccount> userAccounts = new HashMap();
  HashMap<String, Movie> newReleases = new HashMap();

  PrimaryWindow() {
    // Invokes super constructor & sets the window title
    super("Welcome to X-Flix! Entertainment done your way - the right way!");

    // Creates entrance page with login panel
    topPan = new TopPanel();
    topPan.setVisible(true);
    login = new LoginPanel();
    login.setVisible(true);
    loginHolder.add(login);
    add(loginHolder, BorderLayout.CENTER);
    add(topPan, BorderLayout.NORTH);

    createInfoTables();

    String key = "BayWatch";
    Movie value = newReleases.get(key);
    add(new JLabel(value.moviePoster), BorderLayout.SOUTH);
    repaint();
  }

  // Creates various lists/maps/tables based on the current content of the
  // text files containing various information such as username/password, user
  // account info, current movie library, rental history, etc.
  private void createInfoTables() {

    // Creates the HashMap for usernames/passwords
    makeUserPassMap();
    makeUserAcctMap();
    makeNewMoviesMap();

  }

  // This method is responsible for reading all of the usernames and passwords
  // from that last saved version of the file containing this information, and
  // then places the info into a HashMap with usernames being the key (since
  // they must be unique), and the passwords as values (since multiple users
  // could theoretically have the same passwords). This info is used later for
  // credential verification, as well as for adding or deleting user profiles.
  // Any changes to the HashMap are they written back to the file for storage
  // and later use/referrence.

  private void makeUserPassMap() {
    try {
      readFile = new Scanner(new File(Xres.USER_PASS_FILE));
    } catch (FileNotFoundException e) {
      System.out.println("User-Password file not found.");
      e.printStackTrace();
    }

    while (readFile.hasNextLine()) {
      // String tokenizer breaks each text line into raw tokens
      StringTokenizer st = new StringTokenizer(readFile.nextLine());

      while (st.hasMoreTokens()) {
        String keyUser = st.nextToken();
        String valuePass = st.nextToken();
        userAndPass.put(keyUser, valuePass);
      }

    }
  }
    
    /* Testing code to loop through elements of a map
    for (String user : userAndPass.keySet()) {

      String key = user.toString();
      String value = userAndPass.get(user).toString();
      System.out.println("User: " + key + "/ Password: " + value);
    }
    */

  // Creates objects of the UserAccount class based on information in a text
  // file containing all of the user info, and places those user objects into
  // a HashMap with the email address (username/ID) as a key, and the user
  // objects as values. This map will be how user info is retrieved by the
  // program for customers or admins to view/edit.
  private void makeUserAcctMap() {
    try {
      readFile = new Scanner(new File(Xres.USER_ACCOUNT_FILE));
    } catch (FileNotFoundException e) {
      System.out.println("User Account Info file not found.");
      e.printStackTrace();
    }
    while (readFile.hasNextLine()) {

      // String tokenizer breaks each text line into raw tokens
      StringTokenizer st = new StringTokenizer(readFile.nextLine());

      while (st.hasMoreTokens()) {
        String keyEmail = st.nextToken();
        String fName = st.nextToken();
        String lName = st.nextToken();

        UserAccount valueUserInfo = new UserAccount(keyEmail, fName, lName);
        userAccounts.put(keyEmail, valueUserInfo);
      }
    }
  }

  // Creates objects of the Movie class (new releases) based on information in
  // a text file containing the movie info, and places those user objects into
  // a HashMap with the movie title as a key, and the objects as values. This
  // map will be how user info is retrieved by the program for customers or
  // admins to view/edit.
  private void makeNewMoviesMap() {
    try {
      readFile = new Scanner(new File(Xres.NEW_MOVIES_FILE));
    } catch (FileNotFoundException e) {
      System.out.println("New Movie Info file not found.");
      e.printStackTrace();
    }

    while (readFile.hasNextLine()) {
      // String tokenizer breaks each text line into raw tokens
      StringTokenizer st = new StringTokenizer(readFile.nextLine(), "|");

      while (st.hasMoreTokens()) {
        String keyTitle = st.nextToken();
        String year = st.nextToken();
        String genre = st.nextToken();
        String director = st.nextToken();
        String actor1 = st.nextToken();
        String actor2 = st.nextToken();
        String actor3 = st.nextToken();
        String tag1 = st.nextToken();
        String tag2 = st.nextToken();
        String tag3 = st.nextToken();
        String picFile = st.nextToken();
        String plotSum = st.nextToken();

        Movie valueNewMovie = new Movie(keyTitle, year, genre, director, actor1,
            actor2, actor3, tag1, tag2, tag3, picFile, plotSum);
        newReleases.put(keyTitle, valueNewMovie);
      }
    }
    //Testing Loop
    for (String movie : newReleases.keySet()) {

      Movie value = newReleases.get(movie);
      System.out.println(value.title);
      System.out.println(value.year);
      System.out.println(value.genre);
      System.out.println(value.director);
      System.out.println(value.cast1);
      System.out.println(value.cast2);
      System.out.println(value.cast3);
      System.out.println(value.tag1);
      System.out.println(value.tag2);
      System.out.println(value.tag3);
      System.out.println(value.picFileName);
      System.out.println(value.synopsis);
    }
  }

  // Resets entrance page & login panel when user logs out
  void refreshLogin() {

    // Clears the window contents
    this.remove(topPan);
    this.remove(main);
//    this.repaint();

    // Resets the values for fields containing username/account type info,
    // both protecting user data and allowing another person to log in
    currentUserID = null;
    currentUserName = null;
    adminUser = false;
    regUser = false;

    // Redraws the login screen back to its original state
    topPan.resetPanel();
    topPan.setVisible(true);
    add(topPan, BorderLayout.NORTH);

    login = new LoginPanel();
    login.setVisible(true);
    loginHolder.add(login);
    add(loginHolder, BorderLayout.CENTER);
    this.revalidate();
    this.repaint();
  }

  LoginPanel getLoginPanel() {
    return login;
  }
}
