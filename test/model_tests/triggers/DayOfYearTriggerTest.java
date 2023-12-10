package model_tests.triggers;

import java.time.LocalDate;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import softeng_project_group08.model.triggers.DayOfYearTrigger;

/**
 *
 * @author group08
 */
public class DayOfYearTriggerTest {

    private DayOfYearTrigger a, b;

    @Before
    public void setUp() {
        // Test case a: trigger on the current day
        a = new DayOfYearTrigger(LocalDate.now());

        // Test case b: trigger on the next day
        b = new DayOfYearTrigger(LocalDate.now().plusDays(1));
    }

    @Test
    public void testCheck() {
        // Test case a: should be true as it triggers on the current day
        assertTrue(a.check());
        // Test case b: should be false as it triggers on the next day
        assertFalse(b.check());
    }
}
