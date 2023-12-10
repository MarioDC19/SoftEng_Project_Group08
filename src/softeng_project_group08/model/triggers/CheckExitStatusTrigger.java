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

    public CheckExitStatusTrigger(File programPath, int expectedExitStatus, String parameters) {
        this.programPath = programPath;
        this.expectedExitStatus = expectedExitStatus;
        this.parameters = parameters;
    }

    @Override
    public boolean check() {
        List<String> command = new ArrayList<>();
        command.add(programPath.getAbsolutePath());
        parameters = parameters.trim();
        // Tokenize the parameters if there are any
        if (!parameters.isEmpty()) {
            String[] paramsArray = parameters.split("\\s+");
            command.addAll(Arrays.asList(paramsArray));
        }

        ProcessBuilder processBuilder = new ProcessBuilder(command);
        processBuilder.redirectErrorStream(true); // redirect error stream to output stream

        try {
            Process process = processBuilder.start();
            int actualExitStatus = process.waitFor();

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
