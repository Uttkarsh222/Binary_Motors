package car_rental_book_and_manage.Utility;

import org.mindrot.jbcrypt.BCrypt;


public class PIIHashManager {


  public static String hashPassword(String password) {
    return BCrypt.hashpw(password, BCrypt.gensalt());
  }

  public static boolean checkPassword(String password, String hashedPassword) {
    return BCrypt.checkpw(password, hashedPassword);
  }
}
