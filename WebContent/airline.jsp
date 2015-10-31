<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="database.DataAccess" %>
<%@ page import="database.Airline" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<style>
table,th,td{
	border: 1px solid black;
}
</style>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Welcome Airline</title>
<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
</head>
<body>
<h1>Welcome Airline</h1>
<div class="table-responsive">
Your Warehouse bookings are as follows<br>
<%
{
String aid="10";
List<String> warehouses=Airline.getWarehouse(aid);//airline_id
if(warehouses.size()==0){
	out.print("you have no warehouses bookded");
}
else{
	out.print("<table class='table'>");
	out.print("<tr><th>Warehouse</th><th>Start_date</th><th>End_date</th></tr>");
	int count=0;
	for(String house:warehouses){
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
Your Gate bookings are as follows<br>
<% 
{
	
List<String> gates=Airline.getGate("10");//airline_id
if(gates.size()==0){
	out.print("you have no gates booked");
}
else{
	out.print("<table class='table'>");
	out.print("<tr><th>Gate</th><th>Start_date</th><th>End_date</th></tr>");
	int count=0;
	for(String house:gates){
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
List<String> pending=Airline.getPending("10");//airline_id
if(pending.size()==0){
	out.print("you have requests");
}
else{
	out.print("<table class='table'>");
	out.print("<tr><th>Requested For</th><th>Booking_id</th><th>Status</th></tr>");
	int count=0;
	for(String house:pending){
		if(count==0)out.print("<tr>");
		out.print("<td>"+house + "</td>");
		count++;
		if(count==3){out.print("</tr>");count=0;}
	}
	out.print("</table>");
}

%>

<%
if ("POST".equalsIgnoreCase(request.getMethod())) {
	String aid="10";//change this after login is put in
    String option=request.getParameter("item");
    String id=request.getParameter("id");
    String start=request.getParameter("startdate");
    String end=request.getParameter("enddate");
    out.print(option+"<br>"+id+"<br>"+start+"<br>"+end);
    Airline.book(option,aid,id,start,end);
    out.print("<br><a href='airline.jsp'>book another warehouse/gate</a><br>");
} else {
    // Putting form here
%>
<br><br>
<form class="form-horizontal" role="form">
  <div class="form-group">
    <label class="control-label col-sm-2" for="email">Email:</label>
    <div class="col-sm-10">
      <input type="email" class="form-control" id="email" placeholder="Enter email">
    </div>
  </div>
  <div class="form-group">
    <label class="control-label col-sm-2" for="pwd">Password:</label>
    <div class="col-sm-10"> 
      <input type="password" class="form-control" id="pwd" placeholder="Enter password">
    </div>
  </div>
  <div class="form-group"> 
    <div class="col-sm-offset-2 col-sm-10">
      <div class="checkbox">
        <label><input type="checkbox"> Remember me</label>
      </div>
    </div>
  </div>
  <div class="form-group"> 
    <div class="col-sm-offset-2 col-sm-10">
      <button type="submit" class="btn btn-default">Submit</button>
    </div>
  </div>
</form>
Request for a Gate/Warehouse here
<br>
<form action="airline.jsp" method="post" role="form">
<div class="form-group">
<select name="item">
  <option value="warehosue">Warehouse</option>
  <option value="gate">Gate</option>
</select>
<br>
Enter Id: 
<input type="text" name="id" value=""></input>
Enter StartDate:
<input type="text" name="startdate" value=""></input>
Enter EndDate:
<input type="text" name="enddate" value=""></input>
</div>
<input type="submit" class="btn btn-default"></input>

</form>
<% 
//closing the else loop
}
%>


</div>

</body>
</html>