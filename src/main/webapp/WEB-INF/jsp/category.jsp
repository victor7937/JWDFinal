<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Categories</title>
    <jsp:include page="includes.jsp"/>
    <link href="<c:url value='/css/style.css'/>" rel="stylesheet" type="text/css">
    <link href="<c:url value='/css/card.css'/>" rel="stylesheet" type="text/css">
    <c:set var="forWhom" value="${requestScope.forWhom}"/>
    <c:set var="brandParam" value="${requestScope.brand}"/>
    <c:set var="categoryParam" value="${requestScope.category}"/>
</head>
<body>

<jsp:include page="header.jsp"/>

<section class="jumbotron text-center">
    <div class="container">
        <h1 class="jumbotron-heading">CATEGORY</h1>
        <p class="lead text-muted mb-0">Choose shoes as you like</p>
    </div>
</section>
<div class="container">
    <div class="row">
        <div class="col">
            <nav aria-label="breadcrumb">
                <ol class="breadcrumb">
                    <li class="breadcrumb-item"><a href="lei-shoes">Home</a></li>
                    <li class="breadcrumb-item"><a href="Controller?command=gotocategory">Category</a></li>
                    <li class="breadcrumb-item active" aria-current="page">Sub-category</li>
                </ol>
            </nav>
        </div>
    </div>
</div>
<div class="container">
    <div class="row">
        <div class="col-12 col-sm-3">
            <div class="card bg-light mb-3">
<%--                <div class="card-header bg-card-header text-white text-uppercase"><i class="fa fa-list"></i> Categories</div>--%>
                <ul class="list-group category_block">
                    <li class="list-group-item ${'all'.equals(forWhom) ? 'active' : ''}"><a href="Controller?command=gotocategory&for=all&category=${categoryParam}&brand=${brandParam}">All</a></li>
                    <li class="list-group-item ${'him'.equals(forWhom) ? 'active' : ''}"><a href="Controller?command=gotocategory&for=him&category=${categoryParam}&brand=${brandParam}">For Him</a></li>
                    <li class="list-group-item ${'her'.equals(forWhom) ? 'active' : ''}"><a href="Controller?command=gotocategory&for=her&category=${categoryParam}&brand=${brandParam}">For Her</a></li>
                </ul>
            </div>
            <div class="card bg-light mb-3">
                <div class="card-header bg-card-header text-white text-uppercase"><i class="fa fa-list"></i> Categories</div>
                <ul class="list-group category_block">
                    <li class="list-group-item ${'all'.equals(categoryParam) ? 'active' : ''}"><a href="Controller?command=gotocategory&category=all&brand=${brandParam}&for=${forWhom}">All</a></li>
                    <c:forEach var="category" items="${requestScope.categoryList}" >
                        <li class="list-group-item ${categoryParam.equals(category) ? 'active' : ''}"><a href="Controller?command=gotocategory&category=${category}&brand=${brandParam}&for=${forWhom}">${category}</a></li>
                    </c:forEach>
                </ul>
            </div>
            <div class="card bg-light mb-3">
                <div class="card-header bg-card-header text-white text-uppercase"><i class="fa fa-list"></i> Brands</div>
                <ul class="list-group category_block">
                    <li class="list-group-item ${'all'.equals(brandParam) ? 'active' : ''}"><a href="Controller?command=gotocategory&brand=all&category=${categoryParam}&for=${forWhom}">All</a></li>
                    <c:forEach var="brand" items="${requestScope.brandList}" >
                        <li class="list-group-item ${brandParam.equals(brand) ? 'active' : ''}"><a href="Controller?command=gotocategory&brand=${brand}&category=${categoryParam}&for=${forWhom}">${brand}</a></li>
                    </c:forEach>
                </ul>
            </div>
<%--            <div class="card bg-light mb-3">--%>
<%--                <div class="card-header text-white text-uppercase">Last product</div>--%>
<%--                <div class="card-body">--%>
<%--                    <img class="img-fluid" src="https://dummyimage.com/600x400/55595c/fff" />--%>
<%--                    <h5 class="card-title">Product title</h5>--%>
<%--                    <p class="card-text">Some quick example text to build on the card title and make up the bulk of the card's content.</p>--%>
<%--                    <p class="bloc_left_price">99.00 $</p>--%>
<%--                </div>--%>
<%--            </div>--%>
        </div>
        <div class="col">
            <div class="row">
                <!--<div class="col-12 col-md-6 col-lg-4">
                    <div class="card">
                        <img class="card-img-top" src="https://dummyimage.com/600x400/55595c/fff" alt="Card image cap">
                        <div class="card-body">
                            <h4 class="card-title"><a href="Controller?command=gotoproduct" title="View Product">Product title</a></h4>
                            <p class="card-text">Some quick example text to build on the card title and make up the bulk of the card's content.</p>
                            <div class="row">
                                <div class="col">
                                    <p class="btn btn-danger btn-block">99.00 $</p>
                                </div>
                                <div class="col">
                                    <a href="#" class="btn btn-success btn-block">Add to cart</a>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>-->
                <c:forEach var="footwear" items="${requestScope.footwearList}">
                    <div class="col-md-4 col-sm-6 my-3">
                        <div class="product-card">
                            <div class="product-image">
                                    <img class="pic-1" src="${footwear.imageLink}">
                                    <img class="pic-2" src="${footwear.imageLink}">
                                <ul class="social">
                                   <!-- <li><a href="" class="fa fa-search"></a></li>
                                    <li><a href="" class="fa fa-shopping-bag"></a></li> -->
                                    <li><a href="" class="fa fa-shopping-cart"></a></li>
                                </ul>
                                <!--<span class="product-discount-label">-20%</span>-->
                            </div>
                            <div class="product-content">
                                <div class="price">$ ${footwear.price}
<%--                                    <span>${footwear.price}</span>--%>
                                </div>
                                <h2 class="brand">${footwear.brand}</h2>
                                <span class="model">${footwear.name}</span>
                                <h3 class="category"><a href="Controller?command=gotocategory&category=${footwear.category}">${footwear.category}</a></h3>
                                <a class="details" href="Controller?command=gotoproduct&art=${footwear.art}">More Details<i class="fa fa-angle-right icon"></i></a>
                            </div>
                        </div>
                    </div>
                </c:forEach>
                <div class="col-12">
                    <nav aria-label="...">
                        <ul class="pagination">
                            <li class="page-item disabled">
                                <a class="page-link" href="#" tabindex="-1">Previous</a>
                            </li>
                            <li class="page-item"><a class="page-link" href="#">1</a></li>
                            <li class="page-item active">
                                <a class="page-link" href="#">2 <span class="sr-only">(current)</span></a>
                            </li>
                            <li class="page-item"><a class="page-link" href="#">3</a></li>
                            <li class="page-item">
                                <a class="page-link" href="#">Next</a>
                            </li>
                        </ul>
                    </nav>
                </div>
            </div>
        </div>

    </div>
</div>

<jsp:include page="footer.jsp"/>

</body>
</html>
