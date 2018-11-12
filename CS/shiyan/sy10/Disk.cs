using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ConsoleApplication1.shiyan.sy10
{
    class Disk
    {
        protected double storge;    //用于派生类调用

        public Disk(double s)
        {
            this.storge = s;
        }

        public virtual void insertData(string data) { }
        public virtual void removeData() { }
    }
}
