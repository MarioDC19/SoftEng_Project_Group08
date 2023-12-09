package softeng_project_group08.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class utilized by subjects that need to notify observers when a change
 * to a rule or a list of rules occurs.
 *
 * @author group08
 */
public class RuleEventManager implements Serializable {

    // Map that contains a list of subscribers for every event type that the 
    // subject can geprivate Map<RuleEventType, List<RuleEventListener>> listeners;nerate.
    private Map<RuleEventType, List<RuleEventListener>> listeners;
    // listeners.keySet() returns the set of events that the subject can generate

    // The constructor accepts a variable number of eventTypes, which represent 
    // the types of events the subject can generate. 
    public RuleEventManager(RuleEventType... eventTypes) {
        listeners = new HashMap<>();
        for (RuleEventType eventType : eventTypes) {
            listeners.put(eventType, new ArrayList<>());
        }
    }

    public void subscribe(RuleEventType eventType, RuleEventListener listener) {
        if (listeners.keySet().contains(eventType)) {
            List<RuleEventListener> users = listeners.get(eventType);
            users.add(listener);
        }
    }

    // Overloaded method that subscribes the listener to all the event types
    // that the subject can generate.
    public void subscribe(RuleEventListener listener) {
        for (RuleEventType ret : listeners.keySet()) {
            subscribe(ret, listener);
        }
    }

    public void unsubscribe(RuleEventType eventType, RuleEventListener listener) {
        if (listeners.keySet().contains(eventType)) {
            List<RuleEventListener> users = listeners.get(eventType);
            users.remove(listener);
        }
    }

    // Overloaded method that unsubscribes the listener to all the event types
    // that the subject can generate.
    public void unsubscribe(RuleEventListener listener) {
        for (RuleEventType ret : listeners.keySet()) {
            unsubscribe(ret, listener);
        }
    }

    public void notify(RuleEventType eventType, Rule updatedRule) {
        List<RuleEventListener> users = listeners.get(eventType);
        for (RuleEventListener listener : users) {
            listener.update(eventType, updatedRule);
        }
    }
    
}
