package softeng_project_group08.model.actions;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import softeng_project_group08.model.DialogType;

/**
 * Java class implementing the Action interface to copy a file from a source to
 * a target directory. Implements template method.
 *
 * @author group08
 */
public class CopyFileAction extends FileDirectoryAction {

    // Constructor to initialize the source file and target directory
    public CopyFileAction(File sourceFile, File targetDirectory) {
        super(sourceFile, targetDirectory);
    }

    @Override
    protected void handleFileDirectory() {
        // Construct the path for the destination file within the target directory
        Path targetPath = targetDirectory.toPath().resolve(sourceFile.getName());
        try {
            Files.copy(sourceFile.toPath(), targetPath, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("The file has been copied successfully:\n" + sourceFile.getAbsolutePath());
        } catch (IOException ex) {
            dialogEventManager.requestDialog(DialogType.ERROR,
                    this.getClass().getName() + " Error",
                    "Generic file copy error:\n" + sourceFile.getAbsolutePath()
                    + "\n" + targetDirectory.getAbsolutePath());
        }
    }

}
