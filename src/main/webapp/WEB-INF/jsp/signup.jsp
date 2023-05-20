<%@ taglib prefix ="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<html>
<head>
	<title>Customer Registration Form</title>
	<style>
		.error{color:red}
	</style>
</head>
<body>
Sign up

<br><br>

	<form:form action="signup" modelAttribute="student">

		User name: <form:input path="username" />

		<br><br>


		Password: <form:input path="password" />



		<br><br>

		Email: <form:input path="email" />

		<br><br>


        First name: <form:input path="firstName" />

        <br><br>


        Last name: <form:input path="lastName" />


        <br><br>


        Department id: <form:input path="dept_id" />


        <br><br>


		<input type="submit"  value = "Submit" >

	</form:form>

</body>

</html>