package po;


public class Music {

	private String name;
	private String md5value;
	private String path;

	public Music() {
		
	}
	public Music(String name) {
		this.name = name;
		this.path = "/root/CloudMusic/" + name + ".mp3";
	}
	public Music(String name, String md5value) {
		this.name = name;
		this.md5value = md5value;
		this.path = "/root/CloudMusic/" + name + ".mp3";
	}

	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMd5Value() {
		return md5value;
	}

	public void setMd5value(String md5value) {
		this.md5value = md5value;
	}
	public String getPath() {
		return path;
	}

}