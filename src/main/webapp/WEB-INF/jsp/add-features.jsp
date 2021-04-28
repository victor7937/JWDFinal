<%--
  Created by IntelliJ IDEA.
  User: victor
  Date: 19.04.21
  Time: 02:32
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add Features</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <jsp:include page="includes.jsp"/>
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
                        <h2>Add New Category</h2>
                        <hr>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-12">
                        <form action="Controller" method="post">
                            <input type="hidden" name="command" value="addcategory">
                            <div class="form-group row">
                                <label for="category_name_en" class="col-4 col-form-label">Category Name(EN)</label>
                                <div class="col-6">
                                    <input id="category_name_en" name="category_en" placeholder="Category in English" class="form-control" type="text" required>
                                </div>
                            </div>
                            <div class="form-group row">
                                <label for="category_name_ru" class="col-4 col-form-label">Category Name(RU)</label>
                                <div class="col-6">
                                    <input id="category_name_ru" name="category_ru" placeholder="Category in Russian" class="form-control" type="text" required>
                                </div>
                            </div>
                            <div class="form-group row">
                                <div class="offset-4 col-6">
                                    <button name="submit" type="submit" class="btn btn-primary">Add Category</button>
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
    <div class="container">
        <div class="card my-3 mx-auto w-75">
            <div class="card-body">
                <div class="row">
                    <div class="col-md-12">
                        <h2>Add New Color</h2>
                        <hr>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-12">
                        <form action="Controller" method="post">
                            <input type="hidden" name="command" value="addcolor">
                            <div class="form-group row">
                                <label for="color_name_en" class="col-4 col-form-label">Color Name(EN)</label>
                                <div class="col-6">
                                    <input id="color_name_en" name="color_en" placeholder="Color in English" class="form-control" type="text" required>
                                </div>
                            </div>
                            <div class="form-group row">
                                <label for="color_name_ru" class="col-4 col-form-label">Color Name(RU)</label>
                                <div class="col-6">
                                    <input id="color_name_ru" name="color_ru" placeholder="Color in Russian" class="form-control" type="text" required>
                                </div>
                            </div>
                            <div class="form-group row">
                                <div class="offset-4 col-6">
                                    <button name="submit" type="submit" class="btn btn-primary">Add Color</button>
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
    <div class="container">
        <div class="card my-3 mx-auto w-75">
            <div class="card-body">
                <div class="row">
                    <div class="col-md-12">
                        <h2>Add New Brand</h2>
                        <hr>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-12">
                        <form action="Controller" method="post">
                            <input type="hidden" name="command" value="addbrand">
                            <div class="form-group row">
                                <label for="brand_name" class="col-4 col-form-label">Brand Name</label>
                                <div class="col-6">
                                    <input id="brand_name" name="brand" placeholder="Brand" class="form-control" type="text" required>
                                </div>
                            </div>
                            <div class="form-group row">
                                <div class="offset-4 col-6">
                                    <button name="submit" type="submit" class="btn btn-primary">Add Brand</button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>

<jsp:include page="footer.jsp"/>

<div class="toast" id="infoToast" style="position: absolute; top: 70px; right: 5px;">
    <div class="toast-header">
        <strong class="mr-auto"><i class="fas fa-info-circle"></i> Info</strong>
        <button type="button" class="ml-2 mb-1 close" data-dismiss="toast">
            <span aria-hidden="true">&times;</span>
        </button>
    </div>
    <div class="toast-body">
        <c:choose>
            <c:when test="${'success'.equals(param.get('message'))}">
                <span class="text-success">Successfully added</span>
            </c:when>
            <c:when test="${'fail'.equals(param.get('message'))}">
                <span class="text-danger">Fail to add, please try again</span>
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

</body>
</html>
