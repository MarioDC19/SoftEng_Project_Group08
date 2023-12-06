package softeng_project_group08.controller.actions;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import softeng_project_group08.controller.RuleManager;
import softeng_project_group08.model.actions.DeleteFileAction;

/**
 * Manages delete action creation for rules. Controls the selection of a file
 * and delete the selected one
 *
 * @author salvatore
 */
public class DeleteFileActionScreenController implements Initializable {

    @FXML
    private Button saveButtonID;
    @FXML
    private TextField filePathID;
    @FXML
    private Button insertFileID;

    private RuleManager ruleManager;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ruleManager = RuleManager.getRuleManager();
    }

    @FXML
    private void saveButtonAction(ActionEvent event) {

        // Get the file selected by the FileChooser
        File selectedFile = new File(filePathID.getText());

        //Set the action as a DeleteFileAction with the selected file
        DeleteFileAction deleteFileAction = new DeleteFileAction(selectedFile);

        ruleManager.getCurrentRule().setAction(deleteFileAction);

        Stage currentStage = (Stage) saveButtonID.getScene().getWindow();
        currentStage.close();

    }

    @FXML
    private void insertFileAction(ActionEvent event) {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select a file to Delete");

        //Show file chooser
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(stage);

        // Handle the selected file and display it in the TextField
        if (selectedFile != null) {
            String filePath = selectedFile.getPath();
            filePathID.setText(filePath);
        }
    }

}
