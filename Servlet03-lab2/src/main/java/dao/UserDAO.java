package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import entity.User;
import util.DBUtils;

/**
 * 封装了数据访问逻辑
 * @author Administrator
 *
 */
public class UserDAO {
	/**
	 * 将所有用户信息查询出来
	 * @throws Exception 
	 */
	public List<User> findAll() throws Exception{
		List<User> users = new ArrayList<User>();
		Connection conn = null;
		PreparedStatement ps = null ;
		ResultSet rs = null ;
		try {
			conn = DBUtils.getConn();
			ps = conn.prepareStatement("select * from t_user");
			rs = ps.executeQuery();
			while(rs.next()) {
				User user = new User();
				user.setId(rs.getInt("id"));
				user.setUname(rs.getString("username"));
				user.setPwd(rs.getString("password"));
				user.setEmail(rs.getString("email"));
				users.add(user);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e ;
		}finally {
			DBUtils.close(rs, ps, conn);
		}
		
		return users;
	}
}
