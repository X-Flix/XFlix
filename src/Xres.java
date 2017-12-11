import javax.swing.*;
import java.awt.*;
import java.util.stream.Stream;

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
  String FORGOT_PASSWORD_BUTTON = "Forgot Password?";
  String FORGOT_PASSWORD_BUTTON_TOOLTIP = "Click here to retrieve/reset forgotten passwords";

  // Global variables

}
