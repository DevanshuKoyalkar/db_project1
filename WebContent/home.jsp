<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="database.DataAccess" %>
<%@ page import="database.Admin" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Welcome to CST Int'l Airport</title>
</head>
<body>
Here is the flight information for the next few hours

<%
if(session.getAttribute("username")==null){
	
}
else{	
	session.removeAttribute("item");
	session.removeAttribute("username");
	session.removeAttribute("password");
	session.invalidate();
}

      
%>
<br>
<a href="login.jsp">Click here to login</a>

</body>
</html>