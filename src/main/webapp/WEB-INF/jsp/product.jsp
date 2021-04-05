<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Product Page</title>
    <jsp:include page="includes.jsp"/>
    <link href="<c:url value='/css/style.css'/>" rel="stylesheet" type="text/css">
    <link href="<c:url value='/css/product.css'/>" rel="stylesheet" type="text/css">
    <c:set var="footwear" value="${requestScope.footwear}" scope="request"/>

</head>
<body>

<jsp:include page="header.jsp"/>

<section class="jumbotron text-center">
    <div class="container">
        <h1 class="jumbotron-heading">E-COMMERCE PRODUCT</h1>
        <p class="lead text-muted mb-0">Lorem ipsum dolor sit amet, consectetur adipisicing elit. Ab adipisci consequatur ea error expedita illo in quam? Doloribus esse est, hic iste nostrum officiis perspiciatis repudiandae similique tempora temporibus vitae?</p>
    </div>
</section>
<<div class="container">
    <div class="row">
        <div class="col">
            <nav aria-label="breadcrumb">
                <ol class="breadcrumb">
                    <li class="breadcrumb-item"><a href="lei-shoes">Home</a></li>
                    <li class="breadcrumb-item"><a href="Controller?command=gotocategory">Category</a></li>
                    <li class="breadcrumb-item active" aria-current="page">Product</li>
                </ol>
            </nav>
        </div>
    </div>
</div>
<div class="container">
    <div class="row">
        <!-- Image -->
<%--        <div class="col-12 col-lg-6">--%>
<%--            <div class="card bg-light mb-3">--%>
<%--                <div class="card-body">--%>
<%--                    <a href="" data-toggle="modal" data-target="#productModal">--%>
<%--                        <img class="img-fluid" src="https://dummyimage.com/800x800/55595c/fff" />--%>
<%--                        <p class="text-center">Zoom</p>--%>
<%--                    </a>--%>
<%--                </div>--%>
<%--            </div>--%>
<%--        </div>--%>
        <div class="col-12 col-lg-6">
            <div id="carouselExampleFade" class="carousel slide carousel-fade" data-ride="carousel" data-touch="false" data-interval="false">
                <ol class="carousel-indicators">
                    <li data-target="#carouselExampleFade" data-slide-to="0" class="active"></li>
                    <li data-target="#carouselExampleFade" data-slide-to="1"></li>
                    <li data-target="#carouselExampleFade" data-slide-to="2"></li>
                </ol>
                <div class="carousel-inner">
                    <div class="carousel-item active">
<%--                        <img src="https://dummyimage.com/800x800/55595c/fff" class="d-block w-100" alt="...">--%>
                        <a href="" data-toggle="modal" data-target="#productModal">
                            <img class="img-fluid" src="${footwear.imageLink}"  alt="1"/>
                        </a>
                    </div>
                    <div class="carousel-item">
                        <%--                        <img src="https://dummyimage.com/800x800/55595c/fff" class="d-block w-100" alt="...">--%>
                        <a href="" data-toggle="modal" data-target="#productModal">
                            <img class="img-fluid" src="${footwear.imageLink}"  alt="2"/>
                        </a>
                    </div>
                    <div class="carousel-item">
                        <%--                        <img src="https://dummyimage.com/800x800/55595c/fff" class="d-block w-100" alt="...">--%>
                        <a href="" data-toggle="modal" data-target="#productModal">
                            <img class="img-fluid" src="${footwear.imageLink}" alt="3" />
                        </a>
                    </div>
                </div>
                <a class="carousel-control-prev" href="#carouselExampleFade" role="button" data-slide="prev">
                    <span class="carousel-control-prev-icon bg-secondary" aria-hidden="true"></span>
                    <span class="sr-only">Previous</span>
                </a>
                <a class="carousel-control-next" href="#carouselExampleFade" role="button" data-slide="next">
                    <span class="carousel-control-next-icon bg-secondary" aria-hidden="true"></span>
                    <span class="sr-only">Next</span>
                </a>
            </div>
        </div>

        <!-- Add to cart -->
        <div class="col-12 col-lg-6 add_to_cart_block">
            <div class="card mb-3">
                <div class="card-body">
<%--                    <p class="price">99.00 $</p>--%>
<%--                    <p class="price_discounted">149.90 $</p>--%>
                    <div class="product-title-brand mb-1">${footwear.brand}</div>
                    <div class="product-title-name mb-1">${footwear.name}</div>
                    <div class="price-title mb-2">${footwear.price}<span>${footwear.price}</span></div>
                    <hr class="mb-2">
<%--    <p><span class="price mr-1"><strong>$12.99</strong></span></p>--%>
                    <div class="table-responsive mb-2">
                        <table class="table table-sm table-borderless mb-0">
                            <tbody>
                            <tr>
                                <th class="pl-0 w-25" scope="row"><div class="product-header">Model</div></th>
                                <td><div class="product-value">${footwear.art}</div></td>
                            </tr>
                            <tr>
                                <th class="pl-0 w-25" scope="row"><div class="product-header">Color</div></th>
                                <td><div class="product-value">${footwear.color}</div></td>
                            </tr>
                            <tr>
                                <th class="pl-0 w-25" scope="row"><div class="product-header">Category</div></th>
                                <td><a class="product-value" href="Controller?command=gotocategory&category=${footwear.category}">${footwear.category}</a></td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                    <hr class="mb-2">

                    <form method="get" action="Controller?command=gotocart">
                        <div class="form-group mb-2">
                            <div class="radio-toolbar">
                                <input type="radio" id="37" name="radioFruit" value="apple">
                                <label for="37">37</label>

                                <input type="radio" id="35" name="radioFruit" value="banana">
                                <label for="35">35</label>

                                <input type="radio" id="36" name="radioFruit" value="orange">
                                <label for="36">36</label>
                            </div>
                        </div>




<%--                       <label for="colors">Color</label>--%>
<%--                            <select class="custom-select" id="colors">--%>
<%--                                <option selected>Select</option>--%>
<%--                                <option value="1">Blue</option>--%>
<%--                                <option value="2">Red</option>--%>
<%--                                <option value="3">Green</option>--%>
<%--                            </select>--%>
<%--                        </div>--%>
<%--                        <div class="form-group">--%>
<%--                            <label>Quantity :</label>--%>
<%--                            <div class="input-group mb-3">--%>
<%--                                <div class="input-group-prepend">--%>
<%--                                    <button type="button" class="quantity-left-minus btn btn-danger btn-number"  data-type="minus" data-field="">--%>
<%--                                        <i class="fa fa-minus"></i>--%>
<%--                                    </button>--%>
<%--                                </div>--%>
<%--                                <input type="text" class="form-control"  id="quantity" name="quantity" min="1" max="100" value="1">--%>
<%--                                <div class="input-group-append">--%>
<%--                                    <button type="button" class="quantity-right-plus btn btn-success btn-number" data-type="plus" data-field="">--%>
<%--                                        <i class="fa fa-plus"></i>--%>
<%--                                    </button>--%>
<%--                                </div>--%>
<%--                            </div>--%>

                        <a href="Controller?command=gotocart" class="btn add-to-card-btn btn-lg btn-block">
                            <i class="fa fa-shopping-cart"></i> Add To Cart
                        </a>
                    </form>
                    <div class="product_rassurance">
                        <ul class="list-inline">
                            <li class="list-inline-item"><i class="fa fa-truck fa-2x"></i><br/>Fast delivery</li>
                            <li class="list-inline-item"><i class="fa fa-credit-card fa-2x"></i><br/>Secure payment</li>
                            <li class="list-inline-item"><i class="fa fa-phone fa-2x"></i><br/>+375 29 180-25-50</li>
                        </ul>
                    </div>
                    <div class="reviews_product p-3 mb-2 ">
                        3 reviews
                        <i class="fa fa-star"></i>
                        <i class="fa fa-star"></i>
                        <i class="fa fa-star"></i>
                        <i class="fa fa-star"></i>
                        <i class="fa fa-star"></i>
                        (4/5)
                        <a class="pull-right" href="#reviews">View all reviews</a>
                    </div>
<%--                    <div class="datasheet p-3 mb-2 bg-info text-white">--%>
<%--                        <a href="" class="text-white"><i class="fa fa-file-text"></i> Download DataSheet</a>--%>
<%--                    </div>--%>
                </div>
            </div>
        </div>
    </div>

    <div class="row">
        <!-- Description -->
        <div class="col-12">
            <div class="card border-light mb-3">
                <div class="card-header text-white text-uppercase"><i class="fa fa-align-justify"></i> Description</div>
                <div class="card-body">
                    <p class="card-text">
                       ${footwear.description}
                    </p>
                </div>
            </div>
        </div>

        <!-- Reviews -->
        <div class="col-12" id="reviews">
            <div class="card border-light mb-3">
                <div class="card-header text-white text-uppercase"><i class="fa fa-comment"></i> Reviews</div>
                <div class="card-body">
                    <div class="review">
                        <span class="glyphicon glyphicon-calendar" aria-hidden="true"></span>
                        <meta itemprop="datePublished" content="02-11-2020">November 02, 2020

                        <span class="fa fa-star"></span>
                        <span class="fa fa-star"></span>
                        <span class="fa fa-star"></span>
                        <span class="fa fa-star"></span>
                        <span class="fa fa-star"></span>
                        by Paul Smith
                        <p class="blockquote">
                            <p class="mb-0">Lorem ipsum dolor sit amet, consectetur adipisicing elit. Adipisci, beatae deserunt eveniet excepturi, facilis illo in laboriosam maxime</p>
                        </p>
                        <hr>
                    </div>
                    <div class="review">
                        <span class="glyphicon glyphicon-calendar" aria-hidden="true"></span>
                        <meta itemprop="datePublished" content="02-03-2021">March 02, 2021

                        <span class="fa fa-star" aria-hidden="true"></span>
                        <span class="fa fa-star" aria-hidden="true"></span>
                        <span class="fa fa-star" aria-hidden="true"></span>
                        <span class="fa fa-star" aria-hidden="true"></span>
                        <span class="fa fa-star" aria-hidden="true"></span>
                        by Paul Smith
                        <p class="blockquote">
                            <p class="mb-0"> Lorem ipsum dolor sit amet, consectetur adipisicing elit. Adipisci, beatae deserunt eveniet excepturi, facilis illo in laboriosam maxime</p>
                        </p>
                        <hr>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<jsp:include page="footer.jsp"/>

<!-- Modal image -->
<div class="modal fade" id="productModal" tabindex="-1" role="dialog" aria-labelledby="productModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-lg" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title text-center" id="productModalLabel">Product title</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <img class="img-fluid" src="https://cdn-images.farfetch-contents.com/16/11/50/16/16115016_32008727_1000.jpg" data-dismiss="modal" />
            </div>
<%--            <div class="modal-footer">--%>
<%--                <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>--%>
<%--            </div>--%>
        </div>
    </div>
</div>

<script type="text/javascript">
    //Plus & Minus for Quantity product
    $(document).ready(function(){
        var quantity = 1;

        $('.quantity-right-plus').click(function(e){
            e.preventDefault();
            var quantity = parseInt($('#quantity').val());
            $('#quantity').val(quantity + 1);
        });

        $('.quantity-left-minus').click(function(e){
            e.preventDefault();
            var quantity = parseInt($('#quantity').val());
            if(quantity > 1){
                $('#quantity').val(quantity - 1);
            }
        });

    });
</script>
</body>
</html>
