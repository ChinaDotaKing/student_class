<%@ taglib  uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java"
 contentType="text/html; charset=ISO-8859-1"
 pageEncoding="ISO-8859-1"%>


<!DOCTYPE html>
<html>
<head>
	<title>Admin Navigation Page</title>
</head>

<h2>Admin Navigation Bar<h2>
<body>
<a href="/admin/home/1/5">Home Page</a>
<a href="/admin/course/all">Course Management</a>
<a href="/admin/class/all">Class Management</a>
<a href="/admin/application/all">Application Management</a>

 <form action="/login" method="get">

                  <button type="submit" name="submit-button" value="signout">Signout</button>
                </form>

</body>

</html>