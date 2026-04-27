package net.javaguides.MiniSettlement.Controller;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.javaguides.MiniSettlement.DAO.SettlementDAO;
import net.javaguides.MiniSettlement.DAO.SettlementInterface;
import net.javaguides.MiniSettlement.Models.Settlement;

@WebServlet("/settlement")
public class SettlementController extends HttpServlet {

    private SettlementInterface setIn;

    @Override
    public void init() {
        setIn = new SettlementDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
                    request.getRequestDispatcher("/WEB-INF/views/settlement.jsp").forward(request, response);
                    break;

                case "getById":
                    String getById = request.getParameter("id");
                    int getSetId = Integer.parseInt(getById);
                    Settlement getSet = setIn.getById(getSetId);
                    request.setAttribute("settlement", getSet);
                    request.getRequestDispatcher("/WEB-INF/views/settlement.jsp").forward(request, response);
                    break;

                case "edit":
                    System.out.println("Edit action - getting ID");

                    String idParam = request.getParameter("id");
                    int id = Integer.parseInt(idParam);

                    Settlement set = setIn.getById(id);

                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");

                    String json
                            = "{"
                            + "\"id\":" + set.getSettlementId() + ","
                            + "\"merchantId\":\"" + set.getMerchantId() + "\","
                            + "\"totalAmount\":\"" + set.getTotalAmount() + "\","
                            + "\"fee\":\"" + set.getFee() + "\","
                            + "\"netAmount\":\"" + set.getNetAmount() + "\""
                            + "}";

                    response.getWriter().write(json);
                    break;

                case "delete":
                    int deleteId = Integer.parseInt(request.getParameter("id"));
                    setIn.deleteSettlement(deleteId);
                    response.setContentType("text/plain");
                    response.getWriter().write("success");

                    break;

                default:
                    System.out.println("List action - getting all settlement");
                    List<Settlement> list = setIn.getAllSettlement();
                    request.setAttribute("settlementList", list);
                    request.getRequestDispatcher("/WEB-INF/views/settlement.jsp").forward(request, response);
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
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
            try {
                System.out.println("doPost settlement Start");
                int merchantId = Integer.parseInt(req.getParameter("id"));

                float total = setIn.getPendingByMerchant(merchantId);

                float fee = total * 0.02f;
                float net = total - fee;

                Settlement s = new Settlement();
                s.setMerchantId(merchantId);
                s.setTotalAmount(total);
                s.setFee(fee);
                s.setNetAmount(net);
                System.out.println("Merchant id: " + s.getMerchantId());
                System.out.println("Total Amount: " + s.getTotalAmount());
                System.out.println("Fee: " + s.getFee());
                System.out.println("Net Amount: " + s.getNetAmount());

                setIn.createSettlement(s);

                setIn.markAsSettled(merchantId);

                resp.setContentType("text/plain");
                resp.getWriter().write("settled");

                System.out.println("doPost End");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
    }
}
