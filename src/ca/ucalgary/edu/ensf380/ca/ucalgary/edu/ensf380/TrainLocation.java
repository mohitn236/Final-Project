package ca.ucalgary.edu.ensf380;

/**
 * The TrainLocation class represents the location of a train with an ID, coordinates, and a flag
 * indicating whether it is the current train.
 * It provides methods to access and modify these properties.
 */
class TrainLocation {
    private int id;
    private int x;
    private int y;
    private boolean isCurrentTrain;

    /**
     * Constructs a TrainLocation object with the specified ID and coordinates.
     * By default, the train is not marked as the current train.
     *
     * @param id the unique identifier of the train
     * @param x  the x-coordinate of the train's location
     * @param y  the y-coordinate of the train's location
     */
    public TrainLocation(int id, int x, int y) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.isCurrentTrain = false;
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
     * Returns the x-coordinate of the train's location.
     *
     * @return the x-coordinate of the train's location
     */
    public int getX() {
        return x;
    }

    /**
     * Returns the y-coordinate of the train's location.
     *
     * @return the y-coordinate of the train's location
     */
    public int getY() {
        return y;
    }

    /**
     * Returns whether this train is marked as the current train.
     *
     * @return true if this train is the current train, false otherwise
     */
    public boolean isCurrentTrain() {
        return isCurrentTrain;
    }

    /**
     * Sets whether this train is marked as the current train.
     *
     * @param isCurrentTrain true if this train should be marked as the current train, false otherwise
     */
    public void setCurrentTrain(boolean isCurrentTrain) {
        this.isCurrentTrain = isCurrentTrain;
    }
}
