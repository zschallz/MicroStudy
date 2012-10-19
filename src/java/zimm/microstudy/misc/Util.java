/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package zimm.microstudy.misc;

/**
 *
 * @author zschallz
 */
public class Util {

    public static String findIndexEnd(String originalStr, String searchStr)
    {
        return originalStr.substring(originalStr.indexOf(searchStr)+searchStr.length());
    }
    
}
