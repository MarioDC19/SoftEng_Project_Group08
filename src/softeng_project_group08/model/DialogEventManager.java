package softeng_project_group08.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Helper class utilized by subjects from the model that need to show a dialog
 * window in the GUI by notifying the appropriate controller object
 *
 * @author group08
 */
public class DialogEventManager implements Serializable {

    private List<DialogEventListener> listeners = new ArrayList<>();

    public void subscribe(DialogEventListener listener) {
        listeners.add(listener);
    }

    public void unsubscribe(DialogEventListener listener) {
        listeners.remove(listener);
    }

    public void requestDialog(DialogType dialogType, String title, String message) {
        for (DialogEventListener listener : listeners) {
            listener.show(dialogType, title, message);
        }
    }

}
