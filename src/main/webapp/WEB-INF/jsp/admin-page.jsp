<%--
  Created by IntelliJ IDEA.
  User: victor
  Date: 7.04.21
  Time: 15:53
  To change this template use File | Settings | File Templates.
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <jsp:include page="includes.jsp"/>
    <link href="<c:url value='/css/style.css'/>" rel="stylesheet" type="text/css">
    <c:set var="pageCount" value="${requestScope.pageCount}"/>
    <c:set var="currentPage" value="${requestScope.currentPage}"/>
    <fmt:setLocale value="${sessionScope.lang}" scope="session"/>
    <fmt:setBundle basename="application"/>
    <title><fmt:message key="admin.title"/></title>
</head>

<body>

<jsp:include page="header.jsp"/>

<section class="jumbotron text-center">
    <div class="container">
        <h1 class="jumbotron-heading text-uppercase"><fmt:message key="admin.title"/></h1>
    </div>
</section>

<section>
    <div class="container">
        <nav>
            <div class="nav nav-tabs row" id="nav-tab" role="tablist">
                    <a class="nav-link col-6 text-center ${"showusers".equals(param.get("command")) ? 'active' : ''}" id="nav-users-tab"  href="Controller?command=gotoadminpage"  aria-selected="${"showusers".equals(param.get("command")) ? 'true' : 'false'}"><fmt:message key="admin.users"/></a>
                    <a class="nav-link col-6 text-center ${"showorders".equals(param.get("command")) ? 'active' : ''}" id="nav-orders-tab"  href="Controller?command=showorders" aria-selected="${"showorders".equals(param.get("command")) ? 'true' : 'false'}"><fmt:message key="admin.orders"/></a>
<%--                    <a class="nav-link col-3 text-center" id="nav-footwear-tab" data-toggle="tab" href="#nav-footwear" role="tab" aria-controls="nav-footwear" aria-selected="false">Footwear</a>--%>
            </div>
        </nav>
    </div>

    <div class="tab-content" id="nav-tabContent">
        <div class="tab-pane fade ${"showusers".equals(param.get("command")) ? 'active show' : ''}" id="nav-users" role="tabpanel" aria-labelledby="nav-users-tab">

            <div class="container mb-4 tab-page">
                <div class="row">
                    <div class="col-12">
                        <div class="table-responsive mt-4">
                            <table class="table table-bordered table-hover">
                                <thead class="thead-dark">
                                <tr>
                                    <th scope="col"> </th>
                                    <th scope="col"><fmt:message key="table.name"/></th>
                                    <th scope="col"><fmt:message key="table.email"/></th>
                                    <th scope="col"><fmt:message key="table.phone"/></th>
                                    <th scope="col"><fmt:message key="profile.country"/></th>
                                    <%--                            <th scope="col" class="text-center">Quantity</th>--%>
                                    <th scope="col"><fmt:message key="profile.city"/></th>
                                    <th scope="col"><fmt:message key="profile.address"/></th>
                                    <th></th>
                                    <th></th>
                                </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="customer" items="${requestScope.customers}">
                                        <tr>
                                            <td>
                                                <c:choose>
                                                    <c:when test="${'BLOCK'.equals(customer.role.toString())}">
                                                        <div class="text-danger">
                                                            <i class="fas fa-2x fa-ban"></i>
                                                        </div>
                                                    </c:when>
                                                    <c:when test="${'ADMIN'.equals(customer.role.toString())}">
                                                        <div class="text-info">
                                                            <i class="fas fa-2x fa-crown"></i>
                                                        </div>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <div class="text-dark">
                                                            <i class="fas fa-2x fa-user-circle"></i>
                                                        </div>
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>
                                            <td>${customer.name}</td>
                                            <td>${customer.email}</td>
                                            <td>${customer.phone}</td>
                                            <td>${customer.country}</td>
                                                <%--                            <td><input class="form-control" type="text" value="1" /></td>--%>
                                            <td>${customer.city}</td>
                                            <td>${customer.address}</td>

                                                <c:choose>
                                                    <c:when test="${'BLOCK'.equals(customer.role.toString())}">
                                                        <td>
                                                            <form method="post" action="Controller">
                                                                <input type="hidden" name="command" value="unblockuser">
                                                                <input type="hidden" name="email" value="${customer.email}">
                                                                <button type="submit" data-toggle="tooltip" data-placement="top" title="<fmt:message key="admin.action.unblock"/>" class="btn btn-sm btn-info"><i class="fas fa-unlock-alt"></i> </button>
                                                            </form>
                                                        </td>
                                                    </c:when>
                                                    <c:when test="${'USER'.equals(customer.role.toString())}">
                                                        <td class="text-center">
                                                            <form method="post" action="Controller">
                                                                <input type="hidden" name="command" value="blockuser">
                                                                <input type="hidden" name="email" value="${customer.email}">
                                                                <button type="submit" data-toggle="tooltip" data-placement="top" title="<fmt:message key="admin.action.block"/>" class="btn btn-sm btn-danger"><i class="fas fa-ban"></i> </button>
                                                            </form>
                                                        </td>
                                                        <td class="text-center">
                                                            <form method="post" action="Controller">
                                                                <input type="hidden" name="command" value="makeadmin">
                                                                <input type="hidden" name="email" value="${customer.email}">
                                                                <button type="submit" data-toggle="tooltip" data-placement="top" title="<fmt:message key="admin.action.make.admin"/>" class="btn btn-sm btn-info"><i class="fas fa-crown"></i> </button>
                                                            </form>
                                                        </td>
                                                    </c:when>
                                                </c:choose>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="tab-pane fade ${"showorders".equals(param.get("command")) ? 'active show' : ''}" id="nav-orders" role="tabpanel" aria-labelledby="nav-orders-tab">
            <div class="container mb-4 tab-page">
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
                                                <li class="list-group-item col-3">${order.customer.address}</li>

                                                   <c:if test="${order.orderStatus.toString().equals('WAITING') || order.orderStatus.toString().equals('APPROVED') }">
                                                        <li class="list-group-item col-4">
                                                           <form method="post" action="Controller">
                                                               <div class="input-group">
                                                                   <input type="hidden" name="command" value="changestatus">
                                                                   <input type="hidden" name="order_id" value="${order.id}">
                                                                   <select name="status" class="form-control">
                                                                       <option value="waiting" class="text-warning"><fmt:message key="table.orders.waiting"/></option>
                                                                       <option value="approved" class="text-success"><fmt:message key="table.orders.approved"/></option>
                                                                       <option value="decline" class="text-danger"><fmt:message key="table.orders.decline"/></option>
                                                                       <option value="complete" class="text-info"><fmt:message key="table.orders.complete"/></option>
                                                                   </select>
                                                                   <div class="input-group-append">
                                                                       <button type="submit" class="btn btn-primary"><fmt:message key="admin.set.status"/></button>
                                                                   </div>
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

            </div>
        </div>
    </div>
    <c:if test="${pageCount != 1 && 'showorders'.equals(param.get('command'))}">
        <div class="container">
            <div class="row mb-5">
                <div class="col-12">
                    <nav class="d-flex justify-content-center" aria-label="...">
                        <ul class="pagination">
                            <c:choose>
                                <c:when test="${currentPage == 1}">
                                    <li class="page-item disabled">
                                        <a class="page-link " href="#" tabindex="-1"><fmt:message key="pagination.previous"/></a>
                                    </li>
                                </c:when>
                                <c:otherwise>
                                    <li class="page-item">
                                        <a class="page-link" href="Controller?command=showorders&page=${requestScope.currentPage - 1}"><fmt:message key="pagination.previous"/></a>
                                    </li>
                                </c:otherwise>
                            </c:choose>

                            <c:forEach var="i" begin="1" end="${pageCount}">
                                <li class="page-item ${i == currentPage ? 'active' : ''}">
                                    <a class="page-link" href="Controller?command=showorders&page=${i}">${i}</a>
                                </li>
                            </c:forEach>
                            <c:choose>
                                <c:when test="${currentPage == pageCount}">
                                    <li class="page-item disabled">
                                        <a class="page-link " href="#" tabindex="-1"><fmt:message key="pagination.next"/></a>
                                    </li>
                                </c:when>
                                <c:otherwise>
                                    <li class="page-item">
                                        <a class="page-link" href="Controller?command=showorders&page=${currentPage + 1}"><fmt:message key="pagination.next"/></a>
                                    </li>
                                </c:otherwise>
                            </c:choose>
                        </ul>
                    </nav>
                </div>
            </div>
        </div>
    </c:if>
</section>

<div class="toast" id="infoToast" style="position: absolute; top: 70px; right: 5px;">
    <div class="toast-header">
        <strong class="mr-auto"><i class="fas fa-info-circle"></i> <fmt:message key="info.toast.title"/></strong>
        <button type="button" class="ml-2 mb-1 close" data-dismiss="toast">
            <span aria-hidden="true">&times;</span>
        </button>
    </div>
    <div class="toast-body">
        <c:choose>
            <c:when test="${'success'.equals(param.get('blocked'))}">
                <span class="text-success"><fmt:message key="admin.toast.msg.blocked.success"/></span>
            </c:when>
            <c:when test="${'success'.equals(param.get('unblocked'))}">
                <span class="text-success"><fmt:message key="admin.toast.msg.unblocked.success"/></span>
            </c:when>
            <c:when test="${'success'.equals(param.get('make_admin'))}">
                <span class="text-success"><fmt:message key="admin.toast.msg.make.admin.success"/></span>
            </c:when>
            <c:when test="${'success'.equals(param.get('change'))}">
                <span class="text-success"><fmt:message key="admin.toast.msg.status.success"/></span>
            </c:when>
            <c:when test="${'fail'.equals(param.get('blocked'))}">
                <span class="text-danger"><fmt:message key="admin.toast.msg.block.fail"/></span>
            </c:when>
            <c:when test="${'fail'.equals(param.get('unblocked'))}">
                <span class="text-danger"><fmt:message key="admin.toast.msg.unblock.fail"/></span>
            </c:when>
            <c:when test="${'fail'.equals(param.get('change'))}">
                <span class="text-danger"><fmt:message key="admin.toast.msg.status.fail"/></span>
            </c:when>
            <c:when test="${'fail'.equals(param.get('make_admin'))}">
                <span class="text-danger"><fmt:message key="admin.toast.msg.make.admin.fail"/></span>
            </c:when>
        </c:choose>
    </div>
</div>

<jsp:include page="footer.jsp"/>

<script type="text/javascript">
    $(document).ready(function(){
        if (${'yes'.equals(param.get('show'))})
        {
            $("#infoToast").toast({delay: 5000});
            $("#infoToast").toast('show');
        }
    });
</script>

</body>
</html>
