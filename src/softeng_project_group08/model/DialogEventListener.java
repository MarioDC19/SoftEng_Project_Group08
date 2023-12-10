package softeng_project_group08.model;

import java.util.EventListener;

/**
 * Interface that has to be implemented by observers that can show a dialog
 * window on request
 * 
 * @author group08
 */
public interface DialogEventListener extends EventListener {
    
    public void show(DialogType dialogType, String title, String message);
    
}
