<%-- 
    Document   : authorList
    Created on : Sep 18, 2016, 10:03:28 AM
    Author     : Tim
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>




<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="style/bookStyle.css" rel="stylesheet" type="text/css"/>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css">
        <link href="https://fonts.googleapis.com/css?family=Noto+Sans" rel="stylesheet">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
        <link rel="stylesheet" href="https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/themes/smoothness/jquery-ui.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/jquery-ui.min.js"></script>
        <title>Author List</title>
    </head>
    <body>
        <h1 class="header">List of Authors</h1>
        <div class="container" id="authorTable">
            <form id="authorForm" name="authorForm" method="POST" action="authors">
                <table class="table table-hover">
                    <th class="tableHead">Author ID</th>
                    <th class="tableHead">Author Name</th>
                    <th class="tableHead">Date Added</th>
                        <c:forEach var="author" items="${authors}">
                        <tr>
                            <td><input type="radio" name="authorPk" id="authorPk" value="${author.getAuthorId()}">${author.getAuthorId()}</td>
                            <td>${author.getAuthorName()}</td>
                            <td>${author.getDateAdded()}</td>

                        </tr>
                    </c:forEach>

                </table>
                <input type ="submit" id="userActionAdd" name ="userAction" value="Add">&nbsp;
                <input type ="submit" id="userActionUpdate" name ="userAction" value="Update">&nbsp;
                <input type ="submit" id="userActionDelete" name ="userAction" value="Delete">
            </form>



        </div>
        <a href="index.jsp">Home</a>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
    </body>
</html>
