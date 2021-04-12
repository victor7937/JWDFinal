<%--
  Created by IntelliJ IDEA.
  User: victor
  Date: 7.04.21
  Time: 15:53
  To change this template use File | Settings | File Templates.
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Lei Shoes</title>
    <jsp:include page="includes.jsp"/>
    <link href="<c:url value='/css/style.css'/>" rel="stylesheet" type="text/css">
    <title>Admin page</title>
</head>
<body>

<jsp:include page="header.jsp"/>

<section class="jumbotron text-center">
    <div class="container">
        <h1 class="jumbotron-heading">Admin page</h1>
    </div>
</section>

<section>
    <div class="container">
        <nav>
            <div class="nav nav-tabs row" id="nav-tab" role="tablist">
                    <a class="nav-link col-6 text-center ${"showusers".equals(param.get("command")) ? 'active' : ''}" id="nav-users-tab"  href="Controller?command=gotoadminpage"  aria-selected="${"showusers".equals(param.get("command")) ? 'true' : 'false'}">Users</a>
                    <a class="nav-link col-6 text-center ${"showorders".equals(param.get("command")) ? 'active' : ''}" id="nav-orders-tab"  href="Controller?command=showorders" aria-selected="${"showorders".equals(param.get("command")) ? 'true' : 'false'}">Orders</a>
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
                                    <th scope="col">Name</th>
                                    <th scope="col">Email</th>
                                    <th scope="col">Phone</th>
                                    <th scope="col">Country</th>
                                    <%--                            <th scope="col" class="text-center">Quantity</th>--%>
                                    <th scope="col">City</th>
                                    <th scope="col">Address</th>
                                    <th></th>
                                </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="customer" items="${requestScope.customers}">
                                        <tr>
                                            <td> <div class="text-dark"><i class="fa fa-3x fa-user-circle"></i> </div> </td>
                                            <td>${customer.name}</td>
                                            <td>${customer.email}</td>
                                            <td>${customer.phone}</td>
                                            <td>${customer.country}</td>
                                                <%--                            <td><input class="form-control" type="text" value="1" /></td>--%>
                                            <td>${customer.city}</td>
                                            <td>${customer.address}</td>
                                            <td class="text-center">
                                               <form method="post" action="Controller">
                                                    <input type="hidden" name="command" value="deletecustomer">
                                                    <input type="hidden" name="email" value="${customer.email}">
                                                    <button type="submit" class="btn btn-sm btn-danger"><i class="fa fa-trash"></i> </button>
                                               </form>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>
    <%--                <div class="col mb-2">--%>
    <%--                    <div class="row">--%>
    <%--                        <div class="col-sm-12  col-md-6">--%>
    <%--                            <a href="Controller?command=gotocategory" class="btn btn-block btn-light">Continue Shopping</a>--%>
    <%--                        </div>--%>
    <%--                        <div class="col-sm-12 col-md-6 text-right">--%>
    <%--                            <button class="btn btn-lg btn-block btn-success text-uppercase">Checkout</button>--%>
    <%--                        </div>--%>
    <%--                    </div>--%>
    <%--                </div>--%>
                </div>
            </div>
        </div>
        <div class="tab-pane fade ${"showorders".equals(param.get("command")) ? 'active show' : ''}" id="nav-orders" role="tabpanel" aria-labelledby="nav-orders-tab">
            <div class="container">
                <div class="accordion" id="accordionExample">
                    <c:forEach var="n" begin="1" end="5">
                        <div class="card">
                            <div class="card-header bg-light" id="heading${n}">
                                <h2 class="mb-0">
                                    <button class="btn btn-link btn-block text-center order-header" type="button" data-toggle="collapse" data-target="#collapse${n}" aria-expanded="true" aria-controls="collapse${n}">
                                        Order ${n}
                                    </button>
                                </h2>
                            </div>

                            <div id="collapse${n}" class="collapse" aria-labelledby="heading${n}" data-parent="#accordionExample">
                                <div class="card-body">
                                    Some placeholder content for the first accordion panel. This panel is shown by default, thanks to the <code>.show</code> class.
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </div>
<%--        <div class="tab-pane fade" id="nav-footwear" role="tabpanel" aria-labelledby="nav-footwear-tab">--%>
<%--            <div class="container">--%>
<%--                Lorem ipsum dolor sit amet, consectetur adipisicing elit. Asperiores autem cumque distinctio, dolores dolorum est eum facere harum, impedit iste neque numquam! Exercitationem perspiciatis praesentium quam quis rem suscipit velit!--%>
<%--            </div>--%>
<%--        </div>--%>
    </div>
</section>

<div class="toast" id="infoToast" style="position: absolute; top: 10px; right: 10px;">
    <div class="toast-header">
        <strong class="mr-auto"><i class="fa fa-info-circle"></i> Info</strong>
        <button type="button" class="ml-2 mb-1 close" data-dismiss="toast">
            <span aria-hidden="true">&times;</span>
        </button>
    </div>
    <div class="toast-body">
        <c:choose>
            <c:when test="${'success'.equals(param.get('deleted'))}">
                <span class="text-success">Successfully deleted</span>
            </c:when>
            <c:when test="${'fail'.equals(param.get('deleted'))}">
                <span class="text-warning">Fail to delete customer</span>
            </c:when>
        </c:choose>
    </div>
</div>

<script type="text/javascript">
    $(document).ready(function(){

        if (${'yes'.equals(param.get('show'))})
        {
            $("#infoToast").toast({delay: 5000});
            $("#infoToast").toast('show');
        }
    });
</script>



<jsp:include page="footer.jsp"/>

</body>
</html>
