package softeng_project_group08.controller;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import softeng_project_group08.model.RuleList;

/**
 * Service class responsible for saving a list of rules to a file. Extends
 * JavaFX Service to perform the file saving operation in a background thread.
 *
 * @author group08
 */
public class SaveRulesService extends Service<Void> {

    private RuleList rules;

    public SaveRulesService(RuleList rules) {
        this.rules = rules;
    }

    @Override
    protected Task<Void> createTask() {
        // Create a task for saving rules to a file
        return new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                rules.saveToFile();
                return null;
            }
        };
    }

}
