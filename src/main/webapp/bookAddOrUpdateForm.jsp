<%-- 
    Document   : bookAddOrUpdateForm
    Created on : Nov 2, 2016, 9:35:36 PM
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
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">
        <title>Add or Update</title>
    </head>
    <body>
        <!--
        use if logic using a book being null or not to set the values of the form
        -->
        <form id="addOrUpdate" name="addOrUpdateForm" method="POST" action="<%= response.encodeURL("books")%>">
            <h3>Add/Edit Book</h2>
                <c:choose>
                    <c:when test="${book != null}">
                        <table>
                            <tr>
                                <td>Book ID:</td>
                                <td><input type ="text" id="bookId" name="bookId" value="${book.getBookId()}" readOnly="true"/></td>
                            </tr>
                            <tr>
                                <td>Title:</td>
                                <td><input type ="text" id="title" name="title" value="${book.getTitle()}"/></td>
                            </tr>
                            <tr>
                                <td>ISBN:</td>
                                <td><input type ="text" id="isbn" name="isbn" value="${book.getIsbn()}"/></td>
                            </tr>
                            <tr>
                                <td>Author:</td>
                                <td><select name="authorId">
                                        <c:forEach var="author" items="${authorList}">
                                            <option value="${author.getAuthorId()}" 
                                                    <c:if test="${author.getAuthorId() == book.authorId.getAuthorId()}"> 
                                                        selected="selected"</c:if>>
                                                    ${author.getAuthorName()}</option>
                                            </c:forEach>
                                    </select>
                                </td>

                            </tr>
                        </table>
                        <input type="submit" id="userActionCreate" name="userAction" value="Submit"/>
                    </c:when>
                    <c:otherwise>

                        <table>
                            <tr>
                                <td>Book ID:</td>
                                <td><input type ="text" id="bookId" name="bookId" value=""  readOnly="true"/></td>
                            </tr>
                            <tr>
                                <td>Title:</td>
                                <td><input type ="text" id="title" name="title" value=""/></td>
                            </tr>
                            <tr>
                                <td>ISBN:</td>
                                <td><input type ="text" id="isbn" name="isbn" value=""/></td>
                            </tr>
                            <td>Author:</td>
                            <td><select name="authorId">
                                    <c:forEach var="author" items="${authorList}">
                                        <option value="${author.getAuthorId()}">${author.getAuthorName()}</option>
                                    </c:forEach>
                                </select>
                            </td>
                        </table>
                        <input type="submit" id="userActionCreate" name="userAction" value="Create"/>
                    </c:otherwise>
                </c:choose>

        </form>
        <a href="books">Back to List</a>
        <jsp:include page="footer.jsp" />
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>

    </body>

</html>
