package car_rental_book_and_manage.Controllers;

import car_rental_book_and_manage.Objects.Reservation;
import car_rental_book_and_manage.Utility.SceneManager;
import car_rental_book_and_manage.Utility.SceneManager.Scenes;
import java.util.function.Predicate;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class BookingsController extends Controller {

  @FXML private TableColumn<Reservation, Integer> colRentalId;
  @FXML private TableColumn<Reservation, Integer> colClientId;
  @FXML private TableColumn<Reservation, Integer> colVehicleId;
  @FXML private TableColumn<Reservation, String> colVehicleReg;
  @FXML private TableColumn<Reservation, String> colLicenseNum;
  @FXML private TableColumn<Reservation, Double> colTotalRate;
  @FXML private TableColumn<Reservation, String> colStartDate;
  @FXML private TableColumn<Reservation, String> colReturnDate;
  @FXML private TableColumn<Reservation, String> colInsuranceType;
  @FXML private TableView<Reservation> tableReservation;
  @FXML private TextField searchTxt;
  @FXML private ChoiceBox<String> searchChoiceBox;
  @FXML private Button cancelBtn;

  public void initialize() {
    SceneManager.setController(Scenes.BOOKINGS, this);
    searchChoiceBox
        .getItems()
        .addAll("Rental ID", "Client License", "Vehicle Reg", "Vehicle ID", "Client ID");
    searchChoiceBox.setValue("Rental ID");
    setUpTableColumns();
    addSearchListener();
  }

  private void addSearchListener() {
    searchTxt
        .textProperty()
        .addListener(
            (observable, oldValue, newValue) -> {
              if (newValue.isEmpty()) {
                tableReservation.getSelectionModel().clearSelection();
              } else {
                searchReservationBy(newValue);
              }
            });
  }

  private void searchReservationBy(String value) {
    String searchOption = searchChoiceBox.getValue();
    switch (searchOption) {
      case "Rental ID":
        searchReservation(
            reservation -> String.valueOf(reservation.getReservationId()).equals(value));
        break;
      case "Vehicle Reg":
        searchReservation(
            reservation -> {
              String vehicleReg = reservation.getLicensePlate();
              return vehicleReg != null && vehicleReg.equalsIgnoreCase(value);
            });
        break;
      case "Client License":
        searchReservation(
            reservation -> {
              String licenseNo = reservation.getLicenseNo();
              return licenseNo != null && licenseNo.equalsIgnoreCase(value);
            });
        break;
      case "Vehicle ID":
        searchReservation(reservation -> String.valueOf(reservation.getVehicleId()).equals(value));
        break;
      case "Client ID":
        searchReservation(reservation -> String.valueOf(reservation.getClientId()).equals(value));
        break;
      default:
        searchReservation(
            reservation -> String.valueOf(reservation.getReservationId()).equals(value));
        break;
    }
  }

  private void searchReservation(Predicate<Reservation> predicate) {
    for (int i = 0; i < tableReservation.getItems().size(); i++) {
      if (predicate.test(tableReservation.getItems().get(i))) {
        tableReservation.scrollTo(i);
        tableReservation.getSelectionModel().select(i);
        return;
      }
    }
  }

  private void setUpTableColumns() {
    colRentalId.setCellValueFactory(new PropertyValueFactory<>("reservationId"));
    colClientId.setCellValueFactory(new PropertyValueFactory<>("clientId"));
    colVehicleId.setCellValueFactory(new PropertyValueFactory<>("vehicleId"));
    colVehicleReg.setCellValueFactory(new PropertyValueFactory<>("licensePlate"));
    colLicenseNum.setCellValueFactory(new PropertyValueFactory<>("licenseNo"));
    colTotalRate.setCellValueFactory(new PropertyValueFactory<>("totalRate"));
    colStartDate.setCellValueFactory(new PropertyValueFactory<>("startDate"));
    colReturnDate.setCellValueFactory(new PropertyValueFactory<>("returnDate"));
    colInsuranceType.setCellValueFactory(new PropertyValueFactory<>("insuranceType"));

    tableReservation.setPlaceholder(new Label(""));
    tableReservation.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    tableReservation.setFocusTraversable(false);

    setTableColumnsResizable(false);

    tableReservation.setItems(dataModel.getReservationList());
  }

  private void setTableColumnsResizable(boolean resizable) {
    colRentalId.setResizable(resizable);
    colClientId.setResizable(resizable);
    colVehicleId.setResizable(resizable);
    colVehicleReg.setResizable(resizable);
    colLicenseNum.setResizable(resizable);
    colTotalRate.setResizable(resizable);
    colStartDate.setResizable(resizable);
    colReturnDate.setResizable(resizable);
    colInsuranceType.setResizable(resizable);
  }
}
