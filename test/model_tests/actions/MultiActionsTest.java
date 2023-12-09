package model_tests.actions;

import model_tests.FakeAction;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import softeng_project_group08.model.Action;
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
    public void testAddChild() {
        assertEquals(0, multiActions.getList().size());

        multiActions.addChild(dummyAction1);
        assertEquals(1, multiActions.getList().size());

        multiActions.addChild(dummyAction2);
        assertEquals(2, multiActions.getList().size());
    }

    @Test
    public void testRemoveChild() {
        multiActions.addChild(dummyAction1);
        multiActions.addChild(dummyAction2);

        assertEquals(2, multiActions.getList().size());
        assertTrue(multiActions.getList().contains(dummyAction1));
        assertTrue(multiActions.getList().contains(dummyAction2));

        multiActions.removeChild(dummyAction1);
        assertEquals(1, multiActions.getList().size());
        assertFalse(multiActions.getList().contains(dummyAction1));
        assertTrue(multiActions.getList().contains(dummyAction2));
    }

    @Test
    public void testGetChild() {
        multiActions.addChild(dummyAction1);
        multiActions.addChild(dummyAction2);

        Action retrievedAction = multiActions.getChild(dummyAction1);

        assertNotNull(retrievedAction);
        assertEquals(dummyAction1, retrievedAction);
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

}
