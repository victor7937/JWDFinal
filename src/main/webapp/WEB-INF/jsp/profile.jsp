<%--
  Created by IntelliJ IDEA.
  User: victor
  Date: 12.03.21
  Time: 15:28
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="r" uri="http://lei-shoes/region-functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <jsp:include page="includes.jsp"/>
    <fmt:setLocale value="${sessionScope.lang}" scope="session"/>
    <fmt:setBundle basename="application"/>
    <link href="<c:url value='/css/register.css'/>" rel="stylesheet" type="text/css">
    <link href="<c:url value='/css/style.css'/>" rel="stylesheet" type="text/css">
    <title><fmt:message key="profile.title"/></title>
</head>
<body>

<jsp:include page="header.jsp"/>

<section>
    <div class="container">
        <div class="card my-3 mx-auto w-75">
            <div class="card-body">
                <div class="row">
                    <div class="col-md-12">
                        <h2><fmt:message key="profile.title"/></h2>
                        <hr>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-12">
                        <form action="Controller" method="post">
                            <c:if test="${param.message.equals('err_empty')}">
                                <p class="text-danger text-center"><fmt:message key="profile.message.not.empty"/></p>
                            </c:if>
                            <c:if test="${param.message.equals('err_email')}">
                                <p class="text-danger text-center"><fmt:message key="profile.message.email.incorrect"/></p>
                            </c:if>
                            <c:if test="${param.message.equals('err_phone')}">
                                <p class="text-danger text-center"><fmt:message key="profile.message.phone.not.valid"/></p>
                            </c:if>
                            <c:if test="${param.message.equals('not_upd')}">
                                <p class="text-danger text-center"><fmt:message key="profile.message.cant.update"/></p>
                            </c:if>
                            <c:if test="${param.message.equals('upd_success')}">
                                <p class="text-success text-center"><fmt:message key="profile.message.update.success"/></p>
                            </c:if>
                            <c:if test="${param.message.equals('change_psw_success')}">
                                <p class="text-success text-center"><fmt:message key="profile.message.password.success"/></p>
                            </c:if>
                            <input type="hidden" name="command" value="updateprofile">
                            <div class="form-group row">
                                <label for="email" class="col-4 col-form-label"><fmt:message key="profile.email"/></label>
                                <div class="col-6">
                                    <input id="email" name="email"  value="${requestScope.customer.email}" readonly class="form-control-plaintext" required type="email">
                                </div>
                            </div>
                            <div class="form-group row">
                                <label for="name" class="col-4 col-form-label"><fmt:message key="profile.name"/></label>
                                <div class="col-6">
                                    <input id="name" name="name" value="${requestScope.customer.name}" placeholder="<fmt:message key="profile.name"/>" class="form-control" type="text" required>
                                </div>
                            </div>
                            <div class="form-group row">
                                <label for="country" class="col-4 col-form-label"><fmt:message key="profile.country"/></label>
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
                                <label for="city" class="col-4 col-form-label"><fmt:message key="profile.city"/></label>
                                <div class="col-6">
                                    <input id="city" name="city" placeholder="<fmt:message key="profile.city"/>" class="form-control" type="text" value="${customer.city}">
                                </div>
                            </div>
                            <div class="form-group row">
                                <label for="address" class="col-4 col-form-label"><fmt:message key="profile.address"/></label>
                                <div class="col-6">
                                    <input id="address" name="address" placeholder="<fmt:message key="profile.address"/>" class="form-control" type="text" value="${customer.address}">
                                </div>
                            </div>
                            <div class="form-group row">
                                <label class="col-4 col-form-label"><fmt:message key="profile.phone"/></label>
                                <div class="col-6" >
                                    <input name="phone" id="phonenum" placeholder="<fmt:message key="profile.phone"/>" class="form-control" type="text" value="${customer.phone}" maxlength="15">
                                </div>
                            </div>
                            <div class="form-group row">
                                <div class="offset-4 col-3">
                                    <button name="submit" type="submit" class="btn btn-primary"><fmt:message key="profile.update"/></button>
                                </div>
                                <div class="col-3">
                                    <a href="Controller?command=gotochangepassword" class="btn btn-primary"><fmt:message key="profile.change.password"/></a>
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
                <h2><fmt:message key="profile.orders.title"/></h2>
                <hr>
            </div>
        </div>
        <c:if test="${'success'.equals(param.get('change'))}">
            <p class="text-success text-center"><fmt:message key="profile.orders.message.success"/></p>
        </c:if>
        <c:if test="${'fail'.equals(param.get('change'))}">
            <p class="text-success text-center"><fmt:message key="profile.orders.message,fail"/></p>
        </c:if>
        <c:choose>
            <c:when test="${requestScope.orders.size() == 0}">
                <div class="row my-2">
                    <div class="col-12">
                        <h3 class="text-info"><fmt:message key="profile.orders.no"/></h3>
                    </div>
                </div>
            </c:when>
            <c:otherwise>
                <div class="row">
                    <div class="col-12">
                        <ul class="list-group row list-group-horizontal order-main-header px-4 my-3">
                            <li class="list-group-item col-2"><fmt:message key="table.order.number"/></li>
                            <li class="list-group-item col-2"><fmt:message key="table.for"/></li>
                            <li class="list-group-item col-2"><fmt:message key="table.email"/></li>
                            <li class="list-group-item col-2"><fmt:message key="table.price"/></li>
                            <li class="list-group-item col-2"><fmt:message key="table.created.at"/></li>
                            <li class="list-group-item col-2"><fmt:message key="table.status"/></li>
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
                                                            <li class="list-group-item col-2 text-warning"><fmt:message key="table.orders.waiting"/></li>
                                                        </c:when>
                                                        <c:when test="${order.orderStatus.toString().equals('APPROVED')}">
                                                            <li class="list-group-item col-2 text-success"><fmt:message key="table.orders.approved"/></li>
                                                        </c:when>
                                                        <c:when test="${order.orderStatus.toString().equals('DECLINE')}">
                                                            <li class="list-group-item col-2 text-danger"><fmt:message key="table.orders.decline"/></li>
                                                        </c:when>
                                                        <c:when test="${order.orderStatus.toString().equals('COMPLETE')}">
                                                            <li class="list-group-item col-2 text-info"><fmt:message key="table.orders.complete"/></li>
                                                        </c:when>
                                                    </c:choose>
                                                </ul>
                                            </button>
                                        </h2>
                                    </div>

                                    <div id="collapse${order.id}" class="collapse" aria-labelledby="heading${order.id}" data-parent="#accordionExample">
                                        <div class="card-body">
                                            <ul class="list-group row list-group-horizontal delivery justify-content-center">
                                                <li class="list-group-item col-1"><i class="fas fa-2x fa-truck text-dark"></i></li>
                                                <li class="list-group-item col-2">${order.customer.country}</li>
                                                <li class="list-group-item col-2">${order.customer.city}</li>
                                                <li class="list-group-item col-3r">${order.customer.address}</li>
                                                <c:if test="${order.orderStatus.toString() == 'WAITING' || order.orderStatus.toString() == 'APPROVED'}">
                                                    <li class="list-group-item col-4">
                                                        <form method="post" action="Controller">
                                                            <input type="hidden" name="command" value="userdecline">
                                                            <input type="hidden" name="order_id" value="${order.id}">
                                                            <div class="input-group justify-content-center">
                                                                <button type="submit" class="btn btn-danger"><fmt:message key="profile.orders.decline"/></button>
                                                            </div>
                                                        </form>
                                                    </li>
                                                </c:if>
                                            </ul>
                                            <div class="table-responsive mt-4">
                                                <table class="table table-bordered">
                                                    <thead class="thead-light">
                                                    <tr>
                                                        <th scope="col"><fmt:message key="table.product"/></th>
                                                        <th scope="col"><fmt:message key="table.brand"/></th>
                                                        <th scope="col"><fmt:message key="table.quantity"/></th>
                                                        <th scope="col"><fmt:message key="table.size"/></th>
                                                            <%--                            <th scope="col" class="text-center">Quantity</th>--%>
                                                        <th scope="col" class="text-right"><fmt:message key="table.price"/></th>
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
