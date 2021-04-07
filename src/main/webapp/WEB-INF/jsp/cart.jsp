<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Lei Shoes</title>
    <jsp:include page="includes.jsp"/>
    <link href="<c:url value='/css/style.css'/>" rel="stylesheet" type="text/css">
</head>
<body>

<jsp:include page="header.jsp"/>

<section class="jumbotron text-center">
    <div class="container">
        <h1 class="jumbotron-heading">CART</h1>
     </div>
</section>

<div class="container mb-4">
    <div class="row">
        <div class="col-12">
            <div class="table-responsive">
                <table class="table table-striped">
                    <thead>
                        <tr>
                            <th scope="col"> </th>
                            <th scope="col">Product</th>
                            <th scope="col">Brand</th>
                            <th scope="col">Quantity</th>
                            <th scope="col">Size</th>
<%--                            <th scope="col" class="text-center">Quantity</th>--%>
                            <th scope="col" class="text-right">Price</th>
                            <th> </th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="item" items="${requestScope.itemsList}">
                            <tr>
                                <td> <div class="small-image-container"><img src="${item.footwear.imageLink}"  alt="item"/></div> </td>
                                <td>${item.footwear.art}</td>
                                <td>${item.footwear.brand}</td>
                                <td>
                                    <select name="quantity" class="form-control">
                                        <option>1</option>
                                        <option>2</option>
                                    </select>
                                </td>
                                <td>${item.size}</td>
                                    <%--                            <td><input class="form-control" type="text" value="1" /></td>--%>
                                <td class="text-right">$${item.footwear.price}</td>
                                <td class="text-right">
                                    <form method="post" action="Controller">
                                        <input type="hidden" name="command" value="deletecartitem">
                                        <input type="hidden" name="art" value="${item.footwear.art}">
                                        <input type="hidden" name="size" value="${item.size}">
                                        <button type="submit" class="btn btn-sm btn-danger"><i class="fa fa-trash"></i> </button>
                                    </form>
                                </td>
                            </tr>
                        </c:forEach>

<%--                        <tr>--%>
<%--                            <td><img src="https://dummyimage.com/50x50/55595c/fff" /> </td>--%>
<%--                            <td>Product Name Toto</td>--%>
<%--                            <td>In stock</td>--%>
<%--                            <td><input class="form-control" type="text" value="1" /></td>--%>
<%--                            <td class="text-right">33,90 €</td>--%>
<%--                            <td class="text-right"><button class="btn btn-sm btn-danger"><i class="fa fa-trash"></i> </button> </td>--%>
<%--                        </tr>--%>
<%--                        <tr>--%>
<%--                            <td><img src="https://dummyimage.com/50x50/55595c/fff" /> </td>--%>
<%--                            <td>Product Name Titi</td>--%>
<%--                            <td>In stock</td>--%>
<%--                            <td><input class="form-control" type="text" value="1" /></td>--%>
<%--                            <td class="text-right">70,00 €</td>--%>
<%--                            <td class="text-right"><button class="btn btn-sm btn-danger"><i class="fa fa-trash"></i> </button> </td>--%>
<%--                        </tr>--%>
<%--                        <tr>--%>
<%--                            <td></td>--%>
<%--                            <td></td>--%>
<%--                            <td></td>--%>
<%--                            <td></td>--%>
<%--                            <td></td>--%>
<%--                            <td>Sub-Total</td>--%>
<%--                            <td class="text-right">$ 255,90</td>--%>
<%--                        </tr>--%>
<%--                        <tr>--%>
<%--                            <td></td>--%>
<%--                            <td></td>--%>
<%--                            <td></td>--%>
<%--                            <td></td>--%>
<%--                            <td>Shipping</td>--%>
<%--                            <td class="text-right">$ 6,90 </td>--%>
<%--                            <td></td>--%>
<%--                        </tr>--%>
                        <tr>
                            <td></td>
                            <td></td>
                            <td></td>
                            <td></td>
                            <td></td>
                            <td></td>
                            <td></td>
                        </tr>
                        <tr>
                            <td></td>
                            <td></td>
                            <td></td>
                            <td></td>
                            <td><strong>Total</strong></td>
                            <td class="text-right"><strong>$${requestScope.total}</strong></td>
                            <td></td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </div>
        <div class="col mb-2">
            <div class="row">
                <div class="col-sm-12  col-md-6">
                    <button class="btn btn-block btn-light">Continue Shopping</button>
                </div>
                <div class="col-sm-12 col-md-6 text-right">
                    <button class="btn btn-lg btn-block btn-success text-uppercase">Checkout</button>
                </div>
            </div>
        </div>
    </div>
</div>



<jsp:include page="footer.jsp"/>

</body>
</html>
