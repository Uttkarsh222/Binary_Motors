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

  @FXML private TableColumn<Reservation, Integer> bookingColRentalId;
  @FXML private TableColumn<Reservation, Integer> bookingColClientId;
  @FXML private TableColumn<Reservation, Integer> bookingColVehicleId;
  @FXML private TableColumn<Reservation, String> bookingColVehicleReg;
  @FXML private TableColumn<Reservation, String> bookingColLicenseNum;
  @FXML private TableColumn<Reservation, Double> bookingColTotalRate;
  @FXML private TableColumn<Reservation, String> bookingColStartDate;
  @FXML private TableColumn<Reservation, String> bookingColReturnDate;
  @FXML private TableColumn<Reservation, String> bookingColInsuranceType;
  @FXML private TableView<Reservation> tableReservation;
  @FXML private TextField customersearchTxt;
  @FXML private ChoiceBox<String> customersearchChoiceBox;
  @FXML private Button cancelBtn;

  public void initialize() {
    SceneManager.setController(Scenes.BOOKINGS, this);
    customersearchChoiceBox
        .getItems()
        .addAll("Rental ID", "Client License", "Vehicle Reg", "Vehicle ID", "Client ID");
    customersearchChoiceBox.setValue("Rental ID");
    bookingsetUpTableColumns();
    bookingaddSearchListener();
  }

  private void bookingaddSearchListener() {
    customersearchTxt
        .textProperty()
        .addListener(
            (observable, oldValue, newValue) -> {
              if (newValue.isEmpty()) {
                tableReservation.getSelectionModel().clearSelection();
              } else {
                bookingsearchReservationBy(newValue);
              }
            });
  }

  private void bookingsearchReservationBy(String value) {
    String searchOption = customersearchChoiceBox.getValue();
    switch (searchOption) {
      case "Rental ID":
        bookingsearchReservation(
            reservation -> String.valueOf(reservation.getReservationId()).equals(value));
        break;
      case "Vehicle Reg":
        bookingsearchReservation(
            reservation -> {
              String vehicleReg = reservation.getLicensePlate();
              return vehicleReg != null && vehicleReg.equalsIgnoreCase(value);
            });
        break;
      case "Client License":
        bookingsearchReservation(
            reservation -> {
              String licenseNo = reservation.getLicenseNo();
              return licenseNo != null && licenseNo.equalsIgnoreCase(value);
            });
        break;
      case "Vehicle ID":
        bookingsearchReservation(reservation -> String.valueOf(reservation.getVehicleId()).equals(value));
        break;
      case "Client ID":
        bookingsearchReservation(reservation -> String.valueOf(reservation.getClientId()).equals(value));
        break;
      default:
        bookingsearchReservation(
            reservation -> String.valueOf(reservation.getReservationId()).equals(value));
        break;
    }
  }

  private void bookingsearchReservation(Predicate<Reservation> predicate) {
    for (int i = 0; i < tableReservation.getItems().size(); i++) {
      if (predicate.test(tableReservation.getItems().get(i))) {
        tableReservation.scrollTo(i);
        tableReservation.getSelectionModel().select(i);
        return;
      }
    }
  }

  private void bookingsetUpTableColumns() {
    bookingColRentalId.setCellValueFactory(new PropertyValueFactory<>("reservationId"));
    bookingColClientId.setCellValueFactory(new PropertyValueFactory<>("clientId"));
    bookingColVehicleId.setCellValueFactory(new PropertyValueFactory<>("vehicleId"));
    bookingColVehicleReg.setCellValueFactory(new PropertyValueFactory<>("licensePlate"));
    bookingColLicenseNum.setCellValueFactory(new PropertyValueFactory<>("licenseNo"));
    bookingColTotalRate.setCellValueFactory(new PropertyValueFactory<>("totalRate"));
    bookingColStartDate.setCellValueFactory(new PropertyValueFactory<>("startDate"));
    bookingColReturnDate.setCellValueFactory(new PropertyValueFactory<>("returnDate"));
    bookingColInsuranceType.setCellValueFactory(new PropertyValueFactory<>("insuranceType"));

    tableReservation.setPlaceholder(new Label(""));
    tableReservation.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    tableReservation.setFocusTraversable(false);

    bookingsetTableColumnsResizable(false);

    tableReservation.setItems(dataModel.getReservationList());
  }

  private void bookingsetTableColumnsResizable(boolean resizable) {
    bookingColRentalId.setResizable(resizable);
    bookingColClientId.setResizable(resizable);
    bookingColVehicleId.setResizable(resizable);
    bookingColVehicleReg.setResizable(resizable);
    bookingColLicenseNum.setResizable(resizable);
    bookingColTotalRate.setResizable(resizable);
    bookingColStartDate.setResizable(resizable);
    bookingColReturnDate.setResizable(resizable);
    bookingColInsuranceType.setResizable(resizable);
  }
}
