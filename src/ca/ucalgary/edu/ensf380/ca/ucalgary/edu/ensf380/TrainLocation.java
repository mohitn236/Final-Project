package ca.ucalgary.edu.ensf380;


class TrainLocation {
    private int id;
    private int x;
    private int y;
    private boolean isCurrentTrain;

    public TrainLocation(int id, int x, int y) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.isCurrentTrain = false;
    }

    public int getId() {
        return id;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isCurrentTrain() {
        return isCurrentTrain;
    }

    public void setCurrentTrain(boolean isCurrentTrain) {
        this.isCurrentTrain = isCurrentTrain;
    }
}