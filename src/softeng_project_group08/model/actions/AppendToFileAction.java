package softeng_project_group08.model.actions;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import softeng_project_group08.model.Action;

/**
 * Action class to append content to a file. Used to add a string to an existing
 * file or create a new one if it doesn't exist. Requires the content to be
 * added and the file path.
 *
 * @author group08
 */
public class AppendToFileAction implements Action{

    private String content;
    private String filePath;

    // Constructor for the AppendToFileAction class
    public AppendToFileAction(String content, String filePath) {
        this.content = content;
        this.filePath = filePath;
    }

    // Method to execute the action of appending content to the file
    public void execute() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.newLine(); // Move to a new line before appending content
            writer.append(content); // Append the content
            System.out.println("String successfully added to the file: " + filePath);
        } catch (IOException e) {
            System.err.println("Error while adding the string to the file: " + filePath);
            e.printStackTrace();
        }
    }
    
    @Override
    public String toString() {
        return "AppendToFileAction: " + "\ncontent=" + content + "\nfilePath=\n" + filePath;
    }
}
