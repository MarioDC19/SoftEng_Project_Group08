package softeng_project_group08.model;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import javafx.application.Platform;
import javafx.scene.control.Alert;

/**
 *Represents an action to play an audio file using the default system application.
 *Implements the Action interface.
 *@author group08
 */
public class PlayAudioAction implements Action {

    private File file;

    public PlayAudioAction(File file) {
        this.file = file;
    }

    @Override
    public void execute() {
        try {
            // Check if Desktop is supported
            if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.OPEN)) {
                Desktop desktop = Desktop.getDesktop();
                // Check if audio file exists
                if (file.exists() && file.isFile()) {
                    desktop.open(file);
                } else {
                    Platform.runLater(()->{
                    showDialog("Audio file does not exist.");
                    });
                    
                }

            } else {
                Platform.runLater(()->{
                    showDialog("Desktop is not supported.");
                    });
            }
        } catch (IOException e) {
            showDialog("Audio file generic error.");
        }
    }
    
    @Override
    public String toString() {
        return "PlayAudioAction:\n" + "file=\n" + file;
    }
    
    private void showDialog(String message){
        Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Errore");
                    alert.setHeaderText(null);
                    alert.setContentText(message);
                    alert.show();
    }
}
