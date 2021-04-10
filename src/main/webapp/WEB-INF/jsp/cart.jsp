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
                <table class="table">
                    <thead class="thead-dark">
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
                            <tr class="text-cart">
                                <td> <div class="small-image-container"><img src="${item.footwear.imageLink}"  alt="item"/></div> </td>
                                <td>${item.footwear.art}</td>
                                <td>${item.footwear.brand}</td>
                                <td>
                                    <c:choose>
                                        <c:when test="${item.quantity == 0}">
                                        <select name="quantity" class="form-control quantity-select text-cart" disabled>
                                            <option>0</option>
                                        </select>
                                        </c:when>
                                        <c:otherwise>
                                            <select name="quantity" class="form-control quantity-select text-cart">
                                                <c:forEach var="i" begin="1" end="${item.quantity}">
                                                    <option value="${i}">${i}</option>
                                                </c:forEach>
                                            </select>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td>${item.size}</td>
                                    <%--                            <td><input class="form-control" type="text" value="1" /></td>--%>

                                <td class="text-right">$<span class="price_view">${item.footwear.price}</span></td>
                                <input type="hidden" class="price_data" name="price" value="${item.footwear.price}">
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
                            <td><span class="total-cart">Total</span></td>
                            <td class="text-right total-cart">$<span class="total-cart" id="total_val"></span></td>
                            <td></td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </div>
        <div class="col mb-2">
            <div class="row">
                <div class="col-sm-12  col-md-6">
                    <a href="Controller?command=gotocategory" class="btn btn-block btn-light">Continue Shopping</a>
                </div>
                <div class="col-sm-12 col-md-6 text-right">
                    <button class="btn btn-lg btn-block btn-success text-uppercase" type="submit">Checkout</button>
                </div>
            </div>
        </div>
    </div>

</div>

<script type="text/javascript">
    let selectList = document.getElementsByClassName("quantity-select");
    let priceDataList = document.getElementsByClassName("price_data");
    let priceViewList = document.getElementsByClassName("price_view");
    function countTotal(){
        let total = 0.0;
        for (let i = 0; i < selectList.length; i++) {
            let optionsList = selectList[i].options;
            let price = parseFloat(priceDataList[i].value);
            for (let j = 0; j < optionsList.length; j++) {
                if (optionsList[j].selected === true){
                    let price_count = (price * parseInt(optionsList[j].value));
                    total += price_count;
                    priceViewList[i].innerHTML = price_count.toString();
                }
            }
        }
        document.getElementById("total_val").innerHTML = total.toString();
    }
    countTotal()
    for (let i = 0; i < selectList.length; i++) {
        selectList[i].onchange = countTotal;
    }
</script>


<jsp:include page="footer.jsp"/>

</body>
</html>
