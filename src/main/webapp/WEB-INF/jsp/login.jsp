<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<h1>PetClinic Login Page</h1>
<form action="login" method="post">
	Username:<input type="text" name="username" /><br/>
	Password:<input type="password" name="password"><br/>
	Remember Me:<input type="checkbox" name="remember-me"/><br/>
	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
	<input type="submit" value="login" />
	<font color="red">
		<c:if test="${not empty param.loginFailed}">
			<c:out value="login failed, incorrect Username or Passwprd"></c:out>
		</c:if>
	</font>
	 
</form>

</body>
</html>