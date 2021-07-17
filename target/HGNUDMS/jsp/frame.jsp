<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Content</title>
</head>
<body>
    <h1>你好亲爱的<span style="color: #7395ff">${USER_SESSION.userName}</span> Welcome to Content</h1>
    <br>

    <a href="${pageContext.request.contextPath }/jsp/logout.do">注销</a>
</body>
</html>
