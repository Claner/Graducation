<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<body>
<h2>Hello World</h2>
<h1>单挑呀</h1>
<h1>查成绩</h1>
<form action="${pageContext.request.contextPath}/Test/test" method="post">
    <%--账号：<input type="text" name="student_num"><br>--%>
    <%--密码：<input type="password" name="password"><br>--%>
    <input type="submit" value="登陆">
</form>
</body>
</html>
