<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>黄冈师范学院宿舍管理系统</title>
</head>
<body>
<header class="publicHeader">
    <h1>超市订单管理系统</h1>   <%--${userSession.userName}--%>
    <div class="publicHeaderR">      <%--<%=request.getSession().getAttribute("userName")%>--%>
        <p><span>尊敬的</span><span style="color: #fff21b"> ${USER_SESSION.userName} </span> , 欢迎你！</p>
        <a href="${pageContext.request.contextPath }/jsp/logout.do">退出</a>
    </div>
</header>

<div class="left">
    <h2 class="leftH2"><span class="span1"></span>功能列表 <span></span></h2>
    <nav>
        <ul class="list">
            <li><a href="${pageContext.request.contextPath }/404">宿舍信息查询</a></li>
            <li><a href="${pageContext.request.contextPath }/jsp/pwdmodify.jsp">密码修改</a></li>
            <li><a href="${pageContext.request.contextPath }/jsp/logout.do">退出系统</a></li>
        </ul>
    </nav>
</div>
