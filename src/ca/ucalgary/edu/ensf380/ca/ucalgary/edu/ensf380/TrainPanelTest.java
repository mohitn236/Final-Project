package ca.ucalgary.edu.ensf380;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TrainPanelTest {

    private List<Station> stations;
    private TrainPanel trainPanel;
    
    @BeforeEach
    public void setUp() {
        stations = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            stations.add(new Station(i, "L" + i, i, "S" + i, "Station " + i, i * 10, i * 10));
        }
        trainPanel = new TrainPanel(stations);
    }


    @Test
    public void testUpdateTrainInfo() {
        // Initialize trains
        trainPanel.initializeTrains();
        
        // Update train info
        trainPanel.updateTrainInfo();
        
        // Verify the train info update
        JLabel label = (JLabel) trainPanel.getComponent(0);
        assertTrue(label.getText().contains("Previous Station:"));
        assertTrue(label.getText().contains("Current Station:"));
        assertTrue(label.getText().contains("Next Station"));
    }

    @Test
    public void testSelectTrain() {
        // Initialize trains
        trainPanel.initializeTrains();
        
        // Select a train
        trainPanel.selectTrain(2);
        
        // Verify that the current train number is updated
        assertEquals(2, trainPanel.getcurrentTrainNumber());
        JLabel label = (JLabel) trainPanel.getComponent(0);
        assertTrue(label.getText().contains("Current Station: Station 2"));
    }

   

    @Test
    public void testSetcurrentTrainNumber() {
        // Initialize trains
        trainPanel.initializeTrains();
        
        // Set a new current train number
        trainPanel.setcurrentTrainNumber(3);
        
        // Verify that the current train number is updated
        assertEquals(3, trainPanel.getcurrentTrainNumber());
        JLabel label = (JLabel) trainPanel.getComponent(0);
        assertTrue(label.getText().contains("Current Station: Station 3"));
    }

    @Test
    public void testUpdateCurrentTrainLocation() {
        // Initialize trains
        trainPanel.initializeTrains();
        
        // Select train 2
        trainPanel.selectTrain(2);
        
        // Update current train location
        trainPanel.updateCurrentTrainLocation();
        
        // Verify that the current train location is updated
        assertEquals(2, trainPanel.getcurrentTrainNumber());
    }
}
