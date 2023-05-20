<%@ taglib  uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java"
 contentType="text/html; charset=ISO-8859-1"
 pageEncoding="ISO-8859-1"%>


<!DOCTYPE html>
<html>
<head>
	<title>Class Page</title>
</head>
<body>
<%@ include file="admin_navigate.jsp" %>
<h1>Admin Class Management</h1>
<br><br>


<table class="table table-striped">
    <thead>
    <tr>
                <th scope="col">Index</th>
              <th scope="col">Course Id</th>
              <th scope="col">Course name</th>
              <th scope="col">Course code</th>
               <th scope="col">Dept_name</th>
               <th scope="col">School_name</th>
              <th scope="col">Enroll_NUM</th>
              <th scope="col">Capacity</th>
               <th scope="col">Semester</th>
               <th scope="col">Is_Active</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${classes}" var="class1" varStatus="loop">
        <tr>
            <td><a href="/class/${class1.id}">${class1.id}</a></td>
            <td>${class1.course_id}</td>
             <td>${courses.get(loop.index).getCourse_name()}</td>
            <td>${courses.get(loop.index).getCourse_code()}</td>
             <td>${departments.get(loop.index).getName()}</td>
             <td>${departments.get(loop.index).getSchool()}</td>
              <td>${en_num.get(loop.index)}</td>
            <td>${class1.getCapacity()}</td>
            <td>${semesters.get(loop.index).getName()}</td>
            <td>${class1.is_active()}</td>

             <td>
            <form action="${class1.id}" method="post">
            <c:choose>
              <c:when test="${class1.is_active()}"><button type="submit" name="button" value="deactivate">Deactivate</button></c:when>
               <c:when test="${!class1.is_active()}"><button type="submit" name="button" value="activate">Activate</button></c:when>
            </c:choose>
            </form>
            </td>


        </tr>
        <tr>

        </tr>
    </c:forEach>
    </tbody>
</table>

<br><br>


<br><br>

</body>

</html>