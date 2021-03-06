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
    <jsp:include page="includes.jsp"/>
    <link href="<c:url value='/css/style.css'/>" rel="stylesheet" type="text/css">
    <title><fmt:message key="cart.title"/></title>
</head>
<body>

<jsp:include page="header.jsp"/>

<section class="jumbotron text-center">
    <div class="container">
        <h1 class="jumbotron-heading text-uppercase"><fmt:message key="cart.title"/></h1>
     </div>
</section>
<form method="post" action="Controller">
    <input type="hidden" name="command" value="neworder">
    <div class="container mb-4">
        <div class="row">
            <div class="col-12">
                <div class="table-responsive">
                    <table class="table">
                        <thead class="thead-dark">
                            <tr>
                                <th scope="col"> </th>
                                <th scope="col"><fmt:message key="table.product"/></th>
                                <th scope="col"><fmt:message key="table.brand"/></th>
                                <th scope="col"><fmt:message key="table.quantity"/></th>
                                <th scope="col"><fmt:message key="table.size"/></th>
    <%--                            <th scope="col" class="text-center">Quantity</th>--%>
                                <th scope="col" class="text-right"><fmt:message key="table.price"/></th>
                                <th> </th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="item" items="${requestScope.itemsList}">
                                <tr class="text-cart">
                                    <td> <div class="small-image-container"><img src="images/${item.footwear.imageLinks.get(0)}" alt="item"/></div> </td>
                                    <td>${item.footwear.art}</td>
                                    <input type="hidden" name="art" value="${item.footwear.art}">
                                    <td>${item.footwear.brand}</td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${item.maxQuantity == 0}">
                                            <select name="quantity" class="form-control quantity-select text-cart" disabled>
                                                <option value="0">0</option>
                                            </select>
                                            </c:when>
                                            <c:otherwise>
                                                <select name="quantity" class="form-control quantity-select text-cart">
                                                    <c:forEach var="i" begin="1" end="${item.maxQuantity}">
                                                        <option value="${i}">${i}</option>
                                                    </c:forEach>
                                                </select>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td>${item.size}</td>
                                        <input type="hidden" name="size" value="${item.size}">
                                        <%--                            <td><input class="form-control" type="text" value="1" /></td>--%>

                                    <td class="text-right">$<span class="price_view">${item.footwear.price}</span></td>
                                    <input type="hidden" class="price_data" name="price" value="${item.footwear.price}">
                                    <td class="text-right">
                                         <a href="Controller?command=deletecartitem&art=${item.footwear.art}&size=${item.size}" class="btn btn-sm btn-danger"><i class="fas fa-trash"></i> </a>
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
                                <td><span class="total-cart"><fmt:message key="cart.total"/></span></td>
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
                        <a href="Controller?command=gotocategory" class="btn btn-block btn-light"><fmt:message key="cart.continue"/></a>
                    </div>
                    <div class="col-sm-12 col-md-6 text-right">
                        <button class="btn btn-lg btn-block btn-success text-uppercase" type="submit"><fmt:message key="cart.checkout"/></button>
                    </div>
                </div>
            </div>
        </div>

    </div>
</form>

<div class="toast" id="orderToast" style="position: absolute; top: 53px; right: 2px;">
    <div class="toast-header">
        <strong class="mr-auto"><i class="fas fa-shopping-cart"></i> <fmt:message key="cart.toast.message"/></strong>
        <button type="button" class="ml-2 mb-1 close" data-dismiss="toast">
            <span aria-hidden="true">&times;</span>
        </button>
    </div>
    <div class="toast-body">
        <c:choose>
            <c:when test="${'success'.equals(param.get('message'))}">
                <span class="text-success"><fmt:message key="cart.toast.success"/><a href="Controller?command=gotoprofile"><br/><fmt:message key="product.toast.check.it"/></a></span>
            </c:when>
            <c:when test="${'not_logged_in'.equals(param.get('message'))}">
                <span class="text-warning"><fmt:message key="cart.toast.not.logged.in"/></span>
            </c:when>
            <c:when test="${'fail'.equals(param.get('message'))}">
                <span class="text-danger"><fmt:message key="cart.toast.fail"/></span>
            </c:when>
        </c:choose>
    </div>
</div>

<script type="text/javascript">
    $(document).ready(function(){

        if (${param.get('message') != null})
        {
            $("#orderToast").toast({delay: 5000});
            $("#orderToast").toast('show');
        }
    });
</script>

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
