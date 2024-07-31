package ca.ucalgary.edu.ensf380;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class TrainPanel extends JPanel {
    private static final long serialVersionUID = 1L;
    private JLabel trainLabel;
    private List<Station> stations;
    private List<Train> trains;
    private int currentStationIndex = 0;

    public TrainPanel(List<Station> stations) {
        setBackground(new Color(0, 0, 128)); // dark blue color for the background
        setPreferredSize(new Dimension(800, 100));
        trainLabel = new JLabel("Train Information");
        trainLabel.setForeground(Color.WHITE);
        trainLabel.setFont(new Font("Verdana", Font.PLAIN, 14));
        add(trainLabel);

        this.stations = stations != null ? stations : new ArrayList<>();
        if (this.stations.isEmpty()) {
            loadStationsFromCSV("src/map/Map.csv");
        }
        initializeTrains();
        updateTrainInfo();
        startTrainSimulation();
    }

    private void loadStationsFromCSV(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine(); // Skip header line
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length < 7) {
                    System.err.println("Skipping invalid line: " + line);
                    continue;
                }
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
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initializeTrains() {
        trains = new ArrayList<>();
        int numberOfTrains = 12;
        int distanceBetweenTrains = 4;

        for (int i = 0; i < numberOfTrains; i++) {
            int stationIndex = (i * distanceBetweenTrains) % stations.size();
            String direction = (i % 2 == 0) ? "forward" : "backward";
            Train train = new Train(i + 1, direction, 1);
            trains.add(train);
            stations.get(stationIndex).setTrain(train);
        }
    }

    private void startTrainSimulation() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                simulateTrainMovement();
                updateTrainInfo();
                outputTrainPositions();
            }
        }, 0, 15000); // 15 seconds interval
    }

    private void simulateTrainMovement() {
        for (Train train : trains) {
            for (Station station : stations) {
                if (station.getTrain() == train) {
                    int currentIndex = stations.indexOf(station);
                    station.setTrain(null);
                    int nextIndex;
                    if ("forward".equals(train.getDirection())) {
                        nextIndex = (currentIndex + train.getSpeed()) % stations.size();
                    } else {
                        nextIndex = (currentIndex - train.getSpeed() + stations.size()) % stations.size();
                    }
                    stations.get(nextIndex).setTrain(train);
                    break;
                }
            }
        }
    }

    private void updateTrainInfo() {
        StringBuilder info = new StringBuilder("<html>");
        for (Station station : stations) {
            if (station.hasTrain()) {
                info.append("Train ").append(station.getTrain().getId()).append(" is at ").append(station.getStationName()).append("<br>");
            }
        }
        trainLabel.setText(info.toString());
    }

    private void outputTrainPositions() {
        try (FileWriter writer = new FileWriter("train_positions.txt")) {
            for (Station station : stations) {
                if (station.hasTrain()) {
                    writer.write("Train " + station.getTrain().getId() + " is at " + station.getStationName() + "\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setCurrentStationIndex(int index) {
        if (index >= 0 && index < stations.size()) {
            stations.get(currentStationIndex).setCurrentTrainLocation(false);
            currentStationIndex = index;
            stations.get(currentStationIndex).setCurrentTrainLocation(true);
            updateTrainInfo();
        }
    }

    public List<Station> getStations() {
        return stations;
    }

    public void updateTrainLocation(int stationIndex) {
        if (stationIndex >= 0 && stationIndex < stations.size()) {
            for (Station station : stations) {
                station.setCurrentTrainLocation(false);
            }
            currentStationIndex = stationIndex;
            stations.get(currentStationIndex).setCurrentTrainLocation(true);
            updateTrainInfo();
        }
    }
}
