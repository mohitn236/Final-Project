//package ca.ucalgary.edu.ensf380;
//
//import org.junit.jupiter.api.Test;
//import static org.junit.jupiter.api.Assertions.*;
//
//public class TrainTest {
//
//    @Test
//    public void testTrainConstructorAndGetters() {
//        Train train = new Train(1, "forward", 5);
//
//        assertEquals(1, train.getId(), "Train ID should be 1");
//        assertEquals("forward", train.getDirection(), "Train direction should be 'forward'");
//        assertEquals(5, train.getSpeed(), "Train speed should be 5");
//    }
//
//    @Test
//    public void testSetters() {
//        Train train = new Train(1, "forward", 5);
//
//        train.setId(2);
//        train.setDirection("backward");
//        train.setSpeed(10);
//
//        assertEquals(2, train.getId(), "Train ID should be 2");
//        assertEquals("backward", train.getDirection(), "Train direction should be 'backward'");
//        assertEquals(10, train.getSpeed(), "Train speed should be 10");
//    }
//
//    @Test
//    public void testSetDirectionInvalidValue() {
//        Train train = new Train(1, "forward", 5);
//        train.setDirection("upward"); // Invalid value
//
//        assertNotEquals("upward", train.getDirection(), "Direction should not accept invalid values");
//        assertEquals("forward", train.getDirection(), "Train direction should remain 'forward' after invalid set");
//    }
//
//    @Test
//    public void testSetSpeedNegativeValue() {
//        Train train = new Train(1, "forward", 5);
//        train.setSpeed(-10); // Invalid speed
//
//        assertNotEquals(-10, train.getSpeed(), "Speed should not be set to negative values");
//        assertEquals(5, train.getSpeed(), "Train speed should remain 5 after invalid set");
//    }
//
//    @Test
//    public void testTrainEquality() {
//        Train train1 = new Train(1, "forward", 5);
//        Train train2 = new Train(1, "forward", 5);
//
//        assertEquals(train1.getId(), train2.getId(), "Train IDs should be equal");
//        assertEquals(train1.getDirection(), train2.getDirection(), "Train directions should be equal");
//        assertEquals(train1.getSpeed(), train2.getSpeed(), "Train speeds should be equal");
//    }
//
//    @Test
//    public void testTrainInequality() {
//        Train train1 = new Train(1, "forward", 5);
//        Train train2 = new Train(2, "backward", 10);
//
//        assertNotEquals(train1.getId(), train2.getId(), "Train IDs should not be equal");
//        assertNotEquals(train1.getDirection(), train2.getDirection(), "Train directions should not be equal");
//        assertNotEquals(train1.getSpeed(), train2.getSpeed(), "Train speeds should not be equal");
//    }
//}
