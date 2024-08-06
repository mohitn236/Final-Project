package ca.ucalgary.edu.ensf380;

/**
 * The Train class represents a train with an ID, direction, and station.
 * It provides methods to access and modify these properties.
 */
public class Train {
    private int id;
    private String direction; // "forward" or "backward"
    private Station currentStation;
    private String name;
    private Station station;
    private int x;
    private int y;

    /**
     * Constructs a Train object with the specified ID and direction.
     *
     * @param id        the unique identifier of the train
     * @param direction the direction of the train ("forward" or "backward")
     */
    public Train(int id, String direction) {
        this.id = id;
        this.direction = direction;
    }
    
    /**
     * Constructs a Train object with the specified ID, name, and station.
     *
     * @param id      the unique identifier of the train
     * @param name    the name of the train
     * @param station the station where the train is located
     */
    public Train(int id, String name, Station station) {
        this.id = id;
        this.name = name;
        this.station = station;
    }

    /**
     * Returns the ID of the train.
     *
     * @return the ID of the train
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the ID of the train.
     *
     * @param id the new ID of the train
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Returns the direction of the train.
     *
     * @return the direction of the train ("forward" or "backward")
     */
    public String getDirection() {
        return direction;
    }

    /**
     * Sets the direction of the train.
     *
     * @param direction the new direction of the train ("forward" or "backward")
     */
    public void setDirection(String direction) {
        if ("forward".equals(direction) || "backward".equals(direction)) {
            this.direction = direction;
        }
        // Else do nothing or handle invalid input as needed
    }

    /**
     * Returns the current station of the train.
     *
     * @return the current station of the train
     */
    public Station getCurrentStation() {
        return currentStation;
    }

    /**
     * Returns the name of the train.
     *
     * @return the name of the train
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the train.
     *
     * @param name the new name of the train
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the station where the train is located.
     *
     * @return the station where the train is located
     */
    public Station getStation() {
        return station;
    }

    /**
     * Sets the station where the train is located.
     *
     * @param station the new station where the train is located
     */
    public void setStation(Station station) {
        this.station = station;
    }

    /**
     * Returns the X coordinate of the train.
     *
     * @return the X coordinate of the train
     */
    public int getX() {
        return x;
    }

    /**
     * Returns the Y coordinate of the train.
     *
     * @return the Y coordinate of the train
     */
    public int getY() {
        return y;
    }
}
