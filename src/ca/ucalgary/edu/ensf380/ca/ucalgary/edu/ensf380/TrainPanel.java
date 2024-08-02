package ca.ucalgary.edu.ensf380;

import org.apache.batik.swing.JSVGCanvas;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

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

/**
 * TrainPanel displays train information and updates train positions on a map.
 */
public class TrainPanel extends JPanel {
    private static final long serialVersionUID = 1L;
    private JLabel trainLabel;
    private JSVGCanvas svgCanvas;
    private List<Station> stations;
    private List<Train> trains;
    private int currentStationIndex = 10;

    /**
     * Constructs a TrainPanel with a list of stations and initializes the panel.
     *
     * @param stations The list of stations to be used
     */
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
//        initializeSVGCanvas();
        updateTrainInfo();
        startTrainSimulation();
    }
    
    /**
     * Constructs a TrainPanel with an initial station index.
     *
     * @param initialStationIndex The initial station index
     */
    public TrainPanel(int initialStationIndex) {
        this.currentStationIndex = initialStationIndex;
        // Other initialization code...
    }
    
    /**
     * Gets the current station index.
     *
     * @return The current station index
     */
    public int getCurrentStationIndex() {
        return currentStationIndex;
    }

    /**
     * Loads station data from a CSV file.
     *
     * @param filePath The path to the CSV file
     */
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

    /**
     * Initializes train objects and assigns them to stations.
     */
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

//    /**
//     * Initializes the SVG canvas for displaying the map.
//     */
//    private void initializeSVGCanvas() {
//        svgCanvas = new JSVGCanvas();
//        svgCanvas.setPreferredSize(new Dimension(800, 600));
//        add(svgCanvas, BorderLayout.CENTER);
//        loadSVG("src/map/Trains.svg");
//    }

//    /**
//     * Loads an SVG file into the SVG canvas.
//     *
//     * @param filePath The path to the SVG file
//     */
//    private void loadSVG(String filePath) {
//        try {
//            svgCanvas.setURI(new java.io.File(filePath).toURI().toString());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    /**
     * Starts the simulation of train movement and updates the display at regular intervals.
     */
    private void startTrainSimulation() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                simulateTrainMovement();
                updateTrainInfo();
                updateTrainPositionsOnSVG();
                outputTrainPositions();
            }
        }, 0, 15000); // 15 seconds interval
    }

    /**
     * Simulates the movement of trains between stations.
     */
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

    /**
     * Updates the train information displayed on the panel.
     */
    private void updateTrainInfo() {
        StringBuilder info = new StringBuilder("<html>");
        
        // Ensure we have at least 5 stations to show
        if (stations.size() > 0) {
            int previousIndex = (currentStationIndex - 1 + stations.size()) % stations.size();
            info.append("Previous Station: ").append(stations.get(previousIndex).getStationName()).append("<br>");

            info.append("Current Station: ").append(stations.get(currentStationIndex).getStationName()).append("<br>");
            
            for (int i = 1; i <= 4; i++) {
                int nextIndex = (currentStationIndex + i) % stations.size();
                info.append("Next Station ").append(i).append(": ").append(stations.get(nextIndex).getStationName()).append("->");
            }
        } else {
            info.append("No stations available.");
        }
        
        info.append("</html>");
        trainLabel.setText(info.toString());
    }

    /**
     * Updates the positions of trains on the SVG map.
     */
    private void updateTrainPositionsOnSVG() {
        Document document = svgCanvas.getSVGDocument();

        if (document != null) {
            for (Train train : trains) {
                for (Station station : stations) {
                    if (station.getTrain() == train) {
                        Element trainElement = document.getElementById("train-" + train.getId());

                        if (trainElement != null) {
                            double x = station.getX();
                            double y = station.getY();
                            trainElement.setAttribute("x", String.valueOf(x));
                            trainElement.setAttribute("y", String.valueOf(y));
                        }
                    }
                }
            }
        }
    }

    /**
     * Outputs the current positions of trains to a text file.
     */
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

    /**
     * Sets the current station index and updates the display.
     *
     * @param index The new index of the current station
     */
    public void setCurrentStationIndex(int index) {
        if (index >= 0 && index < stations.size()) {
            stations.get(currentStationIndex).setCurrentTrainLocation(false);
            currentStationIndex = index;
            stations.get(currentStationIndex).setCurrentTrainLocation(true);
            updateTrainInfo();
        }
    }

    /**
     * Gets the list of stations.
     *
     * @return The list of stations
     */
    public List<Station> getStations() {
        return stations;
    }

    /**
     * Updates the current train location based on the provided station index.
     *
     * @param stationIndex The index of the station to set as the current train location
     */
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
