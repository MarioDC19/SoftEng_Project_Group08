package softeng_project_group08.model.triggers;

import softeng_project_group08.model.Trigger;

/**
 * Concrete Decorator. It allows extending the check of a trigger with an
 * additional trigger using the logical OR operator.
 *
 * @author group08
 */
public class OrTriggerDecorator extends TriggerDecorator {

    private Trigger trigger2;

    public OrTriggerDecorator(Trigger trigger1, Trigger trigger2) {
        super(trigger1);
        this.trigger2 = trigger2;
    }

    @Override
    public boolean check() {
        return super.trigger1.check() || trigger2.check();
    }

    @Override
    public String toString() {
        return "( " + super.trigger1 + " OR\n " + trigger2 + " )";
    }
}
