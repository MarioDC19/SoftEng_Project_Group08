package softeng_project_group08.model.triggers;

import softeng_project_group08.model.Trigger;
import java.time.LocalDate;


/**
 * Represents a trigger based on a specific day of the month.
 * @author group08
 */
public class DayOfMonthTrigger implements Trigger {

    private int dayOfMonth;

    public DayOfMonthTrigger(int dayOfMonth) {
        this.dayOfMonth = dayOfMonth;
    }

    @Override
    public boolean check() {
        return LocalDate.now().getDayOfMonth() == dayOfMonth;
    }

    @Override
    public String toString() {
        return "DayOfMonth: "  + dayOfMonth ;
    }
    
    

}
