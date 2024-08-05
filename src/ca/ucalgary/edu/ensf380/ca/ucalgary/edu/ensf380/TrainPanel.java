
package ca.ucalgary.edu.ensf380;


import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * TrainPanel displays train information and updates train positions on a map.
 */
public class TrainPanel extends JPanel {
    private static final long serialVersionUID = 1L;
    private JLabel trainLabel;
    private List<Station> stations;
    private List<Train> trains;
    //private int currentStationIndex;
    private int currentTrainNumber = 1; // Default train number
    private Train selectedTrain;

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
        updateTrainInfo();
        startTrainSimulation();
    }
    
    public TrainPanel(Train selectedTrain) {
        this.selectedTrain = selectedTrain;
        setPreferredSize(new Dimension(400, 300));
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (selectedTrain != null) {
            g.drawString("Selected Train: " + selectedTrain, 10, 20);
        }
    }

    /**
     * Constructs a TrainPanel with an initial station index.
     *
     * @param initialStationIndex The initial station index
     * @param currentTrainNumber  The current train number
     */
    public TrainPanel(int currentTrainNumber) {
        this.currentTrainNumber = currentTrainNumber;
        this.stations = new ArrayList<>();
        initializeTrains();
        updateTrainInfo();
        startTrainSimulation();
    }

    /**
     * Gets the current station index.
     *
     * @return The current station index
     */
    public int getcurrentTrainNumber() {
        return currentTrainNumber;
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
            Train train = new Train(i + 1, direction);
            trains.add(train);
            stations.get(stationIndex).setTrain(train);
        }
    }

  

    /**
     * Starts the simulation of train movement and updates the display at regular intervals.
     */
    private void startTrainSimulation() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                //simulateTrainMovement();
                updateTrainInfo();
                //updateTrainPositionsOnSVG();
                outputTrainPositions();
            }
        }, 0, 15000); // 15 seconds interval
    }

    
    /**
     * Updates the train information displayed on the panel.
     */
    private void updateTrainInfo() {
        StringBuilder info = new StringBuilder("<html>");

        // Ensure we have at least 5 stations to show
        if (stations.size() > 0) {
            int previousIndex = (currentTrainNumber - 1 + stations.size()) % stations.size();
            info.append("Previous Station: ").append(stations.get(previousIndex).getStationName()).append("<br>");

            info.append("Current Station: ").append(stations.get(currentTrainNumber).getStationName()).append("<br>");

            for (int i = 1; i <= 4; i++) {
                int nextIndex = (currentTrainNumber + i) % stations.size();
                info.append("Next Station ").append(i).append(": ").append(stations.get(nextIndex).getStationName()).append("->");
            }
        } else {
            info.append("No stations available.");
        }

        info.append("</html>");
        trainLabel.setText(info.toString());
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
    public void setcurrentTrainNumber(int index) {
        if (index >= 0 && index < stations.size()) {
            stations.get(currentTrainNumber).setCurrentTrainLocation(false);
            currentTrainNumber = index;
            stations.get(currentTrainNumber).setCurrentTrainLocation(true);
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
    * Updates the current train location based on the selected train number.
     */
    public void updateCurrentTrainLocation() {
        for (Station station : stations) {
            if (station.hasTrain() && station.getTrain().getId() == currentTrainNumber) {
                setcurrentTrainNumber(stations.indexOf(station));
                break;
            }
        }
    }

    /**
     * Selects a train and updates the display with its information.
     *
     * @param trainNumber The number of the train to select
     */
    public void selectTrain(int trainNumber) {
        this.currentTrainNumber = trainNumber;
        updateCurrentTrainLocation();
        updateTrainInfo();
    }
    
    public static void main(String[] args) {
        try {
            TrainMapParser parser = new TrainMapParser("src/map/Map.csv");
            Map<Integer, Station> stations = parser.getStations();
            TrainMap trainMap = new TrainMap(stations);
            trainMap.displayTrains();
            Train selectedTrain = trainMap.selectTrain();
            if (selectedTrain != null) {
                System.out.println("You are on: " + selectedTrain);
                JFrame frame = new JFrame("TrainPanel");
                TrainPanel trainPanel = new TrainPanel(selectedTrain);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.add(trainPanel);
                frame.pack();
                frame.setVisible(true);
            } else {
                System.out.println("Train not found.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
    

