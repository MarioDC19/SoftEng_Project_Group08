package softeng_project_group08.model;

import javafx.application.Platform;
import javafx.scene.control.Alert;

/**
 * Represents an action to show an information dialog.
 * @author group08
 */
public class ShowDialogAction implements Action{
    
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
