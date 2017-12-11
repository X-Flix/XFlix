/*
 * This class is for the panel that a user is first presented with when the
 * application is run. It prompts them to enter their username/password to gain
 * access to X-Flix and use the service or view/update their account info.
 */

// Imports the AWT and Swing packages needed to implement GUI elements
import java.awt.*;
import javax.swing.*;

class LoginPanel extends JPanel{

  private final JLabel userLabel; // Label indicating username
  private final JTextField userField; // Text field to enter username
  private final JPasswordField passField; // Text field to enter password
  private JButton userLogin; // Button user clicks after entering
  // their credentials to login
  private JButton forgotPass; // Button user clicks to reset their
  // password if forgotten

  LoginPanel() {
    super();

    // Sets layout for the panel, used to position items later
    setPreferredSize(new Dimension(400, 100));

    setLayout(new GridLayout(3, 2, 5, 5));
    setBorder(BorderFactory.createTitledBorder(
        BorderFactory.createEtchedBorder(), Xres.BORDER_TITLE_WELCOME_BACK));
    
    userLabel = new JLabel(Xres.USER_LABEL, SwingConstants.LEFT);
    userLabel.setToolTipText(Xres.USER_LABEL_TOOLTIP);
    add(userLabel);

    userField = new JTextField(50);
    userField
        .setToolTipText(Xres.USER_FIELD_TOOLTIP);
    add(userField);

    JLabel passLabel = new JLabel(Xres.PASSWORD_LABEL, SwingConstants.LEFT);
    passLabel.setToolTipText(Xres.PASSWORD_LABEL_TOOLTIP);
    add(passLabel);
    passField = new JPasswordField(20);
    passField.setToolTipText(Xres.PASSWORD_FIELD_TOOLTIP);
    add(passField);

    userLogin = new JButton(Xres.USER_LOGIN_BUTTON);
    userLogin.setToolTipText(Xres.USER_LOGIN_BUTTON_TOOLTIP);
    add(userLogin);

    forgotPass = new JButton(Xres.FORGOT_PASSWORD_BUTTON);
    forgotPass.setToolTipText(Xres.FORGOT_PASSWORD_BUTTON_TOOLTIP);
    add(forgotPass);
  }

  JButton getUserLoginBtn() {
    return userLogin;
  }

  JButton getForgotPassBtn() { return forgotPass; }

  String getUserFieldText(){ return userField.getText(); }

  char[] getPassFieldText(){ return passField.getPassword(); }
}
