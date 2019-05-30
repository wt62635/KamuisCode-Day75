package util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.apache.commons.dbcp.BasicDataSource;

import web.AddUserServlet;

/**
 * JDBC工具类
 * @author Administrator
 *
 */
public class DBUtils {
	/*
	 * getConn()连接数据库，返回Connection对象conn;
	 */
	private static BasicDataSource ds ;
	static {
		// 创建数据源对象
		ds = new BasicDataSource();
		// 读取配置文件
		Properties p = new Properties();
		InputStream ips = AddUserServlet.class.getClassLoader().getResourceAsStream("jdbc.properties");
		try {
			p.load(ips);
			String driver = p.getProperty("driver");
			String url = p.getProperty("url");
			String username = p.getProperty("username");
			String password = p.getProperty("password");
			// 设置连接信息
			ds.setDriverClassName(driver);
			ds.setUrl(url);
			ds.setUsername(username);
			ds.setPassword(password);

			ds.setInitialSize(3);// 设置初始链接数量
			ds.setMaxActive(5);// 设置最大连接数量
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static Connection getConn() throws Exception {
		// 获取连接对象 注意导错包 异常抛出
		Connection conn = ds.getConnection();
		System.out.println(conn);

		return conn;
	}
	
	/**
	 * 按照JDBC规范，连接关闭的时候对应的Statment以及ResultSet应该自动关闭。
	 * 但是，有一些连接池的实现没有完全符合规范，导致关闭连接时，对应的Statment及
	 * ResultSet并没有关闭。所以建议在关闭连接时，最好手动关闭Statment及
	 * ResultSet，避免资源的浪费。
	 */
	public static void close(ResultSet rs, Statement stat,Connection conn) {
		if(rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if(stat != null) {
			try {
				stat.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if(conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
