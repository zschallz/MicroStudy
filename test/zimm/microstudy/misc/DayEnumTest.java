/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package zimm.microstudy.misc;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author zschallz
 */
public class DayEnumTest {

    /**
     * Test of parseDaysOfWeekString method, of class DayEnum.
     */
    @Test
    public void testGetDay()
    {

        DayEnum expResult = DayEnum.Saturday;

        DayEnum result = DayEnum.getDay(7);

        assertEquals(expResult, result);
    }
    /**
     * Test of parseDaysOfWeekString method, of class DayEnum.
     */
    @Test
    public void testParseDaysOfWeekString()
    {
        System.out.println("parseDaysOfWeekString");
        String daysOfWeek = "1356";
        List<DayEnum> expResult = new ArrayList<DayEnum>();

        expResult.add(DayEnum.Sunday);
        expResult.add(DayEnum.Tuesday);
        expResult.add(DayEnum.Thursday);
        expResult.add(DayEnum.Friday);

        List<DayEnum> result = DayEnum.parseDaysOfWeekString(daysOfWeek);
        assertEquals(expResult, result);
    }

}