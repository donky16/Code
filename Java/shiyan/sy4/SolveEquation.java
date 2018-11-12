package shiyan.sy4;

import java.text.DecimalFormat;

public class SolveEquation implements ISolveEquation{
	private DecimalFormat df = new DecimalFormat("#0.000");
	
	public void LinearEquationWithOneUnknown(Double a, Double b) {
		Double x = null;
		
		if (a == 0) {
			System.out.println("a不能为0，请重新选择...");
		} else {
			x = -(b/a);
			System.out.println("此方程的有一个解 x = " + df.format(x));
		}	
	}
	
	public void QuadraticEquaationWithOneUnknown(Double a, Double b, Double c) {
		Double x1 = null;
		Double x2 = null;
		
		Double e = b*b - 4*a*c;
		
		if (a == 0) {
			System.out.println("a不能为0，请重新选择...");
		} else {
			if (e == 0) {
				x1 = -(b/2*a);
				x2 = -(b/2*a);
				System.out.println("此方程的有两个相同的实数解 " + "\n" 
						+ "x1=x2=" + df.format(x1));
			} else if (e < 0) {
				System.out.println("此方程无实数解");
			} else {
				x1 = (-b + Math.pow(e, 0.5))/2*a;
				x2 = (-b - Math.pow(e, 0.5))/2*a;
				System.out.println("此方程有两个不同的实数解 " + "\n" 
						+ "x1=" + df.format(x1) + " x2=" + df.format(x2));
			}
		}
	}
	public void CubicEquationWithOneUnknown(Double a, Double b, Double c, Double d) {
		
		System.out.println("此功能未开放...");
	}
}
