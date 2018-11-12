using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ConsoleApplication1.shiyan.第六章.sy1
{
    class StringManagement
    {
        public static void Main(string[] args)
        {
            string pendingString = "C:\\Program\\Work\\Test.cs";
            Console.WriteLine("File Path: {0}", pendingString);

            string drive = pendingString.Split(':')[0];
            string fileName = pendingString.Split('.')[0].Split('\\')[3];
            string fileType = pendingString.Split('.')[1];

            Console.WriteLine("Drive Name:{0}  File Name:{1}  File Type:{2}", drive, fileName, fileType);

            Console.ReadLine();
        }
    }
}
