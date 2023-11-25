package model_tests;

import softeng_project_group08.model.Trigger;

/**
 *
 * @author group08
 */
public class FakeTrigger implements Trigger {
    
    // Package level, test-only class
    int a, b;
    
    FakeTrigger(int a, int b){
        this.a = a;
        this.b = b;
    }

    @Override
    public boolean check() {
        return a == b;
    }
    
}
