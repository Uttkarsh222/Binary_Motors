package car_rental_book_and_manage.Objects;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Vehicle {

    private String model, licensePlate, brand, fuelType, colour, imagePath, economy;
    private BigDecimal pricePerDay;
    private int vehicleId, makeYear;
    private boolean availability;

    public Vehicle() {}

    public Vehicle(
            int vehicleId,
            int makeYear,
            String model,
            String licensePlate,
            BigDecimal pricePerDay,
            String brand,
            String fuelType,
            String colour,
            String imagePath,
            String economy) {
        this.vehicleId = vehicleId;
        this.model = model;
        this.makeYear = makeYear;
        this.licensePlate = licensePlate;
        this.pricePerDay = pricePerDay.setScale(2, RoundingMode.HALF_UP);
        this.brand = brand;
        this.colour = colour;
        this.fuelType = fuelType;
        this.imagePath = imagePath;
        this.economy = economy;
    }

    public Vehicle(
            int makeYear,
            String model,
            String licensePlate,
            BigDecimal pricePerDay,
            String brand,
            String fuelType,
            String colour,
            String imagePath,
            String economy) {
        this.model = model;
        this.makeYear = makeYear;
        this.licensePlate = licensePlate;
        this.pricePerDay = pricePerDay.setScale(2, RoundingMode.HALF_UP);
        this.brand = brand;
        this.colour = colour;
        this.fuelType = fuelType;
        this.imagePath = imagePath;
        this.economy = economy;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public BigDecimal getPricePerDay() {
        return pricePerDay;
    }

    public void setPricePerDay(BigDecimal pricePerDay) {
        this.pricePerDay = pricePerDay.setScale(2, RoundingMode.HALF_UP);
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(int vehicleId) {
        this.vehicleId = vehicleId;
    }

    public int getMakeYear() {
        return makeYear;
    }

    public void setMakeYear(int makeYear) {
        this.makeYear = makeYear;
    }

    public String getFuelType() {
        return fuelType;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public boolean getAvailability() {
        return availability;
    }

    public void setAvailability(boolean availability) {
        this.availability = availability;
    }

    public String getImage() {
        return imagePath;
    }

    public void setImage(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getEconomy() {
        return economy;
    }

    public void setEconomy(String economy) {
        this.economy = economy;
    }
}
