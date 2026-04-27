package net.javaguides.MiniSettlement.Controller;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import net.javaguides.MiniSettlement.DAO.UserDAO;
import net.javaguides.MiniSettlement.DAO.UserInterface;
import net.javaguides.MiniSettlement.Models.User;

@WebServlet("/user")
public class UserController extends HttpServlet {

    private UserInterface userDAO;

    @Override
    public void init() {
        userDAO = new UserDAO();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        try {
            System.out.println("=== doGet START ===");

            String action = req.getParameter("action");
            if (action == null) {
                action = "";
            }

            switch (action) {

                // ---------------- NEW PAGE ----------------
                case "new":
                    req.getRequestDispatcher("/WEB-INF/views/user.jsp")
                            .forward(req, res);
                    break;

                // ---------------- GET BY ID (JSON) ----------------
                case "getById":
                    int getId = Integer.parseInt(req.getParameter("id"));
                    User getUser = userDAO.getUserById(getId);

                    res.setContentType("application/json");
                    res.setCharacterEncoding("UTF-8");

                    String jsonGet
                            = "{"
                            + "\"id\":" + getUser.getId() + ","
                            + "\"username\":\"" + getUser.getUsername() + "\","
                            + "\"password\":\"" + getUser.getPassword() + "\","
                            + "\"role\":\"" + getUser.getRole() + "\""
                            + "}";

                    res.getWriter().write(jsonGet);
                    break;

                // ---------------- EDIT (JSON) ----------------
                case "edit":
                    int id = Integer.parseInt(req.getParameter("id"));
                    User user = userDAO.getUserById(id);

                    res.setContentType("application/json");
                    res.setCharacterEncoding("UTF-8");

                    String jsonEdit
                            = "{"
                            + "\"id\":" + user.getId() + ","
                            + "\"username\":\"" + user.getUsername() + "\","
                            + "\"password\":\"" + user.getPassword() + "\","
                            + "\"role\":\"" + user.getRole() + "\""
                            + "}";

                    res.getWriter().write(jsonEdit);
                    break;

                // ---------------- DELETE ----------------
                case "delete":
                    int deleteId = Integer.parseInt(req.getParameter("id"));
                    userDAO.deleteUser(deleteId);

                    res.setContentType("text/plain");
                    res.getWriter().write("success");
                    break;

                // ---------------- LIST USERS ----------------
                default:
                    List<User> list = userDAO.getAllUser();

                    HttpSession session = req.getSession(false);
                    if (session != null) {
                        req.setAttribute("role", session.getAttribute("role"));
                    }

                    req.setAttribute("user", list);

                    req.getRequestDispatcher("/WEB-INF/views/user.jsp")
                            .forward(req, res);
                    break;
            }

            System.out.println("=== doGet END ===");

        } catch (Exception e) {
            e.printStackTrace();
            res.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Error: " + e.getMessage());
        }
    }

    // ---------------- OPTIONAL: POST ----------------
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String role = req.getParameter("role");

        String idParam = req.getParameter("id");
                    System.out.println("ID param: " + idParam);

        int id = 0;

        if (idParam != null && !idParam.isEmpty()) {
            id = Integer.parseInt(idParam);
        }

        UserDAO dao = new UserDAO();
        boolean result;
        System.out.println("id update:" + id);
        if (id == 0) {
            // INSERT
            result = dao.register(username, password, role);
        } else {
            // UPDATE
            result = dao.updateUser(id, username, password, role);
        }

        res.setContentType("application/json");

        if (result) {
            res.getWriter().write("{\"status\":\"success\"}");
        } else {
            res.getWriter().write("{\"status\":\"error\"}");
        }
    }
}
