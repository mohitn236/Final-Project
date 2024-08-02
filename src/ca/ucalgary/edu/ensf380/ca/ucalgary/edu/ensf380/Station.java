package ca.ucalgary.edu.ensf380;

/**
 * The Station class represents a train station with various attributes such as row,
 * line code, station number, station code, station name, coordinates, common stations,
 * and information about whether it is a current train location and which train is at the station.
 */
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
    private Train train;

    /**
     * Constructs a Station object with the specified attributes.
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
    public Station(int row, String lineCode, int stationNumber, String stationCode, String stationName, double x, double y, String commonStations) {
        this.row = row;
        this.lineCode = lineCode;
        this.stationNumber = stationNumber;
        this.stationCode = stationCode;
        this.stationName = stationName;
        this.x = x;
        this.y = y;
        this.commonStations = commonStations;
        this.isCurrentTrainLocation = false;
        this.train = null; // Initialize with no train
    }

    /**
     * Returns whether there is a train at this station.
     *
     * @return true if there is a train at this station, false otherwise
     */
    public boolean hasTrain() {
        return train != null;
    }

    /**
     * Returns the train at this station.
     *
     * @return the train at this station, or null if there is no train
     */
    public Train getTrain() {
        return train;
    }

    /**
     * Sets the train at this station.
     *
     * @param train the train to set at this station
     */
    public void setTrain(Train train) {
        this.train = train;
    }

    /**
     * Returns the row number of the station in the data source.
     *
     * @return the row number of the station
     */
    public int getRow() {
        return row;
    }

    /**
     * Sets the row number of the station in the data source.
     *
     * @param row the new row number of the station
     */
    public void setRow(int row) {
        this.row = row;
    }

    /**
     * Returns the code of the line the station is on.
     *
     * @return the line code of the station
     */
    public String getLineCode() {
        return lineCode;
    }

    /**
     * Sets the code of the line the station is on.
     *
     * @param lineCode the new line code of the station
     */
    public void setLineCode(String lineCode) {
        this.lineCode = lineCode;
    }

    /**
     * Returns the number of the station on the line.
     *
     * @return the station number on the line
     */
    public int getStationNumber() {
        return stationNumber;
    }

    /**
     * Sets the number of the station on the line.
     *
     * @param stationNumber the new station number on the line
     */
    public void setStationNumber(int stationNumber) {
        this.stationNumber = stationNumber;
    }

    /**
     * Returns the code of the station.
     *
     * @return the station code
     */
    public String getStationCode() {
        return stationCode;
    }

    /**
     * Sets the code of the station.
     *
     * @param stationCode the new station code
     */
    public void setStationCode(String stationCode) {
        this.stationCode = stationCode;
    }

    /**
     * Returns the name of the station.
     *
     * @return the station name
     */
    public String getStationName() {
        return stationName;
    }

    /**
     * Sets the name of the station.
     *
     * @param stationName the new station name
     */
    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    /**
     * Returns the x-coordinate of the station's location.
     *
     * @return the x-coordinate of the station's location
     */
    public double getX() {
        return x;
    }

    /**
     * Sets the x-coordinate of the station's location.
     *
     * @param x the new x-coordinate of the station's location
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * Returns the y-coordinate of the station's location.
     *
     * @return the y-coordinate of the station's location
     */
    public double getY() {
        return y;
    }

    /**
     * Sets the y-coordinate of the station's location.
     *
     * @param y the new y-coordinate of the station's location
     */
    public void setY(double y) {
        this.y = y;
    }

    /**
     * Returns a string representing common stations between lines.
     *
     * @return the common stations string
     */
    public String getCommonStations() {
        return commonStations;
    }

    /**
     * Sets a string representing common stations between lines.
     *
     * @param commonStations the new common stations string
     */
    public void setCommonStations(String commonStations) {
        this.commonStations = commonStations;
    }

    /**
     * Returns whether this station is a current train location.
     *
     * @return true if this station is a current train location, false otherwise
     */
    public boolean isCurrentTrainLocation() {
        return isCurrentTrainLocation;
    }

    /**
     * Sets whether this station is a current train location.
     *
     * @param isCurrentTrainLocation true if this station should be marked as a current train location, false otherwise
     */
    public void setCurrentTrainLocation(boolean isCurrentTrainLocation) {
        this.isCurrentTrainLocation = isCurrentTrainLocation;
    }
}
