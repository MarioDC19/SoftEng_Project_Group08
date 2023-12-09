package model_tests.triggers;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.io.File;
import static org.junit.Assert.*;
import softeng_project_group08.model.triggers.FileSizeTrigger;

/**
 *
 * @author group08
 */
public class FileSizeTriggerTest {

    private File testFile;

    @Before
    public void setUp() {
        try {
            testFile = File.createTempFile("testFile", ".txt");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @After
    public void tearDown() {
        if (testFile != null && testFile.exists()) {
            testFile.delete();
        }
    }

    @Test
    public void testCheckFileSizeTrigger() {
        // Create a FileSizeTrigger with max size 1 KB
        FileSizeTrigger trigger = new FileSizeTrigger(testFile, 1);

        //Verify that the trigger returns false before writing to the file.
        assertFalse(trigger.check());

        writeToFile(testFile, 1025);

        assertTrue(trigger.check());
    }

    private void writeToFile(File file, int numberOfBytes) {
        //Write to the file to make it larger than numberOfBytes.
        try {
            java.nio.file.Files.write(file.toPath(), new byte[numberOfBytes]);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
