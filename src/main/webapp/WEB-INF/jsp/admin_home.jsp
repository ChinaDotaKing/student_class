<%@ taglib  uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java"
 contentType="text/html; charset=ISO-8859-1"
 pageEncoding="ISO-8859-1"%>


<!DOCTYPE html>
<html>
<head>
	<title>Student Page</title>
</head>
<body>

<%@ include file="admin_navigate.jsp" %>
<h1>Student Management</h1>
<br><br>


<table class="table table-striped">
    <thead>
    <tr>
                <th scope="col">Student_ID</th>
              <th scope="col">FirstName</th>
              <th scope="col">LastName</th>
              <th scope="col">Email</th>
               <th scope="col">Dept_name</th>
               <th scope="col">School_name</th>
               <th scope="col">Is_Active</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${students}" var="student1" varStatus="loop">
        <tr>
            <td><a href="/admin/student/${student1.id}">${student1.id}</a></td>
            <td>${student1.getFirstName()}</td>
             <td>${student1.getLastName()}</td>
            <td>${student1.getEmail()}</td>
             <td>${departments.get(loop.index).getName()}</td>
             <td>${departments.get(loop.index).getSchool()}</td>

            <td>${student1.is_active()}</td>
             <td>
            <form action="/admin/student/${student1.id}" method="post">
                        <c:choose>
                          <c:when test="${student1.is_active()}"><button type="submit" name="button" value="deactivate">Deactivate</button></c:when>
                           <c:when test="${!student1.is_active()}"><button type="submit" name="button" value="activate">Activate</button></c:when>
                        </c:choose>

                        </form>
            </td>


        </tr>
        <tr>

        </tr>
    </c:forEach>
    </tbody>
</table>
 <%
                 // Get the current page number from a request parameter or default to 1

                 int currentPage = request.getSession(false).getAttribute("page") != null ? Integer.parseInt(request.getSession(false).getAttribute("page").toString()) : 1;
                   request.getSession(false).setAttribute("currentPage",currentPage);


                 // Get the total number of pages
                 //int limit=Integer.parseInt(request.getParameter("limit"));
                 //int studentId=(request.getParameter("student")).getId();
                 int totalPages = Integer.parseInt(request.getSession(false).getAttribute("totalPages").toString());

                 // Set the maximum number of page links to display
                 int maxPageLinks = 10;

                 // Calculate the starting and ending page numbers based on the current page
                 int startPage = Math.max(currentPage - maxPageLinks/2, 1);
                 int endPage = Math.min(startPage + maxPageLinks - 1, totalPages);

                 // Adjust the starting page number to display the maximum number of links
                 startPage = Math.max(endPage - maxPageLinks + 1, 1);
             %>



                <c:if test="${currentPage > 1}">
                      <a href="/admin/home/${currentPage-1}/${limit}">previous</a>
                 </c:if>


                 <c:forEach begin="1" end="${totalPages}" varStatus="loop">
                           <a href="/admin/home/${loop.index}/${limit}">${loop.index}</a>

                 </c:forEach>


                 <c:if test="${currentPage < totalPages}">
                   <a href="/admin/home/${currentPage+1}/${limit}">next</a>
                 </c:if>
<br><br>


<br><br>

</body>

</html>