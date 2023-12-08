package softeng_project_group08.model;

/**
 * State that represents when the rule is inactive and not recurring. From this
 * state the rule can only become active and not recurring, because the user
 * cannot add the sleeping time once a rule is created.
 *
 * @author group08
 */
class InactiveRuleState implements RuleState {

    @Override
    public void fire(Rule rule) {
        // if the rule is inactive nothing happens
        System.out.println("Rule " + rule.getName() + " state: inactive");
    }

    @Override
    public void changeActiveState(Rule rule) {
        rule.setState(new ActiveRuleState());
    }

    @Override
    public boolean isActive(Rule rule) {
        return false;
    }

    @Override
    public void changeRecurringState(Rule rule) {
        rule.setState(new InactiveRecurringRuleState());
    }

    @Override
    public boolean isRecurring(Rule rule) {
        return false;
    }

}
