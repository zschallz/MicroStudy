<%-- 
    Document   : index
    Created on : May 19, 2011, 10:39:07 PM
    Author     : zschallz
--%>

<%@page import="zimm.microstudy.QuizObjects.views.QuestionView"%>
<%@page import="zimm.microstudy.QuizObjects.views.ViewType"%>
<%@page import="zimm.microstudy.QuizObjects.Question"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Single Question</title>
        <link rel="stylesheet" href="style/style.css" type="text/css">
    </head>
    <body>
        <%
            Question question = Question.GetRandomQuestion(1);

            out.print(question.getView().generateView());
        %>
    </body>
</html>
