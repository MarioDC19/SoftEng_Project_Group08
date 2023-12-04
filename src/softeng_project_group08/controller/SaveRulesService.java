package softeng_project_group08.controller;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import softeng_project_group08.model.RuleList;

/**
 * Service class responsible for saving a list of rules to a file. Extends
 * JavaFX Service to perform the file saving operation in a background thread.
 *
 * @author grou08
 */
public class SaveRulesService extends Service<Void> {

    private RuleList rules;
    private String filePath;

    public SaveRulesService(RuleList rules, String filePath) {
        this.rules = rules;
        this.filePath = filePath;
    }

    @Override
    protected Task<Void> createTask() {
        // Create a task for saving rules to a file
        return new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                saveRulesToFile();
                return null;
            }
        };
    }

    // Method to save rules to a file
    private void saveRulesToFile() {
        try {
            try (ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(filePath)))) {

                oos.writeObject(rules);

                System.out.println("RuleList saved successfully to file: " + filePath);
            }
        } catch (IOException e) {
            // Handle IO exceptions if there's an error during file writing
            System.err.println("Error while saving RuleList to file: " + filePath);
            e.printStackTrace();
        }
    }
}
