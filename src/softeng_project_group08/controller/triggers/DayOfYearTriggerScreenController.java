package softeng_project_group08.controller.triggers;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;
import softeng_project_group08.controller.RuleManager;
import softeng_project_group08.model.triggers.DayOfYearTrigger;

/**
 * Controller for setting a trigger based on a specific day of the year.
 *
 * @author group08
 */
public class DayOfYearTriggerScreenController implements Initializable {

    @FXML
    private Button saveButtonID;
    @FXML
    private DatePicker pickerDateOfYearID;
    
    private RuleManager ruleManager;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ruleManager= RuleManager.getRuleManager();
        pickerDateOfYearID.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);

                if (date.isBefore(LocalDate.now())) {
                    setDisable(true);
                    setStyle("-fx-background-color: #ffc0cb;"); 
                }
            }
        });
        
        saveButtonID.disableProperty().bind(
                pickerDateOfYearID.valueProperty().isNull()
        );
        
    }    

    @FXML
    private void saveButtonAction(ActionEvent event) {
        ruleManager.getCurrentRule().setTrigger(new DayOfYearTrigger(pickerDateOfYearID.getValue()));
        System.out.println(pickerDateOfYearID.getValue());
        Stage currentStage = (Stage) saveButtonID.getScene().getWindow();
        currentStage.close();
    }

    
}
