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
<c:choose>

              <c:when test="${student.is_admin()}">
                    <%@ include file="admin_navigate.jsp" %>

              </c:when>
               <c:when test="${!student.is_admin()}">
                    <%@ include file="student_navigate.jsp" %>
               </c:when>
</c:choose>
<h1>Class Information</h1>
<br><br>


<h2>Course information </h2>
<table class="table table-striped">
    <thead>
    <tr>
               <th scope="col">Course Name</th>
              <th scope="col">Course Code</th>
              <th scope="col">Dept_name</th>
              <th scope="col">School name</th>
              <th scope="col">Course description</th>
              <th scope="col">Capacity</th>
              <th scope="col">Enroll_Num</th>
              <th scope="col">Is_Active</th>
    </tr>
    </thead>
    <tbody>

        <tr>
            <td>${course.getCourse_name()}</a></td>
            <td>${course.getCourse_code()}</td>
             <td>${department.getName()}</td>
            <td>${department.getSchool()}</td>
            <td>${course.getDescription()}</td>
            <td>${theClass.getCapacity()}</td>
            <td>${enrollments_num}</td>
            <td>${theClass.is_active()}</td>

        </tr>


    </tbody>
</table>
<br><br>

<h2>Semester information </h2>
<table class="table table-striped">
    <thead>
    <tr>
               <th scope="col">SemeserName</th>
              <th scope="col">Start Date</th>
              <th scope="col">End Date</th>
    </tr>
    </thead>
    <tbody>

        <tr>
            <td>${semester.getName()}</a></td>
            <td>${semester.getStart_date()}</td>
             <td>${semester.getEnd_date()}</td>


        </tr>


    </tbody>
</table>

<h2>Lecture information </h2>
<table class="table table-striped">
    <thead>
    <tr>
               <th scope="col">Day of week</th>
              <th scope="col">Start time</th>
              <th scope="col">End time</th>
    </tr>
    </thead>
    <tbody>

        <tr>
            <td>${lecture.getDay_of_week()}</a></td>
            <td>${lecture.getStart_time()}</td>
             <td>${lecture.getEnd_time()}</td>


        </tr>


    </tbody>
</table>

<br><br>

<h2>Professor information </h2>
<table class="table table-striped">
    <thead>
    <tr>
               <th scope="col">First Name</th>
              <th scope="col">Last Name</th>
              <th scope="col">Email</th>
               <th scope="col">DepartmentName</th>
               <th scope="col">School</th>
    </tr>
    </thead>
    <tbody>

        <tr>
            <td>${professor.getFirstName()}</a></td>
            <td>${professor.getLastName()}</td>
             <td>${professor.getEmail()}</td>
              <td>${pro_dept.getName()}</td>
              <td>${pro_dept.getSchool()}</td>


        </tr>


    </tbody>
</table>

<br><br>

<h2>Classroom information </h2>
<table class="table table-striped">
    <thead>
    <tr>
               <th scope="col">Name</th>
              <th scope="col">Building</th>
              <th scope="col">Capacity</th>
    </tr>
    </thead>
    <tbody>

        <tr>
            <td>${classroom.getName()}</a></td>
            <td>${classroom.getBuilding()}</td>
             <td>${classroom.getCapacity()}</td>



        </tr>


    </tbody>
</table>

<br><br>

<h2>Prerequisites course id</h2>
<table class="table table-striped">
    <thead>
    <tr>
               <th scope="col">Course_id</th>
    </tr>
    </thead>
    <tbody>

        <c:forEach items="${prerequisites}" var="prereq" varStatus="loop">
                <tr>
                    <td>${userService.getCourseNameById(prereq.pre_req_id)}</td>

                </tr>
        </c:forEach>


    </tbody>
</table>

<br><br>

 <c:if test="${student.is_admin()}">



         <h2>Enrolled students</h2>
         <table class="table table-striped">
             <thead>
             <tr>
                        <th scope="col">StudentId</th>
                        <th scope="col">firstName</th>
                        <th scope="col">LastName</th>
                        <th scope="col">DeptName</th>
                        <th scope="col">School</th>
             </tr>
             </thead>
             <tbody>

                 <c:forEach items="${students}" var="student1" varStatus="loop">
                         <tr>
                             <td><a href="/admin/student/${student1.id}">${student1.id}</td>
                             <td>${student1.getFirstName()}</td>
                             <td>${student1.getLastName()}</td>
                             <td>${departments.get(loop.index).getName()}</td>
                               <td>${departments.get(loop.index).getSchool()}</td>
                         </tr>
                 </c:forEach>


             </tbody>
         </table>
  </c:if>

</body>

</html>