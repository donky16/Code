package player.dao;

import java.util.List;

import player.client.clientdownload.DownloadMusic;
import player.client.httpclient.Music;
import player.client.httpclient.UpdateDatabase;
import player.play.MP3Player;

public class DaoTest {
	public static void main(String[] args) {
		UpdateDatabase ud = new UpdateDatabase();
		try {
			ud.updateMusicAndMusicSheet();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
