package softeng_project_group08.model.actions;

import softeng_project_group08.model.Action;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import softeng_project_group08.model.DialogEventManager;
import softeng_project_group08.model.DialogType;

/**
 * Class that represents an action to move a file to a specified directory.
 * Notifies listeners when there's a show dialog request.
 *
 * @author group08
 */
public class MoveFileAction implements Action {

    private File sourceFile;
    private File targetDirectory;
    private DialogEventManager dialogEventManager;

    // Constructor to initialize the source file and target directory
    public MoveFileAction(File sourceFile, File targetDirectory) {
        this.dialogEventManager = new DialogEventManager();
        this.sourceFile = sourceFile;
        this.targetDirectory = targetDirectory;
    }

    // Method to execute the file move action
    public void execute() {
        // If the file or folder doesn't exist, notify the user with a dialog
        if (!sourceFile.exists()) {
            dialogEventManager.requestDialog(DialogType.ERROR,
                    "MoveFileAction Error",
                    "File to copy does not exist:\n" + sourceFile.getAbsolutePath());
            return;
        }
        if (!targetDirectory.exists()) {
            dialogEventManager.requestDialog(DialogType.ERROR,
                    "MoveFileAction Error",
                    "Target directory does not exist:\n" + targetDirectory.getAbsolutePath());
            return;
        }
        // Construct the path for the destination file within the target directory
        Path targetPath = targetDirectory.toPath().resolve(sourceFile.getName());
        try {
            Files.move(sourceFile.toPath(), targetPath, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("The file has been copied successfully.");
        } catch (IOException ex) {
            dialogEventManager.requestDialog(DialogType.ERROR,
                    "MoveFileAction Error",
                    "Generic file move error:\n" + sourceFile.getAbsolutePath()
                    + "\n" + targetDirectory.getAbsolutePath());
        }
    }

    @Override
    public DialogEventManager getDialogEventManager() {
        return dialogEventManager;
    }

    @Override
    public String toString() {
        return "MoveFileAction:\n" + "sourceFile=\n" + sourceFile + "\ntargetDirectory=\n" + targetDirectory;
    }

}
