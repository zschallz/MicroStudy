/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package zimm.microstudy.misc;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author zschallz
 */
public enum DayEnum {
    Sunday(1),
    Monday(2),
    Tuesday(3),
    Wednesday(4),
    Thursday(5),
    Friday(6),
    Saturday(7);

    private final int index;

    DayEnum(int index)
    {
        this.index = index;
    }

    public static DayEnum getDay(int index)
    {
        switch( index )
        {
            case 1:
                return DayEnum.Sunday;
            case 2:
                return DayEnum.Monday;
            case 3:
                return DayEnum.Tuesday;
            case 4:
                return DayEnum.Wednesday;
            case 5:
                return DayEnum.Thursday;
            case 6:
                return DayEnum.Friday;
            case 7:
                return DayEnum.Saturday;
            default:
                throw new RuntimeException("Invalid index for DayEnum: " + index);
        }
    }

    public static List<DayEnum> parseDaysOfWeekString(String daysOfWeek)
    {
        List<DayEnum> days = new ArrayList<DayEnum>();

        for(char c : daysOfWeek.toCharArray())
        {
            // Assuming all input is correct, because validation is done at DB level by triggers
            days.add(DayEnum.getDay(Integer.parseInt(Character.toString(c))));
        }

        return days;
    }
}
