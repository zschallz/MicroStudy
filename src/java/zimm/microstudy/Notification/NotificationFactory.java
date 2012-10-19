/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package zimm.microstudy.Notification;

import zimm.microstudy.QuizObjects.Question;

/**
 *
 * @author zschallz
 */
public class NotificationFactory {

    public static Notification getNotification(QuizNotifyMethod method, Question question)
    {
//        if(method.equals(QuizNotifyMethod.Email))
            return new EmailNotification(question);
    }
}
