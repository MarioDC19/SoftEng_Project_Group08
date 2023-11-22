package softeng_project_group08.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

/**
 *
 * @author group08
 */
public class MainScreenController implements Initializable {

    @FXML
    private Button newRuleID;
    @FXML
    private TableColumn<?, ?> tableRulesID;
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

@FXML
private void newRuleAction(ActionEvent event) throws IOException {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/softeng_project_group08/view/RuleCreateScreen.fxml"));
    Parent root = loader.load();

    // Get the controller of the second interface
    RuleCreateScreenController secondController = loader.getController();

    

    // Get current window
    Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

    // Close current window
    currentStage.close();

    // Show the interface in the new window
    Stage newStage = new Stage();
    newStage.setScene(new Scene(root));
    newStage.show();
}


    
}
