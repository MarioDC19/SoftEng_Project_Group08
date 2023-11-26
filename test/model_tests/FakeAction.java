package model_tests;

import java.io.FileOutputStream;
import softeng_project_group08.model.Action;

/**
 *
 * @author group08
 */
public class FakeAction implements Action {
    
    // Package level, test-only class
    String testfilename;
    
    FakeAction(String testfilename){
        this.testfilename = testfilename;
    }

    @Override
    public void execute() {
        try {
            FileOutputStream fileOut = new FileOutputStream(testfilename);
            fileOut.close();
        } catch (Exception e){}
    }
    
}
