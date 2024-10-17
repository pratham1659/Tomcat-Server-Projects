package registration;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/register")
public class RegistrationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//			PrintWriter out = response.getWriter();
//			out.write("working");
		
		String name = request.getParameter("name");
		String username = request.getParameter("username");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String contact = request.getParameter("contact");
		
//		PrintWriter out = response.getWriter();
//		out.print(name);
//		out.print(username);
//		out.print(email);
//		out.print(password);
//		out.print(contact);
		
		RequestDispatcher dispatcher =  null;
		Connection con = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ServletGetPost", "root", "root@1234");
			
			PreparedStatement pst = con.prepareStatement("insert into user(name, username, email, password, contact) values(?, ?, ?, ?, ?)");
			
			pst.setString(1, name);
			pst.setString(2, username);
			pst.setString(3, email);
			pst.setString(4, password);
			pst.setString(5, contact);
			
			
			
			int rowCount = pst.executeUpdate();
			dispatcher = request.getRequestDispatcher("registration.jsp");
			
			if(rowCount > 0){
				request.setAttribute("status", "success");
			}else {
				request.setAttribute("status", "failed"); 
				
			}
			dispatcher.forward(request, response);
			
		} catch (Exception  e) {
			e.printStackTrace();
		}finally {
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
}
