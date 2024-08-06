# Final-Project

# Subway Screen Panel 

## Overview
This project includes a suite of Java classes for managing train information, displaying weather reports, and selecting trains. It is designed to be part of a larger application that tracks train positions and provides real-time weather updates.

## Setup and Usage

### Prerequisites
- Java Development Kit (JDK) 8 or higher
- Internet connection for fetching weather data


### Installation

1. **Clone the Repository:**
    ```bash
    git clone https://github.com/your-repo/train-management-weather-reporting.git
    ```

2. **Navigate to the Project Directory:**
    ```bash
    cd train-management-weather-reporting
    ```

3. **Compile the Code:**
    ```bash
    javac -d bin src/ca/ucalgary/edu/ensf380/*.java
    ```

4. **Run the Application:**
    ```bash
    java -cp bin ca.ucalgary.edu.ensf380.Main
    ```

### Database Initialization

If you want to use a database for storing and retrieving image data, follow these steps to initialize the database:

1. **Set Up the Database:**
    - Install a database system (e.g., MySQL, PostgreSQL, SQLite).
    - Create a new database schema for the project.

2. **Create Tables:**
    - Define tables for storing images. Example SQL schema:
    ```sql
    CREATE TABLE Images (
        id INT AUTO_INCREMENT PRIMARY KEY,
        title VARCHAR(100),
        description TEXT,
        media BLOB
    );
    ```

3. **Configure Database Connection:**
    - Update your Java application to connect to the database.
    - Add JDBC driver dependencies to your project and configure database connection settings in your code.

4. **Populate the Database:**
    - Import data into the database. You can use SQL INSERT statements or a database management tool to import image data.

5. **Verify the Database Setup:**
    - Ensure that the data is correctly imported and accessible. You can run queries to check the contents of the tables.

### Running Individual Components

- **TrainMap:**
    ```bash
    java -cp bin ca.ucalgary.edu.ensf380.TrainMap
    ```

- **TrainMapParser:**
    ```bash
    java -cp bin ca.ucalgary.edu.ensf380.TrainMapParser
    ```

- **TrainPanel:**
    ```bash
    java -cp bin ca.ucalgary.edu.ensf380.TrainPanel
    ```

- **TrainSelectionDialog:**
    ```bash
    java -cp bin ca.ucalgary.edu.ensf380.TrainSelectionDialog
    ```

- **WeatherPanel:**
    ```bash
    java -cp bin ca.ucalgary.edu.ensf380.WeatherPanel
    ```

### CSV File Format
The CSV file for station data should have the following columns:
- Row: The row number in the station map.
- Line Code: The code for the train line.
- Station Number: The unique number of the station.
- Station Code: The code representing the station.
- Station Name: The name of the station.
- X Coordinate: The X coordinate of the station on the map.
- Y Coordinate: The Y coordinate of the station on the map.
- Common Stations: List of common stations (optional).

## License
This project is licensed under the MIT License. See the LICENSE file for details.
