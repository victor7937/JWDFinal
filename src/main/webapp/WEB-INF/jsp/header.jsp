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
            <a class="navbar-brand" href="lei-shoes"><fmt:message key="header.company.name"/></a>
            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarsExampleDefault" aria-controls="navbarsExampleDefault" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>

            <div class="collapse navbar-collapse justify-content-end" id="navbarsExampleDefault">
                <ul class="navbar-nav m-auto">
                    <c:choose>
                        <c:when test="${param.size() == 0}">
                            <li class="nav-item active">
                                <a class="nav-link" href="lei-shoes"><fmt:message key="header.home"/><span class="sr-only">(current)</span></a>
                            </li>
                        </c:when>
                        <c:otherwise>
                            <li class="nav-item">
                                <a class="nav-link" href="lei-shoes"><fmt:message key="header.home"/><span class="sr-only">(current)</span></a>
                            </li>
                        </c:otherwise>
                    </c:choose>

                    <c:choose>
                        <c:when test="${param.get('command').equals('gotocategory')}">
                            <li class="nav-item active">
                                <a class="nav-link" href="Controller?command=gotocategory"><fmt:message key="header.categories"/></a>
                            </li>
                        </c:when>
                        <c:otherwise>
                            <li class="nav-item">
                                <a class="nav-link" href="Controller?command=gotocategory"><fmt:message key="header.categories"/></a>
                            </li>
                        </c:otherwise>
                    </c:choose>

                    <c:choose>
                        <c:when test="${param.get('command').equals('gotocontact')}">
                            <li class="nav-item active">
                                <a class="nav-link" href="Controller?command=gotocontact"><fmt:message key="header.contact"/></a>
                            </li>
                        </c:when>
                        <c:otherwise>
                            <li class="nav-item">
                                <a class="nav-link" href="Controller?command=gotocontact"><fmt:message key="header.contact"/></a>
                            </li>
                        </c:otherwise>
                    </c:choose>


                    <c:choose>
                        <c:when test="${sessionScope.email != null}">
                            <li class="nav-item dropdown">
                                <a  class="nav-link dropdown-toggle" href="#" role="button" id="user_email" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                        ${sessionScope.email}</a>
                                <div class="dropdown-menu" aria-labelledby="user_email">
                                    <a class="dropdown-item" href="Controller?command=gotoprofile"><fmt:message key="header.profile"/></a>
                                    <a class="dropdown-item" href="Controller?command=logout"><fmt:message key="header.logout"/></a>
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

                <form class="form-inline my-2 my-lg-0">
                    <div class="input-group input-group-sm">
                        <input type="text" class="form-control" placeholder="Search...">
                        <div class="input-group-append">
                            <button type="button" class="btn btn-secondary btn-number">
                                <i class="fa fa-search"></i>
                            </button>
                        </div>
                    </div>
                    <a class="btn btn-success btn-sm ml-3" href="Controller?command=gotocart">
                        <i class="fa fa-shopping-cart"></i> <fmt:message key="header.cart"/>
                        <span class="badge badge-light">3</span>
                    </a>
                </form>

                <ul class="navbar-nav offset-1">
                    <li class="nav-item">
                        <a class="nav-link" href="#" onclick="addUrlParameter('lang', 'en')"><fmt:message key="header.en"/></a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="#" onclick="addUrlParameter('lang', 'ru')"><fmt:message key="header.ru"/></a>
                    </li>
                </ul>
            </div>
        </div>
    </nav>
</header>

<script>
    function addUrlParameter(name, value) {
        let searchParams = new URLSearchParams(window.location.search)
        searchParams.set(name, value)
        window.location.search = searchParams.toString()
    }
</script>

</body>
</html>


