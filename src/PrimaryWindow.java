/*
 * This class is for the welcome screen that a user is first presented with when
 * the application is run. It prompts them to enter their username/password to
 * gain access to X-Flix and use the service or view/update their account info.
 */

// Imports the AWT and Swing packages needed to implement GUI elements
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

import javax.swing.*;

public class PrimaryWindow extends JFrame implements ActionListener {

  protected TopPanel topPan;
  protected LoginPanel login;
  protected JPanel loginHolder = new JPanel(new FlowLayout());
  protected MainBody main;

  protected String currentUserID = null;
  protected String currentUserName = null;
  protected boolean adminUser = false;
  protected boolean regUser = false;

  // Text files containing information on usernames/passwords, user accounts,
  // and movies currently in the rental library
  protected static String userPassFile = "username_password.txt";
  protected static String userAcctsFile = "user_account_info.txt";
  protected static String newMovFIle = "new_movies.txt";
  private Scanner readFile;

  // HashMaps serving as temporary databases that house info needed by users
  // to search for movies, as well as add or edit information under their
  // profiles.
  protected HashMap<String, String> userAndPass = new HashMap();
  protected HashMap<String, UserAccount> userAccounts = new HashMap();
  protected HashMap<String, Movie> newReleases = new HashMap();
  protected HashSet<String> movieCart = new HashSet();
  

  public PrimaryWindow() {
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

    createInfoTables();

//    String key = "BayWatch";
//    Movie value = newReleases.get("BayWatch");
//    add(new JLabel(value.moviePoster), BorderLayout.SOUTH);
//    repaint();
  }

  // Creates various lists/maps/tables based on the current content of the
  // text files containing various information such as username/password, user
  // account info, current movie library, rental history, etc.
  public void createInfoTables() {

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

  public void makeUserPassMap() {
    try {
      readFile = new Scanner(new File(userPassFile));
    } catch (FileNotFoundException e) {
      System.out.println("User-Password file not found.");
      e.printStackTrace();
    }

    try {

      String userPass;
      String keyUser;
      String valuePass;

      while ((userPass = readFile.nextLine()) != null) {

        // String tokenizer breaks each text line into raw tokens
        StringTokenizer st = new StringTokenizer(userPass);

        while (st.hasMoreTokens()) {
          keyUser = st.nextToken();
          valuePass = st.nextToken();
          userAndPass.put(keyUser, valuePass);
        }

      }
    } catch (NoSuchElementException e) {

      System.out.println("Non-fatal exception at end of userPass file");
    }
    
    /* Testing code to loop through elements of a map
    for (String user : userAndPass.keySet()) {

      String key = user.toString();
      String value = userAndPass.get(user).toString();
      System.out.println("User: " + key + "/ Password: " + value);
    }
    */

  }

  // Creates objects of the UserAccount class based on information in a text
  // file containing all of the user info, and places those user objects into
  // a HashMap with the email address (username/ID) as a key, and the user
  // objects as values. This map will be how user info is retrieved by the
  // program for customers or admins to view/edit.
  public void makeUserAcctMap() {
    try {
      readFile = new Scanner(new File(userAcctsFile));
    } catch (FileNotFoundException e) {
      System.out.println("User Account Info file not found.");
      e.printStackTrace();
    }

    try {

      String userInfo;
      String keyEmail;
      UserAccount valueUserInfo;
      String email;
      String fName;
      String lName;
      while ((userInfo = readFile.nextLine()) != null) {

        // String tokenizer breaks each text line into raw tokens
        StringTokenizer st = new StringTokenizer(userInfo);

        while (st.hasMoreTokens()) {
          keyEmail = st.nextToken();
          fName = st.nextToken();
          lName = st.nextToken();

          valueUserInfo = new UserAccount(keyEmail, fName, lName);
          userAccounts.put(keyEmail, valueUserInfo);

        }
      }
    } catch (NoSuchElementException e) {

      System.out.println("Non-fatal exception at end of userAccounts file");
    }
  }

  // Creates objects of the Movie class (new releases) based on information in
  // a text file containing the movie info, and places those user objects into
  // a HashMap with the movie title as a key, and the objects as values. This
  // map will be how user info is retrieved by the program for customers or
  // admins to view/edit.
  public void makeNewMoviesMap() {
    try {
      readFile = new Scanner(new File(newMovFIle));
    } catch (FileNotFoundException e) {
      System.out.println("New Movie Info file not found.");
      e.printStackTrace();
    }

    try {

      String newMovInfo;
      String keyTitle;
      Movie valueNewMovie;
      String year;
      String genre;
      String director;
      String actor1;
      String actor2;
      String actor3;
      String tag1;
      String tag2;
      String tag3;
      String picFile;
      String plotSum;

      while ((newMovInfo = readFile.nextLine()) != null) {

        // String tokenizer breaks each text line into raw tokens
        StringTokenizer st = new StringTokenizer(newMovInfo, "|");

        while (st.hasMoreTokens()) {
          keyTitle = st.nextToken();
          year = st.nextToken();
          genre = st.nextToken();
          director = st.nextToken();
          actor1 = st.nextToken();
          actor2 = st.nextToken();
          actor3 = st.nextToken();
          tag1 = st.nextToken();
          tag2 = st.nextToken();
          tag3 = st.nextToken();
          picFile = st.nextToken();
          plotSum = st.nextToken();

          valueNewMovie = new Movie(keyTitle, year, genre, director, actor1,
              actor2, actor3, tag1, tag2, tag3, picFile, plotSum);
          newReleases.put(keyTitle, valueNewMovie);
        }
      }
    } catch (NoSuchElementException e) {

      System.out.println("Non-fatal exception at end of newMovies file");
    }
    
    //Testing Loop
    for (String movie : newReleases.keySet()) {

      String key = movie;
      Movie value = newReleases.get(key);
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
  public void refreshLogin() {

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
    topPan.logo = new ImageIcon(getClass().getResource("xflix_logo.png"));
    topPan.add(new JLabel(topPan.logo));
    topPan.setVisible(true);
    add(topPan, BorderLayout.NORTH);

    login = new LoginPanel(this);
    login.setVisible(true);
    loginHolder.add(login);
    add(loginHolder, BorderLayout.CENTER);
    this.revalidate();
    this.repaint();
  }

  @Override
  public void actionPerformed(ActionEvent e) {

    if (e.getSource() == login.userLogin) {
      TopPanel.logo = new ImageIcon(
          getClass().getResource("xflix_logo_small.png"));
      TopPanel.userIcon = new ImageIcon(
          getClass().getResource("user_icon_small.png"));
    }

    if (e.getSource() == login.forgotPass) {

      JOptionPane passwordReset = new JOptionPane();
      passwordReset.showInputDialog("Please enter your email address on file.");
    }
  }
  
  void writeMoviesToFile(){
	  try (BufferedWriter data = new BufferedWriter(new FileWriter(newMovFIle), 1024)) {
		  newReleases.keySet().forEach(k->{
			try {
				data.append(newReleases.get(k).toString());
			} catch (IOException e) {
		        System.err.println("Error opening new movies file.");
			}
		});
		  data.flush(); // write the rest of the buffer to the file
	  } catch (IOException e){  // catch all the errors here
	      System.err.println("Error opening file.");
	  }
  }

}
