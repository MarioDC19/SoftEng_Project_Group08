package softeng_project_group08.controller.actions;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import softeng_project_group08.controller.RuleManager;
import softeng_project_group08.model.actions.ExecuteExternalProgramAction;

/**
 * Controller for executing an external program with provided parameters.
 *
 * @author group08
 */
public class ExecuteExternalProgramActionScreenController implements Initializable {

    @FXML
    private Button saveButtonID;
    @FXML
    private TextArea passParametersID;
    @FXML
    private TextField programPathID;
    @FXML
    private Button insertProgramID;

    RuleManager rm;

    File program;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        rm = RuleManager.getRuleManager();
        program = null;
        // the action cannot be saved if the textfield is empty
        saveButtonID.disableProperty().bind(
                programPathID.textProperty().isEmpty()
        );
    }

    @FXML
    private void saveButtonAction(ActionEvent event) {
        File programFile = new File(programPathID.getText());
        String parametersText = passParametersID.getText();
        //Set the action as a ExecuteExternalProgramAction with the selected program
        ExecuteExternalProgramAction eepm = new ExecuteExternalProgramAction(programFile, parametersText);
        rm.getCurrentRule().setAction(eepm);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    private void insertProgramAction(ActionEvent event) {
        FileChooser fc = new FileChooser();
        fc.setTitle("Select File");
        // set accepted extensions for the filechooser
        FileChooser.ExtensionFilter extFilterAll = new FileChooser.ExtensionFilter("Executable Files", "*.exe", "*.bat", "*.cmd", "*.sh");
        fc.getExtensionFilters().add(extFilterAll);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        program = fc.showOpenDialog(stage);
        // handle the selected file and display it in the TextField
        if (program != null) {
            programPathID.setText(program.getPath());
        }
    }

}
