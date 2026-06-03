package net.javaguides.MiniSettlement.Controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.javaguides.MiniSettlement.DAO.UserDAO;
import net.javaguides.MiniSettlement.Models.User;

@WebServlet("/login")
public class LoginController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();

        try {
            String username = request.getParameter("username");
            String password = request.getParameter("password");

            UserDAO dao = new UserDAO();

            User user = dao.login(username, password);

            if (user != null) {
                LocalDateTime expiryTime = user.getUpdated_at().plusDays(30);
                if (user.getFirstLog() == 1) {

                    HttpSession session2 = request.getSession(true);
                    session2.setAttribute("user", user.getUsername());
                    out.print("{\"status\":\"update\",\"message\":\"Fisrt Login Please Change password!\"}");

                } else if (LocalDateTime.now().isAfter(expiryTime)) {

                    HttpSession session2 = request.getSession(true);
                    session2.setAttribute("user", user.getUsername());
                    out.print("{\"status\":\"update\",\"message\":\"Password Expired Please Change password!\"}");

                } else {

                    HttpSession session = request.getSession(true);
                    session.setAttribute("user", user.getUsername());
                    session.setAttribute("role", user.getRole());
                    session.setAttribute("firstLog", user.getFirstLog());
                    out.print("{\"status\":\"success\"}");

                }
            } else {
                out.print("{\"status\":\"error\",\"message\":\"Invalid credentials\"}");
            }

        } catch (Exception e) {
            e.printStackTrace();

            out.print("{\"status\":\"error\",\"message\":\"Server error\"}");
        }

    }

}
