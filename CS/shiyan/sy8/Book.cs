using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ConsoleApplication1.shiyan.sy8
{
    class Book
    {
        private string title;
        private string author;
        private double price;
        private int storage;

        public Book()
        {
            title = "Python3 程序开发手册";
            author = "Mark Summerfield";
            price = 69.00;
            storage = 100;
        }
        public Book(string t, string a, double p, int s)
        {
            this.title = t;
            this.author = a;
            this.price = p;
            this.storage = s;
        } 

/*      public void setTitle(string t)
        {
            this.title = t;
        }
        public void setAuthor(string a)
        {
            this.author = a;
        }
        public void setPrice(double p)
        {
            this.price = p;
        }
        public void setStorge(int s)
        {
            this.storage = s;
        }

        public string getTitle()
        {
            return this.title;
        }
        public string getAuthor()
        {
            return this.author;
        }
        public double getPrice()
        {
            return this.price;
        }
        public int getStorge()
        {
            return this.storage;
        }
*/
        public void displayBookInfo()
        {
            Console.WriteLine("Book Info:");
            Console.WriteLine("Title:{0} Author:{1} Price:{2} Storge:{3}", title, author, price, storage);
        }
        public static void Main(String[] args)
        {
            Console.WriteLine("/**Books Management**/");

            Console.WriteLine("1.Default Book:");
            Book defaultBook = new Book();
            defaultBook.displayBookInfo();

            Console.WriteLine("2.Custome A Book:");
            Console.Write("Input Title:");
            string title = Console.ReadLine();
            Console.Write("Input Author:");
            string author = Console.ReadLine();
            Console.Write("Input Price:");
            double price = Convert.ToDouble(Console.ReadLine());
            Console.Write("Input Storge:");
            int storge = Convert.ToInt32(Console.ReadLine());
            Book customeBook = new Book(title, author, price, storge);
            customeBook.displayBookInfo();

            Console.ReadLine();
        }
    }
}
