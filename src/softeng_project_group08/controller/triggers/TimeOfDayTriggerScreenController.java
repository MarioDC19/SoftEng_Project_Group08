package softeng_project_group08.controller.triggers;

import java.net.URL;
import java.time.LocalTime;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import softeng_project_group08.controller.RuleManager;
import softeng_project_group08.model.triggers.TimeOfDayTrigger;

/**
 * Controls the setup of time-based triggers for rules. Manages the selection
 * and configuration of time triggers in the application.
 *
 * @author group08
 */
public class TimeOfDayTriggerScreenController implements Initializable {

    @FXML
    private Spinner<Integer> hourSelectorID;
    @FXML
    private Spinner<Integer> minutesSelectorID;
    @FXML
    private Button saveButtonID;

    private RuleManager ruleManager;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ruleManager = RuleManager.getRuleManager();
        // Actual hour
        LocalTime currentTime = LocalTime.now();
        initHourFactory(currentTime);
        initMinuteFactory(currentTime);
        initListeners();
    }

    private void initHourFactory(LocalTime currentTime) {
        //Configure the factory for hours with the current time
        SpinnerValueFactory<Integer> hourFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23, currentTime.getHour()) {
            @Override
            public void decrement(int steps) {
                int newValue = getValue() - steps;
                if (newValue < getMin()) {
                    setValue(getMax());
                } else {
                    setValue(newValue);
                }
            }

            @Override
            public void increment(int steps) {
                int newValue = getValue() + steps;
                if (newValue > getMax()) {
                    setValue(getMin());
                } else {
                    setValue(newValue);
                }
            }
        };
        //Use a StringConverter to format the display of hours
        hourFactory.setConverter(new StringConverter<Integer>() {
            @Override
            public String toString(Integer value) {
                //Format the string by adding a leading zero for values less than 10
                return String.format("%02d", value);
            }

            @Override
            public Integer fromString(String string) {
                // This method is called when a value is manually entered into the text box

                return Integer.parseInt(string);
            }
        });
        hourSelectorID.setValueFactory(hourFactory);
    }

    private void initMinuteFactory(LocalTime currentTime) {
        //Configure the factory for minutes with the current time
        SpinnerValueFactory<Integer> minuteFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, currentTime.getMinute()) {
            @Override
            public void decrement(int steps) {
                int newValue = getValue() - steps;
                if (newValue < getMin()) {
                    setValue(getMax());
                } else {
                    setValue(newValue);
                }
            }

            @Override
            public void increment(int steps) {
                int newValue = getValue() + steps;
                if (newValue > getMax()) {
                    setValue(getMin());
                } else {
                    setValue(newValue);
                }
            }
        };
        //Use a StringConverter to format the display of minutes
        minuteFactory.setConverter(new StringConverter<Integer>() {
            @Override
            public String toString(Integer value) {
                //Format the string by adding a leading zero for values less than 10
                return String.format("%02d", value);
            }

            @Override
            public Integer fromString(String string) {
                // This method is called when a value is manually entered into the text box
                return Integer.parseInt(string);
            }
        });
        minutesSelectorID.setValueFactory(minuteFactory);
    }

    private void initListeners() {
        // Add listeners to format the text for hours and minutes
        hourSelectorID.getEditor().textProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue.length() == 1) {
                hourSelectorID.getEditor().setText("0" + newValue);
            }
        });
        minutesSelectorID.getEditor().textProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue.length() == 1) {
                minutesSelectorID.getEditor().setText("0" + newValue);
            }
        });
    }

    @FXML
    private void saveButtonAction(ActionEvent event) {
        // get hours and minutes
        int selectedHours = hourSelectorID.getValue();
        int selectedMinutes = minutesSelectorID.getValue();
        //Create a TimeOfDayTrigger with the selected hours and minute
        TimeOfDayTrigger timeTrigger = new TimeOfDayTrigger(selectedHours, selectedMinutes);
        ruleManager.getCurrentRule().setTrigger(timeTrigger);
        Stage currentStage = (Stage) saveButtonID.getScene().getWindow();
        currentStage.close();
    }

}
