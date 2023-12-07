package model_tests.actions;

import java.io.BufferedReader;
import static org.junit.Assert.assertEquals;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import static junit.framework.Assert.assertTrue;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
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
        if (os.contains("win")) {
            createTestFile("testString.bat", "@echo off\n"
                    + "\n"
                    + "if \"%~1\"==\"\" (\n"
                    + "    echo Usage: %0 ^<content^>\n"
                    + "    exit /b 1\n"
                    + ")\n"
                    + "\n"
                    + "echo %~1 > testWrite.txt"
                    + "");

        } else {
            createTestFile("testString.sh", "#!/bin/bash\n"
                    + "\n"
                    + "if [ $# -ne 1 ]; then\n"
                    + "    echo \"Usage: $0 <content>\"\n"
                    + "    exit 1\n"
                    + "fi\n"
                    + "\n"
                    + "content=$1\n"
                    + "echo $content > testWrite.txt"
                    + "");

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
            new File("testString.bat").delete();
            new File("testWrite.txt").delete();
        } else {
            new File("testString.sh").delete();   
            new File("testWrite.txt").delete();
        }
    }
    //test the correct execution
    @Test
    public void testExecuteExternalProgramAction() {
        // path of testing program
        File programFile;
        if (os.contains("win")) {
            programFile = new File("testString.bat");
        } else {
            programFile = new File("testString.sh");
        }

        // Parameters passed
        String parameters = "CiaoCiao";

        ExecuteExternalProgramAction eepm = new ExecuteExternalProgramAction(programFile, parameters);

        eepm.execute();

        try (BufferedReader reader = new BufferedReader(new FileReader("testWrite.txt"))) {
            String line;
            String lastLine = "";

            while ((line = reader.readLine()) != null) {
                lastLine = line; // Update lastLine with each line read
            }

            assertEquals("String not found in the last line of the file", "CiaoCiao", lastLine.trim());

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
