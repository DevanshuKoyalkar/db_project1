<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="database.DataAccess" %>
<%@ page import="database.Login" %>
<%@ page import="java.util.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>
Login Page
</title>
<% 
if ("POST".equalsIgnoreCase(request.getMethod())) {
%>

<script type="text/javascript">
function checkregistration(){
	//checks for password match, username empty, and password length>6
	var p1=document.registration.password.value;
	var p2=document.registration.passwordcheck.value;
	var name=document.registration.username.value;
	var id=document.registration.userid.value;
	var type=document.registration.name.value;
	if (name==null || name==""){  
		  alert("Name can't be blank");  
		  return false;  
	}
	if(p1.length<6){
		alert("Password must be atlest 6 characters long");
		return false;
	}
	<%
	
		String type=request.getParameter("item");
		List<String> userids=Login.getid(type);
		boolean ispresent=false;
		String id=request.getParameter("userid");
		for(String temp:userids){
			if(temp.equalsIgnoreCase(id))ispresent=true;break;
		}
	
	%>
	if(<%=ispresent%>){
		alert("username already taken");
	}
	if(p1==p2){
		return true;
	}
	else{
		alert("password must be same!!");
		return false;
	}
}
</script>
<%
}
%>
</head>
<body>
<%
	if ("POST".equalsIgnoreCase(request.getMethod())) {
		if (request.getParameter("login") != null) {
			String username=request.getParameter("username");
		    String password=request.getParameter("password");
		    String option=request.getParameter("item");
		    out.print("You selected the to login with"+ option+"<br>");
		    out.print("Hi "+username);
		    
		    if(option.equalsIgnoreCase("admin")){
		    	System.out.println("came here");
		    	if(Login.admin_access(username,password)){
		    		response.sendRedirect("admin.jsp");
		    	}
		    	else{
		    		
		    	}
		    }
		    else if(option.equalsIgnoreCase("employee") ){
		    	if(Login.employee_access(username,password)){
		    		response.sendRedirect("employee.jsp");
		    	}
		    	else{
		    		
		    	}
		    }
			else if(option.equalsIgnoreCase("airline")){
				System.out.println("came here airline");
				if(Login.airline_access(username,password)){
		    		response.sendRedirect("airline.jsp");
		    	}
		    	else{
		    		
		    	}
			}
			else{
				if(Login.company_access(username,password)){
		    		response.sendRedirect("company.jsp");
		    	}
		    	else{
		    		
		    	}
			}
		    /* if((username.equals("kranthi") && password.equals("vegulla"))){ 
		     	session.setAttribute("username",username);
		     	response.sendRedirect("home.jsp"); 
		     }
		    else
		     response.sendRedirect("error.jsp");  */
		}//login is covered till here
		else{
			String userid=request.getParameter("userid");
			String username=request.getParameter("username");
		    String password=request.getParameter("password");
		    String option=request.getParameter("item");
		    out.print("you came here");
		    if(option.equalsIgnoreCase("airline")){
		    	//execute adding into pending with default entries
		    	//anybody can register
		    }
		    else if(option.equalsIgnoreCase("enterprise") ){
		    	
		    }
		}
	}
	else{
 %>
 <h3>Login here</h3> <br/>
<form action="login.jsp" method="post">
	<select name="item">
	  <option value="admin">Admin</option>
	  <option value="employee">Employee</option>
	  <option value="airline">Airline</option>
	  <option value="enterprise">Enterprise</option>
	</select>
	<br />
	Username:<input type="text" name="username"/> <br />
	Password:<input	type="password" name="password"/><br />
	<input type="submit" name="login" value="Submit"></input>
</form>
<br><br>
If not registered, Regiter here<br>
<form action="login.jsp" name="registration" method="post" onsubmit="return checkregistration()">
	<select name="item">
	  <option value="airline">Airline</option>
	  <option value="enterprise">Enterprise</option>
	</select>
	<br />
	UseriD:<input type="text" name="userid"/><br>
	Username:<input type="text" name="username"/><br />
	Password:<input	type="password" name="password"/> <br />
	Retype Password:<input type="password" name="passwordcheck"/><br>
	<input type="submit" name="register" value="Submit"/>
</form>
<br/>
 <% 
 }
 %>
</body>
</html>