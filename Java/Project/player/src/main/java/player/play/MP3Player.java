package player.play;


import java.io.BufferedInputStream;
import java.io.FileInputStream;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

public class MP3Player {
	private String filename;
	private Player player;
//	private Thread thread;

	public MP3Player(String filename) {
		this.filename = filename;
	}


	public void run() {
		// TODO Auto-generated method stub
		BufferedInputStream buffer = null;
		Player player = null;
		try {
			buffer = new BufferedInputStream(new FileInputStream(filename));
			player = new Player(buffer);
			player.play();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
	}
	public int where() {
		return player.getPosition();
	}
	
}