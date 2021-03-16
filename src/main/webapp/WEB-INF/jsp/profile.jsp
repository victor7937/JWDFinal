<%--
  Created by IntelliJ IDEA.
  User: victor
  Date: 12.03.21
  Time: 15:28
  To change this template use File | Settings | File Templates.
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <div class="container my-3">
        <div class="card">
            <div class="card-body">
                <div class="row">
                    <div class="col-md-12">
                        <h4>Profile</h4>
                        <hr>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-12">
                        <form>
                            <div class="form-group row">
                                <label for="name" class="col-4 col-form-label">Name</label>
                                <div class="col-6">
                                    <input id="name" name="name" value="${user.name}" placeholder="Name" class="form-control" type="text">
                                </div>
                            </div>
                            <div class="form-group row">
                                <label for="country" class="col-4 col-form-label">Country</label>
                                <div class="col-6">
                                    <input id="country" name="country" placeholder="Country" class="form-control" type="text" value="${user.country}">
                                </div>
                            </div>
                            <div class="form-group row">
                                <label for="city" class="col-4 col-form-label">City</label>
                                <div class="col-6">
                                    <input id="city" name="city" placeholder="City" class="form-control" type="text" value="${user.city}">
                                </div>
                            </div>
                            <div class="form-group row">
                                <label for="address" class="col-4 col-form-label">Address</label>
                                <div class="col-6">
                                    <input id="address" name="address" placeholder="Address" class="form-control" type="text" value="${user.address}">
                                </div>
                            </div>
                            <div class="form-group row">
                                <label class="col-4 col-form-label">Phone number</label>
                                <div class="col-6" >
                                    <!--<select name="phone" id="phonecode" class="custom-select" style="max-width: 120px;">
                                        <option selected="">+375</option>
                                        <option>+972</option>
                                        <option>+198</option>
                                        <option>+701</option>
                                    </select> -->
                                    <input name="phone" id="phonenum" placeholder="Phone number" class="form-control" type="text" value="${user.phone}" maxlength="11">
                                </div>
                            </div>



                            <div class="form-group row">
                                <label for="email" class="col-4 col-form-label">Email</label>
                                <div class="col-6">
                                    <input id="email" name="email" placeholder="Email" value="${user.email}" class="form-control" required="required" type="email">
                                </div>
                            </div>
                            <div class="form-group row">
                                <label for="newpass" class="col-4 col-form-label">New Password</label>
                                <div class="col-6">
                                    <input id="newpass" name="newpass" placeholder="New Password" class="form-control" type="text">
                                </div>
                            </div>
                            <div class="form-group row">
                                <label for="newpass_repeat" class="col-4 col-form-label">Repeat Password</label>
                                <div class="col-6">
                                    <input id="newpass_repeat" name="newpass" placeholder="Repeat Password" class="form-control" type="text">
                                </div>
                            </div>
                            <div class="form-group row">
                                <div class="offset-4 col-6">
                                    <button name="submit" type="submit" class="btn btn-primary">Update My Profile</button>
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

</body>
</html>
