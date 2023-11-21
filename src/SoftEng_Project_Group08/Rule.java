package SoftEng_Project_Group08;

/**
 *
 * @author Master
 */
public class Rule {
    
    private Trigger trigger;
    private Action action;
    private Boolean active;

    public Rule(Trigger trigger, Action action) {
        this.trigger = trigger;
        this.action = action;
        this.active=true;
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

    public Boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
    
    
    
}
