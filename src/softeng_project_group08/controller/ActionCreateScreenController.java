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
    @FXML
    private RadioButton moveFileActionID;
    @FXML
    private RadioButton deleteFileActionID;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cs = new ChangeScreen();
        ruleManager = RuleManager.getRuleManager();
        
        tg = new ToggleGroup();
        audioActionID.setToggleGroup(tg);
        dialogActionID.setToggleGroup(tg);
        appendToFileActionID.setToggleGroup(tg);
        copyFileActionID.setToggleGroup(tg);
        moveFileActionID.setToggleGroup(tg);
        deleteFileActionID.setToggleGroup(tg);

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
        handleAction(audioActionID,"Play Audio Action","/softeng_project_group08/view/PlayAudioActionScreen.fxml");
    }

    @FXML
    private void dialogAction(ActionEvent event) {
        handleAction(dialogActionID,"Show Dialog Action","/softeng_project_group08/view/ShowDialogActionScreen.fxml");
    }

    @FXML
    private void appendToFileAction(ActionEvent event) {
        handleAction(appendToFileActionID,"Append to File Action","/softeng_project_group08/view/AppendToFileActionScreen.fxml");
    }

    @FXML
    private void copyFileAction(ActionEvent event) {
        handleAction(copyFileActionID,"Copy File Action","/softeng_project_group08/view/CopyFileActionScreen.fxml");
    }
    @FXML
    private void moveFileAction(ActionEvent event) {
        handleAction(moveFileActionID,"Move File Action","/softeng_project_group08/view/MoveFileActionScreen.fxml");
    }

    @FXML
    private void deleteFileAction(ActionEvent event) {
        handleAction(deleteFileActionID,"Delete File Action","/softeng_project_group08/view/DeleteFileActionScreen.fxml");
    }
    
    private void handleAction(RadioButton rb, String title, String path){
        // private method to handle the buttons action.
        ruleManager.getCurrentRule().setAction(null); 
        if(rb.isSelected()){
            Stage currentStage = (Stage) rb.getScene().getWindow();
            cs.switchScreenModal(path, currentStage, title);
        
        
        if(ruleManager.getCurrentRule().getAction() == null){
            rb.setSelected(false);
        }
    }
        
    }

}