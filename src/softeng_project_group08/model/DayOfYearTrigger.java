/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package softeng_project_group08.model;

import java.time.LocalDate;

/**
 *
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
