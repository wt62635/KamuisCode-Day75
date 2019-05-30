package web;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.DBUtils;
/**
 * 查询所有用户信息数据处理
 * @author Administrator
 *
 */
public class ListUserServlet extends HttpServlet {
	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();

		// 将所有用户信息查询出来
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = DBUtils.getConn();
			String sql = "select * from t_user ;";
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			// 依据查询到的用户信息，输出表格
			out.println("<table width='60%' border='1' cellpadding='0'>");
			out.println("<tr>" 
					+ "<td>ID</td>" 
					+ "<td>用户名</td>" 
					+ "<td>密码</td>" 
					+ "<td>邮箱</td>" 
					+ "<td>操作</td>" 
					+"</tr>");
			while (rs.next()) {
				int id = rs.getInt("id");
				String username = rs.getString("username");
				String password = rs.getString("password");
				String email = rs.getString("email");
				out.println("<tr>"
						+ "<td>" + id + "</td>"
						+ "<td>" + username + "</td>"
						+ "<td>" + password + "</td>"
						+ "<td>" + email + "</td>"
						+ "<td><a href='del?id=" + id + "'>删除"+ "</td>"
						+ "</tr>");
			}
			out.println("</table>");
			out.println("<p><a href='addUser.html'>添加用户</a></p>");
		} catch (Exception e) {
			e.printStackTrace();
			out.println("系统繁忙，稍后重试。");
		} finally {
			DBUtils.close(rs, ps, conn);
		}
	}
}
