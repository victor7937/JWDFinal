<%--
  Created by IntelliJ IDEA.
  User: victor
  Date: 14.04.21
  Time: 06:07
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <jsp:include page="includes.jsp"/>
  <fmt:setLocale value="${sessionScope.lang}" scope="session"/>
  <fmt:setBundle basename="application"/>
  <link href="<c:url value='/css/style.css'/>" rel="stylesheet" type="text/css">
  <title><fmt:message key="add.title"/></title>
</head>
<body>

<jsp:include page="header.jsp"/>

<section>
    <div class="container">
        <div class="card my-3 mx-auto w-75">
            <div class="card-body">
                <div class="row">
                    <div class="col-md-12">
                        <h2><fmt:message key="add.new.footwear"/></h2>
                        <hr>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-12">
                        <c:if test="${param.message.equals('inv_data')}">
                            <p class="text-danger text-center"><fmt:message key="add.msg.inv.data"/></p>
                        </c:if>
                        <c:if test="${param.message.equals('not_created')}">
                            <p class="text-danger text-center"><fmt:message key="add.msg.fail"/></p>
                        </c:if>
                        <form action="Controller" method="post" enctype="multipart/form-data">
                            <input type="hidden" name="command" value="newfootwear">
                            <div class="form-group row">
                                <label for="art" class="col-4 col-form-label"><fmt:message key="add.model.art"/></label>
                                <div class="col-6">
                                    <input id="art" name="art" placeholder="<fmt:message key="add.model.art.code"/>" class="form-control" type="text" required>
                                </div>
                            </div>
                            <div class="form-group row">
                                <label for="name" class="col-4 col-form-label"><fmt:message key="add.model.name"/></label>
                                <div class="col-6">
                                    <input id="name" name="name" placeholder="<fmt:message key="add.name"/>" class="form-control" type="text" required>
                                </div>
                            </div>
                            <div class="form-group row">
                                <label for="price" class="col-4 col-form-label"><fmt:message key="add.price"/></label>
                                <div class="col-6">
                                    <input id="price" name="price" placeholder="<fmt:message key="add.price.placeholder"/>" class="form-control" type="text"  title="<fmt:message key="add.price.msg"/>"
                                           pattern="^[0-9]{1,4}(\.[0-9]{1,2})?$" required>
                                </div>
                            </div>

                            <div class="form-group row">
                                <label for="category" class="col-4 col-form-label"><fmt:message key="add.category"/></label>
                                <div class="col-6">
                                    <select id="category" name="category" class="form-control" type="text" required>
                                       <c:forEach var="category_name" items="${requestScope.categories}">
                                           <option value="${category_name}">${category_name}</option>
                                       </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group row">
                                <label for="brand" class="col-4 col-form-label"><fmt:message key="add.brand"/></label>
                                <div class="col-6">
                                    <select id="brand" name="brand" class="form-control" type="text" required>
                                        <c:forEach var="brand_name" items="${requestScope.brands}">
                                            <option value="${brand_name}">${brand_name}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group row">
                                <label for="color" class="col-4 col-form-label"><fmt:message key="add.color"/></label>
                                <div class="col-6">
                                    <select id="color" name="color" class="form-control" type="text" required>
                                        <c:forEach var="color_name" items="${requestScope.colors}">
                                            <option value="${color_name}">${color_name}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group row">
                                <label for="for" class="col-4 col-form-label"><fmt:message key="add.for.whom"/></label>
                                <div class="col-6">
                                    <select id="for" name="for" class="form-control" type="text" required>
                                        <option value="HIM"><fmt:message key="add.him"/></option>
                                        <option value="HER"><fmt:message key="add.her"/></option>
                                    </select>
                                </div>
                            </div>

                            <div class="form-group row">
                                <label for="description_ru" class="col-4 col-form-label"><fmt:message key="add.descr.ru"/></label>
                                <div class="col-6">
                                    <textarea class="form-control" name="description_ru" id="description_ru" rows="3"></textarea>
                                </div>
                            </div>
                            <div class="form-group row">
                                <label for="description_en" class="col-4 col-form-label"><fmt:message key="add.descr.en"/></label>
                                <div class="col-6">
                                    <textarea class="form-control" name="description_en" id="description_en" rows="3"></textarea>
                                </div>
                            </div>
                            <div class="form-group row">
                                <label for="file-input-image" class="col-4 col-form-label"><fmt:message key="add.choose.images"/></label>
                                <div class="input-group col-6" id="file-input-image">
                                    <div class="custom-file">
                                        <input type="file" multiple="multiple" name="file" class="custom-file-input" id="image" aria-describedby="inputGroupFileAddon04" accept="image/*" required>
                                        <label class="custom-file-label" for="image"><fmt:message key="add.choose.file"/></label>
                                    </div>
                                </div>
                            </div>
                            <div class="form-group row">
                                <div class="offset-4 col-6">
                                    <button name="submit" type="submit" class="btn btn-primary"><fmt:message key="add.create.footwear"/></button>
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
