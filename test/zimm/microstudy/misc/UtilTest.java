/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package zimm.microstudy.misc;

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
public class UtilTest {
    /**
     * Test of findIndexEnd method, of class Util.
     */
    @Test
    public void testFindIndexEnd()
    {
        System.out.println("findIndexEnd");
        String originalStr = "answer9";
        String searchStr = "answer";
        String expResult = "9";
        String result = Util.findIndexEnd(originalStr, searchStr);
        assertEquals(expResult, result);
    }

}