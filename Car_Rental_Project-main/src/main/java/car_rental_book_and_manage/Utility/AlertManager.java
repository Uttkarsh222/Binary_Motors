package car_rental_book_and_manage.Utility;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/** Utility class for displaying alerts in the application. */
public class AlertManager {

  public static void showAlert(AlertType alertType, String title, String message) {
    Alert alert = new Alert(alertType);

    alert.setTitle(title);
    alert.setContentText(message);
    alert.showAndWait();
  }
}
