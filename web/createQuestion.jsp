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
        <title>Create question</title>
        <link rel="stylesheet" href="style/style.css" type="text/css">
    </head>
    <body>
        <form method="POST" action="./CreateQuestion">
            Quiz ID: <input name="quizID" size="3"/><br/><br/>
            Question: <textarea rows="5" cols="100" name="questionBody"></textarea>
            <br/><br/>

            <ol>
                <li class="answer">
                    <textarea rows="2" cols="50" name="answer0"></textarea>
                    <input type="checkbox" name="correctAnswer0"/> <label for="correctAnswer0">Correct</label></li>
                <li class="answer">
                    <textarea rows="2" cols="50" name="answer1"></textarea>
                    <input type="checkbox" name="correctAnswer1"/> <label for="correctAnswer1">Correct</label></li>
                <li class="answer">
                    <textarea rows="2" cols="50" name="answer2"></textarea>
                    <input type="checkbox" name="correctAnswer2"/> <label for="correctAnswer2">Correct</label></li>
                <li class="answer">
                    <textarea rows="2" cols="50" name="answer3"></textarea>
                    <input type="checkbox" name="correctAnswer3"/> <label for="correctAnswer3">Correct</label></li>
                <li class="answer">
                    <textarea rows="2" cols="50" name="answer4"></textarea>
                    <input type="checkbox" name="correctAnswer4"/> <label for="correctAnswer4">Correct</label></li>
                <li class="answer">
                    <textarea rows="2" cols="50" name="answer5"></textarea>
                    <input type="checkbox" name="correctAnswer5"/> <label for="correctAnswer5">Correct</label></li>
                <li class="answer">
                    <textarea rows="2" cols="50" name="answer6"></textarea>
                    <input type="checkbox" name="correctAnswer6"/> <label for="correctAnswer6">Correct</label></li>
                <li class="answer">
                    <textarea rows="2" cols="50" name="answer7"></textarea>
                    <input type="checkbox" name="correctAnswer7"/> <label for="correctAnswer7">Correct</label></li>
                <li class="answer">
                    <textarea rows="2" cols="50" name="answer8"></textarea>
                    <input type="checkbox" name="correctAnswer8"/> <label for="correctAnswer8">Correct</label></li>
                <li class="answer">
                    <textarea rows="2" cols="50" name="answer9"></textarea>
                    <input type="checkbox" name="correctAnswer9"/> <label for="correctAnswer9">Correct</label></li>
            </ol>

            <input type="submit"/>
        </form>
    </body>
</html>
