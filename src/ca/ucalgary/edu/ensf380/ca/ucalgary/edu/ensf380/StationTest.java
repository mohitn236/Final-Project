package ca.ucalgary.edu.ensf380;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class StationTest {

    @Test
    public void testStationConstructorWithAllParameters() {
        Station station = new Station(1, "L1", 5, "S001", "Central", 100.0, 200.0, "Station A, Station B");

        assertEquals(1, station.getRow());
        assertEquals("L1", station.getLineCode());
        assertEquals(5, station.getStationNumber());
        assertEquals("S001", station.getStationCode());
        assertEquals("Central", station.getStationName());
        assertEquals(100.0, station.getX());
        assertEquals(200.0, station.getY());
        assertEquals("Station A, Station B", station.getCommonStations());
        assertFalse(station.isCurrentTrainLocation());
        assertNull(station.getTrain());
    }

    @Test
    public void testStationConstructorWithBasicParameters() {
        Station station = new Station(10, 50.0, 75.0);

        assertEquals(10, station.getStationNumber());
        assertEquals(50.0, station.getX());
        assertEquals(75.0, station.getY());
        assertFalse(station.isCurrentTrainLocation());
        assertNull(station.getTrain());
    }

    @Test
    public void testStationConstructorWithPartialParameters() {
        Station station = new Station(1, "L1", 10, "S002", "North", 150.0, 250.0);

        assertEquals(1, station.getRow());
        assertEquals("L1", station.getLineCode());
        assertEquals(10, station.getStationNumber());
        assertEquals("S002", station.getStationCode());
        assertEquals("North", station.getStationName());
        assertEquals(150.0, station.getX());
        assertEquals(250.0, station.getY());
        assertFalse(station.isCurrentTrainLocation());
        assertNull(station.getTrain());
    }

    @Test
    public void testHasTrainWhenTrainIsNull() {
        Station station = new Station(1, "L1", 10, "S003", "South", 200.0, 300.0);
        assertFalse(station.hasTrain());
    }

    @Test
    public void testHasTrainWhenTrainIsNotNull() {
        Train train = new Train(1, null); // Assuming a Train class with constructor Train(int id)
        Station station = new Station(1, "L1", 10, "S003", "South", 200.0, 300.0);
        station.setTrain(train);
        assertTrue(station.hasTrain());
    }

    @Test
    public void testSetTrain() {
        Train train = new Train(2, null); // Assuming a Train class with constructor Train(int id)
        Station station = new Station(1, "L1", 10, "S004", "East", 250.0, 350.0);
        station.setTrain(train);
        assertEquals(train, station.getTrain());
    }

    @Test
    public void testSetCurrentTrainLocation() {
        Station station = new Station(1, "L1", 10, "S005", "West", 300.0, 400.0);
        station.setCurrentTrainLocation(true);
        assertTrue(station.isCurrentTrainLocation());

        station.setCurrentTrainLocation(false);
        assertFalse(station.isCurrentTrainLocation());
    }
}
