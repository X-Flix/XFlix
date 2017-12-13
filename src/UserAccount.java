/**
 * This class is the framework that contains the basic information stored for a
 * user's account. The minimum information needed to create an account is a
 * first and last name, as well as an email address, however there is an empty
 * constructor that will be used for the creation of any lists/sets/maps.
 *
 */
public class UserAccount {

  public String emailAddr = null;
  public String password;

  public String firstName = null;
  public String lastName = null;
  public String streetAddr = null;
  public String city = null;
  public String state = null;
  public String zipCode = null;

  public String paymentType = null;
  public String accountNumber = null;
  public String cardType = null;
  public String cardExpireDate = null;

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
    
    System.out.println("hello from empty USER constructor");
  }

  // Constructor for minimum amount of info required from a user or admin when
  // creating a new account using the application
  public UserAccount(String emailAddr, String password, String firstName,
      String lastName, String streetAddr, String city, String state,
      String zipCode/*, String paymentType, String accountNumber, String cardType,
      String cardExpireDat*/) {
    
    this.emailAddr = emailAddr;
    this.password = password;
    this.firstName = firstName;
    this.lastName = lastName;
    this.streetAddr = streetAddr;
    this.city = city;
    this.state = state;
    this.zipCode = zipCode;

//    this. paymentType = paymentType;
//    this.accountNumber = accountNumber;
//    this.cardType = cardType;
//    this.cardExpireDate = cardExpireDate;
    
    System.out.println("hello from full USER constructor");

  }
  
  
  @Override
  public String toString() {
      return emailAddr + "|" + password + "|" + firstName + "|" + lastName + "|" + streetAddr + "|" + 
          city + "|" + state + "|" + zipCode + "|" + paymentType + "|" + accountNumber + "|" + 
          cardType + "|" + cardExpireDate + "\n";
  }  
  
}
