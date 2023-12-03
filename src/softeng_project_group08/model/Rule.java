package softeng_project_group08.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Represents a rule in the application. A rule consists of a name, a trigger,
 * an action,an activation status and a sleeping time. Provides methods to
 * retrieve and modify the components of the rule. Overrides the equals method
 * for comparing rules based on their names.
 *
 * @author group08
 */
public class Rule implements Serializable {

    private String name;
    private Trigger trigger;
    private Action action;
    private boolean active;

    private static final int MINUTE = 1;
    private static final int MINUTE_IN_HOUR = 60;
    private static final int MINUTE_IN_DAY = 24 * MINUTE_IN_HOUR;

    //Represents the duration, in minutes, to wait before the rule can be activated again
    private int sleepingTime = 0;
    //Represents the next scheduled date for rule activation.
    private LocalDateTime repeat = null;

    public Rule(String name, Trigger trigger, Action action) {
        this.name = name;
        this.trigger = trigger;
        this.action = action;
        this.active = true;
    }

    public Rule(String name, Trigger trigger, Action action, int days, int hours, int minutes) {
        this(name, trigger, action);
        this.sleepingTime = days * MINUTE_IN_DAY + hours * MINUTE_IN_HOUR + minutes;
    }

    public Trigger getTrigger() {
        return trigger;
    }

    public void setTrigger(Trigger trigger) {
        this.trigger = trigger;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public void setSleepingTime(int sleepingTime) {
        this.sleepingTime = sleepingTime;
    }

    public void setSleepingTime(int days, int hours, int minutes) {
        this.sleepingTime = days * MINUTE_IN_DAY + hours * MINUTE_IN_HOUR + minutes;
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
