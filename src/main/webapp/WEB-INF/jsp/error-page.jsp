<%--
  Created by IntelliJ IDEA.
  User: victor
  Date: 9.03.21
  Time: 23:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Error page</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <jsp:include page="includes.jsp"/>
    <link href="<c:url value='/css/style.css'/>" rel="stylesheet" type="text/css">
</head>
<body>

<div class="container">
    <c:set var="statusCode" value="${pageContext.errorData.statusCode}"/>
    <h1 class="mt-4 mb-3">
        <c:choose>
            <c:when test="${statusCode == 404}">
                <small>Page Not Found</small>
            </c:when>
            <c:when test="${statusCode == 403}">
                <small>Forbidden</small>
            </c:when>
            <c:when test="${statusCode == 500}">
                <small>Server error</small>
            </c:when>
            <c:otherwise>
                <small>Unknown error</small>
            </c:otherwise>
        </c:choose>
    </h1>

    <ol class="breadcrumb">
        <li class="breadcrumb-item">
            <a href="Controller?command=gotomainpage">Home</a>
        </li>
        <li class="breadcrumb-item active">${statusCode}</li>
    </ol>

    <div class="jumbotron">
        <h1 class="display-1">${statusCode}</h1>
        <c:choose>
            <c:when test="${statusCode == 404}">
                <p>The page you're looking for could not be found.</p>
            </c:when>
            <c:when test="${statusCode == 403}">
                <p>Sorry, but the page you're looking for is forbidden.</p>
            </c:when>
            <c:when test="${statusCode == 500}">
                <p>${pageContext.getException().getMessage()}</p>
            </c:when>
            <c:otherwise>
                <small>Sorry unknown error was occured</small>
            </c:otherwise>
        </c:choose>
        <p>Here are some helpful links to get you back on track:</p>
        <ul>
            <li>
                <a href="Controller?command=gotomainpage">Home</a>
            </li>
            <li>
                <a href="Controller?command=gotocategory">Categories</a>
            </li>
            <li>
                <a href="Controller?command=gotocontact">Contact</a>
            </li>
        </ul>
    </div>
</div>

</body>
</html>
