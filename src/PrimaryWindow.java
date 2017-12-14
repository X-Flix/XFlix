/*
 * This class is for the welcome screen that a user is first presented with when
 * the application is run. It prompts them to enter their username/password to
 * gain access to X-Flix and use the service or view/update their account info.
 */

// Imports the AWT and Swing packages needed to implement GUI elements

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.*;

public class PrimaryWindow extends JFrame implements ActionListener {

  TopPanel topPan;
  private LoginPanel login;
  JPanel loginHolder = new JPanel(new FlowLayout());
  MainBody main;

  String currentUserID = "";
  String currentUserName = "";
  boolean adminUser;
  boolean regUser;

  // Text files containing information on usernames/passwords, user accounts,
  // and movies currently in the rental library
  private static String userPassFile = "TextDatabaseFiles/username_password.txt";
  private static String userAcctsFile = "TextDatabaseFiles/user_account_info.txt";
  private static String newMovFile = "TextDatabaseFiles/new_movies.txt";
  private static String allMovFile = "TextDatabaseFiles/all_movies.txt";
  private static String rentHistFile = "TextDatabaseFiles/rental_histories.txt";
  private Scanner readFile;

  // HashMaps serving as temporary databases that house info needed by users
  // to search for movies, as well as add or edit information under their
  // profiles.
  HashMap<String, String> userAndPass = new HashMap();
  HashMap<String, UserAccount> userAccounts = new HashMap();
  HashMap<String, Movie> newReleases = new HashMap();
  HashMap<String, Movie> allMovies = new HashMap();
  HashSet<String> movieCart = new HashSet();
  HashMap<String, ArrayList<String>> rentalHistory = new HashMap();

  PrimaryWindow() {
    // Invokes super constructor & sets the window title
    super("Welcome to X-Flix! Entertainment done your way - the right way!");

    // Creates entrance page with login panel
    topPan = new TopPanel(this);
    topPan.setVisible(true);
    login = new LoginPanel(this);
    login.setVisible(true);
    loginHolder.add(login);
    add(loginHolder, BorderLayout.CENTER);
    add(topPan, BorderLayout.NORTH);

    getRootPane().setDefaultButton(login.userLogin);

    createInfoTables();
  }

  // Creates various lists/maps/tables based on the current content of the
  // text files containing various information such as username/password, user
  // account info, current movie library, rental history, etc.
  private void createInfoTables() {

    // Creates the HashMap for usernames/passwords
    makeUserPassMap();
    makeUserAcctMap();
    makeNewMoviesMap();
    makeAllMoviesMap();
    makeRentalHistoryMap();

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
      readFile = new Scanner(new File(userPassFile));
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
      readFile = new Scanner(new File(userAcctsFile));
    } catch (FileNotFoundException e) {
      System.out.println("User Account Info file not found.");
      e.printStackTrace();
    }
    while (readFile.hasNextLine()) {

        // String tokenizer breaks each text line into raw tokens
        StringTokenizer st = new StringTokenizer(readFile.nextLine(), "|");

        while (st.hasMoreTokens()) {
          String keyEmail = st.nextToken();
          String password = st.nextToken();
          String fName = st.nextToken();
          String lName = st.nextToken();
          String strAdd = st.nextToken();
          String city = st.nextToken();
          String state = st.nextToken();
          String zip = st.nextToken();
          // payType = st.nextToken();
          // acctNum = st.nextToken();
          // cardType = st.nextToken();
          // cardExp = st.nextToken();

          UserAccount valueUserInfo = new UserAccount(keyEmail, password, fName, lName,
              strAdd, city, state,
              zip/* , payType, acctNum, cardType, cardExp */);
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
      readFile = new Scanner(new File(newMovFile));
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
    /*newReleases.put("x",new Movie("temp","1232","x","x","x","x","x","x","x","x","Alien_Covenant.png","y"));
    writeMoviesToFile();*/
    //Testing Loop
/*    for (String movie : newReleases.keySet()) {

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
    }*/

  }

  // Creates objects of the Movie class (entire library) based on information in
  // a text file containing the movie info, and places those user objects into
  // a HashMap with the movie title as a key, and the objects as values. This
  // map will be how user info is retrieved by the program for customers or
  // admins to view/edit.
  private void makeAllMoviesMap() {
    try {
      readFile = new Scanner(new File(allMovFile));
    } catch (FileNotFoundException e) {
      System.out.println("All Movie Info file not found.");
      e.printStackTrace();
    }

    try {

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

          Movie valueMovie = new Movie(keyTitle, year, genre, director, actor1,
              actor2, actor3, tag1, tag2, tag3, picFile, plotSum);
          allMovies.put(keyTitle, valueMovie);
        }
      }
    } catch (NoSuchElementException e) {

      System.out.println("Non-fatal exception at end of allMovies file");
    }

    // Testing Loop
    for (String movie : allMovies.keySet()) {

      String key = movie;
      Movie value = allMovies.get(key);
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

  // Makes the HasHMap holding each user's rental history, which is used to
  // display that respective list depending on which user is logged in.
  private void makeRentalHistoryMap() {
    try {
      readFile = new Scanner(new File(rentHistFile));
    } catch (FileNotFoundException e) {
      System.out.println("Rental Info file not found.");
      e.printStackTrace();
    }

    try {

      while (readFile.hasNextLine()) {

        // String tokenizer breaks each text line into raw tokens
        StringTokenizer st = new StringTokenizer(readFile.nextLine(), "|");
        String keyUser = st.nextToken();
        ArrayList valueRentals = new ArrayList<String>();

        while (st.hasMoreTokens()) {

          valueRentals.add(st.nextToken());

          // rentalHistory.put(keyUser, valueRentals);
        }
        rentalHistory.put(keyUser, valueRentals);
      }
    } catch (NoSuchElementException e) {

      System.out.println("Non-fatal exception at end of rentalHistories file");
    }

    // Testing Loop
    for (String user : rentalHistory.keySet()) {

      String key = user;
      System.out.println("Rentals from user " + key);

      for (String movie : rentalHistory.get(user)) {
        System.out.println(movie);
      }
    }
  }

  // Resets entrance page & login panel when user logs out
  void refreshLogin() {

    // Clears the window contents
    this.remove(topPan);
    this.remove(main);
    this.repaint();

    // Resets the values for fields containing username/account type info,
    // both protecting user data and allowing another person to log in
    currentUserID = null;
    currentUserName = null;
    adminUser = false;
    regUser = false;

    // Redraws the login screen back to its original state
    TopPanel.logo = new ImageIcon("IconPics/xflix_logo.png");
    topPan.add(new JLabel(TopPanel.logo));
    topPan.setVisible(true);
    add(topPan, BorderLayout.NORTH);

    login = new LoginPanel(this);
    login.setVisible(true);
    loginHolder.add(login);
    add(loginHolder, BorderLayout.CENTER);

    getRootPane().setDefaultButton(login.userLogin);
    this.revalidate();
    this.repaint();
  }

  @Override
  public void actionPerformed(ActionEvent e) {

    if (e.getSource() == login.userLogin) {
      TopPanel.logo = new ImageIcon("IconPics/xflix_logo_small.png");
      TopPanel.userIcon = new ImageIcon("IconPics/user_icon_small.png");
    }

    if (e.getSource() == login.forgotPass) {

      JOptionPane passwordReset = new JOptionPane();
      passwordReset.showInputDialog("Please enter your email address on file.");
    }
  }

  // Writes the current content of the HashMap containing the movies to the
  // master text file, overwriting the original contents. It then is supposed to
  // clear the HashMap and recreate it so that system admins can instantly see
  // the results of any changes they make.
  void writeMoviesToFile() {
    try (BufferedWriter data = new BufferedWriter(new FileWriter(allMovFile),
        1024)) {
      newReleases.keySet().forEach(k -> {
        try {
          data.append(newReleases.get(k).toString());
        } catch (IOException e) {
          System.err.println("Error opening new movies file.");
        }
      });
      data.flush(); // write the rest of the buffer to the file
    } catch (IOException e) { // catch all the errors here
      System.err.println("Error opening new movies file.");
    }
    
    //Clears movies HashMap since the data is no longer current 
    allMovies.clear();
    
    //Rewrites the HasMap based on the newest, updated file
    makeNewMoviesMap();
  }
  
  // Writes the current content of the HashMap containing the users to the
  // master text file, overwriting the original contents. It then is supposed to
  // clear the HashMap and recreate it so that system admins can instantly see
  // the results of any changes they make.
  void writeUsersToFile() {
    try (BufferedWriter data = new BufferedWriter(new FileWriter(userAcctsFile),
        1024)) {
      userAccounts.keySet().forEach(k -> {
        try {
          data.append(userAccounts.get(k).toString());
        } catch (IOException e) {
          System.err.println("Error opening new movies file.");
        }
      });
      data.flush(); // write the rest of the buffer to the file
    } catch (IOException e) { // catch all the errors here
      System.err.println("Error opening new movies file.");
    }
    
    //Clears movies HashMap since the data is no longer current 
    userAccounts.clear();
    
    //Rewrites the HasMap based on the newest, updated file
    makeUserAcctMap();
  }
  
  
  
  
  
}
