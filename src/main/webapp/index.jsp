<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>login</title>
</head>
<body>
        <div>
            <form action="${pageContext.request.contextPath}/login.do" method="post">
                用户名: <input type="text" name="userName" placeholder="Enter your count"> <br>
                密 码： <input type="password" name="userPassword" placeholder="Enter your password"><br>
                <input type="submit">
            </form>
        </div>
</body>
</html>
