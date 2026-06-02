<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login</title>

        <style>
            body {
                margin: 0;
                padding: 0;
                height: 100vh;
                /*background: url('https://images.unsplash.com/photo-1520607162513-77705c0f0d4a') no-repeat center center/cover;*/
                display: flex;
                align-items: center;
                justify-content: center;
                font-family: Arial, sans-serif;
            }

            /* dark overlay */
            .overlay {
                position: absolute;
                width: 100%;
                height: 100%;
                background: rgba(0,0,0,0.4);
                top: 0;
                left: 0;
            }

            .login-box {
                position: relative;
                z-index: 2;
                width: 850px;
                height: 450px;
                display: flex;
                box-shadow: 0 10px 30px rgba(0,0,0,0.5);
                border-radius: 10px;
                overflow: hidden;
            }

            /* Left login form */
            .login-form {
                width: 40%;
                background: #f5f5f5;
                padding: 40px;
            }

            .login-form h2 {
                text-align: center;
                margin-bottom: 30px;
                font-weight: bold;
            }

            .form-control {
                margin-bottom: 15px;
                border-radius: 5px;
            }

            .btn-login {
                width: 100%;
                background: #007bff;
                color: white;
                border: none;
                padding: 10px;
                border-radius: 5px;
            }

            .btn-login:hover {
                background: #0056b3;
            }
            .btn-register {
                width: 100%;
                color: black;
                border: #0056b3;
                padding: 10px;
                border-radius: 5px;
                margin-top: 10px;
            }
            .forgot {
                font-size: 12px;
                text-align: right;
                display: block;
                margin-bottom: 20px;
            }

            /* Right welcome panel */
            .welcome {
                width: 60%;
                background: linear-gradient(rgba(0,0,0,0.4), rgba(0,0,0,0.4)),
                    url('https://cambodiainvestmentreview.com/wp-content/uploads/2024/09/Edit-Building-2021-scaled-1.jpg') center/cover;
                color: white;
                display: flex;
                flex-direction: column;
                justify-content: center;
                align-items: center;
                text-align: center;
                padding: 30px;
            }

            .welcome h1 {
                font-size: 40px;
                font-weight: bold;
            }

            .welcome p {
                max-width: 300px;
            }
            .input{
                height:50px;

            }
            .input-group {
                position: relative;
                margin-bottom: 20px;
            }

            .input-group input {
                width: 100%;
                padding: 14px 16px;
                border: 1px solid #dcdcdc;
                border-radius: 12px;
                background: #f9fafb;
                font-size: 15px;
                outline: none;
                transition: all 0.25s ease;
                box-sizing: border-box;
            }

            .input-group label {
                position: absolute;
                top: 50%;
                left: 14px;
                transform: translateY(-50%);
                background: #f9fafb;
                padding: 0 5px;
                color: #888;
                font-size: 14px;
                pointer-events: none;
                transition: 0.2s ease;
            }

            .input-group input:focus {
                border-color: #2563eb;
                background: #fff;
                box-shadow: 0 0 0 4px rgba(37, 99, 235, 0.12);
            }

            .input-group input:focus + label,
            .input-group input:not(:placeholder-shown) + label {
                top: 0;
                left: 12px;
                font-size: 12px;
                color: #2563eb;
                background: #fff;
            }

            .input-group input::placeholder {
                color: transparent;
            }
            .btn-register-link{
                width: 100%;
                display: block;
                text-align: center;
                padding: 10px;
                margin-top: 10px;
                border-radius: 5px;
                border: 1px solid #007bff;
                text-decoration: none;
                color: #007bff;
                box-sizing: border-box;
                transition: 0.2s;
            }

            .btn-register-link:hover{
                background: #007bff;
                color: white;
            }
            #Loginresult{
                color:red;
                text-align: center;
            }
        </style>
    </head>
    <body>

        <div class="overlay"></div>

        <div class="login-box">

            <!-- LEFT LOGIN -->
            <!--            <div class="login-form">
                            <h2>Login</h2>
            
                            <input type="text" class="form-control" id="username" placeholder="Username">
                            <input type="password" class="form-control" id="password" placeholder="Password">
            
                            <a href="#" class="forgot">Forgot Password?</a>
            
                            <button class="btn-login" id="loginBtn">Login</button>
                            <button class="btn-register"><a href="<%= request.getContextPath()%>/register">Register</a></button>
                        </div>-->
            <form id="loginForm">

                <div class="login-form" style="width: 80%; height: 90%">

                    <h2>Login</h2>
                    <h6 id="Loginresult"></h6>
                    <div class="input-group">
                        <input type="text"
                               id="username"
                               name="username"
                               placeholder="Username"
                               autocomplete="username"
                               required>
                        <label for="username">Username</label>
                    </div>

                    <div class="input-group">
                        <input type="password"
                               id="password"
                               name="password"
                               placeholder="Password"
                               autocomplete="current-password"
                               required>
                        <label for="password">Password</label>
                    </div>

                    <a href="<%= request.getContextPath()%>/register" class="forgot" id="forgot">Change Password?</a>

                    <button type="submit" class="btn-login" id="btnLogin">
                        Login
                    </button>

                    

                </div>

            </form>

            <!-- RIGHT WELCOME -->
            <div class="welcome">
                <h1>Welcome</h1>
                <p>Settlement System Login Portal</p>
            </div>

        </div>

        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>


        <script>
            $("#forgot").hide();

            $("#loginForm").submit(function (e) {

                e.preventDefault();

                $.ajax({
                    url: "login",
                    type: "POST",
                    dataType: "json",
                    data: {
                        username: $("#username").val(),
                        password: $("#password").val()
                    },

                    success: function (data) {

                        console.log(data);

                        if (data.status === "success") {
                            window.location.href =
                                    "<%= request.getContextPath()%>/mainDash";

                        } else if (data.status === "update") {
                            $("#Loginresult").html(data.message);
                            $("#forgot").show();
                            $("#btnLogin").css("pointer-events", "none");
                            return;
                        } else {
                            $("#Loginresult").html(data.message);
                        }
                    },

                    error: function (xhr, status, error) {

                        console.log("AJAX Error:", error);
                        $("#Loginresult").html(error);
//                        Swal.fire({
//                            title: "Server Error",
//                            text: "Please try again later.",
//                            icon: "error"
//                        });
                    }
                });
            });
        </script>
    </body>
</html>
