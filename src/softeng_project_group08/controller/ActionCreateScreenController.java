package softeng_project_group08.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import softeng_project_group08.model.Action;
import softeng_project_group08.model.actions.MultiActions;

/**
 * Manages rule actions creation, including audio and dialog actions.
 *
 * @author group08
 */
public class ActionCreateScreenController implements Initializable {

    @FXML
    private Button backID;
    @FXML
    private Button saveButtonID1;
    @FXML
    private RadioButton audioActionID;
    @FXML
    private RadioButton dialogActionID;

    private RuleManager ruleManager;

    private ChangeScreen cs;
    @FXML
    private RadioButton appendToFileActionID;
    @FXML
    private RadioButton copyFileActionID;

    ToggleGroup tg;
    @FXML
    private RadioButton moveFileActionID;
    @FXML
    private RadioButton deleteFileActionID;
    @FXML
    private RadioButton executeExternalActionID;
    @FXML
    private CheckBox multipleActionID;
    @FXML
    private ListView<Action> listViewID;

    private MultiActions multiActions;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cs = new ChangeScreen();
        ruleManager = RuleManager.getRuleManager();
        tg = new ToggleGroup();
        audioActionID.setToggleGroup(tg);
        dialogActionID.setToggleGroup(tg);
        appendToFileActionID.setToggleGroup(tg);
        copyFileActionID.setToggleGroup(tg);
        moveFileActionID.setToggleGroup(tg);
        deleteFileActionID.setToggleGroup(tg);
        executeExternalActionID.setToggleGroup(tg);
        /*This check is essential to maintain the selected items in the list and checkbox selected status
        when the user switches between windows to insert parameters for a specific action.
         */
        if (multiActions != null) {
            multipleActionID.setSelected(true);
        }
        // Disable the saveButton if no button is selected
        saveButtonID1.disableProperty().bind(tg.selectedToggleProperty().isNull());
        //list of multi actions is visible when the checkbox multipleActionID is selected
        listViewID.visibleProperty().bind(multipleActionID.selectedProperty());
        /*The context menu is associated with a ListView to enable the deletion of a row when the user right-clicks on the mouse.
        This functionality allows the user to trigger a context menu, providing options
        such as deleting a specific row in the associated ListView.*/
        ContextMenu contextMenu = new ContextMenu();
        MenuItem deleteMenuItem = new MenuItem("Delete");
        deleteMenuItem.setOnAction(event -> deleteSelectedRows(listViewID));
        contextMenu.getItems().add(deleteMenuItem);
        listViewID.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        listViewID.setContextMenu(contextMenu);
    }

    @FXML
    private void saveButtonAction(ActionEvent event) {
        /* This check verifies whether the user has selected the option for multiple actions
         and has inserted rows into the list. */
        if (multipleActionID.isSelected() && (!listViewID.getItems().isEmpty())) {
            /* If the conditions are met, iterate through each Action in the ListView
               and add them as child actions to the MultiActions instance. */
            for (Action a : listViewID.getItems()) {
                multiActions.addChild(a);
            }
            // Set the MultiActions instance as the action for the current rule in the rule manager.
            ruleManager.getCurrentRule().setAction(multiActions);
        }
        if (ruleManager.getCurrentRule().getAction() != null) {
            // return to ruleCreateScreen when the action has been bound
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            String title = "MyIFTTT";
            //display the screen in non-modal mode
            cs.switchScreen("/softeng_project_group08/view/RuleCreateScreen.fxml", currentStage, title);
        }
    }

    @FXML
    private void backAction(ActionEvent event) {
        //if back is pressed the action is not saved
        ruleManager.getCurrentRule().setAction(null);
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        String title = "MyIFTTT";
        cs.switchScreen("/softeng_project_group08/view/RuleCreateScreen.fxml", currentStage, title);
    }

    @FXML
    private void audioAction(ActionEvent event) {
        handleAction(audioActionID, "Play Audio Action", "/softeng_project_group08/view/actions/PlayAudioActionScreen.fxml");
    }

    @FXML
    private void dialogAction(ActionEvent event) {
        handleAction(dialogActionID, "Show Dialog Action", "/softeng_project_group08/view/actions/ShowDialogActionScreen.fxml");
    }

    @FXML
    private void appendToFileAction(ActionEvent event) {
        handleAction(appendToFileActionID, "Append to File Action", "/softeng_project_group08/view/actions/AppendToFileActionScreen.fxml");
    }

    @FXML
    private void copyFileAction(ActionEvent event) {
        handleAction(copyFileActionID, "Copy File Action", "/softeng_project_group08/view/actions/CopyFileActionScreen.fxml");
    }

    @FXML
    private void moveFileAction(ActionEvent event) {
        handleAction(moveFileActionID, "Move File Action", "/softeng_project_group08/view/actions/MoveFileActionScreen.fxml");
    }

    @FXML
    private void deleteFileAction(ActionEvent event) {
        handleAction(deleteFileActionID, "Delete File Action", "/softeng_project_group08/view/actions/DeleteFileActionScreen.fxml");
    }

    @FXML
    private void executeExternalAction(ActionEvent event) {
        handleAction(executeExternalActionID, "Execute External Program Action", "/softeng_project_group08/view/actions/ExecuteExternalProgramActionScreen.fxml");
    }

    private void handleAction(RadioButton rb, String title, String path) {
        // private method to handle the buttons action.
        ruleManager.getCurrentRule().setAction(null);
        if (rb.isSelected()) {
            /*show a different small window to enable the user to input parameters for the
            specific action that has been selected.*/
            Stage currentStage = (Stage) rb.getScene().getWindow();
            cs.switchScreenModal(path, currentStage, title);
            if (ruleManager.getCurrentRule().getAction() == null) {
                rb.setSelected(false);
            } else if (multipleActionID.isSelected()) {
                /*If the action is not null and the "Multi Actions" checkbox is selected,
                add the action to the ListView, deselect the radio button,
                and enable the "Save" button.
                 */
                listViewID.getItems().add(ruleManager.getCurrentRule().getAction());
                rb.setSelected(false);
                //Unbind the "disableProperty" of the "Save" button to allow manual enabling.
                saveButtonID1.disableProperty().unbind();
                saveButtonID1.setDisable(false);
            }
        }

    }

    @FXML
    private void multipleAction(ActionEvent event) {
        if (multipleActionID.isSelected()) {
            multiActions = new MultiActions();
            listViewID.setItems(FXCollections.observableArrayList());
            if (tg.getSelectedToggle() != null) {
                /*If an action has already been created, and the user clicks on the checkbox,
                  insert this action into the ListView.*/
                listViewID.getItems().add(ruleManager.getCurrentRule().getAction());
                tg.getSelectedToggle().setSelected(false);
                saveButtonID1.disableProperty().unbind();
                saveButtonID1.setDisable(false);
            }
        } else {
            //clean multiActions and list.
            multiActions = null;
            listViewID.setItems(null);
            //reset the binding with the toggle group
            saveButtonID1.disableProperty().bind(tg.selectedToggleProperty().isNull());
        }

    }

    private void deleteSelectedRows(ListView<Action> listView) {
        //delete the selected rows in the listView.
        ObservableList<Action> selectedItems = listView.getSelectionModel().getSelectedItems();
        listView.getItems().removeAll(selectedItems);
        if (listView.getItems().isEmpty() ) {
            saveButtonID1.setDisable(true);
        }
    }

}
