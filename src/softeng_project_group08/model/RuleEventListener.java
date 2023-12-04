/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package softeng_project_group08.model;

import java.util.EventListener;

/**
 * Interface that must be implemented by all observers that want to be notified 
 * when a change happens to a rule or a list of rules.
 * @author mario
 */
public interface RuleEventListener extends EventListener {
    
    public void update(RuleEventType eventType, Rule updatedRule);
    
}
