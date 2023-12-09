package softeng_project_group08.model.triggers;

import softeng_project_group08.model.Trigger;

/**
 * Base Decorator class
 *
 * @author group08
 */
public abstract class TriggerDecorator implements Trigger {

    Trigger trigger1;

    public TriggerDecorator(Trigger trigger1) {
        this.trigger1 = trigger1;
    }

}
