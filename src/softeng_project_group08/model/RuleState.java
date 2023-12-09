package softeng_project_group08.model;

import java.io.Serializable;

/**
 * A rule can be in one of four states: 
 * - active and not recurring 
 * - active and recurring 
 * - inactive and not recurring 
 * - inactive and recurring 
 * Package-level interface: the client can manipulate the Rule state only by
 * using public methods of the Rule class
 * 
 * @author group08
 */
interface RuleState extends Serializable {

    public abstract void fire(Rule rule);

    public abstract void changeActiveState(Rule rule);

    public abstract boolean isActive(Rule rule);

    public abstract void changeRecurringState(Rule rule);

    public abstract boolean isRecurring(Rule rule);

}
