package model_tests;

import org.junit.Test;
import static org.junit.Assert.*;
import softeng_project_group08.model.Rule;
import softeng_project_group08.model.RuleEventManager;
import softeng_project_group08.model.RuleEventType;
import static softeng_project_group08.model.RuleEventType.*;

/**
 *
 * @author group08
 */
public class RuleEventManagerTest {

    // test cases are checked in the same test method in order to notify
    // more listeners at the same time
    @Test
    public void testSubscribeAndNotify() {
        // Create ruleEventManager that can generate every eventType
        RuleEventManager ruleEventManager = new RuleEventManager(CHANGE, ADD, REMOVE);
        // Test case 1: listener subscribed to all eventTypes
        FakeRuleEventListener listener1 = new FakeRuleEventListener();
        ruleEventManager.subscribe(listener1);
        // Test case 2: listener subscribed only to the CHANGE event type
        FakeRuleEventListener listener2 = new FakeRuleEventListener();
        ruleEventManager.subscribe(CHANGE, listener2);
        // Test case 3: listener subscribed to ADD and REMOVE event type
        FakeRuleEventListener listener3 = new FakeRuleEventListener();
        ruleEventManager.subscribe(ADD, listener3);
        ruleEventManager.subscribe(REMOVE, listener3);
        // Notify the listeners for the CHANGE event type
        // listeners 1 and 2 should be notified (+1)
        ruleEventManager.notify(CHANGE, null);
        assertEquals(1, listener1.getUpdateCalledCount());
        assertEquals(1, listener2.getUpdateCalledCount());
        assertEquals(0, listener3.getUpdateCalledCount());
        // Notify the listeners for the ADD event type
        // listeners 1 and 3 should be notified (+1)
        ruleEventManager.notify(ADD, null);
        assertEquals(2, listener1.getUpdateCalledCount());
        assertEquals(1, listener2.getUpdateCalledCount());
        assertEquals(1, listener3.getUpdateCalledCount());
        // Notify the listeners for the REMOVE event type
        // listeners 1 and 3 should be notified (+1)
        ruleEventManager.notify(REMOVE, null);
        assertEquals(3, listener1.getUpdateCalledCount());
        assertEquals(1, listener2.getUpdateCalledCount());
        assertEquals(2, listener3.getUpdateCalledCount());
    }

    @Test
    public void testUnsubscribe() {
        // Create ruleEventManager that can generate only CHANGE events
        RuleEventManager ruleEventManager = new RuleEventManager(RuleEventType.CHANGE);
        FakeRuleEventListener listener = new FakeRuleEventListener();
        // Subscribe the listener to the CHANGE event type
        ruleEventManager.subscribe(RuleEventType.CHANGE, listener);
        // Unsubscribe the listener from the CHANGE event type
        ruleEventManager.unsubscribe(RuleEventType.CHANGE, listener);
        // Notify the listeners for the CHANGE event type
        ruleEventManager.notify(RuleEventType.CHANGE, new Rule("TestRule", null, null));
        // Since the listener is unsubscribed, notify should have no effect (updateCount = 0)
        assertEquals(0, listener.getUpdateCalledCount());
    }

}
