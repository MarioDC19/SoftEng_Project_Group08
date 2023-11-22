package softeng_project_group08.model;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

/**
 *
 * @author group08
 */
public class PlayAudioAction implements Action {

    private File file;

    public PlayAudioAction(File file) {
        this.file = file;
    }

    @Override
    public void execute() {
        try {
            // Check if Desktop is supported
            if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.OPEN)) {
                Desktop desktop = Desktop.getDesktop();
                // Check if audio file exists
                if (file.exists() && file.isFile()) {
                    desktop.open(file);
                } else {
                    System.out.println("Audio file does not exist.");
                }

            } else {
                System.out.println("Desktop is not supported.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
