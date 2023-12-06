/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model_tests;

import java.time.LocalDate;
import static org.junit.Assert.assertEquals;
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
        assertEquals(true, a.check());

        // Test case b: should be false as it triggers on the next day
        assertEquals(false, b.check());
    }
}
