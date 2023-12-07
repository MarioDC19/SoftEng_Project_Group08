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
import softeng_project_group08.model.actions.PlayAudioAction;

/**
 * Manages audio action creation for rules. Controls the selection of audio
 * files and handling of related functionalities.
 *
 * @author group08
 */
public class PlayAudioActionScreenController implements Initializable {

    @FXML
    private Button saveButtonID;
    @FXML
    private TextField audioPathID;
    @FXML
    private Button insertAudioID;

    private RuleManager ruleManager;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ruleManager = RuleManager.getRuleManager();
        
        saveButtonID.disableProperty().bind(
                audioPathID.textProperty().isEmpty()
        );
    }

    @FXML
    private void saveButtonAction(ActionEvent event) {

        // Get the file selected by the FileChooser
        File selectedFile = new File(audioPathID.getText());

        //Set the action as a PlayAudioAction with the selected file
        PlayAudioAction playAudioAction = new PlayAudioAction(selectedFile);

        ruleManager.getCurrentRule().setAction(playAudioAction);

        Stage currentStage = (Stage) saveButtonID.getScene().getWindow();
        currentStage.close();

    }

    @FXML
    private void insertAudioAction(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleziona un file audio");

        // Set the filter for audio files, in MP3 format
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("File audio (*.mp3)", "*.mp3");
        fileChooser.getExtensionFilters().add(extFilter);

        //Show file chooser
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(stage);

        // Handle the selected file and display it in the TextField
        if (selectedFile != null) {
            String filePath = selectedFile.getPath();
            audioPathID.setText(filePath);
        }
    }

}
