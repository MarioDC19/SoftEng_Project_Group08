package model_tests.actions;

import java.io.File;
import java.io.IOException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import softeng_project_group08.model.actions.DeleteFileAction;

/**
 *
 * @author group08
 */
public class DeleteFileActionTest {

    private static final String FILE_PATH = "testSource.txt";
    private File sourceFile;

    @Before
    public void setUp() throws IOException {
        // Setting up the initial condition for testing

        // Create a source file
        sourceFile = new File(FILE_PATH);
        sourceFile.createNewFile();
    }

    @After
    public void tearDown() {
        // Cleaning up after each test
        // Delete the source file
        sourceFile.delete();
    }

    @Test
    public void testDeleteFileSuccess() {
        // Test case to check successful file deletion
        // Creating a DeleteFileAction instance with the source file
        DeleteFileAction deleteAction = new DeleteFileAction(sourceFile);
        deleteAction.execute();
        // Verifying if the source file has been deleted successfully
        assertFalse(sourceFile.exists());
    }
}
