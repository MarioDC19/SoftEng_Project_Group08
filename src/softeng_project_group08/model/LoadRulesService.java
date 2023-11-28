/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package softeng_project_group08.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.stage.Stage;

/**
 * This class represents a JavaFX service for asynchronous loading of a list of
 * rules from a binary file.
 *
 * @author group08
 *
 */
public class LoadRulesService extends Service<List<Rule>> {

    private String path;

    public LoadRulesService() {
        this.path = "ListRules.bin";
    }

    @Override
    protected Task<List<Rule>> createTask() {
        return new Task<List<Rule>>() {
            @Override
            protected List<Rule> call() {
                List<Rule> list = new ArrayList<>();
                File f=new File(path);
                if(f.exists()){
                try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path))) {
                    list = (List<Rule>) ois.readObject();

                } catch (FileNotFoundException ex) {
                    System.out.println("I");
                } catch (IOException ex) {

                } catch (ClassNotFoundException ex) {

                }
                }
                else{
                    System.out.println("I have tried to load the list of rules, but the file doesn't exist yet");
                }

                return list;
            }

        };

    }

}
