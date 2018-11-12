package shiyan.sy9;				//版权所有，违反必究

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;


public class LogDao {
	private String driver = "org.mariadb.jdbc.Driver";
	private String url;
	private String user;
	private String pwd;
	private Connection c;
	
	public LogDao(String url,String user,String pwd) {
		this.url = url;
		this.user = user;
		this.pwd = pwd;
	}
	public Connection getConn() {
		return c;
	}
	
	public void ConnectDB() {
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url, user, pwd);
		} catch (SQLException e) {
			System.out.println("No Database Created!");
		}
		this.c = conn;
	}
	
	public Boolean IsTableExits() {
		Boolean result = false;
		
		String sql = "select * from filesyslog;";
		PreparedStatement pstmt1;
		try {
			pstmt1 = c.prepareStatement(sql);
			ResultSet rs = pstmt1.executeQuery();
			if(!rs.wasNull()) {
				result = true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
		}
		if(!result) {
			String createTableSql = "CREATE table filesyslog ("
					+ " id int(5) not null auto_increment,"
					+ " hostname varchar(50) not null,"
					+ " operation varchar(100) not null,"
					+ " data varchar(50) not null,"
					+ " PRIMARY KEY (id))";
			
			PreparedStatement pstmt2;
			try {
				pstmt2 = c.prepareStatement(createTableSql);
				pstmt2.executeQuery();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		}
		return result;
	}
	
	public void InsertData(String log, String hostname) {
		Date data = new Date();
		String insertDataSql = "insert into filesyslog "
				+ " values(null,?,?,?)";
		try {
			PreparedStatement pstmt3 = c.prepareStatement(insertDataSql);
			pstmt3.setString(1, hostname);
			pstmt3.setString(2, log);
			pstmt3.setString(3, data.toString());
			pstmt3.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
}
