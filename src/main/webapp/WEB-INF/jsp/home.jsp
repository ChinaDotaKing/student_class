<%@ taglib  uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java"
 contentType="text/html; charset=ISO-8859-1"
 pageEncoding="ISO-8859-1"%>


<!DOCTYPE html>
<html>
<head>
	<title>Student Home Page</title>
</head>
<body>
<%@ include file="student_navigate.jsp" %>
<h1>Student Home</h1>
The Student is confirmed: ${student.firstName},   ${student.lastName}
<br>
username: ${student.username} email: ${student.email }
<br><br>


<%@ include file="student_enrollclasses.jsp" %>

<br><br>


</body>

</html>