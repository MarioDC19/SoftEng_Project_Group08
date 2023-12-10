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
        // Creating a temporary test file before each test case
        try {
            testFile = File.createTempFile("testFile", ".txt");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @After
    public void tearDown() {
        // Deleting the created test file after each test case if it exists
        if (testFile != null && testFile.exists()) {
            testFile.delete();
        }
    }

    @Test
    public void testCheckFileSizeTrigger() {
        // Create a FileSizeTrigger with a maximum size of 1 KB
        FileSizeTrigger trigger = new FileSizeTrigger(testFile, 1);
        // Verify that the trigger returns false before writing to the file.
        assertFalse(trigger.check());
        // Write to the file to make it larger than 1 KB
        writeToFile(testFile, 1025);
        // Verify that the trigger returns true after writing to the file, exceeding the 1 KB limit.
        assertTrue(trigger.check());
    }

    private void writeToFile(File file, int numberOfBytes) {
        // Method to write data to the file, making it larger than the specified number of bytes.
        try {
            java.nio.file.Files.write(file.toPath(), new byte[numberOfBytes]);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
