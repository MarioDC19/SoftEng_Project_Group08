package model_tests;

import softeng_project_group08.model.Trigger;

/**
 * Simple implementation of Trigger interface for testing purposes
 * 
 * @author group08
 */
public class FakeTrigger implements Trigger {

    private int a, b;

    public FakeTrigger(int a, int b) {
        // Initializes the internal state of the FakeTrigger with the provided integers
        this.a = a;
        this.b = b;
    }

    @Override
    public boolean check() {
        // Compares the stored integers a and b; returns true if they are equal, otherwise false
        return a == b;
    }

}

