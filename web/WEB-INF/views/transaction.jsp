<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Transaction Page</title>
        <!-- Bootstrap -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">


        <!-- Icons -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons/font/bootstrap-icons.css" rel="stylesheet">

        <!-- jQuery -->
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

        <!-- Chart.js -->
        <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
        <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <style>
            .btnaddnew{
                background: #1b3351;
            }
            .btnaddnew:hover{
                background: #133a5a;
            }
        </style>
    </head>
    <body>
        <div class="d-flex justify-content-between align-item-center mb-3">
            <h2>Transaction List</h2>
            <button id="openModalBtn" class="text-white border-none rounded-pill btnaddnew" ${role == 'viewer' ? 'disabled' : ''}>Add new transaction</button>
        </div>
        <div id="alertBox"></div>
        <div class="table-responsive">
            <table class="table" border="1">
                <tr>
                    <!--<th></th>-->
                    <th>ID</th>
                    <th>Merchant Id</th>
                    <th>Amount</th>
                    <th>Status</th>
                    <th>Settlement status</th>
                    <th>Created At</th>
                    <th>Action</th>
                </tr>

                <c:forEach var="t" items="${AllTransactions}">
                    <tr>
                        <td>${t.transactionId}</td>
                        <td>${t.merchantId}</td>
                        <td>${t.amount}$</td>
                        <td>${t.status}</td>
                        <td class="${t.settlementStatus == 'PENDING' ? 'text-info fw-bold' : 'text-success fw-bold'}">${t.settlementStatus}</td>
                        <td>${t.createdAt}</td>
                        <td>

                            <button  class="btn btn-warning btn-sm editBtn" data-id="${t.transactionId}" ${role == 'viewer' ? 'disabled' : ''}>
                                Edit
                            </button>

                            <button class="btn btn-danger btn-sm deleteBtn"
                                    data-id="${t.transactionId}"
                                    ${role == 'viewer' ? 'disabled' : ''}>
                                Delete
                            </button>
                        </td>
                    </tr>
                </c:forEach>

            </table>
            <div class="d-flex justify-content-center mt-3">

                <c:if test="${currentPage > 1}">
                    <a class="btn btn-primary mx-1 " data-page="${currentPage - 1}" id="page-link">
                        Prev
                    </a>
                </c:if>

                <c:forEach begin="1" end="${totalPages}" var="i">
                    <a class="btn mx-1 ${i == currentPage ? 'btn-dark' : 'btn-outline-dark'} " 
                       data-page="${i}" id="page-link">
                        ${i}
                    </a>
                </c:forEach>

                <c:if test="${currentPage < totalPages}">
                    <a class="btn btn-primary mx-1 " data-page="${currentPage + 1}" id="page-link">
                        Next
                    </a>
                </c:if>

            </div>
        </div>
        <!-- Modal -->
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

                        <form action="${pageContext.request.contextPath}/transaction" method="post">

                            <input type="hidden" name="transactionId" id="id">

                            <input type="text" name="merchantId" id="merchantId" placeholder="Merchant Id" class="form-control mb-2">

                            <input type="text" name="amount" id="amount" placeholder="Amount" class="form-control mb-2">

                            <input type="text" name="settlementStatus" id="settlementStatus" value="PENDING" placeholder="Settlement Status" class="form-control mb-2">

                            <input type="text" name="status" id="status" value="SUCCESS" placeholder="Status" class="form-control mb-2">

                            <div class="modal-footer">

                                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">
                                    Close
                                </button>

                                <button type="submit" class="btn btn-primary">
                                    Save
                                </button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
        <script>

            $(document).on("click", "#page-link", function (e) {
                e.preventDefault();
                var page = $(this).data("page");
                $.ajax({
                    url: "${pageContext.request.contextPath}/transaction",
                    type: "GET",
                    data: {page: page},
                    success: function (data) {
                        // this works because transaction.jsp is inside the same DOM as dashboard
                        $("#main-content").html(data);
                    },
                    error: function (xhr) {
                        if (xhr.status === 403) {
                            Swal.fire({title: "Access Denied!", icon: "warning"});
                        }
                    }
                });
            });
            //insert the new data
            $("form").submit(function (e) {
                e.preventDefault();
                $.ajax({
                    url: $(this).attr("action"),
                    type: "POST",
                    data: $(this).serialize(),
                    success: function (res) {
                        if (res === "success") {
                            $("#myModal").modal("hide");
                            $("#main-content").load("transaction");
                            Swal.fire({
                                title: "Inserted Successfully!!",
                                icon: "success"
                            });
                        } else {
                            Swal.fire({
                                title: "Failed!",
                                text: "Something went wrong.",
                                icon: "error"
                            });
                        }
                    },
                    error: function (xhr) {
                        if (xhr.status === 403) {
                            Swal.fire({
                                title: "Access Denied!",
                                text: "You only have view access.",
                                icon: "warning"
                            });
                        } else {
                            Swal.fire({
                                title: "Error!",
                                text: "Server error: " + xhr.status,
                                icon: "error"
                            });
                        }
                    }
                });
            });
            //set value function
            $(document).ready(function () {

                $("#openModalBtn").click(function () {
                    function resetModal() {
                        $("#id").val("");
                        $("#merchantId").val("");
                        $("#amount").val("");
                        $("#settlementStatus").val("PENDING");
                        $("#status").val("SUCCESS");
                    }
                    $('#myModal').on('hidden.bs.modal', function () {
                        resetModal();
                    });
                    $("#title").text("Add New Transaction!");
                    var modal = new bootstrap.Modal(document.getElementById('myModal'));
                    modal.toggle();
                });
            });
            //set value edit
            $(document).on("click", ".editBtn", function () {

                let id = $(this).data("id");
                console.log("Clicked ID:", id);
                $.ajax({
                    url: "${pageContext.request.contextPath}/transaction?action=edit&id=" + id,
                    type: "GET",
                    dataType: "json",
                    success: function (transac) {
                        $("#id").val(transac.id);
                        $("#merchantId").val(transac.merchantId);
                        $("#amount").val(transac.amount);
                        $("#status").val(transac.status);
                        $("#settlementStatus").val(transac.settlementStatus);
                        $("#title").text("Update Transaction!");
                        var modal = new bootstrap.Modal(document.getElementById('myModal'));
                        modal.show();
                    }
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
                            url: "transaction",
                            type: "GET",
                            data: {
                                action: "delete",
                                id: id
                            },
                            success: function (res) {

                                if (res === "success") {

                                    Swal.fire({
                                        title: "Deleted!",
                                        text: "Transaction has been deleted.",
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
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>