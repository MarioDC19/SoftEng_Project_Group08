package softeng_project_group08.controller;

import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

/**
 *
 * @author group08
 */
public class DialogUtil {
    
    public static boolean showDialog(String message, Alert.AlertType at, String title) {
        Alert alert = new Alert(at);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);

        if (at == Alert.AlertType.CONFIRMATION) {
            return showConfirmationDialog(alert);
        } else {
            // Show the dialog for other types
            alert.show();
            return true;
        }
    }

    // in case of a confirmation dialog, wait for user input and return true
    // if "OK" button is pressed, return false otherwise
    private static boolean showConfirmationDialog(Alert alert) {
        Optional<ButtonType> result = alert.showAndWait();
        return result.map(buttonType -> buttonType == ButtonType.OK).orElse(false);
    }
    
}
