package ca.ucalgary.edu.ensf380;

public class Station {
    private int row;
    private String lineCode;
    private int stationNumber;
    private String stationCode;
    private String stationName;
    private double x;
    private double y;
    private String commonStations;

    public Station(int row, String lineCode, int stationNumber, String stationCode, String stationName, double x, double y, String commonStations) {
        this.row = row;
        this.lineCode = lineCode;
        this.stationNumber = stationNumber;
        this.stationCode = stationCode;
        this.stationName = stationName;
        this.x = x;
        this.y = y;
        this.commonStations = commonStations;
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
}

