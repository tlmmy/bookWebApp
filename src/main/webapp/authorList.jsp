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
    if(obj == null){
        response.sendRedirect("authors");
    }
%>


<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Author List</title>
    </head>
    <body>
        <h1>List of Authors</h1>
       <table>
           <th>Author ID</th>
           <th>Author Name</th>
           <th>Date Added</th>
            <c:forEach var="author" items="${authors}">
                <tr>
                    <td>${author.getAuthorId()}</td>
                    <td>${author.getAuthorName()}</td>
                    <td>${author.getDateAdded()}</td>
                    
                </tr>
            </c:forEach>
        </table>  
       
        
    </body>
</html>
