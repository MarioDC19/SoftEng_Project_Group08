package softeng_project_group08.model.triggers;

import softeng_project_group08.model.Trigger;
import java.time.LocalDate;

/**
 * 
 * Represents a trigger based on a specific day of the year.
 * @author grouo08
 */
public class DayOfYearTrigger implements Trigger {
    private LocalDate dayOfYear;

    public DayOfYearTrigger(LocalDate dayOfYear) {
        this.dayOfYear = dayOfYear;
    }

    @Override
    public boolean check() {
        return LocalDate.now().equals(dayOfYear);
    }

    @Override
    public String toString() {
        return "DayOfYear: "  + dayOfYear ;
    }
    
}
