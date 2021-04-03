
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
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-eOJMYsd53ii+scO/bJGFsiCZc+5NDVN2yr8+0RDqr0Ql0h+rP48ckxlpbzKgwra6" crossorigin="anonymous">
    <link href="//maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet" type="text/css">
    <link rel="preconnect" href="https://fonts.gstatic.com">
    <link href="https://fonts.googleapis.com/css2?family=Montserrat:wght@400;500;600;800&display=swap" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/js/bootstrap.bundle.min.js" integrity="sha384-JEW9xMcG8R+pH31jmWH6WWP0WintQrMb4s7ZOdauHnUtxwoG2vI5DkLtS3qm9Ekf" crossorigin="anonymous"></script>

    <style type="text/css">
        body{margin:40px;}
        .btn-circle {
            width: 30px;
            height: 30px;
            text-align: center;
            padding: 6px 0;
            font-size: 12px;
            line-height: 1.428571429;
            border-radius: 15px;
        }
        .btn-circle .btn-lg {
            width: 50px;
            height: 50px;
            padding: 13px 13px;
            font-size: 18px;
            line-height: 1.33;
            border-radius: 25px;

        }
        /*.btn-round {*/
        /*    text-align: center;*/
        /*    border-radius: 50%;*/
        /*    font-size: 15px;*/
        /*    !*outline-color: #343a40;*!*/
        /*    !*outline-width: 10px;*!*/
        /*    border-color: #343a40;*/
        /*    transition: all .5s ease 0s;*/
        /*    border-width: 2px;*/
        /*    color: #343a40;*/
        /*    background-color: #f8f9fa;*/

        /*}*/


        .btn-round {
            text-align: center;
            border-radius: 50%;
            font-size: 15px;
            /*outline-color: #343a40;*/
            /*outline-width: 10px;*/
            border-color: #343a40;
            transition: all .5s ease 0s;
            border-width: 2px;
            color: #343a40;
            margin-right: 10px;
        }

        .btn-round:hover {
            color: #fff!important;
            background-color: #343a40!important;
            border-color: #343a40!important;
        }

        /*.btn-round:focus, .btn-round.focus {*/
        /*    color: #fff!important;*/
        /*    background-color: #343a40!important;*/
        /*    border-color: #343a40!important;*/
        /*}*/
        /*.btn-round:checked {*/
        /*    color: #fff!important;*/
        /*    background-color: #343a40!important;*/
        /*    border-color: #343a40!important;*/
        /*}*/

        /*.btn-round.disabled, .btn-round:disabled {*/
        /*    color: #343a40!important;*/
        /*    background-color: transparent;*/
        /*}*/

        /*.btn-round:not(:disabled):not(.disabled):active, .btn-round:not(:disabled):not(.disabled).active,*/
        /*.show > .btn-round.dropdown-toggle {*/
        /*    color: #fff!important;*/
        /*    background-color: #343a40!important;*/
        /*    border-color: #343a40!important;*/
        /*}*/

        /*.btn-round:not(:disabled):not(.disabled):active:focus, .btn-round:not(:disabled):not(.disabled).active:focus,*/
        /*.show > .btn-round.dropdown-toggle:focus {*/
        /*    color: #fff!important;*/
        /*    background-color: #343a40!important;*/
        /*    border-color: #343a40!important;*/
        /*}*/
        .radio-toolbar {
            margin: 10px;
        }

        .radio-toolbar input[type="radio"] {
            opacity: 0;
            position: fixed;
            width: 0;
        }

        .radio-toolbar label {

            display: inline-block;
            text-align: center;
            border-radius: 50%;
            padding: 5px 8px;
            font-size: 18px;
            border: 2px solid #343a40;
            transition: all 1s ease 0s;
            color: #343a40;
            margin-right: 5px;
            cursor: pointer;
        }

        .radio-toolbar label:hover {
            color: #fff;
            background-color: rgba(52, 58, 64, 0.5);
            border-color: rgba(52, 58, 64, 0.8);
        }

        .radio-toolbar input[type="radio"]:focus + label {
            color: #fff;
            background-color: rgba(52, 58, 64, 0.5);
            border-color: rgba(52, 58, 64, 0.8);
        }

        .radio-toolbar input[type="radio"]:checked + label {
            color: #fff;
            background-color: #343a40;
            border-color: #343a40;
        }





    </style>
    <!-- Bootstrap CSS -->
<%--    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-BmbxuPwQa2lc/FVzBcNJ7UAyJxM6wuqIj61tLrc4wSX0szH/Ev+nYRRuWlolflfl" crossorigin="anonymous">--%>

    <title>Hello, world!</title>
</head>
<body>

<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <div class="container-fluid">
        <a class="navbar-brand" href="#">Navbar</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                <li class="nav-item">
                    <a class="nav-link active" aria-current="page" href="#">Home</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#">Link</a>
                </li>
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                        Dropdown
                    </a>
                    <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                        <li><a class="dropdown-item" href="#">Action</a></li>
                        <li><a class="dropdown-item" href="#">Another action</a></li>
                        <li><hr class="dropdown-divider"></li>
                        <li><a class="dropdown-item" href="#">Something else here</a></li>
                    </ul>
                </li>
                <li class="nav-item">
                    <a class="nav-link disabled" href="#" tabindex="-1" aria-disabled="true">Disabled</a>
                </li>
            </ul>
            <form class="d-flex">
                <input class="form-control me-2" type="search" placeholder="Search" aria-label="Search">
                <button class="btn btn-outline-success" type="submit">Search</button>
            </form>
        </div>
    </div>
</nav>

<div class="container mt-5" >
    <div class="row mt-2">
        <div class="col">
            <div class="card text-dark bg-info mb-3" style="max-width: 18rem;">
                <div class="card-header">Header</div>
                <div class="card-body">
                    <h5 class="card-title">Info card title</h5>
                    <p class="card-text">Some quick example text to build on the card title and make up the bulk of the card's content.</p>
                </div>
            </div>
        </div>
        <div class="col">
            <div class="card text-dark bg-info mb-3" style="max-width: 18rem;">
                <div class="card-header">Header</div>
                <div class="card-body">
                    <h5 class="card-title">Info card title</h5>
                    <p class="card-text">Some quick example text to build on the card title and make up the bulk of the card's content.</p>
                </div>
            </div>
        </div>
        <div class="col">
            <div class="card text-dark bg-info mb-3" style="max-width: 18rem;">
                <div class="card-header">Header</div>
                <div class="card-body">
                    <h5 class="card-title">Info card title</h5>
                    <p class="card-text">Some quick example text to build on the card title and make up the bulk of the card's content.</p>
                </div>
            </div>
        </div>
    </div>
    <div class="row mt-2">
        <div class="col">1</div>
        <div class="col">2</div>
        <div class="col">3</div>
    </div>
</div>

<div id="carouselExampleIndicators" class="carousel slide w-100 h-100"  data-ride="carousel">
    <ol class="carousel-indicators">
        <li data-target="#carouselExampleIndicators" data-slide-to="0" class="active"></li>
        <li data-target="#carouselExampleIndicators" data-slide-to="1"></li>
        <li data-target="#carouselExampleIndicators" data-slide-to="2"></li>
    </ol>
    <div class="carousel-inner">
        <div class="carousel-item active">
            <img class="d-block w-100 h-100" src="http://www.poeltcommunication.de/wp-content/uploads/2017/10/poelcommunication-fashion-landscape-s-w-18.jpg" alt="First slide">
        </div>
        <div class="carousel-item">
            <img class="d-block w-100 h-100" src="http://www.poeltcommunication.de/wp-content/uploads/2017/10/poelcommunication-fashion-landscape-s-w-18.jpg" alt="Second slide">
        </div>
        <div class="carousel-item">
            <img class="d-block w-100 h-100" src="http://www.poeltcommunication.de/wp-content/uploads/2017/10/poelcommunication-fashion-landscape-s-w-18.jpg" alt="Third slide">
        </div>
    </div>
    <a class="carousel-control-prev" href="#carouselExampleIndicators" role="button" data-slide="prev">
        <span class="carousel-control-prev-icon" aria-hidden="true"></span>
        <span class="sr-only">Previous</span>
    </a>
    <a class="carousel-control-next" href="#carouselExampleIndicators" role="button" data-slide="next">
        <span class="carousel-control-next-icon" aria-hidden="true"></span>
        <span class="sr-only">Next</span>
    </a>
</div>






<!-- Optional JavaScript; choose one of the two! -->

<!-- Option 1: Bootstrap Bundle with Popper -->

<%--<div class="form-check form-check-inline">--%>
<%--    <input type="radio" class="btn-check" name="options-outlined" id="35">--%>
<%--    <label class="btn btn-round btn-outline-secondary" for="35">35</label>--%>

<%--    <input type="radio" class="btn-check" name="options-outlined" id="37">--%>
<%--    <label class="btn btn-round btn-outline-secondary" for="37">37</label>--%>
<%--</div>--%>

<form method="get" action="Controller?command=gotocart">
    <div class="form-group mb-2">
        <div class="form-check form-check-inline">
            <input type="radio" class="btn-check" value="35" name="options-outlined" id="35">
            <label class="btn btn-round btn-outline-secondary" for="35">35</label>
        </div>
        <div class="form-check form-check-inline">
            <input type="radio" class="btn-check" value="37" name="options-outlined" id="37">
            <label class="btn btn-round btn-outline-secondary" for="37">37</label>
        </div>
    </div>
</form>

<div class="radio-toolbar">
    <input type="radio" id="radioApple" name="radioFruit" value="apple" checked>
    <label for="radioApple">37</label>

    <input type="radio" id="radioBanana" name="radioFruit" value="banana">
    <label for="radioBanana">35</label>

    <input type="radio" id="radioOrange" name="radioFruit" value="orange">
    <label for="radioOrange">36</label>
</div>


</body>
</html>
