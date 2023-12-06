/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package softeng_project_group08.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.stage.Stage;
import softeng_project_group08.model.DayOfMonthTrigger;

/**
 * FXML Controller class
 *
 * @author Master
 */
public class DayOfMonthTriggerScreenController implements Initializable {

    @FXML
    private Button saveButtonID;
    @FXML
    private Spinner<Integer> daySpinnerID;
    private RuleManager ruleManager;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ruleManager= RuleManager.getRuleManager();
        SpinnerValueFactory<Integer> valueFactoryDays = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 31 , 1 );
        daySpinnerID.setValueFactory(valueFactoryDays);
    }    

    @FXML
    private void saveButtonAction(ActionEvent event) {
        DayOfMonthTrigger domt = new  DayOfMonthTrigger (daySpinnerID.getValue());
        ruleManager.getCurrentRule().setTrigger(domt);
        Stage currentStage = (Stage) saveButtonID.getScene().getWindow();
        currentStage.close();
    }
    
}
