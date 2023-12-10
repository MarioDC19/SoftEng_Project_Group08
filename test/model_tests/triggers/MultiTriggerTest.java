package model_tests.triggers;

import model_tests.FakeTrigger;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import softeng_project_group08.model.Trigger;
import softeng_project_group08.model.triggers.AndTriggerDecorator;
import softeng_project_group08.model.triggers.NotTriggerDecorator;
import softeng_project_group08.model.triggers.OrTriggerDecorator;

/**
 *
 * @author group08
 */
public class MultiTriggerTest {
    // Initializing FakeTrigger instances with specific parameters
    FakeTrigger ft = new FakeTrigger(3, 3);
    FakeTrigger ft2 = new FakeTrigger(4, 4);
    FakeTrigger ft3 = new FakeTrigger(3, 5);
    FakeTrigger ft4 = new FakeTrigger(5, 5);

    @Test
    public void multiCheck() {
        // Creating combined triggers using various decorators for testing logical operations
        // Using an OrTriggerDecorator with an AndTriggerDecorator and a NotTriggerDecorator
        Trigger combined = new OrTriggerDecorator(new AndTriggerDecorator(ft3, ft2), new NotTriggerDecorator(ft4));
        assertFalse(combined.check()); // Verifying the combined trigger returns false
        
        // Using an AndTriggerDecorator with an OrTriggerDecorator and a NotTriggerDecorator
        Trigger combined2 = new AndTriggerDecorator(new OrTriggerDecorator(ft, ft2), new NotTriggerDecorator(ft3));
        assertTrue(combined2.check()); // Verifying the combined2 trigger returns true
        
        assertFalse(new NotTriggerDecorator(combined2).check()); // Verifying the negation of combined2 returns false
        
        assertTrue(new OrTriggerDecorator(combined, combined2).check()); // Verifying the logical OR of combined and combined2 returns true
        
        assertFalse(new AndTriggerDecorator(combined, combined2).check()); // Verifying the logical AND of combined and combined2 returns false
    }
}
