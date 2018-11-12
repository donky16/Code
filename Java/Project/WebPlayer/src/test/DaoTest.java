package test;

import java.util.Map;

import dao.MessageDao;
import dao.UserDao;

public class DaoTest {
	public static void main(String[] args) {
		UserDao ud = new UserDao();
//		ud.insertUserInfo("test", "test");
		System.out.println( ud.findPasswordAndUserName("test","test"));
		
		MessageDao md = new MessageDao();
		Map<String, String> map = md.findMessageByMusicSheetNameAndCreator("永远的Beyond", "Wang Xiaodong");
		System.out.println(map.size());
	}
}
