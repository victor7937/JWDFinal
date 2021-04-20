<%--
  Created by IntelliJ IDEA.
  User: victor
  Date: 12.03.21
  Time: 15:28
  To change this template use File | Settings | File Templates.
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="r" uri="http://lei-shoes/region-functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Profile</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <jsp:include page="includes.jsp"/>
    <link href="<c:url value='/css/register.css'/>" rel="stylesheet" type="text/css">
    <link href="<c:url value='/css/style.css'/>" rel="stylesheet" type="text/css">
</head>
<body>

<jsp:include page="header.jsp"/>

<section>
    <div class="container">
        <div class="card my-3 mx-auto w-75">
            <div class="card-body">
                <div class="row">
                    <div class="col-md-12">
                        <h2>Profile</h2>
                        <hr>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-12">
                        <form action="Controller" method="post">
                            <c:if test="${param.message.equals('err_empty')}">
                                <p class="text-danger text-center">Fields couldn't be empty</p>
                            </c:if>
                            <c:if test="${param.message.equals('err_email')}">
                                <p class="text-danger text-center">Email is incorrect</p>
                            </c:if>
                            <c:if test="${param.message.equals('err_phone')}">
                                <p class="text-danger text-center">Such phone is not valid</p>
                            </c:if>
                            <c:if test="${param.message.equals('not_upd')}">
                                <p class="text-danger text-center">Can't update profile</p>
                            </c:if>
                            <c:if test="${param.message.equals('upd_success')}">
                                <p class="text-success text-center">Profile was updated successfully</p>
                            </c:if>
                            <c:if test="${param.message.equals('change_psw_success')}">
                                <p class="text-success text-center">Password was changed successfully</p>
                            </c:if>
                            <input type="hidden" name="command" value="updateprofile">
                            <div class="form-group row">
                                <label for="email" class="col-4 col-form-label">Email</label>
                                <div class="col-6">
                                    <input id="email" name="email"  value="${requestScope.customer.email}" readonly class="form-control-plaintext" required type="email">
                                </div>
                            </div>
                            <div class="form-group row">
                                <label for="name" class="col-4 col-form-label">Name</label>
                                <div class="col-6">
                                    <input id="name" name="name" value="${requestScope.customer.name}" placeholder="Name" class="form-control" type="text" required>
                                </div>
                            </div>
                            <div class="form-group row">
                                <label for="country" class="col-4 col-form-label">Country</label>
                                <div class="col-6">
                                    <select id="country" name="country" class="form-control" type="text">
                                        <c:forEach var="re" items="${r:displayCountries(sessionScope.lang)}">
                                            <c:choose>
                                                <c:when test="${re.equals(requestScope.customer.country)}">
                                                    <option selected="">${re}</option>
                                                </c:when>
                                                <c:otherwise>
                                                    <option>${re}</option>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group row">
                                <label for="city" class="col-4 col-form-label">City</label>
                                <div class="col-6">
                                    <input id="city" name="city" placeholder="City" class="form-control" type="text" value="${customer.city}">
                                </div>
                            </div>
                            <div class="form-group row">
                                <label for="address" class="col-4 col-form-label">Address</label>
                                <div class="col-6">
                                    <input id="address" name="address" placeholder="Address" class="form-control" type="text" value="${customer.address}">
                                </div>
                            </div>
                            <div class="form-group row">
                                <label class="col-4 col-form-label">Phone number</label>
                                <div class="col-6" >
                                    <input name="phone" id="phonenum" placeholder="Phone number" class="form-control" type="text" value="${customer.phone}" maxlength="15">
                                </div>
                            </div>
                            <div class="form-group row">
                                <div class="offset-4 col-3">
                                    <button name="submit" type="submit" class="btn btn-primary">Update My Profile</button>
                                </div>
                                <div class="col-3">
                                    <a href="Controller?command=gotochangepassword" class="btn btn-primary">Change Password</a>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>

            </div>
        </div>
    </div>

</section>

<section>
    <div class="container my-4 card">
        <div class="row my-2">
            <div class="col-12">
                <h2>My orders</h2>
                <hr>
            </div>
        </div>
        <c:if test="${'success'.equals(param.get('change'))}">
            <p class="text-success text-center">Status was changed successfully!</p>
        </c:if>
        <c:if test="${'fail'.equals(param.get('change'))}">
            <p class="text-success text-center">Fail to change status</p>
        </c:if>
        <c:choose>
            <c:when test="${requestScope.orders.size() == 0}">
                <div class="row my-2">
                    <div class="col-12">
                        <h3 class="text-info">No orders yet</h3>
                    </div>
                </div>
            </c:when>
            <c:otherwise>
                <div class="row">
                    <div class="col-12">
                        <ul class="list-group row list-group-horizontal order-main-header px-4 my-3">
                            <li class="list-group-item col-2">Order #</li>
                            <li class="list-group-item col-2">For</li>
                            <li class="list-group-item col-2">Email</li>
                            <li class="list-group-item col-2">Price</li>
                            <li class="list-group-item col-2">Created at</li>
                            <li class="list-group-item col-2">Status</li>
                        </ul>
                        <div class="accordion mb-4" id="accordionExample">
                            <c:forEach var="order" items="${requestScope.orders}">
                                <div class="card">
                                    <div class="card-header bg-light" id="heading${order.id}">
                                        <h2 class="mb-0">
                                            <button class="btn btn-block" type="button" data-toggle="collapse" data-target="#collapse${order.id}" aria-expanded="true" aria-controls="collapse${order.id}">
                                                <ul class="list-group list-group-horizontal row list-group-flush order-header">
                                                    <li class="list-group-item col-2">#${order.id}</li>
                                                    <li class="list-group-item col-2">${order.customer.name}</li>
                                                    <li class="list-group-item col-2">${order.customer.email}</li>
                                                    <li class="list-group-item col-2">${order.price}</li>
                                                    <li class="list-group-item col-2">${order.date.toLocalDate().toString()} <br/>${order.date.toLocalTime().toString()}</li>
                                                    <c:choose>
                                                        <c:when test="${order.orderStatus.toString().equals('WAITING')}">
                                                            <li class="list-group-item col-2 text-warning">Waiting</li>
                                                        </c:when>
                                                        <c:when test="${order.orderStatus.toString().equals('APPROVED')}">
                                                            <li class="list-group-item col-2 text-success">Approved</li>
                                                        </c:when>
                                                        <c:when test="${order.orderStatus.toString().equals('DECLINE')}">
                                                            <li class="list-group-item col-2 text-danger">Decline</li>
                                                        </c:when>
                                                        <c:when test="${order.orderStatus.toString().equals('COMPLETE')}">
                                                            <li class="list-group-item col-2 text-info">Complete</li>
                                                        </c:when>
                                                    </c:choose>
                                                </ul>
                                            </button>
                                        </h2>
                                    </div>

                                    <div id="collapse${order.id}" class="collapse" aria-labelledby="heading${order.id}" data-parent="#accordionExample">
                                        <div class="card-body">
                                            <ul class="list-group row list-group-horizontal delivery justify-content-center">
                                                <li class="list-group-item col-1"><i class="fa fa-2x fa-truck text-dark"></i></li>
                                                <li class="list-group-item col-2">${order.customer.country}</li>
                                                <li class="list-group-item col-2">${order.customer.city}</li>
                                                <li class="list-group-item col-3r">${order.customer.address}</li>
                                                <c:if test="${order.orderStatus.toString() == 'WAITING' || order.orderStatus.toString() == 'APPROVED'}">
                                                    <li class="list-group-item col-4">
                                                        <form method="post" action="Controller">
                                                            <input type="hidden" name="command" value="userdecline">
                                                            <input type="hidden" name="order_id" value="${order.id}">
                                                            <div class="input-group justify-content-center">
                                                                <button type="submit" class="btn btn-danger">Decline Order</button>
                                                            </div>
                                                        </form>
                                                    </li>
                                                </c:if>
                                            </ul>
                                            <div class="table-responsive mt-4">
                                                <table class="table table-bordered">
                                                    <thead class="thead-light">
                                                    <tr>
                                                        <th scope="col">Product</th>
                                                        <th scope="col">Brand</th>
                                                        <th scope="col">Quantity</th>
                                                        <th scope="col">Size</th>
                                                            <%--                            <th scope="col" class="text-center">Quantity</th>--%>
                                                        <th scope="col" class="text-right">Price</th>
                                                    </tr>
                                                    </thead>
                                                    <tbody>
                                                    <c:forEach var="item" items="${order.items}">
                                                        <tr>
                                                            <td>${item.footwear.art}</td>
                                                            <td>${item.footwear.brand}</td>
                                                            <td>${item.quantity}</td>
                                                            <td>${item.size}</td>
                                                            <td class="text-right">${item.quantity * item.footwear.price}</td>
                                                        </tr>
                                                    </c:forEach>
                                                    </tbody>
                                                </table>
                                            </div>

                                        </div>
                                    </div>
                                </div>
                            </c:forEach>
                        </div>
                    </div>
                </div>
            </c:otherwise>
        </c:choose>

    </div>

</section>

<jsp:include page="footer.jsp"/>

</body>
</html>
