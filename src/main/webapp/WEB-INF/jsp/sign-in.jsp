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
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Sign-in</title>
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

<div class="card bg-light" id="sign-in">
    <article class="card-body mb-4 mx-auto" style="max-width: 400px;">
        <h4 class="card-title text-center mb-4 mt-1"><fmt:message key="sign.in.main"/></h4>
        <hr>
        <c:choose>
            <c:when test="${param.message.equals('wrong_e_or_p')}">
                <p class="text-danger text-center"><fmt:message key="sign.in.wrong.email"/><br/>
                    Please try again</p>
            </c:when>
            <c:when test="${param.message.equals('blocked')}">
                <p class="text-danger text-center">Sorry, but you are blocked<br/>
                    Please write to support for more information</p>
            </c:when>
            <c:when test="${param.message.equals('register_success')}">
                <p class="text-success text-center"><fmt:message key="sign.in.register.ok"/></p>
            </c:when>
            <c:otherwise>
                <p class="text-info text-center"><fmt:message key="sign.in.input"/></p>
            </c:otherwise>

        </c:choose>

        <form action="Controller" method="post">
            <input type="hidden" name="command" value="auth">
            <div class="form-group">
                <div class="input-group">
                    <div class="input-group-prepend">
                        <span class="input-group-text"> <i class="fa fa-user"></i> </span>
                    </div>
                    <input class="form-control" placeholder="<fmt:message key="sign.in.email"/>" type="email" name="email" required="required">
                </div>
            </div>
            <div class="form-group">
                <div class="input-group">
                    <div class="input-group-prepend">
                        <span class="input-group-text"> <i class="fa fa-lock"></i> </span>
                    </div>
                    <input class="form-control" placeholder="<fmt:message key="sign.in.password"/>" type="password" name="password" required="required">
                </div>
            </div>
            <div class="form-group">
                <button type="submit" class="btn btn-primary btn-block"> <fmt:message key="sign.in.login"/>  </button>
            </div>
        </form>
        <p class="text-center"><a href="#" class="btn"><fmt:message key="sign.in.forgot"/></a></p>
        <p class="text-center">Don't have an account? <a href="Controller?command=registration"><fmt:message key="sign.in.register"/></a> </p>

    </article>
</div>

<jsp:include page="footer.jsp"/>

</body>
</html>

