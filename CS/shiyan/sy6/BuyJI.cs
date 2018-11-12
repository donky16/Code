using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ConsoleApplication1.shiyan.sy6
{
    class BuyJI
    {
        public static void Main(String[] args)
        {
            Console.WriteLine("穷举法求解");
            for (int x = 0; x <= 20; x++)
            {
                for (int y = 0; y <= 33; y++)
                {
                    for (int z = 0; z <= 300; z++)
                    {
                        if (z % 3 == 0)
                        {
                            if (5 * x + 3 * y + z / 3 == 100 && x + y + z == 100)
                            {
                                Console.WriteLine("公鸡数：{0,2}，母鸡数：{1,2}，小鸡数：{2,2}", x, y, z);
                            }
                        }
                    }
                }
            }
            Console.WriteLine("二重循环法求解");
            int restMoney ;
            for (int m = 20; m >= 0; m--)
            {
                restMoney = 100 - 5 * m;
                for (int n = 0; n <= 100 - m; n ++)
                {
                    int temp;
                    temp = restMoney - 3 * n - (100 - m - n) / 3;
                    if (temp == 0 && (100 - m - n) % 3 == 0)
                    {
                        Console.WriteLine("公鸡数：{0,2}，母鸡数：{1,2}，小鸡数：{2,2}", m, n, 100 - m - n);
                    }             
                }
            }
            Console.ReadLine();
        }
    }
}