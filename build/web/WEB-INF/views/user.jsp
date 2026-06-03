<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>User</title>
        <!-- Bootstrap -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.7/dist/js/bootstrap.bundle.min.js"></script>
        <!-- Icons -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons/font/bootstrap-icons.css" rel="stylesheet">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/waitme@1.19.0/waitMe.min.css">


        <!-- jQuery -->
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>    
        <script src="https://cdn.jsdelivr.net/npm/waitme@1.19.0/waitMe.min.js"></script>
        <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


        <style>
            .btnaddnew{
                background: #1b3351;
                width: 150px;
            }
            .btnaddnew:hover{
                background: #133a5a;
            }

        </style>
    </head>
    <body>
        <div class="d-flex justify-content-between align-item-center mb-3">
            <h2>User List</h2>
            <button id="openModalBtn" class="text-white border-none rounded-pill btnaddnew" >Add new user!</button>
        </div>
        <div id="alertBox"></div>
        <div class="table-responsive">
            <table class="table" border="1">
                <tr>
                    <th>ID</th>
                    <th>Username</th>
                    <th>Password</th>
                    <th>Role</th>
                    <th>Action</th>
                </tr>
                <c:forEach var="t" items="${user}">
                    <tr>
                        <td>${t.id}</td>
                        <td>${t.username}</td>
                        <td>${t.password}</td>
                        <td>${t.role}</td>
                        <td>
                            <button  class="btn btn-warning btn-sm editBtn" data-id="${t.id}">
                                Edit
                            </button>

                            <button class="btn btn-danger btn-sm deleteBtn" data-id="${t.id}">
                                Delete
                            </button>
                        </td>
                    </tr>
                </c:forEach>

            </table>

            <div class="modal fade" id="myModal" tabindex="-1" aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title">
                                <h5 id="title"></h5>
                            </h5>
                            <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                        </div>
                        <div class="modal-body">
                            <form action="${pageContext.request.contextPath}/user" method="post">

                                <input type="hidden" name="id" id="id">

                                <input type="text" name="username" id="username" placeholder="Username" class="form-control mb-2">

                                <input type="text" name="password" id="password" placeholder="password" class="form-control mb-2">

                                <select name="role" id="role" class="form-control mb-2" placeholder="Select role">
                                    <option value="viewer">VIEWER</option>
                                    <option value="user">USER</option>
                                    <option value="admin">ADMIN</option>
                                </select>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">
                                        Close
                                    </button>

                                    <button type="submit" class="btn btn-primary btnsave">
                                    </button>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>

            <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

            <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>

            <script>

                $(document).on("click", ".editBtn", function () {

                    let id = $(this).data("id");
                    console.log("Clicked ID:", id);
                    $.ajax({
                        url: "${pageContext.request.contextPath}/user?action=edit&id=" + id,
                        type: "GET",
                        dataType: "json",
                        success: function (u) {
                            $("#id").val(u.id);
                            $("#username").val(u.username);
                            $("#password").val(u.password);
                            $("#role").val(u.role);
                            $("#title").text("Update User!");
                            $(".btnsave").text("Update User");
                            var modal = new bootstrap.Modal(document.getElementById('myModal'));
                            modal.show();
                        }
                    });
                });
                $(document).ready(function () {

                    // OPEN MODAL
                    $("#openModalBtn").click(function () {

                        $("#title").text("Add New User!");

                        $("#username").val("");
                        $("#password").val("");
                        $("#role").val("");
                        $(".btnsave").text("Register");


                        var modal = new bootstrap.Modal(document.getElementById('myModal'));
                        modal.show();
                    });

                    // REGISTER USER
                    $("form").submit(function (e) {
                        e.preventDefault(); // IMPORTANT (stop form submit)
                        $("#main-content").waitMe({
                            effect: 'bounce',
                            text: 'Loading...',
                            bg: 'rgba(255,255,255,0.7)',
                            color: '#000'
                        });
                        $.ajax({
                            url: $(this).attr("action"),
                            type: "POST",
                            dataType: "json",
                            data: $(this).serialize(),
                            success: function (data) {
                                console.log(data);
                                if (data.status === "success") {
                                    $("#main-content").waitMe('hide');
                                    Swal.fire({
                                        title: data.message,
                                        icon: "success"
                                    });
//                                    $("#myModal").modal("hide");
                                    let modalEl = document.getElementById('myModal');
                                    let modal = bootstrap.Modal.getInstance(modalEl);

                                    if (modal) {
                                        modal.hide();
                                    }

                                    // optional: fix focus warning
                                    document.activeElement.blur();
                                    $("#main-content").load("user");
                                } else {
                                    $("#main-content").waitMe('hide');
                                    Swal.fire({
                                        title: data.message,
                                        icon: "error"
                                    });
                                    return;
                                }
                            },
                            error: function (xhr, status, error) {
                                $("#main-content").waitMe('hide');

                                console.log("AJAX Error:", error);

                                Swal.fire({
                                    title: "Server Error!",
                                    icon: "error"
                                });
                            }
                        });
                    });

                });
                $(document).off("click.delete").on("click.delete", ".deleteBtn", function () {

                    let id = $(this).data("id");

                    Swal.fire({
                        title: "Are you sure?",
                        text: "This will be deleted on id:" + id + "!",
                        icon: "warning",
                        showCancelButton: true,
                        confirmButtonColor: "#d33",
                        cancelButtonColor: "#3085d6",
                        confirmButtonText: "Yes, delete it!"
                    }).then((result) => {

                        if (result.isConfirmed) {

                            $.ajax({
                                url: "user",
                                type: "GET",
                                data: {
                                    action: "delete",
                                    id: id
                                },
                                success: function (res) {
                                    console.log("response:" + res);
                                    if (res === "success") {

                                        Swal.fire({
                                            title: "Deleted!",
                                            text: "User has been deleted.",
                                            icon: "success"
                                        });

                                        // remove row instantly (no reload)
                                        $("button[data-id='" + id + "']")
                                                .closest("tr")
                                                .remove();

                                    } else {

                                        Swal.fire({
                                            title: "Error!",
                                            text: "Delete failed.",
                                            icon: "error"
                                        });
                                    }
                                },
                                error: function () {

                                    Swal.fire({
                                        title: "Server Error!",
                                        text: "Please try again later.",
                                        icon: "error"
                                    });
                                }
                            });
                        }
                    });
                });
            </script>
    </body>
</html>
