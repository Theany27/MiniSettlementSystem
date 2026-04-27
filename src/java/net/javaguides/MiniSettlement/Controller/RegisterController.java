
package net.javaguides.MiniSettlement.Controller;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.javaguides.MiniSettlement.DAO.UserDAO;

@WebServlet("/register")
public class RegisterController extends HttpServlet{
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(req, resp);
    }
     @Override
     protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        response.setContentType("application/json");

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String role = request.getParameter("role");

         UserDAO dao = new UserDAO();

         PrintWriter out = response.getWriter();

        if (dao.register(username, password,role)) {
            out.print("{\"status\":\"success\",\"message\":\"Registered successfully\"}");
        } else {
            out.print("{\"status\":\"error\",\"message\":\"Username already exists\"}");
        }
    }
}
