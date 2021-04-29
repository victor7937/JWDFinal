<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <fmt:setLocale value="${sessionScope.lang}" scope="session"/>
    <fmt:setBundle basename="application"/>
    <title><fmt:message key="product.page.name"/></title>
    <jsp:include page="includes.jsp"/>
    <link href="<c:url value='/css/style.css'/>" rel="stylesheet" type="text/css">
    <link href="<c:url value='/css/product.css'/>" rel="stylesheet" type="text/css">
    <link href="<c:url value='/css/card.css'/>" rel="stylesheet" type="text/css">
    <c:set var="footwear" value="${requestScope.footwear}" scope="request"/>

</head>
<body>

<jsp:include page="header.jsp"/>

<section class="jumbotron text-center">
    <div class="container">
        <h1 class="jumbotron-heading text-uppercase"><fmt:message key="product.page.name"/></h1>
        <p class="lead text-muted mb-0"><fmt:message key="product.heading"/></p>
    </div>
</section>
<div class="container">
    <div class="row">
        <div class="col">
            <nav aria-label="breadcrumb">
                <ol class="breadcrumb">
                    <li class="breadcrumb-item"><a href="Controller?command=gotomainpage"><fmt:message key="header.home"/></a></li>
                    <li class="breadcrumb-item"><a href="Controller?command=gotocategory"><fmt:message key="header.categories"/></a></li>
                    <li class="breadcrumb-item active" aria-current="page">${footwear.art}</li>
                </ol>
            </nav>
        </div>
    </div>
</div>
<div class="container">
    <div class="row">
        <div class="col-12 col-lg-6">
            <div id="carouselExampleFade" class="carousel slide carousel-fade" data-ride="carousel" data-touch="false" data-interval="false">
                <ol class="carousel-indicators">
                    <li data-target="#carouselExampleFade" data-slide-to="0" class="active"></li>
                    <li data-target="#carouselExampleFade" data-slide-to="1"></li>
                    <li data-target="#carouselExampleFade" data-slide-to="2"></li>
                </ol>
                <div class="carousel-inner">
                    <c:forEach begin="0" var="i" end="${footwear.imageLinks.size() - 1}">
                        <div class="carousel-item ${i == 0 ? 'active' : ''}">
                            <a href="" data-toggle="modal" data-target="#productModal${i}">
                                <img class="img-fluid" src="images/${footwear.imageLinks.get(i)}" alt="image"/>
                            </a>
                        </div>
                    </c:forEach>
                </div>
                <a class="carousel-control-prev" href="#carouselExampleFade" role="button" data-slide="prev">
                    <span class="carousel-control-prev-icon bg-secondary" aria-hidden="true"></span>
                    <span class="sr-only"><fmt:message key="pagination.previous"/></span>
                </a>
                <a class="carousel-control-next" href="#carouselExampleFade" role="button" data-slide="next">
                    <span class="carousel-control-next-icon bg-secondary" aria-hidden="true"></span>
                    <span class="sr-only"><fmt:message key="pagination.previous"/></span>
                </a>
            </div>
        </div>

        <div class="col-12 col-lg-6">
            <div class="card mb-3">
                <div class="card-body">
                    <div class="product-title-brand mb-1">${footwear.brand}</div>
                    <div class="product-title-name mb-1">${footwear.name}</div>
                    <div class="price-title mb-2">$ ${footwear.price}
<%--                        <span>${footwear.price}</span>--%>
                    </div>
                    <hr class="mb-2">
                    <div class="table-responsive mb-2">
                        <table class="table table-sm table-borderless mb-0">
                            <tbody>
                            <tr>
                                <th class="pl-0 w-25" scope="row"><div class="product-header"><fmt:message key="product.model"/></div></th>
                                <td><div class="product-value">${footwear.art}</div></td>
                            </tr>
                            <tr>
                                <th class="pl-0 w-25" scope="row"><div class="product-header"><fmt:message key="product.color"/></div></th>
                                <td><div class="product-value">${footwear.color}</div></td>
                            </tr>
                            <tr>
                                <th class="pl-0 w-25" scope="row"><div class="product-header"><fmt:message key="category.category"/></div></th>
                                <td><a class="product-value" href="Controller?command=gotocategory&category=${footwear.category}">${footwear.category}</a></td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                    <hr class="mb-2">

                    <form method="post" action="Controller">
                        <input type="hidden" name="command" value="addtocart">
                        <input type="hidden" name="art" value="${footwear.art}">
                        <div class="form-group mb-2">
                            <div class="radio-toolbar">
                                <c:forEach var="size" items="${requestScope.sizes}">
                                    <input type="radio" id="${size}" name="size" value="${size}">
                                    <label for="${size}">${size % 1 == 0 ? size.intValue() : size}</label>
                                </c:forEach>
                            </div>
                        </div>
                        <button type="submit" class="btn add-to-card-btn btn-lg btn-block">
                            <i class="fas fa-shopping-cart"></i> <fmt:message key="product.add.to.cart"/>
                        </button>
                    </form>
                    <div class="product_fast_panel">
                        <ul class="list-inline">
                            <li class="list-inline-item mx-4">
                                <a type="button" data-toggle="modal" data-target="#FastOrderModal">
                                    <i class="fas fa-shipping-fast fa-2x"></i><br/><fmt:message key="product.fast.order"/>
                                </a>
                            </li>
                            <li class="list-inline-item mx-4">
                                <a href="#">
                                    <i class="fas fa-credit-card fa-2x"></i><br/><fmt:message key="product.payment"/>
                                </a>

                            </li>
                            <li class="list-inline-item mx-4">
                                <a href="tel:+375291802550">
                                    <i class="fas fa-phone fa-2x"></i><br/><fmt:message key="product.call.us"/>
                                </a>
                            </li>
                        </ul>
                    </div>

                    <div class="modal fade" id="FastOrderModal" tabindex="-1" aria-labelledby="FastOrderModalLabel" aria-hidden="true">
                        <div class="modal-dialog modal-dialog-centered">
                            <div class="modal-content">
                                <form action="Controller" method="post">
                                    <input type="hidden" name="command" value="fastorder">
                                    <input type="hidden" name="art" value="${footwear.art}">
                                    <input type="hidden" name="price" value="${footwear.price}">
                                    <div class="modal-header">
                                        <h4 class="modal-title total-cart text-center" id="FastOrderModalLabel"><i class="fas fa-shipping-fast"></i> <fmt:message key="product.fast.order"/></h4>
                                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                            <span aria-hidden="true">&times;</span>
                                        </button>
                                    </div>
                                    <div class="modal-body">
                                        <div class="form-group mb-2">
                                            <h5 class="text-cart text-left text-info"><fmt:message key="product.fast.pick.size"/></h5>
                                            <div class="radio-toolbar">
                                                <c:forEach var="size" items="${requestScope.sizes}">
                                                    <input type="radio" id="fast${size}" name="size" value="${size}" required>
                                                    <label for="fast${size}">${size % 1 == 0 ? size.intValue() : size}</label>
                                                </c:forEach>
                                            </div>
                                        </div>
                                        <div class="table-responsive">
                                            <table class="table">
                                                <thead class="thead-dark">
                                                <tr>
                                                    <th scope="col"><fmt:message key="table.product"/></th>
                                                    <th scope="col"><fmt:message key="table.brand"/></th>
                                                    <th scope="col"><fmt:message key="table.quantity"/></th>
                                                    <th scope="col" class="text-right"><fmt:message key="table.price"/></th>
                                                </tr>
                                                </thead>
                                                <tbody>
                                                    <tr class="text-cart">
                                                        <td class="text-center">${footwear.art}</td>
                                                        <td class="text-center">${footwear.brand}</td>
                                                        <td class="text-center">1</td>
                                                        <td class="text-right"><span class="total-cart">$ ${footwear.price}</span></td>
                                                    </tr>
                                                </tbody>
                                            </table>
                                        </div>
                                    </div>
                                    <div class="modal-footer justify-content-between">
                                        <button type="button" class="btn btn-primary" data-dismiss="modal"><fmt:message key="product.fast.cancel"/></button>
                                        <button type="submit" class="btn btn-success text-uppercase"><fmt:message key="product.fast.confirm"/></button>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="row">
        <!-- Description -->
        <div class="col-12">
            <div class="card border-light mb-3">
                <div class="card-header text-white text-uppercase"><i class="fas fa-align-justify"></i> <fmt:message key="product.description"/></div>
                <div class="card-body">
                    <p class="card-text">
                       ${footwear.description}
                    </p>
                </div>
            </div>
        </div>


        <div class="col-12">
            <div class="card border-light mb-3">
                <div class="card-header text-white text-uppercase">
                    <i class="fas fa-heart"></i> <fmt:message key="product.also.like"/>
                </div>
                <div class="card-body">
                    <div class="row justify-content-center">
                        <c:forEach var="item" items="${requestScope.related}">
                            <div class="col-md-2 col-sm-3 my-3">
                                <div class="product-card">
                                    <div class="product-image">
                                        <a href="Controller?command=gotoproduct&art=${item.art}">
                                            <img class="pic-1" src="images/${item.imageLinks.get(0)}">
                                            <img class="pic-2" src="images/${item.imageLinks.size() > 1 ? item.imageLinks.get(1) : item.imageLinks.get(0)}">
                                        </a>
                                    </div>
                                    <div class="product-content">
                                        <h2 class="brand">${item.brand}</h2>
                                        <span class="model">${item.name}</span>
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

<jsp:include page="footer.jsp"/>

<!-- Modal image -->
<c:forEach var="i" begin="0" end="${footwear.imageLinks.size() - 1}">
    <div class="modal fade" id="productModal${i}" tabindex="-1" role="dialog" aria-labelledby="productModalLabel${i}" aria-hidden="true">
        <div class="modal-dialog modal-lg" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title text-center" id="productModalLabel${i}">${footwear.brand} ${footwear.name}</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <img class="img-fluid" src="images/${footwear.imageLinks.get(i)}" data-dismiss="modal" />
                </div>
            </div>
        </div>
    </div>
</c:forEach>


<div class="toast" id="cartToast" style="position: absolute; top: 70px; right: 2px;">
    <div class="toast-header">
        <strong class="mr-auto"><i class="fas fa-shopping-cart"></i> <fmt:message key="product.toast"/></strong>
        <button type="button" class="ml-2 mb-1 close" data-dismiss="toast">
            <span aria-hidden="true">&times;</span>
        </button>
    </div>
    <div class="toast-body">
        <c:choose>
            <c:when test="${'no'.equals(param.get('added'))}">
                <span class="text-success"><fmt:message key="product.toast.message.added"/><a href="Controller?command=gotocart"><br/><fmt:message key="product.toast.check.it"/></a></span>
            </c:when>
            <c:when test="${'yes'.equals(param.get('added'))}">
                <span class="text-warning"><fmt:message key="product.toast.message.already.added"/><a href="Controller?command=gotocart"><br/>Shopping Cart</a></span>
            </c:when>
            <c:when test="${'no_size'.equals(param.get('added'))}">
                <span class="text-warning"><fmt:message key="product.toast.message.no.size"/></span>
            </c:when>
            <c:when test="${'success'.equals(param.get('fast'))}">
                <span class="text-success"><fmt:message key="product.toast.message.fast.success"/><a href="Controller?command=gotoprofile"><br/><fmt:message key="product.toast.check.it"/></a></span>
            </c:when>
            <c:when test="${'fail'.equals(param.get('fast'))}">
                <span class="text-danger"><fmt:message key="product.toast.message.fast.fail"/></span>
            </c:when>
            <c:when test="${'not_logged_in'.equals(param.get('fast'))}">
                <span class="text-warning"><fmt:message key="product.toast.message.fast.not.loggedin"/></span>
            </c:when>
        </c:choose>
    </div>
</div>

<script type="text/javascript">
    $(document).ready(function(){
        if (${'yes'.equals(param.get('show'))})
        {
            $("#cartToast").toast({delay: 5000});
            $("#cartToast").toast('show');
        }
    });
</script>
</body>
</html>
