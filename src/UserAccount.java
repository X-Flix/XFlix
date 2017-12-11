/**
 * This class is the framework that contains the basic information stored for a
 * user's account. The minimum information needed to create an account is a
 * first and last name, as well as an email address, however there is an empty
 * constructor that will be used for the creation of any lists/sets/maps.
 *
 */
public class UserAccount {

  private String emailAddr;
  String firstName;
  private String lastName;
  private String streetAddr;
  private String city;
  private String state;
  private String zipCode;

  private String paymentType;
  private String accountNumber;
  private String cardType;
  private String cardExpireDate;

  // Empty constructor
  public UserAccount() {
    emailAddr = null;
    firstName = null;
    lastName = null;
    streetAddr = null;
    city = null;
    state = null;
    zipCode = null;

    paymentType = null;
    accountNumber = null;
    cardType = null;
    cardExpireDate = null;
  }

  // Constructor for minimum amount of info required from a user or admin when
  // creating a new account using the application
  UserAccount(String emailAddr, String firstName, String lastName) {
    this.emailAddr = emailAddr;
    this.firstName = firstName;
    this.lastName = lastName;

  }
}
