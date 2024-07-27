package ca.ucalgary.edu.ensf380;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class AdvertisementPanel extends JPanel {
    private List<Advertisement> advertisements;
    private int currentAdIndex = 0;
    private JLabel adLabel;
    private Timer timer;

    public AdvertisementPanel(Connection connection) {
        setLayout(new BorderLayout());
        adLabel = new JLabel("", JLabel.CENTER);
        adLabel.setPreferredSize(new Dimension(800, 600));
        add(adLabel, BorderLayout.CENTER);

        advertisements = loadAdvertisements(connection);
        if (advertisements.size() > 0) {
            displayAdvertisement(advertisements.get(currentAdIndex));
            startAdRotation();
        }
    }

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

    private void displayAdvertisement(Advertisement ad) {
        byte[] imageData = ad.getMediaFile();
        if (imageData != null && imageData.length > 0) {
            try {
                BufferedImage img = ImageIO.read(new ByteArrayInputStream(imageData));
                if (img != null) {
                    ImageIcon icon = new ImageIcon(img);
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

    private void startAdRotation() {
        timer = new Timer(true);
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                SwingUtilities.invokeLater(() -> {
                    currentAdIndex = (currentAdIndex + 1) % advertisements.size();
                    displayAdvertisement(advertisements.get(currentAdIndex));
                });
            }
        }, 0, 10000); // Change ads every 10 seconds
    }
}
