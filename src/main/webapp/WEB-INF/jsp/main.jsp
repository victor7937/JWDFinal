<%--
  Created by IntelliJ IDEA.
  User: victor
  Date: 10.04.21
  Time: 16:40
  To change this template use File | Settings | File Templates.
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<fmt:setLocale value="${sessionScope.lang}" scope="session"/>
<fmt:setBundle basename="application"/>
<!DOCTYPE html>
<html lang="en">
<head>
    <!-- Site meta -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Lei Shoes</title>
    <!-- CSS -->
    <jsp:include page="includes.jsp"/>
    <link href="<c:url value='/css/style.css'/>" rel="stylesheet" type="text/css">
    <link href="<c:url value='/css/card.css'/>" rel="stylesheet" type="text/css">
    <c:set var="lastProducts" value="${requestScope.lastList}"/>
    <c:set var="popularProducts" value="${requestScope.popularList}"/>
</head>
<body>

<jsp:include page="header.jsp"/>

<section class="jumbotron text-center mb-0">
    <div class="container">
        <h1 class="jumbotron-heading">Lei Shoes</h1>
        <p class="lead text-muted"><fmt:message key="main.company.description"/></p>
    </div>
</section>

<section>
    <div id="carouselExampleIndicators" class="carousel slide w-100 h-100"  data-ride="carousel">
        <ol class="carousel-indicators">
            <li data-target="#carouselExampleIndicators" data-slide-to="0" class="active"></li>
            <li data-target="#carouselExampleIndicators" data-slide-to="1"></li>
            <li data-target="#carouselExampleIndicators" data-slide-to="2"></li>
        </ol>
        <div class="carousel-inner">
            <div class="carousel-item active">
                <img class="d-block w-100 h-100" src="<c:url value="/img/main-slide1.jpg"/>" alt="First slide">
            </div>
            <div class="carousel-item">
                <img class="d-block w-100 h-100" src="<c:url value="/img/main-slide2.jpg"/>" alt="Second slide">
            </div>
            <div class="carousel-item">
                <img class="d-block w-100 h-100" src="<c:url value="/img/main-slide3.jpg"/>" alt="Third slide">
            </div>
        </div>
        <a class="carousel-control-prev" href="#carouselExampleIndicators" role="button" data-slide="prev">
            <span class="carousel-control-prev-icon" aria-hidden="true"></span>
            <span class="sr-only">Previous</span>
        </a>
        <a class="carousel-control-next" href="#carouselExampleIndicators" role="button" data-slide="next">
            <span class="carousel-control-next-icon" aria-hidden="true"></span>
            <span class="sr-only">Next</span>
        </a>
    </div>
</section>

<section>
    <div class="container mt-3">
        <div class="row">
            <div class="col-sm">
                <div class="card">
                    <div class="card-header text-white text-uppercase">
                        <i class="fa fa-star"></i> <fmt:message key="main.last"/>
                    </div>
                    <div class="card-body">
                        <div class="row justify-content-center">
                            <c:if test="${lastProducts.size() == 0}">
                                <h2>Visit <a href="Controller?command=gotocategory" class="text-categories">Categories</a> for more products you can enjoy!</h2>
                            </c:if>
                            <c:forEach var="footwear" items="${lastProducts}">
                                <div class="col-md-3 col-sm-4 my-3">
                                    <div class="product-card">
                                        <div class="product-image">
                                            <img class="pic-1" src="images/${footwear.imageLinks.get(0)}">
                                            <img class="pic-2" src="images/${footwear.imageLinks.size() > 1 ? footwear.imageLinks.get(1) : footwear.imageLinks.get(0)}">
                                        </div>
                                        <div class="product-content">
                                            <div class="price">$ ${footwear.price}</div>
                                            <h2 class="brand">${footwear.brand}</h2>
                                            <span class="model">${footwear.name}</span>
                                            <h3 class="category"><a href="Controller?command=gotocategory&category=${footwear.category}">${footwear.category}</a></h3>
                                            <a class="details" href="Controller?command=gotoproduct&art=${footwear.art}">More Details<i class="fa fa-angle-right icon"></i></a>
                                        </div>
                                    </div>
                                </div>
                            </c:forEach>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>


    <div class="container mt-3 mb-4">
        <div class="row">
            <div class="col-sm">
                <div class="card">
                    <div class="card-header text-white text-uppercase">
                        <i class="fa fa-trophy"></i> <fmt:message key="main.best"/>
                    </div>
                    <div class="card-body">
                        <div class="row justify-content-center">
                            <c:forEach var="footwear" items="${popularProducts}">
                                <div class="col-md-3 col-sm-4 my-3">
                                    <div class="product-card">
                                        <div class="product-image">
                                            <img class="pic-1" src="images/${footwear.imageLinks.get(0)}">
                                            <img class="pic-2" src="images/${footwear.imageLinks.size() > 1 ? footwear.imageLinks.get(1) : footwear.imageLinks.get(0)}">
                                        </div>
                                        <div class="product-content">
                                            <div class="price">$ ${footwear.price}</div>
                                            <h2 class="brand">${footwear.brand}</h2>
                                            <span class="model">${footwear.name}</span>
                                            <h3 class="category"><a href="Controller?command=gotocategory&category=${footwear.category}">${footwear.category}</a></h3>
                                            <a class="details" href="Controller?command=gotoproduct&art=${footwear.art}">More Details<i class="fa fa-angle-right icon"></i></a>
                                        </div>
                                    </div>
                                </div>
                            </c:forEach>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>

<!-- Footer -->
<jsp:include page="footer.jsp"/>

</body>
</html>
