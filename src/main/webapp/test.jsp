
<%--
  Created by IntelliJ IDEA.
  User: victor
  Date: 2.03.21
  Time: 01:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="en">
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <jsp:include page="WEB-INF/jsp/includes.jsp"/>
    <style>
        .bs-example{
            margin: 20px;
            position: relative;
        }
    </style>
    <script>
        $(document).ready(function(){

            if (${param.get('show') == 'true'})
            {
                // $(".show-toast").click(function () {
                    $("#myToast").toast({delay: 3000});
                    $("#myToast").toast('show');
                // });
            }
        });
    </script>
    <title>Hello, world!</title>
</head>
<body>

<%--<div class="bs-example">--%>

<%--</div>--%>


</body>
</html>
