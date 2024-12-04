package car_rental_book_and_manage.Utility;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

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

  public static String getProperty(String key) {
    String value = props.getProperty(key);
    if (value == null) {
      throw new RuntimeException("Property key not found: " + key);
    }
    return value;
  }
}
