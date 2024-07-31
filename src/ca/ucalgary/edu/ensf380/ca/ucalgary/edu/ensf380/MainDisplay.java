
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
    private static final long serialVersionUID = 1L;
    private AdvertisementPanel advertisementPanel;
    private TrainPanel trainPanel;
    private NewsTickerPanel newsTickerPanel;
    private List<Station> stations;
    private int currentStationIndex = 0;
    private JPanel rightPanel;
    private JLabel weatherLabel; // Label to display weather info
    private WeatherPanel weatherPanel;

    public MainDisplay(String newsKeyword) {
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

        stations = readStationsFromCSV("src/map/Map.csv");

        trainPanel = new TrainPanel(stations);
        newsTickerPanel = new NewsTickerPanel(newsKeyword);

        JPanel topPanel = new JPanel(new BorderLayout());
        JPanel topRightPanel = new JPanel(new GridLayout(2, 1));
        JPanel clockPanel = new JPanel();

        rightPanel = new JPanel(new BorderLayout());
        rightPanel.setPreferredSize(new Dimension(300, 0)); // Set a fixed width for the right panel
        
        weatherPanel = new WeatherPanel();
        weatherLabel = new JLabel("Weather Information", JLabel.CENTER);
        weatherLabel.setFont(new Font("Arial", Font.BOLD, 16));
        rightPanel.add(weatherLabel, BorderLayout.NORTH);

        // Create a panel to hold the clock information
        clockPanel.setBackground(Color.GRAY); // Placeholder for clock panel

        topRightPanel.add(clockPanel);
        topRightPanel.add(rightPanel);

        topPanel.add(advertisementPanel, BorderLayout.CENTER);
        topPanel.add(topRightPanel, BorderLayout.EAST);

        add(topPanel, BorderLayout.NORTH);
        add(newsTickerPanel, BorderLayout.CENTER);
        add(trainPanel, BorderLayout.SOUTH);

        // Use javax.swing.Timer instead of java.util.Timer
        javax.swing.Timer timer = new javax.swing.Timer(10000, e -> updateTrainStation());
        timer.start();

        // Fetch and display weather information
        updateWeatherInfo();

        setVisible(true);
    }

    private void updateWeatherInfo() {
        new Thread(() -> {
            try {
                String weatherReport = weatherPanel.fetchWeatherReport("Regina"); // Modify location as needed
                SwingUtilities.invokeLater(() -> weatherLabel.setText("<html>" + weatherReport.replace("\n", "<br>") + "</html>"));
            } catch (Exception e) {
                e.printStackTrace();
                SwingUtilities.invokeLater(() -> weatherLabel.setText("Weather information could not be loaded"));
            }
        }).start();
    }

    private Connection createDatabaseConnection() {
        try {
            Class.forName("org.postgresql.Driver");
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
                    firstLine = false;
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

    private void updateTrainStation() {
        currentStationIndex = (currentStationIndex + 1) % stations.size();
        trainPanel.setCurrentStationIndex(currentStationIndex);
    }

    public static void main(String[] args) {
        String newsKeyword = args.length > 0 ? args[0] : "calgary";
        SwingUtilities.invokeLater(() -> new MainDisplay(newsKeyword));
    }
}

