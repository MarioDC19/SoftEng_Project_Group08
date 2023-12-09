package softeng_project_group08.model.triggers;

import softeng_project_group08.model.Trigger;

/**
 * Concrete Decorator. It allows extending the check of a trigger using the
 * logical NOT operator.
 *
 * @author group08
 */
public class NotTriggerDecorator extends TriggerDecorator {

    public NotTriggerDecorator(Trigger trigger) {
        super(trigger);
    }

    @Override
    public boolean check() {
        return !super.trigger1.check();
    }

    @Override
    public String toString() {
        return "NOT( " + super.trigger1 + " )";
    }

}
