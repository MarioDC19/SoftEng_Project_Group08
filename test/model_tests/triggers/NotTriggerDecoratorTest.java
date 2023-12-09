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
    FakeTrigger ft= new FakeTrigger(3,3);
    Trigger td = new NotTriggerDecorator(ft);
    
    @Test
    public void checkTest(){
        assertFalse(td.check());
    }
    
    @Test
    public void toStringTest(){
        assertEquals(td.toString(),"NOT( " + ft + " )");
    }
}
