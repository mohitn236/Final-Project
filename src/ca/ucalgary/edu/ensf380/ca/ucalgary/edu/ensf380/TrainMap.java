package ca.ucalgary.edu.ensf380;

import java.util.List;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

public class TrainMap {
    private List<Train> trains = new ArrayList<>();
    private Map<Integer, Station> stations;

    public TrainMap(Map<Integer, Station> stations) {
        this.stations = stations;
        initializeTrains();
    }

    private void initializeTrains() {
        for (int i = 1; i <= 12; i++) {
            int stationNumber = (i % stations.size()) + 1;
            trains.add(new Train(i, "forward", stations.get(stationNumber)));
        }
    }

    public void displayTrains() {
        for (Train train : trains) {
            System.out.println(train);
        }
    }

    public List<Train> getTrains() {
        return trains;
    }

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
