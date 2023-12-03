package softeng_project_group08.model;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import javafx.application.Platform;
import javafx.collections.ObservableList;

/**
 * Represents a background thread responsible for periodically checking and
 * executing active rules
 *
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
                    //Checks if the rule has a repetition date and if this date is equal to the current local date truncated to minutes.
                    if ((rule.getRepeat() != null) && (rule.getRepeat().equals(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES)))) {
                        // Activates the rule's state and resets the repetition date to null.
                        rule.setActive(true);
                        rule.setRepeat(null);
                        System.out.println("Rule Reactivated");
                    }
                   System.out.println("Rule " + rule.getName()+ " State: " + (rule.isActive() ? "Active" : "Inactive"));
                    if (rule.isActive() && rule.getTrigger().check()) {
                        rule.getAction().execute();
                        rule.setActive(false);
                        // Checks if the rule has a sleeping time.
                        if (rule.getSleepingTime() != 0) {
                            // Sets the next activation date for the rule based on the current time and sleeping time.
                            rule.setRepeat(LocalDateTime.now().plus(rule.getSleepingTime(), ChronoUnit.MINUTES).truncatedTo(ChronoUnit.MINUTES));
                            System.out.println("Rule triggered, next activation: " + rule.getRepeat());
                        }
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
