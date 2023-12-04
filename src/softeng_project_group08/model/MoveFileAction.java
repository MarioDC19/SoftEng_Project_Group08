package softeng_project_group08.model;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

/**
 * Class that represents an action to move a file to a specified directory.
 *
 * @author group08
 */
public class MoveFileAction implements Action {

    private File sourceFile;
    private File targetDirectory;

    // Constructor to initialize the source file and target directory
    public MoveFileAction(File sourceFile, File targetDirectory) {
        this.sourceFile = sourceFile;
        this.targetDirectory = targetDirectory;
    }

    // Method to execute the file move action
    public void execute() {
        // Construct the path for the destination file within the target directory
        Path targetPath = targetDirectory.toPath().resolve(sourceFile.getName());

        try {
            // Attempt to move the source file to the target path
            Files.move(sourceFile.toPath(), targetPath, StandardCopyOption.REPLACE_EXISTING);
            // Print success message if the file was moved successfully
            System.out.println("File moved successfully from " + sourceFile.getAbsolutePath()
                    + " to " + targetPath);
        } catch (IOException ex) {
            // If an IOException occurs during the move operation, print an error message and the stack trace
            System.err.println("Error while moving the file: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
    
    @Override
    public String toString() {
        return "MoveFileAction:\n" + "sourceFile=\n" + sourceFile + "\ntargetDirectory=\n" + targetDirectory ;
    }
}
