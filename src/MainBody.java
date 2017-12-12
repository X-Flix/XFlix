import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JViewport;
import javax.swing.ScrollPaneConstants;

public class MainBody extends JTabbedPane implements ActionListener {

  protected PrimaryWindow pw;

  protected JPanel whatsNew = new JPanel();
  protected JPanel browseLibrary = new JPanel();
  protected JPanel accountInfo = new JPanel();
  protected JPanel cart = new JPanel();
  protected JPanel rentalHistory = new JPanel();

  protected JPanel searchAccounts = new JPanel();
  protected JPanel viewEditAccount = new JPanel();
  protected JPanel createAccount = new JPanel();
  protected JPanel searchMovies = new JPanel();
  protected JPanel viewEditMovie = new JPanel();
  protected JPanel addNewMovie = new JPanel();

  protected class CartButton extends JButton {
    protected String movTitle;

    CartButton(String button, String movie) {
      super(button);
      movTitle = movie;
    }

    public String getMovie() {
      return movTitle;
    }
  }

  public MainBody(PrimaryWindow pw) {

    super();

    this.pw = pw;

    // Constructor displays different tabs inside the main window based upon
    // what category of user has logged into the software
    if (pw.regUser) {

      this.addTab("What's New", whatsNew);
      this.addTab("Browse Movie Library", browseLibrary);
      this.addTab("Account Info", accountInfo);
      this.addTab("Cart", cart);
      this.addTab("Rental History", rentalHistory);

      // Loads information and content into the respective tabs
      makeWhatsNewTab(pw.newReleases);
      makeAcctInfoTab(pw.userAccounts);
      makeRentHistTab(pw.rentalHistory);

    } else if (pw.adminUser) {

      this.addTab("Search User Accounts", searchAccounts);
      this.addTab("View/Edit User Account", viewEditAccount);
      this.addTab("Create New User Account", createAccount);
      this.addTab("Search Movie Library", searchMovies);
      this.addTab("View/Edit Movie Info", viewEditMovie);
      this.addTab("Add Movie to Library", addNewMovie);
    }

  }

  @Override
  public void actionPerformed(ActionEvent e) {

    // This responds to a user clicking one of the buttons next to a movie,
    // which adds it to their rental cart and displays a confirmation message
    if (e.getSource() instanceof CartButton) {
      System.out.println("button INSIDE generic ACT LISTENER");
    }

    System.out.println("GENERIC ACTION LISTENER");

  }

  // This sets up the What's New tab under a user profile that shows the most
  // recent additions to the film library. It is independent of user account.
  public void makeWhatsNewTab(HashMap<String, Movie> hm) {

    JPanel whatsNewHolder = new JPanel();

    GridLayout grid = new GridLayout(hm.size(), 1);
    FlowLayout flow = new FlowLayout();
    BoxLayout box = new BoxLayout(whatsNew, BoxLayout.Y_AXIS);

    whatsNew.setLayout(box);

    JScrollPane wnScrollPane = new JScrollPane(
        ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
        ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    wnScrollPane.setVisible(true);

    whatsNewHolder.setLayout(grid);

    // Creates the list of new movies, adding the picture, description, and a
    // button to have the movie added to a customer's cart
    for (String movie : hm.keySet()) {

      String key = movie;
      Movie value = hm.get(key);
      JPanel rowHolder = new JPanel();
      rowHolder.setLayout(new BoxLayout(rowHolder, BoxLayout.X_AXIS));

      JLabel picHolder;
      JPanel movieInfo = new JPanel();
      movieInfo.setLayout(new BoxLayout(movieInfo, BoxLayout.Y_AXIS));
      JPanel buttonHolder = new JPanel();

      CartButton addToCart = new CartButton("Add to Cart", key);
      addToCart.setToolTipText("Rent this title today!\nAdd it to your cart.");
      addToCart.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
          if (pw.rentalHistory.containsKey(addToCart.movTitle)
          // &&
          // pw.rentalHistory.get(addToCart.movTitle).equalsIgnoreCase("Checked-Out"))
          ) {
            System.out.println("Movie already CHECKED OUT");
            JOptionPane.showMessageDialog(null,
                "You already have checked out this movie and it"
                    + "\nis stil in your possession.",
                "Movie Already Checked Out", JOptionPane.ERROR_MESSAGE);

          } else if (pw.movieCart.contains(addToCart.movTitle)) {
            System.out.println("Movie ALREADY in queue");
            JOptionPane.showMessageDialog(null,
                "You already have this movie in your cart.",
                "Item Already in Cart", JOptionPane.ERROR_MESSAGE);
          } else {
            System.out.println("You clicked button: " + addToCart.movTitle);
            pw.movieCart.add(addToCart.movTitle);
            JOptionPane.showMessageDialog(null,
                addToCart.movTitle + "has been placed in your cart.",
                "Item Sent to Cart", JOptionPane.PLAIN_MESSAGE);

          }
        }
      });

      picHolder = new JLabel(value.moviePoster);
      picHolder.setPreferredSize(new Dimension(150, 222));
      rowHolder.add(picHolder);

      String actors = String.format("%s, %s, %s", value.cast1, value.cast2,
          value.cast3);
      String tags = String.format("%s, %s, %s", value.tag1, value.tag2,
          value.tag3);
      movieInfo.add(new JLabel("Title: " + value.title));
      movieInfo.add(new JLabel("Released: " + value.year));
      movieInfo.add(new JLabel("Genre: " + value.genre));
      movieInfo.add(new JLabel("Director: " + value.director));
      movieInfo.add(new JLabel("Cast: " + actors));
      movieInfo.add(new JLabel("Tags: " + tags));
      movieInfo.add(new JLabel("<HTML>Summary: " + value.synopsis + "<HTML>"));

      rowHolder.add(movieInfo);
      rowHolder.add(buttonHolder.add(addToCart));

      whatsNewHolder.add(rowHolder);
      whatsNewHolder.setVisible(true);
      whatsNewHolder.repaint();
      whatsNewHolder.revalidate();
    }

    wnScrollPane.setViewportView(whatsNewHolder);
    wnScrollPane.repaint();
    wnScrollPane.revalidate();
    whatsNew.add(wnScrollPane);
    whatsNew.repaint();
    whatsNew.revalidate();

  }

  // This sets up the Account Info tab under a user profile
  public void makeAcctInfoTab(HashMap<String, UserAccount> hm) {

    JPanel acctInfoHolder = new JPanel();

    acctInfoHolder.setLayout(new GridLayout(11, 2, 0, 7)); // set frame layout

    // Customer demographic info
    JLabel userName = new JLabel("First name:");
    acctInfoHolder.add(userName);
    JTextField userFirstName = new JTextField(
        hm.get(pw.currentUserID).firstName, 40);
    acctInfoHolder.add(userFirstName);
    JLabel userLast = new JLabel("Last name:");
    acctInfoHolder.add(userLast);
    JTextField userLastName = new JTextField(hm.get(pw.currentUserID).lastName,
        40);
    acctInfoHolder.add(userLastName);
    JLabel userEmail = new JLabel("Email address:");
    acctInfoHolder.add(userEmail);
    JTextField userEmailAddress = new JTextField(
        hm.get(pw.currentUserID).emailAddr, 40);
    acctInfoHolder.add(userEmailAddress);
    JLabel userStreet = new JLabel("Street:");
    acctInfoHolder.add(userStreet);
    JTextField userStreetAddr = new JTextField(
        hm.get(pw.currentUserID).streetAddr, 40);
    acctInfoHolder.add(userStreetAddr);
    JLabel userCity = new JLabel("City:");
    acctInfoHolder.add(userCity);
    JTextField userCityAddr = new JTextField(hm.get(pw.currentUserID).city, 40);
    acctInfoHolder.add(userCityAddr);
    JLabel userState = new JLabel("State:");
    acctInfoHolder.add(userState);
    JTextField userStateAddr = new JTextField(hm.get(pw.currentUserID).state,
        40);
    acctInfoHolder.add(userStateAddr);
    JLabel userZip = new JLabel("Zip code:");
    acctInfoHolder.add(userZip);
    JTextField userZipCode = new JTextField(hm.get(pw.currentUserID).zipCode,
        40);
    acctInfoHolder.add(userZipCode);

    // Customer payment info
    JLabel userPayment = new JLabel("Payment method:");
    acctInfoHolder.add(userPayment);
    JTextField paymentMethod = new JTextField(
        hm.get(pw.currentUserID).paymentType, 40);
    acctInfoHolder.add(paymentMethod);
    JLabel userCardType = new JLabel("Card type:");
    acctInfoHolder.add(userCardType);
    JTextField cardTypeUser = new JTextField(hm.get(pw.currentUserID).cardType,
        40);
    acctInfoHolder.add(cardTypeUser);
    JLabel userCard = new JLabel("Card number:");
    acctInfoHolder.add(userCard);
    JTextField cardNumber = new JTextField(
        hm.get(pw.currentUserID).accountNumber, 40);
    acctInfoHolder.add(cardNumber);
    JLabel userExpire = new JLabel("Expire Date:");
    acctInfoHolder.add(userExpire);
    JTextField expireDate = new JTextField(
        hm.get(pw.currentUserID).cardExpireDate, 40);
    acctInfoHolder.add(expireDate);

    JButton saveButton = new JButton("Save Changes");
    saveButton.setToolTipText("Click here to save any changes you"
        + "\nmake to your account/profile.");
    saveButton.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent arg0) {
        hm.get(pw.currentUserID).firstName = userFirstName.getText();
        hm.get(pw.currentUserID).lastName = userLastName.getText();
        hm.get(pw.currentUserID).emailAddr = userEmailAddress.getText();
        hm.get(pw.currentUserID).streetAddr = userStreetAddr.getText();
        hm.get(pw.currentUserID).city = userCityAddr.getText();
        hm.get(pw.currentUserID).state = userStateAddr.getText();
        hm.get(pw.currentUserID).zipCode = userZipCode.getText();
        hm.get(pw.currentUserID).paymentType = paymentMethod.getText();
        hm.get(pw.currentUserID).cardType = cardTypeUser.getText();
        hm.get(pw.currentUserID).accountNumber = cardNumber.getText();
        hm.get(pw.currentUserID).cardExpireDate = expireDate.getText();

        JOptionPane.showMessageDialog(null,
            "Changes to your account have been saved successfully.",
            "Account Info Updated", JOptionPane.PLAIN_MESSAGE);
        
      }

    });

    accountInfo.add(acctInfoHolder);

  }

  // This sets up the Rental History tab under a user profile that shows all of
  // the previously rented films.
  public void makeRentHistTab(HashMap<String, ArrayList<String>> hm) {
    System.out.println("HELLLOOOO");
    JPanel rentalHolder = new JPanel();
    rentalHolder.setLayout(new BoxLayout(rentalHolder, BoxLayout.Y_AXIS));
    JPanel rowHolder = new JPanel();
    rowHolder.setLayout(new BoxLayout(rowHolder, BoxLayout.X_AXIS));

    for (String user : hm.keySet()) {
      System.out.println("HELLLOOOO");
      String key = user;
      for (String movie : hm.get(user)) {
        StringTokenizer st = new StringTokenizer(movie, "|");
        String status = st.nextToken();
        System.out.println("Movie: " + movie + " - Status: " + status);
      }
    }

    // System.out.println("Current user logged in: " + pw.currentUserID);
    // ArrayList rentals = hm.get(pw.currentUserID);
    // //String movie;
    // String status;
    //
    // for (String movie : hm.get(pw.currentUserID)) {
    // StringTokenizer st = new StringTokenizer(movie, "|");
    // movie = st.nextToken();
    // status = st.nextToken();
    // System.out.println("Movie: " + movie + " - Status: " + status);
    // }
  }
}
