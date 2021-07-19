<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>密码修改页面</title>
</head>
<body>
    <form action="${pageContext.request.contextPath}/jsp/user.do" method="post">
        <input type="hidden" name="method" value="savepwd">
        <span style="color: red;">${message}</span><br>
        旧密码:<input type="password" name="oldPassword"><br>
        新密码:<input type="password" name="newPassword"><br>
        重复新密码:<input type="password" name="newPassword"><br>
        <input type="submit" name="save" value="提交">
    </form>
</body>
</html>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/pwdmodify.js"></script>