/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package zimm.microstudy.QuizObjects;

import java.sql.ResultSet;
import java.sql.SQLException;
import zimm.microstudy.QuizObjects.views.QuizObjectView;
import zimm.microstudy.db.DBConnect;

/**
 *
 * @author zschallz
 */
public class Account implements QuizObject {

    public int save()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean isSaved()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public QuizObjectView getView()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public static String GetEmailByQuizID(int quizID) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException
    {
        /* Get random QuestionID, then create question object with it */
        ResultSet dbQuestionIDResult = null;
        dbQuestionIDResult = DBConnect.executeSP("showEmailByQuizID(" + quizID + ")");

        dbQuestionIDResult.next();
        String email = dbQuestionIDResult.getString("email");
        dbQuestionIDResult.close();

        return email;
    }

}
