package softeng_project_group08.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import softeng_project_group08.model.Action;
import softeng_project_group08.model.Rule;
import softeng_project_group08.model.RuleEventListener;
import softeng_project_group08.model.RuleEventType;
import softeng_project_group08.model.RuleList;
import softeng_project_group08.model.Trigger;

/**
 * Controls the main screen functionalities and interactions. This controller is
 * subscribed to the list, when the list changes, the table view is updated
 * accordingly.
 *
 * @author group08
 */
public class MainScreenController implements Initializable, RuleEventListener {

    @FXML
    private Button newRuleID;
    @FXML
    private TableColumn<Rule, String> tableRulesID;
    @FXML
    private TableView<Rule> tableViewID;

    private RuleManager ruleManager;

    @FXML
    private MenuItem closeID;

    @FXML
    private MenuItem aboutID;

    private ChangeScreen cs = new ChangeScreen();

    @FXML
    private Button deleteRuleID;
    @FXML
    private TableColumn<Rule, Boolean> activeRuleColumnID;
    @FXML
    private TableColumn<Rule, Trigger> tableTriggerID;
    @FXML
    private TableColumn<Rule, Integer> tableTimeID;
    @FXML
    private TableColumn<Rule, Action> tableActionID;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Get the RuleManager instance.
        ruleManager = RuleManager.getRuleManager();
        initTableView();
        initDeleteRulesFunctionality();
        // subscribe this controller to the RuleList and listen for any type of change.
        ruleManager.getRules().getEventManager().subscribe(this);
        // Initialize the RuleManager (subscribe, start processing rules)
        ruleManager.initialize();
    }

    private void initTableView() {
        // Get the list of rules from the RuleManager.
        RuleList rulesList = ruleManager.getRules();
        // Initialize the table view
        tableRulesID.setCellValueFactory(new PropertyValueFactory<>("name"));
        // set the state of the checkbox at the current value
        activeRuleColumnID.setCellValueFactory(cellData -> new SimpleBooleanProperty(cellData.getValue().isActive()));
        tableTriggerID.setCellValueFactory(new PropertyValueFactory<>("trigger"));
        tableActionID.setCellValueFactory(new PropertyValueFactory<>("action"));
        tableTimeID.setCellValueFactory(new PropertyValueFactory<>("sleepingTime"));
        // add functionality to change the state of the rule when the checkbox is clicked
        activeRuleColumnID.setCellFactory(column -> new CheckBoxTableCell<Rule, Boolean>() {
            @Override
            public void updateItem(Boolean item, boolean empty) {
                super.updateItem(item, empty);
                if (!isEmpty()) {
                    TableCell<Rule, Boolean> cell = this;
                    CheckBox checkBox = (CheckBox) cell.getGraphic();

                    checkBox.setOnMouseClicked(event -> {
                        int rowIndex = getIndex();
                        Rule selectedRule = tableViewID.getItems().get(rowIndex);
                        selectedRule.setActive(!selectedRule.isActive());
                    });
                }
            }
        });
        // initialize table view list by adding rules from ruleList
        for (Rule r : rulesList) {
            tableViewID.getItems().add(r);
        }
    }

    private void initDeleteRulesFunctionality() {
        // Initialize right-click remove functionality on table view
        ContextMenu contextMenu = new ContextMenu();
        MenuItem deleteMenuItem = new MenuItem("Delete");
        deleteMenuItem.setOnAction(event -> deleteRuleAction());
        contextMenu.getItems().add(deleteMenuItem);
        // Add a listener to react to right click on the table view
        tableViewID.setRowFactory(tv -> {
            TableRow<Rule> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getButton() == MouseButton.SECONDARY) {
                    contextMenu.show(tableViewID, event.getScreenX(), event.getScreenY());
                }
            });
            return row;
        });
        // Initialize remove selected rules functionality on table view
        deleteRuleID.disableProperty().bind(Bindings.isEmpty(tableViewID.getSelectionModel().getSelectedItems()));
        tableRulesID.setCellFactory(col -> {
            TableCell<Rule, String> cell = new TableCell<>();
            cell.textProperty().bind(Bindings.createStringBinding(()
                    -> cell.getItem(), cell.itemProperty()));
            return cell;
        });
        tableViewID.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    @FXML
    private void newRuleAction(ActionEvent event) {
        Rule newRule = new Rule(null, null, null);
        ruleManager.setCurrentRule(newRule);
        // Unsubscribe from the ruleList before changing screen
        ruleManager.getRules().getEventManager().unsubscribe(this);
        // Load the RuleCreateScreen
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        String title = "MyIFTTT";
        cs.switchScreen("/softeng_project_group08/view/RuleCreateScreen.fxml", currentStage, title);

    }

    @FXML
    private void closeAction(ActionEvent event) {
        Stage stage = (Stage) closeID.getParentPopup().getOwnerWindow();
        stage.close();
    }

    //Gives information about the application
    @FXML
    private void aboutIAction(ActionEvent event) {
        String message = "Information Contacts: \nSalvatore Bruno, Mario Della Corte, Maurizio Esposito, Antonio De Caro";
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void deleteRuleAction() {
        List<Rule> selectedRules = new ArrayList<>(tableViewID.getSelectionModel().getSelectedItems());

        if (!selectedRules.isEmpty()) {
            Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmAlert.setTitle("Confirmation");
            confirmAlert.setHeaderText("Delete selected rules?");
            confirmAlert.setContentText("Are you sure you want to delete the selected rules?");

            Optional<ButtonType> result = confirmAlert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                for (Rule rule : selectedRules) {
                    ruleManager.getRules().removeRule(rule);
                }
            }
        }
    }

    @Override
    public void update(RuleEventType eventType, Rule updatedRule) {
        switch (eventType) {
            case ADD:
                tableViewID.getItems().add(updatedRule);
                break;
            case REMOVE:
                tableViewID.getItems().remove(updatedRule);
                break;
            case CHANGE:
                // reload the row in the table view
                tableViewID.refresh();
                break;
            default:
                System.out.println("Unknown event");
        }
    }

}
