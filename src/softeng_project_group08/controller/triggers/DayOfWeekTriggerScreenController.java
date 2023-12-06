package softeng_project_group08.controller.triggers;

import java.net.URL;
import java.time.DayOfWeek;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import softeng_project_group08.controller.RuleManager;
import softeng_project_group08.model.triggers.DayOfWeekTrigger;

/**
 * Controller for setting a trigger based on a specific day of the week.
 *
 * @author group08
 */
public class DayOfWeekTriggerScreenController implements Initializable {

    @FXML
    private Button saveButtonID;
    @FXML
    private ChoiceBox<DayOfWeek> dayOfWeekID; 

    private DayOfWeek selectedDay;
    private RuleManager ruleManager;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        dayOfWeekID.getItems().addAll(DayOfWeek.values());
        
        ruleManager = RuleManager.getRuleManager();

        // Set a converter to display the names of the days instead of the enum values
        dayOfWeekID.setConverter(new StringConverter<DayOfWeek>() {
            @Override
            public String toString(DayOfWeek dayOfWeek) {
                return dayOfWeek.toString();
            }

            @Override
            public DayOfWeek fromString(String string) {
                return DayOfWeek.valueOf(string);
            }
        });
        
        saveButtonID.setDisable(true);

        dayOfWeekID.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            saveButtonID.setDisable(newValue == null);
        });
        
        
    }

    @FXML
    private void saveButtonAction(ActionEvent event) {
        selectedDay = dayOfWeekID.getValue();
        DayOfWeekTrigger dayTrigger = new DayOfWeekTrigger(selectedDay);
        ruleManager.getCurrentRule().setTrigger(dayTrigger);

        Stage currentStage = (Stage) saveButtonID.getScene().getWindow();
        currentStage.close();
    }
}

