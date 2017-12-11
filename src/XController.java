import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;

class XController {
  private PrimaryWindow  primary;

  XController() {
    // Four statements needed for almost every GUI program
    primary = new PrimaryWindow();
    primary.topPan.getLogOffBtn().addActionListener(e -> logOff());
    primary.getLoginPanel().getUserLoginBtn().addActionListener(e -> logOn());
    primary.getLoginPanel().getForgotPassBtn().addActionListener(e -> forgotPass());

    primary.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    primary.setSize(1000, 600);
    primary.setLocationRelativeTo(null); // Centers frame on screen
    primary.setVisible(true); // Makes frame (window) visible
    primary.revalidate();
  }

  private void logOn(){
    String userName = primary.getLoginPanel().getUserFieldText();
    String passWord = String.copyValueOf(primary.getLoginPanel().getPassFieldText());

    System.out.println("userName is: " + userName);
    System.out.println("passWord is: " + passWord);

    // Makes sure that a user has not left a field blank, and that
    // either field does not contain any whitespace characters
    if (userName.isEmpty()) {
      JOptionPane.showMessageDialog(null, Xres.MISSING_USER_NAME,
          Xres.MISSING_USER_NAME_TITLE, JOptionPane.ERROR_MESSAGE);
      return;
    } else if (passWord.isEmpty()) {
      JOptionPane.showMessageDialog(null, Xres.MISSING_PASSWORD,
          Xres.MISSING_PASSWORD_TITLE, JOptionPane.ERROR_MESSAGE);
      return;
    } else if (userName.matches(".*\\s+.*") || passWord.matches(".*\\s+.*")) {
      JOptionPane.showMessageDialog(null,
          "Invalid username/password; improper character present."
              + "\n Please make sure either field does not contain any spaces.",
          "Improper Credential Format", JOptionPane.ERROR_MESSAGE);
      return;
    }

    // Assuming properly formatted credentials have been entered, this
    // segment checks the username and password against the current user
    // list.
    if (primary.userAndPass.containsKey(userName)
        && primary.userAndPass.get(userName).equals(passWord)) {
      System.out.println("Username & password match.");

      primary.currentUserID = userName;
      primary.currentUserName = primary.userAccounts.get(userName).firstName;
      // This section removes the login panel and alters the panel on
      // the top border after the user clicks the logon button
      primary.getLoginPanel().setBorder(null); // Removes the JPanel's border
      primary.getLoginPanel().setVisible(false);
      primary.getLoginPanel().removeAll(); // Removes all elements of the panel container

      primary.topPan.removeAll();
      primary.topPan.getLogoLabel().setIcon(Xres.XFLIX_LOGO_SMALL);

      // Sets up the contents within the program window based upon
      // whether the user is a customer or system admin
      if (userName.startsWith("admin")) {

        // Sets boolean flag to indicate admin user logged in
        primary.adminUser = true;

        // Uses different colored icon for admin users
        primary.topPan.getUserLabel().setIcon(Xres.ADMIN_ICON);
      } else {

        // Sets boolean flag to indicate regular user logged in
        primary.regUser = true;

        // Uses different colored icon for regular users
        primary.topPan.getUserLabel().setIcon(Xres.USER_ICON);
      }

      primary.topPan.getToppanelFlow().setAlignment(FlowLayout.RIGHT);
      primary.topPan.add(primary.topPan.getLogoLabel());
      primary.topPan.add(primary.topPan.getUserLabel());

      String welcomeMessage = Xres.WELCOME_MESSAGE + primary.currentUserName + "!";
      JTextPane welcomeBox = new JTextPane();
      welcomeBox.setText(welcomeMessage);
      welcomeBox.setEditable(false);
      welcomeBox.setFont(primary.topPan.getUserLabel().getFont());
      welcomeBox.setBackground(primary.topPan.getUserLabel().getBackground());
      StyledDocument doc = welcomeBox.getStyledDocument();
      SimpleAttributeSet center = new SimpleAttributeSet();
      StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
      doc.setParagraphAttributes(0, doc.getLength(), center, false);

      primary.topPan.add(welcomeBox);
      primary.topPan.add(primary.topPan.getLogOffBtn());
      primary.main = new MainBody(primary);
      primary.add(primary.main, BorderLayout.CENTER);
      primary.revalidate();

    } else {
      JOptionPane.showMessageDialog(null,
          "The username or password does not match our records."
              + "\nPlease check your credentials & try again.",
          "Invalid Credentials", JOptionPane.ERROR_MESSAGE);
    }
  }

  private void forgotPass(){

    int dialogClick = 0;
    String resetEmail = JOptionPane
        .showInputDialog(Xres.FORGOT_PASSWORD_DIALOG_PROMPT);
    if (primary.userAndPass.containsKey(resetEmail)) {
      JOptionPane.showMessageDialog(null,
          Xres.FORGOT_PASSWORD_DIALOG_PROMPT_SUCCESS_1 + resetEmail
              + Xres.FORGOT_PASSWORD_DIALOG_PROMPT_SUCCESS_2,
          Xres.FORGOT_PASSWORD_DIALOG_PROMPT_SUCCESS_TITLE, JOptionPane.PLAIN_MESSAGE);
    } else if (!primary.userAndPass.containsKey(resetEmail)) {
      JOptionPane.showMessageDialog(null,
          Xres.FORGOT_PASSWORD_DIALOG_FAIL,
          Xres.FORGOT_PASSWORD_DIALOG_FAIL_TITLE, JOptionPane.PLAIN_MESSAGE);
    }

    // Message intended for a blank field, nto working UNDER CONSTRUCTION
    else {
      JOptionPane.showMessageDialog(null,
          "It appears something's missing... Please enter a valid email.",
          "No Email Provided", JOptionPane.PLAIN_MESSAGE);
    }
  }

  private void logOff(){
    primary.topPan.removeAll();
    primary.topPan.getToppanelFlow().setAlignment(FlowLayout.CENTER);
    primary.refreshLogin();
  }
}
