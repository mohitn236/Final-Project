package ca.ucalgary.edu.ensf380;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * The AdvertisementPanel class displays advertisements and train information.
 * It rotates through advertisements and can display a map with train locations.
 */
public class AdvertisementPanel extends JPanel {
    private List<Advertisement> advertisements;
    private int currentAdIndex = 0;
    private JLabel adLabel;
    private JLabel trainInfoLabel;
    private Timer timer;
    private JPanel mapPanel;
    private boolean showingMap = false;
    private BufferedImage mapImage;
    private String csvFilePath = "src/map/Map.csv"; 
    private List<TrainLocation> trainLocations;
    private final int IMAGE_WIDTH = 800;
    private final int IMAGE_HEIGHT = 600;
    private static final long serialVersionUID = 1L;
    private int currentStationIndex;

    /**
     * Constructs an AdvertisementPanel and initializes it with advertisements and train information.
     *
     * @param connection The database connection used to load advertisements
     */
    public AdvertisementPanel(Connection connection) {
        setLayout(new BorderLayout());
        setBackground(new Color(255, 228, 196));

        // Advertisement label
        adLabel = new JLabel("", JLabel.CENTER);
        adLabel.setPreferredSize(new Dimension(IMAGE_WIDTH, IMAGE_HEIGHT));
        adLabel.setFont(new Font("Arial", Font.BOLD, 16)); // Set a custom font
        add(adLabel, BorderLayout.CENTER);

        // Train information label
        trainInfoLabel = new JLabel("Train Information", JLabel.CENTER);
        trainInfoLabel.setPreferredSize(new Dimension(IMAGE_WIDTH, 50));
        trainInfoLabel.setFont(new Font("Arial", Font.BOLD, 14));
        trainInfoLabel.setForeground(Color.BLACK);
        add(trainInfoLabel, BorderLayout.NORTH);

        setBorder(BorderFactory.createLineBorder(Color.BLACK, 2)); // Border around the panel

        // Create map panel
        mapPanel = createMapPanel();

        // Load advertisements
        advertisements = loadAdvertisements(connection);
        if (advertisements.size() > 0) {
            displayAdvertisement(advertisements.get(currentAdIndex));
            startAdRotation();
        }
    }

    /**
     * Loads advertisements from the database.
     *
     * @param connection The database connection used to retrieve advertisements
     * @return A list of Advertisement objects
     */
    private List<Advertisement> loadAdvertisements(Connection connection) {
        List<Advertisement> ads = new ArrayList<>();
        String query = "SELECT id, title, description, media FROM images";
        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Advertisement ad = new Advertisement(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getBytes("media")
                );
                ads.add(ad);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ads;
    }

    /**
     * Displays the given advertisement on the panel.
     *
     * @param ad The Advertisement object to display
     */
    private void displayAdvertisement(Advertisement ad) {
        byte[] imageData = ad.getMediaFile();
        if (imageData != null && imageData.length > 0) {
            try {
                BufferedImage img = ImageIO.read(new ByteArrayInputStream(imageData));
                if (img != null) {
                    Image scaledImg = img.getScaledInstance(IMAGE_WIDTH, IMAGE_HEIGHT, Image.SCALE_SMOOTH);
                    ImageIcon icon = new ImageIcon(scaledImg);
                    adLabel.setIcon(icon);
                    adLabel.setText(null); // Clear any previous text
                } else {
                    adLabel.setText("<html><h1>" + ad.getTitle() + "</h1><br>" + ad.getDescription() + "</html>");
                }
            } catch (IOException e) {
                e.printStackTrace();
                adLabel.setText("<html><h1>" + ad.getTitle() + "</h1><br>" + ad.getDescription() + "</html>");
            }
        } else {
            adLabel.setText("<html><h1>" + ad.getTitle() + "</h1><br>" + ad.getDescription() + "</html>");
        }
    }

    /**
     * Creates a panel for displaying the map.
     *
     * @return The JPanel containing the map
     */
    private JPanel createMapPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel label = new JLabel("", JLabel.CENTER);
        label.setPreferredSize(new Dimension(IMAGE_WIDTH, IMAGE_HEIGHT));
        panel.add(label, BorderLayout.CENTER);

        try {
            BufferedImage svgImage = SVGHelper.loadSVGImage("src/map/Trains.svg");
            if (svgImage != null) {
                mapImage = svgImage;

                updateTrainLocations(currentStationIndex);
                BufferedImage updatedMapImage = overlayTrainLocations(mapImage);
                label.setIcon(new ImageIcon(updatedMapImage));
            } else {
                label.setText("Map could not be loaded");
            }
        } catch (Exception e) {
            e.printStackTrace();
            label.setText("Map could not be loaded");
        }

        return panel;
    }

    /**
     * Updates the list of train locations based on the current station index and CSV data.
     *
     * @param currentStationIndex The index of the current station to highlight
     */
    private void updateTrainLocations(int currentStationIndex) {
        trainLocations = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(csvFilePath))) {
            String line;
            br.readLine(); // Skip header line
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length >= 7) {
                    try {
                        int stationNumber = Integer.parseInt(values[2].trim());
                        double x = Double.parseDouble(values[5].trim());
                        double y = Double.parseDouble(values[6].trim());

                        TrainLocation location = new TrainLocation(stationNumber, (int) x, (int) y);
                        if (stationNumber == currentStationIndex) {
                            location.setCurrentTrain(true);
                        }
                        trainLocations.add(location);
                    } catch (NumberFormatException e) {
                        System.err.println("Skipping invalid line: " + line);
                    }
                } else {
                    System.err.println("Skipping invalid line: " + line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates a new image by overlaying train locations on the base map image.
     *
     * @param baseImage The base image to overlay train locations onto
     * @return A BufferedImage with train locations overlaid
     */
    private BufferedImage overlayTrainLocations(BufferedImage baseImage) {
        BufferedImage overlayedImage = new BufferedImage(baseImage.getWidth(), baseImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = overlayedImage.createGraphics();
        g.drawImage(baseImage, 0, 0, null);

        for (TrainLocation location : trainLocations) {
            if (location.isCurrentTrain()) {
                g.setColor(Color.GREEN); // Different color for the current train
                g.fillOval(location.getX() - 7, location.getY() - 7, 14, 14); // Larger circle for visibility
            } else {
                g.setColor(Color.RED);
                g.fillOval(location.getX() - 5, location.getY() - 5, 10, 10);
            }
        }

        g.dispose();
        return overlayedImage;
    }

    /**
     * Displays the map with the current train locations.
     */
    private void displayMap() {
        updateTrainLocations(currentStationIndex);
        BufferedImage updatedMapImage = overlayTrainLocations(mapImage);
        ((JLabel) mapPanel.getComponent(0)).setIcon(new ImageIcon(updatedMapImage));
        remove(adLabel);
        add(mapPanel, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    /**
     * Hides the map and returns to displaying advertisements.
     */
    private void hideMap() {
        remove(mapPanel);
        add(adLabel, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    /**
     * Updates the train information displayed on the panel.
     */
    private void updateTrainInfo() {
        StringBuilder info = new StringBuilder("<html>");
        for (TrainLocation location : trainLocations) {
            if (location.isCurrentTrain()) {
                info.append("Train ").append(location.getId()).append(" is at position (")
                        .append(location.getX()).append(", ").append(location.getY()).append(")<br>");
            }
        }
        info.append("</html>");
        trainInfoLabel.setText(info.toString());
    }

    /**
     * Starts rotating advertisements and displaying the map.
     * Advertisements are displayed for 10 seconds, followed by a 5-second map display.
     */
    private void startAdRotation() {
        timer = new Timer(true);
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                SwingUtilities.invokeLater(() -> {
                    if (showingMap) {
                        hideMap();
                        showingMap = false;
                    } else {
                        currentAdIndex = (currentAdIndex + 1) % advertisements.size();
                        displayAdvertisement(advertisements.get(currentAdIndex));
                        updateTrainInfo(); // Update train info before displaying the map
                        new Timer().schedule(new TimerTask() {
                            @Override
                            public void run() {
                                SwingUtilities.invokeLater(() -> {
                                    updateTrainLocations(currentStationIndex); // Update with current train position
                                    displayMap();
                                    showingMap = true;
                                });
                            }
                        }, 10000); // Delay before showing map
                    }
                });
            }
        }, 0, 15000); // Change ads every 15 seconds (10s ad + 5s map)
    }

    /**
     * Returns the current map image.
     *
     * @return The BufferedImage representing the map
     */
    public BufferedImage getMapImage() {
        return mapImage;
    }

    /**
     * Returns the map panel.
     *
     * @return The JPanel used for displaying the map
     */
    public JPanel getMapPanel() {
        return mapPanel;
    }

    /**
     * Updates the train locations from a TrainPanel instance.
     *
     * @param panel The TrainPanel instance used to get the current station index
     */
//    public void updateTrainLocationsFromPanel(TrainPanel panel) {
//        this.currentStationIndex = panel.getCurrentStationIndex(); // Assuming TrainPanel provides this info
//        updateTrainLocations(currentStationIndex);
//    }
}
