<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Register Page</title>
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
        </style>
    </head>
    <body>
        
        <div class="overlay"></div>

        <div class="login-box">

            <!-- LEFT LOGIN -->
            <div class="login-form">
                <h2>Register</h2>

                <input type="text" class="form-control" id="username" placeholder="Username">
                <input type="password" class="form-control" id="password" placeholder="Password">


                <button class="btn-login" id="registerBtn">Register</button>
                <button class="btn-register"><a href="<%= request.getContextPath()%>/login">Login</a></button>
            </div>

            <!-- RIGHT WELCOME -->
            <div class="welcome">
                <h1>Welcome</h1>
                <p>Settlement System Login Portal</p>
            </div>

        </div>
<!--        <h2>Register</h2>

        <input type="text" id="username" placeholder="Username"><br>
        <input type="password" id="password" placeholder="Password"><br>
        <button id="registerBtn">Register</button>

        <p id="msg"></p>

        <a href="<%= request.getContextPath()%>/login">Register</a>-->

        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>

        <script>
            $("#registerBtn").click(function () {

                $.ajax({
                    url: "register",
                    type: "POST",
                    dataType: "json", 
                    data: {
                        username: $("#username").val(),
                        password: $("#password").val()
                    },
                    success: function (data) {

                        // ❌ NO JSON.parse needed
                        if (data.status === "success") {

                            Swal.fire({
                                title: "Register Successfully!",
                                icon: "success",
                                confirmButtonText: "Login"
                            }).then((result) => {

                                if (result.isConfirmed) {
                                    window.location.href = "<%= request.getContextPath()%>/login";
                                }
                            });
                        } else {
                            Swal.fire({
                                title: "Username already exists!",
                                icon: "error"
                            });
                        }
//                        if (data.status === "success") {
//                            $("#msg")
//                                    .css("color", "green")
//                                    .text(data.message);
//                        } else {
//                            $("#msg")
//                                    .css("color", "red")
//                                    .text(data.message);
//                        }
                    },
                    error: function (xhr, status, error) {
                        console.log("AJAX Error:", error);
                        $("#msg")
                                .css("color", "red")
                                .text("Server error!");
                    }
                });
            });
        </script>
    </body>
</html>
