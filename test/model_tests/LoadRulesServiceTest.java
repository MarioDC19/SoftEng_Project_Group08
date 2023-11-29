/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model_tests;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;

import javafx.embed.swing.JFXPanel;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import softeng_project_group08.model.LoadRulesService;
import softeng_project_group08.model.Rule;
import softeng_project_group08.model.ShowDialogAction;
import softeng_project_group08.model.TimeOfDayTrigger;

/**
 * This class contains JUnit tests for the LoadRulesService class
 *
 * @author group08
 */
public class LoadRulesServiceTest {
    String filePath="ListRules.bin";
    Rule r= new Rule("Rule1", new TimeOfDayTrigger(19, 35), new ShowDialogAction("Test"));

    @BeforeClass
    public static void setUpClass() {
        // Initialize JavaFX Toolkit for running JavaFX operations on the test thread
        JFXPanel jfxPanel = new JFXPanel();
    }
    @Before
    public void setUp() {

        // Creating test data
        ObservableList rules = FXCollections.observableArrayList();
        
        rules.add(r);
        ArrayList<Rule> ruleArrayList = new ArrayList<>(rules);

            // Write the serialized ArrayList to the specified file
            try (ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(filePath)))) {

                oos.writeObject(ruleArrayList);
            } catch (FileNotFoundException ex) { 
            Logger.getLogger(LoadRulesServiceTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(LoadRulesServiceTest.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }

    @After
    public void tearDown() {
        // Deleting file after the test
        File testFile = new File(filePath);
        if (testFile.exists()) {
            testFile.delete();
        }
    }

    @AfterClass
    public static void cleanUp() {
        // Exit JavaFX Platform after testing
        javafx.application.Platform.exit();
    }

    @Test
    public void testLoadRulesService() {
        try {
            LoadRulesService loadRulesService = new LoadRulesService(filePath);
            Platform.runLater(() -> {

                loadRulesService.setOnSucceeded(event -> {
                    List<Rule> loadedRules = loadRulesService.getValue();
                    // Assert that loadedRules is not null and not empty
                    assertNotNull(loadedRules);
                    assertFalse(loadedRules.isEmpty());
                    assertTrue(loadedRules.contains(r));
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
