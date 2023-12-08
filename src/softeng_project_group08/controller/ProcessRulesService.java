package softeng_project_group08.controller;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import softeng_project_group08.model.Rule;
import softeng_project_group08.model.RuleList;

/**
 *
 * @author group08
 */
public class ProcessRulesService extends Service<Void> {

    private final RuleList rules;
    private static final long WAITMILLIS = 10000;

    public ProcessRulesService(RuleList rules) {
        this.rules = rules;
    }

    @Override
    protected Task<Void> createTask() {
        return new Task<Void>() {
            @Override
            protected Void call() {
                while (true) {
                    synchronized (rules) {
                        for (Rule rule : rules) {
                            rule.fire();
                        }
                    }
                    try {
                        Thread.sleep(WAITMILLIS);
                    } catch (InterruptedException ex) {
                        if (isCancelled()) {
                            break;
                        }
                    }
                }
                return null;
            }
        };
    }

}
