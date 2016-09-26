<%-- 
    Document   : authorList
    Created on : Sep 18, 2016, 10:03:28 AM
    Author     : Tim
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%
    Object obj = request.getAttribute("authors");
    if (obj == null) {
        response.sendRedirect("authors");
    }
%>


<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="style/bookStyle.css" rel="stylesheet" type="text/css"/>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">
        <title>Author List</title>
    </head>
    <body>
        <h1 class="header">List of Authors</h1>
        <div class="container" id="authorTable">

            <table class="table table-hover">
                <th class="tableHead">Author ID</th>
                <th class="tableHead">Author Name</th>
                <th class="tableHead">Date Added</th>
                    <c:forEach var="author" items="${authors}">
                    <tr>
                        <td>${author.getAuthorId()}</td>
                        <td>${author.getAuthorName()}</td>
                        <td>${author.getDateAdded()}</td>

                    </tr>
                </c:forEach>
            </table>  


            
        </div>
        <a href="index.jsp">Home</a>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
    </body>
</html>
