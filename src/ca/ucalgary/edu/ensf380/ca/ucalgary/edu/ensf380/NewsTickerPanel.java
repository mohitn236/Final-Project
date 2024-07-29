//////package ca.ucalgary.edu.ensf380;
//////
//////import javax.swing.*;
//////import java.awt.*;
//////import java.io.BufferedReader;
//////import java.io.InputStreamReader;
//////import java.net.HttpURLConnection;
//////import java.net.URI;
//////import java.net.URL;
//////import java.util.Timer;
//////import java.util.TimerTask;
//////
//////import org.json.JSONArray;
//////import org.json.JSONObject;
//////
//////public class NewsTickerPanel extends JPanel {
//////    private static final long serialVersionUID = 1L;
//////    private JLabel newsLabel;
//////    private Timer timer;
//////    private String newsApiUrl = "https://newsdata.io/api/1/news?apikey=pub_49486d9099ea8f9938983cdb2cfa619527a9c&country=us";
//////
//////    public NewsTickerPanel() {
//////        setLayout(new BorderLayout());
//////        newsLabel = new JLabel("Loading news...");
//////        newsLabel.setForeground(Color.WHITE);
//////        add(newsLabel, BorderLayout.CENTER);
//////        setBackground(Color.BLACK);
//////
//////        timer = new Timer(true);
//////        timer.scheduleAtFixedRate(new TimerTask() {
//////            @Override
//////            public void run() {
//////                SwingUtilities.invokeLater(() -> fetchNews());
//////            }
//////        }, 0, 60000); // Fetch news every 60 seconds
//////    }
//////
//////    private void fetchNews() {
//////        try {
//////            URI uri = new URI(newsApiUrl);
//////            HttpURLConnection connection = (HttpURLConnection) uri.toURL().openConnection();
//////            connection.setRequestMethod("GET");
//////
//////            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
//////            String inputLine;
//////            StringBuilder content = new StringBuilder();
//////            while ((inputLine = in.readLine()) != null) {
//////                content.append(inputLine);
//////            }
//////            in.close();
//////            connection.disconnect();
//////
//////            JSONObject jsonResponse = new JSONObject(content.toString());
//////            JSONArray articles = jsonResponse.getJSONArray("results");
//////
//////            StringBuilder newsText = new StringBuilder("<html>");
//////            for (int i = 0; i < articles.length(); i++) {
//////                JSONObject article = articles.getJSONObject(i);
//////                newsText.append(article.getString("title")).append(" | ");
//////            }
//////            newsText.append("</html>");
//////
//////            newsLabel.setText(newsText.toString());
//////        } catch (Exception e) {
//////            e.printStackTrace();
//////            newsLabel.setText("Failed to load news");
//////        }
//////    }
//////}
//

package ca.ucalgary.edu.ensf380;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONArray;
import org.json.JSONObject;

public class NewsTickerPanel extends JPanel {
    private static final long serialVersionUID = 1L;
    private JLabel newsLabel;
    private Timer timer;
    private String apiKey = "pub_49486d9099ea8f9938983cdb2cfa619527a9c"; // Replace with your actual API key
    private String newsApiUrl = "https://newsdata.io/api/1/news?apikey=" + apiKey;

    public NewsTickerPanel(String keyword) {
        setLayout(new BorderLayout());
        newsLabel = new JLabel("Loading news...");
        newsLabel.setForeground(Color.WHITE);
        add(newsLabel, BorderLayout.CENTER);
        
        newsLabel.setFont(new Font("Sans Serif", Font.PLAIN,14)); // different font
        setBackground(Color.BLACK);
        

        if (keyword != null && !keyword.isEmpty()) {
            newsApiUrl += "&q=" + URLEncoder.encode(keyword, StandardCharsets.UTF_8);
        }

        timer = new Timer(true);
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                SwingUtilities.invokeLater(() -> fetchNews());
            }
        }, 0, 60000); // Fetch news every 60 seconds
    }

    private void fetchNews() {
        try {
            URI uri = new URI(newsApiUrl);
            HttpURLConnection connection = (HttpURLConnection) uri.toURL().openConnection();
            connection.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            connection.disconnect();

            JSONObject jsonResponse = new JSONObject(content.toString());
            JSONArray articles = jsonResponse.getJSONArray("results");

            StringBuilder newsText = new StringBuilder("<html>");
            for (int i = 0; i < articles.length(); i++) {
                JSONObject article = articles.getJSONObject(i);
                newsText.append(article.getString("title")).append(" | ");
            }
            newsText.append("</html>");

            newsLabel.setText(newsText.toString());
        } catch (Exception e) {
            e.printStackTrace();
            newsLabel.setText("Failed to load news");
        }
    }
}







