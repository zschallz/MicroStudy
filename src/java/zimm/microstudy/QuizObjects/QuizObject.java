/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package zimm.microstudy.QuizObjects;

import java.sql.SQLException;
import zimm.microstudy.QuizObjects.views.QuizObjectView;

/**
 *
 * @author zschallz
 */
public interface QuizObject {
    int save() throws SQLException,Exception; /* change exception to custom exception */
    boolean isSaved();
}
