package ca.ucalgary.edu.ensf380;

/**
 * The Station class represents a train station with additional attributes related to train presence.
 * It extends BaseStation to include information about the current train location and the train at the station.
 */
public class Station extends BaseStation {
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
        super(row, lineCode, stationNumber, stationCode, stationName, x, y, commonStations);
        this.isCurrentTrainLocation = false;
        this.train = null; // Initialize with no train
    }
    
    // Additional constructors if needed
    public Station(int stationNumber, double x, double y) {
        super(0, null, stationNumber, null, null, x, y, null);
    }

    public Station(int row, String lineCode, int stationNumber, String stationCode, String stationName, double x, double y) {
        super(row, lineCode, stationNumber, stationCode, stationName, x, y, null);
    }

    // Getters and setters

    public boolean hasTrain() {
        return train != null;
    }

    public Train getTrain() {
        return train;
    }

    public void setTrain(Train train) {
        this.train = train;
    }

    public boolean isCurrentTrainLocation() {
        return isCurrentTrainLocation;
    }

    public void setCurrentTrainLocation(boolean isCurrentTrainLocation) {
        this.isCurrentTrainLocation = isCurrentTrainLocation;
    }
}
