package softeng_project_group08.controller.triggers;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import softeng_project_group08.controller.RuleManager;
import softeng_project_group08.model.triggers.FileExistenceTrigger;

/**
 * Controls the configuration of triggers based on the existence of a file in a specific directory. 
 * Manages the selection and configuration of file-based triggers in the application.
 *
 * @author group08
 */
public class FileExistenceTriggerScreenController implements Initializable {

    @FXML
    private Button saveButtonID;
    @FXML
    private TextField fileNameID;
    @FXML
    private Button directoryChooserID;
    @FXML
    private TextField directoryNameID;
    
    File selectedDirectory;
    
    private RuleManager ruleManager;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        selectedDirectory = null;
        ruleManager = RuleManager.getRuleManager();
        
        saveButtonID.disableProperty().bind(Bindings.or(fileNameID.textProperty().isEmpty(), directoryNameID.textProperty().isEmpty()));
    }    

    @FXML
    private void saveButtonAction(ActionEvent event) {
        
        String file = fileNameID.getText();
        
        FileExistenceTrigger fileExistenceTrigger = new FileExistenceTrigger(file, selectedDirectory);
        ruleManager.getCurrentRule().setTrigger(fileExistenceTrigger);
        
        Stage currentStage = (Stage) saveButtonID.getScene().getWindow();
        currentStage.close();
    }

    @FXML
    private void directoryChooserAction(ActionEvent event) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Select the directory");
        selectedDirectory = directoryChooser.showDialog(directoryChooserID.getScene().getWindow());

        // Update the TextField with the selected path
        if (selectedDirectory != null) {
            directoryNameID.setText(selectedDirectory.getAbsolutePath());
        }
    }
    
}
