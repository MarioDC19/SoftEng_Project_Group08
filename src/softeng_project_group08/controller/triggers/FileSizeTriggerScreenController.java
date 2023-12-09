package softeng_project_group08.controller.triggers;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import softeng_project_group08.controller.RuleManager;
import softeng_project_group08.model.triggers.FileSizeTrigger;

/**
 * Controls the configuration of triggers based on the maximum size a file can
 * have. Manages the selection and configuration of file-based triggers in the
 * application.
 *
 * @author group08
 */
public class FileSizeTriggerScreenController implements Initializable {

    @FXML
    private Button saveButtonID;
    @FXML
    private TextField fileSizeID;
    @FXML
    private Button fileChooserID;

    private RuleManager ruleManager;
    @FXML
    private TextField fileNameID;

    private File selectedFile;
    @FXML
    private Spinner<String> measurementUnitID;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ruleManager = RuleManager.getRuleManager();
        selectedFile = null;
        // the trigger cannot be saved if one or both textfields are empty
        saveButtonID.disableProperty().bind(Bindings.or(
                fileSizeID.textProperty().isEmpty(),
                fileNameID.textProperty().isEmpty())
        );
        // if the text does not follow the format "number.number" return to old value
        fileSizeID.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*(\\.\\d*)?")) {
                fileSizeID.setText(oldValue);
            }
        });
        // initialize unit spinner
        SpinnerValueFactory<String> unitFactory = new SpinnerValueFactory.ListSpinnerValueFactory<>(FXCollections.observableArrayList("byte", "kilobyte", "megabyte", "gigabyte"));
        measurementUnitID.setValueFactory(unitFactory);
    }

    @FXML
    private void saveButtonAction(ActionEvent event) {
        double fileSize = Double.parseDouble(fileSizeID.getText());
        String selectedUnit = measurementUnitID.getValue();
        // convert to kbyte
        if ("byte".equals(selectedUnit)) {
            fileSize /= 1024;
        } else if ("megabyte".equals(selectedUnit)) {
            fileSize *= 1024;
        } else if ("gigabyte".equals(selectedUnit)) {
            fileSize *= 1024 * 1024;
        }
        //Set the trigger as a FileSizeTrigger with the selected filesize
        FileSizeTrigger fileSizeTrigger = new FileSizeTrigger(selectedFile, fileSize);
        ruleManager.getCurrentRule().setTrigger(fileSizeTrigger);
        Stage currentStage = (Stage) saveButtonID.getScene().getWindow();
        currentStage.close();
    }

    @FXML
    private void fileChooserAction(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose a File");
        selectedFile = fileChooser.showOpenDialog(new Stage());
        // Update the TextField with the selected path
        if (selectedFile != null) {
            fileNameID.setText(selectedFile.getAbsolutePath());
        }
    }
}
