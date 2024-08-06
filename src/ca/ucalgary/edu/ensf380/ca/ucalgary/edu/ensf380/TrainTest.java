package ca.ucalgary.edu.ensf380;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TrainTest {

    @Test
    public void testTrainConstructorAndGetters() {
        Train train = new Train(1, "forward");

        assertEquals(1, train.getId(), "Train ID should be 1");
        assertEquals("forward", train.getDirection(), "Train direction should be 'forward'");
       
    }

    @Test
    public void testSetters() {
        Train train = new Train(1, "forward");

        train.setId(2);
        train.setDirection("backward");
       

        assertEquals(2, train.getId(), "Train ID should be 2");
        assertEquals("backward", train.getDirection(), "Train direction should be 'backward'");
        
    }

    @Test
    public void testSetDirectionInvalidValue() {
        Train train = new Train(1, "forward");
        train.setDirection("upward"); // Invalid value

        assertNotEquals("upward", train.getDirection(), "Direction should not accept invalid values");
        assertEquals("forward", train.getDirection(), "Train direction should remain 'forward' after invalid set");
    }

    @Test
    public void testSetSpeedNegativeValue() {
        Train train = new Train(1, "forward");
   

        
    }

    @Test
    public void testTrainEquality() {
        Train train1 = new Train(1, "forward");
        Train train2 = new Train(1, "forward");

        assertEquals(train1.getId(), train2.getId(), "Train IDs should be equal");
        assertEquals(train1.getDirection(), train2.getDirection(), "Train directions should be equal");
       
    }

    @Test
    public void testTrainInequality() {
        Train train1 = new Train(1, "forward");
        Train train2 = new Train(2, "backward");

        assertNotEquals(train1.getId(), train2.getId(), "Train IDs should not be equal");
        assertNotEquals(train1.getDirection(), train2.getDirection(), "Train directions should not be equal");
       
    }
}
