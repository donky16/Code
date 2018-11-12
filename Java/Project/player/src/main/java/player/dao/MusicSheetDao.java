package player.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.auth.MalformedChallengeException;

import player.client.httpclient.MusicSheet;

public class MusicSheetDao {
	/**
	 * 提供与musicsheet表连接的Dao
	 */
	private Connection c;
	private MusicDao md = new MusicDao();

	public MusicSheetDao() {
		ConnectDB cdb = new ConnectDB();
		c = cdb.Connect();
	}

	public void insertServerMusicSheet(List<MusicSheet> insertMusicSheetList) {
		/**
		 * 先过滤数据库中已存在的歌单，通过创建者,歌单名，歌曲数量（不太准确） 向数据库中存入歌单，用于下载服务器中的其他歌单
		 * 通过musicsheetd的musicItem拼接所有在歌单中的音乐在数据库表music的id
		 */
		List<MusicSheet> msList = new ArrayList<MusicSheet>();
		msList = findMusicSheet(null);
		for (int j = 0; j < insertMusicSheetList.size(); j++) {
			MusicSheet ms = insertMusicSheetList.get(j);
			Boolean isSameMUsicSheet = false;
			for (int i = 0; i < msList.size(); i++) {
				if (msList.get(i).getName().equals(ms.getName()) && msList.get(i).getCreator().equals(ms.getCreator())
						/**&& msList.get(i).getMusicItems().size() == ms.getMusicItems().size()**/) {
					isSameMUsicSheet = true;
				}
			}

			if (!isSameMUsicSheet) {
				int musicId = 0;
				String md5Vlaue = null;
				String musicIds = null;
				StringBuffer sb = new StringBuffer();
				Iterator iter = ms.getMusicItems().entrySet().iterator();
				while (iter.hasNext()) {
					Map.Entry entry = (Map.Entry) iter.next();
					md5Vlaue = (String) entry.getKey();
					musicId = md.findMusicIdByMd5Value(md5Vlaue);
					if (iter.hasNext()) {
						sb.append(musicId + ",");
					} else {
						sb.append(musicId);
					}
				}
				musicIds = sb.toString();

				String sql = "insert into musicsheet values (null,?,?,?,?,?,?,?)";
				PreparedStatement psts;
				try {
					psts = c.prepareStatement(sql);
					psts.setString(1, ms.getUuid());
					psts.setString(2, ms.getName());
					psts.setString(3, ms.getCreatorId());
					psts.setString(4, ms.getCreator());
					psts.setString(5, ms.getDateCreated());
					psts.setString(6, ms.getPicture());
					psts.setString(7, musicIds);
					psts.execute();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	public void insertMusicIntoMusicSheet(String md5Value, String musicSheetName) {
		int musicId = 0;
		String musicIds = null;
		StringBuffer sb = new StringBuffer();
		musicId = md.findMusicIdByMd5Value(md5Value);
		// 找到数据库中的音乐id
		String sql1 = "select musicId from musicsheet where name = ?";
		try {
			PreparedStatement psts1 = c.prepareStatement(sql1);
			psts1.setString(1, musicSheetName);
			ResultSet rs = psts1.executeQuery();
			while (rs.next()) {
				sb.append(rs.getString("musicId"));
				sb.append("," + musicId);
			}
			musicIds = sb.toString();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		// 将音乐id添加到歌单的musicId中
		String sql2 = "update musicsheet set musicId = ? where name = ?";
		try {
			PreparedStatement psts2 = c.prepareStatement(sql2);
			psts2.setString(1, musicIds);
			psts2.setString(2, musicSheetName);
			psts2.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<MusicSheet> findMusicSheet(String creatorId) {
		/**
		 * 通过创建者查询歌单，如果为null,则默认查询所有 如果creator为自己，则查询本地歌单 也可以实现搜索一名同学的所有歌单功能
		 */
		List<MusicSheet> musicSheetList = new ArrayList<MusicSheet>();

		String sql = null;
		if (creatorId == null) {
			sql = "select * from musicsheet";
		} else {
			sql = "select * from musicsheet where creatorId = " + creatorId;
		}
		try {
			PreparedStatement psts = c.prepareStatement(sql);
			ResultSet rs = psts.executeQuery();
			while (rs.next()) {
				MusicSheet ms = new MusicSheet();
				String musicIds = rs.getString("musicId");
				ms.setName(rs.getString("name"));
				ms.setCreator(rs.getString("creator"));
				ms.setCreatorId(rs.getString("creatorId"));
				// ms.setUuid(rs.getString("uuid"));
				ms.setPicture(rs.getString("picture"));
				musicSheetList.add(ms);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return musicSheetList;
	}

	public void addServerMusicSheetLocal(String name, String creator, String localUserName, String creatorId) {
		/**
		 * 将一个歌单直接添加到本地歌单 用于下载所有歌曲到本地，并创建为自己的歌单
		 */
		String sql = "update musicsheet set creator = ?,creatorId = ? where name = ? and creator = ?";
		try {
			PreparedStatement psts = c.prepareStatement(sql);
			psts.setString(1, localUserName);
			psts.setString(2, creatorId);
			psts.setString(3, name);
			psts.setString(4, creator);;
			psts.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public List<MusicSheet> searchMusicSheetByCreatorName(String name) {
		/**
		 * 通过创建者查询歌单，如果为null,则默认查询所有 如果creator为自己，则查询本地歌单 也可以实现搜索一名同学的所有歌单功能
		 */
		List<MusicSheet> musicSheetList = new ArrayList<MusicSheet>();

		String sql = "select * from musicsheet where creator like '%" + name + "%'";
		try {
			PreparedStatement psts = c.prepareStatement(sql);
//			psts.setString(1, name);
			ResultSet rs = psts.executeQuery();
			while (rs.next()) {
				MusicSheet ms = new MusicSheet();
				String musicIds = rs.getString("musicId");
				ms.setName(rs.getString("name"));
				ms.setCreator(rs.getString("creator"));
				ms.setCreatorId(rs.getString("creatorId"));
				// ms.setUuid(rs.getString("uuid"));
				ms.setPicture(rs.getString("picture"));
				musicSheetList.add(ms);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return musicSheetList;
	}
}
