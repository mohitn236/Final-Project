package ca.ucalgary.edu.ensf380;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Station {
    private int row;
    private String lineCode;
    private int stationNumber;
    private String stationCode;
    private String stationName;
    private double x;
    private double y;
    private String commonStations;
    private boolean isCurrentTrainLocation;

    public Station(int row, String lineCode, int stationNumber, String stationCode, String stationName, double x, double y, String commonStations) {
        this.row = row;
        this.lineCode = lineCode;
        this.stationNumber = stationNumber;
        this.stationCode = stationCode;
        this.stationName = stationName;
        this.x = x;
        this.y = y;
        this.commonStations = commonStations;
        this.isCurrentTrainLocation = false; // default to false
    }

    public Station(double x, double y, String stationName) {
        this.x = x;
        this.y = y;
        this.stationName = stationName;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public String getLineCode() {
        return lineCode;
    }

    public void setLineCode(String lineCode) {
        this.lineCode = lineCode;
    }

    public int getStationNumber() {
        return stationNumber;
    }

    public void setStationNumber(int stationNumber) {
        this.stationNumber = stationNumber;
    }

    public String getStationCode() {
        return stationCode;
    }

    public void setStationCode(String stationCode) {
        this.stationCode = stationCode;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public String getCommonStations() {
        return commonStations;
    }

    public void setCommonStations(String commonStations) {
        this.commonStations = commonStations;
    }

    public boolean isCurrentTrainLocation() {
        return isCurrentTrainLocation;
    }

    public void setCurrentTrainLocation(boolean isCurrentTrainLocation) {
        this.isCurrentTrainLocation = isCurrentTrainLocation;
    }

    public static List<Station> loadStations(String csvPath) {
        List<Station> stations = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(csvPath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                double x = Double.parseDouble(data[0]);
                double y = Double.parseDouble(data[1]);
                String name = data[2];
                stations.add(new Station(x, y, name));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stations;
    }
}
