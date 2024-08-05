package ca.ucalgary.edu.ensf380;

import javax.swing.*;
import java.awt.*;

public class TrainSelectionDialog {

    public static Integer selectTrain(JFrame parentFrame) {
        // List of train numbers (1 to 12 for example)
        String[] trainOptions = new String[12];
        for (int i = 1; i <= 12; i++) {
            trainOptions[i - 1] = "Train " + i;
        }

        // Create a dialog to select a train
        String selectedTrain = (String) JOptionPane.showInputDialog(
            parentFrame,
            "Select a Train:",
            "Train Selection",
            JOptionPane.QUESTION_MESSAGE,
            null,
            trainOptions,
            trainOptions[0]
        );

        if (selectedTrain != null) {
            // Extract train number from the selected option
            return Integer.parseInt(selectedTrain.replace("Train ", ""));
        }
        return null; // No train selected
    }
}
