<%@ taglib  uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java"
 contentType="text/html; charset=ISO-8859-1"
 pageEncoding="ISO-8859-1"%>


<!DOCTYPE html>
<html>
<head>
	<title>Application Page</title>
</head>
<body>
   <%@ include file="admin_navigate.jsp" %>

<h1>Admin Application Management</h1>
<br><br>


<table class="table table-striped">
    <thead>
    <tr>
                <th scope="col">Index</th>
                <th scope="col">ClassId</th>
                 <th scope="col">StudentId</th>
                <th scope="col">FirstName</th>
                 <th scope="col">LastName</th>
                 <th scope="col">Email</th>
              <th scope="col">Course Id</th>
              <th scope="col">Course name</th>
              <th scope="col">Course code</th>
               <th scope="col">Creation time</th>
               <th scope="col">Request</th>
              <th scope="col">Status</th>
              <th scope="col">Feedback</th>
    </tr>
    </thead>
    <tbody>

    <c:forEach items="${applications}" var="application1" varStatus="loop">
        <tr>
            <td>${application1.id}</a></td>
            <td>${application1.getClass_id()}</a></td>
            <td><a href="/admin/student/${students.get(loop.index).getId()}">${students.get(loop.index).getId()}</td>
            <td>${students.get(loop.index).getFirstName()}</td>
              <td>${students.get(loop.index).getLastName()}</td>
              <td>${students.get(loop.index).getEmail()}</td>
            <td>${courses.get(loop.index).getId()}</td>
             <td>${courses.get(loop.index).getCourse_name()}</td>
            <td>${courses.get(loop.index).getCourse_code()}</td>
              <td>${application1.getCreation_time()}</td>
            <td>${application1.getRequest()}</td>
            <td>${application1.getStatus()}</td>
            <td>${application1.getFeedback()}</td>


            <td>

                <form action="/admin/application/${application1.id}" method="post" modelAttribute="application">
                  <label for="text-input">Feedback:</label>
                  <input type="text" id="text-input" name="text-input" path="feedback" />

                  <button type="submit" name="submit-button" value="Reject">Reject</button>
                  <button type="submit" name="submit-button" value="Approve">Approve</button>
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