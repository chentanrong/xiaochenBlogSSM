<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--
  Created by IntelliJ IDEA.
  User: chentanrong
  Date: 2018/12/25
  Time: 10:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>表单处理</title>
</head>
<body>
    <form:form method="POST" action="/addStudent">
        <table>
            <tr>
                <td><form:label path="name">名字：</form:label></td>
                <td><form:input path="name"/></td>
            </tr>
            <tr>
                <td><form:label path="age">年龄：</form:label></td>
                <td><form:input path="age"/></td>
            </tr>
            <tr>
                <td><form:label path="id">名字：</form:label></td>
                <td><form:input path="id"/></td>
            </tr>
            <tr>
                <td colspan="2">
                    <input type="submit" value="提交表单"/>
                </td>
            </tr>
        </table>

    </form:form>
</body>
</html>
