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

/**
 * MainDisplay is the main JFrame for displaying city information including advertisements, news, train information, and weather updates.
 */
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

    /**
     * Constructs the MainDisplay frame and initializes it with advertisements, train information, news ticker, and weather updates.
     *
     * @param newsKeyword The keyword for fetching news
     * @param city The city for fetching weather information
     */
    public MainDisplay(String newsKeyword, String city) {
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

        // Replace clock panel with AnalogClockPanel
        AnalogClockPanel analogClockPanel = new AnalogClockPanel();
        clockPanel.add(analogClockPanel);
        clockPanel.setBackground(Color.GRAY); // Placeholder for clock panel

        topRightPanel.add(clockPanel);
        topRightPanel.add(rightPanel);

        topPanel.add(advertisementPanel, BorderLayout.CENTER);
        topPanel.add(topRightPanel, BorderLayout.EAST);

        add(topPanel, BorderLayout.NORTH);
        add(newsTickerPanel, BorderLayout.CENTER);
        add(trainPanel, BorderLayout.SOUTH);

        // Timer to update the train station information
        javax.swing.Timer trainTimer = new javax.swing.Timer(10000, e -> updateTrainStation());
        trainTimer.start();

        // Fetch and display weather information for the city
        updateWeatherInfo(city);

        setVisible(true);
    }

    /**
     * Updates the weather information displayed on the weatherLabel.
     *
     * @param city The city for which the weather information is fetched
     */
    private void updateWeatherInfo(String city) {
        new Thread(() -> {
            try {
                String weatherReport = weatherPanel.fetchWeatherReport(city);
                SwingUtilities.invokeLater(() -> weatherLabel.setText("<html>" + weatherReport.replace("\n", "<br>") + "</html>"));
            } catch (Exception e) {
                e.printStackTrace();
                SwingUtilities.invokeLater(() -> weatherLabel.setText("Weather information could not be loaded"));
            }
        }).start();
    }

    /**
     * Creates a connection to the PostgreSQL database.
     *
     * @return The database connection, or null if connection fails
     */
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

    /**
     * Reads the station data from a CSV file and returns a list of Station objects.
     *
     * @param filePath The path to the CSV file
     * @return A list of Station objects
     */
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

    /**
     * Updates the current station index in the TrainPanel.
     */
    private void updateTrainStation() {
        currentStationIndex = (currentStationIndex + 1) % stations.size();
        trainPanel.setCurrentStationIndex(currentStationIndex);
    }

    /**
     * Main method to run the application.
     *
     * @param args Command-line arguments for news keyword and city
     */
    public static void main(String[] args) {
        String newsKeyword = args.length > 0 ? args[0] : "calgary"; // Default to Calgary if no news keyword is provided
        String city = args.length > 1 ? args[1] : "Calgary"; // Default to Calgary if no city is provided
        SwingUtilities.invokeLater(() -> new MainDisplay(newsKeyword, city));
    }
}
