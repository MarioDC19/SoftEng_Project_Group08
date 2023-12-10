package softeng_project_group08.model;

import java.io.Serializable;

/**
 *
 * @author group08
 */
public interface Action extends Serializable {
    
    public void execute();
    
    // this method will return null if the action does not need to show dialogs
    public DialogEventManager getDialogEventManager();
    
}
