package softeng_project_group08.model.actions;

import java.io.File;
import javafx.scene.control.Alert;
import softeng_project_group08.model.Action;

/**
 *
 * Represents an action to delete a specified file.
 * Manages the deletion of a file, checking its existence and handling deletion errors
 * 
 * @author group08
 */
public class DeleteFileAction implements Action {
    private File file;
    
    public DeleteFileAction(File file){
        this.file=file;
    }

    @Override
    public void execute() {
        if (file.exists()) {
            boolean deleted = file.delete();

            if (deleted) {
                System.out.println("The file has been deleted.");
            } else {
               showDialog("Error deleting the file.");
            }
        } else {
           showDialog("The file does not exist.");
        }
    }
    
    @Override
    public String toString() {
        return "DeleteFileAction:\n" + "file=\n" + file ;
    }
    
        private void showDialog(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Errore");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.show();
    }
}
