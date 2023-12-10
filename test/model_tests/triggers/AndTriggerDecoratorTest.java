package model_tests.triggers;

import model_tests.FakeTrigger;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import softeng_project_group08.model.Trigger;
import softeng_project_group08.model.triggers.AndTriggerDecorator;

/**
 *
 * @author group08
 */
public class AndTriggerDecoratorTest {

    // Creating FakeTrigger instances for testing
    FakeTrigger ft = new FakeTrigger(3, 3);
    FakeTrigger ft2 = new FakeTrigger(4, 4);
    FakeTrigger ft3 = new FakeTrigger(5, 4);
    FakeTrigger ft4 = new FakeTrigger(6, 4);
    // Test case where the check method returns true for both of them.
    Trigger td = new AndTriggerDecorator(ft, ft2);
    // Test case where the check method returns true for the first trigger and false for the second.
    Trigger td2 = new AndTriggerDecorator(ft, ft3);
    // Test case where the check method returns false for both of them.
    Trigger td3 = new AndTriggerDecorator(ft4, ft3);

    @Test
    public void checkTest() {
        // Verifying the behavior of AndTriggerDecorator's check method
        // Expects true as both ft and ft2 return true
        assertTrue(td.check());
        // Expects false as ft returns true, but ft3 returns false
        assertFalse(td2.check());
        // Expects false as both ft4 and ft3 return false
        assertFalse(td3.check());
    }

    @Test
    public void toStringTest() {
        assertEquals(td.toString(), "( " + ft + " AND\n " + ft2 + " )");
    }
}
