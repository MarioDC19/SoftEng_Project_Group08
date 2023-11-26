package softeng_project_group08.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import softeng_project_group08.model.RuleManager;

/**
 * Controls the rule creation screen functionalities. Manages rule addition,
 * triggers, actions, and navigation between screens.
 *
 * @author group08
 */
public class RuleCreateScreenController implements Initializable {

    @FXML
    private TextField nameRuleID;
    @FXML
    private Button addTriggerID;
    @FXML
    private Button addActionID;
    @FXML
    private Button backID;
    @FXML
    private Button saveRuleID1;

    private RuleManager ruleManager;

    ChangeScreen cs = new ChangeScreen();

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        ruleManager = RuleManager.getRuleManager();
        nameRuleID.setText(ruleManager.getCurrentRule().getName());
        saveRuleID1.disableProperty().bind(Bindings.isEmpty(nameRuleID.textProperty()));
        addTriggerID.disableProperty().bind(Bindings.isEmpty(nameRuleID.textProperty()));
        addActionID.disableProperty().bind(Bindings.isEmpty(nameRuleID.textProperty()));

    }

    @FXML
    private void saveRuleAction(ActionEvent event) {

        // Retrieve the text entered in the textField, removing any leading or trailing white spaces
        String enteredName = nameRuleID.getText().trim();
        ruleManager.getCurrentRule().setName(enteredName);

        if (ruleManager.getCurrentRule().getTrigger() == null) {
            showDialog(" You can't save the rule without a Trigger ", Alert.AlertType.ERROR, "Error");
        } else if (ruleManager.getCurrentRule().getAction() == null) {
            showDialog(" You can't save the rule without an Action ", Alert.AlertType.ERROR, "Error");
        } else {
            ruleManager.addRule();
            nameRuleID.clear();
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            String title = "MyIFTTT";
            cs.switchScreen("/softeng_project_group08/view/MainScreen.fxml", currentStage, title);

        }

    }

    @FXML
    private void addTriggerAction(ActionEvent event) {
        String enteredName = nameRuleID.getText().trim();
        if (!enteredName.equals(ruleManager.getCurrentRule().getName())) {
            ruleManager.getCurrentRule().setName(enteredName); //prova
        }

        // Assuming 'screenChanger' is the instance of the ChangeScreen class created earlier
        // Get the current window
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        String title = "Create Trigger";
        // Switch to the TriggerCreateScreen.fxml
        Initializable newController = cs.switchScreen("/softeng_project_group08/view/TriggerCreateScreen.fxml", currentStage, title);
    }

    @FXML
    private void addAction(ActionEvent event) {
        String enteredName = nameRuleID.getText().trim();
        if (!(enteredName.equals(ruleManager.getCurrentRule().getName()))) {
            ruleManager.getCurrentRule().setName(enteredName); // prova
        }

        // Assuming 'screenChanger' is the instance of the ChangeScreen class created earlier
        // Get the current window
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        String title = "Create Action";
        // Switch to the ActionCreateScreen.fxml
        Initializable newController = cs.switchScreen("/softeng_project_group08/view/ActionCreateScreen.fxml", currentStage, title);
    }

    @FXML
    private void backAction(ActionEvent event) {
        // Get the current window
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        String title = "MyIFTTT";
        // Switch to the MainScreen without saving
        Initializable newController = cs.switchScreen("/softeng_project_group08/view/MainScreen.fxml", currentStage, title);
    }

    //shows a dialog window in order to give an alert
    private void showDialog(String message, Alert.AlertType at, String title) {
        Alert alert = new Alert(at);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);

        alert.showAndWait();
    }

}
