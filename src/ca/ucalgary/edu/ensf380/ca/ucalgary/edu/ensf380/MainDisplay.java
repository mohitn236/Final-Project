package ca.ucalgary.edu.ensf380;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MainDisplay extends JFrame {
    private AdvertisementPanel advertisementPanel;
    private TrainPanel trainPanel;
    //private WeatherPanel weatherPanel;
    private NewsTickerPanel newsTickerPanel;
    private List<Station> stations;

    public MainDisplay() {
        setTitle("City Information Display");
        setSize(1280, 720);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        Connection connection = createDatabaseConnection();

        if (connection != null) {
            advertisementPanel = new AdvertisementPanel(connection);
        } else {
            advertisementPanel = new AdvertisementPanel(null);
        }

        stations = readStationsFromCSV("/Users/shivansh/Downloads/Map.csv");

        trainPanel = new TrainPanel(stations);
        //weatherPanel = new WeatherPanel();
        newsTickerPanel = new NewsTickerPanel();

        JPanel topPanel = new JPanel(new GridLayout(1, 3));
        topPanel.add(advertisementPanel);
        //topPanel.add(weatherPanel);
        topPanel.add(new JPanel());  // Placeholder for clock or other info

        add(topPanel, BorderLayout.CENTER);
        add(trainPanel, BorderLayout.SOUTH);
        add(newsTickerPanel, BorderLayout.NORTH);

        setVisible(true);
    }

    private Connection createDatabaseConnection() {
        try {
            // Load the PostgreSQL JDBC driver
            Class.forName("org.postgresql.Driver");
            // Establish the connection
            String url = "jdbc:postgresql://localhost:5432/advertisements";
            String username = "oop";
            String password = "ucalgary";
            return DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException e) {
            System.err.println("PostgreSQL JDBC Driver not found.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Connection to PostgreSQL failed.");
            e.printStackTrace();
        }
        return null;
    }

    private List<Station> readStationsFromCSV(String filePath) {
        List<Station> stations = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean firstLine = true;
            while ((line = br.readLine()) != null) {
                if (firstLine) {
                    firstLine = false; // Skip the header line
                    continue;
                }
                String[] values = line.split(",");
                if (values.length >= 7) {
                    try {
                        int row = Integer.parseInt(values[0].trim());
                        String lineCode = values[1].trim();
                        int stationNumber = Integer.parseInt(values[2].trim());
                        String stationCode = values[3].trim();
                        String stationName = values[4].trim();
                        double x = Double.parseDouble(values[5].trim());
                        double y = Double.parseDouble(values[6].trim());
                        String commonStations = values.length > 7 ? values[7].trim() : "";

                        Station station = new Station(row, lineCode, stationNumber, stationCode, stationName, x, y, commonStations);
                        stations.add(station);
                    } catch (NumberFormatException e) {
                        System.err.println("Skipping invalid line: " + line);
                    }
                } else {
                    System.err.println("Skipping invalid line: " + line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stations;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainDisplay());
    }
}
