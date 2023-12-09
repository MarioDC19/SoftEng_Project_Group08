package softeng_project_group08.model;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * State that represents when the rule is active and recurring, with a specified
 * sleeping time. 
 * Package-level class: the client can manipulate the Rule state only by using 
 * public methods of the Rule class
 * 
 * @author group08
 */
class ActiveRecurringRuleState implements RuleState {

    @Override
    public void fire(Rule rule) {
        System.out.println("Rule " + rule.getName() + " state: active and recurring");
        if (rule.getTrigger().check()) {
            rule.getAction().execute();
            // Sets the next activation date for the rule based on the current time and sleeping time.
            rule.setRepeat(LocalDateTime.now().plus(rule.getSleepingTime(), ChronoUnit.MINUTES).truncatedTo(ChronoUnit.MINUTES));
            System.out.println("Rule " + rule.getName() + " triggered, next activation: " + rule.getRepeat());
            changeActiveState(rule);
        }
    }

    @Override
    public void changeActiveState(Rule rule) {
        rule.setState(new InactiveRecurringRuleState());
    }

    @Override
    public boolean isActive(Rule rule) {
        return true;
    }

    @Override
    public void changeRecurringState(Rule rule) {
        rule.setState(new ActiveRuleState());
    }

    @Override
    public boolean isRecurring(Rule rule) {
        return true;
    }

}
