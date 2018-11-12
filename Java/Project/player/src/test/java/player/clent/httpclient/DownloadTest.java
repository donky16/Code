package player.clent.httpclient;

import java.util.ArrayList;
import java.util.List;

import player.client.clientdownload.DownloadMusic;
import player.client.clientdownload.DownloadMusicSheet;
import player.client.httpclient.MusicSheet;
import player.dao.MusicDao;
import player.dao.MusicSheetDao;

public class DownloadTest {

	public static void main(String[] args) {
//		DownloadMusic dm = new DownloadMusic();
//		try {
//			dm.downloadMusic("1e659b0eefb3e1bb796e93cfe0710a9c");
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		DownloadMusicSheet dms = new DownloadMusicSheet();
		MusicSheetDao msd = new MusicSheetDao();
		MusicDao md = new MusicDao();
		List<MusicSheet> msList = new ArrayList<MusicSheet>();
		String jsonData = null;
		try {
			jsonData = dms.getJsonString();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(jsonData);
		try {
			msList = dms.jsonToMusicSheetList(jsonData);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		md.InsertServerMusicData(msList);
		msd.insertServerMusicSheet(msList);
		
	}

}
