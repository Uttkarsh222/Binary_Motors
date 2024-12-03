package car_rental_book_and_manage.Utility;

import java.time.Year;

import car_rental_book_and_manage.Objects.ClientDB;
import car_rental_book_and_manage.Objects.DataModel;
import car_rental_book_and_manage.Objects.Vehicle;
import car_rental_book_and_manage.Objects.VehicleDB;
import javafx.scene.control.Alert.AlertType;

/** ValidationManager class for validating user and vehicle input. */
public class ValidationManager {

  private static final DataModel dataModel = DataModel.getInstance();


  public static boolean isSignUpUserInputValid(
      String name, String password, String username, String phoneNo, String LicenceNo) {
    return name.isEmpty()
        || password.isEmpty()
        || username.isEmpty()
        || phoneNo.isEmpty()
        || LicenceNo.isEmpty();
  }


  public static boolean isNameValid(String name) {
    return name.matches("[a-zA-Z]+");
  }


  public static boolean isPhoneNoValid(String phoneNo) {
    return phoneNo.matches("\\d{10}");
  }


  public static boolean isLicenseNoValid(String license) {
    String licNoPattern = "(?i)^[a-z]{4}\\d{4}$";
    return license.matches(licNoPattern);
  }


  public static boolean isVehicleInputValid(
      String brand,
      String model,
      String makeYear,
      String Colour,
      String dailyRate,
      String regNo,
      String fuelType,
      String economy) {
    return brand.isEmpty()
        || model.isEmpty()
        || makeYear.isEmpty()
        || Colour.isEmpty()
        || dailyRate.isEmpty()
        || regNo.isEmpty()
        || fuelType == null
        || fuelType.isEmpty()
        || economy.isEmpty();
  }


  public static boolean isLoginInputValid(String username, String password) {
    if (username.isEmpty() || password.isEmpty()) {
      AlertManager.showAlert(
          AlertType.WARNING, "Login Unsuccessful", "Please Enter Your Information");
      return false;
    }
    return true;
  }


  public static boolean isYearValid(String year) {
    if (!year.matches("\\d{4}")) {
      return false;
    }
    int currentYear = Year.now().getValue();
    final int MIN_YEAR = 1901;
    int yearValue = Integer.parseInt(year);
    return yearValue >= MIN_YEAR && yearValue <= currentYear;
  }


  public static boolean isDailyRateValid(String rate) {
    String decimalPattern = "^\\d*\\.?\\d+$";
    return rate.matches(decimalPattern);
  }


  public static boolean isRegNoValid(String regNo) {
    String regNoPattern = "^[A-Z]{3}\\d{3}$";
    return regNo.matches(regNoPattern);
  }


  public static boolean isColourValid(String colour) {
    return colour.matches("[a-zA-Z]+");
  }


  public static boolean isFuelEconomyValid(String fuelEconomy) {
    return fuelEconomy.matches("\\d{2,3}");
  }


  public static boolean isCvvValid(String cvv) {
    return cvv.matches("\\d{3}");
  }


public static boolean isCardNumberValid(String cardNumber) {
    return cardNumber.matches("^\\d{3} \\d{3} \\d{3} \\d{3}$");
}


  public static boolean isExpiryDateValid(String expiryDate) {
    return expiryDate.matches("\\d{2}/\\d{2}");
  }


  public static boolean validateVehicleFields(
      String brand, String model, String makeYear, String colour, String dailyRate,
      String regNo, String fuelType, String economy) {
    if (isVehicleInputValid(brand, model, makeYear, colour, dailyRate, regNo, fuelType, economy)) {
      AlertManager.showAlert(AlertType.WARNING, "Required Fields", "Please Enter All Missing Fields");
      return false;
    }
    if (!isDailyRateValid(dailyRate)) {
      AlertManager.showAlert(AlertType.WARNING, "Invalid Daily rate", "Daily rate must be in 0.00 format");
      return false;
    }
    if (!isYearValid(makeYear)) {
      AlertManager.showAlert(AlertType.WARNING, "Invalid Make Year", "Make Year must be in XXXX format and in between 1900 and current year");
      return false;
    }
    if (!isRegNoValid(regNo)) {
      AlertManager.showAlert(AlertType.WARNING, "Invalid Registration number", "Registration number plate must be in the format AAA000");
      return false;
    }
    if (!isColourValid(colour)) {
      AlertManager.showAlert(AlertType.WARNING, "Colour is invalid", "Colour entered is not a valid Colour");
      return false;
    }
    if (!isFuelEconomyValid(economy)) {
      AlertManager.showAlert(AlertType.WARNING, "Fuel Economy is invalid", "Fuel economy must be 2 or 3 digits");
      return false;
    }
    return true;
  }


  public static boolean checkRegistrationNoExists(
      String regNo, String action, VehicleDB vehicledb, String id) {
    // Additional validation for save action
    if (action.equals("save")) {
      if (vehicledb.doesRegistrationNoExist(regNo)) {
        AlertManager.showAlert(
            AlertType.WARNING,
            "Registration number Already exists",
            "You must register another Registration number");
        return true;
      }
    }

    // Additional validation for update action
    if (action.equals("update")) {
      Vehicle selectedVehicle = dataModel.getVehicle(Integer.parseInt(id));
      // Only check for duplicate registration number if it has been changed
      if (!selectedVehicle.getLicensePlate().equals(regNo)
          && vehicledb.doesRegistrationNoExist(regNo)) {
        AlertManager.showAlert(
            AlertType.WARNING,
            "Registration number Already exists",
            "You must register another Registration number");
        return true;
      }
    }
    return false;
  }


  public static boolean validateSignUpUserInput(
      String name, String password, String username, String phoneNo, String license, ClientDB clientdb) {
    if (isSignUpUserInputValid(name, password, username, phoneNo, license)) {
      AlertManager.showAlert(AlertType.WARNING, "Required Fields", "Please Enter All Missing Fields");
      return false;
    }
    if (!isNameValid(name)) {
      AlertManager.showAlert(AlertType.WARNING, "Invalid Name Format", "First Name Format Is Invalid");
      return false;
    }
    if (!isPhoneNoValid(phoneNo)) {
      AlertManager.showAlert(AlertType.WARNING, "Invalid Phone Number", "Phone Number must be a 10 digit number\nXXXXXXXXXX");
      return false;
    }
    if (!isLicenseNoValid(license)) {
      AlertManager.showAlert(AlertType.WARNING, "Invalid License Number", "Format must be xxxx0000");
      return false;
    }
    if (clientdb.doesUserNameExist(username)) {
      AlertManager.showAlert(AlertType.WARNING, "Username Already Exists", "Please Come Up With Another Username");
      return false;
    }
    return true;
  }


  public static boolean validateCardDetails(String cardName, String cardNumber, String cvv, String expiryDate) {
    if (cardName.isEmpty() || cardNumber.isEmpty() || cvv.isEmpty() || expiryDate.isEmpty()) {
      AlertManager.showAlert(AlertType.WARNING, "Required Fields", "Please Enter All Missing Fields");
      return false;
    }
    if (!isCardNumberValid(cardNumber)) {
      AlertManager.showAlert(AlertType.WARNING, "Invalid Card Number", "Card number must be 12 digits. Check the spacing");
      return false;
    }
    if (!isCvvValid(cvv)) {
      AlertManager.showAlert(AlertType.WARNING, "Invalid CVV", "CVV must be a 3 digit number");
      return false;
    }
    if (!isExpiryDateValid(expiryDate)) {
      AlertManager.showAlert(AlertType.WARNING, "Invalid Expiry Date", "Expiry date must be in the format MM/YY");
      return false;
    }
    return true;
  }

}