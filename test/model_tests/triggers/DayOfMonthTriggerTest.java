package model_tests.triggers;

import java.time.LocalDate;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import softeng_project_group08.model.triggers.DayOfMonthTrigger;

/**
 *
 * @author group08
 */
public class DayOfMonthTriggerTest {

    private DayOfMonthTrigger a, b;

    @Before
    public void setUp() {
        // Test case a: trigger on the current day
        a = new DayOfMonthTrigger(LocalDate.now().getDayOfMonth());

        // Test case b: trigger on the next day
        b = new DayOfMonthTrigger(LocalDate.now().plusDays(1).getDayOfMonth());
    }

    @Test
    public void testCheck() {
        // Test case a: should be true as it triggers on the current day
        assertEquals(true, a.check());

        // Test case b: should be false as it triggers on the next day
        assertEquals(false, b.check());
    }
}
