//package ca.ucalgary.edu.ensf380;
//
//import org.junit.jupiter.api.Test;
//import static org.junit.jupiter.api.Assertions.*;
//
//public class StationTest {
//
//    @Test
//    public void testStationConstructorAndGetters() {
//        Station station = new Station(1, "L1", 10, "S10", "StationName", 50.0, 100.0, "CS1, CS2");
//
//        assertEquals(1, station.getRow(), "Row should be 1");
//        assertEquals("L1", station.getLineCode(), "Line code should be 'L1'");
//        assertEquals(10, station.getStationNumber(), "Station number should be 10");
//        assertEquals("S10", station.getStationCode(), "Station code should be 'S10'");
//        assertEquals("StationName", station.getStationName(), "Station name should be 'StationName'");
//        assertEquals(50.0, station.getX(), 0.01, "X-coordinate should be 50.0");
//        assertEquals(100.0, station.getY(), 0.01, "Y-coordinate should be 100.0");
//        assertEquals("CS1, CS2", station.getCommonStations(), "Common stations should be 'CS1, CS2'");
//        assertFalse(station.isCurrentTrainLocation(), "Should not be a current train location by default");
//        assertNull(station.getTrain(), "Train should be null by default");
//    }
//
//    @Test
//    public void testSetters() {
//        Station station = new Station(1, "L1", 10, "S10", "StationName", 50.0, 100.0, "CS1, CS2");
//
//        station.setRow(2);
//        station.setLineCode("L2");
//        station.setStationNumber(20);
//        station.setStationCode("S20");
//        station.setStationName("NewStationName");
//        station.setX(60.0);
//        station.setY(120.0);
//        station.setCommonStations("CS3, CS4");
//        station.setCurrentTrainLocation(true);
//
//        assertEquals(2, station.getRow(), "Row should be 2");
//        assertEquals("L2", station.getLineCode(), "Line code should be 'L2'");
//        assertEquals(20, station.getStationNumber(), "Station number should be 20");
//        assertEquals("S20", station.getStationCode(), "Station code should be 'S20'");
//        assertEquals("NewStationName", station.getStationName(), "Station name should be 'NewStationName'");
//        assertEquals(60.0, station.getX(), 0.01, "X-coordinate should be 60.0");
//        assertEquals(120.0, station.getY(), 0.01, "Y-coordinate should be 120.0");
//        assertEquals("CS3, CS4", station.getCommonStations(), "Common stations should be 'CS3, CS4'");
//        assertTrue(station.isCurrentTrainLocation(), "Should be a current train location");
//    }
//
//    @Test
//    public void testTrainHandling() {
//        Station station = new Station(1, "L1", 10, "S10", "StationName", 50.0, 100.0, "CS1, CS2");
//        Train train = new Train(1, "forward", 5);
//
//        station.setTrain(train);
//
//        assertTrue(station.hasTrain(), "Station should have a train");
//        assertEquals(train, station.getTrain(), "Train should be the same as set");
//    }
//
//    @Test
//    public void testRemoveTrain() {
//        Station station = new Station(1, "L1", 10, "S10", "StationName", 50.0, 100.0, "CS1, CS2");
//        Train train = new Train(1, "forward", 5);
//
//        station.setTrain(train);
//        assertTrue(station.hasTrain(), "Station should have a train");
//
//        station.setTrain(null);
//        assertFalse(station.hasTrain(), "Station should not have a train");
//        assertNull(station.getTrain(), "Train should be null");
//    }
//}
