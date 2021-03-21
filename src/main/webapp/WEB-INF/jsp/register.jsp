<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: victor
  Date: 8.03.21
  Time: 23:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Registration page</title>
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
        <h4 class="card-title mt-3 text-center">Create Account</h4>
        <p class="text-center">Get started with your free account</p>
        <p>
            <a href="" class="btn btn-block btn-twitter"> <i class="fab fa-twitter"></i>   Login via Twitter</a>
            <a href="" class="btn btn-block btn-facebook"> <i class="fab fa-facebook-f"></i>   Login via facebook</a>
        </p>
        <p class="divider-text">
            <span class="bg-light">OR</span>
        </p>
        <form action="Controller" method="post">
            <input type="hidden" name="command" value="savenewcustomer">
            <div class="form-group input-group">
                <div class="input-group-prepend">
                    <span class="input-group-text"> <i class="fa fa-user"></i> </span>
                </div>
                <input name="name" class="form-control" placeholder="Full name" type="text" required>
            </div> <!-- form-group// -->
            <div class="form-group input-group">
                <div class="input-group-prepend">
                    <span class="input-group-text"> <i class="fa fa-envelope"></i> </span>
                </div>
                <input name="email" class="form-control" placeholder="Email address" type="email" required>
            </div> <!-- form-group// -->
            <div class="form-group input-group">
                <div class="input-group-prepend">
                    <span class="input-group-text"> <i class="fa fa-phone"></i> </span>
                </div>
                <select name="phone" class="custom-select" style="max-width: 120px;">
                    <option selected="">+375</option>
                    <option>+972</option>
                    <option>+198</option>
                    <option>+701</option>
                </select>
                <input name="phone" class="form-control" placeholder="Phone number" type="number" maxlength="7">
            </div> <!-- form-group// -->
            <div class="form-group input-group">
                <div class="input-group-prepend">
                    <span class="input-group-text"> <i class="fa fa-map-marker"></i> </span>
                </div>
                <select name="country" class="form-control">
                    <option selected="">Belarus</option>
                    <option>Russia</option>
                    <option>Ukraine</option>
                </select>
            </div> <!-- form-group end.// -->
            <div class="form-group input-group">
                <div class="input-group-prepend">
                    <span class="input-group-text"> <i class="fa fa-map-marker"></i> </span>
                </div>
                <input name="city" class="form-control" placeholder="City" type="text">
            </div> <!-- form-group// -->
            <div class="form-group input-group">
                <div class="input-group-prepend">
                    <span class="input-group-text"> <i class="fa fa-map-marker"></i> </span>
                </div>
                <input name="address" class="form-control" placeholder="Address" type="text">
            </div> <!-- form-group// -->
            <div class="form-group input-group">
                <div class="input-group-prepend">
                    <span class="input-group-text"> <i class="fa fa-lock"></i> </span>
                </div>
                <input name="password" class="form-control" placeholder="Create password" type="password" required>
            </div> <!-- form-group// -->
            <div class="form-group input-group">
                <div class="input-group-prepend">
                    <span class="input-group-text"> <i class="fa fa-lock"></i> </span>
                </div>
                <input name="password" class="form-control" placeholder="Repeat password" type="password" required>
            </div> <!-- form-group// -->
            <div class="form-group">
                <button type="submit" class="btn btn-primary btn-block"> Create Account  </button>
            </div> <!-- form-group// -->
            <p class="text-center">Have an account? <a href="Controller?command=gotosigninpage">Log In</a> </p>
        </form>
    </article>
</div> <!-- card.// -->

<jsp:include page="footer.jsp"/>

</body>
</html>
