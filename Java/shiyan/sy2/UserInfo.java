package shiyan.sy2;

import java.util.Date;

public class UserInfo {
	/**
	 * 私有数据类型
	 * 定义账户信息
	 */
	private String num;
	private Double balance;
	private String userName;
	private Date time;
	private String id;
	
	public UserInfo() {
		// TODO Auto-generated constructor stub
		/**
		 * 构造方法
		 * 用于初始化账户信息
		 */
		num = "00000";
		balance = (double) 0;
		userName = "laowang";
		
		Date currDate = new Date();
		time = currDate;	// 获取当前时间
		
		id = "341224199802022589";
		
	}
	public Double getBalance() {	// 获取当前的余额
		return balance;
	}
	
	public void displayInfo() {		// 显示账户信息
		System.out.println("账号：" + num);
		System.out.println("姓名：" + userName);
		System.out.println("开户时间：" + time);
		System.out.println("身份证号：" + id);
	}
	
	public void saveMoney(Double money) {	// 存钱方法的实现
		balance = balance + money;
	}
	
	public void takeMoney(Double money) {	// 取钱方法的实现
		balance = balance - money;
	}
}
