package ca.ucalgary.edu.ensf380;
//
///**
// * The Train class represents a train with an ID, direction, and speed.
// * It provides methods to access and modify these properties.
// */
//public class Train {
//    private int id;
//    private String direction; // "forward" or "backward"
//    private int speed; // Stations per interval
//
//    /**
//     * Constructs a Train object with the specified ID, direction, and speed.
//     *
//     * @param id        the unique identifier of the train
//     * @param direction the direction of the train ("forward" or "backward")
//     * @param speed     the speed of the train in stations per interval
//     */
//    public Train(int id, String direction, int speed) {
//        this.id = id;
//        this.direction = direction;
//        this.speed = speed;
//    }
//
//    /**
//     * Returns the ID of the train.
//     *
//     * @return the ID of the train
//     */
//    public int getId() {
//        return id;
//    }
//
//    /**
//     * Sets the ID of the train.
//     *
//     * @param id the new ID of the train
//     */
//    public void setId(int id) {
//        this.id = id;
//    }
//
//    /**
//     * Returns the direction of the train.
//     *
//     * @return the direction of the train ("forward" or "backward")
//     */
//    public String getDirection() {
//        return direction;
//    }
//
//    /**
//     * Sets the direction of the train.
//     *
//     * @param direction the new direction of the train ("forward" or "backward")
//     */
//    public void setDirection(String direction) {
//        this.direction = direction;
//    }
//
//    /**
//     * Returns the speed of the train in stations per interval.
//     *
//     * @return the speed of the train
//     */
//    public int getSpeed() {
//        return speed;
//    }
//
//    /**
//     * Sets the speed of the train in stations per interval.
//     *
//     * @param speed the new speed of the train
//     */
//    public void setSpeed(int speed) {
//        this.speed = speed;
//    }
//}

import java.util.List;
import java.util.Map;

import javax.swing.JComponent;

public class Train {
    private String line;
    private Station currentStation;
    private int direction;
    private Map<String, Station> stationMap;

    public Train(String line, Station currentStation, int direction, Map<String, Station> stationMap) {
        this.line = line;
        this.currentStation = currentStation;
        this.direction = direction;
        this.stationMap = stationMap;
    }

    public void setStationMap(Map<String, Station> stationMap) {
        this.stationMap = stationMap;
    }

    public void move(Map<String, List<Station>> subwayLines) {
        List<Station> stations = subwayLines.get(line);
        int currentIndex = stations.indexOf(currentStation);
        int newIndex = currentIndex + direction;
        
        // Change direction if needed
        if (newIndex < 0 || newIndex >= stations.size()) {
            direction *= -1;
            newIndex = currentIndex + direction;
        }
        
        currentStation = stations.get(newIndex);
    }

    private String getNextStationName() {
        // Logic to determine the next station name based on the current station and direction
        // This is a placeholder, replace with actual logic
        return "NextStation";
    }

	public Object getLine() {
		return line;
	}

	public JComponent getCurrentStation() {
		this.currentStation = currentStation;
		return null;

  
	}
}


