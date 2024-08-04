package ca.ucalgary.edu.ensf380;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class TrainPositionsPanel extends JPanel {
    private BufferedImage mapImage;
    private final int[] trainX = new int[12]; // X coordinates for the trains
    private final int[] trainY = new int[12]; // Y coordinates for the trains

    public TrainPositionsPanel() {
        try {
            // Load the map image
            mapImage = ImageIO.read(new File("src/Map/trains.svg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        setPreferredSize(new Dimension(mapImage.getWidth(), mapImage.getHeight()));
    }

    public void updateTrainPositions(int[] xPositions, int[] yPositions) {
        System.arraycopy(xPositions, 0, trainX, 0, xPositions.length);
        System.arraycopy(yPositions, 0, trainY, 0, yPositions.length);
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (mapImage != null) {
            g.drawImage(mapImage, 0, 0, null);
        }
        g.setColor(Color.RED);
        for (int i = 0; i < trainX.length; i++) {
            g.fillOval(trainX[i] - 5, trainY[i] - 5, 10, 10); // Draw a small circle for each train
        }
    }
}
