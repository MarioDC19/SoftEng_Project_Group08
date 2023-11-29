package model_tests;

import java.io.File;
import javafx.embed.swing.JFXPanel;
import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import softeng_project_group08.model.Rule;
import softeng_project_group08.model.RuleManager;

/**
 *
 * @author group08
 */
public class RuleManagerTest {
    
    // executes the static method once before all the tests in the class
    @BeforeClass
    public static void setUpClass() {
        // Initialize JavaFX Toolkit for running JavaFX operations on the test thread
        JFXPanel jfxPanel = new JFXPanel();
    }
    
    @After
    public void tearDown() {
        // Deleting file after the test
        String filePath= "ListRules.bin";
        File testFile = new File(filePath);
        if (testFile.exists()) {
            testFile.delete();
        }
    }
    
    @Test
    public void testGetRuleManager() {
        RuleManager instance1 = RuleManager.getRuleManager();
        RuleManager instance2 = RuleManager.getRuleManager();
        // to verify the singleton pattern, instance1 and instance2 must be the same object
        assertSame(instance1, instance2);
    }
    
    @Test
    public void testGetAndSetCurrentRule(){
        RuleManager ruleManager = RuleManager.getRuleManager();
        Rule r = new Rule("r", null, null);
        ruleManager.setCurrentRule(r);
        assertSame(r, ruleManager.getCurrentRule());
    }

    @Test
    public void testAddRule() {
        RuleManager ruleManager = RuleManager.getRuleManager();
        Rule r1 = new Rule("r1", null, null);
        ruleManager.setCurrentRule(r1);
        // Adding a rule should update the rules list
        ruleManager.addRule();
        waitForRunLater();
        assertTrue(ruleManager.getRules().contains(r1));
        int oldsize = ruleManager.getRules().size();
        // Adding the same rule should not be possible, the size should remain unchanged
        ruleManager.addRule();
        waitForRunLater();
        assertEquals(oldsize, ruleManager.getRules().size());
    }

    @Test
    public void testRemoveRule() {
        RuleManager ruleManager = RuleManager.getRuleManager();
        Rule r2 = new Rule("r2", null, null);
        ruleManager.setCurrentRule(r2);
        ruleManager.addRule();
        // Removing the rule should update the rules list
        ruleManager.removeRule(r2);
        waitForRunLater();
        assertFalse(ruleManager.getRules().contains(r2));
        // Removing a non-existing rule should not change the rules list
        int oldsize = ruleManager.getRules().size();
        ruleManager.removeRule(new Rule("r3", null, null));
        waitForRunLater();
        assertEquals(oldsize, ruleManager.getRules().size());
    }
    
    // in order to test methods that use Platform.runLater(Runnable runnable)
    // the test thread must wait for the runnable's execution in the UI thread
    private static void waitForRunLater() {
        // the runnable must run in less than 300 milliseconds
        try {
            Thread.sleep(300);
        } catch (InterruptedException ex) {}
    }
    
}
