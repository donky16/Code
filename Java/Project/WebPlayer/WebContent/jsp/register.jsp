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
	<form action="<%=path%>/RegisterServlet" method="post">
		<table>
			<tr>
				<td>username:<input type="text" name="username" id="username" /></td>
			</tr>
			<tr>
				<td>password:<input type="password" name="password_1"	id="password_1" /></td>
			</tr>
			
			<tr>
				<td>password:<input type="password" name="password_2"	id="password_2" /> repeat</td>
			</tr>
			<tr>
				<td><input type="submit" name="Submit" value="register" /></td>
			</tr>	
		</table>
	</form>
</body>
</html>