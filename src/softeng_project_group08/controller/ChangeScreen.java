package softeng_project_group08.controller;

import java.io.IOException;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Utility class handling screen transitions and controller retrieval.
 *
 * @author group08
 */
public class ChangeScreen {

    // Method to switch screens
    public Initializable switchScreen(String path, Stage currentStage, String title) {
        try {
            // Load FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
            Parent root = loader.load();

            // Create a new stage for the loaded scene
            Stage newStage = new Stage();
            newStage.setScene(new Scene(root));

            // Close the current stage
            currentStage.close();

            // Show the new stage
            newStage.setTitle(title);
            newStage.show();

            // Return the controller for the loaded FXML
            return loader.getController();
        } catch (IOException ex) {
            // Show an error dialog if loading fails
            Platform.runLater(() -> {
                showDialog("File generic error", Alert.AlertType.ERROR, "Error");
            });
        }
        return null;
    }

    // Method to switch screens modally
    public Initializable switchScreenModal(String path, Stage currentStage, String title) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
            Parent root = loader.load();

            Stage newStage = new Stage();
            newStage.setScene(new Scene(root));
            newStage.setTitle(title);

            // Set modality to prevent interaction with other stages
            newStage.initModality(Modality.APPLICATION_MODAL);

            // Set the current stage as the owner of the new stage
            newStage.initOwner(currentStage);

            newStage.showAndWait();

            return loader.getController();
        } catch (IOException ex) {
            // Show an error dialog if loading fails
            Platform.runLater(() -> {
                showDialog("File generic error", Alert.AlertType.ERROR, "Error");
            });
        }
        return null;
    }

    // Method to display a dialog
    private void showDialog(String message, Alert.AlertType at, String title) {
        Alert alert = new Alert(at);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);

        // Show the dialog and wait for user interaction
        alert.showAndWait();
    }
}
