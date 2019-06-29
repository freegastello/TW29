<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
	<title>Title</title>
</head>
<body>

<div id="one" style="border: 1px solid yellow;">
	<h1>Создание CRUD приложения</h1>
</div>
<div>
	<h3><c:out value="${requestScope.error}"/></h3>
</div>
<div id="three" style="border: 2px solid black;">
	<h2>Создание нового пользователя</h2><br/>
	<form method="post" action="<c:url value='/add_user'/>">

		<label><input type="text" name="name" value="${requestScope.name}"></label>Имя<br>
		<label><input type="text" name="login" value="${requestScope.login}"></label>Логин<br>
		<label><input type="text" name="password" value="${requestScope.password}"></label>Пароль<br>
		<input type="submit" value="Ok" name="Ok"><br>
		<br>
	</form>
</div>

<div id="two" border="2" style="border: 1px solid red;" width="50%">
	<h2>Список пользователей:</h2>

	<table border="0" align="left" cellspacing="1" bgcolor="#000000" width="30%">
		<tr bgcolor="#FFFFFF" height="40px">
			<td align="center">users_id</td>
			<td align="center">Имя:</td>
			<td align="center">Логин:</td>
			<td align="center">Пароль:</td>
			<td align="center">Роль:</td>
			<td align="center"></td>
			<td align="center"></td>
		</tr>
		<c:forEach var="user" items="${requestScope.users}">
			<tr bgcolor="#FFFFFF" align="center">
				<td><c:out value="${user.users_id}"/></td>
				<td align="left"><c:out value="${user.name}"/></td>
				<td><c:out value="${user.login}"/></td>
				<td><c:out value="${user.password}"/></td>
				<td><c:out value="${user.role}"/></td>
				<td>
					<form method="get" action="<c:url value='/update'/>">
						<input type="number" hidden name="users_id" value="${user.users_id}"/>
						<input type="submit" value="Редактировать"/>
					</form>
				</td>
				<td>
					<form method="post" action="<c:url value='/delete'/>">
						<input type="number" hidden name="users_id" value="${user.users_id}"/>
						<input type="submit" name="delete" value="Удалить"/>
					</form>
				</td>
			</tr>
		</c:forEach>
	</table>
</div>

</body>
</html>
