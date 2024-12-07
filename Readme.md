
# Car Rental Management System

## Project Overview
The Car Rental Management System is a robust JavaFX application tailored to simplify the management of car rental operations. It provides an intuitive interface for handling bookings, processing payments, managing vehicle inventory, and facilitating customer interactions. The system is designed with modularity in mind, offering various insurance strategies to cater to different customer needs. With its comprehensive set of features, this application aims to streamline the operations of car rental businesses, enhancing both efficiency and user experience.

## Prerequisites
- **Java**: JDK 11 or later must be installed to compile and run the application.
- **Maven**: Required for managing the project's dependencies and for building the application.
- **MySQL**: A MySQL server is necessary as the backend database to store all the operational data.

## Installation
1. **Download and Extract the ZIP File**: Download the ZIP file and extract its contents to your desired location. This will be your working directory.
2. **Database Setup**:
   - Ensure that MySQL is installed and actively running on your system.
   - Run the SQL setup script located in `project_assets/create_table.sql` to create the necessary tables for the application.
3. **Configure Database Settings**:
   - Navigate to `src/main/resources/database.properties`.
   - Update the file with your MySQL credentials to establish a connection:
     ```
     db.url=jdbc:mysql://localhost:3306/car_rental_db
     db.user=your_mysql_username
     db.password=your_mysql_password
     ```

## Running the Application
To run the application, follow these steps:
1. Open a terminal and navigate to the project directory.
2. Execute the command below to install dependencies and build the project:
   ```bash
   mvn clean install
   ```
3. Launch the application by running:
   ```bash
   mvn clean javafx:run
   ```

## Features
- **User Management**: Administer customer and administrative logins, managing roles and access rights.
- **Vehicle Management**: Add, edit, and remove vehicles from the inventory with detailed descriptions and statuses.
- **Booking System**: Comprehensive management of rental bookings, including scheduling, availability checks, and customization options.
- **Payment System**: Supports multiple payment methods and integrates different payment strategies for ease of transactions.

## Contributors
- **Ayush Kiledar**: Developer
- **Uttkarsh Bharadia**: Developer
- **Venkata Sharath Chandra**: Developer


