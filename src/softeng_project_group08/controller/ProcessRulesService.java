/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package softeng_project_group08.controller;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import softeng_project_group08.model.Rule;
import softeng_project_group08.model.RuleList;

/**
 *
 * @author mario
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
                        processRules();
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

    private void processRules() {
        for (Rule rule : rules) {
            //Checks if the rule has a repetition date and if this date is equal to the current local date truncated to minutes.
            if ((rule.getRepeat() != null) && (rule.getRepeat().equals(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES)))) {
                // Activates the rule's state and resets the repetition date to null.
                rule.setActive(true);
                rule.setRepeat(null);
                System.out.println("Rule Reactivated");
            }
            System.out.println("Rule " + rule.getName() + " State: " + (rule.isActive() ? "Active" : "Inactive"));
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
    }

}
