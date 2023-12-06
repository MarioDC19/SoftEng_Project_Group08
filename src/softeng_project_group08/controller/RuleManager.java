package softeng_project_group08.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import softeng_project_group08.model.Rule;
import softeng_project_group08.model.RuleEventListener;
import softeng_project_group08.model.RuleEventType;
import softeng_project_group08.model.RuleList;

/**
 *
 * Manages the rules list in the application. Uses the Singleton
 * pattern to provide a shared instance to all controllers. Contains methods for
 * adding and and retrieving rules. Runs a background service
 * (ProcessRulesService) to handle rule-related tasks.
 * 
 * @author group08 
 */
public class RuleManager implements RuleEventListener {

    private RuleList rules;
    private Rule currentRule;
    private ProcessRulesService prs;
    private boolean subscribedToList;
    // Singleton pattern: shared instance
    private static RuleManager instance = null;
    public static final String saveLoadPath = "ListRules.bin";

    private RuleManager() {
        rules = new RuleList();
        currentRule = null;
        subscribedToList = false;
        // try to load the list from file
        loadRuleListFromFile();
        // initialize the rule process service        
        prs = new ProcessRulesService(rules);
    }

    // Singleton pattern: factory method
    public static RuleManager getRuleManager() {
        if (instance == null) {
            instance = new RuleManager();
        }
        return instance;
    }

    // Initializes the RuleManager: subscribe to the RuleList, start
    // ProcessRulesService
    public void initialize() {
        if (!subscribedToList) {
            rules.getEventManager().subscribe(this);
            subscribedToList = true;
        }
        startRulesProcess();
    }

    // starts the ProcessRulesService only if it isn't already running
    private void startRulesProcess() {
        if (!prs.isRunning()) {
            prs.start();
        }
    }

    public Rule getCurrentRule() {
        return currentRule;
    }

    public void setCurrentRule(Rule currentRule) {
        this.currentRule = currentRule;
    }

    public RuleList getRules() {
        return rules;
    }

    /**
     * Adds the current rule to the list of rules. Displays an error message if
     * the rule already exists.
     */
    public void addCurrentRule() {
        if (rules.containsRule(currentRule)) {
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("The rule " + currentRule.getName() + " has already been created");
                alert.show();
            });
        } else {
            rules.addRule(currentRule);
        }
    }

    @Override
    public void update(RuleEventType eventType, Rule updatedRule) {
        SaveRulesService saveThread = new SaveRulesService(rules, saveLoadPath);
        saveThread.start();
    }

    private void loadRuleListFromFile() {
        RuleList list = null;
        File f = new File(saveLoadPath);
        if (f.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(saveLoadPath))) {
                rules = (RuleList) ois.readObject();
                // eventManager is transient, so it must be initialized
                rules.resetEventManager();
                System.out.println("List of rules is successfully loaded");
            } catch (FileNotFoundException ex) {
                // This catch is unreachable because we have already checked the existence of the file with f.exists()
            } catch (IOException ex) {
                ex.printStackTrace();
            } catch (ClassNotFoundException ex) {
                ex.printStackTrace();
            }
        } else {
            System.out.println("I have tried to load the list of rules, but the file doesn't exist yet");
        }
    }

}
