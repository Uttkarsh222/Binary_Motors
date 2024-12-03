package car_rental_book_and_manage.Controllers;

import car_rental_book_and_manage.App;
import car_rental_book_and_manage.Objects.Client;
import car_rental_book_and_manage.Utility.AlertManager;
import car_rental_book_and_manage.Utility.SceneManager;
import car_rental_book_and_manage.Utility.SceneManager.Scenes;
import car_rental_book_and_manage.Utility.ValidationManager;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class LoginController extends Controller {

  @FXML private Button loginBtn;
  @FXML private PasswordField password;
  @FXML private Button signUpBtn;
  @FXML private Button adminBtn;
  @FXML private TextField username;
  @FXML private Label authenticationLbl;

  public LoginController() {
    super();
  }

  public void initialize() {
    SceneManager.setController(Scenes.LOGIN, this);

    username.addEventFilter(KeyEvent.KEY_PRESSED, this::onEnter);
    password.addEventFilter(KeyEvent.KEY_PRESSED, this::onEnter);
  }

  @FXML
  public void onSignUp(MouseEvent event) {
    App.setUi(Scenes.SIGNUP);
  }


  @FXML
  public void onAdmin(MouseEvent event) {
    App.setUi(Scenes.ADMIN);
  }


  @FXML
  public void onLogIn(MouseEvent event) {
    performLogin();
  }

  @FXML
  private void onEnter(KeyEvent event) {
    if (event.getCode() == KeyCode.ENTER) {
      performLogin();
    }
  }
  private void performLogin() {
    String enteredUsername = username.getText();
    String enteredPassword = password.getText();

    if (isLoginInputValid(enteredUsername, enteredPassword)) {
      if (authenticateUser(enteredUsername, enteredPassword)) {
        handleSuccessfulLogin(enteredUsername, enteredPassword);
      } else {
        AlertManager.showAlert(
            AlertType.WARNING, "Login Unsuccessful", "Wrong Username or Password");
      }
    }
  }


  private boolean isLoginInputValid(String username, String password) {
    return ValidationManager.isLoginInputValid(username, password);
  }


  private boolean authenticateUser(String username, String password) {
    return clientdb.isLoginCredentialsValid(username, password);
  }


  private void handleSuccessfulLogin(String username, String password) {
    Client loggedInClient = clientdb.getClient(username);
    reservationManager.setLoggedInClient(loggedInClient);
    dataModel.setLoggedClientName(loggedInClient.getFirstName());
    clearLoginFields();
    AlertManager.showAlert(AlertType.CONFIRMATION, "Login Successful", "You Are Now Logging In");
    App.setUi(Scenes.FINDVEHICLES);
  }

  private void clearLoginFields() {
    username.clear();
    password.clear();
  }
}
