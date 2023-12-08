package model_tests;

import java.io.FileOutputStream;
import softeng_project_group08.model.Action;

/**
 *
 * @author group08
 */
public class FakeAction implements Action {
    
    private String name;
        private boolean executeCalled;

        public FakeAction(String name) {
            this.name = name;
            this.executeCalled=false;
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
}
