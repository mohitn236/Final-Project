package ca.ucalgary.edu.ensf380;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class TrainMapParser {
    private Map<Integer, Station> stations = new HashMap<>();

    public TrainMapParser(String filePath) throws IOException {
        parseCSV(filePath);
    }

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
            //String commonStations = values[7];
            stations.put(stationNumber, new Station(row, lineCode, stationNumber, stationCode, stationName, x, y ));
        }
        br.close();
    }

    public Map<Integer, Station> getStations() {
        return stations;
    }

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
