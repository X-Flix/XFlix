import javax.swing.*;

public interface Xres {

  // Resource directories
  String ICONS = "resources/icons/";
  String IMAGES = "resources/images/";
  String DATA = "resources/TextDatabaseFiles/";

  // Data File Relative Paths
  String USER_PASS_FILE = DATA+"username_password.txt";
  String USER_ACCOUNT_FILE = DATA+"user_account_info.txt";
  String NEW_MOVIES_FILE = DATA+"new_movies.txt";

  // Image Icons
  ImageIcon XFLIX_LOGO_SMALL = new ImageIcon(ICONS+"xflix_logo_small.png");
  ImageIcon XFLIX_LOGO = new ImageIcon(ICONS+"xflix_logo.png");
  ImageIcon ADMIN_ICON = new ImageIcon(ICONS+"admin_icon_small.png");
  ImageIcon USER_ICON = new ImageIcon(ICONS+"user_icon_small.png");

  // Labels


  // Text Labels
  String LOGOFF_LABEL_TEXT = "Signout";
  String BORDER_TITLE_WELCOME_BACK = "Welcome Back! Please sign in.";
  String USER_LABEL = "Username (Email)";
  String USER_LABEL_TOOLTIP = "Enter the username or email address for your account.";
  String USER_FIELD_TOOLTIP = "Enter the username or email address for your account";
  String PASSWORD_LABEL = "Password";
  String PASSWORD_LABEL_TOOLTIP = "Enter your password.";
  String PASSWORD_FIELD_TOOLTIP = "Enter your password.";
  String USER_LOGIN_BUTTON = "LOGIN";
  String USER_LOGIN_BUTTON_TOOLTIP = "Click her to begin watching!";
  String WELCOME_MESSAGE = "Welcome back,\n";

  // Dialog text
  String MISSING_USER_NAME = "Please enter a username.";
  String MISSING_USER_NAME_TITLE = "Missing Credential";
  String MISSING_PASSWORD = "Please enter a password.";
  String MISSING_PASSWORD_TITLE = "Missing Credential";

  String FORGOT_PASSWORD_BUTTON = "Forgot Password?";
  String FORGOT_PASSWORD_BUTTON_TOOLTIP = "Click here to retrieve/reset forgotten passwords";
  String FORGOT_PASSWORD_DIALOG_PROMPT = "Please enter your email address on file.";
  String FORGOT_PASSWORD_DIALOG_PROMPT_SUCCESS_1 = "Thank you, an email has been sent to ";
  String FORGOT_PASSWORD_DIALOG_PROMPT_SUCCESS_2 =
      "\nwith a temporary password. For security, please " +
          "\nremember to change it IMMEDIATELY after \nsuccessfully logging in.";
  String FORGOT_PASSWORD_DIALOG_PROMPT_SUCCESS_TITLE = "Password Reset Completed";
  String FORGOT_PASSWORD_DIALOG_FAIL = "The email entered does not match any of the user accounts in our"
      + "\nrecords. Please verify the correct address was typed and try"
      + "\nagain. If you do not have an account, please register for one.";
  String FORGOT_PASSWORD_DIALOG_FAIL_TITLE = "No Such User Exists";

  // Global variables

}
