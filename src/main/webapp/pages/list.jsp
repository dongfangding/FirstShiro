<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>List page</title>
</head>
<body>

	<%@include file="authc.jsp" %>
	List Page
	
	<h4><a href="${pageContext.request.contextPath }/pages/admin.jsp">to admin page</a></h4>
	<h4><a href="${pageContext.request.contextPath }/pages/user.jsp">to user page</a></h4>
	<h4><a href="${pageContext.request.contextPath }/pages/downloadUserIcon.jsp">to download page</a></h4>
</body>
</html>