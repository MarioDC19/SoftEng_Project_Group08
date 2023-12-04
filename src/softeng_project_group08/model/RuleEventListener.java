package softeng_project_group08.model;

import java.util.EventListener;

/**
 * Interface that must be implemented by all observers that want to be notified 
 * when a change happens to a rule or a list of rules.
 * @author group08
 */
public interface RuleEventListener extends EventListener {
    
    public void update(RuleEventType eventType, Rule updatedRule);
    
}
