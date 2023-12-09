package softeng_project_group08.model;

import java.io.Serializable;

/**
 * Enum that represents the types of events that a subject can generate.
 * CHANGE: the state of a rule has changed
 * ADD: a rule has been added to the RuleList
 * REMOVE: a rule has been removed from the RuleList
 * 
 * @author group08
 */
public enum RuleEventType implements Serializable {
    CHANGE, ADD, REMOVE
}
