/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package zimm.microstudy.QuizObjects;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import zimm.microstudy.QuizObjects.views.QuizObjectView;
import zimm.microstudy.db.DBConnect;

/**
 *
 * @author zschallz
 */
public class QuestionAnswer implements QuizObject {


    private int answerID;
    private String answerBody;
    private boolean isACorrectAnswer;
    private int questionID;

    private boolean isSaved;

    public QuestionAnswer()
    {
        this.answerID = -1;
        this.questionID = -1;
        this.isSaved = false;
        this.isACorrectAnswer = false;
    }

    public QuestionAnswer(String answerBody)
    {
        this.answerID = -1;
        this.questionID = -1;
        this.isSaved = false;
        this.isACorrectAnswer = false;
        this.answerBody = answerBody;
    }

    public QuestionAnswer (int answerID) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        this.answerID = answerID;
        load();
    }

    public QuestionAnswer (int answerID, String answerBody, int questionID, boolean isACorrectAnswer, boolean isSaved)
    {
        this.answerID           = answerID;
        this.answerBody         = answerBody;
        this.questionID         = questionID;
        this.isSaved            = isSaved;
        this.isACorrectAnswer   = isACorrectAnswer;
    }
    private void load() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException
    {
        ResultSet dbAnswerResults = null;
        dbAnswerResults = DBConnect.executeSP("showAnswer(" + this.answerID + ")");

        answerID = dbAnswerResults.getInt("pkID");
        answerBody = dbAnswerResults.getString("answerBody");
        isACorrectAnswer = DBConnect.parseDBInt(dbAnswerResults.getInt("isACorrectAnswer"));

        throw new UnsupportedOperationException("Not yet implemented");

    }

    public static List<QuestionAnswer> GetQuestionAnswers(int questionID) throws SQLException, IllegalArgumentException, ClassNotFoundException, InstantiationException, IllegalAccessException
    {
        ResultSet dbAnswersResults = null;
        dbAnswersResults = DBConnect.executeSP("showAnswers(" + questionID + ")");

        int answerID;
        String answerBody;
        boolean isACorrectAnswer;
        List<QuestionAnswer> questionAnswerList = new ArrayList<QuestionAnswer>();

        /* create QuestionAnswer object for each result */

        while (dbAnswersResults.next()) {
            
            answerID = dbAnswersResults.getInt("pkID");
            answerBody = dbAnswersResults.getString("answerBody");
            isACorrectAnswer = DBConnect.parseDBInt(dbAnswersResults.getInt("isACorrectAnswer"));
            
            questionAnswerList.add( new QuestionAnswer(answerID, answerBody, questionID, isACorrectAnswer, true) );
            
        }
        
        dbAnswersResults.close();

        return questionAnswerList;
    }

    @Override
    public int save() throws SQLException, Exception {
        validate();

        int rowsAffected = 0;

        if(this.answerID == -1)
        {
            /* this answer has not been saved yet; will save it */
            rowsAffected += saveNewQuestionAnswer();
        }
        else
        {
            rowsAffected += updateQuestionAnswer();
        }

        return rowsAffected;
    }

    @Override
    public boolean isSaved() {
        return this.isSaved;
    }

    public String getAnswerBody()
    {
        return answerBody;
    }

    public QuizObjectView getView()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setAnswerBody(String answerBody)
    {
        this.answerBody = answerBody;
    }

    public void setAnswerID(int answerID)
    {
        this.answerID = answerID;
    }

    public void setIsACorrectAnswer(boolean isACorrectAnswer)
    {
        this.isACorrectAnswer = isACorrectAnswer;
    }

    public void setQuestionID(int questionID)
    {
        this.questionID = questionID;
    }

    private void validate() throws Exception
    {
        if(questionID == -1)
        {
            throw new Exception("Cannot save answer: "
                    + "An answer must be part of a question.");
        }

        if(answerBody.equals(""))
        {
            throw new Exception("Cannot save answer: "
                    + "An answer cannot be blank.");
        }

        if(answerBody.length() > 4096)
        {
            throw new Exception("Cannot save answer: Question text can't be "
                    + "more than 4096 characters. Supplied: " +
                    answerBody.length() + " characters long.");
        }
    }

    private int saveNewQuestionAnswer() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException
    {
        ResultSet dbQuestionAnswerResult = null;

        dbQuestionAnswerResult  = DBConnect.executeSP("createAnswer('"
                + this.answerBody + "',"
                + DBConnect.parseDBBoolean(isACorrectAnswer) + ","
                + this.questionID + ")");

        dbQuestionAnswerResult.next();
        this.answerID         = dbQuestionAnswerResult.getInt("LAST_INSERT_ID()");

        dbQuestionAnswerResult.close();

        if(answerID != -1)
            return 1; // rows affected
        else
            return 0;
    }

    private int updateQuestionAnswer()
    {
        throw new UnsupportedOperationException("Not yet implemented");
    }

}
