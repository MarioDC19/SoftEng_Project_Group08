package softeng_project_group08.model;

import java.time.LocalTime;

/**
 * Represents a trigger to check if the current time matches a specified time of day.
 * @author group08
 */
public class TimeOfDayTrigger implements Trigger{
    
    private LocalTime timeOfDay;

    public TimeOfDayTrigger(int hour, int minute) {
        timeOfDay = LocalTime.of(hour, minute);
    }

    public LocalTime getTimeOfDay() {
        return timeOfDay;
    }

    public void setTimeOfDay(LocalTime timeOfDay) {
        this.timeOfDay = timeOfDay;
    }
    
    @Override
    public boolean check() {
        LocalTime now = LocalTime.now();
        return timeOfDay.getHour() == now.getHour() && timeOfDay.getMinute() == now.getMinute();
    }
    
    @Override
    public String toString() {
        return "TimeOfDayTrigger: " + timeOfDay;
    }
}
