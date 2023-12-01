/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package softeng_project_group08.model;

import java.io.File;
import javafx.scene.control.Alert;

/**
 *
 * @author mauri
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
    
        private void showDialog(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Errore");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.show();
    }
}
