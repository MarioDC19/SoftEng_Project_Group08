package softeng_project_group08.model.actions;

import softeng_project_group08.model.Action;

/**
 * Represents an action that executes an external program with given parameters.
 * @author group08
 *
 */
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class ExecuteExternalProgramAction implements Action {

    private File programFile;
    private String parameters;
    private int exitCode;

    public ExecuteExternalProgramAction(File programFile, String parameters) {
        this.programFile = programFile;
        this.parameters = parameters;
    }

    @Override
    public void execute() {
        List<String> command = new ArrayList<>();
        command.add(programFile.getAbsolutePath());

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
        processBuilder.redirectErrorStream(true); // Redirecting error stream to input stream

        try {
            Process process = processBuilder.start();

            // Reads the output of the program
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            System.out.println("Output del programma esterno:");
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            exitCode = process.waitFor();
            if (exitCode == 0) {
                System.out.println("Programma eseguito con successo");
            } else {
                System.out.println("L'esecuzione del programma è fallita con codice: " + exitCode);
            }
        } catch (IOException | InterruptedException e) {
            System.err.println("Errore durante l'esecuzione del programma: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public int getExitCode() {
        return exitCode;
    }

    @Override
    public String toString() {
        return "ExecuteExternalProgramAction: " + "\nprogramFile=" + programFile.getAbsolutePath() + "\nparameters=" + parameters;
    }
}
