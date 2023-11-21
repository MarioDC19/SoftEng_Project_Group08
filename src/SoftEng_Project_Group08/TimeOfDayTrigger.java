package SoftEng_Project_Group08;

import java.time.LocalTime;

/**
 *
 * @author mario
 */
public class TimeOfDayTrigger implements Trigger{
    
    private LocalTime timeOfDay;

    public TimeOfDayTrigger(int hour, int minute){
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
    
}
