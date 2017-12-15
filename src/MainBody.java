import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.StringTokenizer;

public class MainBody extends JTabbedPane implements ActionListener {

  private PrimaryWindow pw;

  // Panels that make up the various tabs in the TabbedPane layout. For each
  // tab, these are the outermost containers for the various panels, buttons,
  // labels, etc. within
  protected JPanel whatsNew = new JPanel();
  protected JPanel browseLibrary = new JPanel();
  protected JPanel accountInfo = new JPanel();
  protected JPanel cart = new JPanel();
  protected JPanel rentalHistory = new JPanel();

  protected JPanel searchAccounts = new JPanel();
  protected JPanel viewEditCreateAccount = new JPanel();
  protected JPanel searchMovies = new JPanel();
  protected JPanel viewEditCreateMovie = new JPanel();

  protected class MovieButton extends JButton {
    protected String movTitle;

    MovieButton(String button, String movie) {
      super(button);
      movTitle = movie;
    }

    public String getMovie() {
      return movTitle;
    }
  }

  // Variables for editable textfields that must be accessed by multiple tabs
  JTextField userFirstName;
  JTextField userLastName;
  JTextField userEmailAddress;
  JPasswordField userPass;
  JTextField userStreetAddr;
  JLabel userCityAddr;
  JTextField userStateAddr;
  JTextField userZipCode;
  JTextField paymentMethod;
  JTextField cardTypeUser;
  JTextField cardNumber;
  JTextField expireDate;
  
  //Another set if similar variables
  JTextField title;
  JTextField year;
  JTextField genre;
  JTextField director;
  JTextField cast1;
  JTextField cast2;
  JTextField cast3;
  JTextField tag1;
  JTextField tag2;
  JTextField tag3;
  JTextField picFileName;
  JTextField synopsis;

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

      // Loads information and content that a customer user may view or
      // manipulate into the respective tabs that only display for a user
      makeWhatsNewTab(pw.newReleases);
      makeBrowseTab(pw.allMovies);
      makeAcctInfoTab(pw.userAccounts);
      makeCartTab(pw.movieCart);
      makeRentHistTab(pw.rentalHistory);

    } else if (pw.adminUser) {

      this.addTab("Search User Accounts", searchAccounts);
      this.addTab("View/Edit/Create User Account", viewEditCreateAccount);
      this.addTab("Search Movie Library", searchMovies);
      this.addTab("View/Edit/Create Movie Info", viewEditCreateMovie);

      // Loads information and content that an admin user may view or manipulate
      // into the respective tabs that only display for a user
      //makeAdminSearchUsers(pw.userAccounts);
      makeAdminAcctInfo(pw.userAccounts);
      makeAdminSearchMovies(pw.allMovies);
      makeAdminMovieInfo(pw.allMovies);

    }

  }
  

@Override
  public void actionPerformed(ActionEvent e) {

    // This responds to a user clicking one of the buttons next to a movie,
    // which adds it to their rental cart and displays a confirmation message
    if (e.getSource() instanceof MovieButton) {
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

      MovieButton addToCart = new MovieButton("Add to Cart", key);
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
                addToCart.movTitle + " has been placed in your cart.",
                "Item Sent to Cart", JOptionPane.PLAIN_MESSAGE);
            cart.repaint();
            cart.revalidate();

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

  // Makes the Browse tab where users can search for a movie by criteria
  public void makeBrowseTab(HashMap<String, Movie> hm) {

    BoxLayout box = new BoxLayout(browseLibrary, BoxLayout.Y_AXIS);
    browseLibrary.setLayout(box);

    // Sets up the area where search criteria is entered by user
    JPanel searchBox = new JPanel();
    String[] criteria = { "Title", "Year", "Genre", "Director", "Cast Member", "Tag",
        "Plot Keyword(s)" };
    JComboBox searchCriteria = new JComboBox<String>(criteria);
    searchCriteria.setBackground(Color.WHITE);
    JTextField searchInput = new JTextField(50);
    JButton searchButton = new JButton("Search");

    searchBox.add(new JLabel("Select Search Criteria"));
    searchBox.add(searchCriteria);
    searchBox.add(searchInput);
    searchBox.add(searchButton);

    browseLibrary.add(searchBox);

    // Sets up the area where the search results are displayed
    JPanel resultsBox = new JPanel();

    JScrollPane bmScrollPane = new JScrollPane(
        ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
        ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    bmScrollPane.setVisible(true);

    // Sets up the actionlistener for the search button based upon what search
    // criteria the user selected
    searchButton.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {

        resultsBox.removeAll();

        // Current selection in the drop=-down list
        int s = searchCriteria.getSelectedIndex();

        switch (s) {
          case 0:
            for (String movie : hm.keySet()) {
              String searchTerm = searchInput.getText().trim();
              if (hm.get(movie).title.equalsIgnoreCase(searchTerm)
                  || (hm.get(movie).title.toLowerCase()
                      .contains(searchTerm.toLowerCase()))) {
                pw.movieSearchResults.add(movie);
                System.out.println("Found a title match");
              }
            }
            break;
          case 1:
            for (String movie : hm.keySet()) {
              String searchTerm = searchInput.getText().trim();
              if (hm.get(movie).year.equalsIgnoreCase(searchTerm)
                  || (hm.get(movie).year.toLowerCase()
                      .contains(searchTerm.toLowerCase()))) {
                pw.movieSearchResults.add(movie);
                System.out.println("Found a year match");
              }
            }
            break;
          case 2:
            for (String movie : hm.keySet()) {
              String searchTerm = searchInput.getText().trim();
              if (hm.get(movie).genre.equalsIgnoreCase(searchTerm)
                  || (hm.get(movie).genre.toLowerCase()
                      .contains(searchTerm.toLowerCase()))) {
                pw.movieSearchResults.add(movie);
                System.out.println("Found a director match");
              }
            }
            break;
          case 3:
              for (String movie : hm.keySet()) {
                String searchTerm = searchInput.getText().trim();
                if (hm.get(movie).director.equalsIgnoreCase(searchTerm)
                    || (hm.get(movie).director.toLowerCase()
                        .contains(searchTerm.toLowerCase()))) {
                  pw.movieSearchResults.add(movie);
                  System.out.println("Found a director match");
                }
              }
              break;
          case 4:
            for (String movie : hm.keySet()) {
              String searchTerm = searchInput.getText().trim();
              if (hm.get(movie).cast1.equalsIgnoreCase(searchTerm)
                  || hm.get(movie).cast1.toLowerCase()
                      .contains(searchTerm.toLowerCase())
                  || hm.get(movie).cast2.equalsIgnoreCase(searchTerm)
                  || hm.get(movie).cast2.toLowerCase()
                      .contains(searchTerm.toLowerCase())
                  || hm.get(movie).cast3.equalsIgnoreCase(searchTerm)
                  || hm.get(movie).cast3.toLowerCase().contains(searchTerm.toLowerCase())) {
                pw.movieSearchResults.add(movie);
                System.out.println("Found a cast match");
              }
            }
            break;
          case 5:
            for (String movie : hm.keySet()) {
              String searchTerm = searchInput.getText().trim();
              if (hm.get(movie).tag1.equalsIgnoreCase(searchTerm)
                  || hm.get(movie).tag1.toLowerCase()
                      .contains(searchTerm.toLowerCase())
                  || hm.get(movie).tag2.equalsIgnoreCase(searchTerm)
                  || hm.get(movie).tag2.toLowerCase()
                      .contains(searchTerm.toLowerCase())
                  || hm.get(movie).tag3.equalsIgnoreCase(searchTerm)
                  || hm.get(movie).tag3.toLowerCase()
                      .contains(searchTerm.toLowerCase())) {
                pw.movieSearchResults.add(movie);
                System.out.println("Found a tag match");
              }
            }
            break;
          case 6:
            for (String movie : hm.keySet()) {
              String searchTerm = searchInput.getText().trim();
              if (hm.get(movie).synopsis.toLowerCase()
                  .contains(searchTerm.toLowerCase())) {
                pw.movieSearchResults.add(movie);
                System.out.println("Found a plot match");
              }
            }
            break;
        }

        if (pw.movieSearchResults.isEmpty()) {
          System.out.println("Sorry, we could not find a match");
          JOptionPane.showMessageDialog(null,
              "Sorry, we could not find any titles that match the"
                  + "\ncriteria you provided.",
              "No Results Found", JOptionPane.ERROR_MESSAGE);
          return;
        } else {
          System.out.println("Search results:");
          for (String movie : pw.movieSearchResults) {
            System.out.println(movie);
          }

          resultsBox.setLayout(new GridLayout(pw.movieSearchResults.size(), 1));

          for (String movie : pw.movieSearchResults) {

            String key = movie;
            Movie value = hm.get(key);
            JPanel rowHolder = new JPanel();
            rowHolder.setLayout(new BoxLayout(rowHolder, BoxLayout.X_AXIS));

            JLabel picHolder;
            JPanel movieInfo = new JPanel();
            movieInfo.setLayout(new BoxLayout(movieInfo, BoxLayout.Y_AXIS));
            JPanel buttonHolder = new JPanel();

            MovieButton addToCart = new MovieButton("Add to Cart", key);
            addToCart
                .setToolTipText("Rent this title today!\nAdd it to your cart.");
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
                  System.out
                      .println("You clicked button: " + addToCart.movTitle);
                  pw.movieCart.add(addToCart.movTitle);
                  JOptionPane.showMessageDialog(null,
                      addToCart.movTitle + " has been placed in your cart.",
                      "Item Sent to Cart", JOptionPane.PLAIN_MESSAGE);
                  cart.repaint();
                  cart.revalidate();

                }
              }
            });

            picHolder = new JLabel(value.moviePoster);
            picHolder.setPreferredSize(new Dimension(150, 222));
            rowHolder.add(picHolder);

            String actors = String.format("%s, %s, %s", value.cast1,
                value.cast2, value.cast3);
            String tags = String.format("%s, %s, %s", value.tag1, value.tag2,
                value.tag3);
            movieInfo.add(new JLabel("Title: " + value.title));
            movieInfo.add(new JLabel("Released: " + value.year));
            movieInfo.add(new JLabel("Genre: " + value.genre));
            movieInfo.add(new JLabel("Director: " + value.director));
            movieInfo.add(new JLabel("Cast: " + actors));
            movieInfo.add(new JLabel("Tags: " + tags));
            movieInfo
                .add(new JLabel("<HTML>Summary: " + value.synopsis + "<HTML>"));

            rowHolder.add(movieInfo);
            rowHolder.add(buttonHolder.add(addToCart));

            resultsBox.add(rowHolder);
            resultsBox.setVisible(true);
            resultsBox.repaint();
            resultsBox.revalidate();
          }
          bmScrollPane.setViewportView(resultsBox);
          bmScrollPane.repaint();
          bmScrollPane.revalidate();
          browseLibrary.add(bmScrollPane);
          browseLibrary.repaint();
          browseLibrary.revalidate();

          // Clears the HashSet with search results
          pw.movieSearchResults.clear();
        }

      }
    });
  }

  // This sets up the Cart tab under a user profile
  public void makeCartTab(HashSet<String> hm) {

    // JPanel cartHolder = new JPanel();
    // cartHolder.setLayout(new BoxLayout(cartHolder, BoxLayout.Y_AXIS));;
    // cartHolder.setVisible(true);
    // //cart.setLayout(new BoxLayout(cart, BoxLayout.Y_AXIS));
    // //cart.setLayout(new GridLayout(1,10));
    //
    //
    // for (String movie: hm) {
    // cartHolder.add(new JLabel(movie));
    //
    // cartHolder.setVisible(true);
    // cartHolder.repaint();
    // cartHolder.revalidate();
    // }
    // cart.add(cartHolder);
    // cart.setVisible(true);
    // cart.repaint();
    // cart.revalidate();
  }

  // This sets up the Account Info tab under a user profile
  public void makeAcctInfoTab(HashMap<String, UserAccount> hm) {

    JPanel acctInfoHolder = new JPanel();

    acctInfoHolder.setLayout(new GridLayout(13, 2, 0, 7)); // set frame layout

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
    JLabel passLabel = new JLabel("Password:");
    acctInfoHolder.add(passLabel);
    JPasswordField userPass = new JPasswordField(
        hm.get(pw.currentUserID).password, 40);
    acctInfoHolder.add(userPass);

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
      public void actionPerformed(ActionEvent s) {
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
    acctInfoHolder.add(saveButton);

    accountInfo.add(acctInfoHolder);

  }

  // This sets up the Rental History tab under a user profile that shows all of
  // the previously rented films.
  public void makeRentHistTab(HashMap<String, ArrayList<String>> hm) {

    JPanel rentalHolder;// = new JPanel();
    // rentalHolder.setLayout(new BoxLayout(rentalHolder, BoxLayout.Y_AXIS));
    // rentalHolder.setLayout(new GridLayout(20, 2));
    JPanel rowHolder;// = new JPanel();
    // rowHolder.setLayout(new BoxLayout(rowHolder, BoxLayout.X_AXIS));
    // rowHolder.setLayout(new GridLayout(1, 2));

    ArrayList<String> userRented = new ArrayList<String>();

    userRented = hm.get(pw.currentUserID);

    for (String rental : userRented) {

      StringTokenizer st = new StringTokenizer(rental, "~");

      String movie = st.nextToken();
      String status = st.nextToken();

      System.out.println("Movie: " + movie + " - Status: " + status);

      rowHolder = new JPanel();
      rowHolder.add(new JLabel(movie));
      rowHolder.add(new JLabel(status));

      rentalHolder = new JPanel();
      rentalHolder.add(rowHolder);
      rentalHolder.repaint();
      rentalHolder.revalidate();
      rentalHistory.add(rentalHolder);
      rentalHistory.repaint();
      rentalHistory.revalidate();
    }

    // rentalHistory.add(rentalHolder);
    // rentalHistory.repaint();
    // rentalHistory.revalidate();

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

  /*
   * This section creates the tabs that appear when an administrator logs in
   */
  
//  //This sets up the admin tab that allows them to search user accounts
//  private void makeAdminSearchUsers(HashMap<String, UserAccount> userAccounts) {
//      BoxLayout box = new BoxLayout(searchMovies, BoxLayout.Y_AXIS);
//      searchMovies.setLayout(box);
//
//      // Sets up the area where search criteria is entered by user
//      JPanel searchBox = new JPanel();
//      String[] criteria = { "Title", "Year", "Genre", "Cast Member", "Tag",
//          "Plot Keyword(s)" };
//      JComboBox searchCriteria = new JComboBox<String>(criteria);
//      searchCriteria.setBackground(Color.WHITE);
//      JTextField searchInput = new JTextField(50);
//      JButton searchButton = new JButton("Search");
//
//      searchBox.add(new JLabel("Select Search Criteria"));
//      searchBox.add(searchCriteria);
//      searchBox.add(searchInput);
//      searchBox.add(searchButton);
//
//      searchMovies.add(searchBox);
//
//      // Sets up the area where the search results are displayed
//      JPanel resultsBox = new JPanel();
//
//      JScrollPane bmScrollPane = new JScrollPane(
//          ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
//          ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
//      bmScrollPane.setVisible(true);
//
//      // Sets up the actionlistener for the search button based upon what search
//      // criteria the user selected
//      searchButton.addActionListener(new ActionListener() {
//
//        @Override
//        public void actionPerformed(ActionEvent e) {
//
//          resultsBox.removeAll();
//
//          // Current selection in the drop=-down list
//          int s = searchCriteria.getSelectedIndex();
//
//          switch (s) {
//            case 0:
//              for (String movie : hm.keySet()) {
//                String searchTerm = searchInput.getText().trim();
//                if (hm.get(movie).title.equalsIgnoreCase(searchTerm)
//                    || (hm.get(movie).title.toLowerCase()
//                        .contains(searchTerm.toLowerCase()))) {
//                  pw.movieSearchResults.add(movie);
//                  System.out.println("Found a title match");
//                }
//              }
//              break;
//            case 1:
//              for (String movie : hm.keySet()) {
//                String searchTerm = searchInput.getText().trim();
//                if (hm.get(movie).year.equalsIgnoreCase(searchTerm)
//                    || (hm.get(movie).year.toLowerCase()
//                        .contains(searchTerm.toLowerCase()))) {
//                  pw.movieSearchResults.add(movie);
//                  System.out.println("Found a year match");
//                }
//              }
//              break;
//            case 2:
//              for (String movie : hm.keySet()) {
//                String searchTerm = searchInput.getText().trim();
//                if (hm.get(movie).director.equalsIgnoreCase(searchTerm)
//                    || (hm.get(movie).director.toLowerCase()
//                        .contains(searchTerm.toLowerCase()))) {
//                  pw.movieSearchResults.add(movie);
//                  System.out.println("Found a director match");
//                }
//              }
//              break;
//            case 3:
//              for (String movie : hm.keySet()) {
//                String searchTerm = searchInput.getText().trim();
//                if (hm.get(movie).cast1.equalsIgnoreCase(searchTerm)
//                    || hm.get(movie).cast1.toLowerCase()
//                        .contains(searchTerm.toLowerCase())
//                    || hm.get(movie).cast2.equalsIgnoreCase(searchTerm)
//                    || hm.get(movie).cast2.toLowerCase()
//                        .contains(searchTerm.toLowerCase())
//                    || hm.get(movie).cast3.equalsIgnoreCase(searchTerm)
//                    || hm.get(movie).cast3.contains(searchTerm)) {
//                  pw.movieSearchResults.add(movie);
//                  System.out.println("Found a cast match");
//                }
//              }
//              break;
//            case 4:
//              for (String movie : hm.keySet()) {
//                String searchTerm = searchInput.getText().trim();
//                if (hm.get(movie).tag1.equalsIgnoreCase(searchTerm)
//                    || hm.get(movie).tag1.toLowerCase()
//                        .contains(searchTerm.toLowerCase())
//                    || hm.get(movie).tag2.equalsIgnoreCase(searchTerm)
//                    || hm.get(movie).tag2.toLowerCase()
//                        .contains(searchTerm.toLowerCase())
//                    || hm.get(movie).tag3.equalsIgnoreCase(searchTerm)
//                    || hm.get(movie).tag3.toLowerCase()
//                        .contains(searchTerm.toLowerCase())) {
//                  pw.movieSearchResults.add(movie);
//                  System.out.println("Found a tag match");
//                }
//              }
//              break;
//            case 5:
//              for (String movie : hm.keySet()) {
//                String searchTerm = searchInput.getText().trim();
//                if (hm.get(movie).synopsis.toLowerCase()
//                    .contains(searchTerm.toLowerCase())) {
//                  pw.movieSearchResults.add(movie);
//                  System.out.println("Found a plot match");
//                }
//              }
//              break;
//          }
//
//          if (pw.movieSearchResults.isEmpty()) {
//            System.out.println("Sorry, we could not find a match");
//            JOptionPane.showMessageDialog(null,
//                "Sorry, we could not find any titles that match the"
//                    + "\ncriteria you provided.",
//                "No Results Found", JOptionPane.ERROR_MESSAGE);
//            return;
//          } else {
//            System.out.println("Search results:");
//            for (String movie : pw.movieSearchResults) {
//              System.out.println(movie);
//            }
//
//            resultsBox.setLayout(new GridLayout(pw.movieSearchResults.size(), 1));
//
//            for (String movie : pw.movieSearchResults) {
//
//              String key = movie;
//              Movie value = hm.get(key);
//              JPanel rowHolder = new JPanel();
//              rowHolder.setLayout(new BoxLayout(rowHolder, BoxLayout.X_AXIS));
//
//              JLabel picHolder;
//              JPanel movieInfo = new JPanel();
//              movieInfo.setLayout(new BoxLayout(movieInfo, BoxLayout.Y_AXIS));
//              JPanel buttonHolder = new JPanel();
//
//              MovieButton seeMovieDetails = new MovieButton(
//                  "Send Movie Info \nto View/Edit tab.", key);
//              seeMovieDetails.setToolTipText(
//                  "Places movie in approproate tab to view and edit the record.");
//              seeMovieDetails.addActionListener(new ActionListener() {
//                @Override
//                public void actionPerformed(ActionEvent e) {
//                  //if (pw.movieSearchResults.contains(seeMovieDetails.movTitle)) {
//                  //seeMovieDetails.movTitle;
//                  Movie temp = hm.get(seeMovieDetails.movTitle);
//                  
//                  title.setText(temp.title);
//                  year.setText(temp.year);
//                  genre.setText(temp.genre);
//                  director.setText(temp.director);
//                  cast1.setText(temp.cast1);
//                  cast2.setText(temp.cast2);
//                  cast3.setText(temp.cast3);
//                  tag1.setText(temp.tag1);
//                  tag2.setText(temp.tag2);
//                  tag3.setText(temp.tag3);
//                  picFileName.setText(temp.picFileName);
//                  synopsis.setText(temp.synopsis);
//                  
//                    System.out.println("You clicked " + seeMovieDetails.movTitle);
//                    JOptionPane.showMessageDialog(null,
//                        "The details for " + seeMovieDetails.movTitle + "have been sent to the"
//                            + "\nView/Edit Tab for any changes to be made.",
//                        "Movie Info Forwarded", JOptionPane.ERROR_MESSAGE);
//
//                  //}
//                }
//              });
//
//              picHolder = new JLabel(value.moviePoster);
//              picHolder.setPreferredSize(new Dimension(150, 222));
//              rowHolder.add(picHolder);
//
//              String actors = String.format("%s, %s, %s", value.cast1,
//                  value.cast2, value.cast3);
//              String tags = String.format("%s, %s, %s", value.tag1, value.tag2,
//                  value.tag3);
//              movieInfo.add(new JLabel("Title: " + value.title));
//              movieInfo.add(new JLabel("Released: " + value.year));
//              movieInfo.add(new JLabel("Genre: " + value.genre));
//              movieInfo.add(new JLabel("Director: " + value.director));
//              movieInfo.add(new JLabel("Cast: " + actors));
//              movieInfo.add(new JLabel("Tags: " + tags));
//              movieInfo
//                  .add(new JLabel("<HTML>Summary: " + value.synopsis + "<HTML>"));
//
//              rowHolder.add(movieInfo);
//              rowHolder.add(buttonHolder.add(seeMovieDetails));
//
//              resultsBox.add(rowHolder);
//              resultsBox.setVisible(true);
//              resultsBox.repaint();
//              resultsBox.revalidate();
//            }
//            bmScrollPane.setViewportView(resultsBox);
//            bmScrollPane.repaint();
//            bmScrollPane.revalidate();
//            searchMovies.add(bmScrollPane);
//            searchMovies.repaint();
//            searchMovies.revalidate();
//
//            // Clears the HashSet with search results
//            pw.movieSearchResults.clear();
//          }
//
//        }
//      });
//      
//  }
//  
  // This sets up the admin account info tab
  public void makeAdminAcctInfo(HashMap<String, UserAccount> hm) {
    JPanel acctInfoHolder = new JPanel();

    acctInfoHolder.setLayout(new GridLayout(13, 2, 0, 7)); // set frame layout

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
    JLabel passLabel = new JLabel("Password:");
    acctInfoHolder.add(passLabel);
    JPasswordField userPass = new JPasswordField(
        hm.get(pw.currentUserID).password, 40);
    acctInfoHolder.add(userPass);

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
      public void actionPerformed(ActionEvent s) {
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

        // Invokes method that updates HashMap and master text file
        pw.writeUsersToFile();

        JOptionPane.showMessageDialog(null,
            "Changes to your account have been saved successfully.",
            "Account Info Updated", JOptionPane.PLAIN_MESSAGE);

      }

    });

    JButton clearButton = new JButton("Clear");
    saveButton.setToolTipText("Click here to clear this form so that you"
        + "\nmay work on or create another user account.");
    clearButton.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent arg0) {
        userFirstName.setText(null);
        userLastName.setText(null);
        userEmailAddress.setText(null);
        userStreetAddr.setText(null);
        userCityAddr.setText(null);
        userStateAddr.setText(null);
        userZipCode.setText(null);
        paymentMethod.setText(null);
        cardTypeUser.setText(null);
        cardNumber.setText(null);
        expireDate.setText(null);

        JOptionPane.showMessageDialog(null,
            "All fields have been cleared successfully.", "Form Cleared",
            JOptionPane.PLAIN_MESSAGE);
      }
    });

    acctInfoHolder.add(saveButton);
    acctInfoHolder.add(clearButton);

    viewEditCreateAccount.add(acctInfoHolder);
  }

  // Creates the tab that Admin users utilize to search the movie library
  private void makeAdminSearchMovies(HashMap<String, Movie> hm) {
    BoxLayout box = new BoxLayout(searchMovies, BoxLayout.Y_AXIS);
    searchMovies.setLayout(box);

    // Sets up the area where search criteria is entered by user
    JPanel searchBox = new JPanel();
    String[] criteria = { "Title", "Year", "Genre", "Director", "Cast Member", "Tag",
    "Plot Keyword(s)" };
    JComboBox searchCriteria = new JComboBox<String>(criteria);
    searchCriteria.setBackground(Color.WHITE);
    JTextField searchInput = new JTextField(50);
    JButton searchButton = new JButton("Search");

    searchBox.add(new JLabel("Select Search Criteria"));
    searchBox.add(searchCriteria);
    searchBox.add(searchInput);
    searchBox.add(searchButton);

    searchMovies.add(searchBox);

    // Sets up the area where the search results are displayed
    JPanel resultsBox = new JPanel();

    JScrollPane bmScrollPane = new JScrollPane(
        ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
        ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    bmScrollPane.setVisible(true);

    // Sets up the actionlistener for the search button based upon what search
    // criteria the user selected
    searchButton.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {

        resultsBox.removeAll();

        // Current selection in the drop=-down list
        int s = searchCriteria.getSelectedIndex();

        switch (s) {
          case 0:
            for (String movie : hm.keySet()) {
              String searchTerm = searchInput.getText().trim();
              if (hm.get(movie).title.equalsIgnoreCase(searchTerm)
                  || (hm.get(movie).title.toLowerCase()
                      .contains(searchTerm.toLowerCase()))) {
                pw.movieSearchResults.add(movie);
                System.out.println("Found a title match");
              }
            }
            break;
          case 1:
            for (String movie : hm.keySet()) {
              String searchTerm = searchInput.getText().trim();
              if (hm.get(movie).year.equalsIgnoreCase(searchTerm)
                  || (hm.get(movie).year.toLowerCase()
                      .contains(searchTerm.toLowerCase()))) {
                pw.movieSearchResults.add(movie);
                System.out.println("Found a year match");
              }
            }
            break;
          case 2:
            for (String movie : hm.keySet()) {
              String searchTerm = searchInput.getText().trim();
              if (hm.get(movie).genre.equalsIgnoreCase(searchTerm)
                  || (hm.get(movie).genre.toLowerCase()
                      .contains(searchTerm.toLowerCase()))) {
                pw.movieSearchResults.add(movie);
                System.out.println("Found a genre match");
              }
            }
            break;
          case 3:
              for (String movie : hm.keySet()) {
                String searchTerm = searchInput.getText().trim();
                if (hm.get(movie).director.equalsIgnoreCase(searchTerm)
                    || (hm.get(movie).director.toLowerCase()
                        .contains(searchTerm.toLowerCase()))) {
                  pw.movieSearchResults.add(movie);
                  System.out.println("Found a director match");
                }
              }
              break;
          case 4:
            for (String movie : hm.keySet()) {
              String searchTerm = searchInput.getText().trim();
              if (hm.get(movie).cast1.equalsIgnoreCase(searchTerm)
                  || hm.get(movie).cast1.toLowerCase()
                      .contains(searchTerm.toLowerCase())
                  || hm.get(movie).cast2.equalsIgnoreCase(searchTerm)
                  || hm.get(movie).cast2.toLowerCase()
                      .contains(searchTerm.toLowerCase())
                  || hm.get(movie).cast3.equalsIgnoreCase(searchTerm)
                  || hm.get(movie).cast3.contains(searchTerm)) {
                pw.movieSearchResults.add(movie);
                System.out.println("Found a cast match");
              }
            }
            break;
          case 5:
            for (String movie : hm.keySet()) {
              String searchTerm = searchInput.getText().trim();
              if (hm.get(movie).tag1.equalsIgnoreCase(searchTerm)
                  || hm.get(movie).tag1.toLowerCase()
                      .contains(searchTerm.toLowerCase())
                  || hm.get(movie).tag2.equalsIgnoreCase(searchTerm)
                  || hm.get(movie).tag2.toLowerCase()
                      .contains(searchTerm.toLowerCase())
                  || hm.get(movie).tag3.equalsIgnoreCase(searchTerm)
                  || hm.get(movie).tag3.toLowerCase()
                      .contains(searchTerm.toLowerCase())) {
                pw.movieSearchResults.add(movie);
                System.out.println("Found a tag match");
              }
            }
            break;
          case 6:
            for (String movie : hm.keySet()) {
              String searchTerm = searchInput.getText().trim();
              if (hm.get(movie).synopsis.toLowerCase()
                  .contains(searchTerm.toLowerCase())) {
                pw.movieSearchResults.add(movie);
                System.out.println("Found a plot match");
              }
            }
            break;
        }

        if (pw.movieSearchResults.isEmpty()) {
          System.out.println("Sorry, we could not find a match");
          JOptionPane.showMessageDialog(null,
              "Sorry, we could not find any titles that match the"
                  + "\ncriteria you provided.",
              "No Results Found", JOptionPane.ERROR_MESSAGE);
          return;
        } else {
          System.out.println("Search results:");
          for (String movie : pw.movieSearchResults) {
            System.out.println(movie);
          }

          resultsBox.setLayout(new GridLayout(pw.movieSearchResults.size(), 1));

          for (String movie : pw.movieSearchResults) {

            String key = movie;
            Movie value = hm.get(key);
            JPanel rowHolder = new JPanel();
            rowHolder.setLayout(new BoxLayout(rowHolder, BoxLayout.X_AXIS));

            JLabel picHolder;
            JPanel movieInfo = new JPanel();
            movieInfo.setLayout(new BoxLayout(movieInfo, BoxLayout.Y_AXIS));
            JPanel buttonHolder = new JPanel();

            MovieButton seeMovieDetails = new MovieButton(
                "Send Movie Info \nto View/Edit tab.", key);
            seeMovieDetails.setToolTipText(
                "Places movie in approproate tab to view and edit the record.");
            seeMovieDetails.addActionListener(new ActionListener() {
              @Override
              public void actionPerformed(ActionEvent e) {
                //if (pw.movieSearchResults.contains(seeMovieDetails.movTitle)) {
                //seeMovieDetails.movTitle;
                Movie temp = hm.get(seeMovieDetails.movTitle);
                
                title.setText(temp.title);
                year.setText(temp.year);
                genre.setText(temp.genre);
                director.setText(temp.director);
                cast1.setText(temp.cast1);
                cast2.setText(temp.cast2);
                cast3.setText(temp.cast3);
                tag1.setText(temp.tag1);
                tag2.setText(temp.tag2);
                tag3.setText(temp.tag3);
                picFileName.setText(temp.picFileName);
                synopsis.setText(temp.synopsis);
                
                  System.out.println("You clicked " + seeMovieDetails.movTitle);
                  JOptionPane.showMessageDialog(null,
                      "The details for " + seeMovieDetails.movTitle + "have been sent to the"
                          + "\nView/Edit Tab for any changes to be made.",
                      "Movie Info Forwarded", JOptionPane.ERROR_MESSAGE);

                //}
              }
            });

            picHolder = new JLabel(value.moviePoster);
            picHolder.setPreferredSize(new Dimension(150, 222));
            rowHolder.add(picHolder);

            String actors = String.format("%s, %s, %s", value.cast1,
                value.cast2, value.cast3);
            String tags = String.format("%s, %s, %s", value.tag1, value.tag2,
                value.tag3);
            movieInfo.add(new JLabel("Title: " + value.title));
            movieInfo.add(new JLabel("Released: " + value.year));
            movieInfo.add(new JLabel("Genre: " + value.genre));
            movieInfo.add(new JLabel("Director: " + value.director));
            movieInfo.add(new JLabel("Cast: " + actors));
            movieInfo.add(new JLabel("Tags: " + tags));
            movieInfo
                .add(new JLabel("<HTML>Summary: " + value.synopsis + "<HTML>"));

            rowHolder.add(movieInfo);
            rowHolder.add(buttonHolder.add(seeMovieDetails));

            resultsBox.add(rowHolder);
            resultsBox.setVisible(true);
            resultsBox.repaint();
            resultsBox.revalidate();
          }
          bmScrollPane.setViewportView(resultsBox);
          bmScrollPane.repaint();
          bmScrollPane.revalidate();
          searchMovies.add(bmScrollPane);
          searchMovies.repaint();
          searchMovies.revalidate();

          // Clears the HashSet with search results
          pw.movieSearchResults.clear();
        }

      }
    });

  }

  public void makeAdminMovieInfo(HashMap<String, Movie> hm) {
    JPanel movInfoHolder = new JPanel();

    movInfoHolder.setLayout(new GridLayout(13, 2, 0, 7)); // set frame layout

    // Customer demographic info
    JLabel titleLabel = new JLabel("Title:");
    movInfoHolder.add(titleLabel);
    title = new JTextField(40);
    movInfoHolder.add(title);
    JLabel yearLabel = new JLabel("Year:");
    movInfoHolder.add(yearLabel);
    year = new JTextField(40);
    movInfoHolder.add(year);

    JLabel genreLabel = new JLabel("Genre:");
    movInfoHolder.add(genreLabel);
    genre = new JTextField(40);
    movInfoHolder.add(genre);
    JLabel directorLabel = new JLabel("Director:");
    movInfoHolder.add(directorLabel);
    director = new JTextField(40);
    movInfoHolder.add(director);

    JLabel cast1Label = new JLabel("Cast Member:");
    movInfoHolder.add(cast1Label);
    cast1 = new JTextField(40);
    movInfoHolder.add(cast1);
    JLabel cast2Label = new JLabel("Cast Member:");
    movInfoHolder.add(cast2Label);
    cast2 = new JTextField(40);
    movInfoHolder.add(cast2);
    JLabel cast3Label = new JLabel("Cast Member:");
    movInfoHolder.add(cast3Label);
    cast3 = new JTextField(40);
    movInfoHolder.add(cast3);

    JLabel tag1Label = new JLabel("Tag:");
    movInfoHolder.add(tag1Label);
    tag1 = new JTextField(40);
    movInfoHolder.add(tag1);
    JLabel tag2Label = new JLabel("Tag:");
    movInfoHolder.add(tag2Label);
    tag2 = new JTextField(40);
    movInfoHolder.add(tag2);
    JLabel tag3Label = new JLabel("Tag:");
    movInfoHolder.add(tag3Label);
    tag3 = new JTextField(40);
    movInfoHolder.add(tag3);

    JLabel iconLabel = new JLabel("Icon File Name:");
    movInfoHolder.add(iconLabel);
    picFileName = new JTextField(40);
    movInfoHolder.add(picFileName);
    JLabel plotLabel = new JLabel("Plot Summary:");
    movInfoHolder.add(plotLabel);
    synopsis = new JTextField(40);
    movInfoHolder.add(synopsis);

    JButton saveButton = new JButton("Save Changes");
    saveButton.setToolTipText("Click here to save any changes you"
        + "\nmake to your account/profile.");
    saveButton.addActionListener(s -> {
      hm.put(title.getText(), new Movie(title.getText(),
          year.getText(), genre.getText(), director.getText(),
          cast1.getText(),cast2.getText(), cast3.getText(),
          tag1.getText(), tag2.getText(), tag3.getText(),
          picFileName.getText(), synopsis.getText()));
//      hm.entrySet().forEach(System.err::println); delete test code
        // Invokes method that updates HashMap and master text file
      pw.writeAllMoviesToFile();

        JOptionPane.showMessageDialog(null,
            "The movie record has been created/updated successfully.",
            "Movie Info Updated", JOptionPane.PLAIN_MESSAGE);

    });

    JButton clearButton = new JButton("Clear");
    saveButton.setToolTipText("Click here to clear this form so that you"
        + "\nmay work on or create another user account.");
    clearButton.addActionListener(arg0 -> {

        title.setText(null);
        year.setText(null);
        genre.setText(null);
        director.setText(null);
        cast1.setText(null);
        cast2.setText(null);
        cast3.setText(null);
        tag1.setText(null);
        tag2.setText(null);
        tag3.setText(null);
        picFileName.setText(null);
        synopsis.setText(null);
        JOptionPane.showMessageDialog(null,
            "All fields have been cleared successfully.", "Form Cleared",
            JOptionPane.PLAIN_MESSAGE);
    });

    movInfoHolder.add(saveButton);
    movInfoHolder.add(clearButton);

    viewEditCreateMovie.add(movInfoHolder);

  }

}
