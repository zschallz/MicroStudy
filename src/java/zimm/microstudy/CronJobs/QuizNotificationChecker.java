/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package zimm.microstudy.CronJobs;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import zimm.microstudy.Notification.Notification;
import zimm.microstudy.Notification.NotificationFactory;
import zimm.microstudy.QuizObjects.Question;
import zimm.microstudy.db.DBConnect;
import zimm.microstudy.Notification.QuizNotifyMethod;

/**
 *
 * @author zschallz
 */
public class QuizNotificationChecker {

    public static int check() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException
    {
        ResultSet quizzesToCheck = DBConnect.executeSP("showQuizzesNotNotified()");

        int quizID;
        QuizNotifyMethod notifyMethod;
        List<Notification> notificationList = new ArrayList<Notification>();

        while(quizzesToCheck.next())
        {
            quizID = quizzesToCheck.getInt("pkID");
            notifyMethod = QuizNotifyMethod.valueOf(quizzesToCheck.getString("notifyMethod"));
            notificationList.add(NotificationFactory.getNotification(notifyMethod,
                    Question.GetRandomQuestion(quizID)));
        }

        int success = 0;
        
        for(Notification notification : notificationList)
        {
            if(notification.process())
                success++;
        }

        quizzesToCheck.close();

        return success;
    }
}
