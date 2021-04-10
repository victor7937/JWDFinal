<%--
  Created by IntelliJ IDEA.
  User: victor
  Date: 10.04.21
  Time: 16:40
  To change this template use File | Settings | File Templates.
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
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
</head>
<body>

<jsp:include page="header.jsp"/>

<section class="jumbotron text-center mb-0">
    <div class="container">
        <h1 class="jumbotron-heading">Lei Shoes</h1>
        <p class="lead text-muted">Belarusian high quality hand made footwear</p>
    </div>
</section>

<section>
    <%--   <div class="container-fluid">--%>
    <%--       <div class="row">--%>
    <%--          <div class="col">--%>
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
    <%--            </div>--%>
    <%--        <div class="col-12 col-md-3">--%>
    <%--            <div class="card">--%>
    <%--                <div class="card-header bg-success text-white text-uppercase">--%>
    <%--                    <i class="fa fa-heart"></i> Top product--%>
    <%--                </div>--%>
    <%--                <img class="img-fluid border-0" src="https://dummyimage.com/600x400/55595c/fff" alt="Card image cap">--%>
    <%--                <div class="card-body">--%>
    <%--                    <h4 class="card-title text-center"><a href="Controller?command=gotoproduct" title="View Product">Product title</a></h4>--%>
    <%--                    <div class="row">--%>
    <%--                        <div class="col">--%>
    <%--                            <p class="btn btn-danger btn-block">99.00 $</p>--%>
    <%--                        </div>--%>
    <%--                        <div class="col">--%>
    <%--                            <a href="Controller?command=gotoproduct" class="btn btn-success btn-block">View</a>--%>
    <%--                        </div>--%>
    <%--                    </div>--%>
    <%--                </div>--%>
    <%--            </div>--%>
    <%--                    </div>--%>
    <%--        </div>--%>
    <%--    </div>--%>
</section>

<section>
    <div class="container mt-3">
        <div class="row">
            <div class="col-sm">
                <div class="card">
                    <div class="card-header text-white text-uppercase">
                        <i class="fa fa-star"></i> Last products
                    </div>
                    <div class="card-body">
                        <div class="row justify-content-center">
                            <c:forEach begin="1" end="3">
                                <%--                            <div class="col-sm">--%>
                                <%--                                <div class="card">--%>
                                <%--                                    <img class="card-img-top" src="https://dummyimage.com/600x400/55595c/fff" alt="Card image cap">--%>
                                <%--                                    <div class="card-body">--%>
                                <%--                                        <h4 class="card-title"><a href="Controller?command=gotoproduct" title="View Product">Product title</a></h4>--%>
                                <%--                                        <p class="card-text">Some quick example text to build on the card title and make up the bulk of the card's content.</p>--%>
                                <%--                                        <div class="row">--%>
                                <%--                                            <div class="col">--%>
                                <%--                                                <p class="btn btn-danger btn-block">99.00 $</p>--%>
                                <%--                                            </div>--%>
                                <%--                                            <div class="col">--%>
                                <%--                                                <a href="Controller?command=gotocart" class="btn btn-success btn-block">Add to cart</a>--%>
                                <%--                                            </div>--%>
                                <%--                                        </div>--%>
                                <%--                                    </div>--%>
                                <%--                                </div>--%>
                                <%--                            </div>--%>
                                <div class="col-md-3 col-sm-6 my-3">
                                    <div class="product-card">
                                        <div class="product-image">
                                            <img class="pic-1" src="https://cdn-images.farfetch-contents.com/16/11/50/16/16115016_32008729_1000.jpg">
                                            <img class="pic-2" src="https://cdn-images.farfetch-contents.com/16/11/50/16/16115016_32008727_1000.jpg">
                                            <ul class="social">
                                                <!-- <li><a href="" class="fa fa-search"></a></li>
                                                 <li><a href="" class="fa fa-shopping-bag"></a></li> -->
                                                <li><a href="" class="fa fa-shopping-cart"></a></li>
                                            </ul>
                                            <!--<span class="product-discount-label">-20%</span>-->
                                        </div>
                                        <div class="product-content">
                                            <div class="price">$ 100.00
                                                <span>$ 150.00</span>
                                            </div>
                                            <h2 class="brand">Balenciaga</h2>
                                            <span class="model">Track</span>
                                            <h3 class="category"><a href="Controller?command=gotoproduct">Sneakers</a></h3>
                                            <a class="details" href="Controller?command=gotoproduct">More Details<i class="fa fa-angle-right icon"></i></a>
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
                        <i class="fa fa-trophy"></i> Best products
                    </div>
                    <div class="card-body">
                        <div class="row justify-content-center">
                            <c:forEach begin="1" end="4">
                                <div class="col-md-3 col-sm-6 my-3">
                                    <div class="product-card">
                                        <div class="product-image">
                                            <img class="pic-1" src="https://cdn-images.farfetch-contents.com/16/52/13/52/16521352_32325136_1000.jpg">
                                            <img class="pic-2" src="https://cdn-images.farfetch-contents.com/16/52/13/52/16521352_32324357_1000.jpg">
                                            <ul class="social">
                                                <!-- <li><a href="" class="fa fa-search"></a></li>
                                                 <li><a href="" class="fa fa-shopping-bag"></a></li> -->
                                                <li><a href="" class="fa fa-shopping-cart"></a></li>
                                            </ul>
                                            <!--<span class="product-discount-label">-20%</span>-->
                                        </div>
                                        <div class="product-content">
                                            <div class="price">$ 100.00
                                                <span>$ 150.00</span>
                                            </div>
                                            <h2 class="brand">Balenciaga</h2>
                                            <span class="model">Track</span>
                                            <h3 class="category"><a href="Controller?command=gotoproduct">Sneakers</a></h3>
                                            <a class="details" href="Controller?command=gotoproduct">More Details<i class="fa fa-angle-right icon"></i></a>
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
