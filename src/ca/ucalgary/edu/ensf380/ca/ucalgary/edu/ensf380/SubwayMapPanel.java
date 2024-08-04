package ca.ucalgary.edu.ensf380;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class SubwayMapPanel extends JPanel {
    private static final long serialVersionUID = 1L;
	private final Map<String, List<Station>> lineStations;
    private List<Train> activeTrains;
    private static final int PANEL_WIDTH = 1200;
    private static final int PANEL_HEIGHT = 800;
    private static final Map<String, Color> LINE_COLORS;

    static {
        LINE_COLORS = new HashMap<>();
        LINE_COLORS.put("Red", Color.RED);
        LINE_COLORS.put("Green", Color.GREEN);
        LINE_COLORS.put("Blue", Color.BLUE);
    }

    public SubwayMapPanel(Map<String, List<Station>> lineStations, List<Train> activeTrains) {
        this.lineStations = lineStations;
        this.activeTrains = activeTrains;
        setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        setBackground(Color.WHITE);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        enableAntialiasing(g2d);
        applyScaling(g2d);
        drawMapComponents(g2d);
    }

    private void enableAntialiasing(Graphics2D g2d) {
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    }

    private void applyScaling(Graphics2D g2d) {
        double xScale = getWidth() / (double) PANEL_WIDTH;
        double yScale = getHeight() / (double) PANEL_HEIGHT;
        g2d.scale(xScale, yScale);
    }

    private void drawMapComponents(Graphics2D g2d) {
        drawLinesAndStations(g2d);
        drawTrainsOnMap(g2d);
    }

    private void drawLinesAndStations(Graphics2D g2d) {
        Iterator<Map.Entry<String, List<Station>>> iterator = lineStations.entrySet().iterator();

        while (iterator.hasNext()) {
            Map.Entry<String, List<Station>> entry = iterator.next();
            String line = entry.getKey();
            List<Station> stations = entry.getValue();
            g2d.setColor(LINE_COLORS.getOrDefault(line, Color.BLACK));

            int i = 0;
            while (i < stations.size() - 1) {
                Station currentStation = stations.get(i);
                Station nextStation = stations.get(i + 1);
                //g2d.drawLine(currentStation.getX(), currentStation.getY(), nextStation.getX(), nextStation.getY());
                i++;
            }
        }
    }

    private void drawTrainsOnMap(Graphics2D g2d) {
        int index = 0;
        int trainSize = activeTrains.size();
        while (index < trainSize) {
            Train train = activeTrains.get(index);
            Color trainColor = getTrainColor(train);
            g2d.setColor(trainColor);
            Point trainPosition = calculateTrainPosition(train);
            drawTrain(g2d, trainPosition);
            index++;
        }
    }

    private Color getTrainColor(Train train) {
        return LINE_COLORS.getOrDefault(train.getLine(), Color.BLACK);
    }

    private Point calculateTrainPosition(Train train) {
        int x = train.getCurrentStation().getX() - 5;
        int y = train.getCurrentStation().getY() - 5;
        return new Point(x, y);
    }

    private void drawTrain(Graphics2D g2d, Point position) {
        int trainDiameter = 10;
        g2d.fillOval(position.x, position.y, trainDiameter, trainDiameter);
    }

    public void refreshTrains(List<Train> newActiveTrains) {
        this.activeTrains = newActiveTrains;
        repaint();
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(PANEL_WIDTH, PANEL_HEIGHT);
    }
}
