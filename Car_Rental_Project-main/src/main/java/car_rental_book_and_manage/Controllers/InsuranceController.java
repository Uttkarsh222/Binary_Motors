package car_rental_book_and_manage.Controllers;

import car_rental_book_and_manage.App;
import car_rental_book_and_manage.InsuranceStrategy.BasicCoverStrategy;
import car_rental_book_and_manage.InsuranceStrategy.InsuranceManager;
import car_rental_book_and_manage.InsuranceStrategy.LimitedCoverStrategy;
import car_rental_book_and_manage.InsuranceStrategy.PremiumCoverStrategy;
import car_rental_book_and_manage.Utility.SceneManager;
import car_rental_book_and_manage.Utility.SceneManager.Scenes;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class InsuranceController extends Controller {

  @FXML private Button BasicBtn;
  @FXML private Button PremiumBtn;
  @FXML private Label clientNameLbl;
  @FXML private Label dailyCostLbl;
  @FXML private Label dailyTotalLbl;
  @FXML private Label insuranceCostLbl;
  @FXML private Label insuranceDetailsLbl;
  @FXML private Label insuranceTypeLbl;
  @FXML private Button limitedBtn;
  @FXML private Label noOfDaysLbl;
  @FXML private Label pickUpLbl;
  @FXML private Label returnLbl;
  @FXML private Label totalAmountLbl;
  @FXML private Label brandLbl;
  @FXML private Label modelLbl;
  @FXML private VBox imageVbox;
  @FXML private ImageView vehicleImageView;
  @FXML private Pane basicCoverPane;
  @FXML private Pane limitedCoverPane;
  @FXML private Pane premiumCoverPane;

  private InsuranceManager insuranceManage = new InsuranceManager();

  public void initialize() {
    setupBindings();
    selectInitialInsuranceOption();
  }

  private void setupBindings() {
    SceneManager.setController(Scenes.INSURANCE, this);
    clientNameLbl.textProperty().bind(dataModel.loggedInClientName());
    pickUpLbl.textProperty().bind(reservationManager.pickUpDateProperty().asString());
    returnLbl.textProperty().bind(reservationManager.returnDateProperty().asString());
    noOfDaysLbl.textProperty().bind(reservationManager.totalDaysProperty());
    dailyCostLbl.textProperty().bind(reservationManager.dailyPriceProperty());
    totalAmountLbl.textProperty().bind(reservationManager.totalAmountProperty());
    brandLbl.textProperty().bind(reservationManager.brandProperty());
    modelLbl.textProperty().bind(reservationManager.modelProperty());
    insuranceCostLbl.textProperty().bind(reservationManager.insuranceCostProperty());
    insuranceTypeLbl.textProperty().bind(reservationManager.insuranceTypeProperty());
    dailyTotalLbl.textProperty().bind(reservationManager.dailyTotalProperty());
    vehicleImageView.imageProperty().bind(reservationManager.vehicleImageProperty());
  }

  private void selectInitialInsuranceOption() {
    onSelectLimited(null);
  }

  @FXML
  void onGoBack(MouseEvent event) {
    App.setUi(Scenes.FINDVEHICLES);
    onSelectLimited(null);
  }

  @FXML
  void onProceed(MouseEvent event) {
    setInsuranceDetails();
    App.setUi(Scenes.PAYMENT);
  }

  private void setInsuranceDetails() {
    updateInsuranceDetails();
  }


  @FXML
  void onSelectBasic(MouseEvent event) {
    insuranceManage.setStrategy(new BasicCoverStrategy());
    updateInsuranceDetails();
    setSelectedButton(BasicBtn);
  }

  @FXML
  void onSelectPremium(MouseEvent event) {
    insuranceManage.setStrategy(new PremiumCoverStrategy());
    updateInsuranceDetails();
    setSelectedButton(PremiumBtn);
  }

  @FXML
  void onSelectLimited(MouseEvent event) {
    insuranceManage.setStrategy(new LimitedCoverStrategy());
    updateInsuranceDetails();
    setSelectedButton(limitedBtn);
  }

  private void updateInsuranceDetails() {
    reservationManager.setInsuranceType(insuranceManage.getStrategy().getInsuranceTypeName());
    reservationManager.setInsuranceCost(insuranceManage.getDailyCost());
    reservationManager.updateTotalAmount();
    insuranceDetailsLbl.setText(insuranceManage.getDescription());
  }

  private void setSelectedButton(Button selectedButton) {
    resetButtonStyles();
    setButtonAsSelected(selectedButton);
  }

  private void resetButtonStyles() {
    resetButtonStyle(BasicBtn);
    resetButtonStyle(PremiumBtn);
    resetButtonStyle(limitedBtn);
  }

  private void resetButtonStyle(Button button) {
    button.getStyleClass().remove("selected-button");
    button.getStyleClass().add("button-insurance");
    button.setText("SELECT");
  }

  private void setButtonAsSelected(Button button) {
    button.getStyleClass().remove("button-insurance");
    button.getStyleClass().add("selected-button");
    button.setText("SELECTED");
  }
}
