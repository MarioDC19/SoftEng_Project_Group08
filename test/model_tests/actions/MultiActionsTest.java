package model_tests.actions;

import model_tests.FakeAction;
import model_tests.FakeDialogEventListener;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import softeng_project_group08.model.Action;
import softeng_project_group08.model.DialogType;
import softeng_project_group08.model.actions.MultiActions;

/**
 *
 * @author group08
 */
public class MultiActionsTest {

    private MultiActions multiActions;
    private MultiActions multiActions2;
    private FakeAction dummyAction1;
    private FakeAction dummyAction2;

    @Before
    public void setUp() {
        multiActions = new MultiActions();
        dummyAction1 = new FakeAction("Action 1");
        dummyAction2 = new FakeAction("Action 2");
        multiActions2 = new MultiActions();
    }

    @Test
    public void testAddChildAndContainsChild() {
        assertFalse(multiActions.containsChild(dummyAction1));
        assertFalse(multiActions.containsChild(dummyAction2));
        multiActions.addChild(dummyAction1);
        assertTrue(multiActions.containsChild(dummyAction1));
        assertFalse(multiActions.containsChild(dummyAction2));
        multiActions.addChild(dummyAction2);
        assertTrue(multiActions.containsChild(dummyAction1));
        assertTrue(multiActions.containsChild(dummyAction2));
    }

    @Test
    public void testRemoveChildAndContainsChild() {
        multiActions.addChild(dummyAction1);
        multiActions.addChild(dummyAction2);
        multiActions.removeChild(dummyAction1);
        assertFalse(multiActions.containsChild(dummyAction1));
        assertTrue(multiActions.containsChild(dummyAction2));
        multiActions.removeChild(dummyAction2);
        assertFalse(multiActions.containsChild(dummyAction1));
        assertFalse(multiActions.containsChild(dummyAction2));
    }

    @Test
    public void testExecute() {
        // test case multiaction composed of two simple action
        multiActions.addChild(dummyAction1);
        multiActions.addChild(dummyAction2);
        multiActions.execute();
        assertTrue(dummyAction1.isExecuteCalled());
        assertTrue(dummyAction2.isExecuteCalled());
    }

    @Test
    public void testExecute2() {
        // test case multiaction composed of another multiaction and a simple action
        multiActions.addChild(dummyAction1);
        multiActions.addChild(dummyAction2);

        assertFalse(dummyAction1.isExecuteCalled());

        multiActions2.addChild(multiActions);
        FakeAction dummyAction3 = new FakeAction("Action3");
        multiActions2.addChild(dummyAction3);
        multiActions2.execute();

        assertTrue(dummyAction1.isExecuteCalled());
        assertTrue(dummyAction2.isExecuteCalled());
        assertTrue(dummyAction3.isExecuteCalled());
    }

    @Test
    public void testToString() {
        multiActions.addChild(dummyAction1);
        multiActions.addChild(dummyAction2);

        String expected = "Action 1\nAction 2\n";
        assertEquals(expected, multiActions.toString());
    }
    
    @Test
    public void testDialogObserverPattern() {
        // add an action as a child to multiactions, and subscribe a listener
        // to multiactions: when the child actions generates a dialog request,
        // listener should be notified
        multiActions.addChild(dummyAction1);
        FakeDialogEventListener listener = new FakeDialogEventListener();
        multiActions.getDialogEventManager().subscribe(listener);
        dummyAction1.getDialogEventManager().requestDialog(DialogType.INFO, "", "");
        assertTrue(listener.isShowCalled());
    }

}
