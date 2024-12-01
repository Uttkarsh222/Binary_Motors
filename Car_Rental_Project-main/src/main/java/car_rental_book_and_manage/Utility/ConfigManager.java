package car_rental_book_and_manage.Utility;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/** utility class for managing configuration properties. 
 * 
 * Just a method to handle JDBC credentials until connection to server side.
 * 
*/
public class ConfigManager {
  private static final Properties props = new Properties();

  // static block to load properties from the configuration file
  static {
    try (FileInputStream input = new FileInputStream("src/main/resources/database.properties")) {
      props.load(input);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Retrieves the property value associated with the given key.
   *
   * @param key the key whose associated value is to be retrieved
   * @return the value associated with the key
   * @throws RuntimeException if the property key is not found
   */
  public static String getProperty(String key) {
    String value = props.getProperty(key);
    if (value == null) {
      throw new RuntimeException("Property key not found: " + key);
    }
    return value;
  }
}
