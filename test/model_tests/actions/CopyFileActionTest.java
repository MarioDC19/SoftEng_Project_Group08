package model_tests.actions;

import java.io.File;
import java.io.IOException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import softeng_project_group08.model.actions.CopyFileAction;

/**
 *
 * @author group08
 */
public class CopyFileActionTest {

    private static final String SOURCE_FILE_PATH = "testSource.txt";
    private static final String TARGET_DIRECTORY_PATH = "testDestination";
    private File sourceFile;
    private File targetDirectory;
    private File targetFile;

    @Before
    public void setUp() throws IOException {
        // Setting up initial conditions for testing
        // Create a source file and a target directory
        sourceFile = new File(SOURCE_FILE_PATH);
        sourceFile.createNewFile();
        targetDirectory = new File(TARGET_DIRECTORY_PATH);
        targetDirectory.mkdir();
        // Define the target file within the target directory
        targetFile = new File(targetDirectory, sourceFile.getName());
    }

    @After
    public void tearDown() throws IOException {
        // Clean up after each test

        // Delete the source file, target file, and target directory
        sourceFile.delete();
        targetFile.delete();
        targetDirectory.delete();
    }

    @Test
    public void testCopyFileSuccess() {
        // Test case to check successful file copying

        // Creating a CopyFileAction instance with the source file and target directory
        CopyFileAction copyAction = new CopyFileAction(sourceFile, targetDirectory);
        copyAction.execute();

        // Check if the file has been copied successfully by verifying its existence in the target directory
        assertTrue(targetFile.exists());
    }

}
