/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model_tests;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Platform;
import javafx.concurrent.Service;

import javafx.embed.swing.JFXPanel;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.BeforeClass;
import org.junit.Test;
import softeng_project_group08.model.LoadRulesService;
import softeng_project_group08.model.Rule;

/**
 * This class contains JUnit tests for the LoadRulesService class
 *
 * @author group08
 */
public class LoadRulesServiceTest {

    @BeforeClass
    public static void setUpClass() {
        // Initialize JavaFX Toolkit for running JavaFX operations on the test thread
        JFXPanel jfxPanel = new JFXPanel();
    }

    @AfterClass
    public static void cleanUp() {
        // Exit JavaFX Platform after testing
        javafx.application.Platform.exit();
    }

    @Test
    public void testLoadRulesService() {
        try {
            LoadRulesService loadRulesService = new LoadRulesService();
            Platform.runLater(() -> {

                loadRulesService.setOnSucceeded(event -> {
                    List<Rule> loadedRules = loadRulesService.getValue();
                    // Assert that loadedRules is not null and not empty
                    assertNotNull(loadedRules);
                    assertFalse(loadedRules.isEmpty());
                });
                loadRulesService.start();
            });
            //sleep to wait for the service to complete
            Thread.sleep(300);

            Platform.runLater(() -> {
                // Assert that the service state is SUCCEEDED
                assertEquals(Service.State.SUCCEEDED, loadRulesService.getState());
            });
        } catch (InterruptedException ex) {
            Logger.getLogger(LoadRulesServiceTest.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
