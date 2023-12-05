/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package softeng_project_group08.controller;

import java.net.URL;
import java.time.DayOfWeek;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import softeng_project_group08.model.DayOfWeekTrigger;

/**
 * FXML Controller class
 *
 * @author mauri
 */
public class DayOfWeekTriggerScreenController implements Initializable {

    @FXML
    private Button saveButtonID;
    @FXML
    private ChoiceBox<DayOfWeek> dayOfWeekID; 

    private DayOfWeek selectedDay;
    private RuleManager ruleManager;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        dayOfWeekID.getItems().addAll(DayOfWeek.values());
        
        ruleManager = RuleManager.getRuleManager();

        // Set a converter to display the names of the days instead of the enum values
        dayOfWeekID.setConverter(new StringConverter<DayOfWeek>() {
            @Override
            public String toString(DayOfWeek dayOfWeek) {
                return dayOfWeek.toString();
            }

            @Override
            public DayOfWeek fromString(String string) {
                return DayOfWeek.valueOf(string);
            }
        });
        
        saveButtonID.setDisable(true);

        dayOfWeekID.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            saveButtonID.setDisable(newValue == null);
        });
        
        
    }

    @FXML
    private void saveButtonAction(ActionEvent event) {
        selectedDay = dayOfWeekID.getValue();

        System.out.println("You have selected: " + selectedDay);
        DayOfWeekTrigger dayTrigger = new DayOfWeekTrigger(selectedDay);
        ruleManager.getCurrentRule().setTrigger(dayTrigger);

        Stage currentStage = (Stage) saveButtonID.getScene().getWindow();
        currentStage.close();
    }
}

