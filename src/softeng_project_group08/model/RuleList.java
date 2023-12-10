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
 * is iterated upon, the iteration must be done in a synchronized block This is
 * subscribed to all the contained rules for rule events, and to all rules'
 * actions for dialog events; the events are forwarded to listeners of this
 * object.
 *
 * @author group08
 */
public class RuleList implements Iterable<Rule>, RuleEventListener, DialogEventListener, Serializable {

    private List<Rule> rules;
    // eventManager in this case must not be serialized, subscriptions are added
    // at application start
    private transient RuleEventManager ruleEventManager;
    private transient DialogEventManager dialogEventManager;
    public static final String saveLoadPath = "ListRules.bin";

    public RuleList() {
        this.rules = new ArrayList<>();
        this.ruleEventManager = new RuleEventManager(RuleEventType.ADD,
                RuleEventType.REMOVE,
                RuleEventType.CHANGE);
        this.dialogEventManager = new DialogEventManager();
    }

    public RuleEventManager getRuleEventManager() {
        return ruleEventManager;
    }

    public DialogEventManager getDialogEventManager() {
        return dialogEventManager;
    }

    // helper method, must be used only when this object is read from file and
    // event managers must be initialized
    private void resetEventManagers() {
        this.ruleEventManager = new RuleEventManager(RuleEventType.ADD,
                RuleEventType.REMOVE,
                RuleEventType.CHANGE);
        this.dialogEventManager = new DialogEventManager();
    }

    public synchronized void addRule(Rule rule) {
        rules.add(rule);
        // this list must be notified when an added rule is changed
        rule.getRuleEventManager().subscribe(this);
        // this list must be notified when an added rule's action generates a dialog request
        if (rule.getAction().getDialogEventManager() != null) {
            rule.getAction().getDialogEventManager().subscribe(this);
        }
        // notify observers that the list has changed
        ruleEventManager.notify(RuleEventType.ADD, rule);
    }

    public synchronized void removeRule(Rule rule) {
        rules.remove(rule);
        // this list does not need to be notified about removed rules
        rule.getRuleEventManager().unsubscribe(this);
        // this list does not need be notified when a removed rule's action generates a dialog request
        if (rule.getAction().getDialogEventManager() != null) {
            rule.getAction().getDialogEventManager().unsubscribe(this);
        }
        // notify observers that the list has changed
        ruleEventManager.notify(RuleEventType.REMOVE, rule);
    }

    public boolean containsRule(Rule rule) {
        return rules.contains(rule);
    }

    // when this method is called, a rule of this list has been modified
    @Override
    public void update(RuleEventType eventType, Rule updatedRule) {
        // forward the event to observers of this list
        ruleEventManager.notify(RuleEventType.CHANGE, updatedRule);
    }

    // when this method is called, an action of a rule contained in this list
    // has generated a dialog request
    @Override
    public void show(DialogType dialogType, String title, String message) {
        // forward the event to subscribed listeners
        dialogEventManager.requestDialog(dialogType, title, message);
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
                list.resetEventManagers();
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
