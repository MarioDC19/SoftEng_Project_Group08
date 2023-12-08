package softeng_project_group08.model;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * State that represents when the rule is inactive and recurring, with a
 * specified sleeping time. From this state the rule can only become active and
 * recurring, because the user cannot remove the sleeping time once a rule is
 * created.
 *
 * @author group08
 */
class InactiveRecurringRuleState implements RuleState {

    @Override
    public void fire(Rule rule) {
        System.out.println("Rule " + rule.getName() + " state: inactive and recurring");
        LocalDateTime currentTime = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
        if ((rule.getRepeat() != null) && 
                (rule.getRepeat().equals(currentTime) || rule.getRepeat().isBefore(currentTime))) {
            rule.setRepeat(null);
            System.out.println("Rule " + rule.getName() + " reactivated");
            changeActiveState(rule);
        }
    }

    @Override
    public void changeActiveState(Rule rule) {
        rule.setState(new ActiveRecurringRuleState());
    }

    @Override
    public boolean isActive(Rule rule) {
        return false;
    }

    @Override
    public void changeRecurringState(Rule rule) {
        rule.setState(new InactiveRuleState());
    }

    @Override
    public boolean isRecurring(Rule rule) {
        return true;
    }

}
