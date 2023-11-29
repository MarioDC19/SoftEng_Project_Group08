/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package softeng_project_group08.controller;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import softeng_project_group08.model.AppendToFileAction;
import softeng_project_group08.model.RuleManager;

/**
 * FXML Controller class
 *
 * @author group08
 */
public class AppendToFileActionScreenController implements Initializable {

    @FXML
    private Button saveButtonID;
    @FXML
    private TextField appendStringID;
    @FXML
    private TextField filePathID;
    @FXML
    private Button insertFileID;
    File file;
    RuleManager rm;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        rm= RuleManager.getRuleManager();
        file=null;
       saveButtonID.disableProperty().bind(
                Bindings.or(
                        filePathID.textProperty().isEmpty(),
                        appendStringID.textProperty().isEmpty()
                ));
    }    

    @FXML
    private void saveButtonAction(ActionEvent event) {
        AppendToFileAction atfa = new AppendToFileAction(appendStringID.getText(),file.getPath());
        rm.getCurrentRule().setAction(atfa);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
        
    }

    @FXML
    private void insertFileAction(ActionEvent event) {
        FileChooser fc= new FileChooser();
        fc.setTitle("Select File");
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Text File", "*.txt", "*.csv", "*.xml", "*.json", "*.html", "*.md", "*.log", "*.conf", "*.properties", "*.ini");
        fc.getExtensionFilters().add(extFilter);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        file=fc.showOpenDialog(stage);
        if(file != null){
            filePathID.setText(file.getPath());
        }
    }
    
}
