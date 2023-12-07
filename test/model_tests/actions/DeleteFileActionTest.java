
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
        sourceFile = new File(FILE_PATH);
        sourceFile.createNewFile();
    }
    
    @After
    public void tearDown(){
        sourceFile.delete();
    }
    
    @Test
    public void testDeleteFileSuccess(){
        DeleteFileAction deleteAction = new DeleteFileAction(sourceFile);
        deleteAction.execute();
        
        assertFalse(sourceFile.exists());
    }  
}
