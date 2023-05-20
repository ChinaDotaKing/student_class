<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Login</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.1/dist/css/bootstrap.min.css" integrity="sha384-F3w7mX95PdgyTmZZMECAngseQB83DfGTowi0iMjiWaeVhAn4FJkqJByhZMI3AhiU" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.1/dist/js/bootstrap.bundle.min.js" integrity="sha384-/bQdsTh/da6pkI1MST/rWKFNjaCP5gBSY4sEBT38Q/9RBh9AH40zEOg7Hlq2THRZ" crossorigin="anonymous"></script>
</head>

<body>
<div class="container">
    <form method="post" action="">
        <div class="form-group">
            <label for="username">Username</label>
            <input type="text"
                   class="form-control"
                   id="username"
                   name="username"
                   placeholder="Enter username">
        </div>
        <div class="form-group">
            <label for="password">Password</label>
            <input type="password"
                   class="form-control"
                   id="password"
                   name="password"
                   placeholder="Password">
        </div>
        <button type="submit" class="btn btn-primary">Submit</button>
    </form>

    <a href="signup">No account?Sign up</a>
</div>
</body>

</html>
