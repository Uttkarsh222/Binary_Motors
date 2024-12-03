package car_rental_book_and_manage.Objects;

public class Client {

  private String username, password, firstName, licenseNo, phoneNo;
  private int clientId;

  public Client() {}

  public Client(
          String username,
          String password,
          String firstName,
          int clientId,
          String phoneNo,
          String licenseNo) {
    this.username = username;
    this.password = password;
    this.firstName = firstName;
    this.clientId = clientId;
    this.phoneNo = phoneNo;
    this.licenseNo = licenseNo;
  }

  public Client(int clientId, String firstName, String phoneNo, String licenseNo) {
    this.clientId = clientId;
    this.firstName = firstName;
    this.phoneNo = phoneNo;
    this.licenseNo = licenseNo;
  }

  public Client(
          String username, String password, String firstName, String phoneNo, String licenseNo) {
    this.username = username;
    this.password = password;
    this.firstName = firstName;
    this.phoneNo = phoneNo;
    this.licenseNo = licenseNo;
  }

  public int getClientId() {
    return clientId;
  }

  public void setClientId(int clientId) {
    this.clientId = clientId;
  }

  public String getPhoneNo() {
    return phoneNo;
  }

  public void setPhoneNo(String phoneNo) {
    this.phoneNo = phoneNo;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getLicenseNo() {
    return licenseNo;
  }

  public void setLicenseNo(String licenseNo) {
    this.licenseNo = licenseNo;
  }
}
