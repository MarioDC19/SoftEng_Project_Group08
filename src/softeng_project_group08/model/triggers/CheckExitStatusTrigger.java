package softeng_project_group08.model.triggers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import softeng_project_group08.model.Trigger;

/**
 * Represents a Trigger implementation that checks the exit status of an
 * external program. Compares the expected exit status with the actual exit
 * status of the executed program.
 *
 * @author group08
 */
public class CheckExitStatusTrigger implements Trigger {

    private File programPath;
    private int expectedExitStatus;
    private String parameters;
    private int actualExitStatus;

    public CheckExitStatusTrigger(File programPath, int expectedExitStatus, String parameters) {
        this.programPath = programPath;
        this.expectedExitStatus = expectedExitStatus;
        this.parameters = parameters;

    }

    @Override
    public boolean check() {
        List<String> command = new ArrayList<>();
        command.add(programPath.getAbsolutePath());

        // Controls if there's a presence of spaces inside the parameters string
        if (parameters.contains(" ")) {
            // if there are spaces it separates the parameters
            String[] paramsArray = parameters.split("\\s+");
            command.addAll(Arrays.asList(paramsArray));
        } else {
            // If there are no parameters simply add the string
            command.add(parameters);
        }

        ProcessBuilder processBuilder = new ProcessBuilder(command);
        processBuilder.redirectErrorStream(true);
        
        try {
            Process process = processBuilder.start();
            actualExitStatus= process.waitFor();
            
            return actualExitStatus == expectedExitStatus;
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public String toString() {
        return "CheckExitStatusTrigger:\n" + "programName=\n" + programPath + "\nexpected ExitStatus=\n" + expectedExitStatus + "\nPassed parameters=\n" + parameters;
    }

}
