package model_tests.actions;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import softeng_project_group08.model.actions.ExecuteExternalProgramAction;

/**
 *
 * @author group08
 */
public class ExecuteExternalProgramActionTest {

    String os = System.getProperty("os.name").toLowerCase();
    
    @Before
    public void setUp() {
        // Initial setup for test cases
        if (os.contains("win")) {
            /* Creating a batch script for Windows
         The script checks for content presence, displaying usage instructions if missing,
         then writes the provided content to the 'testWrite.txt' file*/
            createTestFile("testString.bat", "@echo off\n"
                    + "\n"
                    + "if \"%~1\"==\"\" (\n"
                    + "    echo Usage: %0 ^<content^>\n" // Displays usage instructions if no content is provided
                    + "    exit /b 1\n" // Exits with an error code 1
                    + ")\n"
                    + "\n"
                    + "echo %~1 > testWrite.txt" // Writes the provided content to 'testWrite.txt'
                    + "");
        } else {
            /*Creating a shell script for non-Windows systems (Unix-like)
        The script checks for a single argument, displaying usage instructions otherwise,
        then writes the provided content to the 'testWrite.txt' file*/
            createTestFile("testString.sh", "#!/bin/bash\n"
                    + "\n"
                    + "if [ $# -ne 1 ]; then\n" // Checks if a single argument is provided
                    + "    echo \"Usage: $0 <content>\"\n" // Displays usage instructions if argument count is incorrect
                    + "    exit 1\n" // Exits with an error code 1
                    + "fi\n"
                    + "\n"
                    + "content=$1\n"
                    + "echo $content > testWrite.txt" // Writes the provided content to 'testWrite.txt'
                    + "");
        }
    }

    private static void createTestFile(String fileName, String content) {
        try {
            // Creating a test file with given content
            // Creating a new file with the provided file name
            File file = new File(fileName);
            // Creating a FileWriter to write content to the file
            FileWriter writer = new FileWriter(file);
            // Writing the trimmed content to the file
            writer.write(content.trim());
            // Closing the writer
            writer.close();
            // Setting file permissions for Unix-like systems to make it executable
            if (!System.getProperty("os.name").toLowerCase().contains("win")) {
                file.setExecutable(true);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @After
    public void tearDown() {
        if (os.contains("win")) {
            // Deleting test files for Windows
            new File("testString.bat").delete();
            new File("testWrite.txt").delete();
        } else {
            // Deleting test files for non-Windows systems (Unix-like)
            new File("testString.sh").delete();
            new File("testWrite.txt").delete();
        }
    }

    @Test
    public void testExecuteExternalProgramAction() {
        // Test method to execute an external program with provided parameters
        // Path of the testing program based on OS
        File programFile;
        if (os.contains("win")) {
            programFile = new File("testString.bat");
        } else {
            programFile = new File("testString.sh");
        }
        // Parameters passed to the program
        String parameters = "CiaoCiao";

        ExecuteExternalProgramAction eepm = new ExecuteExternalProgramAction(programFile, parameters);

        eepm.execute();
        // Adding a 500ms pause to allow the external program to complete its execution
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try (BufferedReader reader = new BufferedReader(new FileReader("testWrite.txt"))) {
            String line;
            String lastLine = "";

            while ((line = reader.readLine()) != null) {
                lastLine = line; // Update lastLine with each line read
            }
            // Assertion to check if the provided parameter is in the last line of the file
            assertEquals("String not found in the last line of the file", "CiaoCiao", lastLine.trim());

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
