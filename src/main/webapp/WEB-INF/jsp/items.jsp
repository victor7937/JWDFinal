<%--
  Created by IntelliJ IDEA.
  User: victor
  Date: 19.04.21
  Time: 13:25
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <jsp:include page="includes.jsp"/>
    <link href="<c:url value='/css/style.css'/>" rel="stylesheet" type="text/css">
    <title>Footwear Items</title>
    <c:set var="footwear" value="${requestScope.footwear}"/>
</head>
<body>

<jsp:include page="header.jsp"/>

<section class="jumbotron text-center">
    <div class="container">
        <h1 class="jumbotron-heading text-uppercase">${footwear.brand} ${footwear.name} Items</h1>
        <p class="lead text-muted mb-0">Art code : ${footwear.art}</p>
    </div>
</section>
<div class="container">
    <div class="row">
        <div class="col">
            <nav aria-label="breadcrumb">
                <ol class="breadcrumb">
                    <li class="breadcrumb-item"><a href="Controller?command=gotomainpage">Home</a></li>
                    <li class="breadcrumb-item"><a href="Controller?command=gotocategory">Category</a></li>
                    <li class="breadcrumb-item active" aria-current="page">${footwear.art}</li>
                </ol>
            </nav>
        </div>
    </div>
</div>

<section>
    <div class="container mt-3 mx-auto w-75">
        <div class="row">
            <div class="col-12">
                <div class="table-responsive">
                    <table class="table table-bordered table-hover">
                        <thead class="thead-dark">
                        <tr>
                            <th scope="col">Item #</th>
                            <th scope="col">Art Code</th>
                            <th scope="col">Size</th>
                            <th scope="col">Status</th>
                            <th></th>
                        </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="item" items="${requestScope.items}">
                                <tr class="text-table">
                                    <td>${item.id}</td>
                                    <td>${item.footwear.art}</td>
                                    <td>${item.size}</td>
                                    <c:if test="${'SOLVED'.equals(item.status.toString())}">
                                        <td class="text-warning">Solved</td>
                                    </c:if>
                                    <c:if test="${'STOCK'.equals(item.status.toString())}">
                                        <td class="text-success">Stock</td>
                                    </c:if>
                                    <td class="text-center">
                                        <form method="post" action="Controller">
                                            <div class="input-group">
                                                <input type="hidden" name="command" value="changeitemstatus">
                                                <input type="hidden" name="art" value="${item.footwear.art}">
                                                <input type="hidden" name="id" value="${item.id}">
                                                <select name="status" class="form-control">
                                                    <option value="stock" class="text-success">Stock</option>
                                                    <option value="solved" class="text-warning">Solved</option>
                                                </select>
                                                <div class="input-group-append">
                                                    <button type="submit" class="btn btn-primary">Set status</button>
                                                </div>
                                            </div>
                                        </form>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</section>

<section>
    <div class="container">
        <div class="card mt-3 mb-4 mx-auto w-50">
            <div class="card-body">
                <div class="row">
                    <div class="col-md-12">
                        <h2>Add New Size</h2>
                        <hr>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-12">
                        <form action="Controller" method="post">
                            <input type="hidden" name="command" value="additem">
                            <div class="form-group row">
                                <label for="art_code" class="col-4 col-form-label">Art Code</label>
                                <div class="col-6">
                                    <input id="art_code" name="art" value="${footwear.art}" readonly class="form-control-plaintext" type="text" required>
                                </div>
                            </div>
                            <div class="form-group row">
                                <label for="size" class="col-4 col-form-label">Size</label>
                                <div class="col-6">
                                    <input id="size" name="size" placeholder="Size" class="form-control" type="text" title="Size should be float or integer number from 30.0 to 50.0 format(## or ##.#) "
                                           pattern="^[0-9]{2}(\.[0-9])?$" required>
                                </div>
                            </div>
                            <div class="form-group row">
                                <div class="offset-4 col-6">
                                    <button name="submit" type="submit" class="btn btn-primary">Add Size</button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>

<div class="toast" id="infoToast" style="position: absolute; top: 70px; right: 5px;">
    <div class="toast-header">
        <strong class="mr-auto"><i class="fas fa-info-circle"></i> Info</strong>
        <button type="button" class="ml-2 mb-1 close" data-dismiss="toast">
            <span aria-hidden="true">&times;</span>
        </button>
    </div>
    <div class="toast-body">
        <c:choose>
            <c:when test="${'success'.equals(param.get('add'))}">
                <span class="text-success">Successfully added</span>
            </c:when>
            <c:when test="${'fail'.equals(param.get('add'))}">
                <span class="text-danger">Fail to add, please try again</span>
            </c:when>
            <c:when test="${'success'.equals(param.get('change'))}">
                <span class="text-success">Successfully changed</span>
            </c:when>
            <c:when test="${'fail'.equals(param.get('change'))}">
                <span class="text-danger">Fail to change status, please try again</span>
            </c:when>
            <c:when test="${'same'.equals(param.get('change'))}">
                <span class="text-warning">Such status is already set</span>
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
