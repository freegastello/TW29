<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
	<title>Редактировать юзера</title>
</head>
<body>
<c:forEach var="userMap" items="${requestScope.userMap}">
	<p>users_id: ${userMap.users_id}</p>
	<form method="post" action="<c:url value='/update'/>">
		<label>Новое имя:	<input type="text" name="name"      value="${userMap.name}"		/></label><br>
		<label>Новый логин:	<input type="text" name="login"     value="${userMap.login}"	/></label><br>
		<label>Новый пароль:<input type="text" name="password"  value="${userMap.password}" /></label><br>
		<input type="number" hidden name="users_id" value="${userMap.users_id}"/>
		<input type="submit" value="Ok" name="Ok"><br>
	</form>
</c:forEach>
</body>
</html>
