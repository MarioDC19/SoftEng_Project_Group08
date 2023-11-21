package SoftEng_Project_Group08;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

/**
 *
 * @author Master
 */
public class PlayAudioAction implements Action {

    private File file;

    public PlayAudioAction(File file) {
        this.file = file;
    }

    @Override
    public void execute() {
        try {
            //Controlo che Desktop sia supportato
            if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.OPEN)) {
                Desktop desktop = Desktop.getDesktop();
                //Controlo sull'esistenza del file
                if (file.exists() && file.isFile()) {
                    desktop.open(file);
                } else {
                    System.out.println("Il file audio non esiste o non è valido.");
                }

            } else {
                System.out.println("Desktop non è supportato su questo ambiente.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
