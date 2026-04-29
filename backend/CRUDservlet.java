import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import java.sql.*;

@WebServlet({"/CrudServlet", "/read"})
public class CRUDservlet extends HttpServlet {

    String url = "jdbc:mysql://localhost:3306/Project";
    String user = "root";
    String password = "root";

    // CREATE, UPDATE, DELETE
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(url, user, password);

            String id = request.getParameter("id");
            String name = request.getParameter("name");
            String email = request.getParameter("email");
            String age = request.getParameter("age");
            String department = request.getParameter("department");

            PrintWriter out = response.getWriter();

            // ✅ CREATE
            if ("create".equals(action)) {
                PreparedStatement ps = con.prepareStatement(
                        "INSERT INTO details(name,email,age,department) VALUES(?,?,?,?)");

                ps.setString(1, name);
                ps.setString(2, email);
                ps.setInt(3, Integer.parseInt(age));
                ps.setString(4, department);

                ps.executeUpdate();
                out.println("<h3>Record Inserted Successfully</h3>");
            }
            
            else if ("read".equals(action)) {

                PreparedStatement ps = con.prepareStatement(
                    "SELECT * FROM details"
                );

                ResultSet rs = ps.executeQuery();

                out.println("<h2>Records:</h2>");
                out.println("<table border='1'>");
                out.println("<tr><th>ID</th><th>Name</th><th>Email</th><th>Age</th><th>Department</th></tr>");

                while (rs.next()) {
                    out.println("<tr>");
                    out.println("<td>" + rs.getInt("id") + "</td>");
                    out.println("<td>" + rs.getString("name") + "</td>");
                    out.println("<td>" + rs.getString("email") + "</td>");
                    out.println("<td>" + rs.getInt("age") + "</td>");
                    out.println("<td>" + rs.getString("department") + "</td>");
                    out.println("</tr>");
                }

                out.println("</table>");
            }

            // ✅ UPDATE (FULLY FIXED)
            else if ("update".equals(action)) {

                if (id == null || id.isEmpty()) {
                    out.println("<h3>ID is required for update!</h3>");
                    return;
                }

                PreparedStatement ps = con.prepareStatement(
                        "UPDATE details SET name=?, email=?, age=?, department=? WHERE id=?");

                ps.setString(1, name);
                ps.setString(2, email);
                ps.setInt(3, Integer.parseInt(age));
                ps.setString(4, department);
                ps.setInt(5, Integer.parseInt(id));

                int rows = ps.executeUpdate();

                if (rows > 0) {
                    out.println("<h3>Record Updated Successfully</h3>");
                } else {
                    out.println("<h3>No record found with given ID</h3>");
                }
            }

            // ✅ DELETE
            else if ("delete".equals(action)) {

                PreparedStatement ps = con.prepareStatement(
                        "DELETE FROM details WHERE id=?");

                ps.setInt(1, Integer.parseInt(id));

                int rows = ps.executeUpdate();

                if (rows > 0) {
                    out.println("<h3>Record Deleted Successfully</h3>");
                } else {
                    out.println("<h3>No record found with given ID</h3>");
                }
            }

            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ✅ READ (GET METHOD)
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(url, user, password);

            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM details");

            PrintWriter out = response.getWriter();

            out.println("<h2>All Records</h2>");
            out.println("<table border='1' cellpadding='10'>");

            out.println("<tr>");
            out.println("<th>ID</th>");
            out.println("<th>Name</th>");
            out.println("<th>Email</th>");
            out.println("<th>Age</th>");
            out.println("<th>Department</th>");
            out.println("</tr>");

            while (rs.next()) {
                out.println("<tr>");
                out.println("<td>" + rs.getInt("id") + "</td>");
                out.println("<td>" + rs.getString("name") + "</td>");
                out.println("<td>" + rs.getString("email") + "</td>");
                out.println("<td>" + rs.getInt("age") + "</td>");
                out.println("<td>" + rs.getString("department") + "</td>");
                out.println("</tr>");
            }

            out.println("</table>");

            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}