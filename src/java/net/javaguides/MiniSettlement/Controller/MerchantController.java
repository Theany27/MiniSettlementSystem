package net.javaguides.MiniSettlement.Controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.javaguides.MiniSettlement.DAO.MerchantDAO;
import net.javaguides.MiniSettlement.DAO.MerchantInterface;
import net.javaguides.MiniSettlement.Models.Merchant;
import net.javaguides.MiniSettlement.Models.Transaction;

@WebServlet("/merchant")
public class MerchantController extends HttpServlet {

    private MerchantInterface merchantInterface;

    @Override
    public void init() {
        merchantInterface = new MerchantDAO();
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
                    request.getRequestDispatcher("/WEB-INF/views/merchant.jsp").forward(request, response);
                    break;

                case "getById":
                    String getById = request.getParameter("id");
                    int getMerhantId = Integer.parseInt(getById);
                    Merchant getMerchant = merchantInterface.getById(getMerhantId);
                    request.setAttribute("merchant", getMerchant);
                    request.getRequestDispatcher("/WEB-INF/views/merchant.jsp").forward(request, response);
                    break;

                case "edit":    
                    System.out.println("Edit action - getting ID");

                    String idParam = request.getParameter("id");
                    int id = Integer.parseInt(idParam);

                    Merchant merchant = merchantInterface.getById(id);

                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");
                    String json
                            = "{"
                            + "\"id\":" + merchant.getMerchant_id()+ ","
                            + "\"name\":\"" + merchant.getName() + "\","
                            + "\"email\":\"" + merchant.getEmail() + "\","
                            + "\"phone\":\"" + merchant.getPhone() + "\","
                            + "\"status\":\"" + merchant.getStatus() + "\""
                            + "}";

                    response.getWriter().write(json);
                    break;

                case "delete":
                    int deleteId = Integer.parseInt(request.getParameter("id"));
                    merchantInterface.deleteMerchant(deleteId);
                    response.setContentType("text/plain");
                    response.getWriter().write("success");

                    break;

                case "viewTransactions":
                    System.out.println("Modal Transaction View");

                    int merchantId = Integer.parseInt(request.getParameter("id"));

                    List<Transaction> txList = merchantInterface.getTransactionsByMerchantId(merchantId);

                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");

                    StringBuilder jsons = new StringBuilder("[");

                    for (int i = 0; i < txList.size(); i++) {
                        Transaction t = txList.get(i);

                        jsons.append("{")
                                .append("\"transactionId\":").append(t.getTransactionId()).append(",")
                                .append("\"amount\":").append(t.getAmount()).append(",")
                                .append("\"status\":\"").append(t.getStatus()).append("\",")
                                .append("\"settlementStatus\":\"").append(t.getSettlementStatus()).append("\",")
                                .append("\"merchantName\":\"").append(t.getMerchantName()).append("\",")
                                .append("\"TotalAmount\":\"").append(t.getTotalAmount()).append("\",")
                                .append("\"createdAt\":\"").append(t.getCreatedAt()).append("\"")
                                .append("}");

                        if (i < txList.size() - 1) {
                            jsons.append(",");
                        }
                    }

                    jsons.append("]");

                    response.getWriter().write(jsons.toString());
                    break;

                default:
                    System.out.println("List action - getting all merchants");
                    List<Merchant> list = merchantInterface.getAllMerchants();
                    // pass the role to JSP so it can show/hide buttons
                    HttpSession session = request.getSession(false);
                    request.setAttribute("role", session.getAttribute("role"));
                    request.setAttribute("merchantList", list);
                    request.getRequestDispatcher("/WEB-INF/views/merchant.jsp").forward(request, response);
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

            String name = request.getParameter("name");
            String phone = request.getParameter("phone");
            String status = request.getParameter("status");
            String email = request.getParameter("email");

            System.out.println("Name: " + name);
            System.out.println("Phone: " + phone);
            System.out.println("Email: " + email);
            System.out.println("status: " + status);

            // FIX 1: use "id" not "merchant_id"
            String idParam = request.getParameter("id");
            System.out.println("ID param: " + idParam);
            String hour = request.getParameter("confirmCutoff");
            LocalDateTime time = LocalDateTime.parse(hour);
            System.out.println("settle time:" + time);
            
            int id = 0;
            if (idParam != null && !idParam.isEmpty()) {
                id = Integer.parseInt(idParam);
            }

            Merchant merchant = new Merchant();
            merchant.setName(name);
            merchant.setEmail(email);
            merchant.setPhone(phone);
            merchant.setStatus(status);
            HttpSession session = request.getSession(false);
            request.setAttribute("role", session.getAttribute("role"));
            if (id == 0) {
                System.out.println("Inserting new merchant");
                merchantInterface.insertMerchant(merchant);
            } else {
                System.out.println("Updating merchant with id: " + id);
                merchant.setMerchant_id(id);
                merchantInterface.updateMerchant(merchant);
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
