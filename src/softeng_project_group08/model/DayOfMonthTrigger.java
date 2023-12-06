/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package softeng_project_group08.model;

import java.time.LocalDate;

/**
 *
 * @author Master
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
