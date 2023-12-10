package model_tests.triggers;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.time.LocalDate;
import softeng_project_group08.model.triggers.DayOfWeekTrigger;

/**
 *
 * @author group08
 */
public class DayOfWeekTriggerTest {

    private DayOfWeekTrigger a, b;

    @Before
    public void setUp() {
        // Test case a: trigger on the current day
        a = new DayOfWeekTrigger(LocalDate.now().getDayOfWeek());
        // Test case b: trigger on the next day
        b = new DayOfWeekTrigger(LocalDate.now().plusDays(1).getDayOfWeek());
    }

    @Test
    public void testCheck() {
        // Test case a: should be true as it triggers on the current day
        assertTrue(a.check());
        // Test case b: should be false as it triggers on the next day
        assertFalse(b.check());
    }
}
