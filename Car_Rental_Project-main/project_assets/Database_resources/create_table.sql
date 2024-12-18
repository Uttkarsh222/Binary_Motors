-- Rental Tables Creation

USE car_rental_db;

CREATE TABLE CLIENT(
    C_Id INT NOT NULL AUTO_INCREMENT,
    Fname VARCHAR(20) NOT NULL,
    Username VARCHAR(30) NOT NULL,
    Pword VARCHAR(60) NOT NULL,
    Phone_no VARCHAR(10) NOT NULL,
    License_no VARCHAR(8) NOT NULL,
    PRIMARY KEY (C_Id),
    UNIQUE (Username),
    UNIQUE (License_no)
);

CREATE TABLE VEHICLE(
    V_Id INT NOT NULL AUTO_INCREMENT,
    Brand VARCHAR(20) NOT NULL,
    Model VARCHAR(20) NOT NULL,
    Make_year YEAR NOT NULL,
    Reg_no VARCHAR(6) NOT NULL,
    Colour VARCHAR(20) NOT NULL,
    Fuel_option ENUM('Premium','Regular','Diesel') NOT NULL,
    Fuel_economy VARCHAR(3) NOT NULL,
    Daily_rate DECIMAL(10,2) NOT NULL,
    Availability BOOLEAN NOT NULL DEFAULT TRUE,
    image_path VARCHAR(255) NOT NULL,
    PRIMARY KEY (V_Id),
    UNIQUE (Reg_no)
);

CREATE TABLE RESERVATION(
    Rental_Id INT NOT NULL AUTO_INCREMENT,
    Client_Id INT NOT NULL,
    Vehicle_Id INT NOT NULL,
    Total_rate DECIMAL(10,2) NOT NULL,
    Vehicle_reg VARCHAR(6) NOT NULL,
    License_no VARCHAR(8) NOT NULL,
    Start_date DATE NOT NULL, 
    Return_date DATE NOT NULL,
    Insurance_type VARCHAR(20) NOT NULL,
    PRIMARY KEY (Rental_Id),
    FOREIGN KEY (Vehicle_Id) REFERENCES VEHICLE(V_Id),
    FOREIGN KEY (Client_Id) REFERENCES CLIENT(C_Id)
);

CREATE TABLE PAYMENT(
    Payment_Id INT NOT NULL AUTO_INCREMENT,
    Rental_Id INT NOT NULL,
    Client_Id INT NOT NULL,
    Payment_Date DATE NOT NULL,
    Amount DECIMAL(10,2) NOT NULL,
    Payment_Method ENUM('Credit Card', 'Debit Card') NOT NULL,
    PRIMARY KEY (Payment_Id),
    FOREIGN KEY (Rental_Id) REFERENCES RESERVATION(Rental_Id),
    FOREIGN KEY (Client_Id) REFERENCES CLIENT(C_Id)
);
