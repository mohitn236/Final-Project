//package ca.ucalgary.edu.ensf380;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.PreparedStatement;
//import java.sql.SQLException;
//
///**
// * The ImageUploader class provides functionality to upload image files to a PostgreSQL database.
// * It reads image files and associated metadata, then inserts this data into a database table.
// */
//public class ImageUploader {
//    private static final String URL = "jdbc:postgresql://localhost:5432/advertisements";
//    private static final String USERNAME = "oop";
//    private static final String PASSWORD = "ucalgary";
//
//    /**
//     * The main method that initializes image paths and metadata, and uploads these images to the database.
//     *
//     * @param args Command-line arguments (not used)
//     */
//    public static void main(String[] args) {
//        String[] imagePaths = {"src/media/burger.jpeg", "src/media/pizzahut.jpeg", "src/media/subway.jpeg"};
//        String[] titles = {"Burger Ad", "Pizza Hut Ad", "Subway Ad"};
//        String[] descriptions = {
//                "A delicious burger advertisement",
//                "Pizza Hut advertisement",
//                "Subway new series menu advertisement"
//        };
//
//        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
//            for (int i = 0; i < imagePaths.length; i++) {
//                insertImage(conn, titles[i], descriptions[i], new File(imagePaths[i]));
//            }
//        } catch (SQLException | IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * Inserts an image file along with its title and description into the database.
//     *
//     * @param connection         The connection to the database
//     * @param title        The title of the image
//     * @param description  A description of the image
//     * @param file         The image file to be uploaded
//     * @throws SQLException If a database access error occurs
//     * @throws IOException  If an I/O error occurs while reading the file
//     */
//    private static void insertImage(Connection conn, String title, String description, File file) throws SQLException, IOException {
//        String sql = "INSERT INTO images (title, description, media) VALUES (?, ?, ?)";
//        try (PreparedStatement pstmt = conn.prepareStatement(sql);
//             FileInputStream fis = new FileInputStream(file)) {
//            pstmt.setString(1, title);
//            pstmt.setString(2, description);
//            pstmt.setBinaryStream(3, fis, (int) file.length());
//            pstmt.executeUpdate();
//        }
//    }
//}
