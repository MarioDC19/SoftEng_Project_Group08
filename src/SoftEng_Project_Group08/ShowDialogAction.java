package SoftEng_Project_Group08;

import javafx.application.Platform;
import javafx.scene.control.Alert;

/**
 *
 * @author Master
 */
public class ShowDialogAction implements Action {

    private String message;

    public ShowDialogAction(String message) {
        this.message = message;
    }

    @Override
    public void execute() {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Informazione");
            alert.setHeaderText(null);
            alert.setContentText(message);

            alert.showAndWait();
        });
    }
}
