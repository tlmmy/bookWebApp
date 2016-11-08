<%-- 
    Document   : bookList
    Created on : Nov 2, 2016, 9:23:53 PM
    Author     : Timothy
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
        <script src="http://code.jquery.com/jquery-latest.js"></script>
        
       
        <title>Book List</title>
    </head>
    <body>
        <h1 class="header">List of Books</h1>
        <div class="container" id="listTable">
            <form id="bookForm" name="bookForm" method="POST" action="<%= response.encodeURL("books")%>">
                <table style="background-color: white"  class="table table-hover">
                    <tr style="background-color: black;color:white;">                    
                    <th class="tableHead">Book ID</th>
                    <th class="tableHead">Title</th>
                    <th class="tableHead">ISBN</th>
                    <th class="tableHead">Author</th>
                    </tr>
                        <c:forEach var="book" items="${books}">
                        <tr>
                            <td><input type="radio" name="bookPk" id="authorPk" value="${book.getBookId()}">${book.getBookId()}</td>
                            <td>${book.getTitle()}</td>
                            <td>${book.getIsbn()}</td>
                            <td>${book.authorId.getAuthorName()}</td>

                        </tr>
                    </c:forEach>

                </table>
                <input type ="submit" id="userActionAdd" name ="userAction" value="Add">&nbsp;
                <input type ="submit" id="userActionUpdate" name ="userAction" value="Update">&nbsp;
                <input type ="submit" id="userActionDelete" name ="userAction" value="Delete">&nbsp;
                <input type ="submit" id="userActionDelete" name ="userAction" value="Help">
            </form>

        <c:if test="${errMsg != null}">
            <p style="font-weight: bold;color: red;width:500px;">Sorry, data could not be retrieved:<br>
                ${errMsg}</p>
        </c:if>
            <p style="color: white">Books Created: ${bCreated} Updated: ${bUpdated} Deleted: ${bDeleted}</p>
        </div>
        <a href="index.jsp">Home</a>
        <jsp:include page="footer.jsp" />
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
        
    
</html>
