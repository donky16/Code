package player.client.clientupload;

import java.util.ArrayList;
import java.util.List;

import player.client.httpclient.Music;
import player.client.httpclient.MusicSheet;
import player.client.httpclient.MusicSheetAndFilesUploader;
import player.dao.MusicDao;
import player.dao.MusicSheetDao;

public class Uploader {
	
	public static void main(String[] args) {
		MusicSheetDao msd = new MusicSheetDao();
		MusicDao md = new MusicDao();
		
		String url = "http://service.uspacex.com/music.server/upload";
				
		md.InsertLocalMusicData("/root/CloudMusic/mengqing");
		List<MusicSheet> msList = msd.findMusicSheet("16020032027");
		List<Music> mList = new ArrayList<Music>();
		for(int i = 0; i < msList.size(); i ++) {
			MusicSheet ms = msList.get(i);
			mList = md.selectMusicByMusicSheet(ms.getName(), ms.getCreator());
			List<String> filePaths = new ArrayList<String>();
			for(int j = 0; j < mList.size(); j ++) {
				filePaths.add("/root/CloudMusic/mengqing/" + (String) mList.get(j).getName() + ".mp3");
			}
			ms.setPicture("/root/CloudMusic/mengqing/1.jpg");
			MusicSheetAndFilesUploader.createMusicSheetAndUploadFiles(url, ms, filePaths);
		}
	}
}
