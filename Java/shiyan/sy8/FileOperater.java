package shiyan.sy8;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Formatter;
import java.util.Locale;

import javax.xml.crypto.Data;

public class FileOperater {
	public static void IsError() {
		System.out.println("Fsops : 1.0 \nNo vaild arguments. " + "See the output of \"java -jar fsops.jar help.\"");
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub	
		if(args.length == 0) {
			IsError();
		}else {		
			String CMD = args[0];
			if(CMD.equals("mkdir")) {
				int i = 1;
				while(i < args.length) {
					File file1 = new File(args[i]);
					if (file1.exists()) {
						System.out.println("文件目录已存在！");
					} else {
						file1.mkdirs();
					}
					i = i + 1;
				}
			}else if(CMD.equals("mfile")) {
				File file2 = new File(args[1]);
				if(file2.exists()) {
					System.out.println("文件已存在，删除了哦...");
					file2.delete();
				}else {
					try {
						file2.createNewFile();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}else if(CMD.equals("ls")) {
				System.out.println("Directory:" + args[1]);
				System.out.println("    --------------------------------");
				System.out.format("%-25s%-40s%-4s","Name","Time Modified","Type");
				System.out.println();
				File file3 = new File(args[1]);
				File[] files = file3.listFiles();
				for(File f : files) {
					long modifiedTime = f.lastModified();
					Calendar cal = Calendar.getInstance();
					cal.setTimeInMillis(modifiedTime);
					if(f.isDirectory()) {
						System.out.format("%-25s%-40s%-4s",f.getName(),cal.getTime(),"d");
						System.out.println();
					}else {
						System.out.format("%-25s%-40s%-4s",f.getName(),cal.getTime(),"f");
						System.out.println();
					}
				}
			}else if(CMD.equals("rm")) {
				File file4 = new File(args[1]);
				file4.delete();
			}else if(CMD.equals("help")) {
				System.out.println("Usage:\n"
						+ "mkdir /root/test1 /root/test2 ;创建新的文件夹(可以创建多个)\n"
						+ "mfile /root/test.txt  ;创建新的文件\n"
						+ "rm /root/test1/test.txt  ;删除文件\n"
						+ "rm /root/test1  ;删除文件夹\n"
						+ "ls /root/test1  ;显示此目录下的文件及文件夹\n"
						+ "self  ;显示软件信息\n"
						+ "help  ;显示当前帮助文档");
			}else if(CMD.equals("self")){
				System.out.println("Fsops : 1.0\nAuthor : donky16\nCreate Data ： 2017.10.30");
			}else {
				IsError();
			}
		}
	}

}
