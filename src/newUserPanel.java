/*
 * This class is for the panel that a user is first presented with when the
 * application is run. It prompts them to enter their username/password to gain
 * access to X-Flix and use the service or view/update their account info.
 */

// Imports the AWT and Swing packages needed to implement GUI elements

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class newUserPanel extends JPanel {

	private PrimaryWindow pw;
	private final JLabel userFirstNameLabel; // Label indicating user first name
	private final JLabel userLastNameLabel; // Label indicating user last name
	private final JLabel userEmailLabel; // Label indicating user email
	private final JLabel userPaymentLabel; // Label indicating user payment
											// method
	private final JLabel userCardLabel; // Label indicating user card number
	private final JLabel userStreetLabel; // Label indicating user street
	private final JLabel userCityLabel; // Label indicating user city
	private final JLabel userStateLabel; // Label indicating user state
	private final JLabel userZipLabel; // Label indicating user zip code
	
	private final JTextField userFirstNameField; // Text field to enter first
													// name
	private final JTextField userLastNameField; // Text field to enter last name
	private final JTextField userEmailField; // Text field to enter email
	private final JTextField userPaymentField; // Text field to enter payment
												// method
	private final JTextField userCardField; // Text field to entercard number
	private final JTextField userStreetField; // Text field to enter street
	private final JTextField userCityField; // Text field to enter city
	private final JTextField userStateField; // Text field to enter state
	private final JTextField userZipField; // Text field to enter zip code
	JButton userCreateAccount; // Button user clicks to create account
	JButton userHelp; // Button user clicks for help when creating account

	newUserPanel(PrimaryWindow pw) {
		super();
		this.pw = pw;

		// Sets layout for the panel, used to position items later
		setPreferredSize(new Dimension(400, 300));
		setBackground(pw.X_BACKGROUND_COLOR);
		setLayout(new GridLayout(10, 2, 5, 5));
		
		setBorder(BorderFactory.createTitledBorder(null, "SIGN UP", TitledBorder.CENTER,
				TitledBorder.TOP, new Font("arial", Font.BOLD, 12), Color.white));


		// First name
		userFirstNameLabel = new JLabel("First Name", SwingConstants.LEFT);
		userFirstNameLabel.setForeground(Color.white);
		userFirstNameLabel.setToolTipText("Enter your first name.");
		add(userFirstNameLabel);
		userFirstNameField = new JTextField(50);
		userFirstNameField.setToolTipText("Enter your first name for your account");
		add(userFirstNameField);

		// Last name
		userLastNameLabel = new JLabel("Last name", SwingConstants.LEFT);
		userLastNameLabel.setForeground(Color.white);
		userLastNameLabel.setToolTipText("Enter your last name.");
		add(userLastNameLabel);
		userLastNameField = new JTextField(50);
		userLastNameField.setToolTipText("Enter your last name");
		add(userLastNameField);
		
		// Email
		userEmailLabel = new JLabel("Email", SwingConstants.LEFT);
		userEmailLabel.setForeground(Color.white);
		userEmailLabel.setToolTipText("Enter your email.");
		add(userEmailLabel);
		userEmailField = new JTextField(50);
		userEmailField.setToolTipText("Enter your email");
		add(userEmailField);
		
		// Payment method
		userPaymentLabel = new JLabel("Payment method", SwingConstants.LEFT);
		userPaymentLabel.setForeground(Color.white);
		userPaymentLabel.setToolTipText("Set payment method.");
		add(userPaymentLabel);
		userPaymentField = new JTextField(50);
		userPaymentField.setToolTipText("Visa, MasterCard or Discover");
		add(userPaymentField);
		
		// Card number
		userCardLabel = new JLabel("Card number", SwingConstants.LEFT);
		userCardLabel.setForeground(Color.white);
		userCardLabel.setToolTipText("ENter card number.");
		add(userCardLabel);
		userCardField = new JTextField(50);
		userCardField.setToolTipText("Card number");
		add(userCardField);
		
		// Street
		userStreetLabel = new JLabel("Street", SwingConstants.LEFT);
		userStreetLabel.setForeground(Color.white);
		userStreetLabel.setToolTipText("Enter your street.");
		add(userStreetLabel);
		userStreetField = new JTextField(50);
		userStreetField.setToolTipText("Enter your street address");
		add(userStreetField);
		
		// City
		userCityLabel = new JLabel("City", SwingConstants.LEFT);
		userCityLabel.setForeground(Color.white);
		userCityLabel.setToolTipText("Enter your city.");
		add(userCityLabel);
		userCityField = new JTextField(50);
		userCityField.setToolTipText("Enter your city");
		add(userCityField);
		
		// State
		userStateLabel = new JLabel("State", SwingConstants.LEFT);
		userStateLabel.setForeground(Color.white);
		userStateLabel.setToolTipText("Enter your state.");
		add(userStateLabel);
		userStateField = new JTextField(50);
		userStateField.setToolTipText("Enter your state");
		add(userStateField);
		
		// Zip Code
		userZipLabel = new JLabel("Zip code", SwingConstants.LEFT);
		userZipLabel.setForeground(Color.white);
		userZipLabel.setToolTipText("Enter your zip code.");
		add(userZipLabel);
		userZipField = new JTextField(50);
		userZipField.setToolTipText("Enter your zip code");
		add(userZipField);

		// Create account button
		userHelp = new JButton("Help");
		userHelp.setToolTipText("If you need help creating an account, click here. ");
		// userCreateAccount.addActionListener(this);
		userHelp.setDefaultCapable(true);
		add(userHelp);
		
		// Create account button
		userCreateAccount = new JButton("Create Account");
		userCreateAccount.setToolTipText("Click here to create account "
				+ "and begin watching!");
		// userCreateAccount.addActionListener(this);
		add(userCreateAccount);

	}

	/**@Override
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
				JOptionPane.showMessageDialog(null, "Please enter a username.", "Missing Credential",
						JOptionPane.ERROR_MESSAGE);
				return;
			} else if (passWord.isEmpty()) {
				JOptionPane.showMessageDialog(null, "Please enter a password.", "Missing Credential",
						JOptionPane.ERROR_MESSAGE);
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
			if (pw.userAndPass.containsKey(userName) && pw.userAndPass.get(userName).equals(passWord)) {
				System.out.println("Username & password match.");

				pw.currentUserID = userName;
				System.out.println("after assigning userName to currentName");
				pw.currentUserName = pw.userAccounts.get(userName).firstName;
				// This section removes the login panel and alters the panel on
				// the top border after the user clicks the logon button
				this.setBorder(null); // Removes the JPanel's border
				this.setVisible(false);
				this.removeAll(); // Removes all elements of the panel container

				pw.topPan.removeAll();
				TopPanel.logo = new ImageIcon("IconPics/xflix_logo_small.png");

				// Sets up the contents within the program window based upon
				// whether the user is a customer or system admin
				if (userName.startsWith("admin")) {

					// Sets boolean flag to indicate admin user logged in
					pw.adminUser = true;

					// Uses different colored icon for admin users
					TopPanel.userIcon = new ImageIcon("IconPics/admin_icon_small.png");
				} else {

					// Sets boolean flag to indicate regular user logged in
					pw.regUser = true;

					// Uses different colored icon for regular users
					TopPanel.userIcon = new ImageIcon("IconPics/user_icon_small.png");
				}

				TopPanel.flow.setAlignment(FlowLayout.RIGHT);
				pw.topPan.add(new JLabel(TopPanel.logo));
				pw.topPan.add(new JLabel(TopPanel.userIcon));

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
				pw.topPan.add(TopPanel.logOff);
				pw.remove(pw.loginHolder);
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

			String resetEmail = JOptionPane.showInputDialog("Please enter your email address on file.");
			if (pw.userAndPass.containsKey(resetEmail)) {
				JOptionPane.showMessageDialog(null,
						"Thank you, an email has been sent to " + resetEmail
								+ "\nwith a temporary password. For security, please "
								+ "\nremember to change it IMMEDIATELY after \nsuccessfully " + "logging in.",
						"Password Reset Completed", JOptionPane.PLAIN_MESSAGE);
			} else if (!pw.userAndPass.containsKey(resetEmail)) {
				JOptionPane.showMessageDialog(null,
						"The email entered does not match any of the user accounts in our"
								+ "\nrecords. Please verify the correct address was typed and try"
								+ "\nagain. If you do not have an account, please register for one.",
						"No Such User Exists", JOptionPane.PLAIN_MESSAGE);
			}

			// Message intended for a blank field, not working UNDER
			// CONSTRUCTION
			else {
				JOptionPane.showMessageDialog(null, "It appears something's missing... Please enter a valid email.",
						"No Email Provided", JOptionPane.PLAIN_MESSAGE);
			}
		}
	}*/
}
