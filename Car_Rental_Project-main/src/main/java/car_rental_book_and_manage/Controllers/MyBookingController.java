package car_rental_book_and_manage.Controllers;

import car_rental_book_and_manage.InsuranceStrategy.InsuranceManager;
import car_rental_book_and_manage.InsuranceStrategy.InsuranceStrategy;
import car_rental_book_and_manage.Objects.Client;
import car_rental_book_and_manage.Objects.Reservation;
import car_rental_book_and_manage.Objects.Vehicle;
import car_rental_book_and_manage.Utility.SceneManager;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class MyBookingController extends Controller {

  @FXML private Label fuelLbl;
  @FXML private Label regLbl;
  @FXML private Label brandLbl;
  @FXML private Label clientNameLbl;
  @FXML private Label colourLbl;
  @FXML private Label insuranceCostLbl;
  @FXML private Label insuranceTypeLbl;
  @FXML private Label insuranceDetailsLbl;
  @FXML private Label modelLbl;
  @FXML private Label pickUpLbl;
  @FXML private Label returnLbl;
  @FXML private Label totalAmountLbl;
  @FXML private Label yearLbl;
  @FXML private VBox bookingDetailsVBox;
  @FXML private VBox vehicleDetailsVBox;
  @FXML private Pane resBox;
  @FXML private Pane noResBox;
  @FXML private ImageView vehicleImage;

  private InsuranceManager insuranceManage = new InsuranceManager();

  /** Initializes the controller, setting up bindings and listeners for reservation details. */
  public void initialize() {
    SceneManager.setController(SceneManager.Scenes.MYBOOKING, this);
    clientNameLbl.textProperty().bind(dataModel.loggedInClientName());

    setupClientChangeListener();
    setupReservationChangeListener();

    Client loggedInClient = reservationManager.getLoggedInClient();
    if (loggedInClient != null) {
      loadReservationDetails(loggedInClient);
    }
  }

  private void setupClientChangeListener() {
    reservationManager
        .loggedInClientProperty()
        .addListener(
            new ChangeListener<Client>() {
              @Override
              public void changed(
                  ObservableValue<? extends Client> observable, Client oldValue, Client newValue) {
                if (newValue != null) {
                  loadReservationDetails(newValue);
                } else {
                  clearReservationDetails();
                }
              }
            });
  }


  private void setupReservationChangeListener() {
    reservationManager
        .currentReservationProperty()
        .addListener(
            (obs, oldReservation, newReservation) -> {
              if (newReservation != null) {
                displayReservationDetails(newReservation);
              } else {
                clearReservationDetails();
              }
            });
  }

  private void loadReservationDetails(Client client) {
    Reservation reservation = reservationdb.getReservationForClient(client.getClientId());
    if (reservation != null) {
      Vehicle vehicle = vehicledb.getVehicleById(reservation.getVehicleId());
      reservationManager.setSelectedVehicle(vehicle);
      reservationManager.setCurrentReservation(reservation);
      displayReservationDetails(reservation);
    } else {
      clearReservationDetails();
    }
  }

  private void displayReservationDetails(Reservation reservation) {
    Vehicle bookedVehicle = reservationManager.getSelectedVehicle();
    if (bookedVehicle != null) {
      updateVehicleDetails(bookedVehicle);
      updateReservationDetails(reservation);
      toggleDetailsVisibility(true);
    } else {
      clearReservationDetails();
    }
  }

  private void updateVehicleDetails(Vehicle bookedVehicle) {
    brandLbl.setText(bookedVehicle.getBrand());
    modelLbl.setText(bookedVehicle.getModel());
    yearLbl.setText(String.valueOf(bookedVehicle.getMakeYear()));
    regLbl.setText(bookedVehicle.getLicensePlate());
    colourLbl.setText(bookedVehicle.getColour());
    fuelLbl.setText(bookedVehicle.getFuelType());
    vehicleImage.setImage(
        new Image(
            getClass().getResourceAsStream("/images and attribution/" + bookedVehicle.getImage())));
  }

  private void updateReservationDetails(Reservation reservation) {
    pickUpLbl.setText(reservation.getStartDate().toString());
    returnLbl.setText(reservation.getReturnDate().toString());
    insuranceTypeLbl.setText(reservation.getInsuranceType());
    InsuranceStrategy strategy = insuranceManage.getStrategyByType(reservation.getInsuranceType());
    insuranceDetailsLbl.setText(strategy.getDescription());
    totalAmountLbl.setText(String.format("$%.2f", reservation.getTotalRate()));
  }

  private void clearReservationDetails() {
    toggleDetailsVisibility(false);
  }

  private void toggleDetailsVisibility(boolean visible) {
    bookingDetailsVBox.setVisible(visible);
    vehicleDetailsVBox.setVisible(visible);
    resBox.setVisible(visible);
    noResBox.setVisible(!visible);
  }
}
