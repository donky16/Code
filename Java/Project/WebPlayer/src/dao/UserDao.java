package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import sun.print.BackgroundLookupListener;

public class UserDao {
	/**
	 * 用于用户权限的管理，添加用户或者，查询某一用户的密码
	 */
	private Connection conn = null;
	public UserDao() {
		ConnectDB cdb = new ConnectDB();
		this.conn = (Connection) cdb.Connect();
	}
	
	public void insertUserInfo(String name, String pwd) {
		//插入一条用户数据
		String sql = "insert into user values(null,?,?)";
		PreparedStatement psts = null;
		try {
			psts = conn.prepareStatement(sql);
			psts.setString(1, name);
			psts.setString(2, pwd);
			psts.execute();
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public Boolean findPasswordAndUserName(String name, String pwd) {
		//通过用户名找到该用户的密码
		String sql = "select username,password from user where username = ? and password = ?";
		PreparedStatement psts = null;
		try {
			psts = conn.prepareStatement(sql);
			psts.setString(1, name);
			psts.setString(2, pwd);
			ResultSet rs = psts.executeQuery();
			while(rs.next()) {
				return true;
			}
			
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return false;
	}
}
