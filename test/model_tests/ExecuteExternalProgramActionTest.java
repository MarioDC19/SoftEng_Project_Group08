package model_tests;

import static org.junit.Assert.assertEquals;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import org.junit.After;
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
            createTestFile("test.bat", "@echo off\n"
                    + "\n"
                    + "echo This is a test batch script.\n"
                    + "echo Parameters received:\n"
                    + "echo %1\n"
                    + "echo %2\n");
            createTestFile("testSum.bat", "@echo off\n"
                    + "\n"
                    + "rem Controlla se ci sono meno di due parametri o se non sono interi\n"
                    + "if \"%~1\"==\"\" (\n"
                    + "    echo Usage: %0 <number1> <number2>\n"
                    + "    exit /b 1\n"
                    + ")\n"
                    + "set \"num1=%~1\"\n"
                    + "if not \"%num1%\"==\"%num1: =%\" (\n"
                    + "    echo First parameter is not an integer\n"
                    + "    exit /b 2\n"
                    + ")\n"
                    + "\n"
                    + "if \"%~2\"==\"\" (\n"
                    + "    echo Usage: %0 <number1> <number2>\n"
                    + "    exit /b 1\n"
                    + ")\n"
                    + "set \"num2=%~2\"\n"
                    + "if not \"%num2%\"==\"%num2: =%\" (\n"
                    + "    echo Second parameter is not an integer\n"
                    + "    exit /b 2\n"
                    + ")\n"
                    + "\n"
                    + "rem Effettua la somma se entrambi i parametri sono interi\n"
                    + "set /a sum=%1+%2\n"
                    + "echo Sum of %1 and %2 is: %sum%\n"
                    + "exit /b 0");
        } else {
            createTestFile("test.sh", "#!/bin/bash\n"
                    + "\n"
                    + "echo \"This is a test shell script.\"\n"
                    + "echo \"Parameters received:\"\n"
                    + "echo $1\n"
                    + "echo $2\n"
                    + "");
            createTestFile("testSum.sh", "#!/bin/bash\n"
                    + "\n"
                    + "if [ \"$#\" -ne 2 ]; then\n"
                    + "    echo \"Usage: $0 <number1> <number2>\"\n"
                    + "    exit 1\n"
                    + "fi\n"
                    + "\n"
                    + "if ! [[ $1 =~ ^[0-9]+$ ]]; then\n"
                    + "    echo \"First parameter is not an integer\"\n"
                    + "    exit 2\n"
                    + "fi\n"
                    + "\n"
                    + "if ! [[ $2 =~ ^[0-9]+$ ]]; then\n"
                    + "    echo \"Second parameter is not an integer\"\n"
                    + "    exit 2\n"
                    + "fi\n"
                    + "\n"
                    + "sum=$(expr $1 + $2)\n"
                    + "echo \"Sum of $1 and $2 is: $sum\"\n"
                    + "exit 0"
                    + "");
        }
    }

    private static void createTestFile(String fileName, String content) {
        try {
            File file = new File(fileName);
            FileWriter writer = new FileWriter(file);
            writer.write(content);
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
            new File("test.bat").delete();
            new File("testSum.bat").delete();
        } else {
            new File("test.sh").delete();
            new File("testSum.sh").delete();
        }
    }
    
    //test the correct execution
    @Test
    public void testExecuteExternalProgramAction() {
        // path of testing program
        File programFile;
        if (os.contains("win")) {
            programFile = new File("test.bat");
        } else {
            programFile = new File("test.sh");
        }

        // Parameters passed
        String parameters = "Ciao Ciao";

        ExecuteExternalProgramAction eepm = new ExecuteExternalProgramAction(programFile, parameters);

        eepm.execute();

        assertEquals(0, eepm.getExitCode());

    }

    //test the failure
    @Test
    public void testExecuteExternalProgramAction2() {
        // path of testing program
        File programFile;
        if (os.contains("win")) {
            programFile = new File("testSum.bat");
        } else {
            programFile = new File("testSum.sh");
        }
        // Parameters passed
        String parameters = "1 e";

        ExecuteExternalProgramAction eepm = new ExecuteExternalProgramAction(programFile, parameters);

        eepm.execute();

        assertNotEquals(0, eepm.getExitCode());

    }
}
