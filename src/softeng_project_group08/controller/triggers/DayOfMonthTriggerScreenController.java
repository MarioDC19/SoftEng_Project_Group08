package softeng_project_group08.controller.triggers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.stage.Stage;
import softeng_project_group08.controller.RuleManager;
import softeng_project_group08.model.triggers.DayOfMonthTrigger;

/**
 * Controller for setting a trigger based on a specific day of the month.
 *
 * @author group08
 */
public class DayOfMonthTriggerScreenController implements Initializable {

    @FXML
    private Button saveButtonID;
    @FXML
    private Spinner<Integer> daySpinnerID;

    private RuleManager ruleManager;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ruleManager = RuleManager.getRuleManager();
        SpinnerValueFactory<Integer> valueFactoryDays = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 31, 1);
        daySpinnerID.setValueFactory(valueFactoryDays);
    }

    @FXML
    private void saveButtonAction(ActionEvent event) {
        //Set the trigger as a DayOfMonthTrigger with the selected day
        DayOfMonthTrigger domt = new DayOfMonthTrigger(daySpinnerID.getValue());
        ruleManager.getCurrentRule().setTrigger(domt);
        Stage currentStage = (Stage) saveButtonID.getScene().getWindow();
        currentStage.close();
    }

}
