<%--
  Created by IntelliJ IDEA.
  User: victor
  Date: 10.03.21
  Time: 23:49
  To change this template use File | Settings | File Templates.
--%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <fmt:setLocale value="${sessionScope.lang}" scope="session"/>
    <fmt:setBundle basename="application"/>
    <title>footer</title>
</head>
<body>

<footer class="text-light">
    <div class="container">
        <div class="row">
            <div class="col-md-3 col-lg-4 col-xl-3">
                <h5><fmt:message key="footer.about"/></h5>
                <hr class="bg-white mb-2 mt-0 d-inline-block mx-auto w-25">
                <p class="mb-0">
                    <fmt:message key="footer.description"/>
                </p>
            </div>

            <div class="col-md-2 col-lg-2 col-xl-2 mx-auto">
                <h5><fmt:message key="footer.info"/></h5>
                <hr class="bg-white mb-2 mt-0 d-inline-block mx-auto w-25">
                <ul class="list-unstyled">
                    <li><a href=""><fmt:message key="header.home"/></a></li>
                    <li><a href=""><fmt:message key="header.categories"/></a></li>
                    <li><a href=""><fmt:message key="header.contact"/></a></li>
                    <li><a href=""><fmt:message key="cart.title"/></a></li>
                </ul>
            </div>

            <div class="col-md-3 col-lg-2 col-xl-2 mx-auto">
                <h5><fmt:message key="footer.other"/></h5>
                <hr class="bg-white mb-2 mt-0 d-inline-block mx-auto w-25">
                <ul class="list-unstyled">
                    <li><a href=""><fmt:message key="header.home"/></a></li>
                    <li><a href=""><fmt:message key="header.categories"/></a></li>
                    <li><a href=""><fmt:message key="header.contact"/></a></li>
                    <li><a href=""><fmt:message key="cart.title"/></a></li>
                </ul>
            </div>

            <div class="col-md-4 col-lg-3 col-xl-3">
                <h5><fmt:message key="footer.contact"/></h5>
                <hr class="bg-white mb-2 mt-0 d-inline-block mx-auto w-25">
                <ul class="list-unstyled">
                    <li><i class="fas fa-home mr-2"></i> Lei Shoes</li>
                    <li><i class="fas fa-envelope mr-2"></i> lei-shoes@mail.ru</li>
                    <li><i class="fas fa-phone mr-2"></i> +375 29 180 25 50</li>
                    <li><i class="fas fa-print mr-2"></i> +375 33 181 15 70</li>
                </ul>
            </div>
            <div class="col-12 copyright mt-3">
                <p class="float-left">
                    <a href="#"><fmt:message key="footer.back.to.top"/></a>
                </p>
                <p class="text-right text-muted">created with <i class="fas fa-heart"></i> by <a href="https://github.com/victor7937/JWDFinal"><i>victor</i></a> for JWD Epam | <span>v. 1.0</span></p>
            </div>
        </div>
    </div>
</footer>

</body>
</html>
