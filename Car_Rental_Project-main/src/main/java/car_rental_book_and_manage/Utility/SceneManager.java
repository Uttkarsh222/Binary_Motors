package car_rental_book_and_manage.Utility;

import car_rental_book_and_manage.Controllers.Controller;
import java.util.HashMap;
import javafx.scene.Parent;

/** SceneManager class for managing different scenes and their controllers in the application. */
public class SceneManager {

  /** Enum representing all the different scenes in the system. */
  public enum Scenes {
    LOGIN,
    ADMIN,
    SIGNUP,
    MANAGE,
    BOOKINGS,
    CUSTOMERS,
    CONFIRMATION,
    FINDVEHICLES,
    INSURANCE,
    MYBOOKING,
    PAYMENT
  }

  private static Controller activeController;
  private static HashMap<Scenes, Controller> controllerMap = new HashMap<>();
  private static HashMap<Scenes, Parent> sceneMap = new HashMap<>();

  public static void addUi(Scenes appUi, Parent uiRoot) {
    sceneMap.put(appUi, uiRoot);
  }

  public static Parent getUiRoot(Scenes appUi) {
    activeController = controllerMap.get(appUi);
    return sceneMap.get(appUi);
  }


  public static void setActiveController(Controller controller) {
    activeController = controller;
  }


  public static Controller getActiveController() {
    return activeController;
  }


  public static void addController(Scenes appUi, Controller controller) {
    controllerMap.put(appUi, controller);
  }


  public static Controller getController(Scenes appUi) {
    return controllerMap.get(appUi);
  }


  public static void setController(Scenes appUi, Controller controller) {
    controllerMap.replace(appUi, controller);
  }
}
