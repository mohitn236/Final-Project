//package ca.ucalgary.edu.ensf380;
//
//import javax.swing.*;
//import java.awt.*;
//
//public class WeatherPanel extends JPanel {
//    private static final long serialVersionUID = 1L;
//    private JLabel weatherLabel;
//
//    public WeatherPanel() {
//        setLayout(new BorderLayout());
//        weatherLabel = new JLabel("Weather Information", JLabel.CENTER);
//        weatherLabel.setPreferredSize(new Dimension(800, 50));
//        add(weatherLabel, BorderLayout.CENTER);
//        updateWeatherInfo();
//    }
//
//    private void updateWeatherInfo() {
//        String weatherReport = "Calgary,CA\n\n   _`/\"\".-.     Patchy rain nearby\n    ,\\_(   ).   +12(10) °C\n     /(___(__)  ↘ 23 km/h\n       ‘ ‘ ‘ ‘  9 km\n      ‘ ‘ ‘ ‘   0.7 mm";
//        weatherLabel.setText("<html><pre>" + weatherReport + "</pre></html>");
//    }
//}
