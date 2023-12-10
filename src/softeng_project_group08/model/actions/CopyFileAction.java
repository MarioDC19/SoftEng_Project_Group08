package softeng_project_group08.model.actions;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import softeng_project_group08.model.Action;
import softeng_project_group08.model.DialogEventManager;
import softeng_project_group08.model.DialogType;

/**
 * Java class implementing the Action interface to copy a file from a source to
 * a target directory. Notifies listeners when there's a show dialog request.
 *
 * @author group08
 */
public class CopyFileAction implements Action {

    private File sourceFile;
    private File targetDirectory;
    private DialogEventManager dialogEventManager;

    public CopyFileAction(File sourceFile, File targetDirectory) {
        this.dialogEventManager = new DialogEventManager();
        this.sourceFile = sourceFile;
        this.targetDirectory = targetDirectory;
    }

    @Override
    public void execute() {
        // If the file or folder doesn't exist, notify the user with a dialog
        if (!sourceFile.exists()) {
            dialogEventManager.requestDialog(DialogType.ERROR,
                    "CopyFileAction Error",
                    "File to copy does not exist:\n" + sourceFile.getAbsolutePath());
            return;
        }
        if (!targetDirectory.exists()) {
            dialogEventManager.requestDialog(DialogType.ERROR,
                    "CopyFileAction Error",
                    "Target directory does not exist:\n" + targetDirectory.getAbsolutePath());
            return;
        }
        // Construct the path for the destination file within the target directory
        Path targetPath = targetDirectory.toPath().resolve(sourceFile.getName());
        try {
            Files.copy(sourceFile.toPath(), targetPath, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("The file has been copied successfully.");
        } catch (IOException ex) {
            dialogEventManager.requestDialog(DialogType.ERROR,
                    "CopyFileAction Error",
                    "Generic file copy error:\n" + sourceFile.getAbsolutePath()
                    + "\n" + targetDirectory.getAbsolutePath());
        }
    }

    @Override
    public DialogEventManager getDialogEventManager() {
        return dialogEventManager;
    }

    @Override
    public String toString() {
        return "CopyFileAction:\n" + "sourceFile=\n" + sourceFile + "\ntargetDirectory=\n" + targetDirectory;
    }

}
