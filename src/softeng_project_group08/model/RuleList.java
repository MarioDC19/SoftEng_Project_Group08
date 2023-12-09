package softeng_project_group08.model;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
 * @author group08
 */
public class RuleList implements Iterable<Rule>, RuleEventListener, Serializable {

    private List<Rule> rules;
    // eventManager in this case must not be serialized, subscriptions are added
    // at application start
    private transient RuleEventManager eventManager;
    public static final String saveLoadPath = "ListRules.bin";

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
    private void resetEventManager(){
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
    
    // load RuleList from file and return it
    public static RuleList loadFromFile() {
        RuleList list = null;
        File f = new File(saveLoadPath);
        if (f.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(saveLoadPath))) {
                list = (RuleList) ois.readObject();
                // eventManager is transient, so it must be initialized
                list.resetEventManager();
                System.out.println("Rule list successfully loaded");
            } catch (FileNotFoundException ex) {
                // This catch is unreachable because we have already checked the existence of the file with f.exists()
            } catch (IOException ex) {
                ex.printStackTrace();
            } catch (ClassNotFoundException ex) {
                ex.printStackTrace();
            }
        } else {
            System.out.println("Rule list cannot be loaded, the file doesn't exist yet");
        }
        return list;
    }
    
    // save this object to file
    public void saveToFile() {
        try {
            try (ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(saveLoadPath)))) {
                oos.writeObject(this);
                System.out.println("RuleList saved successfully to file: " + saveLoadPath);
            }
        } catch (IOException e) {
            // Handle IO exceptions if there's an error during file writing
            System.err.println("Error while saving RuleList to file: " + saveLoadPath);
            e.printStackTrace();
        }
    }
    
}
