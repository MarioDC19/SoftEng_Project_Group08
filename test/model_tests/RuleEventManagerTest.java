package model_tests;

import java.io.File;
import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.*;
import softeng_project_group08.model.Rule;
import softeng_project_group08.model.RuleEventListener;
import softeng_project_group08.model.RuleEventManager;
import softeng_project_group08.model.RuleEventType;

/**
 *
 * @author group08
 */
public class RuleEventManagerTest {

    @After
    public void tearDown() {
        // Deleting file after the test
        String filePath = "ListRules.bin";
        File testFile = new File(filePath);
        if (testFile.exists()) {
            testFile.delete();
        }
    }

    @Test
    public void testSubscribeAndNotify() {
        RuleEventManager ruleEventManager = new RuleEventManager(RuleEventType.CHANGE);
        RuleEventListener listener = new TestRuleEventListener();

        // Subscribe the listener to the CHANGE event type
        ruleEventManager.subscribe(RuleEventType.CHANGE, listener);
        // Notify the listeners for the CHANGE event type
        ruleEventManager.notify(RuleEventType.CHANGE, new Rule("TestRule", null, null));

        assertTrue(((TestRuleEventListener) listener).isUpdateCalled());
    }

    @Test
    public void testUnsubscribe() {
        RuleEventManager ruleEventManager = new RuleEventManager(RuleEventType.CHANGE);
        RuleEventListener listener = new TestRuleEventListener();

        // Subscribe the listener to the CHANGE event type
        ruleEventManager.subscribe(RuleEventType.CHANGE, listener);
        // Unsubscribe the listener from the CHANGE event type
        ruleEventManager.unsubscribe(RuleEventType.CHANGE, listener);
        // Notify the listeners for the CHANGE event type
        ruleEventManager.notify(RuleEventType.CHANGE, new Rule("TestRule", null, null));

        assertFalse(((TestRuleEventListener) listener).isUpdateCalled());
    }

    private static class TestRuleEventListener implements RuleEventListener {

        private boolean updateCalled = false;

        @Override
        public void update(RuleEventType eventType, Rule updatedRule) {
            updateCalled = true;
        }

        public boolean isUpdateCalled() {
            return updateCalled;
        }
    }

}
