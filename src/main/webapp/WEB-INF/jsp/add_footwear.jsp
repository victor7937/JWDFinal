<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: victor
  Date: 14.04.21
  Time: 06:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>TestFile</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <jsp:include page="includes.jsp"/>
  <link href="<c:url value='/css/style.css'/>" rel="stylesheet" type="text/css">
</head>
<body>
<%--<form action="upload" method="post" enctype="multipart/form-data">--%>
<%--    <div class="input-group">--%>
<%--        <div class="custom-file">--%>
<%--            <input type="file" multiple="multiple" name="file" class="custom-file-input" id="inputGroupFile03" aria-describedby="inputGroupFileAddon03" accept="image/*">--%>
<%--            <label class="custom-file-label" for="inputGroupFile03">Choose file</label>--%>
<%--        </div>--%>
<%--        <div class="input-group-append">--%>
<%--            <button class="btn btn-primary" type="submit" id="inputGroupFileAddon03">Button</button>--%>
<%--        </div>--%>
<%--    </div>--%>
<%--</form>--%>

<section>
    <div class="container">
        <div class="card my-3 mx-auto w-75">
            <div class="card-body">
                <div class="row">
                    <div class="col-md-12">
                        <h2>Add new Footwear</h2>
                        <hr>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-12">
                        <form action="Controller" method="post" enctype="multipart/form-data">
                            <input type="hidden" name="command" value="newfootwear">
                            <div class="form-group row">
                                <label for="art" class="col-4 col-form-label">Model Art</label>
                                <div class="col-6">
                                    <input id="art" name="art" placeholder="Art code" class="form-control" type="text" required>
                                </div>
                            </div>
                            <div class="form-group row">
                                <label for="name" class="col-4 col-form-label">Model Name</label>
                                <div class="col-6">
                                    <input id="name" name="name" placeholder="Name" class="form-control" type="text" required>
                                </div>
                            </div>
                            <div class="form-group row">
                                <label for="price" class="col-4 col-form-label">Price in USD</label>
                                <div class="col-6">
                                    <input id="price" name="price" placeholder="Price($)" class="form-control" type="text"  title="Price should be correct max value is 9999.99$"
                                           pattern="^[0-9]{1,4}(\.[0-9]{1,2})?$" required>
                                </div>
                            </div>

                            <div class="form-group row">
                                <label for="category" class="col-4 col-form-label">Category</label>
                                <div class="col-6">
                                    <select id="category" name="category" class="form-control" type="text" required>
                                       <c:forEach var="category_name" items="${requestScope.categories}">
                                           <option value="${category_name}">${category_name}</option>
                                       </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group row">
                                <label for="brand" class="col-4 col-form-label">Brand</label>
                                <div class="col-6">
                                    <select id="brand" name="brand" class="form-control" type="text" required>
                                        <c:forEach var="brand_name" items="${requestScope.brands}">
                                            <option value="${brand_name}">${brand_name}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group row">
                                <label for="color" class="col-4 col-form-label">Color</label>
                                <div class="col-6">
                                    <select id="color" name="color" class="form-control" type="text" required>
                                        <c:forEach var="color_name" items="${requestScope.colors}">
                                            <option value="${color_name}">${color_name}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group row">
                                <label for="color" class="col-4 col-form-label">For Whom</label>
                                <div class="col-6">
                                    <select id="for" name="for" class="form-control" type="text" required>
                                        <option value="HIM">Him</option>
                                        <option value="HER">Her</option>
                                    </select>
                                </div>
                            </div>

                            <div class="form-group row">
                                <label for="description_ru" class="col-4 col-form-label">Russian Description</label>
                                <div class="col-6">
                                    <textarea class="form-control" name="description_ru" id="description_ru" rows="3"></textarea>
                                </div>
                            </div>
                            <div class="form-group row">
                                <label for="description_en" class="col-4 col-form-label">English Description</label>
                                <div class="col-6">
                                    <textarea class="form-control" name="description_en" id="description_en" rows="3"></textarea>
                                </div>
                            </div>
                            <div class="form-group row">
                                <label for="file-input-image" class="col-4 col-form-label">Choose Images</label>
                                <div class="input-group col-6" id="file-input-image">
                                    <div class="custom-file">
                                        <input type="file" multiple="multiple" name="file" class="custom-file-input" id="image" aria-describedby="inputGroupFileAddon04" accept="image/*" required>
                                        <label class="custom-file-label" for="image">Choose file</label>
                                    </div>
                                </div>
                            </div>
                            <div class="form-group row">
                                <div class="offset-4 col-6">
                                    <button name="submit" type="submit" class="btn btn-primary">Create Footwear</button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>

            </div>
        </div>
    </div>

</section>






<script type="application/javascript">
    $('.custom-file input').change(function (e) {
        var files = [];
        for (var i = 0; i < $(this)[0].files.length; i++) {
            files.push($(this)[0].files[i].name);
        }
        $(this).next('.custom-file-label').html(files.join(', '));
    });
</script>


</body>
</html>
