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

public class WeatherPanel {

    public String fetchWeatherReport(String location) throws Exception {
        String baseEndpoint = "https://wttr.in/";
        String requestFormat = URLEncoder.encode("%C %t %w %p %P", StandardCharsets.UTF_8.toString());
        String requestUrl = baseEndpoint + location + "?format=" + requestFormat + "&2";
        String weatherData = getWeatherData(requestUrl);
        String detailedReport = prepareWeatherData(weatherData);
        String currentDate = getCurrentDate();
        return formatWeatherReport(location, currentDate, detailedReport);
    }

    private static String getCurrentDate() {
        LocalDate today = LocalDate.now();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("EEEE, MMMM dd");
        return today.format(dateFormatter);
    }

    private static String getWeatherData(String requestUrl) throws Exception {
        URI uri = new URI(requestUrl);
        URL url = uri.toURL();
        HttpURLConnection httpConnection = (HttpURLConnection) url.openConnection();
        httpConnection.setRequestMethod("GET");

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpConnection.getInputStream()));
        StringBuilder responseContent = new StringBuilder();
        String line;

        while ((line = bufferedReader.readLine()) != null) {
            responseContent.append(line).append("\n");
        }
        bufferedReader.close();
        httpConnection.disconnect();
        return responseContent.toString();
    }

    private static String prepareWeatherData(String weatherData) {
        String[] dataSegments = weatherData.split("\\s+");
        StringBuilder organizedData = new StringBuilder();
        for (String segment : dataSegments) {
            if (!segment.trim().isEmpty()) {
                // Clean up any unwanted characters
                String cleanedSegment = segment.replaceAll("[^\\p{L}\\p{N}\\s]", "").trim();
                if (isWeatherDescriptor(cleanedSegment)) {
                    cleanedSegment = cleanedSegment + " " + getWeatherEmoji(cleanedSegment);
                }
                organizedData.append(cleanedSegment).append("\n");
            }
        }
        return organizedData.toString().trim();
    }

    private static boolean isWeatherDescriptor(String segment) {
        String[] descriptors = {"Partly", "Cloudy", "Sunny", "Rain", "Snow"};
        for (String descriptor : descriptors) {
            if (segment.contains(descriptor)) {
                return true;
            }
        }
        return false;
    }

    private static String getWeatherEmoji(String descriptor) {
        switch (descriptor.toLowerCase()) {
            case "partly":
                return "⛅";
            case "cloudy":
                return "☁️";
            case "sunny":
                return "☀️";
            case "rain":
                return "🌧️";
            case "snow":
                return "❄️";
            default:
                return "";
        }
    }

    private static String formatWeatherReport(String city, String date, String report) {
        StringBuilder formattedReport = new StringBuilder();
        formattedReport.append("Weather Report\n");
        formattedReport.append("====================\n");
        formattedReport.append("City: ").append(city).append("\n");
        formattedReport.append("Date: ").append(date).append("\n");
        formattedReport.append("\nWeather Details:\n");
        formattedReport.append(report).append("\n");
        formattedReport.append("====================\n");
        return formattedReport.toString();
    }
}
