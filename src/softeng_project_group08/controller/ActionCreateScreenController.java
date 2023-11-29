package softeng_project_group08.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import softeng_project_group08.model.RuleManager;

/**
 * Manages rule actions creation, including audio and dialog actions.
 *
 * @author group08
 */
public class ActionCreateScreenController implements Initializable {

    @FXML
    private Button backID;
    @FXML
    private Button saveButtonID1;
    @FXML
    private RadioButton audioActionID;
    @FXML
    private RadioButton dialogActionID;

    private RuleManager ruleManager;

    private ChangeScreen cs;
    @FXML
    private RadioButton appendToFileActionID;
    @FXML
    private RadioButton copyFileActionID;
    
    ToggleGroup tg;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cs = new ChangeScreen();
        ruleManager = RuleManager.getRuleManager();
        
        tg = new ToggleGroup();
        audioActionID.setToggleGroup(tg);
        dialogActionID.setToggleGroup(tg);
        appendToFileActionID.setToggleGroup(tg);
        copyFileActionID.setToggleGroup(tg);

        // Disable the saveButton if no button is selected
        saveButtonID1.disableProperty().bind(tg.selectedToggleProperty().isNull());
        
    }

    @FXML
    private void saveButtonAction(ActionEvent event) {
        //back to the previous screen
        if (ruleManager.getCurrentRule().getAction() != null) {

            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            String title = "MyIFTTT";
            //display the screen in non-modal mode
            cs.switchScreen("/softeng_project_group08/view/RuleCreateScreen.fxml", currentStage, title);
        }
    }

    @FXML
    private void backAction(ActionEvent event) {
        //if back is pressed the action is not saved
        ruleManager.getCurrentRule().setAction(null);

        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        String title = "MyIFTTT";
        cs.switchScreen("/softeng_project_group08/view/RuleCreateScreen.fxml", currentStage, title);
    }

    @FXML
    private void audioAction(ActionEvent event) {
        ruleManager.getCurrentRule().setAction(null); 
       if (audioActionID.isSelected()) {
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            String title = "Play Audio Action";
            cs.switchScreenModal("/softeng_project_group08/view/PlayAudioActionScreen.fxml", currentStage, title);

            if (ruleManager.getCurrentRule().getAction() == null) {
                audioActionID.setSelected(false);
            }
            
    }
    }

    @FXML
    private void dialogAction(ActionEvent event) {
        ruleManager.getCurrentRule().setAction(null); 
        //New Screen appears only if the radio button switches from not selected to selected
        if (dialogActionID.isSelected()) {
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            String title = "Show Dialog Action";
            cs.switchScreenModal("/softeng_project_group08/view/ShowDialogActionScreen.fxml", currentStage, title);

            if (ruleManager.getCurrentRule().getAction() == null) {
                dialogActionID.setSelected(false);
            } 
        }
    }

    @FXML
    private void appendToFileAction(ActionEvent event) {
        ruleManager.getCurrentRule().setAction(null); 
        if(appendToFileActionID.isSelected()){
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            String title = "Append to File Action";
            cs.switchScreenModal("/softeng_project_group08/view/AppendToFileActionScreen.fxml", currentStage, title);
        
        
        if(ruleManager.getCurrentRule().getAction() == null){
            appendToFileActionID.setSelected(false);
        } 
                }
    }

    @FXML
    private void copyFileAction(ActionEvent event) {
        ruleManager.getCurrentRule().setAction(null); 
        if(copyFileActionID.isSelected()){
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            String title = "Copy File Action";
            cs.switchScreenModal("/softeng_project_group08/view/CopyFileActionScreen.fxml", currentStage, title);
        
        
        if(ruleManager.getCurrentRule().getAction() == null){
            copyFileActionID.setSelected(false);
        }
    }
    }
}