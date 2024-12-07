package car_rental_book_and_manage.Controllers;

import car_rental_book_and_manage.App;
import car_rental_book_and_manage.Objects.Client;
import car_rental_book_and_manage.Objects.Reservation;
import car_rental_book_and_manage.Objects.ReservationDB;
import car_rental_book_and_manage.Objects.Vehicle;
import car_rental_book_and_manage.Payment.CardPayment;
import car_rental_book_and_manage.Payment.CreditCardPayment;
import car_rental_book_and_manage.Payment.DebitCardPayment;
import car_rental_book_and_manage.Utility.SceneManager.Scenes;
import car_rental_book_and_manage.Utility.ValidationManager;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.time.LocalDate;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class PaymentController extends Controller {

  @FXML private FontAwesomeIconView card;
  @FXML private TextField cardNameLbl;
  @FXML private TextField cardNoLbl;
  @FXML private Label clientNameLbl;
  @FXML private AnchorPane creditBlock;
  @FXML private TextField cvvLbl;
  @FXML private AnchorPane debitBlock;
  @FXML private TextField expiryDateLbl;
  @FXML private Label informationLbl;
  @FXML private Button proceedBtn;
  @FXML private VBox informationBox;

  private AnchorPane selectedBlock;
  private String selectedPaymentType;

  @FXML
  void initialize() {
    clientNameLbl.textProperty().bind(dataModel.loggedInClientName());
    informationBox.setDisable(true);
    addTextListeners();
    updateProceedButtonState();
  }

  @FXML
  void onCredit(MouseEvent event) {
    selectPaymentMethod(creditBlock, "Credit Card");
  }

  @FXML
  void onDebit(MouseEvent event) {
    selectPaymentMethod(debitBlock, "Debit Card");
  }

  private void selectPaymentMethod(AnchorPane block, String paymentType) {
    if (selectedBlock != null) {
      selectedBlock.getStyleClass().remove("selected");
    }
    selectedBlock = block;
    selectedBlock.getStyleClass().add("selected");
    selectedPaymentType = paymentType;
    informationBox.setDisable(false);
    updateProceedButtonState();
  }

  private void addTextListeners() {
    ChangeListener<String> listener =
        (observable, oldValue, newValue) -> updateProceedButtonState();
    cardNameLbl.textProperty().addListener(listener);
    cardNoLbl.textProperty().addListener(listener);
    cvvLbl.textProperty().addListener(listener);
    expiryDateLbl.textProperty().addListener(listener);
  }

  private void updateProceedButtonState() {
    boolean isDisabled =
        cardNameLbl.getText().isEmpty()
            || cardNoLbl.getText().isEmpty()
            || cvvLbl.getText().isEmpty()
            || expiryDateLbl.getText().isEmpty()
            || selectedPaymentType == null;
    proceedBtn.setDisable(isDisabled);
  }


  @FXML
  void onGoBack(MouseEvent event) {
    App.setUi(Scenes.INSURANCE);
  }

  @FXML
  void onProceed(MouseEvent event) {
    if (!ValidationManager.validateCardDetails(
        cardNameLbl.getText(), cardNoLbl.getText(), cvvLbl.getText(), expiryDateLbl.getText())) {
      return;
    }

    Reservation newReservation = createReservationObject();
    CardPayment payment = createPaymentObject(newReservation);

    saveReservationAndPayment(newReservation, payment);
    updateVehicleAvailability();
    resetFields();

    App.resetAllDatePickers();
    App.setUi(Scenes.CONFIRMATION);
  }

  private CardPayment createPaymentObject(Reservation reservation) {
    String cardNumber = cardNoLbl.getText();
    String cardHolderName = cardNameLbl.getText();
    String cvv = cvvLbl.getText();
    String expiryDate = expiryDateLbl.getText();
    double amount = Double.parseDouble(reservationManager.getDailyTotal());
    LocalDate paymentDate = LocalDate.now();
    int clientId = reservation.getClientId();
    int rentalId = reservation.getReservationId();

    if (selectedPaymentType.equals("Credit Card")) {
      return new CreditCardPayment(
          cardNumber, cardHolderName, cvv, expiryDate, amount, rentalId, clientId, paymentDate);
    } else {
      return new DebitCardPayment(
          cardNumber, cardHolderName, cvv, expiryDate, amount, rentalId, clientId, paymentDate);
    }
  }

  private void saveReservationAndPayment(Reservation reservation, CardPayment payment) {
    ReservationDB reservationdb = new ReservationDB();
    reservationdb.saveReservationAndPayment(reservation, payment);
    reservationManager.setCurrentReservation(reservation);
  }

  private Reservation createReservationObject() {
    Vehicle selectedVehicle = reservationManager.getSelectedVehicle();
    Client loggedInClient = reservationManager.getLoggedInClient();
    LocalDate pickUpDate = reservationManager.getPickUpDate();
    LocalDate returnDate = reservationManager.getReturnDate();
    double totalRate = Double.parseDouble(reservationManager.getTotalAmount());
    String insuranceType = reservationManager.getInsuranceType();

    return new Reservation(
        loggedInClient.getClientId(),
        selectedVehicle.getVehicleId(),
        totalRate,
        selectedVehicle.getLicensePlate(),
        loggedInClient.getLicenseNo(),
        pickUpDate,
        returnDate,
        insuranceType);
  }

  private void updateVehicleAvailability() {
    Vehicle selectedVehicle = reservationManager.getSelectedVehicle();
    vehicledb.setVehicleAvailability(selectedVehicle.getVehicleId(), false);
  }

  private void resetFields() {
    cardNameLbl.clear();
    cardNoLbl.clear();
    cvvLbl.clear();
    expiryDateLbl.clear();
    informationBox.setDisable(true);
    selectedBlock.getStyleClass().remove("selected");
    selectedBlock = null;
    selectedPaymentType = null;
    updateProceedButtonState();
  }

  @Override
  protected void onSignOut(MouseEvent event) {
    super.onSignOut(event);
    resetFields();
  }
}
