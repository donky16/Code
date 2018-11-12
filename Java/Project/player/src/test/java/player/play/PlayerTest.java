package player.play;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

public class PlayerTest {
	private Player player;

	public PlayerTest() {

	}

	public void run() {
		// TODO Auto-generated method stub
		Thread thread = new Thread() {
			public void run() {
				BufferedInputStream buffer = null;

				try {
					buffer = new BufferedInputStream(new FileInputStream("/root/CloudMusic/80000.mp3"));
					player = new Player(buffer);
					player.play();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		};
		thread.start();
	}

	public int where() {
		return player.getPosition();
	}
}
