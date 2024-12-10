<%--
  Created by IntelliJ IDEA.
  User: lenam
  Date: 12/8/2024
  Time: 1:07 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>LOGIN</title>
</head>
<body>
<form action="login" method="post">
    <input type="text" name="username" placeholder="Username" required>
    <input type="password" name="password" placeholder="Password" required>
    <button type="submit">Login</button>
    <p style="color: red;"><%= request.getAttribute("errorMessage") %></p>
</form>
</body>
</html>
