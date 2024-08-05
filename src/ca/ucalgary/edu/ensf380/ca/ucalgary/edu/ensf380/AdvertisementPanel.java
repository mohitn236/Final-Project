
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
    private List<Train> trains;
    private MapPanel mapPanelComponent;
    private static final long serialVersionUID = 1L;
    private static final int IMAGE_WIDTH = 800;  // Define the width of the image
    private static final int IMAGE_HEIGHT = 600; // Define the height of the image


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
        adLabel.setPreferredSize(new Dimension(800, 600));
        adLabel.setFont(new Font("Arial", Font.BOLD, 16)); // Set a custom font
        add(adLabel, BorderLayout.CENTER);

        // Train information label
        trainInfoLabel = new JLabel("Train Information", JLabel.CENTER);
        trainInfoLabel.setPreferredSize(new Dimension(800, 50));
        trainInfoLabel.setFont(new Font("Arial", Font.BOLD, 14));
        trainInfoLabel.setForeground(Color.BLACK);
        add(trainInfoLabel, BorderLayout.NORTH);

        setBorder(BorderFactory.createLineBorder(Color.BLACK, 2)); // Border around the panel

        // Initialize map panel
        trains = new ArrayList<>();
        mapPanelComponent = new MapPanel("src/map/Trains.svg", trains, -1);
        mapPanel = mapPanelComponent;

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
                        remove(mapPanel); // Remove the map panel
                        add(adLabel, BorderLayout.CENTER); // Re-add the ad label
                        showingMap = false;
                    } else {
                        remove(adLabel); // Remove the ad label
                        currentAdIndex = (currentAdIndex + 1) % advertisements.size();
                        displayAdvertisement(advertisements.get(currentAdIndex));
                        updateTrainInfo(); // Update train info before displaying the map
                        add(mapPanel, BorderLayout.CENTER); // Add the map panel
                        showingMap = true;
                    }
                    revalidate(); // Revalidate the layout
                    repaint(); // Repaint the panel
                });
            }
        }, 0, 15000); // Change ads every 15 seconds (10s ad + 5s map)
    }
    /**
     * Displays the map with the current train locations.
     */
    private void displayMap() {
        updateTrains(trains);
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
        for (Train train : trains) {
            info.append("Train ").append(train.getId()).append(" is at position (")
                    .append(train.getStation().getX()).append(", ").append(train.getStation().getY()).append(")<br>");
        }
        info.append("</html>");
        trainInfoLabel.setText(info.toString());
    }

    /**
     * Updates the train list with new train locations.
     *
     * @param trains The list of Train objects representing the current train locations
     */
    public void updateTrains(List<Train> trains) {
        this.trains = trains;
        ((MapPanel) mapPanel).updateTrains(trains);
    }

    /**
     * Sets the selected train ID to highlight it on the map.
     *
     * @param trainId The ID of the train to highlight
     */
    public void setSelectedTrainId(int trainId) {
        ((MapPanel) mapPanel).setSelectedTrainId(trainId);
    }
}
