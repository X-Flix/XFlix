import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
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

      // this.setLayout(new BorderLayout());
      makeWhatsNewTab(pw.newReleases);

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

  public void makeWhatsNewTab(HashMap<String, Movie> hm) {

    JPanel whatsNewHolder = new JPanel();

    GridLayout grid = new GridLayout(hm.size(), 1);
    FlowLayout flow = new FlowLayout();
    BoxLayout box = new BoxLayout(whatsNew, BoxLayout.Y_AXIS);

    whatsNew.setLayout(box);

    JScrollPane wnScrollPane = new JScrollPane(
        ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
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
        @Override public void actionPerformed(ActionEvent e) {
          System.out.println("You clicked button: " + addToCart.movTitle);
          pw.movieCart.add(addToCart.movTitle);
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
}
