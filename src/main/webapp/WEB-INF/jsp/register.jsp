<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="r" uri="http://lei-shoes/region-functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
    <fmt:setLocale value="${sessionScope.lang}" scope="session"/>
    <fmt:setBundle basename="application"/>
</head>
<body>

<jsp:include page="header.jsp"/>

<div class="card bg-light">
    <article class="card-body mx-auto" style="max-width: 400px;">
        <h4 class="card-title mt-3 text-center"><fmt:message key="register.create"/></h4>
        <p class="text-center"><fmt:message key="register.new.account"/></p>
        <p>
            <a href="" class="btn btn-block btn-twitter"> <i class="fa fa-twitter"></i> <fmt:message key="register.twitter"/></a>
            <a href="" class="btn btn-block btn-facebook"> <i class="fa fa-facebook"></i> <fmt:message key="register.facebook"/></a>
        </p>
        <p class="divider-text">
            <span class="bg-light"><fmt:message key="register.or"/></span>
        </p>
        <c:if test="${err_message != null}">
            <p class="text-danger text-center">${err_message}</p>
        </c:if>
        <form action="Controller" method="post">
            <input type="hidden" name="command" value="savenewcustomer">
            <div class="form-group input-group">
                <div class="input-group-prepend">
                    <span class="input-group-text"> <i class="fa fa-user"></i> </span>
                </div>
                <input name="name" class="form-control" placeholder="<fmt:message key="register.placeholder.name"/>" type="text" value="${incorrect_customer == null ? '' : incorrect_customer.name}" required>
            </div>
            <div class="form-group input-group">
                <div class="input-group-prepend">
                    <span class="input-group-text"> <i class="fa fa-envelope"></i> </span>
                </div>
                <input name="email" class="form-control" placeholder="<fmt:message key="register.placeholder.email"/>" type="email" value="${incorrect_customer == null ? '' : incorrect_customer.email}" required>
            </div>
            <div class="form-group input-group">
                <div class="input-group-prepend">
                    <span class="input-group-text"> <i class="fa fa-phone"></i> </span>
                </div>
                <select name="phone" class="custom-select" style="max-width: 120px;">
                    <c:forEach var="code" items="${r:displayCodes()}">
                        <option value="${code}">${r:getCountryFlag(code)}${code}</option>
                    </c:forEach>
                </select>
                <input name="phone" class="form-control" placeholder="<fmt:message key="register.placeholder.phone"/>" type="tel" maxlength="12">
            </div>
            <div class="form-group input-group">
                <div class="input-group-prepend">
                    <span class="input-group-text"> <i class="fa fa-map-marker"></i> </span>
                </div>

                <select name="country" class="form-control">
                   <c:forEach var="re" items="${r:displayCountries(sessionScope.lang)}">
                       <option>${re}</option>
                   </c:forEach>
                </select>
            </div>
            <div class="form-group input-group">
                <div class="input-group-prepend">
                    <span class="input-group-text"> <i class="fa fa-map-marker"></i> </span>
                </div>
                <input name="city" class="form-control" placeholder="<fmt:message key="register.placeholder.city"/>" type="text" value="${incorrect_customer == null ? '' : incorrect_customer.city}">
            </div>
            <div class="form-group input-group">
                <div class="input-group-prepend">
                    <span class="input-group-text"> <i class="fa fa-map-marker"></i> </span>
                </div>
                <input name="address" class="form-control" placeholder="<fmt:message key="register.placeholder.address"/>" type="text" value="${incorrect_customer == null ? '' : incorrect_customer.address}">
            </div>
            <div class="form-group input-group">
                <div class="input-group-prepend">
                    <span class="input-group-text"> <i class="fa fa-lock"></i> </span>
                </div>
                <input name="password" id="password" class="form-control" placeholder="<fmt:message key="register.placeholder.password"/>" type="password" required
                       title="Password should contain only english letters, numbers and symbols such as '-_!@' at least one number and lowercase english letter, 8-40 characters"
                pattern="^(?=.*[0-9])(?=.*[a-z])[a-zA-Z0-9-_!@]{7,40}$">
            </div>
            <div class="form-group input-group">
                <div class="input-group-prepend">
                    <span class="input-group-text"> <i class="fa fa-lock"></i> </span>
                </div>
                <input name="password" id="password_repeat" class="form-control" placeholder="<fmt:message key="register.placeholder.password.repeat"/>" type="password" required>
            </div>
            <div class="form-group">
                <button type="submit" class="btn btn-primary btn-block"> <fmt:message key="register.create"/>  </button>
            </div>
            <p class="text-center"><fmt:message key="register.have.account"/> <a href="Controller?command=gotosigninpage"><fmt:message key="register.log.in"/></a> </p>
        </form>
    </article>
</div>

<jsp:include page="footer.jsp"/>

<script>
    let password = document.getElementById("password")
        , password_repeat = document.getElementById("password_repeat");
    function validatePassword(){
        if(password.value !== password_repeat.value) {
            password_repeat.setCustomValidity("Passwords aren't the same");
        } else {
            password_repeat.setCustomValidity('');
        }
    }
    password.onchange = validatePassword;
    password_repeat.onkeyup = validatePassword;
</script>

</body>
</html>
