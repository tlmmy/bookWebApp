<%-- 
    Document   : footer
    Created on : Oct 17, 2016, 9:16:55 PM
    Author     : Timothy
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
Object objCount = application.getAttribute("user");
String users = (objCount == null) ? "1" : objCount.toString();

%>
<div class="footer" style="color:black">Application Launched ${date}, Current Users: <%= users %> </div>
