package shiyan.sy2;

import java.util.Scanner;

import shiyan.sy2.UserInfo;	// 导入UserInfo类
public class BankAccount {
	private static Scanner scanner;

	public static void main(String[] args) {	// 主函数的实现
		UserInfo us = new UserInfo();	// 新建一个账户，信息为初始化值
		
		for(;;) {	// 循环操作
			scanner = new Scanner(System.in);
			
			int flag;	// 用来接收输入的选择
			System.out.println("请选择要进行的操作：1.存款 2.取款 3.查询 4.显示账户 5.退出");
			flag = scanner.nextInt();	// 将输入的int型赋值给flag
			
			switch (flag) {		// 通过flag值选择需要进行的操作
			case 1:		// 存钱的操作
				Double money1;
				System.out.println("请输入要存入的金额：");
				money1 = scanner.nextDouble(); 	// 将输入的Double型赋值给monery1
				us.saveMoney(money1);	// 调用UserInfo中的存钱方法
				System.out.println("存款成功！已存入" + money1 + "元"
						+ "可用余额为" + us.getBalance() + "元");
				break;
			case 2:		// 取钱的操作 同存钱操作
				Double money2;
				System.out.println("请输入要取款的金额：");
				money2 = scanner.nextDouble();
				if (money2 > us.getBalance()) {
					System.out.println("余额不足...");
				} else {
					us.takeMoney(money2);
					System.out.println("取款成功！已取款" + money2 + "元"
							+ "可用余额为" + us.getBalance() + "元");
				}
				break;
			case 3:		// 查询余额的操作
				System.out.println("目前余额为：" + us.getBalance() + "元"); 
				// 调用UserInfo中getBalance方法，查询余额
				break;
			case 4:		// 显示账户信息的操作
				us.displayInfo(); 	// 显示账户信息
				break;
			case 5:		// 直接结束switch选择语句
				break;
			default:	// 输入非选择的flag时，提示错误
				System.out.println("输入错误！请重新输入...");
				break;
			}
			if(flag == 5) {	// 当输入5时，结束循环
				System.out.println("程序已退出...");
				break;
			}
		}
	}
}
