package shiyan.sy9;			//版权所有，违反必究

import java.io.File;
import java.io.IOException;
import java.util.Calendar;

public class FileOperaterWithMysql {
	public void LogData(String url, String user, String pwd, String log, String hostname) {
		LogDao lg = new LogDao(url, user, pwd);
		lg.ConnectDB();
		if (lg.getConn() != null) {
			if (!lg.IsTableExits()) {
				System.out.println("Table is not exiting!\n" + "Creating Table named filesyslog...");
			} else {
				lg.InsertData(log, hostname);
				System.out.println("Log With Mysql Succeed!");
			}
		}
	}

	public void IsError() {
		System.out.println(
				"Fsops : 1.0 \nNo vaild arguments. " + "See the output of \"java -jar fsopsWithMysql.jar help.\"");
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		FileOperaterWithMysql fo = new FileOperaterWithMysql();

		if (args.length == 0) {
			fo.IsError();
		} else {
			if (args[0].equals("help")) {
				System.out.println(
						"Usage:\n" + "-logdb [url] [user] [password] mkdir /root/test1 /root/test2 ;创建新的文件夹(可以创建多个)\n"
								+ "-logdb [url] [user] [password] mfile /root/test.txt  ;创建新的文件\n"
								+ "-logdb [url] [user] [password] rm /root/test1/test.txt  ;删除文件\n"
								+ "-logdb [url] [user] [password] rm /root/test1  ;删除文件夹\n"
								+ "-logdb [url] [user] [password] ls /root/test1  ;显示此目录下的文件及文件夹\n" + "self  ;显示软件信息\n"
								+ "help  ;显示当前帮助文档");
			} else if (args[0].equals("self")) {
				System.out.println("Fsops : 1.0\nAuthor : donky16\nCreate Data ： 2017.10.30");
			} else if(args.length >= 6 && args[0].equals("-logdb")){
				String url = args[1];
				String user = args[2];
				String pwd = args[3];
				String hostname = "root";
				StringBuffer sb = new StringBuffer();
				for (int j = 4; j < args.length; j++) {
					sb.append(args[j]);
					sb.append(" ");
				}
				String log = sb.toString();

				String CMD = args[4];
				if (args[5] == null) {
					fo.IsError();
				}
				if (CMD.equals("mkdir")) {
					int i = 5;
					while (i < args.length) {
						File file1 = new File(args[i]);
						if (file1.exists()) {
							System.out.println("文件目录已存在！");
						} else {
							file1.mkdirs();
						}
						i = i + 1;
					}
					fo.LogData(url, user, pwd, log, hostname);
				} else if (CMD.equals("mfile")) {
					File file2 = new File(args[5]);
					if (file2.exists()) {
						System.out.println("文件已存在，删除了哦...");
						file2.delete();
					} else {
						try {
							file2.createNewFile();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					fo.LogData(url, user, pwd, log, hostname);
				} else if (CMD.equals("ls")) {
					System.out.println("Directory:" + args[5]);
					System.out.println("    --------------------------------");
					System.out.format("%-25s%-40s%-4s", "Name", "Time Modified", "Type");
					System.out.println();
					File file3 = new File(args[5]);
					File[] files = file3.listFiles();
					for (File f : files) {
						long modifiedTime = f.lastModified();
						Calendar cal = Calendar.getInstance();
						cal.setTimeInMillis(modifiedTime);
						if (f.isDirectory()) {
							System.out.format("%-25s%-40s%-4s", f.getName(), cal.getTime(), "d");
							System.out.println();
						} else {
							System.out.format("%-25s%-40s%-4s", f.getName(), cal.getTime(), "f");
							System.out.println();
						}
					}
					fo.LogData(url, user, pwd, log, hostname);
				} else if (CMD.equals("rm")) {
					File file4 = new File(args[5]);
					file4.delete();
					fo.LogData(url, user, pwd, log, hostname);
				} else {
					fo.IsError();
				}
			}else {
				fo.IsError();
			}
		}
	}
}
