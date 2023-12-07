package softeng_project_group08.model.triggers;

import java.io.File;
import java.io.IOException;
import softeng_project_group08.model.Trigger;

/**
 *  
 * Represents a Trigger implementation that checks the exit status of an external program.
 * Compares the expected exit status with the actual exit status of the executed program.
 * 
 * @author group08
 */
public class CheckExitStatusTrigger implements Trigger {

    private File programPath;
    private int expectedExitStatus;
    private int actualExitStatus;

    public CheckExitStatusTrigger(File programPath, int expectedExitStatus) {
        this.programPath = programPath;
        this.expectedExitStatus = expectedExitStatus;
    }

    @Override
    public boolean check() {
        try {
            Process process = Runtime.getRuntime().exec(programPath.getAbsolutePath());
            actualExitStatus = process.waitFor();

            return actualExitStatus == expectedExitStatus;
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public int getActualExitCode(){
        return actualExitStatus;
    }

    @Override
    public String toString() {
        return "CheckExitStatusTrigger:\n" + "programName=\n" + programPath + "\nexpected ExitStatus=\n" + expectedExitStatus;
    }
}
