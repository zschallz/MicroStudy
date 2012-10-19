/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package zimm.microstudy.Notification;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import zimm.microstudy.QuizObjects.Account;
import zimm.microstudy.QuizObjects.Question;
import zimm.microstudy.QuizObjects.QuizObject;
import zimm.microstudy.QuizObjects.views.QuestionView;
import zimm.microstudy.QuizObjects.views.QuizObjectView;
import zimm.microstudy.QuizObjects.views.ViewType;
import zimm.microstudy.db.DBConnect;

/**
 *
 * @author zschallz
 */
public class EmailNotification implements Notification, QuizObject {


    int notificationID = -1;

    /* Email info */
    private final String smtpHost = "smtp.gmail.com";
    private final boolean smtpTLS = true;
    private final int smtpPort = 587;
    private final boolean smtpAuth = true;
    private final boolean smtpSSL = true;
    private final String smtpUser = "projecttinystudy@gmail.com";
    private final String smtpPassword = "tghwanoi";

    private Question question;
    boolean isSaved = false;

    EmailNotification(Question question)
    {
        this.question = question;
    }

    public boolean process()
    {
        try {
            if (send()) {
                save();
            }
            else
            {
                return false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(EmailNotification.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } catch (ClassNotFoundException ex) {
           Logger.getLogger(EmailNotification.class.getName()).log(Level.SEVERE, null, ex);
           return false;
        } catch (InstantiationException ex) {
           Logger.getLogger(EmailNotification.class.getName()).log(Level.SEVERE, null, ex);
           return false;
        } catch (IllegalAccessException ex) {
           Logger.getLogger(EmailNotification.class.getName()).log(Level.SEVERE, null, ex);
           return false;
        }
        
        return true;
    }

    private boolean send() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException
    {
        Message msg = createNewMessage();

        try {
        
            msg.setFrom(new InternetAddress(smtpUser));
            msg.setRecipient(Message.RecipientType.TO, new InternetAddress(getToAddress()));
            msg.setSubject("Your Tinystudy Quiz Question");
            
            QuestionView questionView = new QuestionView(question,ViewType.Email);

            msg.setContent(questionView.generateView(), "text/html");
            // Send the message.
            
            Transport.send(msg);
        } catch (MessagingException e) {
            String test = System.getProperties().getProperty("mail.smtp.host");
            Logger.getLogger(EmailNotification.class.getName()).log(Level.SEVERE, "Exception while sending email: " + e);
            return false;
        }
        
        return true;
    }



    private Message createNewMessage() {
            Session session = getMailingSession();
            return new MimeMessage(session);
    }

    private Session getMailingSession() {
            Properties properties = setSMTPProperties();

            Authenticator auth = new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(smtpUser, smtpPassword);
                    }
            };

            return Session.getDefaultInstance(properties, auth);
    }

    private Properties setSMTPProperties() {
            Properties props = new Properties();
            props.put("mail.smtp.host", smtpHost);
            props.put("mail.smtp.starttls.enable", smtpTLS);
            props.put("mail.smtp.port", smtpPort);
            props.put("mail.smtp.auth", smtpAuth);
            props.put("mail.smtp.ssl", smtpSSL);
            return props;
    }

    private String getToAddress() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException
    {
        return Account.GetEmailByQuizID(question.getQuizID());
    }

    public int save() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException
    {
        ResultSet dbSaveNotificationResult = null;
        dbSaveNotificationResult = DBConnect.executeSP("createNotification('email'," + question.getQuizID() + "," + question.getQuestionID() + ")");

        dbSaveNotificationResult.next();

        this.notificationID = dbSaveNotificationResult.getInt("LAST_INSERT_ID()");
        this.isSaved = true;
        
        dbSaveNotificationResult.close();

        // return number of rows affected
        return 1;
    }

    public boolean isSaved()
    {
        return this.isSaved;
    }

    public QuizObjectView getView()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
