package car_rental_book_and_manage.Utility;

import java.io.File;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;
import javafx.stage.Window;

/** Utility class for selecting and validating image files. */
public class ImageSelect {


  public static File selectImageFile(Window ownerWindow) {
    FileChooser fileChooser = new FileChooser();
    fileChooser
        .getExtensionFilters()
        .add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg"));
    return fileChooser.showOpenDialog(ownerWindow);
  }


  public static boolean isValidImage(File selectedFile) {
    if (selectedFile != null) {
      if (!isImageInCorrectDirectory(selectedFile.getPath())) {
        AlertManager.showAlert(
            AlertType.ERROR,
            "Invalid Image",
            "Please select an image file from the 'images and attribution' directory from the"
                + " project.");
        return false;
      }
    }
    return true;
  }


  private static boolean isImageInCorrectDirectory(String imagePath) {
	  return true;
//    return imagePath.contains("/images and attribution/");
  }
}
