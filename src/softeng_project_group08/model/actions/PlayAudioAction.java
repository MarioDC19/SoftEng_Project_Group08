package softeng_project_group08.model.actions;

import softeng_project_group08.model.Action;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import softeng_project_group08.model.DialogEventManager;
import softeng_project_group08.model.DialogType;

/**
 * Represents an action to play an audio file using the default system
 * application. Implements the Action interface. Notifies listeners when there's
 * a show dialog request.
 *
 * @author group08
 */
public class PlayAudioAction implements Action {

    private File file;
    private DialogEventManager dialogEventManager;

    public PlayAudioAction(File file) {
        this.dialogEventManager = new DialogEventManager();
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
                    dialogEventManager.requestDialog(DialogType.ERROR,
                            "PlayAudioAction Error",
                            "Audio file does not exist:\n" + file.getAbsolutePath());
                }
            } else {
                dialogEventManager.requestDialog(DialogType.ERROR,
                        "PlayAudioAction Error",
                        "Operating System is not supported");
            }
        } catch (IOException e) {
            dialogEventManager.requestDialog(DialogType.ERROR,
                    "PlayAudioAction Error",
                    "Audio file generic error:\n" + file.getAbsolutePath());
        }
    }

    @Override
    public DialogEventManager getDialogEventManager() {
        return dialogEventManager;
    }

    @Override
    public String toString() {
        return "PlayAudioAction:\n" + "file=\n" + file;
    }

}
