using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Collections;

namespace ConsoleApplication1.shiyan.第六章.sy2
{
    class SetType
    {
        public static void Main(string[] args)
        {
            Queue q = new Queue(50);
            Stack s = new Stack(50);
            Hashtable hs = new Hashtable(50);
            ArrayList al = new ArrayList(50);

            q.Enqueue("HaHaHa!");
            q.Enqueue("HeHeHe!");
            Console.WriteLine(q.Dequeue());

            s.Push("HaHaHa!");
            s.Push("HeHeHe!");
            Console.WriteLine(s.Pop());

            hs.Add(1, "HaHaHa!");
            hs.Add(2, "HeHeHe!");
            hs.Remove(1);
            Console.WriteLine(hs[2]);

            al.Add("HaHaHa!");
            al.Add("HeHeHe!");
            al.Remove("HaHaHa!");
            Console.WriteLine(al[0]);

            Console.ReadLine();
        }
    }
}
