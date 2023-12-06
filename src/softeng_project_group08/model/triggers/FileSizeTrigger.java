package softeng_project_group08.model.triggers;

import java.io.File;
import softeng_project_group08.model.Trigger;

/**
 * Represents a trigger based on the maximum size a file can have.
 * @author group08
 */
public class FileSizeTrigger implements Trigger {
    
    private double sizeFile;
    private File fileName;
    
    public FileSizeTrigger(File fileName, double sizeFile){
        this.fileName=fileName;
        this.sizeFile=sizeFile;
    }
    
    
    public boolean check() {
        return fileName.exists() && (fileName.length() / 1024.0) >= sizeFile;
    }

    @Override
    public String toString() {
        return "FileSizeTrigger:\n" + "fileName=\n" + fileName + "\nsizeFile=\n" + sizeFile;
    }
}
