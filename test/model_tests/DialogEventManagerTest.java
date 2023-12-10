package model_tests;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import softeng_project_group08.model.DialogEventManager;
import softeng_project_group08.model.DialogType;

/**
 *
 * @author group08
 */
public class DialogEventManagerTest {

    private DialogEventManager manager;
    private FakeDialogEventListener listener1, listener2;

    @Before
    public void setUp() {
        manager = new DialogEventManager();
        listener1 = new FakeDialogEventListener();
        listener2 = new FakeDialogEventListener();
    }

    @Test
    public void testSubscribeAndRequestDialog() {
        // subscribe listener1 and not listener 2; only the first should be notified
        manager.subscribe(listener1);
        manager.requestDialog(DialogType.INFO, "", "");
        assertTrue(listener1.isShowCalled());
        assertFalse(listener2.isShowCalled());
    }

    @Test
    public void testUnsubscribe() {
        // subscribe listener1 and then unsubscribe; it should not be notified
        manager.subscribe(listener1);
        manager.unsubscribe(listener1);
        manager.requestDialog(DialogType.INFO, "", "");
        assertFalse(listener1.isShowCalled());
    }

}
