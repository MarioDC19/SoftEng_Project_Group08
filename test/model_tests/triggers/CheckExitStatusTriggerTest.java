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
        if (os.contains("win")) {
            createTestFile("testExit.bat", "@echo off\n"
                    + "IF \"%1\"==\"success\" (\n"
                    + "    exit /b 37\n"
                    + ") ELSE (\n"
                    + "    exit /b 0\n"
                    + ")");
            createTestFile("testExit2.bat", "@@echo off\n"
                    + "IF \"%1\"==\"success\" (\n"
                    + "    exit /b 0\n"
                    + ") ELSE (\n"
                    + "    exit /b 37\n"
                    + ")");
        } else {
            createTestFile("testExit.sh", "#!/bin/bash\n"
                    + "\n"
                    + "if [ \"$1\" = \"success\" ]; then\n"
                    + "    exit 37\n"
                    + "else\n"
                    + "    exit 0\n"
                    + "fi");
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
            File file = new File(fileName);
            FileWriter writer = new FileWriter(file);
            writer.write(content.trim());
            writer.close();
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
            new File("testExit.bat").delete();
            new File("testExit2.bat").delete();
        } else {
            new File("testExit.sh").delete();
            new File("testExit2.sh").delete();
        }
    }

    @Test
    public void testCheckExitStatus() {
        File programFile;
        if (os.contains("win")) {
            programFile = new File("testExit.bat");
        } else {
            programFile = new File("testExit.sh");
        }
        // Test case 1: trigger checks for exit status 37, that is returned by the program
        // so the check should return true
        CheckExitStatusTrigger checkExitStatus = new CheckExitStatusTrigger(programFile, 37, "success");
        assertTrue(checkExitStatus.check());
        // Test case 2: trigger checks for exit status 36,
        // so the check should return false
        checkExitStatus = new CheckExitStatusTrigger(programFile, 36, "fail");
        assertFalse(checkExitStatus.check());
    }

    @Test
    public void testCheckExitStatus2() {
        File programFile;
        if (os.contains("win")) {
            programFile = new File("testExit2.bat");
        } else {
            programFile = new File("testExit2.sh");
        }
        // Test case 1: trigger checks for exit status 0, that is returned by the program
        // so the check should return true
        CheckExitStatusTrigger checkExitStatus = new CheckExitStatusTrigger(programFile, 0, "success");
        assertTrue(checkExitStatus.check());
        // Test case 2: trigger checks for exit status 1,s
        // so the check should return false
        checkExitStatus = new CheckExitStatusTrigger(programFile, 1, "fail");
        assertFalse(checkExitStatus.check());
    }
}
