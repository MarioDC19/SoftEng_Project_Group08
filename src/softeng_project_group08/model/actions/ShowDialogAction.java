package softeng_project_group08.model.actions;

import softeng_project_group08.model.Action;
import softeng_project_group08.model.DialogEventManager;
import softeng_project_group08.model.DialogType;

/**
 * Represents an action to show an information dialog. Notifies listeners when
 * there's a show dialog request.
 *
 * @author group08
 */
public class ShowDialogAction implements Action {

    private String message;
    private DialogEventManager dialogEventManager;

    public ShowDialogAction(String message) {
        this.dialogEventManager = new DialogEventManager();
        this.message = message;
    }

    @Override
    public void execute() {
        dialogEventManager.requestDialog(DialogType.INFO, "ShowDialogAction", message);
    }

    @Override
    public DialogEventManager getDialogEventManager() {
        return dialogEventManager;
    }

    @Override
    public String toString() {
        return "ShowDialogAction:\n" + "message= " + message;
    }

}
