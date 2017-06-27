<%@  taglib  prefix="c"   uri="http://java.sun.com/jsp/jstl/core"  %>
<%@page session="false"%>
<%String cPath = request.getContextPath();%>

<!DOCTYPE html>
<html>
    <head>
        <script language='javascript' type='text/javascript'>
            function checkPassword(input) {
                if (input.value != document.getElementById('password').value) {
                    input.setCustomValidity('Password Must be Matching.');
                } else {
                    input.setCustomValidity('');
                }
            }
        </script>
        <title>Login</title>
        <link type="text/css" rel="stylesheet" href="<%=cPath%>/css/login.css" />
    </head>
    <body>

        <div class="center">
            <img  id="login-background" src="<%=cPath%>/img/login_background.png">
            <p id="invalidSession"><c:if test="${param.invalid == 'session'}"> ${initParam.invalidSession}</c:if></p>
        </div>

        <div class="center" id="login-form">
            <form class="form" method="POST" action="" >
                <p id="invalid"><c:if test="${param.invalid == 'login'}"> ${initParam.invalidUser}</c:if></p>
                <p>User</p>
                <input type="text" name="user" required="required">
                <br>
                <p>Password</p>
                <input type="password" name="password" required="required">
                <br>
                <br>
                <button id="login-button" type="submit" name="login"> Login </button>
            </form>

            <h1 class="form">- or -</h1>

            <form class="form" method="POST" action="" >
                <p id="invalid"><c:if test="${param.invalid == 'register'}"> ${initParam.invalidRegister}</c:if></p>
                <p>User</p>
                <input type="text" name="user" required="required"/>
                <br>
                <p>Password</p>
                <input type="password" name="password" required="required" id="password"/>
                <br>
                <p>Retype Password</p>
                <input type="password" name="retypepassword" required="required" oninput="checkPassword(this)"/>
                <br>
                <br>
                <button id="login-button" type="submit" name="register"> Register </button>
            </form>
        </div>
    </body>
</html>