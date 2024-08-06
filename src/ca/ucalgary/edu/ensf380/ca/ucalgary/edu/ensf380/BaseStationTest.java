package ca.ucalgary.edu.ensf380;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class BaseStationTest {

    @Test
    public void testGetRow() {
        // Arrange
        BaseStation station = new BaseStation(1, "L1", 100, "S100", "Central Station", 123.456, 789.012, "StationA, StationB");

        // Act & Assert
        assertEquals(1, station.getRow());
    }

    @Test
    public void testSetRow() {
        // Arrange
        BaseStation station = new BaseStation(0, null, 0, null, null, 0, 0, null);

        // Act
        station.setRow(2);

        // Assert
        assertEquals(2, station.getRow());
    }

    @Test
    public void testGetLineCode() {
        // Arrange
        BaseStation station = new BaseStation(1, "L1", 100, "S100", "Central Station", 123.456, 789.012, "StationA, StationB");

        // Act & Assert
        assertEquals("L1", station.getLineCode());
    }

    @Test
    public void testSetLineCode() {
        // Arrange
        BaseStation station = new BaseStation(0, null, 0, null, null, 0, 0, null);

        // Act
        station.setLineCode("L2");

        // Assert
        assertEquals("L2", station.getLineCode());
    }

    @Test
    public void testGetStationNumber() {
        // Arrange
        BaseStation station = new BaseStation(1, "L1", 100, "S100", "Central Station", 123.456, 789.012, "StationA, StationB");

        // Act & Assert
        assertEquals(100, station.getStationNumber());
    }

    @Test
    public void testSetStationNumber() {
        // Arrange
        BaseStation station = new BaseStation(0, null, 0, null, null, 0, 0, null);

        // Act
        station.setStationNumber(200);

        // Assert
        assertEquals(200, station.getStationNumber());
    }

    @Test
    public void testGetStationCode() {
        // Arrange
        BaseStation station = new BaseStation(1, "L1", 100, "S100", "Central Station", 123.456, 789.012, "StationA, StationB");

        // Act & Assert
        assertEquals("S100", station.getStationCode());
    }

    @Test
    public void testSetStationCode() {
        // Arrange
        BaseStation station = new BaseStation(0, null, 0, null, null, 0, 0, null);

        // Act
        station.setStationCode("S200");

        // Assert
        assertEquals("S200", station.getStationCode());
    }

    @Test
    public void testGetStationName() {
        // Arrange
        BaseStation station = new BaseStation(1, "L1", 100, "S100", "Central Station", 123.456, 789.012, "StationA, StationB");

        // Act & Assert
        assertEquals("Central Station", station.getStationName());
    }

    @Test
    public void testSetStationName() {
        // Arrange
        BaseStation station = new BaseStation(0, null, 0, null, null, 0, 0, null);

        // Act
        station.setStationName("Downtown Station");

        // Assert
        assertEquals("Downtown Station", station.getStationName());
    }

    @Test
    public void testGetX() {
        // Arrange
        BaseStation station = new BaseStation(1, "L1", 100, "S100", "Central Station", 123.456, 789.012, "StationA, StationB");

        // Act & Assert
        assertEquals(123.456, station.getX());
    }

    @Test
    public void testSetX() {
        // Arrange
        BaseStation station = new BaseStation(0, null, 0, null, null, 0, 0, null);

        // Act
        station.setX(654.321);

        // Assert
        assertEquals(654.321, station.getX());
    }

    @Test
    public void testGetY() {
        // Arrange
        BaseStation station = new BaseStation(1, "L1", 100, "S100", "Central Station", 123.456, 789.012, "StationA, StationB");

        // Act & Assert
        assertEquals(789.012, station.getY());
    }

    @Test
    public void testSetY() {
        // Arrange
        BaseStation station = new BaseStation(0, null, 0, null, null, 0, 0, null);

        // Act
        station.setY(987.654);

        // Assert
        assertEquals(987.654, station.getY());
    }

    @Test
    public void testGetCommonStations() {
        // Arrange
        BaseStation station = new BaseStation(1, "L1", 100, "S100", "Central Station", 123.456, 789.012, "StationA, StationB");

        // Act & Assert
        assertEquals("StationA, StationB", station.getCommonStations());
    }

    @Test
    public void testSetCommonStations() {
        // Arrange
        BaseStation station = new BaseStation(0, null, 0, null, null, 0, 0, null);

        // Act
        station.setCommonStations("StationC, StationD");

        // Assert
        assertEquals("StationC, StationD", station.getCommonStations());
    }
}
