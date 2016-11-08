<%-- 
    Document   : addOrUpdateForm
    Created on : Oct 5, 2016, 7:58:05 PM
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
        use if logic using a author being null or not to set the values of the form
        -->
        <form id="addOrUpdate" name="addOrUpdateForm" method="POST" action="<%= response.encodeURL("authors")%>">
            <h3>Add/Edit Author</h2>
                <c:choose>
                    <c:when test="${author != null}">
                        <table>
                            <tr>
                                <td>Author ID:</td>
                                <td><input type ="text" id="authorId" name="authorId" value="${author.getAuthorId()}" readOnly="true"/></td>
                            </tr>
                            <tr>
                                <td>Author:</td>
                                <td><input type ="text" id="authorName" name="authorName" value="${author.getAuthorName()}"/></td>
                            </tr>
                            <tr>
                                <td>Books:</td>
                                <td><select>
                                        <c:forEach var="book" items="${bookSet}">
                                            <option value="${book.getBookId()}">${book.getTitle()}</option>
                                            </c:forEach>
                                        </select>
                                    </td>

                                </tr>
                            
                            </tr>
                            <tr>
                                <td>Date:</td>
                                <td><input type ="text" name="date" value="${author.getDateAdded()}" readOnly="true"/></td>
                            </tr>
                        </table>
                        <input type="submit" id="userActionCreate" name="userAction" value="Submit"/>
                    </c:when>
                    <c:otherwise>

                        <table>
                            <tr>
                                <td>Author ID:</td>
                                <td><input type ="text" id="authorId" name="authorId" value="" readOnly="true"/></td>
                            </tr>
                            <tr>
                                <td>Author:</td>
                                <td><input type ="text" id="authorName" name="authorName" value=""/></td>
                            </tr>
                            <tr>
                                <td>Date:</td>
                                <td><input type ="text" name="date" value="" readOnly="true"/></td>
                            </tr>
                        </table>
                        <input type="submit" id="userActionCreate" name="userAction" value="Create"/>
                    </c:otherwise>
                </c:choose>

        </form>
        <a href="authors">Back to List</a>
        <jsp:include page="footer.jsp" />
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>

    </body>

</html>
