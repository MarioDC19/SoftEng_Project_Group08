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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cs = new ChangeScreen();
        ruleManager = RuleManager.getRuleManager();

        // Disable the saveButton if the two RadioButtons are disabled
        saveButtonID1.disableProperty().bind(
                Bindings.and(
                        audioActionID.selectedProperty().not(),
                        dialogActionID.selectedProperty().not()
                )
        );
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
        if (audioActionID.isSelected()) {
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            String title = "Play Audio Action";
            cs.switchScreenModal("/softeng_project_group08/view/PlayAudioActionScreen.fxml", currentStage, title);

            if (ruleManager.getCurrentRule().getAction() == null) {
                audioActionID.setSelected(false);
            } else {
                dialogActionID.setDisable(true);
            }
        } else {
            dialogActionID.setDisable(false);
        }
    }

    @FXML
    private void dialogAction(ActionEvent event) {
        //New Screen appears only if the radio button switches from not selected to selected
        if (dialogActionID.isSelected()) {
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            String title = "Show Dialog Action";
            cs.switchScreenModal("/softeng_project_group08/view/ShowDialogActionScreen.fxml", currentStage, title);

            if (ruleManager.getCurrentRule().getAction() == null) {
                dialogActionID.setSelected(false);
            } else {
                audioActionID.setDisable(true);
            }
        } else {
            audioActionID.setDisable(false);
        }
    }
}
