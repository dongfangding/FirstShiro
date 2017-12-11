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
		
		
	<h4>简单说一下shiro的执行步骤</h4>
	<h5>1. 这里请求一个controller。用来接受前端填写的用户名和密码， 然后封装用户名和密码来做登陆验证。具体代码请看com.ddf.training.controller.UserController</h5>
	<h5>2. 执行到Subject.login方法之后，shiro会自动根据在applicationContext.xml中配置的realms来查找对应的Realm来进行验证，请查看方法com.ddf.training.shiro.UserRealm.doGetAuthenticationInfo(AuthenticationToken)</h5>
	<h5>3. 登陆成功后， 有一些资源是需要权限的。或者匿名的。这一块的代码在初始化应用程序时做过，具体请看applicationContext.xml的filterChainDefinitionMap这个bean</h5>
	<h5>4. 如果需要对有权限的资源进行访问， shiro会自动调用Realm中的com.ddf.training.shiro.UserRealm.doGetAuthorizationInfo(PrincipalCollection)来进行权限的存入和判别</h5>
	<h5>5. 如果权限匹配，则返回对资源的访问，如果没有对应的权限，则返回shiroFilter中配置的unauthorizedUrl对应的资源界面</h5>
	<h5>6. 程序说明</h5>
	<h6></h6>
	<h6>com.ddf.training.bean.FilterChainDefinitionMapBuilder里初始化了一些资源对应的权限列表</h6>
	<h6>com.ddf.training.DAO.UserDao里初始化了一些用户和角色 </h6>
</body>
</html>