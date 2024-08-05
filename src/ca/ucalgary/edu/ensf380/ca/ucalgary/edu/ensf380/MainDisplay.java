package ca.ucalgary.edu.ensf380;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainDisplay extends JFrame implements ActionListener {
    private static final long serialVersionUID = 1L;
    private AdvertisementPanel advertisementPanel;
    private TrainPanel trainPanel;
    private NewsTickerPanel newsTickerPanel;
    private List<Station> stations;
    private int currentStationIndex = 0;
    private JPanel rightPanel;
    private JLabel weatherLabel; 
    private WeatherPanel weatherPanel;
    private JButton startButton;
    private JButton stopButton;
    private JTextArea outputArea;
    private Process process;
    private ExecutorService executor = Executors.newFixedThreadPool(2);

    public MainDisplay(String newsKeyword, String city, Integer trainNumber) {
        setTitle("City Information Display");
        setSize(1280, 720);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        Connection connection = createDatabaseConnection();
        advertisementPanel = new AdvertisementPanel(connection);

        stations = readStationsFromCSV("src/map/Map.csv");

        trainPanel = new TrainPanel(stations);
        if (trainNumber != null) {
            trainPanel.selectTrain(trainNumber);
        }
        newsTickerPanel = new NewsTickerPanel(newsKeyword);

        JPanel topPanel = new JPanel(new BorderLayout());
        JPanel topRightPanel = new JPanel(new GridLayout(2, 1));
        JPanel clockPanel = new JPanel();

        rightPanel = new JPanel(new BorderLayout());
        rightPanel.setPreferredSize(new Dimension(300, 0));

        weatherPanel = new WeatherPanel();
        weatherLabel = new JLabel("Weather Information", JLabel.CENTER);
        weatherLabel.setFont(new Font("Arial", Font.BOLD, 16));
        rightPanel.add(weatherLabel, BorderLayout.NORTH);

        AnalogClockPanel analogClockPanel = new AnalogClockPanel();
        clockPanel.add(analogClockPanel);
        clockPanel.setBackground(Color.GRAY);

        topRightPanel.add(clockPanel);
        topRightPanel.add(rightPanel);

        topPanel.add(advertisementPanel, BorderLayout.CENTER);
        topPanel.add(topRightPanel, BorderLayout.EAST);

        add(topPanel, BorderLayout.NORTH);
        add(newsTickerPanel, BorderLayout.CENTER);
        add(trainPanel, BorderLayout.SOUTH);

        JPanel processPanel = new JPanel(new BorderLayout());
        startButton = new JButton("Start Process");
        stopButton = new JButton("Stop Process");
        outputArea = new JTextArea();
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);

        startButton.addActionListener(this);
        stopButton.addActionListener(this);

        processPanel.add(startButton, BorderLayout.NORTH);
        processPanel.add(stopButton, BorderLayout.CENTER);
        processPanel.add(scrollPane, BorderLayout.SOUTH);

        add(processPanel, BorderLayout.EAST);

        javax.swing.Timer trainTimer = new javax.swing.Timer(10000, e -> updateTrainStation());
        trainTimer.start();

        updateWeatherInfo(city);

        setVisible(true);
    }

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
        trainPanel.setcurrentTrainNumber(currentStationIndex);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == startButton) {
            startProcess();
        } else if (e.getSource() == stopButton) {
            stopProcess();
        }
    }

    private void startProcess() {
        if (process == null) {
            try {
                ProcessBuilder builder = new ProcessBuilder("java", "-jar", "./exe/SubwaySimulator.jar", "--in", "./data/subway.csv", "--out", "./out");
                builder.redirectErrorStream(true);
                process = builder.start();

                executor.execute(() -> handleProcessOutput());

                executor.execute(() -> {
                    try {
                        process.waitFor();
                        process = null;
                        SwingUtilities.invokeLater(() -> {
                            stopButton.setEnabled(false);
                            startButton.setEnabled(true);
                        });
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                });

                stopButton.setEnabled(true);
                startButton.setEnabled(false);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void handleProcessOutput() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                final String currentLine = line; // Effectively final
                SwingUtilities.invokeLater(() -> outputArea.append(currentLine + "\n"));
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


    private void stopProcess() {
        if (process != null) {
            process.destroy();
            process = null;
            stopButton.setEnabled(false);
            startButton.setEnabled(true);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame dummyFrame = new JFrame(); // Dummy frame to use as parent for the dialog
            dummyFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            // Show train selection dialog
            Integer trainNumber = TrainSelectionDialog.selectTrain(dummyFrame);

            // Show main application window
            String newsKeyword = args.length > 0 ? args[0] : "calgary";
            String city = args.length > 1 ? args[1] : "Calgary";
            
            new MainDisplay(newsKeyword, city, trainNumber);
        });
    }
}
