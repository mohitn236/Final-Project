package ca.ucalgary.edu.ensf380;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TrainPanel extends JPanel {
    private static final long serialVersionUID = 1L;
    private JLabel trainLabel;
    private List<Station> stations;
    private int currentStationIndex = 0;

    public TrainPanel(List<Station> stations) {
        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(800, 100));
        trainLabel = new JLabel("Train Information");
        trainLabel.setForeground(Color.WHITE);
        add(trainLabel);

        this.stations = stations != null ? stations : new ArrayList<>();
        if (this.stations.isEmpty()) {
            loadStationsFromCSV("/Users/shivansh/Downloads/Map.csv");
        }
        updateTrainInfo();
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

    private void updateTrainInfo() {
        if (!stations.isEmpty()) {
            StringBuilder info = new StringBuilder("<html>");

            if (currentStationIndex > 0) {
                Station previousStation = stations.get(currentStationIndex - 1);
                info.append("Previous Stop: ").append(previousStation.getStationName()).append("<br>");
            }

            Station currentStation = stations.get(currentStationIndex);
            info.append("Current Stop: ").append(currentStation.getStationName()).append("<br>");

            for (int i = 1; i <= 4; i++) {
                int nextIndex = currentStationIndex + i;
                if (nextIndex < stations.size()) {
                    Station nextStation = stations.get(nextIndex);
                    info.append("Next Stop ").append(i).append(": ").append(nextStation.getStationName()).append("<br>");
                }
            }

            String transferInfo = "You can change to line blue";
            info.append(transferInfo).append("</html>");

            trainLabel.setText(info.toString());
        }
    }

    public void setCurrentStationIndex(int index) {
        if (index >= 0 && index < stations.size()) {
            currentStationIndex = index;
            updateTrainInfo();
        }
    }

    public List<Station> getStations() {
        return stations;
    }
}
