package softeng_project_group08.model.actions;

import java.io.File;
import softeng_project_group08.model.Action;
import softeng_project_group08.model.DialogEventManager;
import softeng_project_group08.model.DialogType;

/**
 * Abstract class that represents all action classes that do an action with a
 * source file and a target directory. Uses template method pattern to delegate
 * to subclasses the implementation of the specific action.
 * Notifies listeners when there's a show dialog request.
 *
 * @author group08
 */
public abstract class FileDirectoryAction implements Action {
    
    // protected because these attributes can be used by subclasses
    protected File sourceFile;
    protected File targetDirectory;
    protected DialogEventManager dialogEventManager;

    public FileDirectoryAction(File sourceFile, File targetDirectory) {
        this.dialogEventManager = new DialogEventManager();
        this.sourceFile = sourceFile;
        this.targetDirectory = targetDirectory;
    }

    @Override
    public void execute() {
        // If the file or folder doesn't exist, notify the user with a dialog
        if (!sourceFile.exists()) {
            dialogEventManager.requestDialog(DialogType.ERROR,
                    this.getClass().getName() + " Error",
                    "File does not exist:\n" + sourceFile.getAbsolutePath());
            return;
        }
        if (!targetDirectory.exists()) {
            dialogEventManager.requestDialog(DialogType.ERROR,
                    this.getClass().getName() + " Error",
                    "Target directory does not exist:\n" + targetDirectory.getAbsolutePath());
            return;
        }
        handleFileDirectory();
    }
    
    // template method
    protected abstract void handleFileDirectory();

    @Override
    public DialogEventManager getDialogEventManager() {
        return dialogEventManager;
    }

    @Override
    public String toString() {
        return this.getClass().getName() + ":\n" + "sourceFile=\n" + sourceFile + "\ntargetDirectory=\n" + targetDirectory;
    }

}
