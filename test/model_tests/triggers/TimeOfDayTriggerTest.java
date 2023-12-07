package model_tests.triggers;

import java.time.LocalTime;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import softeng_project_group08.model.triggers.TimeOfDayTrigger;

/**
 *
 * @author group08
 */
public class TimeOfDayTriggerTest {

    private TimeOfDayTrigger a, b, c, d, e;

    @Before
    public void setUp() {
        // Test case a: trigger at this hour and minute
        a = new TimeOfDayTrigger(LocalTime.now().getHour(),
                LocalTime.now().getMinute());
        // Test case b: trigger 2 hours and 20 minutes later
        b = new TimeOfDayTrigger(LocalTime.now().plusHours(2).getHour(),
                LocalTime.now().plusMinutes(20).getMinute());
        // Test case c: trigger 3 hours and 40 minutes before
        c = new TimeOfDayTrigger(LocalTime.now().minusHours(3).getHour(),
                LocalTime.now().minusMinutes(40).getMinute());
        // Test case d: trigger one minute after
        d = new TimeOfDayTrigger(LocalTime.now().getHour(),
                LocalTime.now().plusMinutes(1).getMinute());
        // Test case e: trigger one minute before
        e = new TimeOfDayTrigger(LocalTime.now().getHour(),
                LocalTime.now().minusMinutes(1).getMinute());
    }

    @Test
    public void testCheck() {
        assertEquals(true, a.check());
        assertEquals(false, b.check());
        assertEquals(false, c.check());
        assertEquals(false, d.check());
        assertEquals(false, e.check());
    }

}
