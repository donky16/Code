using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ConsoleApplication1.shiyan.sy5
{
    class College
    {
        public static void Main(String [] args)
        {
            String num = null;
            String name = null;
            String degree = null;
            String course = null;

            Console.Write("请输入你的学号：");
            num = Console.ReadLine();
            Console.Write("请输入你的姓名：");
            name = Console.ReadLine();

            int isError = 0;
            Console.Write("请选择你的学历(1.本科 2.硕士 3.博士)：");
            int flag = Convert.ToSByte(Console.ReadLine());
            switch (flag)
            {
                case 1:
                    degree = "本科";
                    Console.Write("请选择课程(1.C# 2.Java):");
                    int flag1 = Convert.ToSByte(Console.ReadLine());
                    switch (flag1)
                    {
                        case 1:
                            course = "C#";
                            break;
                        case 2:
                            course = "Java";
                            break;
                        default:
                            Console.Write("输入错误...\n");
                            isError = 1;
                            break;  
                    }
                    break;
                case 2:
                    degree = "硕士";
                    Console.Write("请选择课程(1.C#开发 2.Java开发):");
                    int flag2 = Convert.ToSByte(Console.ReadLine());
                    switch (flag2)
                    {
                        case 1:
                            course = "C#开发";
                            break;
                        case 2:
                            course = "Java开发";
                            break;
                        default:
                            Console.Write("输入错误...\n");
                            isError = 1;
                            break;
                    }
                    break;
                case 3:
                    degree = "博士";
                    Console.Write("请选择课程(1.C#研究 2.Java研究):");
                    int flag3 = Convert.ToSByte(Console.ReadLine());
                    switch (flag3)
                    {
                        case 1:
                            course = "C#研究";
                            break;
                        case 2:
                            course = "Java研究";
                            break;
                        default:
                            Console.Write("输入错误...\n");
                            isError = 1;
                            break;
                    }
                    break;
                default:
                    
                    isError = 1;
                    break;
            }
            if (isError == 1)
            {
                Console.WriteLine("输入存在错误,正在退出程序...");
            }
            else
                Console.WriteLine("选课信息为：\n学号：{0}\n姓名：{1}\n学历：{2}\n课程：{3}", num, name, degree, course);

            Console.ReadLine();
        }    
    }
}
