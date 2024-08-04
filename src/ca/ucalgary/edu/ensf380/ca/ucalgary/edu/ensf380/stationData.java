package ca.ucalgary.edu.ensf380;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class stationData {

    public static Map<String, Station> parseCSV(String filePath) {
        Map<String, Station> stations = new HashMap<>();
        
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean headerSkipped = false;
            
            while ((line = reader.readLine()) != null) {
                if (!headerSkipped) {
                    headerSkipped = true;
                    continue;
                }

                String[] fields = line.split(",");
                String name = fields[0];
                String color = fields[1];
                String commonCode = fields[7]; // Column H

                stations.computeIfAbsent(name, Station::new).updateInfo(color, commonCode);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return stations;
    }
}