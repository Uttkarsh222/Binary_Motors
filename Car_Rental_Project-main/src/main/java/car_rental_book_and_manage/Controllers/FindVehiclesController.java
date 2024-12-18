package car_rental_book_and_manage.Controllers;

import car_rental_book_and_manage.App;
import car_rental_book_and_manage.Objects.Reservation;
import car_rental_book_and_manage.Objects.Vehicle;
import car_rental_book_and_manage.Objects.VehicleDisplayMaker;
import car_rental_book_and_manage.Utility.AlertManager;
import car_rental_book_and_manage.Utility.SceneManager;
import car_rental_book_and_manage.Utility.SceneManager.Scenes;
import java.time.LocalDate;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.util.Callback;

public class FindVehiclesController extends Controller {

  // FXML components
  @FXML private Label clientNameLbl;
  @FXML private ScrollPane scrollPane;
  @FXML private GridPane contentGrid;
  @FXML private DatePicker pickUpDate;
  @FXML private DatePicker returnDate;

  // Constants
  private final int COLUMNS = 2;
  private final VehicleDisplayMaker vehicleImageFactory = new VehicleDisplayMaker();

  public void initialize() {
    SceneManager.setController(SceneManager.Scenes.FINDVEHICLES, this);
    clientNameLbl.textProperty().bind(dataModel.loggedInClientName());
    dataModel
        .getAvailableVehicleList()
        .addListener(
            (ListChangeListener<Vehicle>)
                change -> setupGridPane(dataModel.getAvailableVehicleList(), COLUMNS));
    initializeDatePickers();
  }

  private void initializeDatePickers() {
    pickUpDate.setDayCellFactory(getDayCellFactoryForPickup());
    returnDate.setDayCellFactory(getDayCellFactoryForReturn());
    makeDatePickerTextFieldUneditable(pickUpDate);
    makeDatePickerTextFieldUneditable(returnDate);
    pickUpDate.valueProperty().bindBidirectional(reservationManager.pickUpDateProperty());
    returnDate.valueProperty().bindBidirectional(reservationManager.returnDateProperty());
  }

  private void makeDatePickerTextFieldUneditable(DatePicker datePicker) {
    datePicker.getEditor().setDisable(true);
    datePicker.getEditor().setOpacity(1);
  }

  private Callback<DatePicker, DateCell> getDayCellFactoryForPickup() {
    return datePicker ->
        new DateCell() {
          @Override
          public void updateItem(LocalDate item, boolean empty) {
            super.updateItem(item, empty);
            if (item.isBefore(LocalDate.now()) || item.isAfter(LocalDate.now().plusMonths(6))) {
              setDisable(true);
              setStyle("-fx-background-color: #ffc0cb;");
            }
          }
        };
  }

  private Callback<DatePicker, DateCell> getDayCellFactoryForReturn() {
    return datePicker ->
        new DateCell() {
          @Override
          public void updateItem(LocalDate item, boolean empty) {
            super.updateItem(item, empty);
            LocalDate pickUp = pickUpDate.getValue();
            if (pickUp == null
                || item.isBefore(pickUp.plusDays(1))
                || item.isAfter(pickUp.plusMonths(6))) {
              setDisable(true);
              setStyle("-fx-background-color: #ffc0cb;");
            }
          }
        };
  }


  private void setupGridPane(ObservableList<Vehicle> vehicles, int columns) {
    int totalItems = vehicles.size();
    int rows = (int) Math.ceil((double) totalItems / columns);

    clearGridPane();

    setGridPaneProperties(columns, rows);

    setupGridColumns(columns);
    setupGridRows(rows);

    addVehiclesToGrid(vehicles, columns, rows);
  }


  private void clearGridPane() {
    contentGrid.getColumnConstraints().clear();
    contentGrid.getRowConstraints().clear();
    contentGrid.getChildren().clear(); // Clear previous content
  }


  private void setGridPaneProperties(int columns, int rows) {
    contentGrid.setHgap(20); // Set horizontal gap (spacing between columns)
    contentGrid.setVgap(20); // Set vertical gap (spacing between rows)
    contentGrid.setPadding(new Insets(20)); // Set padding around the edges of the GridPane
  }


  private void setupGridColumns(int columns) {
    for (int col = 0; col < columns; col++) {
      ColumnConstraints colConstraints = new ColumnConstraints();
      colConstraints.setPercentWidth(100.0 / columns);
      contentGrid.getColumnConstraints().add(colConstraints);
    }
  }


  private void setupGridRows(int rows) {
    for (int row = 0; row < rows; row++) {
      RowConstraints rowConstraints = new RowConstraints();
      rowConstraints.setPrefHeight(400); // Set a preferred height for rows
      contentGrid.getRowConstraints().add(rowConstraints);
    }
  }


  private void addVehiclesToGrid(ObservableList<Vehicle> vehicles, int columns, int rows) {
    int itemIndex = 0;
    int totalItems = vehicles.size();
    for (int row = 0; row < rows; row++) {
      for (int col = 0; col < columns; col++) {
        if (itemIndex >= totalItems) break;
        Vehicle vehicle = vehicles.get(itemIndex);
        Pane pane = vehicleImageFactory.createVehiclePane(vehicle, event -> handleBookNow(vehicle));
        contentGrid.add(pane, col, row);
        GridPane.setHalignment(pane, HPos.CENTER); // Center horizontally
        GridPane.setValignment(pane, VPos.CENTER); // Center vertically
        itemIndex++;
      }
    }
  }

  private void handleBookNow(Vehicle vehicle) {
    if (pickUpDate.getValue() == null
        || returnDate.getValue() == null
        || !returnDate.getValue().isAfter(pickUpDate.getValue())) {
      AlertManager.showAlert(
          AlertType.WARNING,
          "Date Not Selected",
          "Please select a valid pick-up and return date before booking.");
      return;
    }

    int clientId = reservationManager.getLoggedInClient().getClientId();
    Reservation existingReservation = reservationdb.getReservationForClient(clientId);
    System.out.println(existingReservation);

    if (existingReservation != null) {
      AlertManager.showAlert(
          AlertType.WARNING,
          "Already Booked",
          "You already have a booking. You can not make a new booking.");
      return;
    }

    reservationManager.setSelectedVehicle(vehicle);
    reservationManager.updateTotalAmount();

    App.setUi(Scenes.INSURANCE);
  }
}
