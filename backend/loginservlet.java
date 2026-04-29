import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import java.sql.*;

import com.project.dao.DBConnection;

@WebServlet("/login")
public class loginservlet extends HttpServlet {

    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {

        String username = req.getParameter("username");
        String password = req.getParameter("password");

        try {
            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(
                "SELECT * FROM users WHERE username=? AND password=?"
            );

            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
            	res.sendRedirect("main.html");
            } else {
                res.getWriter().println("Invalid Credentials ❌");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}