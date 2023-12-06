package softeng_project_group08.model.triggers;

import softeng_project_group08.model.Trigger;
import java.time.DayOfWeek;
import java.time.LocalDate;

/**
 * Represents a trigger based on a specific day of the week.
 * @author group08
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
