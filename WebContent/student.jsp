<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

<form action="student_result.jsp" method="post">
Enter Id: 
<input type="text" name="id" value=""></input><br><br>
<input type="radio" name="option" value="getCourses">Get courses this student has been enrolled in<br>
<input type="radio" name="option" value="getInstructors">Get Name of instructors who have taught the students <br>
<input type="radio" name="option" value="getYearCredits">Get the yearwise credits for this student<br>
<br>
<input type="submit"></input>
</form>
</body>
</html>