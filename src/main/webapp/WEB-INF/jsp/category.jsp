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
    <c:set var="pageCount" value="${requestScope.pageCount}"/>
    <c:set var="currentPage" value="${requestScope.currentPage}"/>
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
                    <li class="breadcrumb-item"><a href="Controller?command=gotomainpage">Home</a></li>
                    <li class="breadcrumb-item"><a href="Controller?command=gotocategory">Category</a></li>
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
            <c:if test="${sessionScope.email != null && 'admin'.equals(sessionScope.role)}">
                <div class="card bg-light">
                    <ul class="list-group category_block">
                        <li class="list-group-item"><a href="Controller?command=gotoaddfeatures"><i class="fa fa-plus-circle"></i> Category|Brand|Color</a></li>
                    </ul>
                </div>
            </c:if>
        </div>
        <div class="col">
            <div class="row">
                <c:forEach var="footwear" items="${requestScope.footwearList}">
                    <div class="col-md-4 col-sm-6 my-3">
                        <div class="product-card">
                            <div class="product-image">
                                    <img class="pic-1" src="images/${footwear.imageLinks.get(0)}">
                                    <img class="pic-2" src="images/${footwear.imageLinks.size() > 1 ? footwear.imageLinks.get(1) : footwear.imageLinks.get(0)}">
                                <c:if test="${sessionScope.email != null && 'admin'.equals(sessionScope.role)}">
                                    <ul class="social">
                                       <!-- <li><a href="" class="fa fa-search"></a></li>
                                        <li><a href="" class="fa fa-shopping-bag"></a></li> -->
                                        <li><a href="Controller?command=gotoeditfootwear&art=${footwear.art}" data-toggle="tooltip" data-placement="top" title="Edit footwear" class="social-button fa fa-edit"></a></li>
                                        <li><a href="Controller?command=gotoitemspage&art=${footwear.art}" data-toggle="tooltip" data-placement="top" title="Add new size" class="social-button fa fa-plus-circle"></a></li>
                                    </ul>
                                </c:if>
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

                <c:if test="${pageCount != 1}">
                    <div class="col-12">
                        <nav aria-label="...">
                            <ul class="pagination">
                                <c:choose>
                                    <c:when test="${currentPage == 1}">
                                        <li class="page-item disabled">
                                            <a class="page-link " href="#" tabindex="-1">Previous</a>
                                        </li>
                                    </c:when>

                                    <c:otherwise>
                                        <li class="page-item">
                                            <a class="page-link " href="Controller?command=gotocategory&page=${requestScope.currentPage - 1}&brand=${brandParam}&category=${categoryParam}&for=${forWhom}">Previous</a>
                                        </li>
                                    </c:otherwise>
                                </c:choose>

                                <c:forEach var="i" begin="1" end="${pageCount}">
                                    <li class="page-item ${i == currentPage ? 'active' : ''}">
                                        <a class="page-link" href="Controller?command=gotocategory&page=${i}&brand=${brandParam}&category=${categoryParam}&for=${forWhom}">${i}</a>
                                    </li>
                                </c:forEach>
                                <c:choose>
                                    <c:when test="${currentPage == pageCount}">
                                        <li class="page-item disabled">
                                            <a class="page-link " href="#" tabindex="-1">Next</a>
                                        </li>
                                    </c:when>
                                    <c:otherwise>
                                        <li class="page-item">
                                            <a class="page-link " href="Controller?command=gotocategory&page=${currentPage + 1}&brand=${brandParam}&category=${categoryParam}&for=${forWhom}">Next</a>
                                        </li>
                                    </c:otherwise>
                                </c:choose>
                            </ul>
                        </nav>
                    </div>
                </c:if>
            </div>
        </div>

    </div>
</div>

<c:if test="${sessionScope.email != null && 'admin'.equals(sessionScope.role)}">
    <a href="Controller?command=addfootwear" data-toggle="tooltip" data-placement="top" title="Add new footwear" class="add-footwear-btn">
        <i class="fa fa-5x fa-plus"></i>
    </a>
</c:if>


<jsp:include page="footer.jsp"/>

</body>
</html>
