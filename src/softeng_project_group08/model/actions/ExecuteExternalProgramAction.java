package softeng_project_group08.model.actions;

import softeng_project_group08.model.Action;

/**
 * Represents an action that executes an external program with given parameters.
 * Notifies listeners when there's a show dialog request.
 *
 * @author group08
 */
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import softeng_project_group08.model.DialogEventManager;
import softeng_project_group08.model.DialogType;

public class ExecuteExternalProgramAction implements Action {

    private File programFile;
    private String parameters;
    private DialogEventManager dialogEventManager;

    public ExecuteExternalProgramAction(File programFile, String parameters) {
        this.programFile = programFile;
        this.parameters = parameters;
        this.dialogEventManager = new DialogEventManager();
    }

    @Override
    public void execute() {
        List<String> command = new ArrayList<>();
        command.add(programFile.getAbsolutePath());
        parameters = parameters.trim();
        // Tokenize the parameters if there are any
        if (!parameters.isEmpty()) {
            String[] paramsArray = parameters.split("\\s+");
            command.addAll(Arrays.asList(paramsArray));
        }
        // Create the processBuilder: a process is defined as a list of strings,
        // where the first string is the path of the executable and the rest are
        // process arguments
        ProcessBuilder processBuilder = new ProcessBuilder(command);
        processBuilder.redirectErrorStream(true); // Redirect error stream to output stream
        try {
            // Start the process
            Process process = processBuilder.start();
            // Create and start a thread for writing to StdOut the process output 
            Thread outputThread = new Thread(() -> {
                writeToStdoutProcessOutput(process);
            });
            outputThread.setDaemon(true);
            outputThread.start();
        } catch (IOException e) {
            dialogEventManager.requestDialog(DialogType.ERROR,
                    "ExecuteExternalProgramAction Error",
                    "Error while executing the program:\n" + programFile.getAbsolutePath());
        }
    }

    @Override
    public DialogEventManager getDialogEventManager() {
        return dialogEventManager;
    }

    private void writeToStdoutProcessOutput(Process process) {
        try (InputStream inputStream = process.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // write process output to StdOut
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "ExecuteExternalProgramAction: " + "\nprogramFile=" + programFile.getAbsolutePath() + "\nparameters=" + parameters;
    }

}
