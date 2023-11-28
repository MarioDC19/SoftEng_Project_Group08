package softeng_project_group08.model;

import java.util.List;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

/**
 * @author group08 Manages the rules in the application. Uses the Singleton
 * pattern to provide a shared instance. Contains methods for adding, removing,
 * and retrieving rules. Runs a background thread (RulesThread) to handle
 * rule-related tasks.
 */
public class RuleManager {

    private ObservableList<Rule> rules;
    private Rule currentRule;
    private RulesThread rt;
    // Singleton pattern: shared instance
    private static RuleManager instance = null;

    private RuleManager() {
        rules = FXCollections.observableArrayList();
        currentRule = null;

        rt = new RulesThread(rules);
        rt.setDaemon(true);
        rt.start();
    }

    public Rule getCurrentRule() {
        return currentRule;
    }

    public void setCurrentRule(Rule currentRule) {
        this.currentRule = currentRule;
    }

    // Singleton pattern: factory method
    public static RuleManager getRuleManager() {
        if (instance == null) {
            instance = new RuleManager();

        }
        return instance;
    }

    public ObservableList<Rule> getRules() {
        return rules;
    }

    /**
     * Adds the current rule to the list of rules. Displays an error message if
     * the rule already exists.
     */
    public void addRule() {
        Platform.runLater(() -> {
            if (this.rules.contains(currentRule)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("The rule " + currentRule.getName() + " has already been created");

                alert.showAndWait();
            } else {
                this.rules.add(currentRule);
                saveRulesToFile(rules); // Starting the thread once the rule has been created
            }
        });
    }

    public void removeRule(Rule rule) {
        Platform.runLater(() -> {
            this.rules.remove(rule);
            saveRulesToFile(rules); // Starting the thread once the rule has been removed
        });
    }

    /**
     * Saves the provided list of rules to a binary file asynchronously using a
     * service.
     *
     *
     */
    private void saveRulesToFile(ObservableList<Rule> rulesToSave) {
        String filePath = "ListRules.bin"; // Destination Path, binary file
        SaveRulesService saveThread = new SaveRulesService(rulesToSave, filePath);
        saveThread.start();
    }

}
