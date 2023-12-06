package softeng_project_group08.model.triggers;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import softeng_project_group08.model.Trigger;

/**
 * Represents a trigger based on the existence of a file in a directory.
 * @author group08
 */
public class FileExistenceTrigger implements Trigger{
    
    private String fileName;
    private File targetDirectory;
    
    public FileExistenceTrigger(String fileName, File targetDirectory){
        this.fileName=fileName;
        this.targetDirectory=targetDirectory;
    }

    @Override
    public boolean check() {
        // Combine the target directory and file name to create the full path
        Path filePath = Paths.get(targetDirectory.getAbsolutePath(), fileName);

        // Check if the file exists
        return Files.exists(filePath);
    }

    @Override
    public String toString() {
        return "FileExistenceTrigger:\n" + "fileName=\n" + fileName + "\ntargetDirectory=\n" + targetDirectory;
    }
    
}
