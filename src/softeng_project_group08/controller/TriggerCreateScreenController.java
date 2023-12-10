package softeng_project_group08.controller;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import softeng_project_group08.model.Trigger;
import softeng_project_group08.model.triggers.AndTriggerDecorator;
import softeng_project_group08.model.triggers.NotTriggerDecorator;
import softeng_project_group08.model.triggers.OrTriggerDecorator;

/**
 * Manages trigger selection and configuration for rule creation. Controls the
 * interface for selecting triggers and updating the rule's trigger settings.
 *
 * @author group08
 */
public class TriggerCreateScreenController implements Initializable {

    @FXML
    private Button backID;
    @FXML
    private Button saveButtonID;
    @FXML
    private RadioButton hourTriggerID;

    private RuleManager ruleManager;

    ChangeScreen cs = new ChangeScreen();

    ToggleGroup tg;
    ToggleGroup tg2;

    @FXML
    private RadioButton dayOfWeekID;
    @FXML
    private RadioButton dayOfMonthID;
    @FXML
    private RadioButton dayOfYearID;
    @FXML
    private RadioButton fileExistenceID;
    @FXML
    private RadioButton fileSizeID;
    @FXML
    private RadioButton checkExitStatusID;
    @FXML
    private RadioButton notID;
    @FXML
    private VBox vBoxID;
    @FXML
    private ListView<Trigger> listViewID;
    @FXML
    private CheckBox multiTriggerID;
    @FXML
    private TextArea fieldID;
    @FXML
    private RadioButton andID;
    @FXML
    private RadioButton orID;

    @Override
   public void initialize(URL url, ResourceBundle rb) {
        ruleManager = RuleManager.getRuleManager();
        initBindings();
        initContextMenu();
    }
    
    private void initBindings() {
        tg = new ToggleGroup();
        hourTriggerID.setToggleGroup(tg);
        dayOfWeekID.setToggleGroup(tg);
        dayOfMonthID.setToggleGroup(tg);
        dayOfYearID.setToggleGroup(tg);
        fileExistenceID.setToggleGroup(tg);
        fileSizeID.setToggleGroup(tg);
        checkExitStatusID.setToggleGroup(tg);
        // Disable the saveButton if no button is selected and list of multi trigger is not equal to 1
        saveButtonID.disableProperty().bind(tg.selectedToggleProperty().isNull().and(Bindings.size(listViewID.getItems()).isNotEqualTo(1)));
        vBoxID.visibleProperty().bind(multiTriggerID.selectedProperty());
        //group for or and not operator
        tg2 = new ToggleGroup();
        notID.setToggleGroup(tg2);
        andID.setToggleGroup(tg2);
        orID.setToggleGroup(tg2);
        listViewID.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        notID.setOnAction(event -> handleNotButtonClick(listViewID));
        andID.setOnAction(event -> handleAndButtonClick(listViewID));
        orID.setOnAction(event -> handleOrButtonClick(listViewID));
    }
    
    private void initContextMenu(){
        /*The context menu is associated with a ListView to enable the deletion of a row when the user right-clicks on the mouse.
        This functionality allows the user to trigger a context menu, providing options
        such as deleting a specific row in the associated ListView.*/
        ContextMenu contextMenu = new ContextMenu();
        MenuItem deleteMenuItem = new MenuItem("Delete");
        deleteMenuItem.setOnAction(event -> deleteSelectedRows(listViewID));
        contextMenu.getItems().add(deleteMenuItem);
        listViewID.setContextMenu(contextMenu);
    }

    //Control if the button is checked
    @FXML
    private void saveButtonAction(ActionEvent event) {
        // Saving choosen triggers
        if (ruleManager.getCurrentRule().getTrigger() != null) {
            // Get the current window
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            String title = "MyIFTTT";
            // Switch to the RuleCreateScreen.fxml
            cs.switchScreen("/softeng_project_group08/view/RuleCreateScreen.fxml", currentStage, title);

        }
    }

    @FXML
    private void backAction(ActionEvent event) {
        //if back is pressed the trigger is not saved
        ruleManager.getCurrentRule().setTrigger(null);

        // Get the current window
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        String title = "MyIFTTT";
        // Switch to the RuleCreateScreen.fxml
        cs.switchScreen("/softeng_project_group08/view/RuleCreateScreen.fxml", currentStage, title);

    }

    @FXML
    private void hourTriggerAction(ActionEvent event) {
        handleTrigger(hourTriggerID, "Time Of Day Trigger", "/softeng_project_group08/view/triggers/TimeOfDayTriggerScreen.fxml", "Time of day");
    }

    @FXML
    private void dayOfWeekAction(ActionEvent event) {
        handleTrigger(dayOfWeekID, "Day Of Week Trigger", "/softeng_project_group08/view/triggers/DayOfWeekTriggerScreen.fxml", "Day of week");
    }

    @FXML
    private void dayOfMonthAction(ActionEvent event) {
        handleTrigger(dayOfMonthID, "Day Of Month Trigger", "/softeng_project_group08/view/triggers/DayOfMonthTriggerScreen.fxml", "Day of month");
    }

    @FXML
    private void dayOfYearAction(ActionEvent event) {
        handleTrigger(dayOfYearID, "Day Of Year Trigger", "/softeng_project_group08/view/triggers/DayOfYearTriggerScreen.fxml", "Day of year");
    }

    @FXML
    private void fileExistenceAction(ActionEvent event) {
        handleTrigger(fileExistenceID, "File Existence Trigger", "/softeng_project_group08/view/triggers/FileExistenceTriggerScreen.fxml", "File existence");
    }

    @FXML
    private void fileSizeAction(ActionEvent event) {
        handleTrigger(fileSizeID, "File Size Trigger", "/softeng_project_group08/view/triggers/FileSizeTriggerScreen.fxml", "File size");
    }

    @FXML
    private void checkExitStatusAction(ActionEvent event) {
        handleTrigger(checkExitStatusID, "Check Exit Status Trigger", "/softeng_project_group08/view/triggers/CheckExitStatusTriggerScreen.fxml", "Exit Status");
    }

    private void handleTrigger(RadioButton rb, String title, String path, String text) {
        // private method to handle the buttons triggers.
        ruleManager.getCurrentRule().setTrigger(null);
        if (rb.isSelected()) {
            Stage currentStage = (Stage) rb.getScene().getWindow();
            cs.switchScreenModal(path, currentStage, title);
            if (ruleManager.getCurrentRule().getTrigger() == null) {
                rb.setSelected(false);
            } else if (multiTriggerID.isSelected()) {
                listViewID.getItems().add(ruleManager.getCurrentRule().getTrigger());
                rb.setSelected(false);
            }
        }

    }

    private void handleNotButtonClick(ListView<Trigger> listView) {
        Trigger selected = listView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            Trigger notT = new NotTriggerDecorator(selected);
            ruleManager.getCurrentRule().setTrigger(notT);
            fieldID.setText(ruleManager.getCurrentRule().getTrigger().toString());
            notID.setSelected(false);
            listView.getItems().removeAll(selected);
            listView.getItems().add(ruleManager.getCurrentRule().getTrigger());
        } else {
            DialogUtil.showDialog("You need to create a trigger and select a row in the list to apply the NOT operator.", Alert.AlertType.ERROR, "Error");
            notID.setSelected(false);
        }
    }
    
    private void handleAndButtonClick(ListView<Trigger> listView) {
        ObservableList<Trigger> selectedItems = listView.getSelectionModel().getSelectedItems();
        if (selectedItems != null && selectedItems.size() >= 2) {
            Trigger combinedTrigger = selectedItems.get(0);
            for (int i = 1; i < selectedItems.size(); i++) {
                combinedTrigger = new AndTriggerDecorator(combinedTrigger, selectedItems.get(i));
            }
            ruleManager.getCurrentRule().setTrigger(combinedTrigger);
            fieldID.setText(ruleManager.getCurrentRule().getTrigger().toString());
            listView.getItems().removeAll(selectedItems);
            listView.getItems().add(ruleManager.getCurrentRule().getTrigger());
            andID.setSelected(false);
        } else {
            DialogUtil.showDialog("You need to create at least two triggers and select at least two rows in the list to apply the AND operator. ", Alert.AlertType.ERROR, "Error");
            andID.setSelected(false);
        }
    }
    private void handleOrButtonClick(ListView<Trigger> listView) {
        ObservableList<Trigger> selectedItems = listView.getSelectionModel().getSelectedItems();
        if (selectedItems != null && selectedItems.size() >= 2) {
            Trigger combinedTrigger = selectedItems.get(0);
            for (int i = 1; i < selectedItems.size(); i++) {
                combinedTrigger = new OrTriggerDecorator(combinedTrigger, selectedItems.get(i));
            }
            ruleManager.getCurrentRule().setTrigger(combinedTrigger);
            fieldID.setText(ruleManager.getCurrentRule().getTrigger().toString());
            listView.getItems().removeAll(selectedItems);
            listView.getItems().add(ruleManager.getCurrentRule().getTrigger());
            orID.setSelected(false);
        } else {
            DialogUtil.showDialog("You need to create at least two triggers and select at least two rows in the list to apply the OR operator.", Alert.AlertType.ERROR, "Error");
            orID.setSelected(false);
        }
    }
    
    @FXML
    private void multiTriggerAction(ActionEvent event) {
        if (multiTriggerID.isSelected()) {

            if (tg.getSelectedToggle() != null) {
                /*If a trigger has already been created, and the user clicks on the checkbox,
                  insert this action into the ListView.*/
                listViewID.getItems().add(ruleManager.getCurrentRule().getTrigger());
                tg.getSelectedToggle().setSelected(false);
            }
        } else {
           if(!listViewID.getItems().isEmpty()){
                Boolean confirm=DialogUtil.showDialog("Are you sure to delete the multi trigger expression?",Alert.AlertType.CONFIRMATION,"Confirmation");
                if (confirm){
                     listViewID.getItems().removeAll(listViewID.getItems());
                    fieldID.setText("");
                } else{
                    multiTriggerID.setSelected(true);
                }
           }
        }
    }

    

    private void deleteSelectedRows(ListView<Trigger> listView) {
        //delete the selected rows in the listView.
        ObservableList<Trigger> selectedItems = listView.getSelectionModel().getSelectedItems();
        listView.getItems().removeAll(selectedItems);
        fieldID.setText("");

    }

}
