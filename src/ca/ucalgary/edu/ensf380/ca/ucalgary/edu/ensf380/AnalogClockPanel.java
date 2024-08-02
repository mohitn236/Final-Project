package ca.ucalgary.edu.ensf380;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalTime;

/**
 * The AnalogClockPanel class is a custom Swing panel that displays an analog clock.
 * The clock updates every second to reflect the current time.
 */
public class AnalogClockPanel extends JPanel {
    private static final int CLOCK_BORDER = 10; // Border space around the clock

    /**
     * Constructs an AnalogClockPanel object with a preferred size and starts a timer to update the clock every second.
     */
    public AnalogClockPanel() {
        setPreferredSize(new Dimension(300, 300)); // Set initial preferred size
        Timer timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                repaint();
            }
        });
        timer.start();
    }

    /**
     * Paints the clock face and hands on the panel.
     *
     * @param g the Graphics object used for painting
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        LocalTime time = LocalTime.now();

        int width = getWidth();
        int height = getHeight();
        int diameter = Math.min(width, height) - CLOCK_BORDER * 2; // Make clock fit within the panel
        int centerX = width / 2;
        int centerY = height / 2;

        // Draw clock face
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(Color.WHITE);
        g2d.fillOval(centerX - diameter / 2, centerY - diameter / 2, diameter, diameter);

        g2d.setColor(Color.BLACK);
        g2d.drawOval(centerX - diameter / 2, centerY - diameter / 2, diameter, diameter);

        // Draw clock hands
        drawHand(g2d, centerX, centerY, time.getHour() % 12, 12, diameter * 0.35, 6, Color.BLACK); // Hour hand
        drawHand(g2d, centerX, centerY, time.getMinute(), 60, diameter * 0.45, 4, Color.BLACK); // Minute hand
        drawHand(g2d, centerX, centerY, time.getSecond(), 60, diameter * 0.4, 1, Color.RED); // Second hand
    }

    /**
     * Draws a clock hand on the panel.
     *
     * @param g2d       the Graphics2D object used for painting
     * @param centerX   the x-coordinate of the center of the clock
     * @param centerY   the y-coordinate of the center of the clock
     * @param value     the current value (e.g., hour, minute, second) to be represented by the hand
     * @param maxValue  the maximum value (e.g., 12 for hours, 60 for minutes and seconds)
     * @param length    the length of the hand
     * @param width     the width of the hand
     * @param color     the color of the hand
     */
    private void drawHand(Graphics2D g2d, int centerX, int centerY, int value, int maxValue, double length, int width, Color color) {
        double angle = Math.toRadians((value / (double) maxValue) * 360 - 90);
        int x = (int) (centerX + length * Math.cos(angle));
        int y = (int) (centerY + length * Math.sin(angle));

        g2d.setColor(color);
        g2d.setStroke(new BasicStroke(width));
        g2d.drawLine(centerX, centerY, x, y);
    }
}
