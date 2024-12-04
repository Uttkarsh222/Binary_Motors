package car_rental_book_and_manage.Controllers;

import car_rental_book_and_manage.Objects.Vehicle;
import car_rental_book_and_manage.Utility.AlertManager;
import car_rental_book_and_manage.Utility.ImageSelect;
import car_rental_book_and_manage.Utility.SceneManager;
import car_rental_book_and_manage.Utility.SceneManager.Scenes;
import car_rental_book_and_manage.Utility.ValidationManager;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Stack;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;


public class ManageController<T> extends Controller {

  @FXML private TableColumn<Vehicle, Void> colView;
  @FXML private TableColumn<Vehicle, Boolean> colAvail;
  @FXML private TableColumn<Vehicle, String> colBrand;
  @FXML private TableColumn<Vehicle, String> colColour;
  @FXML private TableColumn<Vehicle, Double> colDailyRate;
  @FXML private TableColumn<Vehicle, String> colFuel;
  @FXML private TableColumn<Vehicle, Integer> colId;
  @FXML private TableColumn<Vehicle, Integer> colMakeYear;
  @FXML private TableColumn<Vehicle, String> colModel;
  @FXML private TableColumn<Vehicle, String> colReg;
  @FXML private TableColumn<Vehicle, String> colEconomy;
  @FXML private TableView<Vehicle> tableVehicle;
  @FXML private Button cancelBtn;
  @FXML private ChoiceBox<String> choiceFuel;
  @FXML private Button importBtn;
  @FXML private Button saveVehicleBtn;
  @FXML private Button updateVehicleBtn;
  @FXML private Button deleteVehicleBtn;
  @FXML private AnchorPane vehiclePane;
  @FXML private AnchorPane pane1;
  @FXML private AnchorPane pane2;
  @FXML private TextField txtBrand;
  @FXML private TextField txtColour;
  @FXML private TextField txtDailyRate;
  @FXML private TextField txtModel;
  @FXML private TextField txtRegNumber;
  @FXML private TextField txtYear;
  @FXML private TextField txtEconomy;
  @FXML private Label titleLbl;
  @FXML private Label idLbl;
  @FXML private ChoiceBox<String> searchChoiceBox;
  @FXML private TextField searchTxt;
  @FXML private ImageView vehicleImageView;

  private String imageName;
  private Vehicle selectedVehicle;
  private boolean isDefaultImage = true;
  
  /*  Implemented a Stack to keep track of list updates over search functionality*/
  private Stack<Vehicle> tableItems;

  public void initialize() {
    SceneManager.setController(Scenes.MANAGE, this);
    initializeChoiceBoxes();
    setUpTableColumns();
    setUpViewButtonCol();
    addSearchListener();
    pane2.getStylesheets().add(getClass().getResource("/css/customcol.css").toExternalForm());
    tableItems = new Stack<>();
  }

  private void initializeChoiceBoxes() {
    choiceFuel.getItems().addAll("Regular", "Diesel", "Premium");
    choiceFuel.setValue("Regular");
    searchChoiceBox.setValue("ID");
    searchChoiceBox.getItems().addAll("ID", "Registration");
  }

  private void addSearchListener() {
    searchTxt
        .textProperty()
        .addListener(
            (observable, oldValue, newValue) -> {
              if (newValue.isEmpty()) {
                tableVehicle.getSelectionModel().clearSelection();
              } else {
                searchVehicleBy(newValue);
              }
            });
  }

  private void searchVehicleBy(String value) {
    String searchOption = searchChoiceBox.getValue();
    Predicate<Vehicle> predicate = getSearchPredicate(searchOption, value);
    searchVehicle(predicate);
  }



  private Predicate<Vehicle> getSearchPredicate(String searchOption, String value) {
    switch (searchOption) {
      case "Registration":
        return vehicle -> {
          String reg = vehicle.getLicensePlate();
          return reg != null && reg.equalsIgnoreCase(value);
        };
      case "ID":
      default:
        return vehicle -> String.valueOf(vehicle.getVehicleId()).equals(value);
    }
  }


  private void searchVehicle(Predicate<Vehicle> predicate) {
    for (int i = 0; i < tableVehicle.getItems().size(); i++) {
    	System.out.println("Object type: " + tableVehicle.getItems().getClass());
      if (predicate.test(tableVehicle.getItems().get(i))) {
    	  tableItems.add((tableVehicle.getItems().get(i)));
    	  while(!tableItems.isEmpty()) {
    	    	
    	    	System.out.println("Vehicle: " + tableItems.pop().toString());
    	    }
        tableVehicle.scrollTo(i);
        tableVehicle.getSelectionModel().select(i);
        return;
      }
      
      else {
    	  if(tableItems.size() > 0) {
    		  tableItems.pop();
    	  }
      }
    }
    
    
  }

  private void setUpTableColumns() {
    setUpTableColumn(colId, "vehicleId", 30);
    setUpTableColumn(colMakeYear, "makeYear", 40);
    setUpTableColumn(colModel, "model", 100);
    setUpTableColumn(colBrand, "brand", 100);
    setUpTableColumn(colReg, "licensePlate", 65);
    setUpTableColumn(colDailyRate, "pricePerDay", 80);
    setUpTableColumn(colColour, "colour", 72);
    setUpTableColumn(colFuel, "fuelType", 75);
    setUpTableColumn(colEconomy, "economy", 99);
    setUpAvailabilityColumn();
    configureTable();
  }

  private void setUpTableColumn(TableColumn<Vehicle, ?> column, String property, double width) {
    column.setCellValueFactory(new PropertyValueFactory<>(property));
    column.setPrefWidth(width);
    column.setResizable(false);
  }

  private void setUpAvailabilityColumn() {
    colAvail.setCellValueFactory(new PropertyValueFactory<>("availability"));
    colAvail.setCellFactory(
        column ->
            new TableCell<Vehicle, Boolean>() {
              private final AnchorPane pane = new AnchorPane();

              @Override
              protected void updateItem(Boolean item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                  setGraphic(null);
                } else {
                  pane.getStyleClass().clear();
                  pane.getStyleClass().add(item ? "square-true" : "square-false");
                  setGraphic(pane);
                }
              }
            });
    colAvail.setPrefWidth(55);
    colAvail.setResizable(false);
  }

  private void configureTable() {
    tableVehicle.setPlaceholder(new Label(""));
    tableVehicle.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    tableVehicle.setFocusTraversable(false);
    tableVehicle.setItems(dataModel.getVehicleList());
  }

  private void setUpViewButtonCol() {
    colView.setCellFactory(
        param ->
            new TableCell<>() {
              private final Button viewButton = createViewButton();

              {
                viewButton.setOnAction(
                    event -> {
                      Vehicle vehicle = getTableView().getItems().get(getIndex());
                      selectedVehicle = vehicle;
                      handleViewAction(vehicle);
                    });
              }

              @Override
              protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : viewButton);
              }
            });
    colView.setPrefWidth(45);
    colView.setResizable(false);
  }

  private Button createViewButton() {
    Button viewButton = new Button();
    viewButton.getStyleClass().add("button-view");

    FontAwesomeIconView icon = new FontAwesomeIconView();
    icon.setGlyphName("EYE");
    icon.setFill(Color.BLACK);

    viewButton.setGraphic(icon);
    viewButton.setMaxWidth(Double.MAX_VALUE);
    viewButton.setMaxHeight(Double.MAX_VALUE);
    viewButton.setAlignment(Pos.CENTER);
    return viewButton;
  }

  private boolean validateVehicleFields(String action) {
    String id = idLbl.getText();
    String brand = txtBrand.getText();
    String model = txtModel.getText();
    String dailyRate = txtDailyRate.getText();
    String regNo = txtRegNumber.getText();
    String year = txtYear.getText();
    String colour = txtColour.getText();
    String fuel = choiceFuel.getValue();
    String econ = txtEconomy.getText();

    if (!ValidationManager.validateVehicleFields(brand, model, year, colour, dailyRate, regNo, fuel, econ)) {
      return false;
    }

    // Check if an image is selected only for "save" action or if the current image is the default one
    if (action.equals("save") && isDefaultImage) {
      AlertManager.showAlert(
          AlertType.WARNING, "Missing Image", "Please import an image before saving.");
      return false;
    }

    // Additional validation for save action
    if (action.equals("save")) {
      if (ValidationManager.checkRegistrationNoExists(regNo, action, vehicledb, id)) {
        return false;
      }
    }

    // Additional validation for update action
    if (action.equals("update")) {
      if (ValidationManager.checkRegistrationNoExists(regNo, action, vehicledb, id)) {
        return false;
      }
    }

    return true;
  }

  private void handleViewAction(Vehicle vehicle) {
    showVehiclePane(true);
    vehiclePane.requestFocus();
    saveVehicleBtn.setVisible(false);
    updateVehicleBtn.setVisible(true);
    deleteVehicleBtn.setVisible(true);

    boolean isAvailable = vehicle.getAvailability();
    updateVehicleBtn.setDisable(!isAvailable);
    deleteVehicleBtn.setDisable(!isAvailable);
    importBtn.setDisable(!isAvailable);

    titleLbl.setText("View Vehicle ID:");
    idLbl.setText(String.valueOf(vehicle.getVehicleId()));
    populateVehicleFields(vehicle);
  }

  private void handleAddAction() {
    titleLbl.setText("Add New Vehicle");
    showVehiclePane(true);
    vehiclePane.requestFocus();
    updateVehicleBtn.setVisible(false);
    deleteVehicleBtn.setVisible(false);
    saveVehicleBtn.setVisible(true);
    clearTextFields();
    vehicleImageView.setImage(new Image("/images and attribution/importcar.png"));
    isDefaultImage = true;
  }

  @FXML
  void onAddNewVehicle(MouseEvent event) {
    handleAddAction();
  }

  @FXML
  void onSaveVehicle(MouseEvent event) {
    if (validateVehicleFields("save")) {
      Vehicle vehicle = createVehicleFromFields();
      storeOrUpdateVehicle(vehicle);
    }
  }

  @FXML
  void onUpdateVehicle(MouseEvent event) {
    if (validateVehicleFields("update")) {
      Vehicle vehicle = createVehicleFromFields();
      vehicle.setVehicleId(Integer.parseInt(idLbl.getText()));
      storeOrUpdateVehicle(vehicle);
    }
  }

  @FXML
  void onDeleteVehicle(MouseEvent event) {
    vehicledb.deleteVehicle(selectedVehicle);
    AlertManager.showAlert(AlertType.CONFIRMATION, "", "Vehicle Deleted Successfully");
    clearTextFields();
    showVehiclePane(false);
  }

  @FXML
  void onCancel(MouseEvent event) {
    clearTextFields();
    showVehiclePane(false);
  }

  @FXML
  void onImportImage(MouseEvent event) {
    vehiclePane.setDisable(true);

    File selectedFile = ImageSelect.selectImageFile(null);
    if (selectedFile != null) {
      if (ImageSelect.isValidImage(selectedFile)) {
        imageName = selectedFile.getName();
        vehicleImageView.setImage(new Image("/images and attribution/" + imageName));
        isDefaultImage = false;
        System.out.println("Image Path: " + imageName);
      } else {
        System.out.println("Invalid image selected.");
      }
    } else {
      System.out.println("File selection cancelled.");
    }

    vehiclePane.setDisable(false);
  }

  private void showVehiclePane(boolean visible) {
    vehiclePane.setVisible(visible);
    pane1.setDisable(visible);
    pane2.setDisable(visible);
  }

  private void clearTextFields() {
    txtBrand.clear();
    txtModel.clear();
    txtDailyRate.clear();
    txtRegNumber.clear();
    txtYear.clear();
    txtColour.clear();
    idLbl.setText(null);
    choiceFuel.setValue("Regular");
    txtEconomy.clear();
    vehicleImageView.setImage(new Image("/images and attribution/importcar.png"));
    isDefaultImage = true;
  }

  private void populateVehicleFields(Vehicle vehicle) {
    txtBrand.setText(vehicle.getBrand());
    txtColour.setText(vehicle.getColour());
    txtDailyRate.setText(String.valueOf(vehicle.getPricePerDay()));
    txtModel.setText(vehicle.getModel());
    txtRegNumber.setText(vehicle.getLicensePlate());
    txtYear.setText(String.valueOf(vehicle.getMakeYear()));
    choiceFuel.setValue(vehicle.getFuelType());
    txtEconomy.setText(vehicle.getEconomy());
    imageName = vehicle.getImage();
    if (imageName != null && !imageName.isEmpty()) {
      String fullImagePath = "/images and attribution/" + imageName;
      vehicleImageView.setImage(new Image(fullImagePath));
      isDefaultImage = false;
    } else {
      vehicleImageView.setImage(new Image("/images and attribution/importcar.png"));
      isDefaultImage = true;
    }
  }

  private Vehicle createVehicleFromFields() {
    String brand = txtBrand.getText();
    String model = txtModel.getText();
    String dailyRate = txtDailyRate.getText();
    String regNo = txtRegNumber.getText();
    String year = txtYear.getText();
    String colour = txtColour.getText();
    String fuel = choiceFuel.getValue();
    String econ = txtEconomy.getText();

    BigDecimal pricePerDay = new BigDecimal(dailyRate).setScale(2, RoundingMode.HALF_UP);

    return new Vehicle(
        Integer.parseInt(year),
        model,
        regNo,
        pricePerDay,
        brand,
        fuel,
        colour,
        imageName,
        econ);
  }

  private void storeOrUpdateVehicle(Vehicle vehicle) {
    if (vehicle.getVehicleId() == 0) {
      vehicledb.saveVehicle(vehicle);
      AlertManager.showAlert(AlertType.CONFIRMATION, "", "Vehicle Added Successfully");
    } else {
      vehicledb.updateVehicle(vehicle);
      AlertManager.showAlert(AlertType.CONFIRMATION, "", "Vehicle Updated Successfully");
    }
    clearTextFields();
    showVehiclePane(false);
  }
}
