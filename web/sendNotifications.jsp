<%-- 
    Document   : sendNotifications
    Created on : May 25, 2011, 12:20:04 AM
    Author     : zschallz
--%>

<%@page import="zimm.microstudy.CronJobs.QuizNotificationChecker"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Notifications</title>
    </head>
    <body>
        <%

            int notificationsSent = QuizNotificationChecker.check();

            out.print(notificationsSent + " notifications sent.");

        %>
    </body>
</html>
