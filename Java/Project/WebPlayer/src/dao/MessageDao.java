package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class MessageDao {
	/**
	 * 与数据库中message表进行交互的类
	 */
	private Connection conn = null;
	
	public MessageDao() {
		ConnectDB cdb = new ConnectDB();
		this.conn = (Connection) cdb.Connect();
	}
	public Map<String, String> findMessageByMusicSheetNameAndCreator(String name, String creator){
		Map<String, String> userNameAndMessageMap = new HashMap<String, String>();
		//查询歌单id，通过歌单名称和创建者
		int id = 0;
		String sql1 = "select id from musicsheet where name = ? and creator = ?;";
		PreparedStatement psts1;
		try {
			psts1 = conn.prepareStatement(sql1);
			psts1.setString(1, name);
			psts1.setString(2, creator);
			ResultSet rs = psts1.executeQuery();

			while(rs.next()) {
				id = rs.getInt("id");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(id != 0) {
			//如果歌单id查找到，就 查找所有此歌单的评论，并对应其留言者
			String sql2 = "select username,msg from user u,message m "
					+ " where u.id = m.user and m.musicsheet = ?";
			try {
				PreparedStatement psts2 = conn.prepareStatement(sql2);
				psts2.setInt(1, id);
				ResultSet rs2 = psts2.executeQuery();
				
				while(rs2.next()) {
					userNameAndMessageMap.put(rs2.getString("username"), rs2.getString("msg"));
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return userNameAndMessageMap;
		}
		else {
			return null;
		}
	}

}
