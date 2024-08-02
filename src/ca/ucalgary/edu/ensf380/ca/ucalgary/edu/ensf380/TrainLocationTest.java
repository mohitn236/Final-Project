package ca.ucalgary.edu.ensf380;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TrainLocationTest {

    @Test
    public void testTrainLocationConstructorAndGetters() {
        TrainLocation location = new TrainLocation(1, 100, 200);

        assertEquals(1, location.getId(), "Train ID should be 1");
        assertEquals(100, location.getX(), "Train x-coordinate should be 100");
        assertEquals(200, location.getY(), "Train y-coordinate should be 200");
        assertFalse(location.isCurrentTrain(), "Train should not be the current train by default");
    }

    @Test
    public void testSetCurrentTrain() {
        TrainLocation location = new TrainLocation(1, 100, 200);

        location.setCurrentTrain(true);
        assertTrue(location.isCurrentTrain(), "Train should be marked as the current train");

        location.setCurrentTrain(false);
        assertFalse(location.isCurrentTrain(), "Train should not be marked as the current train");
    }

    @Test
    public void testUpdateCoordinates() {
        TrainLocation location = new TrainLocation(1, 100, 200);

        location.setCurrentTrain(true);
        assertTrue(location.isCurrentTrain(), "Train should be marked as the current train");

        location.setCurrentTrain(false);
        assertFalse(location.isCurrentTrain(), "Train should not be marked as the current train");
    }
}
