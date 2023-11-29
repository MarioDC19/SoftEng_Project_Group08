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
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import softeng_project_group08.model.Rule;
import softeng_project_group08.model.RuleManager;

/**
 * Controls the main screen functionalities and interactions.
 *
 * @author group08
 */
public class MainScreenController implements Initializable {

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

    private ChangeScreen cs;
    @FXML
    private Button deleteRuleID;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cs = new ChangeScreen();
        // Get the RuleManager instance.
        ruleManager = RuleManager.getRuleManager();
        // Get the list of rules from the RuleManager.
        ObservableList<Rule> rulesList = ruleManager.getRules();

        tableRulesID.setCellValueFactory(new PropertyValueFactory<>("name"));
        tableViewID.setItems(rulesList);
        
        ContextMenu contextMenu = new ContextMenu();
        MenuItem deleteMenuItem = new MenuItem("Delete");
        deleteMenuItem.setOnAction(event -> deleteRuleAction());
        contextMenu.getItems().add(deleteMenuItem);

        // Aggiungi un listener per il clic destro sulla TableView
        tableViewID.setRowFactory(tv -> {
            TableRow<Rule> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getButton() == MouseButton.SECONDARY) {
                    contextMenu.show(tableViewID, event.getScreenX(), event.getScreenY());
                }
            });
            return row;
        });
        
        deleteRuleID.disableProperty().bind(Bindings.isEmpty(tableViewID.getSelectionModel().getSelectedItems()));
        
        tableRulesID.setCellFactory(col -> {
            TableCell<Rule, String> cell = new TableCell<>();
            cell.textProperty().bind(Bindings.createStringBinding(() ->
                    cell.getItem(), cell.itemProperty()));
            return cell;
        });
        
        tableViewID.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        
        

    }

    @FXML
    private void newRuleAction(ActionEvent event) {
        Rule newRule = new Rule(null, null, null);
        ruleManager.setCurrentRule(newRule);
        //Load the RuleCreateScreen
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
        String message = "Build 1.0.0, Information Contacts: \nSalvatore Bruno, Mario Della Corte, Maurizio Esposito, Antonio De Caro";

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    @FXML
    private void deleteRuleAction() {
        ObservableList<Rule> selectedRules = tableViewID.getSelectionModel().getSelectedItems();

        if (!selectedRules.isEmpty()) {
            Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmAlert.setTitle("Confirmation");
            confirmAlert.setHeaderText("Delete selected rules?");
            confirmAlert.setContentText("Are you sure you want to delete the selected rules?");

            Optional<ButtonType> result = confirmAlert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                for (Rule rule : selectedRules) {
                    ruleManager.removeRule(rule);
                }
                tableViewID.getItems().removeAll(selectedRules);
            }
        } 
    }
    
}
