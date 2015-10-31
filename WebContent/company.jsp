<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="database.DataAccess" %>
<%@ page import="database.Company" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<style>
table,td,th{
	border: 1px solid black;
}
</style>



<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="bootstrap/css/bootstrap.css">
<link rel="stylesheet" type="text/css" href="bootstrap/css/bootstrap-theme.css">
<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>


<title>Welcome Company</title>
<h1>Welcome Company</h1>
</head>
<body>
Your Shop bookings are as follows<br>
<div class="table-responsive">
<%
{
List<String> shops=Company.getShops("20");//airline_id
if(shops.size()==0){
	out.print("You have no shops booked<br>");
}
else{
	out.print("<table class='table'>");
	out.print("<tr><th>Shop</th><th>Start_date</th><th>End_date</th></tr>");
	int count=0;
	for(String house:shops){
		if(count==0)out.print("<tr>");
		out.print("<td>"+house + "</td>");
		count++;
		if(count==3){out.print("</tr>");count=0;}
	}
	out.print("</table>");
}

}
%>

<br><br>
The Results for your last 5 requests are as follows<br>
<%
List<String> pending=Company.getPending("20");//company_id
if(pending.size()==0){
	out.print("you have no requests<br>");
}
else{
	out.print("<table class='table'>");
	out.print("<tr><th>Requested For</th><th>Booking_id</th><th>Status</th></tr>");
	int count=0;
	for(String house:pending){
		if(count==0)out.print("<tr>");
		out.print("<td>"+house+"</td>");
		count++;
		if(count==3){out.print("</tr>");count=0;}
	}
	out.print("</table>");
}

%>
<%
if ("POST".equalsIgnoreCase(request.getMethod())) {
	String cid="20";//change this after login is put in
    String id=request.getParameter("id");
    String start=request.getParameter("startdate");
    String end=request.getParameter("enddate");
    out.print("You Requested for Shop with ID "+id+"<br>From "+start+" to "+end);
    Company.book(cid,id,start,end);
    out.print("<br><a href='company.jsp'>book another shop</a><br>");
} 
    // Putting form here
else{
%>
<br><br>
Request for a Shop here
<br>
<form action="company.jsp" method="post">
<br>
Enter Shop Id: 
<input type="text" name="id" value=""></input><br><br>
Enter StartDate:
<input type="text" name="startdate" value=""></input><br><br>
Enter EndDate:
<input type="text" name="enddate" value=""></input><br><br>
<br>
<input type="submit"></input>
</form>
<%
}//end of else loop
%>


</div>







</body>
</html>