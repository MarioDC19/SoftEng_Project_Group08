package model_tests.actions;

import java.io.File;
import org.junit.Before;
import org.junit.Test;
import softeng_project_group08.model.actions.MoveFileAction;
import java.io.IOException;
import org.junit.After;
import static org.junit.Assert.assertTrue;

/**
 *
 * @author group08
 */
public class MoveFileActionTest {

    private static final String SOURCE_FILE_PATH = "testSource.txt";
    private static final String TARGET_DIRECTORY_PATH = "testDestination";
    private File sourceFile;
    private File targetDirectory;
    private File targetFile;

    @Before
    public void setUp() throws IOException {
        // Setting up the source file and target directory
        // Creating a new source file for testing
        sourceFile = new File(SOURCE_FILE_PATH);
        sourceFile.createNewFile();
        // Creating a target directory to move the file
        targetDirectory = new File(TARGET_DIRECTORY_PATH);
        targetDirectory.mkdir();
        // Creating a reference to the expected target file after moving
        targetFile = new File(targetDirectory, sourceFile.getName());
    }

    @After
    public void tearDown() throws IOException {
        // Cleaning up the testing environment
        // Deleting the source and target files along with the target directory
        sourceFile.delete();
        targetFile.delete();
        targetDirectory.delete();
    }

    @Test
    public void testCopyFileSuccess() {
        // Test to move the file from source to target directory
        // Creating an action to move the source file to the target directory
        MoveFileAction moveAction = new MoveFileAction(sourceFile, targetDirectory);
        moveAction.execute();
        // Checking if the file has been moved successfully to the target directory
        assertTrue(targetFile.exists());
    }

}
