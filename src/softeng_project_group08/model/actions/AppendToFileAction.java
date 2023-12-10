package softeng_project_group08.model.actions;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import softeng_project_group08.model.Action;
import softeng_project_group08.model.DialogEventManager;
import softeng_project_group08.model.DialogType;

/**
 * Action class to append content to a file. Used to add a string to an existing
 * file or create a new one if it doesn't exist. Requires the content to be
 * added and the file path. Notifies listeners when there's a show dialog
 * request.
 *
 * @author group08
 */
public class AppendToFileAction implements Action {

    private String content;
    private String filePath;
    private DialogEventManager dialogEventManager;

    // Constructor for the AppendToFileAction class
    public AppendToFileAction(String content, String filePath) {
        this.dialogEventManager = new DialogEventManager();
        this.content = content;
        this.filePath = filePath;
    }

    // Method to execute the action of appending content to the file
    @Override
    public void execute() {
        // the file is created if it is deleted before the action is executed
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.newLine(); // Move to a new line before appending content
            writer.append(content); // Append the content
            System.out.println("String successfully added to the file: " + filePath);
        } catch (IOException e) {
            dialogEventManager.requestDialog(DialogType.ERROR,
                    "AppendToFileAction Error",
                    "Error while adding the string to the file: " + filePath);
        }
    }

    @Override
    public DialogEventManager getDialogEventManager() {
        return dialogEventManager;
    }

    @Override
    public String toString() {
        return "AppendToFileAction: " + "\ncontent=" + content + "\nfilePath=\n" + filePath;
    }

}
