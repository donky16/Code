using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ConsoleApplication1.shiyan.sy7
{
    class Function
    {
        public double twoNumAdd(double a, double b)
        {
            return a + b;
        }

        public int twoNumAdd(int a, int b)
        {
            return a + b;
        }

        public double twoNumMultiply(double a, double b)
        {
            return a * b;
        }

        public int twoNumMultiply(int a, int b)
        {
            return a * b;
        }
    }
    class DelegateTest
    {
        delegate int intDelegate(int a, int b);
        static void Main(String[] args)
        {
            Function f = new Function();

            Console.WriteLine("请输入要操作的两个浮点数：");
            double x = Convert.ToDouble(Console.ReadLine());
            double y = Convert.ToDouble(Console.ReadLine());
            Console.WriteLine("积为{0}", f.twoNumMultiply(x, y));
            Console.WriteLine("和为{0}", f.twoNumAdd(x, y));

            intDelegate addDelegate = new intDelegate(f.twoNumAdd);
            intDelegate multiplyDelegate = new intDelegate(f.twoNumMultiply);

            int i = 0;
            while(i < 2)
            {
                Console.WriteLine("请输入要操作的两个整数：");
                int a = Convert.ToByte(Console.ReadLine());
                int b = Convert.ToByte(Console.ReadLine());
                if (a < 10 && b < 10)
                {
                    Console.WriteLine("两数都小于10，和为{0}", addDelegate(a, b));
                }
                else
                    Console.WriteLine("两数不都小于10，积为{0}", multiplyDelegate(a, b));
                i ++;
            }
            Console.ReadLine();
        }
    }
}
