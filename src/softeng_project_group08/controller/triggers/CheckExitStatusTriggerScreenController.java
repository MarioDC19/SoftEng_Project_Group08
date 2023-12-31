package softeng_project_group08.controller.triggers;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import softeng_project_group08.controller.RuleManager;
import softeng_project_group08.model.triggers.CheckExitStatusTrigger;

/**
 * Controller class for managing the Check Exit Status Trigger screen. Handles
 * user interactions to set the program path and expected exit status for a rule
 * trigger.
 *
 * @author group08
 */
public class CheckExitStatusTriggerScreenController implements Initializable {

    @FXML
    private Button saveButtonID;
    @FXML
    private TextField programPathID;
    @FXML
    private Button insertProgramID;

    private RuleManager ruleManager;
    @FXML
    private TextField exitStatusID;
    @FXML
    private TextField passParametersID;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ruleManager = RuleManager.getRuleManager();
        // the trigger cannot be saved if one or both textfields are empty
        saveButtonID.disableProperty().bind(Bindings.or(
                exitStatusID.textProperty().isEmpty(),
                programPathID.textProperty().isEmpty())
        );
    }

    @FXML
    private void saveButtonAction(ActionEvent event) {
        // Get the file selected by the FileChooser
        File programPath = new File(programPathID.getText());
        String parametersText = passParametersID.getText();
        int exitStatus = Integer.parseInt(exitStatusID.getText());
        //Set the trigger as a CheckExitStatusTrigger with the selected program
        CheckExitStatusTrigger checkExitStatus = new CheckExitStatusTrigger(programPath, exitStatus, parametersText);
        ruleManager.getCurrentRule().setTrigger(checkExitStatus);
        Stage currentStage = (Stage) saveButtonID.getScene().getWindow();
        currentStage.close();
    }

    @FXML
    private void insertProgramAction(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select a program to check");
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(stage);
        // set accepted extensions for the filechooser
        FileChooser.ExtensionFilter extFilterAll = new FileChooser.ExtensionFilter("Executable Files", "*.exe", "*.bat", "*.cmd", "*.sh");
        fileChooser.getExtensionFilters().add(extFilterAll);
        // Handle the selected file and display it in the TextField
        if (selectedFile != null) {
            String programPath = selectedFile.getPath();
            programPathID.setText(programPath);
        }
    }

}
