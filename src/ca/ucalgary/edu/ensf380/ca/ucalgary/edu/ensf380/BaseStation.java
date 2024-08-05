package ca.ucalgary.edu.ensf380;

/**
 * The BaseStation class represents a train station with attributes such as row,
 * line code, station number, station code, station name, coordinates, and common stations.
 */
public class BaseStation {
    private int row;
    private String lineCode;
    private int stationNumber;
    private String stationCode;
    private String stationName;
    private double x;
    private double y;
    private String commonStations;

    /**
     * Constructs a BaseStation object with the specified attributes.
     *
     * @param row            the row number of the station in the data source
     * @param lineCode       the code of the line the station is on
     * @param stationNumber  the number of the station on the line
     * @param stationCode    the code of the station
     * @param stationName    the name of the station
     * @param x              the x-coordinate of the station's location
     * @param y              the y-coordinate of the station's location
     * @param commonStations a string representing common stations between lines
     */
    public BaseStation(int row, String lineCode, int stationNumber, String stationCode, String stationName, double x, double y, String commonStations) {
        this.row = row;
        this.lineCode = lineCode;
        this.stationNumber = stationNumber;
        this.stationCode = stationCode;
        this.stationName = stationName;
        this.x = x;
        this.y = y;
        this.commonStations = commonStations;
    }

    // Getters and setters

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
