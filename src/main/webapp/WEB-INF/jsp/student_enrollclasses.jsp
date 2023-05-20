<%@ taglib  uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java"
 contentType="text/html; charset=ISO-8859-1"
 pageEncoding="ISO-8859-1"%>


<!DOCTYPE html>
<html>

<body>
<table class="table table-striped">
    <thead>
    <tr>
                <th scope="col">Index</th>
              <th scope="col">Course Id</th>
              <th scope="col">Course name</th>
              <th scope="col">Course code</th>
              <th scope="col">P.firstName</th>
              <th scope="col">P.lastName</th>
              <th scope="col">Dept_name</th>
               <th scope="col">School_name</th>
               <th scope="col">Semester</th>
               <th scope="col">Is_Active</th>
               <th scope="col">Status</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${classes}" var="class1" varStatus="loop">
        <tr>
            <td><a href="/class/${class1.id}">${class1.id}</a></td>
            <td>${class1.course_id}</td>
             <td>${courses.get(loop.index).getCourse_name()}</td>
            <td>${courses.get(loop.index).getCourse_code()}</td>
            <td>${professors.get(loop.index).getFirstName()}</td>
            <td>${professors.get(loop.index).getLastName()}</td>
            <td>${departments.get(loop.index).getName()}</td>
            <td>${departments.get(loop.index).getSchool()}</td>

            <td>${semesters.get(loop.index).getName()}</td>
            <td>${class1.is_active()}</td>
            <td>${enrollments.get(loop.index).getStatus()}</td>

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
                      <a href="/home/${currentPage-1}/${limit}">previous</a>
                 </c:if>


                 <c:forEach begin="1" end="${totalPages}" varStatus="loop">
                            <a href="/home/${loop.index}/${limit}">${loop.index}</a>

                 </c:forEach>


                 <c:if test="${currentPage < totalPages}">
                   <a href="/home/${currentPage+1}/${limit}">next</a>
                 </c:if>

</body>

</html>