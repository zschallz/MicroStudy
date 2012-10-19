/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package zimm.microstudy.QuizObjects.views;

import zimm.microstudy.QuizObjects.Question;
import zimm.microstudy.QuizObjects.QuestionAnswer;

/**
 *
 * @author zschallz
 */
public class QuestionView implements QuizObjectView {

    Question question;
    ViewType viewType;


    public QuestionView(Question question, ViewType viewType)
    {
        this.question = question;
        this.viewType = viewType;
    }

    @Override
    public String generateView()
    {
        if( viewType.equals(ViewType.Webpage) )
            return generateWebpageView();
        else if( viewType.equals(ViewType.Email) )
            return generateEmailView();
        else
            throw new UnsupportedOperationException("Invalid view type provided");

    }

    private String generateWebpageView()
    {
        StringBuilder sb = new StringBuilder();

        sb.append("<ol>\n"); // Start of question Ordered List
        sb.append("     <li class=\"question\"> ").append(question.getQuestionBody()).append("\n"); // Single question list item containing question body
        sb.append("         <ol> "); // Start of answer Ordred List
        for( QuestionAnswer answer : question.getQuestionAnswers() )
        {
            /* create list items for each answer for the question */
            sb.append("                 <li class=\"answer\"> ").append(answer.getAnswerBody()).append(" </li>\n");
        }
        sb.append("         </ol>\n"); // End of answer Ordered List
        sb.append("     </li>\n"); // End of Question List Item
        sb.append("</ol>"); // End of question Ordered List

        return sb.toString();
    }

    private String generateEmailView()
    {
        StringBuilder sb = new StringBuilder();

        String questionStyle = "list-style-type: decimal;";
        String answerStyle = "list-style-type: upper-alpha;";

        sb.append("</head><body>");
        sb.append("<ol>\n"); // Start of question Ordered List
        sb.append("     <li style=\"" + questionStyle + "\"> ").append(question.getQuestionBody()).append("\n"); // Single question list item containing question body
        sb.append("         <ol> "); // Start of answer Ordred List
        for( QuestionAnswer answer : question.getQuestionAnswers() )
        {
            /* create list items for each answer for the question */
            sb.append("                 <li class=\"" + answerStyle + "\"> ").append(answer.getAnswerBody()).append(" </li>\n");
        }
        sb.append("         </ol>\n"); // End of answer Ordered List
        sb.append("     </li>\n"); // End of Question List Item
        sb.append("</ol>"); // End of question Ordered List

        sb.append("</body></html>");
        return sb.toString();
    }


}
