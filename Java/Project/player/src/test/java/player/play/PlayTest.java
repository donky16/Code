package player.play;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

public class PlayTest {
	private static PlayerTest playerTest;

	public static void main(String[] args) {
//		BufferedInputStream buffer = null;
//		Player player = null;
//		try {
//			buffer = new BufferedInputStream(new FileInputStream("/root/CloudMusic/80000.mp3"));
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		try {
//			player = new Player(buffer);
//		} catch (JavaLayerException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		playerTest = new PlayerTest();
		playerTest.run();

	
	
				for(int i = 0; i < 1000000000; i ++) {
					System.out.println(playerTest.where());
					
				}
		
	}

}
