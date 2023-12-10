package model_tests;

import softeng_project_group08.model.DialogEventListener;
import softeng_project_group08.model.DialogType;

/**
 * Simple implementation of DialogEventListener interface for testing purposes
 *
 * @author group08
 */
public class FakeDialogEventListener implements DialogEventListener {

    private boolean showCalled = false;

    @Override
    public void show(DialogType dialogType, String title, String message) {
        showCalled = true;
    }

    public boolean isShowCalled() {
        return showCalled;
    }

}
