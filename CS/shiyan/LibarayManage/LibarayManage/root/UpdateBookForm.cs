using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using MySql.Data.MySqlClient;

namespace LibarayManage.root
{
    public partial class UpdateBookForm : Form

    {
        private MySqlConnection conn;
        private int id;
        //      private string bookName = null;
        //      private string bookAuthor = null;

        /*
         * 连接数据库
         */
        public void ConnectMariaDB()
        {
            string connectionString = "server=localhost;user id=root;password=donky@16;database=libaraymanage";
            this.conn = new MySqlConnection(connectionString);
            conn.Open();
        }
        public UpdateBookForm(int id, string name, string author)
        {
            this.id = id;
            //            this.bookName = name;
            //            this.bookAuthor = author;
            InitializeComponent();
            this.IDLable.Text = Convert.ToString(this.id);
            bookNameText.Text = name;
            bookAuthorText.Text = author;
        }

        private void button1_Click(object sender, EventArgs e)
        {

            ConnectMariaDB();
            string updateBookString = "update books set name = '" + bookNameText.Text + "', author = '" + bookAuthorText.Text
                + "' where id = " + id + ";";
            MySqlCommand command = new MySqlCommand(updateBookString, conn);
            command.ExecuteNonQuery();
            MessageBox.Show("更新书籍信息成功！");

            conn.Close();
            Close();
        }


    }
}
