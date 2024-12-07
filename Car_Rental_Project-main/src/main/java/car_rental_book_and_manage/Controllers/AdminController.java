package car_rental_book_and_manage.Controllers;

import car_rental_book_and_manage.Objects.Vehicle;
import car_rental_book_and_manage.Utility.SceneManager;
import car_rental_book_and_manage.Utility.SceneManager.Scenes;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;

public class AdminController extends Controller {

  @FXML private Label customerLabel;
  @FXML private Label vehicleLabel;
  @FXML private Label earningsLabel;
  @FXML private PieChart piechart;

  public void initialize() {
    SceneManager.setController(Scenes.ADMIN, this);
    AdminBindingSetup();
    AdminUpdateDataModel();
    AdminInitializePieChart();
    AdminAddVehicleListChangeListener();
  }

  private void AdminBindingSetup() {
    vehicleLabel.textProperty().bind(dataModel.numOfVehiclesProperty());
    customerLabel.textProperty().bind(dataModel.numOfClientsProperty());
    earningsLabel.textProperty().bind(dataModel.totalEarningsProperty());
  }

  private void AdminUpdateDataModel() {
    dataModel.setNumOfVehicles(String.valueOf(vehicledb.getNumOfVehicles()));
    dataModel.setNumOfClients(String.valueOf(clientdb.getNumOfClients()));
  }

  private void AdminInitializePieChart() {
    updatePieChart();
  }

  private void AdminAddVehicleListChangeListener() {
    dataModel
        .getVehicleList()
        .addListener((ListChangeListener<? super Vehicle>) change -> updatePieChart());
  }

  private void updatePieChart() {
    int availableCars = dataModel.getNumOfAvailableCars();
    int bookedCars = dataModel.getNumOfBookedCars();

    PieChart.Data availableCarsData = new PieChart.Data("Available Cars", availableCars);
    PieChart.Data bookedCarsData = new PieChart.Data("Booked Cars", bookedCars);

    piechart.getData().clear();
    piechart.getData().addAll(availableCarsData, bookedCarsData);
  }
}
