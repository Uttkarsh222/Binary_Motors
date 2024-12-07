package car_rental_book_and_manage.Controllers;

import car_rental_book_and_manage.Objects.Client;
import car_rental_book_and_manage.Utility.AlertManager;
import car_rental_book_and_manage.Utility.SceneManager;
import car_rental_book_and_manage.Utility.SceneManager.Scenes;
import car_rental_book_and_manage.Utility.ValidationManager;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.util.function.Predicate;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

public class CustomersController extends Controller {

  @FXML private AnchorPane customerClientUpdatePane;
  @FXML private AnchorPane customerpane1;
  @FXML private AnchorPane customerpane2;
  @FXML private TableColumn<Client, Integer> customercolId;
  @FXML private TableColumn<Client, String> customercolLicense;
  @FXML private TableColumn<Client, String> customercolName;
  @FXML private TableColumn<Client, Integer> customercolPhone;
  @FXML private TableColumn<Client, Void> customercolUpdate;
  @FXML private TableView<Client> customertableClient;
  @FXML private ChoiceBox<String> customersearchChoiceBox;
  @FXML private TextField customersearchTxt;
  @FXML private TextField customertxtID;
  @FXML private TextField customertxtLicense;
  @FXML private TextField customertxtName;
  @FXML private TextField customertxtPhone;
  @FXML private Label customerclientNoLbl;

  public void initialize() {
    SceneManager.setController(Scenes.CUSTOMERS, this);
    setUpTableColumns();
    setUpUpdateButtonCol();
    clientdb.retrieveAllClients();
    setUpSearchChoiceBox();
    addSearchListener();
    customerpane2.getStylesheets().add(getClass().getResource("/css/customcol.css").toExternalForm());
  }

  private void setUpSearchChoiceBox() {
    customersearchChoiceBox.getItems().addAll("ID", "License Number");
    customersearchChoiceBox.setValue("ID");
  }

  private void addSearchListener() {
    customersearchTxt
        .textProperty()
        .addListener(
            (observable, oldValue, newValue) -> {
              if (newValue.isEmpty()) {
                customertableClient.getSelectionModel().clearSelection();
              } else {
                searchClientBy(newValue);
              }
            });
  }

  private void searchClientBy(String value) {
    String searchOption = customersearchChoiceBox.getValue();
    switch (searchOption) {
      case "ID":
        searchClient(client -> String.valueOf(client.getClientId()).equals(value));
        break;
      case "License Number":
        searchClient(
            client -> {
              String licenseNo = client.getLicenseNo();
              return licenseNo != null && licenseNo.equalsIgnoreCase(value);
            });
        break;
      default:
        searchClient(client -> String.valueOf(client.getClientId()).equals(value));
        break;
    }
  }

  private void searchClient(Predicate<Client> predicate) {
    for (int i = 0; i < customertableClient.getItems().size(); i++) {
      if (predicate.test(customertableClient.getItems().get(i))) {
        customertableClient.scrollTo(i);
        customertableClient.getSelectionModel().select(i);
        return;
      }
    }
  }

  private void setUpTableColumns() {
    customercolId.setCellValueFactory(new PropertyValueFactory<>("clientId"));
    customercolName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
    customercolPhone.setCellValueFactory(new PropertyValueFactory<>("phoneNo"));
    customercolLicense.setCellValueFactory(new PropertyValueFactory<>("licenseNo"));
    customertableClient.setPlaceholder(new Label(""));
    customertableClient.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    customertableClient.setFocusTraversable(false);
    customertableClient.setItems(dataModel.getClientList());
    setTableColumnsWidthAndResizable();
  }

  private void setTableColumnsWidthAndResizable() {
    customercolId.setPrefWidth(120);
    customercolName.setPrefWidth(180);
    customercolPhone.setPrefWidth(170);
    customercolLicense.setPrefWidth(170);
    customercolUpdate.setPrefWidth(120);

    customercolId.setResizable(false);
    customercolName.setResizable(false);
    customercolPhone.setResizable(false);
    customercolLicense.setResizable(false);
    customercolUpdate.setResizable(false);
  }

  private void setUpUpdateButtonCol() {
    customercolUpdate.setCellFactory(
        param ->
            new TableCell<>() {
              private final Button updateButton = createUpdateButton();

              {
                updateButton.setOnAction(
                    event -> {
                      Client client = getTableView().getItems().get(getIndex());
                      handleUpdateButtonAction(client);
                    });
              }

              @Override
              protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                  setGraphic(null);
                } else {
                  setGraphic(updateButton);
                }
              }
            });
  }
  private Button createUpdateButton() {
    Button updateButton = new Button();
    updateButton.getStyleClass().add("button-view");

    FontAwesomeIconView icon = new FontAwesomeIconView();
    icon.setGlyphName("REFRESH");
    icon.setFill(Color.BLACK);

    updateButton.setGraphic(icon);
    updateButton.setMaxWidth(Double.MAX_VALUE);
    updateButton.setMaxHeight(Double.MAX_VALUE);
    updateButton.setAlignment(Pos.CENTER);
    return updateButton;
  }
  private void handleUpdateButtonAction(Client client) {
    showUpdatePane(true);
    customerClientUpdatePane.requestFocus();
    customerclientNoLbl.setText(String.valueOf(client.getClientId()));
    customertxtID.setText(String.valueOf(client.getClientId()));
    customertxtName.setText(client.getFirstName());
    customertxtPhone.setText(String.valueOf(client.getPhoneNo()));
    customertxtLicense.setText(client.getLicenseNo());
  }


  private void showUpdatePane(boolean show) {
    customerClientUpdatePane.setVisible(show);
    customerpane1.setDisable(show);
    customerpane2.setDisable(show);
  }

  private void handleUpdateClient(int clientId, String firstName, String phone, String license) {
    Client updatedClient = new Client(clientId, firstName, phone, license);
    clientdb.updateClient(updatedClient);
    showUpdatePane(false);
  }

  @FXML
  void onUpdateClient(MouseEvent event) {
    String firstName = customertxtName.getText();
    String phoneNo = customertxtPhone.getText();
    if (!ValidationManager.isNameValid(firstName)) {
      AlertManager.showAlert(
          AlertType.WARNING, "Invalid Name Format", "First Name Format Is Invalid");
      return;
    }
    if (!ValidationManager.isPhoneNoValid(phoneNo)) {
      AlertManager.showAlert(
          AlertType.WARNING, "Invalid Phone Number", "Phone Number must be a 7 digit number");
      return;
    }

    int clientId = Integer.parseInt(customertxtID.getText());
    String licenseNo = customertxtLicense.getText();
    handleUpdateClient(clientId, firstName, phoneNo, licenseNo);
  }

  @FXML
  void onCancelUpdate(MouseEvent event) {
    showUpdatePane(false);
  }
}
