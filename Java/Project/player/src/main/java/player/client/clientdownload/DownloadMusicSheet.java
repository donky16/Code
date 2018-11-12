package player.client.clientdownload;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import player.client.httpclient.MusicSheet;

public class DownloadMusicSheet {
	public String getJsonString() throws Exception {
		/**
		 * 获取指定url中的Json数据，并转化为String
		 */

		URL url = new URL("http://service.uspacex.com/music.server/queryMusicSheets?type=all");
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.connect();
		InputStream inputStream = connection.getInputStream();
		Reader reader = new InputStreamReader(inputStream, "UTF-8");
		BufferedReader bufferedReader = new BufferedReader(reader);
		String str = null;
		StringBuffer sb = new StringBuffer();
		while ((str = bufferedReader.readLine()) != null) {
			sb.append(str);
		}
		reader.close();
		connection.disconnect();
		return sb.toString();
	}

	public List<MusicSheet> jsonToMusicSheetList(String jsonStr) throws Exception {
		/**
		 * 将获得的json数据转化为MsuicSheet类的列表 {}中是一个JSONObject []中是一个JSONArray
		 */
		List<MusicSheet> msList = new ArrayList<MusicSheet>();

		JSONObject jsonObject = new JSONObject(jsonStr);
		JSONArray musicSheetJSONArray = jsonObject.getJSONArray("musicSheetList");
		for (int i = 0; i < musicSheetJSONArray.length(); i++) {
			JSONObject musicSheetJSONObj = musicSheetJSONArray.getJSONObject(i);
			if (musicSheetJSONObj.getJSONObject("musicItems").length() != 0 && musicSheetJSONObj.get("name") != JSONObject.NULL
					&& musicSheetJSONObj.get("creator") != JSONObject.NULL && musicSheetJSONObj.get("creatorId")  != JSONObject.NULL
					&& musicSheetJSONObj.get("uuid") != JSONObject.NULL && musicSheetJSONObj.get("picture") != JSONObject.NULL
					&& musicSheetJSONObj.get("dateCreated") != JSONObject.NULL) {
				// 将歌单列表中音乐数量为0的歌单,与没有名称的过滤
				Map<String, String> musicItemsMap = new HashMap<String, String>();
				JSONObject musicItemsJSONObj = musicSheetJSONObj.getJSONObject("musicItems");
				Iterator<String> iterator = musicItemsJSONObj.keys();
				while (iterator.hasNext()) {
					String md5Value_key = iterator.next();
					musicItemsMap.put(md5Value_key, musicItemsJSONObj.getString(md5Value_key));
				}
				/**
				 * 将服务器中的歌单中，如果有名称，创建者和音乐数量都想同的歌单，默认为相同歌单 防止测试数据插入
				 */
				Boolean isSameMusicSheet = false;
				for (int j = 0; j < msList.size(); j++) {
					if (musicSheetJSONObj.getString("creator").equals(msList.get(j).getCreator())
							&& musicSheetJSONObj.getString("name").equals(msList.get(j).getName())) {
						isSameMusicSheet = true;
					}
				}
				if (!isSameMusicSheet) {
					MusicSheet ms = new MusicSheet();
					JSONObject jms  = null;
					jms = new JSONObject(musicSheetJSONObj.toString());
					ms.setName( (String) jms.get("name"));
					ms.setCreator((String)jms.get("creator"));
					ms.setCreatorId((String)jms.get("creatorId"));
					ms.setUuid((String)jms.get("uuid"));
					ms.setPicture((String)jms.get("picture"));
					ms.setDateCreated((String)jms.get("dateCreated"));
					ms.setMusicItems(musicItemsMap);
					msList.add(ms);
				}
			}
		}
		return msList;
	}
}
