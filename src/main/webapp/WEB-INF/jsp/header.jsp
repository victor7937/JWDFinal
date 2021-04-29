<%--
  Created by IntelliJ IDEA.
  User: victor
  Date: 10.03.21
  Time: 23:40
  To change this template use File | Settings | File Templates.
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${sessionScope.lang}" scope="session"/>
<fmt:setBundle basename="application"/>

<html>
<head>
    <title>header</title>
</head>
<body>
<header>
    <nav class="navbar navbar-expand-md navbar-dark bg-dark">
        <div class="container">
            <a class="navbar-brand" href="Controller?command=gotomainpage"><span class="logo"><fmt:message key="header.company.name"/></span></a>
            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarsExampleDefault" aria-controls="navbarsExampleDefault" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>

            <div class="collapse navbar-collapse justify-content-end" id="navbarsExampleDefault">
                <ul class="navbar-nav m-auto">
                    <li class="nav-item ${param.size() == 0 ? 'active' : ''}">
                        <a class="nav-link" href="Controller?command=gotomainpage"><fmt:message key="header.home"/><span class="sr-only">(current)</span></a>
                    </li>
                    <li class="nav-item ${param.get('command').equals('gotocategory') ? 'active' : ''}">
                        <a class="nav-link" href="Controller?command=gotocategory"><fmt:message key="header.categories"/></a>
                    </li>
                    <li class="nav-item ${param.get('command').equals('gotocontact') ? 'active' : ''}">
                        <a class="nav-link" href="Controller?command=gotocontact"><fmt:message key="header.contact"/></a>
                    </li>

                    <c:choose>
                        <c:when test="${sessionScope.email != null}">
                            <li class="nav-item dropdown">
                                <a  class="nav-link dropdown-toggle" href="#" role="button" id="user_email" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                        ${sessionScope.email}</a>
                                <div class="dropdown-menu" aria-labelledby="user_email">
                                    <a class="dropdown-item" href="${'admin'.equals(sessionScope.role) ? 'Controller?command=gotoadminpage' : 'Controller?command=gotoprofile'}">
                                        <fmt:message key="header.profile"/>
                                    </a>
                                    <a class="dropdown-item" href="Controller?command=logout">
                                        <fmt:message key="header.logout"/>
                                    </a>
                                </div>
                            </li>
                        </c:when>
                        <c:otherwise>
                            <li class="nav-item dropdown">
                                <a class="nav-link dropdown-toggle" href="#" id="no_user" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                    <fmt:message key="header.user"/>
                                </a>
                                <div class="dropdown-menu" aria-labelledby="no_user">
                                    <a class="dropdown-item" href="Controller?command=gotosigninpage"><fmt:message key="header.signin"/></a>
                                    <a class="dropdown-item" href="Controller?command=registration"><fmt:message key="header.register"/></a>
                                </div>
                            </li>
                        </c:otherwise>
                    </c:choose>
                </ul>

                <a class="btn btn-success btn-sm my-2" href="Controller?command=gotocart">
                    <i class="fas fa-shopping-cart"></i> <fmt:message key="header.cart"/>
                    <span class="badge badge-light text-info"><i class="fab fa-shopify"></i></span>
                </a>

                <ul class="navbar-nav offset-1">
                    <li class="nav-item">
                        <form method="get" class="my-auto" action="${requestScope['javax.servlet.forward.request_uri']}">
                            <c:forEach var="key" items="${param.keySet()}">
                                <c:if test="${!('lang'.equals(key) || 'show'.equals(key))}">
                                    <input type="hidden" name="${key}" value="${param.get(key)}">
                                </c:if>
                            </c:forEach>
                            <input type="hidden" name="lang" value="ru">
                            <button class="btn bg-dark text-light font-weight-normal" type="submit"><fmt:message key="header.ru"/></button>
                        </form>
                    </li>
                    <li class="nav-item">
                        <form method="get" class="my-auto" action="${requestScope['javax.servlet.forward.request_uri']}">
                            <c:forEach var="key" items="${param.keySet()}">
                                <c:if test="${!('lang'.equals(key) || 'show'.equals(key))}">
                                    <input type="hidden" name="${key}" value="${param.get(key)}">
                                </c:if>
                            </c:forEach>
                            <input type="hidden" name="lang" value="en">
                            <button class="btn bg-dark text-light font-weight-normal" type="submit"><fmt:message key="header.en"/></button>
                        </form>
                    </li>
                </ul>

            </div>
        </div>
    </nav>
</header>

</body>
</html>


