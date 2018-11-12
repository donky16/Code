using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ConsoleApplication1.shiyan.第六章.sy3
{
    class ForEach
    {
        public static void Main(string[] args)
        {
            int[,] numArray = { { 1, 2, 3 }, { 3, 4, 5 } };
            foreach (int i in numArray)
            {
                Console.Write(i + " ");
            }
            Console.ReadLine();
        }
       
    }
}
