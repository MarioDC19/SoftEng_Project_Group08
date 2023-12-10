package softeng_project_group08.model.actions;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import softeng_project_group08.model.DialogType;

/**
 * Class that represents an action to move a file to a specified directory.
 * Implements template method.
 *
 * @author group08
 */
public class MoveFileAction extends FileDirectoryAction {

    // Constructor to initialize the source file and target directory
    public MoveFileAction(File sourceFile, File targetDirectory) {
        super(sourceFile, targetDirectory);
    }

    @Override
    protected void handleFileDirectory() {
        // Construct the path for the destination file within the target directory
        Path targetPath = targetDirectory.toPath().resolve(sourceFile.getName());
        try {
            Files.move(sourceFile.toPath(), targetPath, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("The file has been moved successfully:\n" + sourceFile.getAbsolutePath());
        } catch (IOException ex) {
            dialogEventManager.requestDialog(DialogType.ERROR,
                    this.getClass().getName() + " Error",
                    "Generic file move error:\n" + sourceFile.getAbsolutePath()
                    + "\n" + targetDirectory.getAbsolutePath());
        }
    }

}
