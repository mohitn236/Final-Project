package ca.ucalgary.edu.ensf380;

public class Train {
    private int id;
    private String direction; // "forward" or "backward"
    private int speed; // Stations per interval

    public Train(int id, String direction, int speed) {
        this.id = id;
        this.direction = direction;
        this.speed = speed;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
}
// adding comment