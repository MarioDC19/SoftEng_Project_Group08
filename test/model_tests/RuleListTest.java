package model_tests;

import org.junit.Before;
import org.junit.Test;
import java.util.Iterator;
import static org.junit.Assert.*;
import softeng_project_group08.model.DialogType;
import softeng_project_group08.model.Rule;
import static softeng_project_group08.model.RuleEventType.*;
import softeng_project_group08.model.RuleList;

/**
 *
 * @author group08
 */
public class RuleListTest {

    private RuleList ruleList;
    private Rule rule1;
    private Rule rule2;

    @Before
    public void setUp() {
        ruleList = new RuleList();
        // Creating Trigger and Action instances
        FakeTrigger trigger1 = new FakeTrigger(1, 1);
        FakeAction action1 = new FakeAction("action1");
        FakeTrigger trigger2 = new FakeTrigger(1, 2);
        FakeAction action2 = new FakeAction("action2");
        // Creating Rule instances with name, trigger, and action
        rule1 = new Rule("rule1", trigger1, action1);
        rule2 = new Rule("rule2", trigger2, action2);
    }

    @Test
    public void testAddRule() {
        assertFalse(ruleList.containsRule(rule1));
        ruleList.addRule(rule1);
        assertTrue(ruleList.containsRule(rule1));
    }

    @Test
    public void testRemoveRule() {
        ruleList.addRule(rule1);
        assertTrue(ruleList.containsRule(rule1));
        ruleList.removeRule(rule1);
        assertFalse(ruleList.containsRule(rule1));
    }

    @Test
    public void testContainsRule() {
        ruleList.addRule(rule1);
        assertTrue(ruleList.containsRule(rule1));
        assertFalse(ruleList.containsRule(rule2));
    }

    @Test
    public void testIterator() {
        ruleList.addRule(rule1);
        ruleList.addRule(rule2);
        Iterator<Rule> iterator = ruleList.iterator();
        assertTrue(iterator.hasNext());
        assertEquals(rule1, iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals(rule2, iterator.next());
        assertFalse(iterator.hasNext());
    }

    // since RuleList is both a subject and a listener of rule events, two tests are needed
    @Test
    public void testRuleObserverPatternAsSubject() {
        // create fakeRuleEventListener subscribed to the RuleList for ADD and REMOVE events
        FakeRuleEventListener listener = new FakeRuleEventListener();
        ruleList.getRuleEventManager().subscribe(ADD, listener);
        ruleList.getRuleEventManager().subscribe(REMOVE, listener);
        // add and remove the same rule, the listener should be notified twice
        ruleList.addRule(rule1);
        ruleList.removeRule(rule1);
        assertEquals(2, listener.getUpdateCalledCount());
    }

    // this covers test of update() method
    @Test
    public void testRuleObserverPatternAsListener() {
        // create fakeRuleEventListener subscribed to the RuleList for CHANGE events
        FakeRuleEventListener listener = new FakeRuleEventListener();
        ruleList.getRuleEventManager().subscribe(CHANGE, listener);
        // add a rule and change its state, ruleList should be notified
        // and it should forward the event to the listener (+1)
        ruleList.addRule(rule1);
        rule1.setActive(false);
        assertEquals(1, listener.getUpdateCalledCount());
        // remove the same rule and change its state, ruleList should not be
        // notified and it should not forward the event to the listener 
        ruleList.removeRule(rule1);
        rule1.setActive(true);
        assertEquals(1, listener.getUpdateCalledCount());
    }

    // this covers test of show() method
    @Test
    public void testDialogObserverPattern() {
        // create fakeDialogEventListener subscribed to the RuleList
        FakeDialogEventListener listener = new FakeDialogEventListener();
        ruleList.getDialogEventManager().subscribe(listener);
        // add a rule with an action to the list and generate a dialog request; 
        // the listener should be notified
        FakeAction action = new FakeAction("action");
        Rule rule = new Rule("rule", null, action);
        ruleList.addRule(rule);
        action.getDialogEventManager().requestDialog(DialogType.INFO, "", "");
        assertTrue(listener.isShowCalled());
    }

}
