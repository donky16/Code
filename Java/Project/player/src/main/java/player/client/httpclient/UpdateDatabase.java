package player.client.httpclient;

import java.util.ArrayList;
import java.util.List;

import player.client.clientdownload.DownloadMusicSheet;
import player.client.httpclient.MusicSheet;
import player.dao.MusicDao;
import player.dao.MusicSheetDao;

public class UpdateDatabase {
	private DownloadMusicSheet dms = new DownloadMusicSheet();
	private MusicSheetDao msd = new MusicSheetDao();
	private MusicDao md = new MusicDao();
	private String jsonStr = null;
	private List<MusicSheet> msList = new ArrayList<MusicSheet>();

	public void updateMusicAndMusicSheet() throws Exception{
		// 获取歌单列表json数据
		jsonStr = dms.getJsonString();
		msList = dms.jsonToMusicSheetList(jsonStr); // 将json数据处理为musicSheet类的列表
		md.InsertServerMusicData(msList); // 将歌单中的音乐信息，先插入数据库
		msd.insertServerMusicSheet(msList); // 再将歌单信息插入数据库，从而能获得歌单中音乐在数据库中的信息
	}
}
