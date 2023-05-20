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
<%@ include file="student_navigate.jsp" %>
<h1>Student Application Management</h1>
<br><br>


<table class="table table-striped">
    <thead>
    <tr>
                <th scope="col">Index</th>
                <th scope="col">ClassId</th>
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
            <td>${courses.get(loop.index).getId()}</td>
             <td>${courses.get(loop.index).getCourse_name()}</td>
            <td>${courses.get(loop.index).getCourse_code()}</td>
              <td>${application1.getCreation_time()}</td>
            <td>${application1.getRequest()}</td>
            <td>${application1.getStatus()}</td>
            <td>${application1.getFeedback()}</td>
            <td>
            <form action="/application/${application1.id}" method="post">

            <c:choose>

              <c:when test="${application1.getStatus().equals(s1)}">
                    <button type="submit" name="button" value="cancel">Cancel</button>

              </c:when>
               <c:when test="${application1.getStatus().equals(s3)}">
                    <button type="submit" name="button" value="resubmit">Re-submit</button>
               </c:when>
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