package softeng_project_group08.model;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import javafx.application.Platform;
import javafx.scene.control.Alert;

/**
 *
 * @author group08
 */

/*CopyFileAction is a Java class implementing the Action interface to copy a file 
from a source to a target directory. It includes checks for file existence, 
target validity, and displays error messages using JavaFX alerts.*/

public class CopyFileAction implements Action {

    private File sourceFile;
    private File targetDirectory;

    public CopyFileAction(File sourceFile, File targetDirectory) {
        this.sourceFile = sourceFile;
        this.targetDirectory = targetDirectory;
    }

    @Override
    public void execute() {

        if (!sourceFile.exists()) {
            Platform.runLater(() -> {
                showDialog("The source file does not exist.");
            });
        } else if (!targetDirectory.isDirectory()) {
            Platform.runLater(() -> {
                showDialog("The destination must be a directory.");
            });
        } else {

            Path targetPath = targetDirectory.toPath().resolve(sourceFile.getName());
            try {
                Files.copy(sourceFile.toPath(), targetPath, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException ex) {
                Platform.runLater(() -> {
                    showDialog("Copy File generic error.");
                });
            }
            System.out.println("The file has been copied successfully.");
        }
    }

    private void showDialog(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Errore");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.show();
    }

}
