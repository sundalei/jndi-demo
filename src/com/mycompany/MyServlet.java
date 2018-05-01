package com.mycompany;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 * Servlet implementation class MyServlet
 */
@WebServlet("/MyServlet")
public class MyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			Context initCtx = new InitialContext();
			Context envCtx = (Context) initCtx.lookup("java:comp/env");
			MyBean bean = (MyBean) envCtx.lookup("bean/MyBeanFactory");
			MyBean2 bean2 = (MyBean2) envCtx.lookup("bean/MyBeanFactory2");
			DataSource ds = (DataSource) envCtx.lookup("jdbc/EmployeeDB");
			
			PrintWriter writer = response.getWriter();
			response.setContentType("text/html");
			writer.println("foo = " + bean.getFoo() + ", bar = " + bean.getBar());
			writer.print("<br>");
			writer.println("local = " + bean2.getLocal().getHostAddress() + 
					", remote = " + bean2.getRemote().getHostAddress());
			writer.println("<br>");
			writer.println(getInfo(ds));
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	private String getInfo(DataSource ds) {
		Connection con = null;
		Statement stat = null;
		StringBuilder sb = new StringBuilder();
		
		try {
			con = ds.getConnection();
			stat = con.createStatement();
			String sql = "select * from Course";
			ResultSet rs = stat.executeQuery(sql);
			while(rs.next()) {
				sb.append("id = ").append(rs.getInt("id")).append(", ")
				  .append("name = ").append(rs.getString("name")).append(", ")
				  .append("credits = ").append(rs.getString("credits")).append(", ")
				  .append("<br>");
			}
			return sb.toString();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if(stat != null) {
				try {
					stat.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return null;
	}

}
