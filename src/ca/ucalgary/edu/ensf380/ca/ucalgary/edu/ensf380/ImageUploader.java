package ca.ucalgary.edu.ensf380;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ImageUploader {
    private static final String URL = "jdbc:postgresql://localhost:5432/advertisements";
    private static final String USERNAME = "oop";
    private static final String PASSWORD = "ucalgary";

    public static void main(String[] args) {
        String[] imagePaths = {"/Users/mohit/Desktop/Coding/380summer/Final-Project/src/Media/burger.jpeg", "/Users/mohit/Desktop/Coding/380summer/Final-Project/src/Media/subway.jpeg", "/Users/mohit/Desktop/Coding/380summer/Final-Project/src/Media/pizzahut.jpeg"};
        String[] titles = {"Burger Ad", "Pizza Hut Ad", "Subway Ad"};
        String[] descriptions = {
                "A delicious burger advertisement",
                "Pizza Hut advertisement",
                "Subway new series menu advertisement"
        };

        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            for (int i = 0; i < imagePaths.length; i++) {
                insertImage(conn, titles[i], descriptions[i], new File(imagePaths[i]));
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    private static void insertImage(Connection conn, String title, String description, File file) throws SQLException, IOException {
        String sql = "INSERT INTO images (title, description, media) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql);
             FileInputStream fis = new FileInputStream(file)) {
            pstmt.setString(1, title);
            pstmt.setString(2, description);
            pstmt.setBinaryStream(3, fis, (int) file.length());
            pstmt.executeUpdate();
        }
    }
}
