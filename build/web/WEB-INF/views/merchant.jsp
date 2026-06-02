<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Merchants Page</title>
        <!-- Bootstrap -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">

        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/flatpickr/dist/flatpickr.min.css">
        <script src="https://cdn.jsdelivr.net/npm/flatpickr"></script>


        <!-- Icons -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons/font/bootstrap-icons.css" rel="stylesheet">

        <!-- jQuery -->
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

        <!-- Chart.js -->
        <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
        <!-- Pagination.js -->
        <script src="pagination.min.js"></script>

        <link rel="stylesheet" href="pagination.css">
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
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
            <h2>The Merchant</h2>
            <button id="openModalBtn" class="text-white border-none rounded-pill btnaddnew" ${role == 'viewer' ? 'disabled' : ''}>Add new merchant</button>
        </div>
        <div id="alertBox"></div>
        <div class="table-responsive">
            <table class="table demo" border="1">
                <tr>
                    <!--<th></th>-->
                    <th>ID</th>
                    <th>Name</th>
                    <th>Email</th>
                    <th>Phone</th>
                    <th>Status</th>
                    <th>Created At</th>
                        <c:if test="${role !='viewer'}">
                        <th>Action</th>
                        </c:if>
                </tr>


                <c:forEach var="m" items="${merchantList}">
                    <tr>
                        <td>${m.merchant_id}</td>
                        <td>${m.name}</td> 
                        <td>${m.email}</td>
                        <td>${m.phone}</td>
                        <td>${m.status}</td>
                        <td>${m.created_at}</td>
                        <td>
                            <c:if test="${role !='viewer'}">
                                <button  class="btn btn-primary btn-sm viewTxBtn" data-id="${m.merchant_id}">
                                    Transaction
                                </button>
                                <button  class="btn btn-warning btn-sm editBtn" data-id="${m.merchant_id}">
                                    Edit
                                </button>

                                <button class="btn btn-danger btn-sm deleteBtn" data-id="${m.merchant_id}">
                                    Delete
                                </button>
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>

            </table>

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
                            <form action="${pageContext.request.contextPath}/merchant" method="post">

                                <input type="hidden" name="id" id="id">

                                <input type="text" name="name" id="name" placeholder="Name" class="form-control mb-2">

                                <input type="text" name="email" id="email" placeholder="Email" class="form-control mb-2">

                                <input type="text" name="phone" id="phone" placeholder="Phone" class="form-control mb-2">
                                
                                <select name="status" id="status" class="form-control mb-2">
                                    <option value="ACTIVE">ACTIVE</option>
                                    <option value="SUSPENDED">SUSPENDED</option>
                                </select>
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

            <!-- Modal Transaction -->
            <div class="modal fade" id="txModal" tabindex="-1">
                <div class="modal-dialog modal-xl">
                    <div class="modal-content">

                        <div class="modal-header">
                            <h5 class="modal-title">Transactions</h5>
                            <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                        </div>
                        <div class="modal-body">
                            <table class="table table-bordered">
                                <thead>
                                    <tr>
                                        <th>Transaction ID</th>
                                        <th>Merchant Name</th>
                                        <th>Amount</th>
                                        <th>Status</th>
                                        <th>Settlement Status</th>
                                        <th>Date</th>
                                    </tr>
                                </thead>
                                <tbody id="txTable">
                                </tbody>
                            </table>
                            <label id="totalAmount" class="fw-bold d-flex justify-content-end"></label>
                        </div>
                        <div class="modal-footer">

                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">
                                Close
                            </button>

                            <button class="btn btn-warning btnSettleNow " data-bs-dismiss="modal">
                                Settle Now!
                            </button>

                            <div class="dropdown">
                                <button class="btn btn-primary dropdown-toggle" type="button" id="cutoffBtn" data-bs-toggle="dropdown" aria-expanded="false">
                                    <i class="bi bi-clock me-1"></i> Cut Off Time!
                                </button>
                                <div class="dropdown-menu p-3" style="min-width: 260px;">
                                    <label class="form-label text-muted small">Pick Hour</label>
                                    <div class="d-grid gap-1" style="grid-template-columns: repeat(6, 1fr); display: grid;">
                                        <!-- generated by JS below -->
                                    </div>
                                    <div class="border-top mt-2 pt-2 d-flex justify-content-between align-items-center">
                                        <small class="text-muted">Selected:</small>
                                        <strong id="selectedHourDisplay">—</strong>
                                    </div>
                                    <button class="btn btn-primary btn-sm w-100 mt-2" name="confirmCutoff" onclick="confirmCutoff()">Confirm</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <script>
                let selectedHour = null;
                const grid = document.querySelector('.dropdown-menu .d-grid');

                for (let h = 0; h < 24; h++) {
                    const btn = document.createElement('button');
                    btn.className = 'btn btn-outline-secondary btn-sm';
                    btn.textContent = String(h).padStart(2, '0');
                    btn.dataset.hour = h;
                    btn.onclick = () => {
                        selectedHour = h;
                        document.querySelectorAll('.dropdown-menu .d-grid button').forEach(b => b.classList.remove('active', 'btn-primary'));
                        btn.classList.add('active', 'btn-primary');
                        document.getElementById('selectedHourDisplay').textContent = String(h).padStart(2, '0') + ':00';
                    };
                    grid.appendChild(btn);
                }

                function confirmCutoff() {
                    if (selectedHour === null)
                        return;
                    document.getElementById('cutoffBtn').textContent = 'Cut Off: ' + String(selectedHour).padStart(2, '0') + ':00';
                    bootstrap.Dropdown.getInstance(document.getElementById('cutoffBtn')).hide();
                }
                
                if (!window.settleBound) {
                    window.settleBound = true;

                    $(document).on("click", ".btnSettleNow", function (e) {
                        e.preventDefault();

                        let btn = $(this);
                        let id = btn.data("id");

                        console.log("settle id:", id);

                        btn.prop("disabled", true); // prevent double click

                        $.ajax({
                            url: "settlement",
                            type: "POST",
                            data: {id: id},
                            success: function (res) {
                                console.log("Response:", res);

                                if (res === "settled") {
                                    Swal.fire({
                                        title: "Settled Successfully!!",
                                        icon: "success"
                                    });
                                } else {
                                    Swal.fire({
                                        title: res,
                                        icon: "error"
                                    });
                                }
                            },
                            complete: function () {
                                btn.prop("disabled", false);
                            }
                        });
                    });
                }
                //view settlement
                $(document).on("click", ".viewTxBtn", function () {
                    let id = $(this).data("id");
                    console.log("click id: ", id);
                    $(".btnSettleNow").data("id", id);
                    $.ajax({
                        url: "merchant",
                        type: "GET",
                        dataType: "json",
                        data: {
                            action: "viewTransactions",
                            id: id
                        },
                        success: function (data) {

                            let rows = "";
                            let TA = 0.00;
                            data.forEach(function (t) {
                                TA = (t.TotalAmount);
                                rows += "<tr>" +
                                        "<td>" + t.transactionId + "</td>" +
                                        "<td>" + t.merchantName + "</td>" +
                                        "<td>" + t.amount + "</td>" +
                                        "<td>" + t.status + "</td>" +
                                        "<td>" + t.settlementStatus+ "</td>" +
                                        "<td>" + t.createdAt + "</td>" +
                                        "</tr>";
                            });
                            $("#totalAmount").html("Total Amount: " + TA + "$");
                            $("#txTable").html(rows);
                            $("#txModal").modal("show");
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
                                $("#main-content").load("merchant");
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
                            $("#name").val("");
                            $("#email").val("");
                            $("#phone").val("");
                            $("#status").val("ACTIVE");
                        }
                        $('#myModal').on('hidden.bs.modal', function () {
                            resetModal();
                        });
                        $("#title").text("Add New Merchant!");
                        var modal = new bootstrap.Modal(document.getElementById('myModal'));
                        modal.toggle();
                    });
                });
                //set value edit
                $(document).on("click", ".editBtn", function () {

                    let id = $(this).data("id");
                    console.log("Clicked ID:", id);
                    $.ajax({
                        url: "${pageContext.request.contextPath}/merchant?action=edit&id=" + id,
                        type: "GET",
                        dataType: "json",
                        success: function (merchant) {
                            $("#id").val(merchant.id);
                            $("#name").val(merchant.name);
                            $("#email").val(merchant.email);
                            $("#phone").val(merchant.phone);
                            $("#status").val(merchant.status);
                            $("#title").text("Update Merchant!");
                            var modal = new bootstrap.Modal(document.getElementById('myModal'));
                            modal.show();
                        }
                    });
                });
//                //delete function
//                $(document).on("click", ".deleteBtn", function () {
//                    let id = $(this).data("id");
//                    if (confirm("Are you sure?")) {
//                        loadPage("merchant?action=delete&id=" + id);
//                    }
//                });
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
                                url: "merchant",
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
                                            text: "Merchant has been deleted.",
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