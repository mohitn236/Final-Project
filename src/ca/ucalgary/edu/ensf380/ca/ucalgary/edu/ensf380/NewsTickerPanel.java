package ca.ucalgary.edu.ensf380;

import javax.swing.*;
import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

public class NewsTickerPanel extends JPanel {
    private static final long serialVersionUID = 1L;
    private JLabel newsLabel;
    private Timer timer;

    public NewsTickerPanel() {
        setLayout(new BorderLayout());
        newsLabel = new JLabel("News Ticker", JLabel.CENTER);
        newsLabel.setPreferredSize(new Dimension(800, 50));
        add(newsLabel, BorderLayout.CENTER);
        startNewsTicker();
    }

    private void startNewsTicker() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                SwingUtilities.invokeLater(() -> {
                    newsLabel.setText("Updated News: " + System.currentTimeMillis());
                });
            }
        }, 0, 10000); // Update news every 10 seconds
    }
}
