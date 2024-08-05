package ca.ucalgary.edu.ensf380;

/**
 * The Train class represents a train with an ID, direction, and speed.
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
     * Constructs a Train object with the specified ID, direction, and speed.
     *
     * @param id        the unique identifier of the train
     * @param direction the direction of the train ("forward" or "backward")
     * @param station 
     * @param speed     the speed of the train in stations per interval
     */
    public Train(int id, String direction) {
        this.id = id;
        this.direction = direction;
    }
    
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
        this.direction = direction;
    }

	public Station getCurrentStation() {
		return currentStation;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Station getStation() {
		return station;
	}
	
	public void setStaton(Station station) {
		this.station = station;
	}
	
	public int getX(){
		return x;
	}
	
	public int getY() {
		return y;
	}

}