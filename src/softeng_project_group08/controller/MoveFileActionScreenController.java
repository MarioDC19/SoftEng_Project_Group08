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
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import softeng_project_group08.model.CopyFileAction;
import softeng_project_group08.model.MoveFileAction;
import softeng_project_group08.model.RuleManager;

/**
 * FXML Controller class
 *
 * @author group08
 */
public class MoveFileActionScreenController implements Initializable {

    @FXML
    private Button saveButtonID;
    @FXML
    private TextField filePathID;
    @FXML
    private Button insertFileID;
    @FXML
    private TextField directoryPathID;
    @FXML
    private Button insertDirectoryID;
    File file;
    File directory;
    RuleManager rm;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        file=null;
        directory=null;
        saveButtonID.disableProperty().bind(
                Bindings.or(
                        filePathID.textProperty().isEmpty(),
                        directoryPathID.textProperty().isEmpty()
                ));
        rm=RuleManager.getRuleManager();
        
    }    

    @FXML
    private void saveButtonAction(ActionEvent event) {
        MoveFileAction mfa= new MoveFileAction(file,directory);
        rm.getCurrentRule().setAction(mfa);
        Stage currentStage = (Stage) saveButtonID.getScene().getWindow();
        currentStage.close();
        
    }

    @FXML
    private void insertFileAction(ActionEvent event) {
        FileChooser fc= new FileChooser();
        fc.setTitle("Select File");
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        file=fc.showOpenDialog(stage);
        if(file != null){
            filePathID.setText(file.getPath());
        }
        
    }

    @FXML
    private void insertDirectoryAction(ActionEvent event) {
        DirectoryChooser dc = new DirectoryChooser();
        dc.setTitle("Select destionation directory");
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        directory=dc.showDialog(stage);
        if(directory != null){
            directoryPathID.setText(directory.getPath());
        }
    }
    
}
