package model_tests;

import softeng_project_group08.model.Action;
import softeng_project_group08.model.DialogEventManager;

/**
 * Simple implementation of Action interface for testing purposes
 *
 * @author group08
 */
public class FakeAction implements Action {

    private String name;
    private boolean executeCalled;
    private DialogEventManager dialogEventManager;

    public FakeAction(String name) {
        this.name = name;
        this.executeCalled = false;
        this.dialogEventManager = new DialogEventManager();
    }

    @Override
    public void execute() {
        executeCalled = true;
    }

    public boolean isExecuteCalled() {
        return executeCalled;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public DialogEventManager getDialogEventManager() {
        return dialogEventManager;
    }

}
