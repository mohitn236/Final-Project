package subway_screen;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class NewsFetcher {
    private static final String API_KEY = "pub_49486d9099ea8f9938983cdb2cfa619527a9c"; // Replace with your actual API key
    private static final String BASE_URL = "https://newsdata.io/api/1/news";

    public static JsonNode fetchNews(String keywords) {
        String url = BASE_URL + "?q=" + keywords + "&language=en";
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(url);
            request.setHeader("Authorization", API_KEY);

            HttpResponse response = httpClient.execute(request);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != 200) {
                System.out.println("Failed to fetch news. HTTP error code: " + statusCode);
                return null;
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            StringBuilder result = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }

            ObjectMapper mapper = new ObjectMapper();
            return mapper.readTree(result.toString());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
