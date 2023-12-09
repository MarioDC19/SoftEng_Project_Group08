package model_tests;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import softeng_project_group08.model.Rule;

/**
 *
 * @author group08
 */
public class RuleTest {

    FakeTrigger trueTrigger, falseTrigger;
    FakeAction aAction, bAction;
    Rule r1, r2, r3;
    private static final int MINUTE_IN_HOUR = 60;
    private static final int MINUTE_IN_DAY = 24 * MINUTE_IN_HOUR;

    @Before
    public void setUp() {
        trueTrigger = new FakeTrigger(1, 1);
        falseTrigger = new FakeTrigger(1, 2);
        aAction = new FakeAction("a");
        bAction = new FakeAction("b");
        // test case get 1: rule with name=A, trigger=trueTrigger, action=aAction, active=true
        r1 = new Rule("A", trueTrigger, aAction);
        // test case get 2: rule with name=B, trigger=falseTrigger, action=bAction, active=true
        r2 = new Rule("B", falseTrigger, bAction);
        // test case equals: rule with same name as r1, different trigger and action
        // Two rules are considered equal if they have the same name
        r3 = new Rule("A", falseTrigger, bAction);
    }

    // assertSame checks whether the arguments point at the same object
    // assertEquals is not preferrable because triggers and action do not have equals() defined
    @Test
    public void testGetAndSetTrigger() {
        assertSame(trueTrigger, r1.getTrigger());
        r1.setTrigger(falseTrigger);
        assertSame(falseTrigger, r1.getTrigger());

        assertSame(falseTrigger, r2.getTrigger());
        r2.setTrigger(trueTrigger);
        assertSame(trueTrigger, r2.getTrigger());
    }

    @Test
    public void testGetAndSetAction() {
        assertSame(aAction, r1.getAction());
        r1.setAction(bAction);
        assertSame(bAction, r1.getAction());

        assertSame(bAction, r2.getAction());
        r2.setAction(aAction);
        assertSame(aAction, r2.getAction());
    }

    @Test
    public void testIsActiveAndSetActive() {
        assertTrue(r1.isActive());
        r2.setActive(false);
        assertFalse(r2.isActive());
    }

    @Test
    public void testGetAndSetName() {
        assertEquals("A", r1.getName());
        r1.setName("C");
        assertEquals("C", r1.getName());

        assertEquals("B", r2.getName());
        r2.setName("D");
        assertEquals("D", r2.getName());
    }

    @Test
    public void testEquals() {
        // first if: same object
        assertEquals(r3, r3);
        // second if: equals with null
        assertNotEquals(r3, null);
        // third if: equals with object of a different class
        assertNotEquals(r3, aAction);
        // fourth if: equals between rules based on name
        assertEquals(r3, r1);
        assertNotEquals(r3, r2);
    }

    @Test
    public void testGetSetSleepingTime() {
        // after rule creation, sleeping time is zero
        assertEquals(0, r1.getSleepingTime());
        assertEquals(0, r2.getSleepingTime());

        r1.setSleepingTime(2, 3, 4);
        assertEquals(2 * MINUTE_IN_DAY + 3 * MINUTE_IN_HOUR + 4, r1.getSleepingTime());
        r2.setSleepingTime(60);
        assertEquals(60, r2.getSleepingTime());
    }

    @Test
    public void testIsRecurring() {
        // a rule is recurring if sleeping time > 0, not recurring otherwise
        r1.setSleepingTime(4);
        assertTrue(r1.isRecurring());
        r1.setSleepingTime(0);
        assertFalse(r1.isRecurring());
    }

    @Test
    public void testGetAndSetRepeat() {
        // after rule creation, repeat is null
        assertNull(r1.getRepeat());
        r1.setRepeat(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES));
        assertEquals(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), r1.getRepeat());
    }

    @Test
    public void fireActiveRule() {
        // r1 is active non-recurring after it's created, the trigger check returns true
        // the action should not be executed, the rule becomes inactive non-recurring
        r1.fire();
        assertTrue(((FakeAction) r1.getAction()).isExecuteCalled());
        assertFalse(r1.isActive());
        assertFalse(r1.isRecurring());
        // r2 is active non-recurring after it's created, the trigger check returns false
        // the action should not be executed, the rule remains active non-recurring
        r2.fire();
        assertFalse(((FakeAction) r2.getAction()).isExecuteCalled());
        assertTrue(r2.isActive());
        assertFalse(r2.isRecurring());
    }

    @Test
    public void fireInactiveRule() {
        r1.setActive(false);
        // now r1 is inactive non-recurring; the action should not be executed
        // the rule remains inactive non-recurring
        r1.fire();
        assertFalse(((FakeAction) r1.getAction()).isExecuteCalled());
        assertFalse(r1.isActive());
        assertFalse(r1.isRecurring());
    }

    @Test
    public void fireActiveRecurringRule() {
        r1.setSleepingTime(1);
        assertTrue(r1.isRecurring());
        // now r1 is active recurring; the trigger check is true so the action should be executed
        // the rule becomes inactive recurring
        r1.fire();
        assertTrue(((FakeAction) r1.getAction()).isExecuteCalled());
        assertFalse(r1.isActive());
        assertTrue(r1.isRecurring());

        r2.setSleepingTime(2);
        assertTrue(r2.isRecurring());
        // now r2 is active recurring; the trigger check is false so the action should not be executed
        // the rule remains active recurring
        r2.fire();
        assertFalse(((FakeAction) r2.getAction()).isExecuteCalled());
        assertTrue(r2.isActive());
        assertTrue(r2.isRecurring());
    }

    @Test
    public void fireInactiveRecurringRule() {
        r1.setActive(false);
        r1.setSleepingTime(1);
        r1.setRepeat(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES));
        // now r1 is inactive non-recurring, with repeat date and time set to now
        // so firing the rule should set repeat to null and the rule should become active recurring
        r1.fire();
        assertNull(r1.getRepeat());
        assertTrue(r1.isActive());
        assertTrue(r1.isRecurring());

        r2.setActive(false);
        r2.setSleepingTime(1, 2, 3);
        r2.setRepeat(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES).plusMinutes(1));
        // now r2 is inactive non-recurring, with repeat date and time that is after current date and time
        // so firing the rule should have no effect, repeat remains the same and rule remains inactive non-recurring
        r2.fire();
        assertEquals(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES).plusMinutes(1), r2.getRepeat());
        assertFalse(r2.isActive());
        assertTrue(r2.isRecurring());

        r3.setActive(false);
        r3.setSleepingTime(2);
        r3.setRepeat(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES).minusMinutes(1));
        // now r3 is inactive non-recurring, with repeat date and time that is before current date and time
        // so firing the rule should set repeat to null and the rule should become active recurring
        r3.fire();
        assertNull(r3.getRepeat());
        assertTrue(r3.isActive());
        assertTrue(r3.isRecurring());
    }

}
