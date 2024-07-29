//package ca.ucalgary.edu.ensf380;
//
//import javax.imageio.ImageIO;
//import javax.swing.*;
//import java.awt.*;
//import java.awt.image.BufferedImage;
//import java.io.ByteArrayInputStream;
//import java.io.IOException;
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Timer;
//import java.util.TimerTask;
//
//public class AdvertisementPanel extends JPanel {
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
//        String query = "SELECT id, title, description, media FROM images";
//        try (PreparedStatement stmt = connection.prepareStatement(query);
//             ResultSet rs = stmt.executeQuery()) {
//            while (rs.next()) {
//                Advertisement ad = new Advertisement(
//                        rs.getInt("id"),
//                        rs.getString("title"),
//                        rs.getString("description"),
//                        rs.getBytes("media")
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
//        byte[] imageData = ad.getMediaFile();
//        if (imageData != null && imageData.length > 0) {
//            try {
//                BufferedImage img = ImageIO.read(new ByteArrayInputStream(imageData));
//                if (img != null) {
//                    ImageIcon icon = new ImageIcon(img);
//                    adLabel.setIcon(icon);
//                    adLabel.setText(null); // Clear any previous text
//                } else {
//                    adLabel.setText("<html><h1>" + ad.getTitle() + "</h1><br>" + ad.getDescription() + "</html>");
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//                adLabel.setText("<html><h1>" + ad.getTitle() + "</h1><br>" + ad.getDescription() + "</html>");
//            }
//        } else {
//            adLabel.setText("<html><h1>" + ad.getTitle() + "</h1><br>" + ad.getDescription() + "</html>");
//        }
//    }
//
//    private void startAdRotation() {
//        timer = new Timer(true);
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

//package ca.ucalgary.edu.ensf380;
//
//import javax.imageio.ImageIO;
//import javax.swing.*;
//import java.awt.*;
//import java.awt.image.BufferedImage;
//import java.io.ByteArrayInputStream;
//import java.io.IOException;
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Timer;
//import java.util.TimerTask;
//
//public class AdvertisementPanel extends JPanel {
//    private List<Advertisement> advertisements;
//    private int currentAdIndex = 0;
//    private JLabel adLabel;
//    private Timer timer;
//    private JPanel mapPanel;
//    private boolean showingMap = false;
//
//    public AdvertisementPanel(Connection connection) {
//        setLayout(new BorderLayout());
//        adLabel = new JLabel("", JLabel.CENTER);
//        adLabel.setPreferredSize(new Dimension(800, 600));
//        add(adLabel, BorderLayout.CENTER);
//
//        mapPanel = createMapPanel();
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
//        String query = "SELECT id, title, description, media FROM images";
//        try (PreparedStatement stmt = connection.prepareStatement(query);
//             ResultSet rs = stmt.executeQuery()) {
//            while (rs.next()) {
//                Advertisement ad = new Advertisement(
//                        rs.getInt("id"),
//                        rs.getString("title"),
//                        rs.getString("description"),
//                        rs.getBytes("media")
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
//        byte[] imageData = ad.getMediaFile();
//        if (imageData != null && imageData.length > 0) {
//            try {
//                BufferedImage img = ImageIO.read(new ByteArrayInputStream(imageData));
//                if (img != null) {
//                    ImageIcon icon = new ImageIcon(img);
//                    adLabel.setIcon(icon);
//                    adLabel.setText(null); // Clear any previous text
//                } else {
//                    adLabel.setText("<html><h1>" + ad.getTitle() + "</h1><br>" + ad.getDescription() + "</html>");
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//                adLabel.setText("<html><h1>" + ad.getTitle() + "</h1><br>" + ad.getDescription() + "</html>");
//            }
//        } else {
//            adLabel.setText("<html><h1>" + ad.getTitle() + "</h1><br>" + ad.getDescription() + "</html>");
//        }
//    }
//
//    private JPanel createMapPanel() {
//        JPanel panel = new JPanel();
//        JLabel label = new JLabel("Map showing train positions", SwingConstants.CENTER);
//        label.setFont(new Font("Arial", Font.BOLD, 40));
//        panel.add(label);
//        return panel;
//    }
//
//    private void displayMap() {
//        remove(adLabel);
//        add(mapPanel, BorderLayout.CENTER);
//        revalidate();
//        repaint();
//    }
//
//    private void hideMap() {
//        remove(mapPanel);
//        add(adLabel, BorderLayout.CENTER);
//        revalidate();
//        repaint();
//    }
//
//    private void startAdRotation() {
//        timer = new Timer(true);
//        timer.scheduleAtFixedRate(new TimerTask() {
//            @Override
//            public void run() {
//                SwingUtilities.invokeLater(() -> {
//                    if (showingMap) {
//                        hideMap();
//                        showingMap = false;
//                    } else {
//                        currentAdIndex = (currentAdIndex + 1) % advertisements.size();
//                        displayAdvertisement(advertisements.get(currentAdIndex));
//                        new Timer().schedule(new TimerTask() {
//                            @Override
//                            public void run() {
//                                SwingUtilities.invokeLater(() -> {
//                                    displayMap();
//                                    showingMap = true;
//                                });
//                            }
//                        }, 10000);
//                    }
//                });
//            }
//        }, 0, 10000);
//    }
//}

//package ca.ucalgary.edu.ensf380;
//
//import javax.imageio.ImageIO;
//import javax.swing.*;
//import java.awt.*;
//import java.awt.image.BufferedImage;
//import java.io.ByteArrayInputStream;
//import java.io.File;
//import java.io.IOException;
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Timer;
//import java.util.TimerTask;
//
//public class AdvertisementPanel extends JPanel {
//    private List<Advertisement> advertisements;
//    private int currentAdIndex = 0;
//    private JLabel adLabel;
//    private Timer timer;
//    private JPanel mapPanel;
//    private boolean showingMap = false;
//
//    public AdvertisementPanel(Connection connection) {
//        setLayout(new BorderLayout());
//        adLabel = new JLabel("", JLabel.CENTER);
//        adLabel.setPreferredSize(new Dimension(800, 600));
//        add(adLabel, BorderLayout.CENTER);
//
//        mapPanel = createMapPanel();
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
//        String query = "SELECT id, title, description, media FROM images";
//        try (PreparedStatement stmt = connection.prepareStatement(query);
//             ResultSet rs = stmt.executeQuery()) {
//            while (rs.next()) {
//                Advertisement ad = new Advertisement(
//                        rs.getInt("id"),
//                        rs.getString("title"),
//                        rs.getString("description"),
//                        rs.getBytes("media")
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
//        byte[] imageData = ad.getMediaFile();
//        if (imageData != null && imageData.length > 0) {
//            try {
//                BufferedImage img = ImageIO.read(new ByteArrayInputStream(imageData));
//                if (img != null) {
//                    ImageIcon icon = new ImageIcon(img);
//                    adLabel.setIcon(icon);
//                    adLabel.setText(null); // Clear any previous text
//                } else {
//                    adLabel.setText("<html><h1>" + ad.getTitle() + "</h1><br>" + ad.getDescription() + "</html>");
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//                adLabel.setText("<html><h1>" + ad.getTitle() + "</h1><br>" + ad.getDescription() + "</html>");
//            }
//        } else {
//            adLabel.setText("<html><h1>" + ad.getTitle() + "</h1><br>" + ad.getDescription() + "</html>");
//        }
//    }
//
//    private JPanel createMapPanel() {
//        JPanel panel = new JPanel(new BorderLayout());
//        JLabel label = new JLabel("", JLabel.CENTER);
//        label.setPreferredSize(new Dimension(800, 600));
//        panel.add(label, BorderLayout.CENTER);
//
//        try {
//            BufferedImage mapImage = ImageIO.read(new File("/Users/shivansh/Downloads/Map.png"));
//            label.setIcon(new ImageIcon(mapImage));
//        } catch (IOException e) {
//            e.printStackTrace();
//            label.setText("Map could not be loaded");
//        }
//
//        return panel;
//    }
//
//    private void displayMap() {
//        remove(adLabel);
//        add(mapPanel, BorderLayout.CENTER);
//        revalidate();
//        repaint();
//    }
//
//    private void hideMap() {
//        remove(mapPanel);
//        add(adLabel, BorderLayout.CENTER);
//        revalidate();
//        repaint();
//    }
//
//    private void startAdRotation() {
//        timer = new Timer(true);
//        timer.scheduleAtFixedRate(new TimerTask() {
//            @Override
//            public void run() {
//                SwingUtilities.invokeLater(() -> {
//                    if (showingMap) {
//                        hideMap();
//                        showingMap = false;
//                    } else {
//                        currentAdIndex = (currentAdIndex + 1) % advertisements.size();
//                        displayAdvertisement(advertisements.get(currentAdIndex));
//                        new Timer().schedule(new TimerTask() {
//                            @Override
//                            public void run() {
//                                SwingUtilities.invokeLater(() -> {
//                                    displayMap();
//                                    showingMap = true;
//                                });
//                            }
//                        }, 10000);
//                    }
//                });
//            }
//        }, 0, 15000); // Change ads every 15 seconds (10s ad + 5s map)
//    }
//}

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
import java.util.*;
import java.util.Timer;
import java.util.List;

public class AdvertisementPanel extends JPanel {
    private List<Advertisement> advertisements;
    private int currentAdIndex = 0;
    private JLabel adLabel;
    private Timer timer;
    private JPanel mapPanel;
    private boolean showingMap = false;
    private BufferedImage mapImage;
    private String csvFilePath = "src/map/Map.csv"; 
    private List<TrainLocation> trainLocations;
    private final int IMAGE_WIDTH = 800;
    private final int IMAGE_HEIGHT = 600;
    private static final long serialVersionUID = 1L;


    public AdvertisementPanel(Connection connection) {
        setLayout(new BorderLayout());
        setBackground(new Color(255, 228, 196));
        adLabel = new JLabel("", JLabel.CENTER);
        adLabel.setPreferredSize(new Dimension(IMAGE_WIDTH, IMAGE_HEIGHT));
        adLabel.setFont(new Font("Arial", Font.BOLD, 16)); // Set a custom font
        add(adLabel, BorderLayout.CENTER);
        
        setBorder(BorderFactory.createLineBorder(Color.BLACK, 2)); // bordering the panel

        mapPanel = createMapPanel();

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

    private JPanel createMapPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel label = new JLabel("", JLabel.CENTER);
        label.setPreferredSize(new Dimension(IMAGE_WIDTH, IMAGE_HEIGHT));
        panel.add(label, BorderLayout.CENTER);

        try {
            BufferedImage rawMapImage = ImageIO.read(new File("src/map/Map.png"));
            mapImage = new BufferedImage(IMAGE_WIDTH, IMAGE_HEIGHT, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g = mapImage.createGraphics();
            g.drawImage(rawMapImage, 0, 0, IMAGE_WIDTH, IMAGE_HEIGHT, null);
            g.dispose();

            updateTrainLocations();
            BufferedImage updatedMapImage = overlayTrainLocations(mapImage);
            label.setIcon(new ImageIcon(updatedMapImage));
        } catch (IOException e) {
            e.printStackTrace();
            label.setText("Map could not be loaded");
        }

        return panel;
    }

    private void updateTrainLocations() {
        trainLocations = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(csvFilePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length >= 6) {
                    try {
                        int stationNumber = Integer.parseInt(values[2]);
                        int x = Integer.parseInt(values[5]);
                        int y = Integer.parseInt(values[6]);
                        trainLocations.add(new TrainLocation(stationNumber, x, y));
                    } catch (NumberFormatException e) {
                        // Skip invalid lines
                        System.err.println("Skipping invalid line: " + line);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private BufferedImage overlayTrainLocations(BufferedImage baseImage) {
        BufferedImage overlayedImage = new BufferedImage(baseImage.getWidth(), baseImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = overlayedImage.createGraphics();
        g.drawImage(baseImage, 0, 0, null);

        g.setColor(Color.RED);
        for (TrainLocation location : trainLocations) {
            System.out.println("Overlaying train at (" + location.getX() + ", " + location.getY() + ")");
            g.fillOval(location.getX() - 5, location.getY() - 5, 10, 10);
        }

        g.dispose();
        return overlayedImage;
    }

    private void displayMap() {
        updateTrainLocations();
        BufferedImage updatedMapImage = overlayTrainLocations(mapImage);
        ((JLabel) mapPanel.getComponent(0)).setIcon(new ImageIcon(updatedMapImage));
        remove(adLabel);
        add(mapPanel, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    private void hideMap() {
        remove(mapPanel);
        add(adLabel, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

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
                        new Timer().schedule(new TimerTask() {
                            @Override
                            public void run() {
                                SwingUtilities.invokeLater(() -> {
                                    displayMap();
                                    showingMap = true;
                                });
                            }
                        }, 10000);
                    }
                });
            }
        }, 0, 15000); // Change ads every 15 seconds (10s ad + 5s map)
    }
}

class TrainLocation {
    private int id;
    private int x;
    private int y;

    public TrainLocation(int id, int x, int y) {
        this.id = id;
        this.x = x;
        this.y = y;
    }

    public int getId() {
        return id;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}





