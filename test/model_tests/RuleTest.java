package model_tests;

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
    Rule r1, r2, r3, r4;
    
    @Before
    public void setUp() {
        trueTrigger = new FakeTrigger(1,1);
        falseTrigger = new FakeTrigger(1,2);
        aAction = new FakeAction("a");
        bAction = new FakeAction("b");
        // test case get methods 1: rule with name=A, trigger=trueTrigger, action=aAction, active=true
        r1 = new Rule("A", trueTrigger, aAction);
        // test case get methods 2: rule with name=B, trigger=falseTrigger, action=bAction, active=true
        r2 = new Rule("B", falseTrigger, bAction);
        // test case equals: rule with same name as r1, different trigger and action
        // Two rules are considered equal if they have the same name
        r3 = new Rule("A", falseTrigger, bAction);
        // test case set methods: rule with name="C", trigger=null, action=null, active=true
        r4 = new Rule("C", null, null);
    }
    
    // assertSame checks whether the arguments point at the same object
    // assertEquals is not correct because triggers and action do not have equals() defined
    @Test
    public void testGetTrigger() {
        assertSame(trueTrigger, r1.getTrigger());
        assertNotSame(falseTrigger, r1.getTrigger());
        assertSame(falseTrigger, r2.getTrigger());
        assertNotSame(trueTrigger, r2.getTrigger());
    }
    
    @Test
    public void testSetTrigger() {
        r4.setTrigger(trueTrigger);
        assertSame(trueTrigger, r4.getTrigger());
        assertNotNull(r4.getTrigger());
    }
    
    @Test
    public void testGetAction() {
        assertSame(aAction, r1.getAction());
        assertNotSame(bAction, r1.getAction());
        assertSame(bAction, r2.getAction());
        assertNotSame(aAction, r2.getAction());
    }
    
    @Test
    public void testSetAction() {
        r4.setAction(aAction);
        assertSame(aAction, r4.getAction());
        assertNotNull(r4.getAction());
    }
    
    @Test
    public void testIsActiveAndSetActive() {
        assertTrue(r1.isActive());
        r2.setActive(false);
        assertFalse(r2.isActive());
    }
    
    @Test
    public void testGetName() {
        assertEquals("A", r1.getName());
        assertNotEquals("B", r1.getName());
        assertEquals("B", r2.getName());
        assertNotEquals("A", r2.getName());
    }
    
    @Test
    public void testSetName() {
        r4.setName("D");
        assertEquals("D", r4.getName());
        assertNotEquals("C", r4.getName());
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
        
}
