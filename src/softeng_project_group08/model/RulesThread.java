package softeng_project_group08.model;

import javafx.application.Platform;
import javafx.collections.ObservableList;

/**
 * Represents a background thread responsible for periodically checking and executing active rules
 * @author group08
 */
public class RulesThread extends Thread {

    private final ObservableList<Rule> rules;
    private static final long WAITMILLIS = 10000;

    public RulesThread(ObservableList<Rule> rules) {
        this.rules = rules;
    }

    @Override
    public void run() {
        while (true) {
            Platform.runLater(() -> {
                for (Rule rule : rules) {
                    if (rule.isActive() && rule.getTrigger().check()) {
                        rule.getAction().execute();
                        rule.setActive(false);
                    }
                }
            });
            try {
                Thread.sleep(WAITMILLIS);
            } catch (InterruptedException ex) {
            }
        }
    }

}
