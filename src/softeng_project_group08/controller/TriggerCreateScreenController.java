package softeng_project_group08.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

/**
 * Manages trigger selection and configuration for rule creation. Controls the
 * interface for selecting triggers and updating the rule's trigger settings.
 *
 * @author group08
 */
public class TriggerCreateScreenController implements Initializable {
    @FXML
    private Button backID;
    @FXML
    private Button saveButtonID;
    @FXML
    private RadioButton hourTriggerID;

    private RuleManager ruleManager;

    ChangeScreen cs = new ChangeScreen();
    
    ToggleGroup tg;
    
    @FXML
    private RadioButton dayOfWeekID;
    @FXML
    private RadioButton dayOfMonthID;
    @FXML
    private RadioButton dayOfYearID;
    @FXML
    private RadioButton fileExistenceID;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        cs = new ChangeScreen();
        ruleManager = RuleManager.getRuleManager();
        
        tg = new ToggleGroup();
        hourTriggerID.setToggleGroup(tg);
        dayOfWeekID.setToggleGroup(tg);
        dayOfMonthID.setToggleGroup(tg);
        dayOfYearID.setToggleGroup(tg);
        fileExistenceID.setToggleGroup(tg);
        // Disable the saveButton if no button is selected
        saveButtonID.disableProperty().bind(tg.selectedToggleProperty().isNull());

    }

    //Control if the button is checked
    @FXML
    private void saveButtonAction(ActionEvent event) {
        // Saving choosen triggers
        if (ruleManager.getCurrentRule().getTrigger() != null) {
            // Get the current window
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            String title = "MyIFTTT";
            // Switch to the RuleCreateScreen.fxml
            cs.switchScreen("/softeng_project_group08/view/RuleCreateScreen.fxml", currentStage, title);

        }
    }

    @FXML
    private void backAction(ActionEvent event) {
        //if back is pressed the trigger is not saved
        ruleManager.getCurrentRule().setTrigger(null);

        // Get the current window
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        String title = "MyIFTTT";
        // Switch to the RuleCreateScreen.fxml
        cs.switchScreen("/softeng_project_group08/view/RuleCreateScreen.fxml", currentStage, title);

    }

    @FXML
    private void hourTriggerAction(ActionEvent event) {
            handleTrigger(hourTriggerID,"Time Of Day Trigger","/softeng_project_group08/view/triggers/TimeOfDayTriggerScreen.fxml","Time of day");
    }

    @FXML
    private void dayOfWeekAction(ActionEvent event) {
        handleTrigger(dayOfWeekID,"Day Of Week Trigger","/softeng_project_group08/view/triggers/DayOfWeekTriggerScreen.fxml","Day of week");
    }
    
    @FXML
    private void dayOfMonthAction(ActionEvent event) {
        handleTrigger(dayOfMonthID,"Day Of Month Trigger","/softeng_project_group08/view/triggers/DayOfMonthTriggerScreen.fxml","Day of month");
    }
    
    @FXML
    private void dayOfYearAction(ActionEvent event) {
         handleTrigger(dayOfYearID,"Day Of Year Trigger","/softeng_project_group08/view/triggers/DayOfYearTriggerScreen.fxml","Day of year");
    }
    
    private void handleTrigger(RadioButton rb, String title, String path,String text){
        // private method to handle the buttons triggers.
        ruleManager.getCurrentRule().setTrigger(null); 
        if(rb.isSelected()){
            Stage currentStage = (Stage) rb.getScene().getWindow();
            cs.switchScreenModal(path, currentStage, title);
        
        
        if(ruleManager.getCurrentRule().getTrigger() == null){
            rb.setSelected(false);
        }
    }
        
    }

    @FXML
    private void fileExistenceTriggerAction(ActionEvent event) {
        handleTrigger(fileExistenceID,"File Existence Trigger","/softeng_project_group08/view/triggers/FileExistenceTriggerScreen.fxml","File existence");
    }

    
    
}
