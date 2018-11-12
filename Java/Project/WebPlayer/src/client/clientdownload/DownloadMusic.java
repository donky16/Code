package client.clientdownload;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;

public class DownloadMusic {
	public void downloadMusic(String md5Value) throws Exception {
		String urlPath = "http://service.uspacex.com/music.server/downloadMusic?md5=" + md5Value;
		URL url = new URL(urlPath);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.connect();
		/**
		 * 获取带有文件名称Header中Content-Disposition信息 并筛选出文件名,使用UTF-8编码，解决中文乱码问题
		 */
		String attachment = connection.getHeaderField("Content-Disposition");
		String fileName = URLDecoder.decode(attachment.split("filename=")[1].split(".mp3")[0], "UTF-8");

		String saveDir = "F:\\CloudMusic";
		String extName = ".mp3";
		// 判断本地是否存在此音乐
		File file = new File(saveDir + File.separator + fileName + extName);
		if (!file.exists()) {
			InputStream inputStream = connection.getInputStream();
			// 读取数据流
			byte[] buffer = new byte[1024];
			int len = 0;
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			while ((len = inputStream.read(buffer)) != -1) {
				bos.write(buffer, 0, len);
			}
			bos.close();
			// 文件保存位置
			File file1 = new File(saveDir + File.separator + fileName + extName);
			// 将数据流写入File中
			FileOutputStream fos = new FileOutputStream(file1);
			fos.write(bos.toByteArray());
			fos.close();
			inputStream.close();
		}
	}
}
