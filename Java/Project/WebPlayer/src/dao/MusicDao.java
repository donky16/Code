package dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;

import player.client.httpclient.Music;
import player.client.httpclient.MusicSheet;

public class MusicDao {
	/**
	 * 提供与music表连接的Dao
	 */
	private Connection c;

	public MusicDao() {
		ConnectDB cdb = new ConnectDB();
		this.c = cdb.Connect();
	}

	public List<String> findAllMusicMd5Value() {
		/**
		 * ＠return 当前数据库所有音乐的md5值列表 用于在写入数据路音乐信息时，检查是否本地已经存在此音乐
		 */
		List<String> md5ValueList = new ArrayList<String>();
		String sql = "select md5Value from music";
		PreparedStatement ptst;
		try {
			ptst = c.prepareStatement(sql);
			ResultSet rs = ptst.executeQuery();
			while (rs.next()) {
				md5ValueList.add(rs.getString("md5Value"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return md5ValueList;
	}

	public void InsertLocalMusicData(String foderPath) {
		/**
		 * 插入本地文件路径下的所有.mp3文件信息到数据库 可用于添加本地音乐,更新本地音乐到数据库
		 * 
		 * @para 音乐所在路径
		 * 
		 */
		List<Music> musicList = new ArrayList<Music>();

		File file = new File(foderPath);
		File[] files = file.listFiles();
		FileInputStream fis = null;
		String fileMd5 = null;
		for (int i = 0; i < files.length; i++) {
			File f = files[i];
			if (!f.isDirectory() && f.getName().endsWith(".mp3")) {
				String filePath = f.getPath();
				try {
					fis = new FileInputStream(filePath);
					fileMd5 = DigestUtils.md5Hex(IOUtils.toByteArray(fis));
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					IOUtils.closeQuietly(fis);
				}
				/**
				 * 判断是否数据库里已经存在相同信息 判断方法是计算md5值 用于更新数据库音乐信息
				 */
				Boolean isSameMd5 = false;
				for (String md5 : this.findAllMusicMd5Value()) {
					if (md5.equals(fileMd5)) {
						isSameMd5 = true;
					}
				}
				if (!isSameMd5) {
					Music music = new Music();
					music.setMd5value(fileMd5);
					music.setName(f.getName().split(".mp3")[0]);
					musicList.add(music);
				}
			}
		}

		String sql = "insert into music values(null,?,?)";
		for (int i = 0; i < musicList.size(); i++) {
			try {
				PreparedStatement ptst = c.prepareStatement(sql);
				ptst.setString(1, musicList.get(i).getName());
				ptst.setString(2, musicList.get(i).getMd5Value());
				ptst.execute();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public List<Music> selectMusicByMusicSheet(String musicSheetName, String musicSheetCreator) {
		/**
		 * 通过歌单名进行查询所有歌单中的音乐
		 * 
		 * @para 歌单名
		 * @return 音乐类的列表
		 */
		List<Music> musicList = new ArrayList<Music>();

		String sql = "select * from musicplayer.music where find_in_set(id, "
				+ "(select musicId from musicplayer.musicsheet where name = ? and creator = ?))";
		try {
			PreparedStatement psts = c.prepareStatement(sql);
			psts.setString(1, musicSheetName);
			psts.setString(2, musicSheetCreator);
			ResultSet rs = psts.executeQuery();
			while (rs.next()) {
				Music music = new Music();
				music.setName(rs.getString("name"));
				music.setMd5value(rs.getString("md5Value"));
				musicList.add(music);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return musicList;
	}

	public int findMusicIdByMd5Value(String md5Value) {
		/**
		 * 通过音乐md5值查询音乐在数据库中储存的id 从而实现将下载的音乐添加进入某一歌单
		 * 
		 * @para 音乐md5值
		 * @return 音乐id
		 */
		int id = 0;
		String sql = "select id from music where md5Value = ?";
		try {
			PreparedStatement psts = c.prepareStatement(sql);
			psts.setString(1, md5Value);
			ResultSet rs = psts.executeQuery();
			while (rs.next()) {
				id = rs.getInt("id");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return id;
	}

	public void InsertServerMusicData(List<MusicSheet> msList) {
		/**
		 * 下载音乐信息，并插入到数据库 直接把歌单中的音乐信息插入
		 * 
		 * @para 音乐歌单类的列表
		 */
		String sql = "insert into music values(null,?,?)";
		PreparedStatement psts;
		for (int j = 0; j < msList.size(); j++) {
			Map<String, String> musicItems = msList.get(j).getMusicItems();
			Iterator<Entry<String, String>> iter = musicItems.entrySet().iterator();
			while (iter.hasNext()) {
				@SuppressWarnings("rawtypes")
				Map.Entry entry = (Map.Entry) iter.next();
				Boolean isSameMd5 = false;
				for (String md5 : this.findAllMusicMd5Value()) {
					if (md5.equals(entry.getKey().toString())) {
						isSameMd5 = true;
					}
				}
				if (!isSameMd5) {
					try {
						psts = c.prepareStatement(sql);
						psts.setString(1, entry.getValue().toString());
						psts.setString(2, entry.getKey().toString());
						psts.execute();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
	}

	public String findMusicMd5ByName(String name) {
		String md5Value = null;
		String sql = "select md5Value from music where name = ?";
		try {
			PreparedStatement psts = c.prepareStatement(sql);
			psts.setString(1, name);
			ResultSet rs = psts.executeQuery();
			while (rs.next()) {
				md5Value = rs.getString(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return md5Value;
	}
}
// public List<String> findAllMusicName(String foderPath) {
// List<String> musicNameList = new ArrayList<String>();
//
// String sql = "select name from music";
// PreparedStatement psts;
// try {
// psts = c.prepareStatement(sql);
// ResultSet rs = psts.executeQuery();
// while (rs.next()) {
// musicNameList.add(rs.getString("name"));
// }
// } catch (SQLException e) {
// e.printStackTrace();
// }
// return musicNameList;
// }
