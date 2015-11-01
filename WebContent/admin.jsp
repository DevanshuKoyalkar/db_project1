<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="database.DataAccess" %>
<%@ page import="database.Admin" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<style>
table,th,td{
	border: 1px solid black;
}
</style>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
<title>Welcome Admin</title>
<h1>Welcome Admin</h1>
<a href="home.jsp">click here to logout</a>
</head>
<body>
<div class="table-responsive">
<br><br>
Pending requests for shops are as follows<br>
<%
if ("POST".equalsIgnoreCase(request.getMethod())) {
	String request_id=request.getParameter("request_id");
	String option=request.getParameter("option");
	//out.print(option+"<br>"+request_id+"<br><br>");
	Admin.updatePending(request_id, option);
}

{
List<String> shops=Admin.getShops();//airline_id
if(shops.size()==0){
	out.print("You have no shops booked<br>");
}
else{
	out.print("<table class='table'>");
	out.print("<tr><th>Request_Id</th><th>Shop_Id</th><th>Company_id</th><th>Start_date</th><th>End_date</th><th>Accept/Reject</th><th>Submit</th></tr>");
	int count=0;
	for(String house:shops){
		if(count%6==0){
			out.print("<tr>");
			out.print("<form action='admin.jsp' method='post'>");
			out.print("<td>  <input type='hidden' name='request_id' value='" + house + "'>" + house+"</td>");
		}
		else out.print("<td>"+ house +"</td>");
		count++;
		if(count%6==5){
			count++;
			out.print("<td>");
			out.print("<input type='radio' name='option' value='accept'>accept");
			out.print("<input type='radio' name='option' value='reject'>reject");
			out.print("</td>");
			out.print("<td>");
			out.print("<input type='submit' id='1" + count/6+"'>" +"</input>");
			out.print("</td>");
			out.print("</form>");
			out.print("</tr>");
		}
	}
	out.print("</table>");
}

}
%>
<br><br>
Pending requests for warehouses are as follows<br>
<%
{
List<String> shops=Admin.getWarehouses();//airline_id
if(shops.size()==0){
	out.print("You have no shops booked<br>");
}
else{
	out.print("<table class='table'>");
	out.print("<tr><th>Request_Id</th><th>Warehouse_Id</th><th>Company_id</th><th>Start_date</th><th>End_date</th><th>Accept/Reject</th><th>Submit</th></tr>");
	int count=0;
	for(String house:shops){
		if(count%6==0){
			out.print("<tr>");
			out.print("<form action='admin.jsp' method='post'>");
			out.print("<td>  <input type='hidden' name='request_id' value='" + house + "'>" + house+"</td>");
		}
		else out.print("<td>"+ house +"</td>");
		count++;
		if(count%6==5){
			count++;
			out.print("<td>");
			out.print("<input type='radio' name='option' value='accept'>accept");
			out.print("<input type='radio' name='option' value='reject'>reject");
			out.print("</td>");
			out.print("<td>");
			out.print("<input type='submit' id='1" + count/6+"'>" +"</input>");
			out.print("</td>");
			out.print("</form>");
			out.print("</tr>");
		}
	}
	out.print("</table>");
}

}
%>
<br><br>
Pending requests for gates are as follows<br>
<%
{
List<String> shops=Admin.getGates();//airline_id
if(shops.size()==0){
	out.print("You have no shops booked<br>");
}
else{
	out.print("<table class='table'>");
	out.print("<tr><th>Request_Id</th><th>_Id</th><th>Company_id</th><th>Start_date</th><th>End_date</th><th>Accept/Reject</th><th>Submit</th></tr>");
	int count=0;
	for(String house:shops){
		if(count%6==0){
			out.print("<tr>");
			out.print("<form action='admin.jsp' method='post'>");
			out.print("<td>  <input type='hidden' name='request_id' value='" + house + "'>" + house+"</td>");
		}
		else out.print("<td>"+ house +"</td>");
		count++;
		if(count%6==5){
			count++;
			out.print("<td>");
			out.print("<input type='radio' name='option' value='accept'>accept");
			out.print("<input type='radio' name='option' value='reject'>reject");
			out.print("</td>");
			out.print("<td>");
			out.print("<input type='submit' id='1" + count/6+"'>" +"</input>");
			out.print("</td>");
			out.print("</form>");
			out.print("</tr>");
		}
	}
	out.print("</table>");
}

}
%>
</div>

</body>
</html>