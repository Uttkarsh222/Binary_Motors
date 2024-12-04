package car_rental_book_and_manage.Controllers;

import car_rental_book_and_manage.App;
import car_rental_book_and_manage.Objects.ClientDB;
import car_rental_book_and_manage.Objects.DataModel;
import car_rental_book_and_manage.Objects.ReservationDB;
import car_rental_book_and_manage.Objects.VehicleDB;
import car_rental_book_and_manage.Utility.ReservationManager;
import car_rental_book_and_manage.Utility.SceneManager.Scenes;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;

/** Abstract base controller class that provides common functionality for all controllers. */
public abstract class Controller {

  protected ClientDB clientdb;
  protected VehicleDB vehicledb;
  protected ReservationDB reservationdb;
  protected final DataModel dataModel;
  protected final ReservationManager reservationManager;

  public Controller() {
    clientdb = new ClientDB();
    vehicledb = new VehicleDB();
    reservationdb = new ReservationDB();
    dataModel = DataModel.getInstance();
    reservationManager = ReservationManager.getInstance();
  }

  @FXML
  protected void onViewCustomers(MouseEvent event) {
    App.setUi(Scenes.CUSTOMERS);
  }

  @FXML
  protected void onViewBookings(MouseEvent event) {
    App.setUi(Scenes.BOOKINGS);
  }

  @FXML
  protected void onManageVehicles(MouseEvent event) {
    App.setUi(Scenes.MANAGE);
  }

  @FXML
  protected void onDashBoard(MouseEvent event) {
    App.setUi(Scenes.ADMIN);
  }

  @FXML
  protected void onSignOut(MouseEvent event) {
    App.setUi(Scenes.LOGIN);
    App.resetAllDatePickers();
    reservationManager.clearUserSession();
    reservationManager.clearSession();
  }

  @FXML
  protected void onMyBooking(MouseEvent event) {
    App.setUi(Scenes.MYBOOKING);
  }

  @FXML
  protected void onFindVehicles(MouseEvent event) {
    App.setUi(Scenes.FINDVEHICLES);
  }
}
