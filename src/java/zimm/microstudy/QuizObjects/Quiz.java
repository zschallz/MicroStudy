/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package zimm.microstudy.QuizObjects;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import zimm.microstudy.QuizObjects.views.QuizObjectView;
import zimm.microstudy.db.DBConnect;
import zimm.microstudy.Notification.QuizNotifyMethod;
import zimm.microstudy.misc.QuizStatus;

/**
 *
 * @author zschallz
 */
public class Quiz implements QuizObject {


    private int quizID = -1;
    private int accountID;
    private String name;
    private int optionAskIntervalMinutes;
    private List<Calendar> optionAskDays;
    private List<Question> questions = null;
    private Date startDate;
    private Date endDate;
    private QuizStatus status;
    private QuizNotifyMethod notifyMethod;

    private boolean isSaved;

    public Quiz(int quizID, boolean loadQuestions) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        this.quizID = quizID;
        load(loadQuestions);
    }

    private void load(boolean loadQuestions) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException
    {
        ResultSet dbQuizResult = null;

        dbQuizResult                    = DBConnect.executeSP("showQuizzes(" + this.quizID + ")");

        dbQuizResult.next();
        this.accountID                  = dbQuizResult.getInt("fkAccountID");
        this.name                       = dbQuizResult.getString("name");
        this.optionAskIntervalMinutes   = dbQuizResult.getInt("optionAskIntervalMinutes");
        this.optionAskDays              = DBConnect.parseDaysOfWeekString(dbQuizResult.getString("optionAskDays"));
        this.startDate                  = dbQuizResult.getDate("startDate");
        this.endDate                    = dbQuizResult.getDate("endDate");
        this.status                     = QuizStatus.valueOf(dbQuizResult.getString("status"));
        this.notifyMethod               = QuizNotifyMethod.valueOf(dbQuizResult.getString("notifyMethod"));

        if( loadQuestions )
        {
            this.questions = Question.GetAllQuestions(quizID);
        }
    }

    @Override
    public int save() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean isSaved() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
