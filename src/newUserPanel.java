/*
 * This class is for the panel that a user is first presented with when the
 * application is run. It prompts them to enter their username/password to gain
 * access to X-Flix and use the service or view/update their account info.
 */

// Imports the AWT and Swing packages needed to implement GUI elements

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class newUserPanel extends JPanel implements ActionListener {

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
    userCreateAccount.addActionListener(e-> {
          createAccount();
        }
    );
		add(userCreateAccount);

	}

	private void createAccount(){
//	  if (!useruserEmailField)
  }
	@Override
	public void actionPerformed(ActionEvent ae) {
    // required stub
  }
}
