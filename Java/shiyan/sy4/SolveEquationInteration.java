package shiyan.sy4;

import java.util.Scanner;

public class SolveEquationInteration {

	private static Scanner scanner;

	public static void main(String[] args) {
		ISolveEquation ise = new SolveEquation();
		scanner = new Scanner(System.in);
		
		for(;;) {
			int flag;
			Double a, b, c, d;
			System.out.println("请选择方程类型：1.一元一次方程 2.一元二次方程 3.一元三次方程");
			flag = scanner.nextInt();
			
			switch (flag) {
			case 1:
				System.out.println("你选择的是一元一次方程，形如 ax+b=0");
				
				System.out.println("请输入a：");
				a = scanner.nextDouble();
				System.err.println("请输入b：");
				b = scanner.nextDouble();
				
				ise.LinearEquationWithOneUnknown(a, b);
				
				break;
			case 2:
				System.out.println("你选择的是一元二次方程，形如 ax^2+bx+c=0");
				
				System.out.println("请输入a：");
				a = scanner.nextDouble();
				System.err.println("请输入b：");
				b = scanner.nextDouble();
				System.err.println("请输入c：");
				c = scanner.nextDouble();
				
				ise.QuadraticEquaationWithOneUnknown(a, b, c);
				
				break;
			case 3:
				System.out.println("你选择的是一元二次方程，形如 ax^2+bx+c=0");
				
				System.out.println("请输入a：");
				a = scanner.nextDouble();
				System.err.println("请输入b：");
				b = scanner.nextDouble();
				System.err.println("请输入c：");
				c = scanner.nextDouble();
				System.err.println("请输入d：");
				d = scanner.nextDouble();
				
				ise.CubicEquationWithOneUnknown(a, b, c, d);
				
				break;
			default:
				break;
			}	
			System.out.println("你是否想继续（y/n）？");
			String str = scanner.next();
			
			if("y".equals(str)) {
				continue;
			} else if ("n".equals(str)){
				System.out.println("退出程序...");;
				break;
			} else {
				System.out.println("输入错误！默认程序退出...");
				break;
			}
		}
	}
}
