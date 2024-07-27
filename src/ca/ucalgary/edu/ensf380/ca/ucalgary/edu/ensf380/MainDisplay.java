//package ca.ucalgary.edu.ensf380;
//
//import javax.swing.*;
//import java.awt.*;
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.SQLException;
//
//public class MainDisplay extends JFrame {
//    private AdvertisementPanel advertisementPanel;
//    //private TrainPanel trainPanel;
//    //private WeatherPanel weatherPanel;
//    private NewsTickerPanel newsTickerPanel;
//
//    public MainDisplay() {
//        setTitle("City Information Display");
//        setSize(1280, 720);
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        setLayout(new BorderLayout());
//
//        Connection connection = createDatabaseConnection();
//
//        if (connection != null) {
//            advertisementPanel = new AdvertisementPanel(connection);
//        } else {
//            advertisementPanel = new AdvertisementPanel(null);
//        }
//
//        //trainPanel = new TrainPanel();
//        //weatherPanel = new WeatherPanel();
//        newsTickerPanel = new NewsTickerPanel();
//
//        JPanel topPanel = new JPanel(new GridLayout(1, 3));
//        topPanel.add(advertisementPanel);
//        //topPanel.add(weatherPanel);
//        topPanel.add(new JPanel());  // Placeholder for clock or other info
//
//        add(topPanel, BorderLayout.CENTER);
//        //add(trainPanel, BorderLayout.SOUTH);
//        add(newsTickerPanel, BorderLayout.NORTH);
//
//        setVisible(true);
//    }
//
//    private Connection createDatabaseConnection() {
//        try {
//            // Load the PostgreSQL JDBC driver
//            Class.forName("org.postgresql.Driver");
//            // Establish the connection
//            String url = "jdbc:postgresql://localhost:5432/advertisements";
//            String username = "oop";
//            String password = "ucalgary";
//            return DriverManager.getConnection(url, username, password);
//        } catch (ClassNotFoundException e) {
//            System.err.println("PostgreSQL JDBC Driver not found.");
//            e.printStackTrace();
//        } catch (SQLException e) {
//            System.err.println("Connection to PostgreSQL failed.");
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(() -> new MainDisplay());
//    }
//}
package ca.ucalgary.edu.ensf380;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MainDisplay extends JFrame {
    private AdvertisementPanel advertisementPanel;
    //private TrainPanel trainPanel;
    //private WeatherPanel weatherPanel;
    private NewsTickerPanel newsTickerPanel;

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

        //trainPanel = new TrainPanel();
        //weatherPanel = new WeatherPanel();
        newsTickerPanel = new NewsTickerPanel();

        JPanel topPanel = new JPanel(new GridLayout(1, 3));
        topPanel.add(advertisementPanel);
        //topPanel.add(weatherPanel);
        topPanel.add(new JPanel());  // Placeholder for clock or other info

        add(topPanel, BorderLayout.CENTER);
        //add(trainPanel, BorderLayout.SOUTH);
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainDisplay());
    }
}

