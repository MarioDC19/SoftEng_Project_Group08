package softeng_project_group08.model.actions;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import softeng_project_group08.model.Action;

/**
 *
 * Java class implementing the Action interface to copy a file from a source to a target directory.
 * 
 * @author group08
 */


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
    
    @Override
    public String toString() {
        return "CopyFileAction:\n" + "sourceFile=\n" + sourceFile + "\ntargetDirectory=\n" + targetDirectory;
    }

    private void showDialog(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Errore");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.show();
    }

}
