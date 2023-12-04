package softeng_project_group08.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Represents a list of rules, it's notified when a rule it contains is changed
 * and notifies all listeners when a rule it contains changes or when the list
 * itself changes. Thread-safe class with respect to add and remove; if the list
 * is iterated upon, the iteration must be done in a synchronized block
 *
 * @author mario
 */
public class RuleList implements Iterable<Rule>, RuleEventListener, Serializable {

    private List<Rule> rules;
    // eventManager in this case must not be serialized, subscriptions are added
    // at application start
    private transient RuleEventManager eventManager;

    public RuleList() {
        this.rules = new ArrayList<>();
        this.eventManager = new RuleEventManager(RuleEventType.ADD,
                RuleEventType.REMOVE,
                RuleEventType.CHANGE);
    }
    
    public RuleEventManager getEventManager(){
        return eventManager;
    }
    
    // helper method, must be used only when this object is read from file and
    // eventManager must be initialized
    public void resetEventManager(){
        this.eventManager = new RuleEventManager(RuleEventType.ADD,
                RuleEventType.REMOVE,
                RuleEventType.CHANGE);
    }

    public synchronized void addRule(Rule rule) {
        rules.add(rule);
        // this list must be notified when an added rule is changed
        rule.getEventManager().subscribe(this);
        // notify observers that the list has changed
        eventManager.notify(RuleEventType.ADD, rule);
    }

    public synchronized void removeRule(Rule rule) {
        rules.remove(rule);
        // this list does not need to be notified about removed rules
        rule.getEventManager().unsubscribe(this);
        // notify observers that the list has changed
        eventManager.notify(RuleEventType.REMOVE, rule);
    }

    public boolean containsRule(Rule rule) {
        return rules.contains(rule);
    }

    // when this method is called, a rule of this list has been modified
    @Override
    public void update(RuleEventType eventType, Rule updatedRule) {
        // forward the event to observers of this list
        eventManager.notify(RuleEventType.CHANGE, updatedRule);
    }

    @Override
    public Iterator<Rule> iterator() {
        return rules.iterator();
    }

}
