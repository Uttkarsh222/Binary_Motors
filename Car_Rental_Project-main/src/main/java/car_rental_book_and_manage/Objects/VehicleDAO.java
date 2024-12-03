package car_rental_book_and_manage.Objects;

public interface VehicleDAO {

  void saveVehicle(Vehicle vehicle);

  void updateVehicle(Vehicle vehicle);

  void deleteVehicle(Vehicle vehicle);

  boolean doesRegistrationNoExist(String regNo);

  void retrieveAllVehicles();

  int getNumOfVehicles();

  void retrieveVehicleByIdToUpdate(int vehicleId);

  void retrieveLatestVehicleToSave();

  void setVehicleAvailability(int vehicleId, boolean availability);

  Vehicle getVehicleById(int vehicleId);
}
