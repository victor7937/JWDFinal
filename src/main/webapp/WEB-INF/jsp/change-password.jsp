<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--
  Created by IntelliJ IDEA.
  User: victor
  Date: 18.04.21
  Time: 00:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <jsp:include page="includes.jsp"/>
    <link href="<c:url value='/css/style.css'/>" rel="stylesheet" type="text/css">
    <fmt:setLocale value="${sessionScope.lang}" scope="session"/>
    <fmt:setBundle basename="application"/>
    <title><fmt:message key="change.password.title"/></title>
</head>

<body>

<jsp:include page="header.jsp"/>

<section>
    <div class="container">
        <div class="card my-3 mx-auto w-75">
            <div class="card-body">
                <div class="row">
                    <div class="col-md-12">
                        <h2><fmt:message key="change.password.title"/></h2>
                        <hr>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-12">
                        <form action="Controller" method="post">
                            <c:if test="${param.message.equals('not_eq')}">
                                <p class="text-danger text-center"><fmt:message key="change.password.msg.not.valid"/></p>
                            </c:if>
                            <c:if test="${param.message.equals('incorrect_data')}">
                                <p class="text-danger text-center"><fmt:message key="change.password.msg.incorrect"/></p>
                            </c:if>
                            <c:if test="${param.message.equals('not_changed')}">
                                <p class="text-danger text-center"><fmt:message key="change.password.msg.fail"/></p>
                            </c:if>
                            <input type="hidden" name="command" value="changepassword">
                            <div class="form-group row">
                                <label for="current_password" class="col-4 col-form-label"><fmt:message key="change.password.current"/></label>
                                <div class="col-6">
                                    <input id="current_password" name="password" placeholder="<fmt:message key="sign.in.password"/>" class="form-control" type="password" required>
                                </div>
                            </div>

                            <div class="form-group row">
                                <label for="newpass" class="col-4 col-form-label"><fmt:message key="change.password.new.password"/></label>
                                <div class="col-6">
                                    <input id="newpass" name="newpass" placeholder="<fmt:message key="change.password.new.password"/>" class="form-control" type="password"
                                           title="<fmt:message key="change.password.pattern.msg"/>"
                                           pattern="^(?=.*[0-9])(?=.*[a-z])[a-zA-Z0-9-_!@]{7,40}$" required>
                                </div>
                            </div>
                            <div class="form-group row">
                                <label for="newpass_repeat" class="col-4 col-form-label"><fmt:message key="change.password.repeat"/></label>
                                <div class="col-6">
                                    <input id="newpass_repeat" name="newpass" placeholder="<fmt:message key="change.password.repeat"/>" class="form-control" type="password" required>
                                </div>
                            </div>
                            <div class="form-group row">
                                <div class="offset-4 col-6">
                                    <button name="submit" type="submit" class="btn btn-primary"><fmt:message key="change.password.change"/></button>
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

<script type="text/javascript">
    let password = document.getElementById("newpass")
        , password_repeat = document.getElementById("newpass_repeat")
        , password_cur = document.getElementById("current_password");

    function validatePassword(){
        if(password.value !== password_repeat.value) {
            password_repeat.setCustomValidity("<fmt:message key="change.password.not.the.same"/>");
        } else {
            password_repeat.setCustomValidity('');
        }

        if(password_cur.value === password.value) {
            password.setCustomValidity("<fmt:message key="change.password.current.not.same"/>");
        } else {
            password.setCustomValidity('');
        }
    }
    password.onchange = validatePassword;
    password_repeat.onkeyup = validatePassword;
</script>

</body>
</html>
