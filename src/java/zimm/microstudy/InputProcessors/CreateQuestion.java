/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package zimm.microstudy.InputProcessors;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import zimm.microstudy.QuizObjects.Question;
import zimm.microstudy.QuizObjects.QuestionAnswer;
import zimm.microstudy.misc.Util;

/**
 *
 * @author zschallz
 */

public class CreateQuestion extends HttpServlet {

    Question question;
    int rowsAffected = 0;

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {


            /* TODO output your page here
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet CreateQuestion</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CreateQuestion at " + request.getContextPath () + "</h1>");
            out.println("</body>");
            out.println("</html>");
            */
        } finally { 
            out.close();
        }
    } 

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    } 

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {

        this.question = new Question();

        Enumeration<String> parameterNames = request.getParameterNames();

        while(parameterNames.hasMoreElements())
        {
            String parameterName = parameterNames.nextElement();

            if(!request.getParameter(parameterName).equals(""))
                processInputParameter(parameterName, request.getParameter(parameterName));

        }

        PrintWriter out = response.getWriter();
        processRequest(request, response);
        try {
            this.rowsAffected = question.save();
        } catch (SQLException ex) {
            Logger.getLogger(CreateQuestion.class.getName()).log(Level.SEVERE, null, ex);
            out.println(ex.toString());
        } catch (Exception ex) {
            Logger.getLogger(CreateQuestion.class.getName()).log(Level.SEVERE, null, ex);
            out.println(ex.toString());
        }
        out.println(rowsAffected + " rows affected.");
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Processes creation of a question with answers.";
    }// </editor-fold>

    private void processInputParameter(String parameterName, String parameterValue)
    {

        if(parameterName.equals("quizID"))
        {
            /* Validate that quizID exists */
            this.question.setQuizID(Integer.parseInt(parameterValue));
        }
        else if(parameterName.equals("questionBody"))
        {
            /* Validate valid question body */
            this.question.setQuestionBody(parameterValue);
        }
        else if(!parameterName.contains("correct")
                && parameterName.contains("answer")
                && endsWithNumber(parameterName))
        {
            /* validate parameter value */
            /* get answer number */
            int answerNum = Integer.parseInt(Util.findIndexEnd(parameterName,"answer"));

            QuestionAnswer newAnswer = new QuestionAnswer(parameterValue);

            this.question.getQuestionAnswers().add(answerNum, newAnswer);
        }
        else if(parameterName.contains("correctAnswer")
                && endsWithNumber(parameterName)
                && parameterValue.equals("on"))
        {
            int answerNum = Integer.parseInt(Util.findIndexEnd(parameterName, "correctAnswer"));

            this.question.getQuestionAnswers().get(answerNum).setIsACorrectAnswer(true);
        }

    }

    private boolean endsWithNumber(String parameterName)
    {
        if( parameterName.endsWith("1") ||
            parameterName.endsWith("2") ||
            parameterName.endsWith("3") ||
            parameterName.endsWith("4") ||
            parameterName.endsWith("5") ||
            parameterName.endsWith("6") ||
            parameterName.endsWith("7") ||
            parameterName.endsWith("8") ||
            parameterName.endsWith("9") ||
            parameterName.endsWith("2") ||
            parameterName.endsWith("0") )
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}
