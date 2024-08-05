package ca.ucalgary.edu.ensf380;

import java.util.List;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

/**
 * The TrainMap class represents a map of trains and their associated stations.
 * It provides methods to initialize trains, display them, and select a specific train.
 */
public class TrainMap {
    private List<Train> trains = new ArrayList<>();
    private Map<Integer, Station> stations;

    /**
     * Constructs a TrainMap object with the specified stations.
     *
     * @param stations a map of station numbers to Station objects
     */
    public TrainMap(Map<Integer, Station> stations) {
        this.stations = stations;
        initializeTrains();
    }

    /**
     * Initializes the trains and assigns them to stations.
     * Creates 12 trains and assigns each train to a station in a cyclic manner.
     */
    private void initializeTrains() {
        for (int i = 1; i <= 12; i++) {
            int stationNumber = (i % stations.size()) + 1;
            trains.add(new Train(i, "forward", stations.get(stationNumber)));
        }
    }

    /**
     * Displays the list of trains and their details to the console.
     */
    public void displayTrains() {
        for (Train train : trains) {
            System.out.println(train);
        }
    }

    /**
     * Returns the list of trains.
     *
     * @return a list of Train objects
     */
    public List<Train> getTrains() {
        return trains;
    }

    /**
     * Prompts the user to enter a train number and returns the corresponding Train object.
     *
     * @return the selected Train object, or null if the train number is not found
     */
    public Train selectTrain() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the train number you are on: ");
        int trainNumber = scanner.nextInt();
        for (Train train : trains) {
            if (train.getId() == trainNumber) {
                return train;
            }
        }
        return null;
    }

    /**
     * The main method that initializes the TrainMap and allows the user to select a train.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        try {
            TrainMapParser parser = new TrainMapParser("/mnt/data/Map.csv");
            Map<Integer, Station> stations = parser.getStations();
            TrainMap trainMap = new TrainMap(stations);
            trainMap.displayTrains();
            Train selectedTrain = trainMap.selectTrain();
            if (selectedTrain != null) {
                System.out.println("You are on: " + selectedTrain);
            } else {
                System.out.println("Train not found.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
