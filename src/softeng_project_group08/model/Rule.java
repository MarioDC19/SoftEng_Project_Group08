package softeng_project_group08.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Represents a rule in the application. A rule consists of a name, a trigger,
 * an action,an activation status and a sleeping time. Provides methods to
 * retrieve and modify the components of the rule. Overrides the equals method
 * for comparing rules based on their names. Notifies subscribed listeners when
 * a change happens in the rule. Refer to RuleState for a list of possible
 * states that a rule can be in.
 *
 * @author group08
 */
public class Rule implements Serializable {

    private String name;
    private Trigger trigger;
    private Action action;
    private RuleEventManager ruleEventManager;

    private static final int MINUTE_IN_HOUR = 60;
    private static final int MINUTE_IN_DAY = 24 * MINUTE_IN_HOUR;

    //Represents the duration, in minutes, to wait before the rule can be activated again
    private int sleepingTime = 0;
    //Represents the next scheduled date and time for rule activation.
    private LocalDateTime repeat = null;

    //State pattern
    private RuleState state;

    //Create a non recurring rule, the rule can become recurring during its
    //creation by setting the sleeping time
    public Rule(String name, Trigger trigger, Action action) {
        this.name = name;
        this.trigger = trigger;
        this.action = action;
        this.state = new ActiveRuleState();
        // this subject can only generate "change" events
        this.ruleEventManager = new RuleEventManager(RuleEventType.CHANGE);
    }

    public Trigger getTrigger() {
        return trigger;
    }

    // there's no need to notify here, because trigger is set only during rule
    // creation, before the rule is put in a list
    public void setTrigger(Trigger trigger) {
        this.trigger = trigger;
    }

    public Action getAction() {
        return action;
    }

    // there's no need to notify here, because action is set only during rule
    // creation, before the rule is put in a list
    public void setAction(Action action) {
        this.action = action;
    }

    public boolean isActive() {
        return state.isActive(this);
    }

    public void setActive(boolean active) {
        if (this.isActive() != active) {
            state.changeActiveState(this);
        }
    }

    public String getName() {
        return name;
    }

    // there's no need to notify here, because name is set only during rule
    // creation, before the rule is put in a list
    public void setName(String name) {
        this.name = name;
    }

    public RuleEventManager getRuleEventManager() {
        return ruleEventManager;
    }

    public LocalDateTime getRepeat() {
        return repeat;
    }

    public void setRepeat(LocalDateTime repeat) {
        this.repeat = repeat;
    }

    public int getSleepingTime() {
        return sleepingTime;
    }

    public boolean isRecurring() {
        return state.isRecurring(this);
    }

    // a rule is recurring if sleeping time > 0, not recurring otherwise
    public void setSleepingTime(int sleepingTime) {
        this.sleepingTime = sleepingTime;
        if ((sleepingTime <= 0) == (this.isRecurring())) {
            state.changeRecurringState(this);
        }
    }

    public void setSleepingTime(int days, int hours, int minutes) {
        int st = days * MINUTE_IN_DAY + hours * MINUTE_IN_HOUR + minutes;
        setSleepingTime(st);
    }

    public void fire() {
        state.fire(this);
    }

    // The client can manipulate the Rule state only by using public methods of the Rule class
    void setState(RuleState state) {
        this.state = state;
        ruleEventManager.notify(RuleEventType.CHANGE, this);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Rule other = (Rule) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return true;
    }

}
