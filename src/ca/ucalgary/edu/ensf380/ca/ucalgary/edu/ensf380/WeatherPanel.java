package ca.ucalgary.edu.ensf380;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * The WeatherPanel class provides methods to fetch and format weather reports for a given location.
 */
public class WeatherPanel {

    /**
     * Fetches the weather report for the specified location.
     *
     * @param location the location for which to fetch the weather report
     * @return a formatted weather report as a String
     * @throws Exception if an error occurs during the fetching of weather data
     */
    public String fetchWeatherReport(String location) throws Exception {
        String baseEndpoint = "https://wttr.in/";
        String requestFormat = URLEncoder.encode("%C %t %w %p %P", StandardCharsets.UTF_8.toString());
        String requestUrl = baseEndpoint + location + "?format=" + requestFormat + "&2";
        
        String currentDate = getCurrentDate();
        String rawWeatherData = fetchRawWeatherData(requestUrl);
        
        // Include the raw weather data in the formatted report
        String detailedReport = processWeatherData(rawWeatherData);
        return generateWeatherReport(location, currentDate, rawWeatherData, detailedReport);
    }

    /**
     * Gets the current date in the format "YYYY, MMMM dd".
     *
     * @return the current date as a String
     */
    private static String getCurrentDate() {
        LocalDate today = LocalDate.now();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("YYYY, MMMM dd");
        return today.format(dateFormatter);
    }

    /**
     * Fetches weather data from the specified URL.
     *
     * @param requestUrl the URL to fetch weather data from
     * @return the weather data as a String
     * @throws Exception if an error occurs during the fetching of weather data
     */
    private static String fetchRawWeatherData(String requestUrl) throws Exception {
        URI uri = new URI(requestUrl);
        URL url = uri.toURL();
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line).append("\n");
            }
            return response.toString();
        } finally {
            connection.disconnect();
        }
    }

    /**
     * Processes and organizes the fetched weather data.
     *
     * @param weatherData the raw weather data as a String
     * @return the organized weather data as a String
     */
    private static String processWeatherData(String weatherData) {
        if (weatherData == null || weatherData.trim().isEmpty()) {
            return "No weather data available.";
        }

        String[] dataSegments = weatherData.split("\\s+");
        StringBuilder organizedData = new StringBuilder();
        for (String segment : dataSegments) {
            String cleanedSegment = segment.replaceAll("[^\\p{L}\\p{N}\\s]", "").trim();
            if (!cleanedSegment.isEmpty() && isRelevantWeatherDescriptor(cleanedSegment)) {
                organizedData.append(cleanedSegment).append("\n");
            }
        }
        return organizedData.toString().trim();
    }

    /**
     * Checks if the given segment is a relevant weather descriptor.
     *
     * @param segment the segment to check
     * @return true if the segment is a relevant weather descriptor, false otherwise
     */
    private static boolean isRelevantWeatherDescriptor(String segment) {
        String[] descriptors = { "Cloudy", "Sunny", "Rain", "Snow","Partly"};
        for (String descriptor : descriptors) {
            if (segment.contains(descriptor)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Formats the weather report for display.
     *
     * @param location the location for which the weather report is generated
     * @param date the date of the weather report
     * @param rawWeatherData the raw weather data
     * @param processedWeatherData the organized weather data
     * @return the formatted weather report as a String
     */
    private static String generateWeatherReport(String location, String date, String rawWeatherData, String processedWeatherData) {
        return String.format(
            "Weather Report\n====================\nCity: %s\nDate: %s\n\n Weather Data:\n%s\n\n",
            location, date, rawWeatherData
        );
    }
}



