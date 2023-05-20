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
<h1>Student Profile</h1>
<br><br>


<h2>Student information </h2>
<table class="table table-striped">
    <thead>
    <tr>
        <th scope="col">StudentID</th>
               <th scope="col">FirstName</th>
              <th scope="col">LastName</th>
              <th scope="col">Email</th>
              <th scope="col">Dept_name</th>
              <th scope="col">School name</th>
              <th scope="col">Is_Active</th>
    </tr>
    </thead>
    <tbody>

        <tr>
            <td>${theStudent.getId()}</a></td>
            <td>${theStudent.getFirstName()}</td>
             <td>${theStudent.getLastName()}</td>
            <td>${theStudent.getEmail()}</td>
            <td>${department.getName()}</td>
            <td>${department.getSchool()}</td>
            <td>${theStudent.is_active()}</td>

        </tr>


    </tbody>
</table>
<br><br>



<h2>Class information </h2>
<table class="table table-striped">
    <thead>
    <tr>
            <th scope="col">Class Id</th>
               <th scope="col">Course name</th>
              <th scope="col">Course code</th>
              <th scope="col">Dept_name</th>
              <th scope="col">School_name</th>
              <th scope="col">Semester_name</th>
              <th scope="col">Status</th>
    </tr>
    </thead>
    <tbody>
 <c:forEach items="${classes}" var="class1" varStatus="loop">
        <tr>
            <td><a href="/class/${class1.id}">${class1.id}</a></td>
             <td>${courses.get(loop.index).getCourse_name()}</td>
            <td>${courses.get(loop.index).getCourse_code()}</td>
             <td>${departments.get(loop.index).getName()}</td>
             <td>${departments.get(loop.index).getSchool()}</td>


            <td>${semesters.get(loop.index).getName()}</td>
            <td>${en_status.get(loop.index)}</td>
             <td>
            <form action="/admin/student/${theStudent.getId()}/class/${class1.id}" method="post">
              <button type="submit" name="button" value="pass">Pass</button>
              <button type="submit" name="button" value="fail">Fail</button>
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


<br><br>

</body>

</html>