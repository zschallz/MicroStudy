/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package zimm.microstudy.QuizObjects;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import zimm.microstudy.QuizObjects.views.QuestionView;
import zimm.microstudy.QuizObjects.views.QuizObjectView;
import zimm.microstudy.QuizObjects.views.ViewType;
import zimm.microstudy.db.DBConnect;


/**
 *
 * @author zschallz
 */
public class Question implements QuizObject {

    private int questionID;
    private String questionBody;
    private List<QuestionAnswer> questionAnswers;

    private int quizID;
    /* FOR FUTURE... PAST MILESTONE 6:
     * private QuestionType questionType;
     * private List<Diagrams> diagrams;
     */

    private boolean isSaved;

    public Question()
    {
        this.questionID = -1;
        this.quizID = -1;
        this.isSaved = false;
        this.questionAnswers = new ArrayList<QuestionAnswer>();
    }

    public Question (int questionID) throws SQLException, ClassNotFoundException, InstantiationException, IllegalArgumentException, IllegalAccessException {
        this.questionID = questionID;
        load();
    }

    private void load() throws SQLException, ClassNotFoundException, InstantiationException, IllegalArgumentException, IllegalAccessException
    {
        ResultSet dbQuestionResult = null;

        dbQuestionResult        = DBConnect.executeSP("showQuestion(" + this.questionID + ")");

        dbQuestionResult.next();
        //this.questionID         = dbQuestionResult.getInt("pkID"); no need as view doesn't have it, and it is already set.
        this.questionBody       = dbQuestionResult.getString("questionBody");
        this.questionAnswers    = QuestionAnswer.GetQuestionAnswers(questionID);
        this.quizID             = dbQuestionResult.getInt("fkQuizID");

        dbQuestionResult.close();

    }

    public static Question GetRandomQuestion(int quizID) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException
    {
        /* Get random QuestionID, then create question object with it */
        ResultSet dbQuestionIDResult = null;
        dbQuestionIDResult = DBConnect.executeSP("getRandomQuestionID(" + quizID + ")");

        dbQuestionIDResult.next();
        int questionID = dbQuestionIDResult.getInt("pkID");
        dbQuestionIDResult.close();

        return new Question(questionID);        
    }
    public static List<Question> GetAllQuestions(int quizID) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException
    {
        /* Get random QuestionID, then create question object with it */
        ResultSet dbQuestionIDResult = null;
        dbQuestionIDResult = DBConnect.executeSP("getAllQuizQuestionIDs(" + quizID + ")");
        
        List<Question> questions = new ArrayList<Question>();
        int questionID;

        while (dbQuestionIDResult.next())
        {
            questionID = dbQuestionIDResult.getInt("pkID");
            questions.add(new Question(questionID));
        }

        dbQuestionIDResult.close();

        return questions;
    }

    @Override
    public int save() throws SQLException, Exception {

        validate();

        int rowsAffected = 0;

        if(this.questionID == -1)
        {
            /* question newly instnatiated; save as new question */
            rowsAffected += saveNewQuestion();

            if(rowsAffected > 0 && this.questionAnswers.size() > 0)
            {
                /* Question was saved successfully; let's save the answers now, too. */

                addQuestionIDToAnswers();

                List<Exception> notSaved = null;

                for( QuestionAnswer answer : this.questionAnswers )
                {
                    try
                    {
                        rowsAffected += answer.save();
                    }
                    catch(Exception ex)
                    {
                        /* if an answer fails, record exception and reraise after trying to save the rest. */
                        if( ex.getMessage().contains("Cannot Save") )
                        {
                            if( notSaved == null )
                            {
                                notSaved = new ArrayList<Exception>();
                            }
                            notSaved.add(ex);
                        }
                    }

                    if(notSaved != null)
                    {
                        for(Exception ex : notSaved)
                            throw ex;
                    }
                }
            }
        }
        else
        {
            /* question already existed in db; update it */
            update();
        }

        return rowsAffected;
    }

    @Override
    public boolean isSaved() {
        return this.isSaved;
    }

    public List<QuestionAnswer> getQuestionAnswers()
    {
        return questionAnswers;
    }

    public String getQuestionBody()
    {
        return questionBody;
    }

    public QuizObjectView getView()
    {
        return new QuestionView(this,ViewType.Webpage);
    }

    public int getQuestionID()
    {
        return questionID;
    }

    public int getQuizID()
    {
        return quizID;
    }

    public void setQuestionAnswers(List<QuestionAnswer> questionAnswers)
    {
        this.questionAnswers = questionAnswers;
    }

    public void setQuestionBody(String questionBody)
    {
        this.questionBody = questionBody;
    }

    public void setQuizID(int quizID)
    {
        this.quizID = quizID;
    }

    private void validate() throws Exception
    {
        if(quizID == -1)
        {
            throw new Exception("Cannot save question: A question must be part of a quiz.");
        }

        if(questionBody.equals(""))
        {
            throw new Exception("Cannot save question: A question cannot be blank.");
        }

        if(questionBody.length() > 9032)
        {
            throw new Exception("Cannot save question: Question text can't be "
                    + "more than 9032 characters. Supplied: " +
                    questionBody.length() + " characters long.");
        }
    }

    private int saveNewQuestion() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException
    {
        ResultSet dbQuestionResult = null;

        dbQuestionResult        = DBConnect.executeSP("createQuestion('" + this.questionBody + "'," + this.quizID + ")");

        dbQuestionResult.next();
        this.questionID             = dbQuestionResult.getInt("LAST_INSERT_ID()");

        dbQuestionResult.close();

        if(questionID != -1)
            return 1; // rows affected
        else
            return 0;
    }

    private void update()
    {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /* This needs to be called AFTER saving the question, but BEFORE
     * saving the question's answers because the questionID is not known
     * until the question has been saved.
     */
    private void addQuestionIDToAnswers()
    {
        for(QuestionAnswer answer : this.questionAnswers)
        {
            answer.setQuestionID(this.questionID);
        }
    }
}
