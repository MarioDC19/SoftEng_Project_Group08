package model_tests.triggers;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import softeng_project_group08.model.triggers.FileExistenceTrigger;

/**
 *
 * @author group08
 */
public class FileExistenceTriggerTest {

    private File tempDir;
    private File tempFile;

    @Before
    public void setUp() {
        // Create a temporary directory for testing
        tempDir = createTempDir();
        // Create a temporary file within the directory
        String tempFileName = "testFile.txt";
        tempFile = new File(tempDir, tempFileName);
        // Ensure the file exists
        try {
            tempFile.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException("Error creating temporary file", e);
        }
    }

    @Test
    public void testCheckFileExists() {
        // Create an instance of FileExistenceTrigger
        FileExistenceTrigger fileExistenceTrigger = new FileExistenceTrigger("testFile.txt", tempDir);
        // Ensure the file exists
        assertTrue("File should exist", fileExistenceTrigger.check());
    }

    @Test
    public void testCheckFileDoesNotExist() {
        // Create an instance of FileExistenceTrigger with a non-existing file name
        FileExistenceTrigger fileExistenceTrigger = new FileExistenceTrigger("nonExistentFile.txt", tempDir);
        // Ensure the file does not exist
        assertFalse("File should not exist", fileExistenceTrigger.check());
    }

    @After
    public void tearDown() {
        // Clean up: delete the temporary file and directory
        deleteTempFile();
        deleteTempDir();
    }

    // Helper method to create a temporary directory
    private File createTempDir() {
        try {
            return Files.createTempDirectory("tempDir").toFile();
        } catch (IOException e) {
            throw new RuntimeException("Error creating temporary directory", e);
        }
    }

    // Helper method to delete the temporary directory
    private void deleteTempDir() {
        try {
            Files.deleteIfExists(tempDir.toPath());
        } catch (IOException e) {
            throw new RuntimeException("Error deleting temporary directory", e);
        }
    }

    // Helper method to delete the temporary file
    private void deleteTempFile() {
        try {
            Files.deleteIfExists(tempFile.toPath());
        } catch (IOException e) {
            throw new RuntimeException("Error deleting temporary file", e);
        }
    }

}
