package subway_screen;

import javax.swing.*;
import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;
import com.fasterxml.jackson.databind.JsonNode;

public class NewsDisplay extends JFrame {
    private static final long serialVersionUID = 1L; // Add serialVersionUID
    private JLabel newsLabel;
    private int xPosition;

    public NewsDisplay(String keywords) {
        setTitle("Subway News Display");
        setSize(800, 100);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        newsLabel = new JLabel("Loading news...");
        newsLabel.setFont(new Font("Arial", Font.BOLD, 20));
        newsLabel.setForeground(Color.WHITE);
        JPanel panel = new JPanel(null);
        panel.setBackground(Color.BLACK);
        panel.add(newsLabel);
        add(panel);
        fetchAndDisplayNews(keywords);
    }

    private void fetchAndDisplayNews(String keywords) {
        new Thread(() -> {
            JsonNode newsData = NewsFetcher.fetchNews(keywords);
            if (newsData != null && newsData.has("results")) { // Adjust based on API response
                StringBuilder newsText = new StringBuilder();
                for (JsonNode article : newsData.get("results")) {
                    String title = article.get("title").asText();
                    newsText.append(title).append(" | ");
                }
                updateNewsLabel(newsText.toString());
            } else {
                updateNewsLabel("No news available.");
            }
        }).start();
    }

    private void updateNewsLabel(String text) {
        SwingUtilities.invokeLater(() -> {
            newsLabel.setText(text);
            xPosition = getWidth();
            startScrolling();
        });
    }

    private void startScrolling() {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                xPosition--;
                if (xPosition < -newsLabel.getWidth()) {
                    xPosition = getWidth();
                }
                newsLabel.setBounds(xPosition, 20, getWidth(), 50);
            }
        };
        timer.scheduleAtFixedRate(task, 0, 25);
    }

    public static void main(String[] args) {
        String keywords = args.length > 0 ? args[0] : "general";
        SwingUtilities.invokeLater(() -> {
            NewsDisplay frame = new NewsDisplay(keywords);
            frame.setVisible(true);
        });
    }
}
