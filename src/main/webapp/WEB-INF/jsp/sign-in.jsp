<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: victor
  Date: 8.03.21
  Time: 23:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Sign-in</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <jsp:include page="includes.jsp"/>
    <link href="<c:url value='/css/register.css'/>" rel="stylesheet" type="text/css">
    <link href="<c:url value='/css/style.css'/>" rel="stylesheet" type="text/css">
</head>
<body>

<jsp:include page="header.jsp"/>

<div class="card bg-light">
    <article class="card-body mx-auto" style="max-width: 400px;">
        <h4 class="card-title text-center mb-4 mt-1">Sign in</h4>
        <hr>
        <c:choose>
            <c:when test="${param.message.equals('wrong_e_or_p')}">
                <p class="text-danger text-center">Wrong email or password<br/>
                Please try again</p>
            </c:when>
            <c:when test="${param.message.equals('register_success')}">
                <p class="text-success text-center">Registration completed successfully</p>
            </c:when>
            <c:otherwise>
                <p class="text-info text-center">Input email and password</p>
            </c:otherwise>

        </c:choose>

        <form action="Controller" method="post">
            <input type="hidden" name="command" value="auth">
            <div class="form-group">
                <div class="input-group">
                    <div class="input-group-prepend">
                        <span class="input-group-text"> <i class="fa fa-user"></i> </span>
                    </div>
                    <input class="form-control" placeholder="Email" type="email" name="email" required="required">
                </div>
            </div>
            <div class="form-group">
                <div class="input-group">
                    <div class="input-group-prepend">
                        <span class="input-group-text"> <i class="fa fa-lock"></i> </span>
                    </div>
                    <input class="form-control" placeholder="Password" type="password" name="password" required="required">
                </div>
            </div>
            <div class="form-group">
                <button type="submit" class="btn btn-primary btn-block"> Login  </button>
            </div>
        </form>
            <p class="text-center"><a href="#" class="btn">Forgot password?</a></p>
            <p class="text-center">Don't have an account? <a href="Controller?command=registration">Register</a> </p>

    </article>
</div>

<jsp:include page="footer.jsp"/>

</body>
</html>

