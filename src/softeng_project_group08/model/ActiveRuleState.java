package softeng_project_group08.model;

/**
 * State that represents when the rule is active and not recurring.
 * Package-level class: the client can manipulate the Rule state only by using 
 * public methods of the Rule class
 * 
 * @author group08
 */
class ActiveRuleState implements RuleState {

    @Override
    public void fire(Rule rule) {
        System.out.println("Rule " + rule.getName() + " state: active");
        if (rule.getTrigger().check()) {
            rule.getAction().execute();
            changeActiveState(rule);
        }
    }

    @Override
    public void changeActiveState(Rule rule) {
        rule.setState(new InactiveRuleState());
    }

    @Override
    public boolean isActive(Rule rule) {
        return true;
    }

    @Override
    public void changeRecurringState(Rule rule) {
        rule.setState(new ActiveRecurringRuleState());
    }

    @Override
    public boolean isRecurring(Rule rule) {
        return false;
    }

}
