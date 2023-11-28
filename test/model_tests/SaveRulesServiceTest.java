package model_tests;

/**
 *
 * @author group08
 */
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import org.junit.Before;
import org.junit.Test;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.embed.swing.JFXPanel;
import org.junit.After;

import static org.junit.Assert.*;
import org.junit.BeforeClass;
import softeng_project_group08.model.Rule;
import softeng_project_group08.model.SaveRulesService;
import softeng_project_group08.model.ShowDialogAction;
import softeng_project_group08.model.TimeOfDayTrigger;

/**
 *
 * @author group08
 */
public class SaveRulesServiceTest {

    private ObservableList<Rule> rules;
    private String filePath;

    @BeforeClass
    public static void setUpClass() {
        // Initialize JavaFX Toolkit for running JavaFX operations on the test thread
        JFXPanel jfxPanel = new JFXPanel();
    }

    @Before
    public void setUp() {

        // Creating test data
        rules = FXCollections.observableArrayList();
        rules.add(new Rule("Rule1", new TimeOfDayTrigger(19, 35), new ShowDialogAction("Test")));
        filePath = "testFile.dat";
    }

    @After
    public void tearDown() {
        // Deleting file after the test
        File testFile = new File(filePath);
        if (testFile.exists()) {
            testFile.delete();
        }
    }

    @Test
    public void testSaveRulesService() {
        try {
            // Creating the service
            SaveRulesService saveRulesService = new SaveRulesService(rules, filePath);
            Platform.runLater(() -> {
                // Setting the handler for the succeeded event
                saveRulesService.setOnSucceeded(event -> {
                    // Verifying if the file has been created successfully
                    File testFile = new File(filePath);
                    assertTrue(testFile.exists());
                });

                saveRulesService.setOnFailed(event -> {
                    fail("The service failed to perform the operation");
                });

                // Starting the service after setting the handlers
                saveRulesService.start();

            });
            // Waiting for a certain period for the service to complete (used just for this test)
            Thread.sleep(300); // Waiting time in milliseconds (e.g., 300 mseconds)
            Platform.runLater(() -> {
                // Verifying the final state of the service
                assertEquals(Service.State.SUCCEEDED, saveRulesService.getState());

            });
        } catch (InterruptedException ex) {
            Logger.getLogger(SaveRulesServiceTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
