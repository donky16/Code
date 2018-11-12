<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Login</title>
</head>
<body>
	<form action="<%=path%>/LoginServlet" method="post">
		<table>
			<tr>
				<td>username:<input type="text" name="username" id="username" /></td>
			</tr>
			<tr>
				<td>password:<input type="password" name="password"	id="password" /></td>
			</tr>
			<tr>
				<td><input type="submit" name="Submit" value="login" /></td>
			</tr>	
		</table>
	</form>
	<p>No user registered!!!</p>
	<form action="register.jsp" method="post">
		<input type="submit" value="register">
	</form>
</body>
</html>