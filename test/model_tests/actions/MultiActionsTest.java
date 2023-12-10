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
        // Creating instances for testing
        multiActions = new MultiActions();
        dummyAction1 = new FakeAction("Action 1");
        dummyAction2 = new FakeAction("Action 2");
        multiActions2 = new MultiActions();
    }

    @Test
    public void testAddChildAndContainsChild() {
        // Testing adding and checking children in MultiActions

        // Initially, MultiActions doesn't contain dummyAction1 or dummyAction2
        assertFalse(multiActions.containsChild(dummyAction1));
        assertFalse(multiActions.containsChild(dummyAction2));

        // After adding dummyAction1, it should be contained, while dummyAction2 remains uncontained
        multiActions.addChild(dummyAction1);
        assertTrue(multiActions.containsChild(dummyAction1));
        assertFalse(multiActions.containsChild(dummyAction2));

        // Adding dummyAction2 should result in both actions being contained
        multiActions.addChild(dummyAction2);
        assertTrue(multiActions.containsChild(dummyAction1));
        assertTrue(multiActions.containsChild(dummyAction2));
    }

    @Test
    public void testRemoveChildAndContainsChild() {
        // Testing removing children from MultiActions

        // Setting up MultiActions with both dummyAction1 and dummyAction2 as children
        multiActions.addChild(dummyAction1);
        multiActions.addChild(dummyAction2);

        // Removing dummyAction1 should result in it being uncontained while dummyAction2 remains
        multiActions.removeChild(dummyAction1);
        assertFalse(multiActions.containsChild(dummyAction1));
        assertTrue(multiActions.containsChild(dummyAction2));

        // Removing dummyAction2 should leave MultiActions empty of children
        multiActions.removeChild(dummyAction2);
        assertFalse(multiActions.containsChild(dummyAction1));
        assertFalse(multiActions.containsChild(dummyAction2));
    }

    @Test
    public void testExecute() {
        // Testing the execution of child actions within MultiActions

        // Adding dummyAction1 and dummyAction2 as children to MultiActions
        multiActions.addChild(dummyAction1);
        multiActions.addChild(dummyAction2);

        // Executing MultiActions should trigger the execution of both dummyAction1 and dummyAction2
        multiActions.execute();
        assertTrue(dummyAction1.isExecuteCalled());
        assertTrue(dummyAction2.isExecuteCalled());
    }

    @Test
    public void testExecute2() {
        // Testing execution involving a MultiActions within another MultiActions

        // Adding dummyAction1 and dummyAction2 to multiActions
        multiActions.addChild(dummyAction1);
        multiActions.addChild(dummyAction2);

        // Setting up a second MultiActions (multiActions2) containing multiActions and a new action (dummyAction3)
        multiActions2.addChild(multiActions);
        FakeAction dummyAction3 = new FakeAction("Action3");
        multiActions2.addChild(dummyAction3);

        // Executing multiActions2 should trigger execution of its children, including multiActions, dummyAction1, dummyAction2, and dummyAction3
        multiActions2.execute();
        assertTrue(dummyAction1.isExecuteCalled());
        assertTrue(dummyAction2.isExecuteCalled());
        assertTrue(dummyAction3.isExecuteCalled());
    }

    @Test
    public void testToString() {
        // Testing the toString() method of MultiActions

        // Adding dummyAction1 and dummyAction2 as children to MultiActions
        multiActions.addChild(dummyAction1);
        multiActions.addChild(dummyAction2);

        // The toString() method should return a string containing the names of its children separated by newlines
        String expected = "Action 1\nAction 2\n";
        assertEquals(expected, multiActions.toString());
    }

    @Test
    public void testDialogObserverPattern() {
        // Testing the Observer pattern for dialog events in MultiActions and its children
        // Adding dummyAction1 as a child to multiActions
        multiActions.addChild(dummyAction1);
        // Creating a fake event listener and subscribing it to multiActions
        FakeDialogEventListener listener = new FakeDialogEventListener();
        multiActions.getDialogEventManager().subscribe(listener);
        // Requesting a dialog event from dummyAction1 should trigger the listener to receive the event notification
        dummyAction1.getDialogEventManager().requestDialog(DialogType.INFO, "", "");
        assertTrue(listener.isShowCalled());
    }

}
