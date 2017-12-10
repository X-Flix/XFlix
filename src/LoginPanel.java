/*
 * This class is for the panel that a user is first presented with when the
 * application is run. It prompts them to enter their username/password to gain
 * access to X-Flix and use the service or view/update their account info.
 */

// Imports the AWT and Swing packages needed to implement GUI elements
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.io.*;

import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

public class LoginPanel extends JPanel implements ActionListener {

  protected PrimaryWindow pw;
  protected final JLabel userLabel; // Label indicating username
  protected final JLabel passLabel; // Label indicating password
  protected final JTextField userField; // Text field to enter username
  protected final JPasswordField passField; // Text field to enter password
  protected final GridLayout loginLayout; // Layout for this panel
  protected JButton userLogin; // Button user clicks after entering
  // their credentials to login
  protected JButton forgotPass; // Button user clicks to reset their
  // password if forgotten

  protected String userName;
  protected String passWord;

  public LoginPanel(PrimaryWindow pw) {
    super();

    this.pw = pw;

    // Sets layout for the panel, used to position items later
    setPreferredSize(new Dimension(400, 100));

    setLayout(loginLayout = new GridLayout(3, 2, 5, 5));
    setBorder(BorderFactory.createTitledBorder(
        BorderFactory.createEtchedBorder(), "Welcome Back! Please sign in."));
    
    userLabel = new JLabel("Username (Email)", SwingConstants.LEFT);
    userLabel.setToolTipText(
        "Enter the username or email address for your account.");
    
    add(userLabel);
    userField = new JTextField(50);
    userField
        .setToolTipText("Enter the username or email address for your account");
    add(userField);

    passLabel = new JLabel("Password", SwingConstants.LEFT);
    passLabel.setToolTipText("Enter your password.");
    add(passLabel);
    passField = new JPasswordField(20);
    passField.setToolTipText("Enter your password.");
    add(passField);

    userLogin = new JButton("LOGIN");
    userLogin.setToolTipText("Click her to begin watching!");
    userLogin.addActionListener(this);
    add(userLogin);
    forgotPass = new JButton("Forgot Password?");
    forgotPass
        .setToolTipText("Click here to retrieve/reset forgotten passwords");
    forgotPass.addActionListener(this);
    add(forgotPass);
  }

 
  @Override
  public void actionPerformed(ActionEvent ae) {

    // Addresses what happens if the user clicks the login button
    if (ae.getSource() == userLogin) {

      userName = userField.getText();
      passWord = String.copyValueOf(passField.getPassword());

      System.out.println("userName is: " + userName);
      System.out.println("passWord is: " + passWord);

      // Makes sure that a user has not left a field blank, and that
      // either field does not contain any whitespace characters
      if (userName.isEmpty()) {
        JOptionPane.showMessageDialog(null, "Please enter a username.",
            "Missing Credential", JOptionPane.ERROR_MESSAGE);
        return;
      } else if (passWord.isEmpty()) {
        JOptionPane.showMessageDialog(null, "Please enter a password.",
            "Missing Credential", JOptionPane.ERROR_MESSAGE);
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
      if (pw.userAndPass.containsKey(userName)
          && pw.userAndPass.get(userName).equals(passWord)) {
        System.out.println("Username & password match.");

        pw.currentUserID = userName;
        pw.currentUserName = pw.userAccounts.get(userName).firstName;
        // This section removes the login panel and alters the panel on
        // the top border after the user clicks the logon button
        this.setBorder(null); // Removes the JPanel's border
        this.setVisible(false);
        this.removeAll(); // Removes all elements of the panel container
        
        pw.topPan.removeAll();
        pw.topPan.logo = new ImageIcon(
            getClass().getResource("xflix_logo_small.png"));

        // Sets up the contents within the program window based upon
        // whether the user is a customer or system admin
        if (userName.startsWith("admin")) {

          // Sets boolean flag to indicate admin user logged in
          pw.adminUser = true;

          // Uses different colored icon for admin users
          pw.topPan.userIcon = new ImageIcon(
              getClass().getResource("admin_icon_small.png"));
        } else {

          // Sets boolean flag to indicate regular user logged in
          pw.regUser = true;

          // Uses different colored icon for regular users
          pw.topPan.userIcon = new ImageIcon(
              getClass().getResource("user_icon_small.png"));
        }

        pw.topPan.flow.setAlignment(FlowLayout.RIGHT);
        pw.topPan.add(new JLabel(pw.topPan.logo));        
        pw.topPan.add(new JLabel(pw.topPan.userIcon));
        
        String welcomeMessage = "Welcome back,\n" + pw.currentUserName + "!";
        JTextPane welcomeBox = new JTextPane();
        welcomeBox.setText(welcomeMessage);
        welcomeBox.setEditable(false);
        welcomeBox.setFont(userLabel.getFont());
        welcomeBox.setBackground(userLabel.getBackground());
        StyledDocument doc = welcomeBox.getStyledDocument();
        SimpleAttributeSet center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
        doc.setParagraphAttributes(0, doc.getLength(), center, false);
        
        pw.topPan.add(welcomeBox);
        pw.topPan.add(pw.topPan.logOff);
        pw.main = new MainBody(pw);
        pw.add(pw.main, BorderLayout.CENTER);
        pw.revalidate();

      } else {
        JOptionPane.showMessageDialog(null,
            "The username or password does not match our records."
                + "\nPlease check your credentials & try again.",
            "Invalid Credentials", JOptionPane.ERROR_MESSAGE);
        return;
      }
    }

    // Addresses what happens if the user clicks forget password button
    if (ae.getSource() == forgotPass) {

      int dialogClick = 0;
      String resetEmail = null;

      resetEmail = JOptionPane
          .showInputDialog("Please enter your email address on file.");
      if (pw.userAndPass.containsKey(resetEmail)) {
        JOptionPane.showMessageDialog(null,
            "Thank you, an email has been sent to " + resetEmail
                + "\nwith a temporary password. For security, please "
                + "\nremember to change it IMMEDIATELY after \nsuccessfully "
                + "logging in.",
            "Password Reset Completed", JOptionPane.PLAIN_MESSAGE);
      } else if (!pw.userAndPass.containsKey(resetEmail)) {
        JOptionPane.showMessageDialog(null,
            "The email entered does not match any of the user accounts in our"
                + "\nrecords. Please verify the correct address was typed and try"
                + "\nagain. If you do not have an account, please register for one.",
            "No Such User Exists", JOptionPane.PLAIN_MESSAGE);
      }

      // Message intended for a blank field, nto working UNDER CONSTRUCTION
      else {
        JOptionPane.showMessageDialog(null,
            "It appears something's missing... Please enter a valid email.",
            "No Email Provided", JOptionPane.PLAIN_MESSAGE);
      }
    }
  }
}