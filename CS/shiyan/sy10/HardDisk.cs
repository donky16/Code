using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ConsoleApplication1.shiyan.sy10
{
   
    class HardDisk : Disk
    {
        private string data;
        private string hardDiskBrand;
        /*
         * 派生类创建实例时，先调用基类构造函数，再调用自身构造函数
         * 但当基类构造函数中有重载或者参数传递时，应显式地使用base调用基类构造函数
         * 在派生类中，构造函数调用基类构造函数，完成对基类的成员的初始化，方法为base(参数)
         */
        public HardDisk(double s, string hdb): base(s)
        {
            this.hardDiskBrand = hdb;
        }

        public void displayData()
        {
            if(this.data != null)
            {
                Console.WriteLine("Storge:{1}G,data:{0}", this.data, base.storge); 
                //派生类调用基类成员，使用base.成员
            }
            else
            {
                Console.WriteLine("Storge:{0}G,date has been removed!", base.storge);
            }
        }
        public override void insertData(string data)
        {
            this.data = data;
        }
        public override void removeData()
        {
            this.data = null;
        }

        public static void Main(String[] args)
        {
            Console.WriteLine("Storge:1T Brand:Kingson \nCreating HardDisk...");
            HardDisk hd = new HardDisk(1024, "Kingston");

            Console.Write("Insert Data:");
            string data = Console.ReadLine();
            hd.insertData(data);
            hd.displayData();

            Console.WriteLine("Remove Data...");
            hd.removeData();
            hd.displayData();

            Console.ReadLine();
        }
    }
}
