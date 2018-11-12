using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ConsoleApplication1.shiyan.sy11
{
    abstract class FangXing
    {
        protected double length = 0;
        public abstract double getArea();
    }

    class ZhengFangXing : FangXing
    {
        public ZhengFangXing(double length)
        {
            this.length = length;
        }
        public override double getArea()
        {
            return length * length;
        }
        public static void Main(string[] args)
        {
            Console.Write("请输入正方形的边长：");
            double length = Convert.ToDouble(Console.ReadLine());
            ZhengFangXing zfx = new ZhengFangXing(length);
            Console.WriteLine("它的面积为：{0}", zfx.getArea());
            Console.ReadLine();
        }
    }
}
