package ca.ucalgary.edu.ensf380;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

//public class AdvertisementPanel extends JPanel {
//    private static final long serialVersionUID = 1L;
//    private List<Advertisement> advertisements;
//    private int currentAdIndex = 0;
//    private JLabel adLabel;
//    private Timer timer;
//
//    public AdvertisementPanel(Connection connection) {
//        setLayout(new BorderLayout());
//        adLabel = new JLabel("", JLabel.CENTER);
//        adLabel.setPreferredSize(new Dimension(800, 600));
//        add(adLabel, BorderLayout.CENTER);
//
//        advertisements = loadAdvertisements(connection);
//        if (advertisements.size() > 0) {
//            displayAdvertisement(advertisements.get(currentAdIndex));
//            startAdRotation();
//        }
//    }
//
//    private List<Advertisement> loadAdvertisements(Connection connection) {
//        List<Advertisement> ads = new ArrayList<>();
//        String query = "SELECT id, title, description, media_file, media_type FROM advertisements";
//        try (PreparedStatement stmt = connection.prepareStatement(query);
//             ResultSet rs = stmt.executeQuery()) {
//            while (rs.next()) {
//                Advertisement ad = new Advertisement(
//                        rs.getInt("id"),
//                        rs.getString("title"),
//                        rs.getString("description"),
//                        rs.getString("media_file"),
//                        rs.getString("media_type")
//                );
//                ads.add(ad);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return ads;
//    }
//
//    private void displayAdvertisement(Advertisement ad) {
//        String mediaType = ad.getMediaType();
//        String mediaFile = ad.getMediaFile();
//
//        if (mediaType.startsWith("image/")) {
//            // Handle image files
//            try {
//                Image image = ImageIO.read(new File(mediaFile));
//                if (image != null) {
//                    ImageIcon icon = new ImageIcon(image);
//                    adLabel.setIcon(icon);
//                    adLabel.setText(""); // Clear text if displaying an image
//                } else {
//                    adLabel.setText("<html><h1>" + ad.getTitle() + "</h1><br>" + ad.getDescription() + "</html>");
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//                adLabel.setText("<html><h1>" + ad.getTitle() + "</h1><br>" + ad.getDescription() + "</html>");
//            }
//        } else {
//            // Handle text or other media types
//            adLabel.setIcon(null);
//            adLabel.setText("<html><h1>" + ad.getTitle() + "</h1><br>" + ad.getDescription() + "</html>");
//        }
//    }
//
//    private void startAdRotation() {
//        timer = new Timer();
//        timer.scheduleAtFixedRate(new TimerTask() {
//            @Override
//            public void run() {
//                SwingUtilities.invokeLater(() -> {
//                    currentAdIndex = (currentAdIndex + 1) % advertisements.size();
//                    displayAdvertisement(advertisements.get(currentAdIndex));
//                });
//            }
//        }, 0, 10000); // Change ads every 10 seconds
//    }
//}
//
//class Advertisement {
//    private int id;
//    private String title;
//    private String description;
//    private String mediaFile;
//    private String mediaType;
//
//    public Advertisement(int id, String title, String description, String mediaFile, String mediaType) {
//        this.id = id;
//        this.title = title;
//        this.description = description;
//        this.mediaFile = mediaFile;
//        this.mediaType = mediaType;
//    }
//
//    public int getId() {
//        return id;
//    }
//
//    public String getTitle() {
//        return title;
//    }
//
//    public String getDescription() {
//        return description;
//    }
//
//    public String getMediaFile() {
//        return mediaFile;
//    }
//
//    public String getMediaType() {
//        return mediaType;
//    }
//}
//



import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.imageio.ImageIO;
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
        String query = "SELECT * FROM advertisements";
        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Advertisement ad = new Advertisement(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getString("media_file"),
                        rs.getString("media_type")
                );
                ads.add(ad);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ads;
    }

    private void displayAdvertisement(Advertisement ad) {
        if (ad.getMediaType().startsWith("image")) {
            // Load and display image
            displayImage(ad.getMediaFile());
        } else {
            // Display text for other types
            adLabel.setText("<html><h1>" + ad.getTitle() + "</h1><br>" + ad.getDescription() + "</html>");
            adLabel.setIcon(null); // Clear any previous image
        }
    }

    private void displayImage(String filePath) {
        try {
            File file = new File(filePath);
            BufferedImage image = ImageIO.read(file);
            if (image != null) {
                ImageIcon icon = new ImageIcon(image);
                adLabel.setIcon(icon);
                adLabel.setText(null); // Clear any previous text
            } else {
                adLabel.setText("Image not found.");
            }
        } catch (IOException e) {
            e.printStackTrace();
            adLabel.setText("Error loading image.");
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

