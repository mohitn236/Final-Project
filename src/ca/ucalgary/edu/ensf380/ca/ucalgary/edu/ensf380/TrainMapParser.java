package ca.ucalgary.edu.ensf380;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * The TrainMapParser class is responsible for parsing a CSV file containing station information
 * and creating a map of stations.
 */
public class TrainMapParser {
    private Map<Integer, Station> stations = new HashMap<>();

    /**
     * Constructs a TrainMapParser object and parses the CSV file at the specified file path.
     *
     * @param filePath the path to the CSV file containing station information
     * @throws IOException if an I/O error occurs while reading the file
     */
    public TrainMapParser(String filePath) throws IOException {
        parseCSV(filePath);
    }

    /**
     * Parses the CSV file at the specified file path and populates the map of stations.
     *
     * @param filePath the path to the CSV file containing station information
     * @throws IOException if an I/O error occurs while reading the file
     */
    private void parseCSV(String filePath) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(filePath));
        String line;
        br.readLine(); // skip header line
        while ((line = br.readLine()) != null) {
            String[] values = line.split(",");
            int row = Integer.parseInt(values[0]);
            String lineCode = values[1];
            int stationNumber = Integer.parseInt(values[2]);
            String stationCode = values[3];
            String stationName = values[4];
            double x = Double.parseDouble(values[5]);
            double y = Double.parseDouble(values[6]);
            stations.put(stationNumber, new Station(row, lineCode, stationNumber, stationCode, stationName, x, y));
        }
        br.close();
    }

    /**
     * Returns the map of stations.
     *
     * @return a map of station numbers to Station objects
     */
    public Map<Integer, Station> getStations() {
        return stations;
    }

    /**
     * The main method that parses the CSV file and prints the station information to the console.
     *
     * @param args command-line arguments 
     */
    public static void main(String[] args) {
        try {
            TrainMapParser parser = new TrainMapParser("/src/map/Map.csv");
            Map<Integer, Station> stations = parser.getStations();
            for (Map.Entry<Integer, Station> entry : stations.entrySet()) {
                System.out.println(entry.getValue());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
