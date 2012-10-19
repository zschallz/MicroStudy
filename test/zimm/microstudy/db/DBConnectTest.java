/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package zimm.microstudy.db;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author zschallz
 */
public class DBConnectTest {

    public DBConnectTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception
    {
    }

    @AfterClass
    public static void tearDownClass() throws Exception
    {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of parseDaysOfWeekString method, of class DBConnect.
     */
    @Test
    public void testParseDaysOfWeekString()
    {
        System.out.println("parseDaysOfWeekString");
        String daysOfWeek = "1356";

        List<Calendar> expResult = new ArrayList<Calendar>();
        Calendar cal;

        cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        expResult.add(cal);
        cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
        expResult.add(cal);
        cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
        expResult.add(cal);
        cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
        expResult.add(cal);

        List<Calendar> result = DBConnect.parseDaysOfWeekString(daysOfWeek);

        System.out.println("Testing days...");
        Calendar calTest = (Calendar) result.get(0);
        assertEquals(calTest.get(Calendar.DAY_OF_WEEK), Calendar.SUNDAY);
        calTest = (Calendar) result.get(1);
        assertEquals(calTest.get(Calendar.DAY_OF_WEEK), Calendar.TUESDAY);
        calTest = (Calendar) result.get(2);
        assertEquals(calTest.get(Calendar.DAY_OF_WEEK), Calendar.THURSDAY);
        calTest = (Calendar) result.get(3);
        assertEquals(calTest.get(Calendar.DAY_OF_WEEK), Calendar.FRIDAY);
        System.out.println("Success.");
    }

}