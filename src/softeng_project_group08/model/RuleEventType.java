/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package softeng_project_group08.model;

import java.io.Serializable;

/**
 * Enum that represents the types of events that a subject can generate.
 * CHANGE: an attribute of a rule has changed
 * ADD: a rule has been added to the RuleList
 * REMOVE: a rule has been removed from the RuleList
 * @author mario
 */
public enum RuleEventType implements Serializable {
    CHANGE, ADD, REMOVE
}
