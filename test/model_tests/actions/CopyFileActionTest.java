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
        sourceFile = new File(SOURCE_FILE_PATH);
        sourceFile.createNewFile();

        targetDirectory = new File(TARGET_DIRECTORY_PATH);
        targetDirectory.mkdir();

        targetFile = new File(targetDirectory, sourceFile.getName());

    }

    @After
    public void tearDown() throws IOException {
        // Tear down of the testing file
        sourceFile.delete();
        targetFile.delete();
        targetDirectory.delete();
    }

    @Test
    public void testCopyFileSuccess() {
        CopyFileAction copyAction = new CopyFileAction(sourceFile, targetDirectory);
        copyAction.execute();

        // Check if the file has been copied successfully.
        assertTrue(targetFile.exists());
    }

}
