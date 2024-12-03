package car_rental_book_and_manage.Objects;

import javafx.application.Platform;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import car_rental_book_and_manage.Utility.QuickSort;

public class DataModel {

  private static DataModel instance;

  private final StringProperty numOfClients = new SimpleStringProperty();
  private final StringProperty numOfVehicles = new SimpleStringProperty();
  private final StringProperty loggedInClientName = new SimpleStringProperty();
  private final StringProperty totalEarnings = new SimpleStringProperty("0.0");
  private final ObservableList<Client> obClientList =
      FXCollections.synchronizedObservableList(FXCollections.observableArrayList());
  private final ObservableList<Vehicle> obVehicleList =
      FXCollections.synchronizedObservableList(FXCollections.observableArrayList());
  private final ObservableList<Vehicle> availableVehicleList;
  private final ObservableList<Reservation> obReservationList =
      FXCollections.synchronizedObservableList(FXCollections.observableArrayList());

  private DataModel() {
    FilteredList<Vehicle> filteredList =
        new FilteredList<>(obVehicleList, Vehicle::getAvailability);
    availableVehicleList = FXCollections.synchronizedObservableList(filteredList);
  }

  public static synchronized DataModel getInstance() {
    if (instance == null) {
      instance = new DataModel();
    }
    return instance;
  }

  private void updateTotalEarnings() {
    Platform.runLater(
        () -> {
          double total = obReservationList.stream().mapToDouble(Reservation::getTotalRate).sum();
          totalEarnings.set(String.format("%.2f", total));
        });
  }


  public final ObservableList<Client> getClientList() {
    QuickSort.quickSort(obClientList, 0, obClientList.size()-1);
    return obClientList;
  }

  public void addClient(Client client) {
    Platform.runLater(() -> obClientList.add(client));
  }


  public void removeClient(Client client) {
    Platform.runLater(() -> obClientList.remove(client));
  }

  public void clearClients() {
    Platform.runLater(obClientList::clear);
  }

  public void updateClient(Client client) {
    Platform.runLater(
        () -> {
          for (Client c : obClientList) {
            if (c.getClientId() == client.getClientId()) {
              int index = obClientList.indexOf(c);
              obClientList.set(index, client);
              break;
            }
          }
        });
  }

  public Client getClient(int id) {
    return obClientList.stream().filter(c -> c.getClientId() == id).findFirst().orElse(null);
  }

  // Vehicle list methods
  public final ObservableList<Vehicle> getVehicleList() {
    return obVehicleList;
  }

  public final ObservableList<Vehicle> getAvailableVehicleList() {
    return availableVehicleList;
  }

  public void addVehicle(Vehicle vehicle) {
    Platform.runLater(() -> obVehicleList.add(vehicle));
  }

  public void removeVehicle(Vehicle vehicle) {
    Platform.runLater(() -> obVehicleList.remove(vehicle));
  }

  public void clearVehicles() {
    Platform.runLater(obVehicleList::clear);
  }

  public void updateVehicle(Vehicle vehicle) {
    Platform.runLater(
        () -> {
          for (Vehicle v : obVehicleList) {
            if (v.getVehicleId() == vehicle.getVehicleId()) {
              int index = obVehicleList.indexOf(v);
              obVehicleList.set(index, vehicle);
              break;
            }
          }
        });
  }

  public Vehicle getVehicle(int id) {
    return obVehicleList.stream().filter(v -> v.getVehicleId() == id).findFirst().orElse(null);
  }

  public final ObservableList<Reservation> getReservationList() {
    return obReservationList;
  }

  public void addReservation(Reservation reservation) {
    Platform.runLater(
        () -> {
          obReservationList.add(reservation);
          updateTotalEarnings();
        });
  }


  public void removeReservation(Reservation reservation) {
    Platform.runLater(
        () -> {
          obReservationList.remove(reservation);
          updateTotalEarnings();
        });
  }

  /** Clears all reservations from the reservation list. */
  public void clearReservations() {
    Platform.runLater(
        () -> {
          obReservationList.clear();
          updateTotalEarnings();
        });
  }

  public void updateReservation(Reservation reservation) {
    Platform.runLater(
        () -> {
          for (Reservation r : obReservationList) {
            if (r.getReservationId() == reservation.getReservationId()) {
              int index = obReservationList.indexOf(r);
              obReservationList.set(index, reservation);
              updateTotalEarnings();
              break;
            }
          }
        });
  }

  public Reservation getReservation(int id) {
    return obReservationList.stream()
        .filter(r -> r.getReservationId() == id)
        .findFirst()
        .orElse(null);
  }


  public final StringProperty numOfClientsProperty() {
    return numOfClients;
  }

  public final String getNumOfClients() {
    return numOfClients.get();
  }


  public final void setNumOfClients(String numOfClients) {
    this.numOfClients.set(numOfClients);
  }


  public final StringProperty numOfVehiclesProperty() {
    return numOfVehicles;
  }


  public final String getNumOfVehicles() {
    return numOfVehicles.get();
  }

  public final void setNumOfVehicles(String numOfVehicles) {
    this.numOfVehicles.set(numOfVehicles);
  }


  public final StringProperty loggedInClientName() {
    return loggedInClientName;
  }


  public final String getLoggedInClientName() {
    return loggedInClientName.get();
  }


  public final void setLoggedClientName(String name) {
    this.loggedInClientName.set(name);
  }


  public final StringProperty totalEarningsProperty() {
    return totalEarnings;
  }


  public final String getTotalEarnings() {
    return totalEarnings.get();
  }


  public int getNumOfAvailableCars() {
    return (int) obVehicleList.stream().filter(Vehicle::getAvailability).count();
  }


  public int getNumOfBookedCars() {
    return (int) obVehicleList.stream().filter(vehicle -> !vehicle.getAvailability()).count();
  }
}
