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
    FakeTrigger ft = new FakeTrigger(3,3);
    FakeTrigger ft2 = new FakeTrigger(4,4);
    FakeTrigger ft3 = new FakeTrigger(3,5);
    FakeTrigger ft4 = new FakeTrigger(5,5);
    
    @Test
    public void multiCheck(){
        Trigger combined = new OrTriggerDecorator(new AndTriggerDecorator(ft3,ft2),new NotTriggerDecorator(ft4));
        assertFalse(combined.check());
        Trigger combined2 = new AndTriggerDecorator(new OrTriggerDecorator(ft,ft2),new NotTriggerDecorator(ft3));
        assertTrue(combined2.check());
        assertFalse(new NotTriggerDecorator(combined2).check());
        
        assertTrue(new OrTriggerDecorator(combined,combined2).check());
        assertFalse(new AndTriggerDecorator(combined,combined2).check());
    }
}
