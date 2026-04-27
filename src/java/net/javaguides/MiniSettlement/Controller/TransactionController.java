package net.javaguides.MiniSettlement.Controller;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.javaguides.MiniSettlement.DAO.TransactionInterface;
import net.javaguides.MiniSettlement.DAO.TransationDAO;
import net.javaguides.MiniSettlement.Models.Transaction;

@WebServlet("/transaction")
public class TransactionController extends HttpServlet {

    private TransactionInterface tran;

    @Override
    public void init() {
        tran = new TransationDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            System.out.println("=== doGet START ===");
            String action = request.getParameter("action");
            System.out.println("Action parameter: " + action);

            if (action == null) {
                action = "";
            }

            switch (action) {
                case "new":
                    System.out.println("Forwarding to mainDash.jsp");
                    request.getRequestDispatcher("/WEB-INF/views/transaction.jsp").forward(request, response);
                    break;

                case "getById":
                    System.out.println("=== getById START ===");

                    String getById = request.getParameter("id");
                    System.out.println("Raw id param: " + getById);

                    int transacId = Integer.parseInt(getById);
                    System.out.println("Parsed ID: " + transacId);

                    Transaction getTransaction = tran.getTransacById(transacId);

                    System.out.println("Transaction from DB: " + getTransaction);

                    request.setAttribute("getTransacId", getTransaction);

                    System.out.println("Forwarding to JSP...");

                    request.getRequestDispatcher("/WEB-INF/views/transaction.jsp")
                            .forward(request, response);

                    System.out.println("=== getById END ===");
                    break;

                case "edit":
                    System.out.println("Edit action - getting ID");

                    String idParam = request.getParameter("id");
                    int id = Integer.parseInt(idParam);

                    Transaction transaction = tran.getById(id);

                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");

                    String json
                            = "{"
                            + "\"id\":" + transaction.getTransactionId() + ","
                            + "\"merchantId\":\"" + transaction.getMerchantId() + "\","
                            + "\"amount\":\"" + transaction.getAmount() + "\","
                            + "\"status\":\"" + transaction.getStatus() + "\","
                            + "\"settlementStatus\":\"" + transaction.getSettlementStatus() + "\""
                            + "}";

                    response.getWriter().write(json);
                    break;

                case "delete":
                    int deleteId = Integer.parseInt(request.getParameter("id"));
                    tran.deleteTransaction(deleteId);
                    response.setContentType("text/plain");
                    response.getWriter().write("success");
                    break;

                default:
                    System.out.println("List action - getting all transaction");
                    int page = 1;
                    int pageSize = 10;

                    if (request.getParameter("page") != null) {
                        page = Integer.parseInt(request.getParameter("page"));
                    }

                    int offset = (page - 1) * pageSize;
                    List<Transaction> list = tran.getAllTransaction(pageSize, offset);
                    int totalRecords = tran.count();
                    int totalPages = (int) Math.ceil(totalRecords * 1.0 / pageSize);
                    request.setAttribute("AllTransactions", list);
                    request.setAttribute("currentPage", page);
                    request.setAttribute("totalPages", totalPages);
                    request.getRequestDispatcher("/WEB-INF/views/transaction.jsp").forward(request, response);
                    break;
            }
            System.out.println("=== doGet END ===");
        } catch (Exception e) {
            System.err.println("ERROR in doGet: " + e.getMessage());
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error: " + e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            System.out.println("=== doPost START ===");

            String merchantId = request.getParameter("merchantId");
            String amount = request.getParameter("amount");
            String status = request.getParameter("status");
            String settlementStatus = request.getParameter("settlementStatus");

            System.out.println("MerchantID: " + merchantId);
            System.out.println("amount: " + amount);
            System.out.println("settlement Status: " + settlementStatus);
            System.out.println("status: " + status);

            // FIX 1: use "id" not "merchant_id"
            String idParam = request.getParameter("id");
            System.out.println("ID param: " + idParam);

            int id = 0;
            if (idParam != null && !idParam.isEmpty()) {
                id = Integer.parseInt(idParam);
            }

            Transaction transac = new Transaction();
            transac.setMerchantId(Integer.parseInt(merchantId));
            transac.setAmount(Float.parseFloat(amount));
            transac.setSettlementStatus(settlementStatus);
            transac.setStatus(status);

            if (id == 0) {
                System.out.println("Inserting new Transaction");
                tran.createTransaction(transac);
            } else {
                System.out.println("Updating Transaction with id: " + id);
                transac.setTransactionId(id);
                tran.updateTransaction(transac);
            }

            // FIX 2: correct redirect
//            response.sendRedirect(request.getContextPath() + "/mainDash");
            response.setContentType("text/plain");
            response.getWriter().write("success");

            System.out.println("=== doPost END ===");

        } catch (Exception e) {
            System.err.println("ERROR in doPost: " + e.getMessage());
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Error: " + e.getMessage());
        }
    }
}
