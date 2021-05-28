<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Journey</title>
</head>
<body>
<div style="padding:50px 10%; margin: 0 auto">
    <h1>Hi from view!</h1>
    <h2>Привет из вью!</h2>

    <div><a href="${pageContext.request.contextPath}/download">download 1.gpg file</a></div>
    <div><a href="${pageContext.request.contextPath}/welcome">Home page</a></div>

        <p>contextPath  - ${pageContext.request.contextPath}</p>
    <p>now is ${thetime}</p>

</div>
</body>
</html>