package softeng_project_group08.controller.actions;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
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
import softeng_project_group08.model.actions.AppendToFileAction;

/**
 * Manages appending string action creation for rules. Controls the adding of a
 * string at the end of a file.
 *
 * @author group08
 */
public class AppendToFileActionScreenController implements Initializable {

    @FXML
    private Button saveButtonID;
    @FXML
    private TextArea appendStringID;
    @FXML
    private TextField filePathID;
    @FXML
    private Button insertFileID;

    File file;

    private RuleManager rm;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        rm = RuleManager.getRuleManager();
        file = null;
        // the action cannot be saved if one or both textfields are empty
        saveButtonID.disableProperty().bind(
                Bindings.or(
                        filePathID.textProperty().isEmpty(),
                        appendStringID.textProperty().isEmpty()
                ));
    }

    @FXML
    private void saveButtonAction(ActionEvent event) {
        //Set the action as a AppendToFileAction with the selected file and string
        AppendToFileAction atfa = new AppendToFileAction(appendStringID.getText(), file.getPath());
        rm.getCurrentRule().setAction(atfa);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    private void insertFileAction(ActionEvent event) {
        FileChooser fc = new FileChooser();
        fc.setTitle("Select File");
        // set accepted extensions for the filechooser
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Text File", "*.txt", "*.csv", "*.xml", "*.json", "*.html", "*.md", "*.log", "*.conf", "*.properties", "*.ini");
        fc.getExtensionFilters().add(extFilter);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        file = fc.showOpenDialog(stage);
        // handle the selected file and display it in the TextField
        if (file != null) {
            filePathID.setText(file.getPath());
        }
    }

}
