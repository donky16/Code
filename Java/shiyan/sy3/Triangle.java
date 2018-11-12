package shiyan.sy3;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Scanner;

public class Triangle{
	private Double point1X;
	private Double point1Y;
	private Double point2X;
	private Double point2Y;
	private Double point3X;
	private Double point3Y;
	private static Scanner scanner;

	public ArrayList<Double> getSideArray(){
		/**
		 * 通过三个点的坐标，计算出三个边长
		 * @return 含有三个Double类型的List
		 */
		ArrayList<Double> sideArray = new ArrayList<Double>();
		
		Double sideA =  Math.pow((Math.pow((point1X-point2X),2) 
				+ Math.pow((point1Y-point2Y),2)),0.5);
		Double sideB =  Math.pow((Math.pow((point2X-point3X),2) 
				+ Math.pow((point3Y-point2Y),2)),0.5);
		Double sideC =  Math.pow((Math.pow((point1X-point3X),2) 
				+ Math.pow((point1Y-point3Y),2)),0.5);
		
		sideArray.add(sideA);
		sideArray.add(sideB);
		sideArray.add(sideC);
		
		return sideArray;
	}
	public Double getArea() {
		/**
		 * 通过三个边长计算面积
		 * @return 面积Double型
		 */
		ArrayList<Double> sideArray = new ArrayList<Double>();
		sideArray = getSideArray();	// 调用getSideArray方法获得三个边长的List
		
		Double result = (double) 0;
		Double p = (sideArray.get(0) + sideArray.get(1) + sideArray.get(2))/2;
		// 求出三个边长和的一半
		result = Math.pow(p*(p-sideArray.get(0))*(p-sideArray.get(1))*(p-sideArray.get(2)), 0.5);
		
		return result;
	}
	
	public Double getCircumference() {
		/**
		 * 通过三个边长计算周长
		 * @return 周长Double型
		 */
		ArrayList<Double> sideArray = new ArrayList<Double>();
		sideArray = getSideArray();	// 调用getSideArray方法获得三个边长的List
		
		Double result = (double) 0;
		result = sideArray.get(0) + sideArray.get(1) + sideArray.get(2);
		// 求出周长
		return result;
	}
	
	public static void main(String [] args) {
		Triangle t = new Triangle(); // 新建一个三角形类
		scanner = new Scanner(System.in);
		// 给三角形的三个顶点的坐标赋值
		System.out.println("请输入第一个点的横坐标：");
		t.point1X = scanner.nextDouble();
		System.out.println("请输入第一个点的纵坐标：");
		t.point1Y = scanner.nextDouble();
		System.out.println("请输入第二个点的横坐标：");
		t.point2X = scanner.nextDouble();
		System.out.println("请输入第二个点的纵坐标：");
		t.point2Y = scanner.nextDouble();
		System.out.println("请输入第三个点的横坐标：");
		t.point3X = scanner.nextDouble();
		System.out.println("请输入第三个点的纵坐标：");
		t.point3Y = scanner.nextDouble();
		
		ArrayList<Double> sideArray = new ArrayList<Double>();
		sideArray = t.getSideArray();	// 调用getSideArray方法获得三个边长的List，以供显示边长使用
		
		DecimalFormat df = new DecimalFormat("#0.000");	
		// 将Double型数据转化为三位小数类型，df.format(Double)
		
		Double area = t.getArea();	// 调用方法计算面积
		Double circumference = t.getCircumference();	// 调用方法计算周长
		
		System.out.println("此三角形的三边长为：" + df.format(sideArray.get(0)) + " "
				+ df.format(sideArray.get(1)) + " " + df.format(sideArray.get(2)));
		System.out.println("此三角形的面积为：" + df.format(area));
		System.out.println("此三角形的周长为：" + df.format(circumference));
	}
}
