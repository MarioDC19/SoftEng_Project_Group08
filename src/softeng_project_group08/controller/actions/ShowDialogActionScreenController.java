package softeng_project_group08.controller.actions;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import softeng_project_group08.controller.RuleManager;
import softeng_project_group08.model.actions.ShowDialogAction;

/**
 * Manages dialog action creation for rules. Handles input of dialog text and
 * related functionalities.
 *
 * @author group08
 */
public class ShowDialogActionScreenController implements Initializable {

    @FXML
    private Button saveButtonID;
    @FXML
    private TextArea textDialogID;

    private RuleManager ruleManager;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ruleManager = RuleManager.getRuleManager();
        // the action cannot be saved if the textfield is empty
        saveButtonID.disableProperty().bind(
                textDialogID.textProperty().isEmpty()
        );

    }

    @FXML
    private void saveButtonAction(ActionEvent event) {
        String dialogText = textDialogID.getText();
        //Set the action as a ShowDialogAction with the entered text
        ShowDialogAction showDialogAction = new ShowDialogAction(dialogText);
        ruleManager.getCurrentRule().setAction(showDialogAction);
        Stage currentStage = (Stage) saveButtonID.getScene().getWindow();
        currentStage.close();
    }

}
