package softeng_project_group08.model.actions;

import java.io.File;
import softeng_project_group08.model.Action;
import softeng_project_group08.model.DialogEventManager;
import softeng_project_group08.model.DialogType;

/**
 * Represents an action to delete a specified file. Manages the deletion of a
 * file, checking its existence and handling deletion errors. Notifies listeners
 * when there's a show dialog request.
 *
 * @author group08
 */
public class DeleteFileAction implements Action {

    private File file;
    private DialogEventManager dialogEventManager;

    public DeleteFileAction(File file) {
        this.dialogEventManager = new DialogEventManager();
        this.file = file;
    }

    @Override
    public void execute() {
        if (!file.exists()) {
            dialogEventManager.requestDialog(DialogType.ERROR,
                    "DeleteFileAction Error",
                    "File does not exist:\n" + file.getAbsolutePath());
            return;
        }
        boolean deleted = file.delete();
        if (deleted) {
            System.out.println("The file " + file.getAbsolutePath() + " has been deleted successfully.");
        } else {
            dialogEventManager.requestDialog(DialogType.ERROR,
                    "DeleteFileAction Error",
                    "Error while deleting the file:\n" + file.getAbsolutePath());
        }
    }

    @Override
    public DialogEventManager getDialogEventManager() {
        return dialogEventManager;
    }

    @Override
    public String toString() {
        return "DeleteFileAction:\n" + "file=\n" + file;
    }

}
