<%--
  Created by IntelliJ IDEA.
  User: victor
  Date: 22.04.21
  Time: 13:47
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
  <c:set value="${requestScope.footwear}" var="footwear"/>
  <link href="<c:url value='/css/style.css'/>" rel="stylesheet" type="text/css">
  <link href="<c:url value='/css/card.css'/>" rel="stylesheet" type="text/css">
  <title><fmt:message key="edit.title"/></title>
</head>
<body>

<jsp:include page="header.jsp"/>

<section class="jumbotron text-center">
  <div class="container">
    <h1 class="jumbotron-heading text-uppercase">${footwear.brand} ${footwear.name}</h1>
    <p class="lead text-muted mb-0"><fmt:message key="edit.title"/></p>
  </div>
</section>

<div class="container">
  <div class="row">
    <div class="col">
      <nav aria-label="breadcrumb">
        <ol class="breadcrumb">
          <li class="breadcrumb-item"><a href="Controller?command=gotomainpage"><fmt:message key="header.home"/></a></li>
          <li class="breadcrumb-item"><a href="Controller?command=gotocategory"><fmt:message key="header.categories"/></a></li>
          <li class="breadcrumb-item active" aria-current="page">${footwear.art}</li>
        </ol>
      </nav>
    </div>
  </div>
</div>


<section>
  <div class="container">
    <div class="card my-3 mx-auto w-75">
      <div class="card-body">
        <div class="row">
          <div class="col-md-12">
            <h2><fmt:message key="edit.footwear"/></h2>
            <hr>
          </div>
        </div>
        <div class="row">
          <div class="col-md-12">
            <form action="Controller" method="post" enctype="multipart/form-data">
              <input type="hidden" name="command" value="editfootwear">
              <div class="form-group row">
                <label for="art" class="col-4 col-form-label"><fmt:message key="add.model.art"/></label>
                <div class="col-6">
                  <input id="art" name="art" value="${footwear.art}" readonly class="form-control-plaintext" type="text" required>
                </div>
              </div>
              <div class="form-group row">
                <label for="name" class="col-4 col-form-label"><fmt:message key="add.model.name"/></label>
                <div class="col-6">
                  <input id="name" name="name" placeholder="<fmt:message key="add.name"/>" value="${footwear.name}" class="form-control" type="text" required>
                </div>
              </div>
              <div class="form-group row">
                <label for="price" class="col-4 col-form-label"><fmt:message key="add.price"/></label>
                <div class="col-6">
                  <input id="price" name="price" placeholder="<fmt:message key="add.price.placeholder"/>" value="${footwear.price}" class="form-control" type="text"  title="<fmt:message key="add.price.msg"/>"
                         pattern="^[0-9]{1,4}(\.[0-9]{1,2})?$" required>
                </div>
              </div>

              <div class="form-group row">
                <label for="category" class="col-4 col-form-label"><fmt:message key="add.category"/></label>
                <div class="col-6">
                  <select id="category" name="category" class="form-control" type="text" required>
                    <c:forEach var="category_name" items="${requestScope.categories}">
                      <c:choose>
                        <c:when test="${category_name.equals(footwear.category)}">
                          <option selected value="${category_name}">${category_name}</option>
                        </c:when>
                        <c:otherwise>
                          <option value="${category_name}">${category_name}</option>
                        </c:otherwise>
                      </c:choose>
                    </c:forEach>
                  </select>
                </div>
              </div>
              <div class="form-group row">
                <label for="brand" class="col-4 col-form-label"><fmt:message key="add.brand"/></label>
                <div class="col-6">
                  <select id="brand" name="brand" class="form-control" type="text" required>
                    <c:forEach var="brand_name" items="${requestScope.brands}">
                      <c:choose>
                        <c:when test="${brand_name.equals(footwear.brand)}">
                          <option selected value="${brand_name}">${brand_name}</option>
                        </c:when>
                        <c:otherwise>
                          <option value="${brand_name}">${brand_name}</option>
                        </c:otherwise>
                      </c:choose>
                    </c:forEach>
                  </select>
                </div>
              </div>
              <div class="form-group row">
                <label for="color" class="col-4 col-form-label"><fmt:message key="add.color"/></label>
                <div class="col-6">
                  <select id="color" name="color" class="form-control" type="text" required>
                    <c:forEach var="color_name" items="${requestScope.colors}">
                      <c:choose>
                        <c:when test="${color_name.equals(footwear.color)}">
                          <option selected value="${color_name}">${color_name}</option>
                        </c:when>
                        <c:otherwise>
                          <option value="${color_name}">${color_name}</option>
                        </c:otherwise>
                      </c:choose>
                    </c:forEach>
                  </select>
                </div>
              </div>
              <div class="form-group row">
                <label for="for" class="col-4 col-form-label"><fmt:message key="add.for.whom"/></label>
                <div class="col-6">
                  <select id="for" name="for" class="form-control" type="text" required>
                    <c:choose>
                      <c:when test="${'HIM'.equals(footwear.forWhom.toString())}">
                        <option selected value="HIM"><fmt:message key="add.him"/></option>
                        <option value="HER"><fmt:message key="add.her"/></option>
                      </c:when>
                      <c:otherwise>
                        <option selected value="HER"><fmt:message key="add.her"/></option>
                        <option value="HIM"><fmt:message key="add.him"/></option>
                      </c:otherwise>
                    </c:choose>
                  </select>
                </div>
              </div>

              <div class="form-group row">
                <label for="description" class="col-4 col-form-label"><fmt:message key="product.description"/></label>
                <div class="col-6">
                  <textarea class="form-control" name="description" id="description" rows="3">${footwear.description}</textarea>
                </div>
              </div>
              <div class="form-group row">
                <label for="file-input-image" class="col-4 col-form-label"><fmt:message key="edit.add.images"/></label>
                <div class="input-group col-6" id="file-input-image">
                  <div class="custom-file">
                    <input type="file" multiple="multiple" name="file" class="custom-file-input" id="image" aria-describedby="inputGroupFileAddon04" accept="image/*">
                    <label class="custom-file-label" for="image"><fmt:message key="add.choose.file"/></label>
                  </div>
                </div>
              </div>
              <div class="form-group row">
                <div class="offset-4 col-6">
                  <button name="submit" type="submit" class="btn btn-primary"><fmt:message key="edit.footwear"/></button>
                </div>
              </div>
            </form>
          </div>
        </div>

        <div class="row mt-4">
          <div class="col-md-12">
            <h2><fmt:message key="edit.images"/></h2>
            <hr>
          </div>
        </div>
          <div class="row">
            <c:forEach var="image" items="${footwear.imageLinks}">
              <div class="col-md-3 col-4 my-3">
                <div class="product-card">
                  <div class="product-image">
                    <img src="images/${image}">
                    <ul class="social">
                      <li>
                        <form method="post" action="Controller">
                          <input type="hidden" name="command" value="deleteimage">
                          <input type="hidden" name="image" value="${image}">
                          <input type="hidden" name="art" value="${footwear.art}">
                          <button type="submit" data-toggle="tooltip" data-placement="top" class="social-button fas fa-trash-alt" title="<fmt:message key="edit.delete.image"/>"></button>
                        </form>
                      </li>
                    </ul>
                  </div>
                </div>
              </div>
            </c:forEach>
          </div>
      </div>
    </div>
  </div>

</section>

<jsp:include page="footer.jsp"/>

<div class="toast" id="infoToast" style="position: absolute; top: 70px; right: 5px;">
  <div class="toast-header">
    <strong class="mr-auto"><i class="fas fa-info-circle"></i> <fmt:message key="info.toast.title"/></strong>
    <button type="button" class="ml-2 mb-1 close" data-dismiss="toast">
      <span aria-hidden="true">&times;</span>
    </button>
  </div>
  <div class="toast-body">
    <c:choose>
      <c:when test="${'success'.equals(param.get('delete'))}">
        <span class="text-success"><fmt:message key="edit.toast.delete.success"/></span>
      </c:when>
      <c:when test="${'fail'.equals(param.get('delete'))}">
        <span class="text-danger"><fmt:message key="edit.toast.delete.fail"/></span>
      </c:when>
      <c:when test="${'success'.equals(param.get('edit'))}">
        <span class="text-success"><fmt:message key="edit.toast.success"/></span>
      </c:when>
      <c:when test="${'fail'.equals(param.get('edit'))}">
        <span class="text-danger"><fmt:message key="edit.toast.fail"/></span>
      </c:when>
    </c:choose>
  </div>
</div>


<script type="application/javascript">
  $('.custom-file input').change(function (e) {
    var files = [];
    for (var i = 0; i < $(this)[0].files.length; i++) {
      files.push($(this)[0].files[i].name);
    }
    $(this).next('.custom-file-label').html(files.join(', '));
  });
</script>


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
