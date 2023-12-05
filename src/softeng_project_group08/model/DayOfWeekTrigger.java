/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package softeng_project_group08.model;

import java.time.DayOfWeek;
import java.time.LocalDate;

/**
 *
 * @author mauri
 */
public class DayOfWeekTrigger implements Trigger {
    
    private DayOfWeek day;
    
    public DayOfWeekTrigger (DayOfWeek day){
        this.day=day;
    }
    
    public DayOfWeek getDayOfWeek() {
        return day;
    }
    
    public void setDayOfWeek(DayOfWeek day) {
        this.day = day;
    }

    @Override
    public boolean check() {
        // Get the current day of the week
        DayOfWeek currentDay = LocalDate.now().getDayOfWeek();

        // Compare the current day with the specified day
        return currentDay == day;
    }

    @Override
    public String toString() {
        return "DayOfWeekTrigger: " + day ;
    }
    
    
    
}
