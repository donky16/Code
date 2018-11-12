using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ConsoleApplication1.shiyan.sy9
{
    class RefAndStaticFun
    {
        public static void twoRefNumChangeStaticFun(ref int a, ref int b)
        {
            int temp;
            temp = a;
            a = b;
            b = temp;
        }
        public void twoRefNumChangeCommonFun(ref int a, ref int b)
        {
            int temp;
            temp = a;
            a = b;
            b = temp;
        }

        public static void Main(String[] args)
        {
            int i = 3;
            int j = 4;
            Console.WriteLine("i=3,j=4\n");

            RefAndStaticFun.twoRefNumChangeStaticFun(ref i, ref j);
            Console.WriteLine("Changeed By Static Function: i={0} j={1}", i, j);

            RefAndStaticFun r = new RefAndStaticFun();
            r.twoRefNumChangeCommonFun(ref i, ref j);
            Console.WriteLine("Changeed By Common Function: i={0} j={1}", i, j);

            Console.ReadLine();
        }
    }
}
