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
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Manages trigger selection and configuration for rule creation. Controls the
 * interface for selecting triggers and updating the rule's trigger settings.
 *
 * @author group08
 */
public class TriggerCreateScreenController implements Initializable {

    @FXML
    private Text selectedTrigger;
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

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        cs = new ChangeScreen();
        ruleManager = RuleManager.getRuleManager();
        
        tg = new ToggleGroup();
        hourTriggerID.setToggleGroup(tg);
        dayOfWeekID.setToggleGroup(tg);

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

        if (hourTriggerID.isSelected()) {

            String newText = selectedTrigger.getText();

            newText = newText + "Time of day";

            selectedTrigger.setText(newText);

            handleTrigger(hourTriggerID,"Time Of Day Trigger","/softeng_project_group08/view/TimeOfDayTriggerScreen.fxml");

            if (ruleManager.getCurrentRule().getTrigger() == null) {
                hourTriggerID.setSelected(false);
                selectedTrigger.setText("");
            }
        } else {
            // delete "Time of the day" from the text 
            selectedTrigger.setText("");
        }
    }

    @FXML
    private void dayOfWeekAction(ActionEvent event) {
            
        if (dayOfWeekID.isSelected()) {

            String newText = selectedTrigger.getText();

            newText = newText + "Day of week";

            selectedTrigger.setText(newText);

            handleTrigger(dayOfWeekID,"Day Of Week Trigger","/softeng_project_group08/view/DayOfWeekTriggerScreen.fxml");

            if (ruleManager.getCurrentRule().getTrigger() == null) {
                dayOfWeekID.setSelected(false);
                selectedTrigger.setText("");
            }
        } else {
            // delete "Time of the day" from the text 
            selectedTrigger.setText("");
        }
    }
    
    private void handleTrigger(RadioButton rb, String title, String path){
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

}
