<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Login</title>
</head>
<body>
	<%@include file="anon.jsp" %>
	<h4>一切未认证的请求，都会被重定向到这个界面</h4>
		<form action="${pageContext.request.contextPath }/user/loginByShiro" method="post">	
			username: <input type = "text" name = "username" /> <br /><br/>
			password: <input type = "password" name = "password"> <br /> <br/>
			<input type = "submit" value = "Login">
		</form>
</body>
</html>