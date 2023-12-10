package model_tests.triggers;

import model_tests.FakeTrigger;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import org.junit.Test;
import softeng_project_group08.model.Trigger;
import softeng_project_group08.model.triggers.NotTriggerDecorator;

/**
 *
 * @author group08
 */
public class NotTriggerDecoratorTest {
    // Initializing a FakeTrigger instance and wrapping it with a NotTriggerDecorator
    FakeTrigger ft = new FakeTrigger(3, 3);
    Trigger td = new NotTriggerDecorator(ft);
    
    @Test
    public void checkTest() {
        // Verifying the behavior of the NotTriggerDecorator's check method
        assertFalse(td.check()); // Expects the negation of the FakeTrigger's check() method, hence false
    }
    
    @Test
    public void toStringTest() {
        assertEquals(td.toString(), "NOT( " + ft + " )"); 
    }
}
