<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Mini Settlement</title>
        <!-- Bootstrap -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">

        <!-- Icons -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons/font/bootstrap-icons.css" rel="stylesheet">

        <!-- jQuery -->
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
        <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


        <style>
            body {
                background: #f4f6f9;
            }

            /* SIDEBAR */
            .sidebar {
                height: 100vh;
                background: #0b2e4a;
                color: white;
                padding: 20px;
            }

            .sidebar a {
                color: #cfd8dc;
                display: block;
                padding: 10px;
                text-decoration: none;
                border-radius: 8px;
            }

            .sidebar a:hover {
                background: #133a5a;
                color: white;
            }

            .sidebar a.active {
                background: #1e88e5;
                color: white;
            }

            /* TOPBAR */
            .topbar {
                background: #1b3351;
                width: 100%;
                height: 80px;
                /*padding: 15px;*/
                /*border-radius: 12px;*/
            }

            /* CARDS */
            .card {
                border: none;
                border-radius: 12px;
            }
            .tanchetText{
                margin-left: 50px;
            }
            .menu.active {
                background-color: #133a5a; /* Bootstrap primary */
                color: white !important;
                border-radius: 8px;
            }
        </style>
    </head>
    <body>
        <div class="container-fluid">
            <div class="row">

                <!-- SIDEBAR -->
                <div class="col-md-2 sidebar">
                    <div class="d-flex justify-content-center"> 
                        <div>
                            <img src="https://www.acledabank.com.kh/kh/assets/layout/logo3.png" width="170px" height="45px"/> 
                            <h4 class="text-white mt-2 tanchetText">TANCHET</h4> 
                        </div>
                    </div>

                    <a href="#Overview" class="menu active" onclick="loadPage('overview')">
                        <i class="bi bi-speedometer2"></i> Overview
                    </a>

                    <a href="#Merchant" class="menu" onclick="loadPage('merchant')">
                        <i class="bi bi-person"></i> Merchants
                    </a>

                    <a href="#Transaction" class="menu" onclick="loadPage('transaction')">
                        <i class="bi bi-credit-card"></i> Transactions
                    </a>

                    <a href="#Settlement" class="menu" onclick="loadPage('settlement')">
                        <i class="bi bi-bar-chart"></i> Settlements
                    </a>
                    <c:if test="${role =='admin'}">
                        <a href="#User" class="menu" onclick="loadPage('user')">
                            <i class="bi bi-people"></i> Users
                        </a>
                    </c:if>
                </div>

                <!-- MAIN -->
                <div class="col-md-10 w-full">

                    <!--TOP BAR--> 
                    <div class="topbar d-flex justify-content-end align-items-center mb-3">
                        <!--<h4 id="page-title">Overview</h4>-->
                        <!--<input class="form-control w-25" placeholder="Search...">-->
                        <h5 class="text-white p-4">Welcome Back, <b class="text-info">${sessionScope.user}</b>!</h5>
                        <button class="btn btn-danger px-4 shadow-sm btnlogout">
                            Logout
                        </button>
                    </div>

                    <!-- DYNAMIC CONTENT -->
                    <div id="main-content"></div>

                </div>

            </div>
        </div>

        <script>

            $(document).on("click", ".btnlogout", function () {
                $.ajax({
                    url: "logout",
                    success: function () {
                        Swal.fire({
                            title: "Are you sure to logout?",
                            icon: "warning",
                            showCancelButton: true
                        }).then((result) => {
                            if (result.isConfirmed) {
                                window.location.href = "login";
                            }
                        });
                    }
                });
            });
            function loadPage(page) {
                $(document).on("click", ".menu", function () {
                    $(".menu-item").removeClass("active"); // remove from all
                    $(this).addClass("active");            // add to clicked
                });
                // active menu highlight
                $(".menu").removeClass("active");
                // load content
                $("#main-content").html("<p>Loading...</p>");
                $.ajax({
                    url: "${pageContext.request.contextPath}/" + page,
                    type: "GET",
                    success: function (data) {
                        $("#main-content").html(data);
                    },
                    error: function () {
                        $("#main-content").html("<h5>404 not found!!</h5>");
                    }
                });
            }

            // default page
            $(document).ready(function () {
                loadPage("merchant");
            });


        </script>
    </body>
</html>
