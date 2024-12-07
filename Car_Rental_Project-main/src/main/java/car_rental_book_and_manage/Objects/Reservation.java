package car_rental_book_and_manage.Objects;

import java.time.LocalDate;

public class Reservation {

  private int reservationId;
  private int clientId;
  private int vehicleId;
  private double totalRate;
  private String licensePlate;
  private String licenseNo;
  private LocalDate startDate;
  private LocalDate returnDate;
  private String insuranceType;

  public Reservation() {}

  public Reservation(
          int clientId,
          int vehicleId,
          double totalRate,
          String licensePlate,
          String licenseNo,
          LocalDate startDate,
          LocalDate returnDate,
          String insuranceType) {
    this.clientId = clientId;
    this.vehicleId = vehicleId;
    this.totalRate = totalRate;
    this.licensePlate = licensePlate;
    this.licenseNo = licenseNo;
    this.startDate = startDate;
    this.returnDate = returnDate;
    this.insuranceType = insuranceType;
  }

  public int getReservationId() {
    return reservationId;
  }

  public void setReservationId(int reservationId) {
    this.reservationId = reservationId;
  }

  public int getClientId() {
    return clientId;
  }

  public void setClientId(int clientId) {
    this.clientId = clientId;
  }

  public int getVehicleId() {
    return vehicleId;
  }

  public void setVehicleId(int vehicleId) {
    this.vehicleId = vehicleId;
  }

  public double getTotalRate() {
    return totalRate;
  }

  public void setTotalRate(double totalRate) {
    this.totalRate = totalRate;
  }

  public String getLicensePlate() {
    return licensePlate;
  }

  public void setLicensePlate(String licensePlate) {
    this.licensePlate = licensePlate;
  }

  public String getLicenseNo() {
    return licenseNo;
  }

  public void setLicenseNo(String licenseNo) {
    this.licenseNo = licenseNo;
  }

  public LocalDate getStartDate() {
    return startDate;
  }

  public void setStartDate(LocalDate startDate) {
    this.startDate = startDate;
  }

  public LocalDate getReturnDate() {
    return returnDate;
  }

  public void setReturnDate(LocalDate returnDate) {
    this.returnDate = returnDate;
  }

  public String getInsuranceType() {
    return insuranceType;
  }

  public void setInsuranceType(String insuranceType) {
    this.insuranceType = insuranceType;
  }
}
