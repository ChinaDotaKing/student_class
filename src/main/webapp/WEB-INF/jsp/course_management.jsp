<%@ taglib  uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java"
 contentType="text/html; charset=ISO-8859-1"
 pageEncoding="ISO-8859-1"%>
<%@ taglib prefix ="form" uri="http://www.springframework.org/tags/form" %>


<!DOCTYPE html>
<html>
<head>
	<title>Class Page</title>
</head>
<body>
<%@ include file="admin_navigate.jsp" %>
<h1>Course Management</h1>
<br><br>


<table class="table table-striped">
    <thead>
    <tr>

              <th scope="col">Course Id</th>
              <th scope="col">Course name</th>
              <th scope="col">Course code</th>
               <th scope="col">Dept_name</th>
               <th scope="col">School_name</th>
                <th scope="col">Course_description</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${courses}" var="course1" varStatus="loop">
        <tr>
            <td>${course1.id}</a></td>
             <td>${courses.get(loop.index).getCourse_name()}</td>
            <td>${courses.get(loop.index).getCourse_code()}</td>
             <td>${departments.get(loop.index).getName()}</td>
             <td>${departments.get(loop.index).getSchool()}</td>
               <td>${courses.get(loop.index).getDescription()}</td>


        </tr>
        <tr>

        </tr>
    </c:forEach>
    </tbody>
</table>


<h2>Add Course</h2>

<br><br>

	<form:form action="/admin/course" modelAttribute="course">

		Course name: <form:input path="course_name" />



		<br><br>
		Course code: <form:input path="course_code" />



		<br><br>
		 Department id: <form:input path="dept_id" />



		<br><br>
        Description: <form:input path="description" />

        <br><br>
		<input type="submit"  value = "Submit" >

	</form:form>

<br><br>

<h2>Add Class</h2>

<br><br>

	<form:form action="/admin/class" modelAttribute="theClass">

		Course Id: <form:input path="course_id" />



		<br><br>
		Semester Id: <form:input path="semester_id" />



		<br><br>
		 Professor id: <form:input path="professor_id" />


        <br><br>
        Classroom Id: <form:input path="classroom_id" />


        <br><br>
        Lecture Id: <form:input path="lecture_id" />

		<br><br>
        Capacity: <form:input path="capacity" />

        <br><br>
		<input type="submit"  value = "Submit" >

	</form:form>

<br><br>

<br><br>

</body>

</html>