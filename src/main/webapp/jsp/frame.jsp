<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/jsp/common/head.jsp"%>
<div class="right">
    <img class="wColck" src="${pageContext.request.contextPath }/images/clock.jpg" alt=""/>
    <div class="wFont">
        <h2>${USER_SESSION.userName }</h2>
        <p>黄冈师范学院宿舍管理系统!</p>
    </div>
</div>
</section>
<%@include file="/jsp/common/foot.jsp" %>