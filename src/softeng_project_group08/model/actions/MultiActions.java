package softeng_project_group08.model.actions;

import java.util.LinkedList;
import java.util.List;
import softeng_project_group08.model.Action;
import softeng_project_group08.model.DialogEventListener;
import softeng_project_group08.model.DialogEventManager;
import softeng_project_group08.model.DialogType;

/**
 * Composite class implementing the Action interface and holds a collection of
 * Action objects. This class allows the accumulation of multiple leaf classes
 * into a complex composite class, following the Composite pattern. Notifies
 * listeners when there's a show dialog request received by contained actions.
 *
 * @author group08
 */
public class MultiActions implements Action, DialogEventListener {

    private List<Action> list;
    private DialogEventManager dialogEventManager;

    public MultiActions() {
        this.dialogEventManager = new DialogEventManager();
        this.list = new LinkedList();
    }

    public void addChild(Action a) {
        list.add(a);
        // this object must be notified when a contained actions requests a dialog
        // get method can return null
        if (a.getDialogEventManager() != null) {
            a.getDialogEventManager().subscribe(this);
        }
    }

    public void removeChild(Action a) {
        list.remove(a);
        // this object does not need to be notified about removed actions
        // get method can return null
        if (a.getDialogEventManager() != null) {
            a.getDialogEventManager().unsubscribe(this);
        }
    }

    public Action getChild(Action a) {
        return list.get(list.indexOf(a));
    }

    @Override
    public void execute() {
        for (Action a : list) {
            a.execute();
        }
    }

    public List<Action> getList() {
        return list;
    }

    @Override
    public DialogEventManager getDialogEventManager() {
        return dialogEventManager;
    }

    @Override
    public void show(DialogType dialogType, String title, String message) {
        // forward the event to listeners of this object
        dialogEventManager.requestDialog(dialogType, title, message);
    }

    @Override
    public String toString() {
        String s = "";
        for (Action a : list) {
            s = s + a + '\n';
        }
        return s;
    }

}
