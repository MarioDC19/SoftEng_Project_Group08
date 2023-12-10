package model_tests.triggers;

import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import softeng_project_group08.model.triggers.CheckExitStatusTrigger;

/**
 *
 * @author group08
 */
public class CheckExitStatusTriggerTest {

    private String os = System.getProperty("os.name").toLowerCase();

    @Before
    public void setUp() {
        // Creating test files based on the operating system
        // For Windows OS
        if (os.contains("win")) {
            // Creating testExit.bat and testExit2.bat

            // testExit.bat script:
            // If argument passed is "success", exits with status code 37; otherwise, exits with status code 0.
            createTestFile("testExit.bat", "@echo off\n"
                    + "IF \"%1\"==\"success\" (\n"
                    + "    exit /b 37\n"
                    + ") ELSE (\n"
                    + "    exit /b 0\n"
                    + ")");

            // testExit2.bat script:
            // If argument passed is "success", exits with status code 0; otherwise, exits with status code 37.
            createTestFile("testExit2.bat", "@@echo off\n"
                    + "IF \"%1\"==\"success\" (\n"
                    + "    exit /b 0\n"
                    + ") ELSE (\n"
                    + "    exit /b 37\n"
                    + ")");
        } else { // For non-Windows OS (Linux/Mac)
            // Creating testExit.sh and testExit2.sh

            // testExit.sh script:
            // If argument passed is "success", exits with status code 37; otherwise, exits with status code 0.
            createTestFile("testExit.sh", "#!/bin/bash\n"
                    + "\n"
                    + "if [ \"$1\" = \"success\" ]; then\n"
                    + "    exit 37\n"
                    + "else\n"
                    + "    exit 0\n"
                    + "fi");

            // testExit2.sh script:
            // If argument passed is "success", exits with status code 0; otherwise, exits with status code 37.
            createTestFile("testExit2.sh", "#!/bin/bash\n"
                    + "\n"
                    + "if [ \"$1\" = \"success\" ]; then\n"
                    + "    exit 0\n"
                    + "else\n"
                    + "    exit 37\n"
                    + "fi");
        }
    }

    private static void createTestFile(String fileName, String content) {
        try {
            // Creating test files with specified content
            File file = new File(fileName);
            FileWriter writer = new FileWriter(file);
            writer.write(content.trim());
            writer.close();
            // Setting executable permissions for non-Windows OS
            if (!System.getProperty("os.name").toLowerCase().contains("win")) {
                file.setExecutable(true);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @After
    public void tearDown() {
        // Deleting the created test files after the tests are executed
        // For Windows OS
        if (os.contains("win")) {
            new File("testExit.bat").delete();
            new File("testExit2.bat").delete();
        } else { // For non-Windows OS (Linux/Mac)
            new File("testExit.sh").delete();
            new File("testExit2.sh").delete();
        }
    }

    @Test
    public void testCheckExitStatus() {
        // Test cases for checking exit statuses of programs

        File programFile;
        if (os.contains("win")) {
            programFile = new File("testExit.bat");
        } else {
            programFile = new File("testExit.sh");
        }

        // Test case 1: Checks for exit status 37, which should return true
        CheckExitStatusTrigger checkExitStatus = new CheckExitStatusTrigger(programFile, 37, "success");
        assertTrue(checkExitStatus.check());
        // Test case 2: Checks for exit status 36, which should return false
        checkExitStatus = new CheckExitStatusTrigger(programFile, 36, "fail");
        assertFalse(checkExitStatus.check());
    }

    @Test
    public void testCheckExitStatus2() {
        // Additional test cases for checking exit statuses of programs
        File programFile;
        if (os.contains("win")) {
            programFile = new File("testExit2.bat");
        } else {
            programFile = new File("testExit2.sh");
        }
        // Test case 1: Checks for exit status 0, which should return true
        CheckExitStatusTrigger checkExitStatus = new CheckExitStatusTrigger(programFile, 0, "success");
        assertTrue(checkExitStatus.check());
        // Test case 2: Checks for exit status 1, which should return false
        checkExitStatus = new CheckExitStatusTrigger(programFile, 1, "fail");
        assertFalse(checkExitStatus.check());
    }
}
