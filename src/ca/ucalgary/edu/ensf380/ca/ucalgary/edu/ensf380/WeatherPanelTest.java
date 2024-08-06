package ca.ucalgary.edu.ensf380;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class WeatherPanelTest {

    @Test
    public void testFetchWeatherReport() throws Exception {
        WeatherPanel weatherPanel = new WeatherPanelStub();
        String location = "Calgary";
        String expectedWeatherData = "Weather Report\n====================\nCity: Calgary\nDate: 2024, August 05\n\n Weather Data:\nCloudy 23°C 5km/h 0mm 1012hPa\n\n";

        String actualWeatherData = weatherPanel.fetchWeatherReport(location);
        assertEquals(expectedWeatherData, actualWeatherData);
    }

    // Stub class to override network-related methods
    class WeatherPanelStub extends WeatherPanel {

        // Override the fetchRawWeatherData method to return a fixed response
        protected String fetchRawWeatherData(String requestUrl) {
            // Return simulated raw weather data
            return "Cloudy 23°C 5km/h 0mm 1012hPa";
        }

        // Override the getCurrentDate method to return a fixed date
        protected String getCurrentDate() {
            return "2024, August 05"; // Fixed date for testing
        }
    }
}
