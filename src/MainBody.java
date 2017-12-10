import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
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

      //this.setLayout(new BorderLayout());
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
    // Timer timer = new Timer();
    // TODO Auto-generated method stub

  }

  public void makeWhatsNewTab(HashMap<String, Movie> hm) {
    // GridLayout grid = new GridLayout(1, 2);
    // FlowLayout flow = new FlowLayout();
    // BoxLayout box = new BoxLayout(whatsNew, BoxLayout.Y_AXIS);
    // whatsNew.setMinimumSize(new Dimension(150, 222));

    JScrollPane wn = new JScrollPane(
        ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
        ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

    // whatsNew.setLayout();
    JPanel rowHolder = new JPanel();
    rowHolder.setLayout(new BoxLayout(rowHolder, BoxLayout.X_AXIS));
    
    JLabel picHolder;
    JPanel movieInfo = new JPanel();
    movieInfo.setLayout(new BoxLayout(movieInfo, BoxLayout.Y_AXIS));
    JPanel buttonHolder = new JPanel();

    JButton addToCart = new JButton("Add to Cart");
    addToCart.setToolTipText("Rent this title today!\nAdd it to your cart.");

    String key = "BayWatch";
    Movie value = hm.get(key);
    picHolder = new JLabel(value.moviePoster);
    picHolder.setPreferredSize(new Dimension(150, 222));
    whatsNew.add(picHolder);

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

    whatsNew.add(movieInfo);
    whatsNew.add(buttonHolder.add(addToCart));

    wn.setVisible(true);
    whatsNew.add(wn);
    whatsNew.setVisible(true);
    whatsNew.repaint();
    whatsNew.revalidate();

  }

}
