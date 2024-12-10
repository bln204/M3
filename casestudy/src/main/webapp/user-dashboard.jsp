<%--
  Created by IntelliJ IDEA.
  User: lenam
  Date: 12/8/2024
  Time: 1:08 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.example.casestudy.model.User" %>
<%
    User user = (User) session.getAttribute("user");
    if (user == null || !"user".equals(user.getRole().getRoleName())) {
        response.sendRedirect("login.jsp");
        return;
    }
%>
<h1>Welcome, User!</h1>
<a href="logout">Logout</a>
