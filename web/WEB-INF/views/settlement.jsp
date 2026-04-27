<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <!-- Bootstrap -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">


        <!-- Icons -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons/font/bootstrap-icons.css" rel="stylesheet">

        <!-- jQuery -->
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>        
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
            <h2>Settlemet List</h2>
            <button id="openModalBtn" class="text-white border-none rounded-pill btnaddnew" >Pay out</button>
        </div>
        <div id="alertBox"></div>
        <div class="table-responsive">
            <table class="table" border="1">
                <tr>
                    <!--<th></th>-->
                    <th>ID</th>
                    <th>Merchant Id</th>
                    <th>total Amount</th>
                    <th>Fee</th>
                    <th>Net Amount</th>
                    <th>Created At</th>
                    <th>Action</th>
                </tr>
                <c:forEach var="t" items="${settlementList}">
                    <tr>
                        <td>${t.settlementId}</td>
                        <td>${t.merchantId}</td>
                        <td>${t.totalAmount}$</td>
                        <td>${t.fee}</td>
                        <td>${t.netAmount}</td>
                        <td>${t.createdAt}</td>
                        <td>
                            <button  class="btn btn-warning btn-sm editBtn" data-id="${t.settlementId}" ${role == 'viewer' ? 'disabled' : ''}>
                                Edit
                            </button>

                            <button class="btn btn-danger btn-sm deleteBtn" data-id="${t.settlementId}" ${role == 'viewer' ? 'disabled' : ''}>
                                Delete
                            </button>
                        </td>
                    </tr>
                </c:forEach>

            </table>
            <script>

//                $(document).off("click.delete").on("click.delete", ".deleteBtn", function () {
//
//                    let id = $(this).data("id");
//
//                    if (confirm("Are you sure?")) {
//                        loadPage("settlement?action=delete&id=" + id);
//                    }
//                });
                $(document).off("click.delete").on("click.delete", ".deleteBtn", function () {

                    let id = $(this).data("id");

                    Swal.fire({
                        title: "Are you sure?",
                        text: "This will delete id: "+id+"!",
                        icon: "warning",
                        showCancelButton: true,
                        confirmButtonColor: "#d33",
                        cancelButtonColor: "#3085d6",
                        confirmButtonText: "Yes, delete it!"
                    }).then((result) => {

                        if (result.isConfirmed) {

                            $.ajax({
                                url: "${pageContext.request.contextPath}/settlement",
                                type: "GET",
                                data: {
                                    action: "delete",
                                    id: id
                                },
                                success: function (res) {
                                   console.log("res:"+res);
                                    if (res === "success") {

                                        Swal.fire({
                                            title: "Deleted!",
                                            text: "Settlement has been deleted.",
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
