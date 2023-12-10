package model_tests;

import softeng_project_group08.model.Rule;
import softeng_project_group08.model.RuleEventListener;
import softeng_project_group08.model.RuleEventType;

/**
 * Simple implementation of RuleEventListener interface for testing purposes
 * 
 * @author group08
 */
public class FakeRuleEventListener implements RuleEventListener {
    
    private int updateCalledCount = 0;

    @Override
    public void update(RuleEventType eventType, Rule updatedRule) {
        // This method is invoked when a rule event occurs
        updateCalledCount++; // Increment the count for each update call
    }
    
    public int getUpdateCalledCount() {
        // Returns the count of times the update method was called
        return updateCalledCount;
    }
    
}

