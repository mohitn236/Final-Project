
package ca.ucalgary.edu.ensf380;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;
import org.apache.batik.anim.dom.SAXSVGDocumentFactory;
import org.apache.batik.bridge.BridgeContext;
import org.apache.batik.bridge.GVTBuilder;
import org.apache.batik.bridge.UserAgentAdapter;
import org.apache.batik.gvt.GraphicsNode;
import org.apache.batik.util.XMLResourceDescriptor;
import org.w3c.dom.Document;

/**
 * The MapPanel class displays a map with train locations.
 */
public class MapPanel extends JPanel {
    private static final long serialVersionUID = 1L;
    private String mapFilePath;
    private List<Train> trains;
    private int selectedTrainId = -1;
    private BufferedImage mapImage;

    public MapPanel(String mapFilePath, List<Train> trains, int selectedTrainId) {
        this.mapFilePath = mapFilePath;
        this.trains = trains;
        this.selectedTrainId = selectedTrainId;
        loadMapImage();
        setPreferredSize(new Dimension(1200, 600)); // Adjust to desired size or use null layout
        setLayout(new BorderLayout()); // Use BorderLayout to fill the panel
    }
    


    /**
     * Loads the map image from the SVG file.
     */
    private void loadMapImage() {
        try {
            String parser = XMLResourceDescriptor.getXMLParserClassName();
            SAXSVGDocumentFactory factory = new SAXSVGDocumentFactory(parser);
            Document doc = factory.createDocument(new File(mapFilePath).toURI().toString());
            GVTBuilder builder = new GVTBuilder();
            BridgeContext ctx = new BridgeContext(new UserAgentAdapter());
            GraphicsNode mapGraphics = builder.build(ctx, doc);

            int width = 800; // set desired width
            int height = 600; // set desired height
            mapImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = mapImage.createGraphics();
            mapGraphics.paint(g2d);
            g2d.dispose();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Updates the list of trains and refreshes the display.
     *
     * @param trains The new list of Train objects
     */
    public void updateTrains(List<Train> trains) {
        this.trains = trains;
        repaint(); // Repaint the panel to reflect the new train data
    }

    /**
     * Sets the ID of the selected train to highlight it on the map.
     *
     * @param selectedTrainId The ID of the train to highlight
     */
    public void setSelectedTrainId(int selectedTrainId) {
        this.selectedTrainId = selectedTrainId;
        repaint(); // Repaint the panel to reflect the selection change
    }

    /**
     * Paints the component, including the map image and train positions.
     *
     * @param g The Graphics object to use for drawing
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (mapImage != null) {
            g.drawImage(mapImage, 0, 0, getWidth(), getHeight(), null);
        }

        if (trains != null) {
            for (Train train : trains) {
                int x = (int) train.getStation().getX();
                int y = (int) train.getStation().getY();
                Color color = (train.getId() == selectedTrainId) ? Color.RED : Color.BLUE;
                drawTrain(g, x, y, color);
            }
        }
    }

    /**
     * Draws a train on the map at the specified coordinates.
     *
     * @param g     The Graphics object to use for drawing
     * @param x     The x-coordinate of the train's position
     * @param y     The y-coordinate of the train's position
     * @param color The color to use for the train
     */
    private void drawTrain(Graphics g, int x, int y, Color color) {
        g.setColor(color);
        g.fillOval(x - 5, y - 5, 10, 10); // Draw a small circle to represent the train
        g.setColor(Color.BLACK);
        g.drawOval(x - 5, y - 5, 10, 10); // Draw an outline around the train
    }
}
