<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>密码修改页面</title>
</head>
<body>
    <form action="${pageContext.request.contextPath}/jsp/user.do" method="post">
        <span style="color: red;">${message}</span><br>
        新密码:<input type="password" name="newpassword"><br>
        重复新密码:<input type="password" name="renewpassword">
        <input type="submit" name="submit">
    </form>
</body>
</html>
